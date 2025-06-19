package com.komy.flights.flight.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends CrudRepository<Flight, UUID> {
    @Query("""
            SELECT f from Flight  f
            WHERE f.origin=:origin
            AND f.destination=:destination
            AND DATE(f.departure)=:departure
            """)
    List<Flight> findFlights(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("departure") Date departure);
    @Query("""
            SELECT f from Flight  f
            WHERE f.origin=:origin
            AND DATE(f.departure)=:departure
            """)
    List<Flight> findFlights(@Param("origin") String origin,@Param("departure") Date departure);

    @Query("""
            SELECT f from Flight f
            WHERE f.origin=:origin
            AND f.destination=:destination
            AND f.departure>CURRENT_TIMESTAMP
            """)
    List<Flight> findFlights(@Param("origin") String origin, @Param("destination") String destination);

    Optional<Flight> findFlightByNumber(String flightNumber);
}
