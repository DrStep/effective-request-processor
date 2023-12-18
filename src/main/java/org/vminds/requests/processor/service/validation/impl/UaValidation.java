package org.vminds.requests.processor.service.validation.impl;

import lombok.AllArgsConstructor;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.repository.IpBlacklistRepository;
import org.vminds.requests.processor.repository.UaBlacklistRepository;
import org.vminds.requests.processor.service.validation.BaseValidator;
import org.vminds.requests.processor.service.validation.result.BusinessLogicConstraintsError;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;
import org.vminds.requests.processor.service.validation.result.ValidationResult;

/**
 * Validate that user agent (?) is not in the blacklist.
 */
@AllArgsConstructor
public final class UaValidation implements BaseValidator<CustomerRequest> {

    private UaBlacklistRepository uaBlacklistRepository;

    @Override
    public ValidationResult validate(CustomerRequest request) {

        if (uaBlacklistRepository.existsById(request.userID().toString())) {
            return new BusinessLogicConstraintsError(
                    request,
                    "Customer with specified userId " + request.userID() + " is in the blacklist.");
        } else {
            return new ValidatedSuccessfully(request);
        }
    }
}
