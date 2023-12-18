package org.vminds.requests.processor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vminds.requests.processor.service.RequestProcessor;
import org.vminds.requests.processor.service.StatsService;
import org.vminds.requests.processor.service.ValidationRequestService;
import org.vminds.requests.processor.service.validation.result.BusinessLogicConstraintsError;
import org.vminds.requests.processor.service.validation.result.ParsingError;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;

@RestController
@RequestMapping("/api/v1")
public class RequestController {

    Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private ValidationRequestService validationService;

    @Autowired
    private StatsService statsService;

    @Autowired
    private RequestProcessor requestProcessor;

    @PostMapping("/request")
    @Operation(summary = "Method for validating and processing customer's requests")
    @ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "Request has been processed successfully"),
                @ApiResponse(responseCode = "400", description = "Request could not been parsed correctly"),
                @ApiResponse(responseCode = "406", description = "Request did not pass business logic validation ")
        }
    )
    public ResponseEntity<String> processNewRequest(@Valid @RequestBody String rawRequest) {
        logger.debug("Incoming request: {}", rawRequest);
        var res = validationService.runValidation(rawRequest);

        var response = switch (res) {
            case ValidatedSuccessfully req -> {
                var request = req.getRequest();
                logger.debug("Request from customer {} has been validated successfully", request.customerID());
                statsService.saveRequest(request, true);
                logger.debug("Statistics for request {} updated successfully", request);
                requestProcessor.process(request);
                yield new ResponseEntity<>("Request has been processed successfully", HttpStatus.OK);
            }
            case BusinessLogicConstraintsError err -> {
                statsService.saveRequest(err.getRequest(), false);
                yield new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
            case ParsingError err -> new ResponseEntity<>(err.getMessage(), HttpStatus.BAD_REQUEST);  // could not be saved to statistics at all
        };
        return response;
    }
}
