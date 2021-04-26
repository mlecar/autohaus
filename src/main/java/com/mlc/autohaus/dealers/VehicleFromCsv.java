package com.mlc.autohaus.dealers;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleFromCsv {
    @CsvBindByName(column = "code", required = true)
    private String code;

    @CsvCustomBindByName(required = true, converter = TextToMakeAndModel.class, column = "make/model")
    private MakeModel makeModel;

    // TODO: this value in Pferderstärte (PS, or horse power), which is 0,735499 kw
    @CsvBindByName(required = true, column = "power-in-ps")
    private Integer enginePowerInPS;

    @CsvBindByName(column = "year", required = true)
    private Integer year;

    @CsvBindByName(column = "color")
    private String color;

    // TODO: assuming integer instead of double for now
    @CsvBindByName(column = "price", required = true)
    private Integer price;

    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class MakeModel {
        private String make;
        private String model;
    }

    public static class TextToMakeAndModel extends AbstractBeanField<String, MakeModel> {
        @Override
        protected Object convert(String s) {
            String[] split = s.split("/");
            return MakeModel.builder()
                    .make(split[0])
                    .model(split[1])
                    .build();
        }
    }
}
