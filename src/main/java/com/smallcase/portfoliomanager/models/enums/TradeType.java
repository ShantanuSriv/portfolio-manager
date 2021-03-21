package com.smallcase.portfoliomanager.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shantanu
 * TradeType allowed enum class
 */
@AllArgsConstructor
@Getter
public enum TradeType {
    BUY("BUY"), SELL("SELL");
    private String value;
}
