package com.mlc.autohaus.vehicles;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlc.autohaus.model.RunningPostgresDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehiclesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @TestConfiguration
    static class RepositoryTestConfiguration {
        @Bean
        public DataSource dataSource() {
            return RunningPostgresDatabase.getDataSource();
        }
    }

    @BeforeEach
    public void setuUp(){
        final var sql = " INSERT INTO Vehicles (dealer_id, code, make, model, kw, year, color, price, created_at) " +
                        " VALUES (1, 123, 'audi', 'a3', 167, 2015, 'gren', 14550, NOW()::timestamp)," +
                        "        (2, 526, 'bmw', 'x6', 189, 2018, 'red', 20500, NOW()::timestamp) " ;
        jdbcTemplate.execute(sql);
    }

    @AfterEach
    public void tearDown(){
        jdbcTemplate.execute("DELETE FROM Vehicles");
    }

    @Test
    public void givenNoParam_whenFindVehicles_shouldReturnFullList() throws Exception {
        // given, when,
        this.mockMvc
                .perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].code", is("123")))
                .andExpect(jsonPath("$.[1].code", is("526")));
    }

    @Test
    public void givenModel_whenFindVehicles_shouldReturnSearchedModelOnly() throws Exception {
        // given, when,
        this.mockMvc
                .perform(get("/vehicles").param("model", "x6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].code", is("526")));
    }
}
