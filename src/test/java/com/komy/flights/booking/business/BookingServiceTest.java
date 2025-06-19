package com.komy.flights.booking.business;

import com.komy.flights.booking.data.Booking;
import com.komy.flights.flight.business.management.FlightManagementService;
import com.komy.flights.flight.data.Flight;
import com.komy.flights.passenger.business.PassengerService;
import com.komy.flights.passenger.data.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@SpringBootTest
class BookingServiceTest {
    @Autowired
    private FlightManagementService flightService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private BookingService bookingService;
    private Flight flight;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        var flight = new Flight(randomAlphanumeric(6), randomAlphabetic(3), randomAlphabetic(3), now(), now().plusHours(5));
        var passenger = new Passenger(randomAlphanumeric(10), randomAlphabetic(10), randomAlphanumeric(15));
        this.flight = flightService.save(flight);
        this.passenger = passengerService.save(passenger);
    }

    @Test
    void shouldBookTicket() throws BookingException {
        Booking request = Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "USD");
        Booking result = bookingService.save(request);
        assertNotNull(result);
    }

    @Test
    void shouldNotBookTicketWhenFlightDoesNotExist() {
        Booking request = Booking.newBooking(null, passenger, "17F", BigDecimal.valueOf(99.99), "USD");
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        Assertions.assertEquals("Flight not found", exception.getMessage());

    }

    @Test
    void shouldNotBookTicketWhenPassengerDoesNotExist() {
        Booking request = Booking.newBooking(flight, null, "17F", BigDecimal.valueOf(99.99), "USD");
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        Assertions.assertEquals("Passenger not found", exception.getMessage());
    }

    @Test
    void shouldNotBookTicketWhenSeatIsNotAvailable() throws BookingException {
        bookingService.save(Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "USD"));
        Booking request = Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "USD");
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.save(request));
        Assertions.assertEquals("Seat not available", exception.getMessage());


    }

    @Test
    void shouldChangeSeat() throws BookingException {
        var booking = bookingService.save(Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(99.99), "USD"));
        var result = bookingService.changeSeat(booking.getId(), "18F");
        Assertions.assertEquals("18F", result.getSeat());
    }

    @Test
    void shouldNotChangeSeatWhenNewSeatIsNotAvailable() throws BookingException {
        var bookingForSeat17F = bookingService.save(Booking.newBooking(flight, passenger, "17F", BigDecimal.valueOf(49.99), "USD"));
        var bookingForSeat18F = bookingService.save(Booking.newBooking(flight, passenger, "18F", BigDecimal.valueOf(49.99), "USD"));
        Throwable exception = assertThrows(BookingException.class, () -> bookingService.changeSeat(bookingForSeat18F.getId(), "17F"));
        Assertions.assertEquals("Seat not available", exception.getMessage());
    }
}