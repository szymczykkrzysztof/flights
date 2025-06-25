package com.komy.flights.booking.web;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookingDetails(UUID bookingId, String flightNumber, String seat, String firstName, String lastName, String origin,
                             String destination, LocalDateTime departure, LocalDateTime arrival) {
}
