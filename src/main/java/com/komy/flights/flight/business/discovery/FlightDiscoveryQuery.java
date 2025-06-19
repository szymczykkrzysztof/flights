package com.komy.flights.flight.business.discovery;

import java.util.Date;

public sealed interface FlightDiscoveryQuery {

    record ForOriginAndDestinationAndDepartureDate(String origin, String destination, Date departure)
            implements FlightDiscoveryQuery {}

    record ForOriginAndDepartureDate(String origin, Date departure)
            implements FlightDiscoveryQuery {}

    record ForOriginAndDestination(String origin, String destination)
            implements FlightDiscoveryQuery {}
}
