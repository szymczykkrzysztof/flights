package com.komy.flights.flight.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.komy.flights.flight.web.validation.ArrivalAfterDeparture;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@ArrivalAfterDeparture
public record FlightCreationRequest(
        @JsonProperty("from") @Size(min = 3, max = 3, message = "IATA should be a 3 characters code")
        String origin,
        @JsonProperty("to") @Size(min = 3, max = 3, message = "IATA should be a 3 characters code")
        String destination,
        @JsonProperty("departure") @Future(message = "Departure should be in the future")
        LocalDateTime departure,
        @JsonProperty("arrival") @Future(message = "Arrival should be in the future")
        LocalDateTime arrival) {
}
