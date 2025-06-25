package com.komy.flights.flight.business.management;

import com.komy.flights.flight.data.Flight;
import com.komy.flights.flight.data.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlightManagementService {
    private final FlightRepository flightRepository;
    private final FlightNumberGenerator flightNumberGenerator;

    public FlightManagementService(FlightRepository flightRepository, FlightNumberGenerator flightNumberGenerator) {
        this.flightRepository = flightRepository;
        this.flightNumberGenerator = flightNumberGenerator;
    }

    public Flight save(Flight flight) {
        if (flight.doesNotHaveNumber()) {
            var flightNumber = flightNumberGenerator.generate();
            flight.assignNumber(flightNumber);
        }
        return flightRepository.save(flight);
    }
}
