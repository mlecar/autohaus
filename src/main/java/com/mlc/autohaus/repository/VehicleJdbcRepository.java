package com.mlc.autohaus.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
class VehicleJdbcRepository implements VehicleRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehicleJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveOrUpdate(List<Vehicle> vehicles) {
        // saving in
        final var sql = " INSERT INTO Vehicles (dealer_id, code, make, model, kw, year, color, price, created_at) " +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                        " ON CONFLICT ON CONSTRAINT UK_dealer_id_code " +
                        " DO uPDATE " +
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
