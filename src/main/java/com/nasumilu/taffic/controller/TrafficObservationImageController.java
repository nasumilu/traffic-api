package com.nasumilu.taffic.controller;

import com.nasumilu.taffic.repository.TrafficObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class TrafficObservationImageController {

    private final TrafficObservationRepository repository;

    public TrafficObservationImageController(@Autowired TrafficObservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/observation-image/{id}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] observationImage(@PathVariable Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Observation image for id %d not found!", id)))
                .getImage();
    }

}
