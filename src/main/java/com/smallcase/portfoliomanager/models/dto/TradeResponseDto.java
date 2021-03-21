package com.smallcase.portfoliomanager.models.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shantanu
 * Trade response body for a new trade
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeResponseDto {
    private String id;
    private String tickerSymbol;
    private int shares;
    private double price;

}
