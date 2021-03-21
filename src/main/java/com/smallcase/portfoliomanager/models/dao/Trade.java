package com.smallcase.portfoliomanager.models.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.smallcase.portfoliomanager.models.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * @author Shantanu
 * An entity class for the collection trades
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "trades")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Trade {
    @Id
    @JsonProperty(value = "id")
    private String id;
    private String tickerSymbol;
    private int shares;
    private Double price;
    private TradeType tradeType;
    private boolean isActive;
}
