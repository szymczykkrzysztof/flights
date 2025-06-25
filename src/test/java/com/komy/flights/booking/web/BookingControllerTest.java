package com.komy.flights.booking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.komy.flights.booking.business.BookingException;
import com.komy.flights.booking.business.BookingService;
import com.komy.flights.booking.data.Booking;
import com.komy.flights.flight.business.management.FlightManagementService;
import com.komy.flights.flight.data.Flight;
import com.komy.flights.passenger.business.PassengerService;
import com.komy.flights.passenger.data.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.time.LocalDateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private FlightManagementService flightService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BookingService bookingService;

    @Test
    void shouldCreateBooking() throws Exception {
        passengerService.save(new Passenger("Ed", "Sheeran", "ed@perfect.com"));
        flightService.save(new Flight("LO1234", "WAW", "LHR", now().plusHours(1), now().plusHours(3)));

        String bookingRequestJson = """
                {
                "flightNumber":"LO1234",
                "email":"ed@perfect.com",
                "seat":"17C",
                "price":"99.99",
                "currency":"GBP"
                }
                """;
        var result = mockMvc.perform(post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookingRequestJson));
        result.andExpect(status().isCreated());
        String jsonResponse = result.andReturn().getResponse().getContentAsString();
        BookingDetails createdBooking = objectMapper.readValue(jsonResponse, BookingDetails.class);
        Assertions.assertNotNull(createdBooking);
        Assertions.assertNotNull(createdBooking.bookingId());
        Assertions.assertEquals("Sheeran", createdBooking.lastName());
        Assertions.assertEquals("LO1234", createdBooking.flightNumber());


    }

    @Test
    void shouldDeleteBooking() throws Exception {
        var passenger = passengerService.save(new Passenger("Antonio", "Vivaldi", "antonio@classic.at"));
        var flight = flightService.save(new Flight("IT1234", "VIE", "VCE", now().plusHours(24), now().plusHours(25)));
        var booking = bookingService.save(Booking.newBooking(flight, passenger, "17C", BigDecimal.valueOf(119.99), "EUR"));

        var result = mockMvc.perform(delete("/bookings/%s".formatted(booking.getId())));
        result.andExpect(status().isNoContent());
        var exception = Assertions.assertThrows(BookingException.class, () -> bookingService.get(booking.getId()));
        Assertions.assertEquals("Booking not found", exception.getMessage());
    }
}