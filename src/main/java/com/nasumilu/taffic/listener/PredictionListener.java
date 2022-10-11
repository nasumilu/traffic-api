package com.nasumilu.taffic.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nasumilu.taffic.entity.TrafficObservation;
import com.nasumilu.taffic.entity.VehicleClassification;
import com.nasumilu.taffic.repository.TrafficCameraRepository;
import com.nasumilu.taffic.repository.TrafficObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;


@Component
@KafkaListener(id = "spark", topics = "vehicle_prediction")
public class PredictionListener {

    private final TrafficObservationRepository observationRepository;

    private final TrafficCameraRepository cameraRepository;

    public PredictionListener(@Autowired TrafficObservationRepository observationRepository,
                              @Autowired TrafficCameraRepository cameraRepository) {
        this.observationRepository = observationRepository;
        this.cameraRepository = cameraRepository;
    }

    @KafkaHandler
    public void listen(String in) throws JsonProcessingException {
        var evt = PredictionEventBuilder.fromJson(in);
        System.out.println(evt);
        this.saveObservation(evt);
    }

    private void saveObservation(PredictionEventBuilder.MotionEvent evt) {
        var camera = this.cameraRepository.findById(evt.camera());
        camera.ifPresent(c -> {
            var observation = new TrafficObservation(
                    c,
                    evt.prediction().prediction(),
                    evt.prediction().confidence(),
                    new Timestamp(evt.timestamp().getTime()).toLocalDateTime(),
                    evt.prediction().image());
            System.out.println(observation);
            this.observationRepository.save(observation);
        });
    }

}
