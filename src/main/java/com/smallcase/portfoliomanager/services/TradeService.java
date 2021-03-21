package com.smallcase.portfoliomanager.services;

import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.TradeRequestDto;
import com.smallcase.portfoliomanager.models.dto.TradeResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Shantanu
 * Interface for TradeService
 */
public interface TradeService {
    /**
     * Create a new trade
     * @param tradeRequestDto
     * @return
     */
    TradeResponseDto addTrade(TradeRequestDto tradeRequestDto);

    /**
     * Update an existing active trade
     * @param id
     * @param tradeRequestDto
     * @return
     */
    TradeResponseDto updateTrade(String id, TradeRequestDto tradeRequestDto);

    /**
     * Delete an existing trade
     * @param id
     */
    void deleteTrade(String id);

    /**
     * Fetch all saved trades
     * @return
     */
    Map<String, List<Trade>> fetchTrades();

    /**
     * Deactivate all existing trades in the database
     * @param tickerSymbol
     */
    void deactivateTrades(String tickerSymbol);

    /**
     * Fetch all active trades
     * @param tickerSymbol
     * @return
     */
    List<Trade> fetchActiveTrades(String tickerSymbol);
}
