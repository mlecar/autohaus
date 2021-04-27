package com.mlc.autohaus.vehicles;

import com.mlc.autohaus.vehicles.repository.VehicleReaderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(produces = "application/json")
public class VehiclesController {

    private final VehiclesService vehiclesService;

    public VehiclesController(VehiclesService vehiclesService){
        this.vehiclesService = vehiclesService;
    }

    @GetMapping(value = "/vehicles")
    public ResponseEntity<?> findVehicles(@RequestParam MultiValueMap<String, String> params) {
        // TODO: prepare to search multiple values per parameter
        final var vehiclesDto = vehiclesService.findVehicles(params.toSingleValueMap());
        return ResponseEntity.ok(vehiclesDto);
    }
}
