package com.mlc.autohaus.dealers;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
class DealersCSVReader {

    List<DealersVehicleFromCsv> readCSV(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            // TODO: comma separated value, but some regions use semicolon as delimiter
            // TODO: how big are the files from dealers? If huge, it should be handled in a proper way
            return new CsvToBeanBuilder<DealersVehicleFromCsv>(new InputStreamReader(inputStream))
                    .withType(DealersVehicleFromCsv.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ioException) {
            throw new IllegalStateException("File [" + resource.getFilename() + "] could not be read.");
        }
    }
}
