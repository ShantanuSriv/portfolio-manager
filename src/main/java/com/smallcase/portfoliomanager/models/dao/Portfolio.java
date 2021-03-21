package com.smallcase.portfoliomanager.models.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.smallcase.portfoliomanager.models.dto.Security;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Shantanu
 * An entity class for the collection portfolios
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "portfolios")
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Portfolio {
    @Id
    @JsonProperty(value = "id")
    private String id;
    Map<String, Security> securityMap;
}
