package com.komy.flights.flight;

import com.komy.flights.flight.business.discovery.TicketPrice;
import com.komy.flights.flight.business.discovery.TicketPriceCalculator;
import com.komy.flights.flight.business.management.FlightNumberGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class FlightConfiguration {
    @Bean(name = "fixedPriceCalculator")
    TicketPriceCalculator fixedTicketPriceCalculator() {
        return flight -> new TicketPrice(BigDecimal.valueOf(109.99), "USD");
    }
    @Bean
    FlightNumberGenerator sequentialFlightNumberGenerator() {
        var counter = new AtomicInteger(1000);
        return () -> "SQ" + counter.getAndIncrement();
    }
}
