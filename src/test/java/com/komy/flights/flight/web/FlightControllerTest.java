package com.komy.flights.flight.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateFlight() {
        var request = new FlightCreationRequest("WAW", "DXB", now().plusHours(1), now().plusHours(9));
        var response = restTemplate.postForEntity("http://localhost:%d/flights/management".formatted(port), request, FlightDetails.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("WAW", response.getBody().origin());
        Assertions.assertEquals("DXB", response.getBody().destination());
        Assertions.assertNull(response.getBody().price());
    }

    @Test
    void shouldDiscoverFlights() {
        var request = new FlightCreationRequest("WAW", "DXB", now().plusHours(1), now().plusHours(9));
        restTemplate.postForEntity("http://localhost:%d/flights/management".formatted(port), request, FlightDetails.class);

        var response = restTemplate.getForEntity("http://localhost:%d/flights/discovery?origin=WAW&destination=DXB".formatted(port), FlightDetails[].class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        List<FlightDetails> flightDetails = List.of(response.getBody());
        Assertions.assertEquals(1, flightDetails.size());
        Assertions.assertEquals("WAW", flightDetails.getFirst().origin());
        Assertions.assertEquals("DXB", flightDetails.getFirst().destination());
        Assertions.assertEquals("109,99 USD", flightDetails.getFirst().price());
    }

    @Test
    void shouldNotCreateFlightWhenAirportIsInvalid() {
        // given
        var request = new FlightCreationRequest("WAW", "HR", now().plusHours(1), now().plusHours(3));
        // when
        var response = restTemplate
                .postForEntity("http://localhost:%d/flights/management".formatted(port), request, List.class);
        // then
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(List.of("[destination] IATA should be a 3 characters code"), response.getBody());
    }

    @Test
    void shouldNotCreateFlightWhenDepartureIsInThePast() {
        // given
        var request = new FlightCreationRequest("WAW", "LHR", now().minusHours(1), now().plusHours(1));
        // when
        var response = restTemplate
                .postForEntity("http://localhost:%d/flights/management".formatted(port), request, List.class);
        // then
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(List.of("[departure] Departure should be in the future"), response.getBody());
    }

    @Test
    void shouldNotCreateFlightWhenArrivalIsNotAfterDeparture() {
        // given
        var request = new FlightCreationRequest("WAW", "LHR", now().plusHours(2), now().plusHours(1));
        // when
        var response = restTemplate
                .postForEntity("http://localhost:%d/flights/management".formatted(port), request, List.class);
        // then
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals(List.of("Arrival should be after departure"), response.getBody());
    }
}