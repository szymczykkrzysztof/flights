package com.komy.flights.flight.web;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;


public record FlightCreationRequest(
        @JsonProperty("from")
        String origin,
        @JsonProperty("to")
        String destination,
        @JsonProperty("departure")
        LocalDateTime departure,
        @JsonProperty("arrival")
        LocalDateTime arrival) {
}
