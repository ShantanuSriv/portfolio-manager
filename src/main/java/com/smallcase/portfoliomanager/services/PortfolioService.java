package com.smallcase.portfoliomanager.services;

import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.Security;

import java.util.List;

/**
 * @author Shantanu
 * Interface for PortfolioService
 */
public interface PortfolioService {
    /**
     * Updates a portfolio
     * @param trade
     * @return
     */
    boolean updatePortfolio(Trade trade, String id);

    /**
     * Revert changes from a trade for a portfolio
     * @param trade
     */
    void revertPortfolio(Trade trade);

    /**
     * Fetch all portfolios
     * @return
     */
    List<Security> fetchAll();

    /**
     * Fetch total return of a portfolio
     * @return
     */
    double fetchReturns();
}
