package com.komy.flights.booking.data;

import com.komy.flights.flight.data.Flight;
import com.komy.flights.passenger.data.Passenger;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "flight", referencedColumnName = "id")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name = "passenger", referencedColumnName = "id")
    private Passenger passenger;
    @Column(name = "seat")
    private String seat;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency")
    private String currency;
    @Column(name = "created")
    private LocalDateTime created;

    public UUID getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String getSeat() {
        return seat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Booking() {
    }

    public Booking(Flight flight, Passenger passenger, String seat, BigDecimal price, String currency, LocalDateTime created) {
        this.flight = flight;
        this.passenger = passenger;
        this.seat = seat;
        this.price = price;
        this.currency = currency;
        this.created = created;
    }

    public static Booking newBooking(Flight flight, Passenger passenger, String seat, BigDecimal price, String currency) {
        return new Booking(flight, passenger, seat, price, currency, LocalDateTime.now());
    }

    public void changeSeat(String newSeat) {
        this.seat = newSeat;
    }
}
