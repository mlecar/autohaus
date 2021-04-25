package com.mlc.autohaus.dealers;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VehicleFromCsv {
    @CsvBindByName(required = true)
    private String code;
    @CsvBindByName(required = true)
    private String makeAndModel;

    // TODO: this value in Pferderstärte (PS, or horse power), which is 0,735499 kw
    @CsvBindByName(required = true, column = "power-in-ps")
    private Integer enginePowerInPS;

    @CsvBindByName(required = true)
    private Integer year;

    @CsvBindByName(required = true)
    private String color;

    // TODO: assuming integer instead of double for now
    @CsvBindByName(required = true)
    private Integer price;
}
