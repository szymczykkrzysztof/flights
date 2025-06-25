package com.komy.flights.flight.web;

import com.komy.flights.flight.business.discovery.FlightDiscoveryService;
import com.komy.flights.flight.business.management.FlightManagementService;
import com.komy.flights.flight.mapping.FlightMapping;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/flights")
class FlightController {

    private final FlightDiscoveryService discoveryService;
    private final FlightManagementService managementService;
    private final FlightMapping.ApiMapping apiMapping;
    private final FlightMapping.ModelMapping modelMapping;

    FlightController(
            FlightDiscoveryService discoveryService,
            FlightManagementService managementService,
            FlightMapping.ApiMapping apiMapping,
            FlightMapping.ModelMapping modelMapping) {
        this.discoveryService = discoveryService;
        this.managementService = managementService;
        this.apiMapping = apiMapping;
        this.modelMapping = modelMapping;
    }

    @PostMapping("/management")
    @ResponseStatus(HttpStatus.CREATED)
    FlightDetails createFlight(@RequestBody FlightCreationRequest request) {
        var flight = apiMapping.map(request);
        var savedFlight = managementService.save(flight);
        return modelMapping.map(savedFlight);
    }

    @GetMapping("/discovery")
    @ResponseStatus(HttpStatus.OK)
    List<FlightDetails> discoverFlights(
            @RequestParam("origin") String origin,
            @RequestParam(value = "destination", required = false) String destination,
            @RequestParam(value = "departure", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")
            Date departure) {
        var query = apiMapping.map(origin, destination, departure);
        return discoveryService.search(query)
                .stream()
                .map(modelMapping::map)
                .toList();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<String>> handleValidationErrors(MethodArgumentNotValidException e) {
        var globalErrors = e.getBindingResult().getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage);
        var fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> "[%s] %s".formatted(error.getField(), error.getDefaultMessage()));
        var mergedErrors = Stream.concat(globalErrors, fieldErrors)
                .toList();
        return ResponseEntity.badRequest()
                .body(mergedErrors);
    }

//    @ExceptionHandler(HandlerMethodValidationException.class)
//    ResponseEntity<List<String>> handleValidationErrors(HandlerMethodValidationException e) {
//        var errors = e.getAllValidationResults().stream()
//                .map(error -> "[%s] %s".formatted(
//                        error.getMethodParameter().getParameterName(),
//                        error.getResolvableErrors()
//                                .stream()
//                                .map(MessageSourceResolvable::getDefaultMessage)
//                                .collect(joining(", "))))
//                .toList();
//        return ResponseEntity.badRequest()
//                .body(errors);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                .body(e.getMessage());
    }
}

