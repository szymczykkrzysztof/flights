package com.komy.flights.flight.business.discovery;

import com.komy.flights.flight.data.Flight;
import com.komy.flights.flight.data.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FlightDiscoveryService {
    private final FlightRepository flightRepository;
    private final TicketPriceCalculator ticketPriceCalculator;


    public FlightDiscoveryService(FlightRepository flightRepository, TicketPriceCalculator ticketPriceCalculator) {
        this.flightRepository = flightRepository;
        this.ticketPriceCalculator = ticketPriceCalculator;
    }

    public List<TicketPriceAwareFlight> search(FlightDiscoveryQuery request) {

        var matchedFights = switch (request) {
            case FlightDiscoveryQuery.ForOriginAndDestinationAndDepartureDate rq ->
                    flightRepository.findFlights(rq.origin(), rq.destination(), rq.departure());
            case FlightDiscoveryQuery.ForOriginAndDepartureDate rq ->
                    flightRepository.findFlights(rq.origin(), rq.departure());
            case FlightDiscoveryQuery.ForOriginAndDestination rq ->
                    flightRepository.findFlights(rq.origin(), rq.destination());
        };
        return matchedFights.stream()
                .map(flight -> new TicketPriceAwareFlight(flight,ticketPriceCalculator.calculate(flight)))
                .toList();
    }

    public Optional<Flight> getByFlightNumber(String flightNumber) {
        return flightRepository.findFlightByNumber(flightNumber);
    }
}
