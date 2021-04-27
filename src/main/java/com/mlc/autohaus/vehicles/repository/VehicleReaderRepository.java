package com.mlc.autohaus.vehicles.repository;

import com.mlc.autohaus.model.Vehicle;
import java.util.List;
import java.util.Map;

public interface VehicleReaderRepository {
    List<Vehicle> findAll(Map<String, String> searchCriteria);
}
