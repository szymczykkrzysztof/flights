package com.komy.flights.flight.business.management;

import com.komy.flights.flight.data.Flight;
import com.komy.flights.flight.data.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlightManagementService {
    private final FlightRepository flightRepository;

    public FlightManagementService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight save(Flight flight) {
        return flightRepository.save(flight);
    }
}
