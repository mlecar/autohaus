package com.mlc.autohaus.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.autohaus.configuration.JacksonConfiguration;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(controllers = VehiclesController.class, properties = {"spring.flyway.enabled=false"})
@Import(JacksonConfiguration.class)
public class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiclesService vehiclesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Map<String, String>> params;

    @Test
    public void givenNoParams_whenFind_thenNoSearchCriteria() throws Exception {
        // given
        var vehicleDtoList =
                ImmutableList.of(VehicleDto
                        .builder()
                        .vehicleId(1L)
                        .enginePowerInKiloWatts(142)
                        .model("a3")
                        .build());

        willReturn(vehicleDtoList).given(vehiclesService).findVehicles(params.capture());

        //when and then
        this.mockMvc
                .perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].vehicle_id", is(1)))
                .andExpect(jsonPath("$.[0].kw", is(142)))
                .andExpect(jsonPath("$.[0].model", is("a3")));

        assertThat(params.getValue()).isEmpty();
    }

    @Test
    public void givenNoParams_whenFind_thenJsonIsReturned() throws Exception {
        // given
        var vehicleDtoList =
                ImmutableList.of(VehicleDto
                        .builder()
                        .vehicleId(1L)
                        .enginePowerInKiloWatts(142)
                        .model("a3")
                        .build());

        willReturn(vehicleDtoList).given(vehiclesService).findVehicles(params.capture());

        //when and then
        this.mockMvc
                .perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].vehicle_id", is(1)))
                .andExpect(jsonPath("$.[0].kw", is(142)))
                .andExpect(jsonPath("$.[0].model", is("a3")));
    }

    @Test
    public void givenMake_whenFind_thenSearchCriteriaIsMake() throws Exception {
        // given
        var vehicleDtoList =
                ImmutableList.of(VehicleDto
                        .builder()
                        .vehicleId(1L)
                        .enginePowerInKiloWatts(142)
                        .model("a3")
                        .build());

        willReturn(vehicleDtoList).given(vehiclesService).findVehicles(params.capture());

        //when and then
        this.mockMvc
                .perform(get("/vehicles").param("make","audi"))
                .andExpect(status().isOk());

        assertThat(params.getValue()).containsEntry("make", "audi");
    }

    @Test
    public void givenPost_whenGet_thenMethodNotAllowed() throws Exception {
        //given, when and then
        this.mockMvc
                .perform(post("/vehicles"))
                .andExpect(status().isMethodNotAllowed());
    }
}
