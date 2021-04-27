package com.mlc.autohaus.dealers.repository;

import com.mlc.autohaus.model.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
class VehicleJdbcWriterRepository implements VehicleWriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehicleJdbcWriterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveOrUpdate(List<Vehicle> vehicles) {
        // saving using batch, but TODO: assuming large dataset, but vehicles ids are discarded.
        final var sql = " INSERT INTO Vehicles (dealer_id, code, make, model, kw, year, color, price, created_at) " +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        " ON CONFLICT ON CONSTRAINT UK_dealer_id_code " +
                        " DO UPDATE " +
                        "      SET make = EXCLUDED.make, " +
                        "          model = EXCLUDED.model, " +
                        "          kw = EXCLUDED.kw, " +
                        "          year = EXCLUDED.year, " +
                        "          color = EXCLUDED.color, " +
                        "          price = EXCLUDED.price, " +
                        "          updated_at = EXCLUDED.created_at " ;
        jdbcTemplate.batchUpdate(sql, vehicles, 200, (preparedStatement, vehicle) -> {
            preparedStatement.setLong(1, vehicle.getDealerId());
            preparedStatement.setString(2, vehicle.getCode());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setInt(5, vehicle.getKw());
            preparedStatement.setInt(6, vehicle.getYear());
            preparedStatement.setString(7, vehicle.getColor());
            preparedStatement.setInt(8, vehicle.getPrice());
            preparedStatement.setObject(9, Timestamp.from(vehicle.getCreatedAt()));
        });
    }
}
