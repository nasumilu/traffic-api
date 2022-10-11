package com.nasumilu.taffic.listener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ByteArraySerializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.nasumilu.taffic.entity.VehicleClassification;

import java.util.*;
import java.util.stream.Collectors;


public class PredictionEventBuilder {

    private static ObjectMapper mapper;

    private static ObjectMapper mapper() {
        if (null == mapper) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static MotionEvent fromJson(String json) throws JsonProcessingException {
        return mapper().readValue(json, MotionEvent.class);
    }

    @JsonSerialize
    public record Prediction(
            @JsonProperty VehicleClassification prediction,
            @JsonProperty Integer frame,
            @JsonSerialize(using = ByteArraySerializer.class) @JsonProperty byte[] image,
            @JsonProperty Double confidence
    ) { }

    @JsonSerialize
    public record MotionEvent(
            @JsonProperty("camera_id") String camera,
            @JsonSerialize(using = DateSerializer.class) @JsonProperty("event_datetime") Date timestamp,
            @JsonProperty String event,
            @JsonProperty String file,
            @JsonProperty("frame_number") String frameNumber,
            @JsonProperty Prediction prediction
    ) { }
}