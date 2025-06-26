package com.komy.flights.flight.web.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = ArrivalAfterDepartureValidator.class)
public @interface ArrivalAfterDeparture {

    String message() default "Arrival should be after departure";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
