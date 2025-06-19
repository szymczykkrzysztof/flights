package com.komy.flights.booking.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface BookingRepository extends CrudRepository<Booking, UUID> {
    @Query("""
            SELECT b FROM Booking b
            WHERE b.passenger.email=:email
            ORDER BY b.flight.departure
            """)
    Collection<Booking> findBookingsByPassengerEmail(@Param("email") String email);
    @Query ("""
                SELECT (COUNT(b)>0) FROM Booking b
                WHERE b.flight.id =:flight
                AND b.seat=:seat
            """)
    boolean existsBookingByFlightIdAndSeat(
            @Param("flight") UUID flightId,
            @Param("seat") String seat);
}
