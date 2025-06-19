package com.komy.flights.passenger.business;

import com.komy.flights.passenger.data.Passenger;
import com.komy.flights.passenger.data.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PassengerService {
    private final PassengerRepository passengerRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Optional<Passenger> getByEmail(String email) {
        return passengerRepository.findPassengerByEmail(email);
    }

    public Passenger save(Passenger passenger) {
        return passengerRepository.save(passenger);
    }
}
