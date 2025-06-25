package com.komy.flights.flight.mapping;

import com.komy.flights.flight.business.discovery.FlightDiscoveryQuery;
import com.komy.flights.flight.business.discovery.TicketPriceAwareFlight;
import com.komy.flights.flight.data.Flight;
import com.komy.flights.flight.web.FlightCreationRequest;
import com.komy.flights.flight.web.FlightDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

public interface FlightMapping {

    @Component
    class ApiMapping {

        public Flight map(FlightCreationRequest request) {
            return new Flight(
                    null,
                    request.origin(),
                    request.destination(),
                    request.departure(),
                    request.arrival());
        }

        public FlightDiscoveryQuery map(String origin, String destination, Date departure) {
            if (origin == null) {
                throw new IllegalArgumentException("Origin is required");
            }
            if (destination != null && departure != null) {
                return new FlightDiscoveryQuery
                        .ForOriginAndDestinationAndDepartureDate(origin, destination, departure);
            } else if (departure != null) {
                return new FlightDiscoveryQuery
                        .ForOriginAndDepartureDate(origin, departure);
            } else if (destination != null) {
                return new FlightDiscoveryQuery
                        .ForOriginAndDestination(origin, destination);
            }
            throw new IllegalArgumentException("Either destination or departure date is required");
        }
    }

    @Component
    class ModelMapping {

        public FlightDetails map(Flight flight) {
            return new FlightDetails(
                    flight.getNumber(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    null,
                    flight.getDeparture(),
                    flight.getArrival());
        }

        public FlightDetails map(TicketPriceAwareFlight ticketPriceAwareFlight) {
            var flight = ticketPriceAwareFlight.flight();
            var ticketPrice = ticketPriceAwareFlight.ticketPrice();
            return new FlightDetails(
                    flight.getNumber(),
                    flight.getOrigin(),
                    flight.getDestination(),
                    ticketPrice.toString(),
                    flight.getDeparture(),
                    flight.getArrival());
        }
    }
}
