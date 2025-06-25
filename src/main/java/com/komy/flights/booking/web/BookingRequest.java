package com.komy.flights.booking.web;

import java.math.BigDecimal;

public record BookingRequest(String flightNumber, String email, String seat, BigDecimal price, String currency) {
}
