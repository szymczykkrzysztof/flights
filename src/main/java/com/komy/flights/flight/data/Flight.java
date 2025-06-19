package com.komy.flights.flight.data;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "number", length = 6, unique = true)
    private String number;
    @Column(name = "origin", length = 3)
    private String origin;
    @Column(name = "destination", length = 3)
    private String destination;
    @Column(name = "departure")
    private LocalDateTime departure;
    @Column(name = "arrival")
    private LocalDateTime arrival;

    public Flight() {

    }

    public Flight(String number, String origin, String destination, LocalDateTime departure, LocalDateTime arrival) {
        this.number = number;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }
}
