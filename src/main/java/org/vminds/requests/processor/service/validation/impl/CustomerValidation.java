package org.vminds.requests.processor.service.validation.impl;

import lombok.AllArgsConstructor;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.repository.CustomerRepository;
import org.vminds.requests.processor.service.validation.BaseValidator;
import org.vminds.requests.processor.service.validation.result.BusinessLogicConstraintsError;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;
import org.vminds.requests.processor.service.validation.result.ValidationResult;

/**
 * Validate that customer exists and is active.
 */
@AllArgsConstructor
public class CustomerValidation implements BaseValidator<CustomerRequest> {

    private CustomerRepository customerRepository;

    @Override
    public ValidationResult validate(CustomerRequest request) {

        var customer = customerRepository.findById(request.customerID());

        return customer
            .map(c -> {
                if (c.isActive()) return new ValidatedSuccessfully(request);
                else return new BusinessLogicConstraintsError(
                        request,
                        "Customer with ID " + request.customerID() + " is not active." );
            })
            .orElse(new BusinessLogicConstraintsError(request, "Customer with ID " + request.customerID() + " is not registered." ));
    }
}
