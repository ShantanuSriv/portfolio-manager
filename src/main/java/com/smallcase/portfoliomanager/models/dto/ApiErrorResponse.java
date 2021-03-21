package com.smallcase.portfoliomanager.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Shantanu
 * HTTP DTO for Exception Handling
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private String message;
    private int status;
    private Date timestamp;
}
