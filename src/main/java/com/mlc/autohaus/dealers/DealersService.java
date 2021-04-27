package com.mlc.autohaus.dealers;

import com.mlc.autohaus.dealers.repository.VehicleWriterRepository;
import com.mlc.autohaus.model.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Service
class DealersService {

    private static final Double ONE_HORSE_POWER_IN_KW = 0.73549875;

    private final VehicleWriterRepository vehicleWriterRepository;

    private final Clock clock;

    DealersService(VehicleWriterRepository vehicleWriterRepository, Clock clock) {
        this.vehicleWriterRepository = vehicleWriterRepository;
        this.clock = clock;
    }

    void createFromCsv(Long dealerId, List<DealersVehicleFromCsv> dealersVehicleFromCsvList) {
        var vehicles = dealersVehicleFromCsvList.stream().map(k -> createVehiclesFrom(dealerId, k)).collect(Collectors.toList());
        vehicleWriterRepository.saveOrUpdate(vehicles);
    }

    void create(Long dealerId, List<DealersVehicleDto> dealersVehicleDtoList) {
        var vehicles = createVehiclesFrom(dealerId, dealersVehicleDtoList);
        vehicleWriterRepository.saveOrUpdate(vehicles);
    }

    List<Vehicle> createVehiclesFrom(Long dealerId, List<DealersVehicleDto> dealersVehicleDtoList) {
        return dealersVehicleDtoList.stream().map(k ->
                Vehicle.builder()
                        .dealerId(dealerId)
                        .code(k.getCode())
                        .color(k.getColor())
                        .kw(k.getEnginePowerInKiloWatts())
                        .make(k.getMake())
                        .model(k.getModel())
                        .price(k.getPrice())
                        .createdAt(clock.instant())
                        .year(k.getYear())
                        .build()).collect(Collectors.toList());
    }

    Vehicle createVehiclesFrom(Long dealerId, DealersVehicleFromCsv dealersVehicleFromCsv) {
        var enginePowerInKw = new BigDecimal(dealersVehicleFromCsv.getEnginePowerInPS())
                .multiply(new BigDecimal(ONE_HORSE_POWER_IN_KW))
                .setScale(0, RoundingMode.DOWN);

        return Vehicle.builder()
                        .dealerId(dealerId)
                        .code(dealersVehicleFromCsv.getCode())
                        .color(dealersVehicleFromCsv.getColor().trim().equals("") ? "Not specified" : dealersVehicleFromCsv.getColor().trim())
                        .kw(enginePowerInKw.intValue())
                        .make(dealersVehicleFromCsv.getMakeModel().getMake())
                        .model(dealersVehicleFromCsv.getMakeModel().getModel())
                        .price(dealersVehicleFromCsv.getPrice())
                        .year(dealersVehicleFromCsv.getYear())
                        .createdAt(clock.instant())
                        .build();
    }
}
