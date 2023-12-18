package org.vminds.requests.processor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * DTO that defines customer's request format.
 */
public record CustomerRequest(
        @JsonProperty(required = true) Integer customerID,
        @JsonProperty(required = true) Long tagID,
        @JsonProperty(required = true) UUID userID,
        @JsonProperty(required = true) String remoteIP,
        @JsonProperty(required = true) Long timestamp) {
}
