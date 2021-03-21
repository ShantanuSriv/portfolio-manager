package com.smallcase.portfoliomanager.models.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.smallcase.portfoliomanager.models.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Shantanu
 * Request body for a new Trade request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TradeRequestDto {
    @NotEmpty(message = "ticker symbol must not be empty")
    private String tickerSymbol;

    @NotNull(message = "please provide number of shares you want to buy/sell")
    @Min(value = 1, message = "number of shares must be at least 1")
    private int shares;

    private TradeType tradeType;

    @DecimalMin(value = "0.1", message = "price must be at least 0.1")
    private double price;
}
