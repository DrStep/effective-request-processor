package org.vminds.requests.processor.service.validation.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.vminds.requests.processor.dto.CustomerRequest;

@AllArgsConstructor
@Data
public final class BusinessLogicConstraintsError implements ValidationResult {
    private CustomerRequest request;
    private String reason;

    @Override
    public String getMessage() {
        return "Request met specific constraints and has not been executed: " + reason;
    }
}
