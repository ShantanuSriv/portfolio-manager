package com.smallcase.portfoliomanager.repositories;

import com.smallcase.portfoliomanager.models.dao.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Shantanu
 * Interface for Trade
 */
@Repository
public interface TradeRepository extends MongoRepository<Trade, String> {
    List<Trade> findByTickerSymbol(String tickerSymbol);
}
