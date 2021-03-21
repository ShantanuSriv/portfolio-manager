package com.smallcase.portfoliomanager.services.implementation;

import com.smallcase.portfoliomanager.exceptions.NoSuchElementException;
import com.smallcase.portfoliomanager.exceptions.ValidationException;
import com.smallcase.portfoliomanager.models.dao.Portfolio;
import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.Security;
import com.smallcase.portfoliomanager.models.enums.TradeType;
import com.smallcase.portfoliomanager.repositories.PortfolioRepository;
import com.smallcase.portfoliomanager.services.PortfolioService;
import com.smallcase.portfoliomanager.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shantanu
 * An implementation of PortfolioServie interface
 */
@Service
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private TradeService tradeService;

    /**
     * Updates portfolio
     * @param trade
     */
    @Override
    public void updatePortfolio(Trade trade) {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        if (Objects.nonNull(portfolioList) && portfolioList.size() > 1) {
            throw new ValidationException("Portfolio update has been aborted as portfolio size is invalid");
        }

        Portfolio portfolio;
        if (CollectionUtils.isEmpty(portfolioList)) {
            // Adding first entry for a portfolio
            portfolio = Portfolio.builder()
                    .securityMap(new HashMap<>())
                    .build();
            portfolioList = new ArrayList<>();
            portfolioList.add(portfolio);
        }
        portfolio = portfolioList.get(0);
        Map<String, Security> securityMap = portfolio.getSecurityMap();
        if (!securityMap.containsKey(trade.getTickerSymbol())) {
            if (trade.getTradeType().equals(TradeType.SELL)) {
                throw new ValidationException("Sell trade can not be executed as no security present for sell");
            }
            Security security = Security.builder()
                    .tickerSymbol(trade.getTickerSymbol())
                    .shares(trade.getShares())
                    .averageBuyPrice(trade.getPrice())
                    .build();
            securityMap.put(trade.getTickerSymbol(), security);
        } else {
            Security security = securityMap.get(trade.getTickerSymbol());
            List<Trade> tradeList = tradeService.fetchActiveTrades(trade.getTickerSymbol());
            int newShareCount = 0;
            // Get the count of active share count
            for (Trade trade1: tradeList) {
                if (trade1.getTradeType().equals(TradeType.BUY)) {
                    newShareCount += trade1.getShares();
                } else {
                    newShareCount -= trade1.getShares();
                }
            }
            if (trade.getTradeType().equals(TradeType.SELL)) {
                if (security.getShares() < trade.getShares()) {
                    throw new ValidationException("Number of sell shares should be less that total holdings for " + trade.getTickerSymbol());
                }
                // remove the trade entry from the portfolio and deactivate all trades with the tickerSymbol
                if (newShareCount <= 0) {
                    securityMap.remove(trade.getTickerSymbol());
                    tradeService.deactivateTrades(trade.getTickerSymbol());
                } else {
                    security.setShares(newShareCount);
                    securityMap.put(trade.getTickerSymbol(), security);
                }
            } else {
                security.setAverageBuyPrice((security.getAverageBuyPrice() * security.getShares() + trade.getPrice() * trade.getShares()) / newShareCount);
                security.setShares(newShareCount);
                securityMap.put(trade.getTickerSymbol(), security);
            }
        }
        portfolio.setSecurityMap(securityMap);
        portfolioRepository.save(portfolio);
    }

    /**
     * Revert portfolio changes from a trade delete operation
     * @param trade
     */
    public void revertPortfolio(Trade trade) {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        if (CollectionUtils.isEmpty(portfolioList) || portfolioList.size() != 1) {
            throw new ValidationException("Portfolio revert has been aborted as portfolio size is invalid");
        }
        Portfolio portfolio = portfolioList.get(0);
        Map<String, Security> securityMap = portfolio.getSecurityMap();
        if (!securityMap.containsKey(trade.getTickerSymbol())) {
            throw new NoSuchElementException("No security found in the portfolio havind id " + trade.getTickerSymbol());
        }
        Security existingSecurity = securityMap.get(trade.getTickerSymbol());
        int remainingShares = existingSecurity.getShares() - trade.getShares();
        if (trade.getTradeType().equals(TradeType.SELL)) {
            if (remainingShares < 0) {
                throw new ValidationException("Number of shares deleted from the portfolio must be less than current holding");
            }
        } else {
            // Average price is being calculated here by doing opposite of what is done in case of a new BUY order - since this is a rollback
            existingSecurity.setAverageBuyPrice((existingSecurity.getAverageBuyPrice() * existingSecurity.getShares() - trade.getPrice() * trade.getShares()) / remainingShares);
        }
        if (remainingShares <= 0) {
            securityMap.remove(trade.getTickerSymbol());
            tradeService.deactivateTrades(trade.getTickerSymbol());
        } else {
            existingSecurity.setShares(remainingShares);
            securityMap.put(trade.getTickerSymbol(), existingSecurity);
        }
        portfolio.setSecurityMap(securityMap);
        portfolioRepository.save(portfolio);
    }

    /**
     * Fetch portfolios
     * @return
     */
    @Override
    public List<Security> fetchAll() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        if (CollectionUtils.isEmpty(portfolioList)) {
            return new ArrayList<>();
        }
        if (!CollectionUtils.isEmpty(portfolioList) && portfolioList.size() > 1) {
            throw new ValidationException("Portfolio fetching has been aborted as portfolio size is invalid");
        }
        return portfolioList.get(0).getSecurityMap().values().stream().collect(Collectors.toList());
    }

    /**
     * Fetch total returns
     * @return
     */
    @Override
    public double fetchReturns() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        if (CollectionUtils.isEmpty(portfolioList)) {
            throw new ValidationException("No portfolio found");
        }
        if (!CollectionUtils.isEmpty(portfolioList) && portfolioList.size() > 1) {
            throw new ValidationException("Portfolio returns has been aborted as portfolio size is invalid");
        }
        Map<String, Security> securityMap = portfolioList.get(0).getSecurityMap();
        double returns = 0;
        for (Security security: securityMap.values()) {
            returns += (100.0 /* Current Price Default Value */ - security.getAverageBuyPrice()) * security.getShares();
        }
        return returns;
    }
}
