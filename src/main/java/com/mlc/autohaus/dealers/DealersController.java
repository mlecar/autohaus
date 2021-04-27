package com.mlc.autohaus.dealers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping(produces = "application/json")
public class DealersController {

    private final DealersService dealersService;
    private final DealersCSVReader dealersCSVReader;

    public DealersController(DealersService dealersService, DealersCSVReader dealersCSVReader){
        this.dealersService = dealersService;
        this.dealersCSVReader = dealersCSVReader;
    }

    @PostMapping(value = "/dealers/{dealer_id}/vehicles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addVehicles(@PathVariable("dealer_id") Long dealerId, @RequestParam("file") MultipartFile multipartFile) {
        // TODO: provider is important to keep track of changes, but not storing at the moment
        final var vehicleDtoList = dealersCSVReader.readCSV(multipartFile.getResource());
        dealersService.createFromCsv(dealerId, vehicleDtoList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(value = "/dealers/{dealer_id}/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addVehicles(@PathVariable("dealer_id") Long dealerId, @RequestBody @Valid List<DealersVehicleDto> dealersVehicleDtoList) {
        // TODO: provider is important to keep track of changes, but not storing at the moment
        dealersService.create(dealerId, dealersVehicleDtoList);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
