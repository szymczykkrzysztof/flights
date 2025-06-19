package com.komy.flights.passenger.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;

    @Test
    void shouldPersistPassenger() {
        Passenger unsavedPassenger = new Passenger("Elton", "John", "elton@john.uk");
        Passenger savedPassenger = passengerRepository.save(unsavedPassenger);
        assertNotNull(savedPassenger);
    }

    @Test
    void shouldFindPassengerById() {
        Passenger passenger = new Passenger("Michael", "Jackson", "mj@usa.com");
        passengerRepository.save(passenger);
        Optional<Passenger> maybePassenger = passengerRepository.findById(passenger.getId());
        assertTrue(maybePassenger.isPresent());
        assertEquals("Michael", maybePassenger.get().getFirstName());
        assertEquals("Jackson", maybePassenger.get().getLastName());
        assertEquals("mj@usa.com", maybePassenger.get().getEmail());

    }

    @Test
    void shouldFindPassengerByEmail() {
        Passenger passenger = new Passenger("Bryan", "Adams", "bryan@adams.ca");
        passengerRepository.save(passenger);
        Optional<Passenger> maybePassenger = passengerRepository.findPassengerByEmail(passenger.getEmail());
        assertTrue(maybePassenger.isPresent());
        assertEquals("Bryan", maybePassenger.get().getFirstName());
        assertEquals("Adams", maybePassenger.get().getLastName());
        assertEquals("bryan@adams.ca", maybePassenger.get().getEmail());
    }

    @Test
    void shouldNotFindPassengerByUnknownEmail() {
        Optional<Passenger> maybePassenger = passengerRepository.findPassengerByEmail("unknown@email.com");
        assertFalse(maybePassenger.isPresent());
    }

}