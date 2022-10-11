package com.nasumilu.taffic.repository;

import com.nasumilu.taffic.entity.TrafficCamera;
import com.nasumilu.taffic.entity.TrafficObservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public interface TrafficObservationRepository extends CrudRepository<TrafficObservation, Long> {

    public Slice<TrafficObservation> findByCameraOrderByObservationDateTime(TrafficCamera camera, Pageable page);

    @Query(nativeQuery = true)
    public List<TrafficObservationRecord> findCountsByInterval(String duration, LocalDateTime start, LocalDateTime end);

    @Query(value="SELECT id, vehicle_class, camera, observ_timestamp, confidence, encode(lo_get(image), 'base64') image FROM traffic_observation WHERE observ_timestamp BETWEEN :period " +
            "AND CAST(:period AS timestamp) + CAST(:duration AS interval)", nativeQuery = true)
    public List<TrafficObservation> findObservationsForPeriodByDuration(String duration, LocalDateTime period);

    public record TrafficObservationSet(Duration duration, LocalDateTime startDate, LocalDateTime endDate, List<TrafficObservationRecord> observations) {

        public Long sum() {
            return this.observations.stream().collect(Collectors.summingLong(TrafficObservationRecord::count));
        }
    };
    public record TrafficObservationRecord(LocalDateTime series, Long count) { }
}
