package org.vminds.requests.processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO that defines customer's request format.
 */
public record StatisticsRequest(
        @JsonProperty(required = true) Integer customerID,
        @JsonProperty(required = true) String date) {
}
