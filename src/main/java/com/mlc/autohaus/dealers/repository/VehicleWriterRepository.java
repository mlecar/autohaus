package com.mlc.autohaus.dealers.repository;

import com.mlc.autohaus.model.Vehicle;

import java.util.List;

public interface VehicleWriterRepository {
    void saveOrUpdate(List<Vehicle> vehicles);
}
