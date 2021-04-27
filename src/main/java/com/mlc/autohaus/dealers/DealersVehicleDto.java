package com.mlc.autohaus.dealers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter(AccessLevel.NONE)
public class DealersVehicleDto {
    private String code;
    private String make;
    private String model;
    @JsonProperty("kw")
    private Integer enginePowerInKiloWatts;
    private Integer year;
    private String color;

    // TODO: assuming integer instead of double for now
    private Integer price;
}
