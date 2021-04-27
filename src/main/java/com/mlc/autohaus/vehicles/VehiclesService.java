package com.mlc.autohaus.vehicles;

import com.mlc.autohaus.model.Vehicle;
import com.mlc.autohaus.vehicles.repository.VehicleReaderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
class VehiclesService {

    private final VehicleReaderRepository vehicleReaderRepository;

    VehiclesService(VehicleReaderRepository vehicleReaderRepository) {
        this.vehicleReaderRepository = vehicleReaderRepository;
    }

    List<VehicleDto> findVehicles(Map<String, String> vehicleSearch) {
        final var vehicles = vehicleReaderRepository.findAll(vehicleSearch);
        return vehicles.stream().map(this::createFrom).collect(Collectors.toList());
    }

    VehicleDto createFrom(Vehicle vehicle) {
        return VehicleDto.builder()
                .vehicleId(vehicle.getVehicleId())
                .dealerId(vehicle.getDealerId())
                .code(vehicle.getCode())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .enginePowerInKiloWatts(vehicle.getKw())
                .color(vehicle.getColor())
                .year(vehicle.getYear())
                .price(vehicle.getPrice())
                .build();
    }
}
