package com.mlc.autohaus.dealers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DealersController.class, properties = {"spring.flyway.enabled=false"})
public class DealersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealersService dealersService;

    @MockBean
    private DealerCSVReader dealerCSVReader;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenVehiclesListingInputAsJson_whenPost_thenSuccess() throws Exception {
        var dealerId = 1L;
        var vehiclesListingInput =
                ImmutableList.of(VehicleDto.builder()
                        .code("123")
                        .color("red")
                        .make("audi")
                        .model("a3")
                        .enginePowerInKiloWatts(132)
                        .price(13500)
                        .year(2015)
                        .build());

        willDoNothing().given(dealersService).create(dealerId, vehiclesListingInput);

        this.mockMvc
                .perform(post("/dealers/{dealers}/vehicles", dealerId)
                        .content(objectMapper.writeValueAsString(vehiclesListingInput))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenVehiclesListingAsCsvFile_whenUpload_thenSuccess() throws Exception {
        var dealerId = 1L;
        String csvContent = "code,make/model,power-in-ps,year,color,price" +
                "1,mercedes/a 180,123,2014,black,15950";
        var mockedFile = new MockMultipartFile("file",
                "vehicles_listings.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csvContent.getBytes()
        );

        var vehiclesFromCsv =
                VehicleFromCsv.builder()
                        .code("1")
                        .color("black")
                        .makeModel(VehicleFromCsv.MakeModel.builder().make("mercedes").model("a 180").build())
                        .enginePowerInPS(123)
                        .year(2014)
                        .price(15950)
                        .build();

        willReturn(ImmutableList.of(vehiclesFromCsv)).given(dealerCSVReader).readCSV(mockedFile.getResource());
        willDoNothing().given(dealersService).createFromCsv(dealerId, ImmutableList.of(vehiclesFromCsv));

        this.mockMvc
                .perform(multipart("/dealers/{dealers}/vehicles", dealerId)
                        .file(mockedFile)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenWrongJsonInput_whenPost_thenBadRequest() throws Exception {
        var dealerId = 1L;
        this.mockMvc
                .perform(post("/dealers/{dealers}/vehicles", dealerId)
                        .content("wrong json format")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}
