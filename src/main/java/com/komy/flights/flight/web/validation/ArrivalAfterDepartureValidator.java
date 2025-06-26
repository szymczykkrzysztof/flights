package com.komy.flights.flight.web.validation;

import com.komy.flights.flight.web.FlightCreationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class ArrivalAfterDepartureValidator implements ConstraintValidator<ArrivalAfterDeparture, FlightCreationRequest> {

    @Override
    public boolean isValid(FlightCreationRequest value, ConstraintValidatorContext context) {
        var departure = value.departure();
        var arrival = value.arrival();
        if (departure == null) {
            context.buildConstraintViolationWithTemplate("Departure date is required")
                    .addPropertyNode("departure")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        if (arrival == null) {
            context.buildConstraintViolationWithTemplate("Arrival date is required")
                    .addPropertyNode("arrival")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return arrival.isAfter(departure);
    }
}