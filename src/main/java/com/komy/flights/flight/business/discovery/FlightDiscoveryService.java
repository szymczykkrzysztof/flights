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

    public FlightDiscoveryService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> search(FlightDiscoveryQuery request) {
        return switch (request) {
            case FlightDiscoveryQuery.ForOriginAndDestinationAndDepartureDate rq ->
                    flightRepository.findFlights(rq.origin(), rq.destination(), rq.departure());
            case FlightDiscoveryQuery.ForOriginAndDepartureDate rq ->
                    flightRepository.findFlights(rq.origin(), rq.departure());
            case FlightDiscoveryQuery.ForOriginAndDestination rq ->
                    flightRepository.findFlights(rq.origin(), rq.destination());
        };
    }

    public Optional<Flight> getByFlightNumber(String flightNumber) {
        return flightRepository.findFlightByNumber(flightNumber);
    }
}
