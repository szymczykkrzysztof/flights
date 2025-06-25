package com.komy.flights.flight.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FlightDetails(
        @JsonProperty("flightNumber") String number,
        @JsonProperty("from") String origin,
        @JsonProperty("to") String destination,
        @JsonProperty("ticketPrice") String price,
        @JsonProperty("departure")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime departure,
        @JsonProperty("arrival")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime arrival) {
}
