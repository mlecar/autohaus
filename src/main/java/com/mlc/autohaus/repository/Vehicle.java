package com.mlc.autohaus.repository;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Value
@Builder
@Table("vehicles")
public class Vehicle {
    @Id
    @With
    Long vehicleId;
    @NonNull Long dealerId;
    @NonNull String code;
    @NonNull String make;
    @NonNull String model;
    @NonNull Integer kw;
    @NonNull Integer year;
    @NonNull String color;
    @NonNull Integer price;
    // TODO: provider should be stored to identify the source
    String provider;
    @NonNull Instant createdAt;
    Instant updatedAt;
}
