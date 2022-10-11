package com.nasumilu.taffic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum VehicleClassification {

    PASSENGER_VEHICLE,
    TRUCK_VAN;

}
