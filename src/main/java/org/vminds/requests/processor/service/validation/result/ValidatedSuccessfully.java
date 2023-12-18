package org.vminds.requests.processor.service.validation.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vminds.requests.processor.dto.CustomerRequest;

@AllArgsConstructor
@Data
public final class ValidatedSuccessfully implements ValidationResult {
    private CustomerRequest request;

    @Override
    public String getMessage() {
        return "Validation passed successfully";
    }
}
