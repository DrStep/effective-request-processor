package org.vminds.requests.processor.service.validation.impl;

import lombok.AllArgsConstructor;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.repository.IpBlacklistRepository;
import org.vminds.requests.processor.service.validation.BaseValidator;
import org.vminds.requests.processor.service.validation.result.BusinessLogicConstraintsError;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;
import org.vminds.requests.processor.service.validation.result.ValidationResult;

/**
 * Validate that IP of the customer is not in the blacklist.
 */
@AllArgsConstructor
public final class IpValidation implements BaseValidator<CustomerRequest> {

    private IpBlacklistRepository ipBlacklistRepository;

    @Override
    public ValidationResult validate(CustomerRequest request) {

        if (ipBlacklistRepository.existsById(request.remoteIP())) {
            return new BusinessLogicConstraintsError(
                    request,
                    "Customer with IP " + request.remoteIP() + " is in the blacklist.");
        } else {
            return new ValidatedSuccessfully(request);
        }
    }
}
