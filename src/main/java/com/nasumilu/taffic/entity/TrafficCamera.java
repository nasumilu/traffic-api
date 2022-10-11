package com.nasumilu.taffic.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name="traffic_camera")
public class TrafficCamera implements Serializable {

    @Id
    @Column(name="id", length = 16)
    private String id;

    @Column(name="status", length=16, nullable = false)
    @Enumerated(EnumType.STRING)
    private CameraStatus status;

    public TrafficCamera() {}

    public TrafficCamera(String id, CameraStatus status) {
        this.id = id;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<String> getId() {
        return Optional.ofNullable(this.id);
    }

    public Optional<CameraStatus> getStatus() {
        return Optional.ofNullable(status);
    }

    public void setStatus(CameraStatus status) {
        this.status = status;
    }
}
