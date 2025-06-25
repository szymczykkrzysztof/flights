package com.komy.flights.booking.web;

import com.komy.flights.booking.business.BookingException;
import com.komy.flights.booking.business.BookingService;
import com.komy.flights.booking.mapping.BookingMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/bookings")
class BookingController {
    private final BookingService bookingService;
    private final BookingMapping.ApiMapping apiMapping;
    private final BookingMapping.ModelMapping modelMapping;

    @Autowired
    BookingController(BookingService bookingService, BookingMapping.ApiMapping apiMapping, BookingMapping.ModelMapping modelMapping) {
        this.bookingService = bookingService;
        this.apiMapping = apiMapping;
        this.modelMapping = modelMapping;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookingDetails createBooking(@RequestBody BookingRequest request) throws BookingException {
        var booking = apiMapping.map(request);
        var savedBooking = bookingService.save(booking);
        return modelMapping.map(savedBooking);
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    BookingDetails getBooking(@PathVariable("id") UUID bookingId) throws BookingException {
        var booking = bookingService.get(bookingId);
        return modelMapping.map(booking);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Collection<BookingDetails> getBookingsForPassenger(@RequestParam("email") String passengerEmail) {
        var matchedBookings = bookingService.getByPassengerEmail(passengerEmail);
        return matchedBookings.stream()
                .map(modelMapping::map)
                .toList();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    BookingDetails updateBooking(@PathVariable("id") UUID bookingId, @RequestBody String newSeat) throws BookingException {
        var booking = bookingService.changeSeat(bookingId, newSeat);
        return modelMapping.map(booking);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void cancelBooking(@PathVariable("id") UUID bookingId) throws BookingException {
        bookingService.cancel(bookingId);
    }

    @ExceptionHandler(BookingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleBookingException(BookingException e) {
        return e.getMessage();
    }
}
