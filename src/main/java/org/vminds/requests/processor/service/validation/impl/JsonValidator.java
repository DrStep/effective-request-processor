package org.vminds.requests.processor.service.validation.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.service.validation.BaseValidator;
import org.vminds.requests.processor.service.validation.result.ParsingError;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;
import org.vminds.requests.processor.service.validation.result.ValidationResult;

/**
 * This validation check JSON correctness and map it to {@link CustomerRequest} if possible
 */
public final class JsonValidator implements BaseValidator<String> {

    @Override
    public ValidationResult validate(String input) {
        try {
            var customerRequest = new ObjectMapper().readValue(input, CustomerRequest.class);
            return new ValidatedSuccessfully(customerRequest);
        } catch (JsonEOFException e) {
            return new ParsingError("possible reason is the malformed JSON, error message: " + e.getMessage());
        } catch (MismatchedInputException e) {
            return new ParsingError("possible reason is the absence of required fields: " + e.getMessage());
        } catch (JsonProcessingException e) {
            return new ParsingError("unknown processing exception: " + e.getMessage());
        }
    }
}
