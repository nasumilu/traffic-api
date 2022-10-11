package com.nasumilu.taffic.controller;

import com.nasumilu.taffic.entity.CameraStatus;
import com.nasumilu.taffic.entity.TrafficCamera;
import com.nasumilu.taffic.repository.TrafficCameraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class TrafficCameraController {

    private final TrafficCameraRepository repository;

    public TrafficCameraController(@Autowired TrafficCameraRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Optional<TrafficCamera> getCamera(@PathVariable String id) {
        return this.repository.findById(id);
    }

    @GetMapping(value={ "/cameras", "/cameras/{status}"})
    public Page<TrafficCamera> getActiveCameras(@PathVariable Optional<String> status, Pageable page) {
        final var value = new AtomicReference<Page>();
        status.ifPresentOrElse(
                s -> value.set(this.repository.findByStatus(CameraStatus.valueOf(s.toUpperCase()), page)),
                () -> value.set(this.repository.findAll(page)));

        return value.get();
    }

}
