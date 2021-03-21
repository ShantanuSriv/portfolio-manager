package com.smallcase.portfoliomanager.controllers;

import com.smallcase.portfoliomanager.models.dao.Trade;
import com.smallcase.portfoliomanager.models.dto.TradeRequestDto;
import com.smallcase.portfoliomanager.models.dto.TradeResponseDto;
import com.smallcase.portfoliomanager.services.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author Shantanu
 * Trade related end-points
 */
@RestController
@RequestMapping(value = "/v1/trades")
@Slf4j
public class TradeController {
    @Autowired
    TradeService tradeService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TradeResponseDto createTrade(@Valid @RequestBody TradeRequestDto tradeRequestDto) {
        log.info("Request received to create trade {}", tradeRequestDto);
        return tradeService.addTrade(tradeRequestDto);
    }

    @PutMapping(value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TradeResponseDto updateTrade(@PathVariable String id, @Valid @RequestBody TradeRequestDto tradeRequestDto) {
        log.info("Request received to update trade {}", tradeRequestDto);
        return tradeService.updateTrade(id, tradeRequestDto);
    }

    @DeleteMapping(value = "/delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTrade(@PathVariable String id) {
        log.info("Request received to delete trade with trade id {}", id);
        tradeService.deleteTrade(id);
    }

    @GetMapping(value = "/fetch/all",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, List<Trade>> fetchTrades() {
        log.info("Request received to fetch all trades");
        return tradeService.fetchTrades();
    }





}
