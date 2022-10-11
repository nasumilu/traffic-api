package com.nasumilu.taffic.repository;

import com.nasumilu.taffic.entity.CameraStatus;
import com.nasumilu.taffic.entity.TrafficCamera;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

public interface TrafficCameraRepository extends PagingAndSortingRepository<TrafficCamera, String> {

    public Page<TrafficCamera> findByStatus(CameraStatus status, Pageable page);

}
