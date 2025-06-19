package com.komy.flights.booking.data;

import com.komy.flights.flight.data.Flight;
import com.komy.flights.passenger.data.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static java.time.LocalDateTime.*;

@DataJpaTest
class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void shouldFindBookingFetchingFlightAndPassenger() {
        var passenger = new Passenger("Wolfgang Amadeus", "Mozart", "einekleine@nachtmusik.at");
        var flight = new Flight("LO1234", "KRK", "VIE", now().plusHours(24), now().plusHours(25));

        var bookingId = bookingRepository
                .save(Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "EUR"))
                .getId();
        var booking = bookingRepository.findById(bookingId);
        Assertions.assertEquals("Wolfgang Amadeus", booking.map(Booking::getPassenger)
                .map(Passenger::getFirstName)
                .orElseThrow());
        Assertions.assertEquals("LO1234",
                booking.map(Booking::getFlight)
                        .map(Flight::getNumber)
                        .orElseThrow());
    }
}