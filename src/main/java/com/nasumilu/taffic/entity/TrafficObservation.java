package com.nasumilu.taffic.entity;

import com.nasumilu.taffic.repository.TrafficObservationRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "traffic_observation")
@NamedNativeQueries({
        @NamedNativeQuery(query = "SELECT DATE_BIN(CAST(:duration AS interval), observ_timestamp, :start) series," +
                "    COUNT(*) count FROM traffic_observation WHERE observ_timestamp BETWEEN :start AND :end" +
                " GROUP BY series ORDER BY series",
                name = "TrafficObservation.findCountsByInterval",
                resultSetMapping = "countByInterval"
        )
})

@SqlResultSetMapping(
        name = "countByInterval",
        classes = {
                @ConstructorResult(
                        targetClass = TrafficObservationRepository.TrafficObservationRecord.class,
                        columns = {
                                @ColumnResult(name = "series", type= LocalDateTime.class),
                                @ColumnResult(name = "count", type = Long.class)
                        }
                )
        }
)
public class TrafficObservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(targetEntity = TrafficCamera.class, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "camera", referencedColumnName = "id")
    private TrafficCamera camera;

    @Column(name = "vehicle_class")
    @Enumerated(EnumType.STRING)
    private VehicleClassification vehicleClass;

    @Column(name = "observ_timestamp", nullable = false)
    private LocalDateTime observationDateTime;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "confidence", nullable = false)
    private Double confidence;

    public TrafficObservation() {
    }

    public TrafficObservation(TrafficCamera camera, VehicleClassification vehicleClass, Double confidence, LocalDateTime observationDateTime, byte[] image) {
        this.camera = camera;
        this.observationDateTime = observationDateTime;
        this.vehicleClass = vehicleClass;
        this.confidence = confidence;
        this.image = image;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Optional<LocalDateTime> getObservationDateTime() {
        return Optional.ofNullable(observationDateTime);
    }

    public void setObservationDateTime(LocalDateTime observationDateTime) {
        this.observationDateTime = observationDateTime;
    }

    public Optional<VehicleClassification> getVehicleClass() {
        return Optional.ofNullable(vehicleClass);
    }

    public void setVehicleClass(VehicleClassification vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    public Optional<TrafficCamera> getCamera() {
        return Optional.ofNullable(camera);
    }

    public void setCamera(TrafficCamera camera) {
        this.camera = camera;
    }
}
