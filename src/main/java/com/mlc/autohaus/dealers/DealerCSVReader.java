package com.mlc.autohaus.dealers;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
class DealerCSVReader {

    List<VehicleFromCsv> readCSV(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            // TODO: comma separated value, but some regions use semicolon as delimiter
            // TODO: how big are the files from dealers? If huge, an iterator can be used
            return new CsvToBeanBuilder<VehicleFromCsv>(new InputStreamReader(inputStream)).build().stream().collect(Collectors.toList());
            //return dealerCsvIterator.map(this::mapFrom).collect(Collectors.toList());
        } catch (IOException ioException) {
            throw new IllegalStateException("File [" + resource.getFilename() + "] could not be read.");
        }
    }
}
