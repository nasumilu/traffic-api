package com.nasumilu.taffic.controller;

import com.nasumilu.taffic.entity.TrafficObservation;
import com.nasumilu.taffic.repository.TrafficObservationRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class TrafficObservationController {

    private final TrafficObservationRepository repository;

    public TrafficObservationController(@Autowired TrafficObservationRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping({"/traffic-counts", "/traffic-counts/{duration}"})
    public TrafficObservationRepository.TrafficObservationSet observations(
            @PathVariable Optional<String> duration,
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) @RequestParam(value="start", required=false) LocalDateTime start,
            @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) @RequestParam(value="end", required=false) LocalDateTime end
    ) {
        var interval = Duration.parse(duration.orElse("pt15m"));
        var startDate = Optional.ofNullable(start).orElse(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        var endDate = Optional.ofNullable(end).orElse(LocalDateTime.of(LocalDate.now().plus(Period.ofDays(1)), LocalTime.MIDNIGHT).minus(Duration.ofMinutes(1)));
        var observations = this.repository.findCountsByInterval(interval.toString(), startDate, endDate);
        return new TrafficObservationRepository.TrafficObservationSet(interval, startDate, endDate, observations);
    }

    @GetMapping("/traffic-observations/{duration}")
    public List<TrafficObservation> observationForDuration(
            @PathVariable(required = true) String duration,
            @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) @RequestParam(value="period", required = true) LocalDateTime period) {
        var interval = Duration.parse(duration);
        return this.repository.findObservationsForPeriodByDuration(interval.toString(), period);
    }


}
