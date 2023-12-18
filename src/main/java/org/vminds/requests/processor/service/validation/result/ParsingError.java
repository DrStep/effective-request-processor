package org.vminds.requests.processor.service.validation.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public final class ParsingError implements ValidationResult {
    private String reason;

    @Override
    public String getMessage() {
        return "Request could not be parsed: " + reason;
    }
}
