package com.mlc.autohaus.repository;

import java.util.List;

public interface VehicleRepository {
    void saveOrUpdate(List<Vehicle> vehicles);
}
