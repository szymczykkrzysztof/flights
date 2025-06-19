package com.komy.flights.passenger.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, UUID> {
    Optional<Passenger> findPassengerByEmail(String email);
}
