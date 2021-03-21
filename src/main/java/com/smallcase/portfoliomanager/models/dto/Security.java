package com.smallcase.portfoliomanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Shantanu
 * Holding of a portfolio
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Security {
    private String tickerSymbol;
    private Double averageBuyPrice;
    private int shares;
}
