package com.smallcase.portfoliomanager.services.implementation;

import com.smallcase.portfoliomanager.exceptions.NoSuchElementException;
import com.smallcase.portfoliomanager.exceptions.ValidationException;
import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.TradeRequestDto;
import com.smallcase.portfoliomanager.models.dto.TradeResponseDto;
import com.smallcase.portfoliomanager.models.mapper.TradeMapper;
import com.smallcase.portfoliomanager.repositories.TradeRepository;
import com.smallcase.portfoliomanager.services.PortfolioService;
import com.smallcase.portfoliomanager.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Shantanu
 * An implementation for TradeService interface
 */
@Service
@Slf4j
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private PortfolioService portfolioService;

    /**
     * Creates a new trade transaction
     * @param tradeRequestDto
     * @return
     */
    @Override
    public TradeResponseDto addTrade(TradeRequestDto tradeRequestDto) {
        Trade trade = Trade.builder()
                .tickerSymbol(tradeRequestDto.getTickerSymbol())
                .tradeType(tradeRequestDto.getTradeType())
                .price(tradeRequestDto.getPrice())
                .shares(tradeRequestDto.getShares())
                .isActive(true)
                .build();
        trade = tradeRepository.save(trade);
        portfolioService.updatePortfolio(trade);
        return TradeMapper.toDto(trade);
    }

    /**
     * Updates an exisiting active trade
     * @param id
     * @param tradeRequestDto
     * @return
     */
    @Override
    public TradeResponseDto updateTrade(String id, TradeRequestDto tradeRequestDto) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        if (ObjectUtils.isEmpty(optionalTrade)) {
            throw new NoSuchElementException("No elements found having id " + id);
        }
        Trade trade = optionalTrade.get();
        if (!trade.isActive()) {
            throw new ValidationException("Update on an inactive trade id requested, hence aborting.");
        }
        if (!trade.getTickerSymbol().equalsIgnoreCase(tradeRequestDto.getTickerSymbol())) {
            throw new ValidationException("Not allowed to change ticker symbol");
        }
        // Copy all properties
        BeanUtils.copyProperties(tradeRequestDto, trade);
        trade.setActive(true);
        Trade newTrade = tradeRepository.save(trade);
        portfolioService.updatePortfolio(newTrade);
        return TradeMapper.toDto(trade);
    }

    /**
     * Deletes an existing trade
     * @param id
     */
    @Override
    public void deleteTrade(String id) {
        Optional<Trade> optionalTrade = tradeRepository.findById(id);
        if (ObjectUtils.isEmpty(optionalTrade)) {
            throw new NoSuchElementException("No elements found having id " + id);
        }
        Trade trade = optionalTrade.get();
        portfolioService.revertPortfolio(trade);
        tradeRepository.deleteById(id);
    }

    /**
     * Fetch all trades
     * @return
     */
    @Override
    public Map<String, List<Trade>> fetchTrades() {
        List<Trade> tradeList = tradeRepository.findAll();
        Map<String, List<Trade>> responseMap = new HashMap<>();
        if (CollectionUtils.isEmpty(tradeList)) {
            return responseMap;
        }
        tradeList.forEach(trade -> {
            if (!responseMap.containsKey(trade.getTickerSymbol())) {
                responseMap.put(trade.getTickerSymbol(), new ArrayList<>(Arrays.asList(trade)));
            } else {
                responseMap.get(trade.getTickerSymbol()).add(trade);
            }
        });
        return responseMap;
    }

    /**
     * Deactivates trades
     * @param tickerSymbol
     */
    @Override
    public void deactivateTrades(String tickerSymbol) {
        List<Trade> tradeList = tradeRepository.findByTickerSymbol(tickerSymbol);
        if (!CollectionUtils.isEmpty(tradeList)) {
            tradeList = tradeList.stream().filter(Trade::isActive).collect(Collectors.toList());
            tradeList.stream().forEach(trade -> trade.setActive(false));
            tradeRepository.saveAll(tradeList);
        }
    }

    /**
     * Fetch active trades
     * @param tickerSymbol
     * @return
     */
    @Override
    public List<Trade> fetchActiveTrades(String tickerSymbol) {
        List<Trade> tradeList = tradeRepository.findByTickerSymbol(tickerSymbol);
        if (CollectionUtils.isEmpty(tradeList)) {
            return null;
        }
        return tradeList.stream().filter(Trade::isActive).collect(Collectors.toList());
    }


}
