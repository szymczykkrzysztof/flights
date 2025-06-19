package com.komy.flights.booking.business;

import com.komy.flights.booking.data.Booking;
import com.komy.flights.booking.data.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking save(Booking booking) throws BookingException {
        if (Objects.isNull(booking.getPassenger())) {
            throw new BookingException("Passenger not found");
        }
        if (Objects.isNull(booking.getFlight())) {
            throw new BookingException("Flight not found");
        }
        if (seatIsAlreadyBookedForFlight(booking.getFlight().getId(), booking.getSeat())) {
            throw new BookingException("Seat not available");
        }
        return bookingRepository.save(booking);
    }

    public Booking get(UUID bookingId) throws BookingException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingException("Booking not found"));
    }

    public Collection<Booking> getByPassengerEmail(String passengerEmail) {
        return bookingRepository.findBookingsByPassengerEmail(passengerEmail);
    }

    public Booking changeSeat(UUID bookingId, String newSeat) throws BookingException {
        var booking = get(bookingId);
        if (seatIsAlreadyBookedForFlight(booking.getFlight().getId(), newSeat)) {
            throw new BookingException("Seat not available");
        }
        booking.changeSeat(newSeat);
        return booking;
    }

    public void cancel(UUID bookingId) throws BookingException {
        var booking = get(bookingId);
        bookingRepository.delete(booking);
    }

    private boolean seatIsAlreadyBookedForFlight(UUID flightId, String seat) {
        return bookingRepository.existsBookingByFlightIdAndSeat(flightId, seat);
    }
}
