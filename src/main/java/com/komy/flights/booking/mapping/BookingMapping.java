package com.komy.flights.booking.mapping;

import com.komy.flights.booking.data.Booking;
import com.komy.flights.booking.web.BookingDetails;
import com.komy.flights.booking.web.BookingRequest;
import com.komy.flights.flight.business.discovery.FlightDiscoveryService;
import com.komy.flights.passenger.business.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public interface BookingMapping {

    @Component
    class ApiMapping {

        private final FlightDiscoveryService flightDiscoveryService;
        private final PassengerService passengerService;

        @Autowired
        ApiMapping(
                FlightDiscoveryService flightDiscoveryService,
                PassengerService passengerService) {
            this.flightDiscoveryService = flightDiscoveryService;
            this.passengerService = passengerService;
        }

        public Booking map(BookingRequest request) {
            var maybeFlight = flightDiscoveryService.getByFlightNumber(request.flightNumber());
            var maybePassenger = passengerService.getByEmail(request.email());
            return Booking.newBooking(
                    maybeFlight.orElse(null),
                    maybePassenger.orElse(null),
                    request.seat(),
                    request.price(),
                    request.currency());
        }
    }

    @Component
    class ModelMapping {

        public BookingDetails map(Booking booking) {
            return new BookingDetails(
                    booking.getId(),
                    booking.getFlight().getNumber(),
                    booking.getSeat(),
                    booking.getPassenger().getFirstName(),
                    booking.getPassenger().getLastName(),
                    booking.getFlight().getOrigin(),
                    booking.getFlight().getDestination(),
                    booking.getFlight().getDeparture(),
                    booking.getFlight().getArrival());
        }
    }
}
