package com.smallcase.portfoliomanager.models.mapper;

import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.TradeResponseDto;
import lombok.Builder;

/**
 * @author Shantanu
 * Mapper class for Mapping Trade DTO and DAO classes
 */
@Builder
public class TradeMapper {
    public static TradeResponseDto toDto(Trade trade) {
        return TradeResponseDto.builder()
                .id(trade.getId())
                .tickerSymbol(trade.getTickerSymbol())
                .price(trade.getPrice())
                .shares(trade.getShares())
                .build();
    }
}
