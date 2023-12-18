package org.vminds.requests.processor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vminds.requests.processor.dto.CustomerRequest;
import org.vminds.requests.processor.repository.CustomerRepository;
import org.vminds.requests.processor.repository.IpBlacklistRepository;
import org.vminds.requests.processor.repository.StatisticsRepository;
import org.vminds.requests.processor.repository.UaBlacklistRepository;
import org.vminds.requests.processor.service.validation.BaseValidator;
import org.vminds.requests.processor.service.validation.impl.CustomerValidation;
import org.vminds.requests.processor.service.validation.impl.IpValidation;
import org.vminds.requests.processor.service.validation.impl.UaValidation;
import org.vminds.requests.processor.service.validation.result.ValidatedSuccessfully;
import org.vminds.requests.processor.service.validation.result.ValidationResult;
import org.vminds.requests.processor.service.validation.impl.JsonValidator;

import java.util.List;

@Service
public class ValidationRequestService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private IpBlacklistRepository ipBlacklistRepository;

    @Autowired
    private UaBlacklistRepository uaBlacklistRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    private final BaseValidator<String> initialParsingValidator = new JsonValidator();

    public ValidationResult runValidation(String rawRequest) {
        // validate json and get dto object
        var currentRes = initialParsingValidator.validate(rawRequest);
        CustomerRequest req;
        // if validation passed successfully, then get request value, otherwise return error
        if (currentRes instanceof ValidatedSuccessfully) {
            req = ((ValidatedSuccessfully) currentRes).getRequest();
        } else {
            return currentRes;
        }

        var validatorsIter = new LazyValidatorsHolder().logicValidators.iterator();
        // iterate over list of validators till the moment when all validations are made or unsuccessful validation appears
        while (currentRes instanceof ValidatedSuccessfully && validatorsIter.hasNext()) {
            var validator = validatorsIter.next();
            currentRes = validator.validate(req);
        }
        return currentRes;
    }


    // need to store in internal class for lazy initialization due to late autowiring of repositories
    private class LazyValidatorsHolder {

        // all new validators must be added to this list
        private final List<BaseValidator<CustomerRequest>> logicValidators = List.of(
                new CustomerValidation(customerRepository),
                new IpValidation(ipBlacklistRepository),
                new UaValidation(uaBlacklistRepository)
        );
    }
}
