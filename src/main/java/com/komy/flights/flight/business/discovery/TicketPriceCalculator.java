package com.komy.flights.flight.business.discovery;

import com.komy.flights.flight.data.Flight;

@FunctionalInterface
public interface TicketPriceCalculator {

    TicketPrice calculate(Flight flight);
}
