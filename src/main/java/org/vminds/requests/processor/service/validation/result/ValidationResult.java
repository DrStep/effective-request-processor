package org.vminds.requests.processor.service.validation.result;

import lombok.Data;
import org.vminds.requests.processor.dto.CustomerRequest;

/**
 * This file contains all types of validation result.
 * Basically validation can finish either with success or failure.
 * Failures are generalized into two groups (for now):
 * - parsing errors: when the service couldn't parse request correctly
 * - business logic errors: when request is correct, but other constraints are met, so it couldn't be processed
 */
public sealed interface ValidationResult permits ValidatedSuccessfully, BusinessLogicConstraintsError, ParsingError {
    String getMessage();
}

