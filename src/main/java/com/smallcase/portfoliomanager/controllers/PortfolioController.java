package com.smallcase.portfoliomanager.controllers;

import com.smallcase.portfoliomanager.models.dto.Security;
import com.smallcase.portfoliomanager.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shantanu
 * Portfolio related end-points
 */
@RestController
@RequestMapping(value = "/v1/portfolios")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping(value = "/fetch/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Security> fetchAll() {
        return portfolioService.fetchAll();
    }

    @GetMapping(value = "/fetch/returns",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public double fetchReturns() {
        return portfolioService.fetchReturns();
    }

}
