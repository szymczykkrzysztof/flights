package com.komy.flights.flight.business.discovery;

import com.komy.flights.flight.data.Flight;

public record TicketPriceAwareFlight(Flight flight, TicketPrice ticketPrice) {
}
