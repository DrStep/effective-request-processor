package org.vminds.requests.processor.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.vminds.requests.processor.dto.StatisticsRequest;
import org.vminds.requests.processor.dto.StatisticsResponse;
import org.vminds.requests.processor.service.StatsService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;


@RestController
@RequestMapping("/api/v1")
public class StatisticsController {

    Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    private StatsService statsService;


    @PostMapping("/statistics")
    @Operation(summary = "Request statistics for the customer")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = StatisticsResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Request could not been parsed correctly")
            }
    )
    public ResponseEntity<StatisticsResponse> getStatisticsForCustomer(@Valid @RequestBody StatisticsRequest request) {
        logger.debug("Incoming request for statistics: {}", request);
        try {
            var dateTime = LocalDate.parse(request.date()).atStartOfDay(ZoneOffset.UTC);
            var res = statsService.getStatsForCustomer(request.customerID(), dateTime);
            return new ResponseEntity<>(
                    res,
                    HttpStatus.OK
            );
        } catch (DateTimeParseException parseError) {
            logger.debug("Could not parse date: {}", parseError.getMessage());
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect date format, should be yyyy-MM-dd");
        }

    }

}
