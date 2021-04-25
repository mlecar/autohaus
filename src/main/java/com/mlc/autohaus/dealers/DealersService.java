package com.mlc.autohaus.dealers;

import com.mlc.autohaus.repository.VehicleRepository;
import com.mlc.autohaus.repository.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealersService {

    private static final Double ONE_HORSE_POWER_IN_KW = 0.73549875;

    private final VehicleRepository vehicleRepository;

    private final Clock clock;

    public DealersService(VehicleRepository vehicleRepository, Clock clock) {
        this.vehicleRepository = vehicleRepository;
        this.clock = clock;
    }

    void createFromCsv(Long dealerId, List<VehicleFromCsv> vehicleFromCsvList) {
        var vehicles = vehicleFromCsvList.stream().map(k -> createVehiclesFrom(dealerId, k)).collect(Collectors.toList());
        vehicleRepository.saveOrUpdate(vehicles);
    }

    void create(Long dealerId, List<VehicleDto> vehicleDtoList) {
        var vehicles = createVehiclesFrom(dealerId, vehicleDtoList);
        vehicleRepository.saveOrUpdate(vehicles);
    }

    List<Vehicle> createVehiclesFrom(Long dealerId, List<VehicleDto> vehicleDtoList) {
        return vehicleDtoList.stream().map(k ->
                Vehicle.builder()
                        .dealerId(dealerId)
                        .code(k.getCode())
                        .color(k.getColor())
                        .kw(k.getEnginePowerInKiloWatts())
                        .make(k.getMake())
                        .model(k.getModel())
                        .price(k.getPrice())
                        .createdAt(clock.instant())
                        .build()).collect(Collectors.toList());
    }

    Vehicle createVehiclesFrom(Long dealerId, VehicleFromCsv vehicleFromCsv) {
        var enginePowerInKw = new BigDecimal(vehicleFromCsv.getEnginePowerInPS())
                .multiply(new BigDecimal(ONE_HORSE_POWER_IN_KW))
                .setScale(0, RoundingMode.DOWN);

        var makeAndModel = vehicleFromCsv.getMakeAndModel().split("/");
        return Vehicle.builder()
                        .dealerId(dealerId)
                        .code(vehicleFromCsv.getCode())
                        .color(vehicleFromCsv.getColor())
                        .kw(enginePowerInKw.intValue())
                        .make(makeAndModel[0])
                        .model(makeAndModel[1])
                        .price(vehicleFromCsv.getPrice())
                        .createdAt(clock.instant())
                        .build();
    }
}
