package org.vminds.requests.processor.service.validation;


import org.vminds.requests.processor.service.validation.result.ValidationResult;

/**
 * Base interface for request validation logic.
 * To add more validations extend this interface and place implementation in
 * {@link org.vminds.requests.processor.service.validation.impl} package.
 */
public interface BaseValidator<T> {
    ValidationResult validate(T request);

}
