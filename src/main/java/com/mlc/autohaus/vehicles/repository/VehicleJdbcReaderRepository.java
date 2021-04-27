package com.mlc.autohaus.vehicles.repository;

import com.mlc.autohaus.model.Vehicle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
class VehicleJdbcReaderRepository implements VehicleReaderRepository {

    private final JdbcTemplate jdbcTemplate;

    public VehicleJdbcReaderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Vehicle> findAll(Map<String, String>searchCriteria) {
        var sql = " SELECT vehicle_id, dealer_id, code, make, model, kw, year, color, price, created_at " +
                  "   FROM Vehicles ";
        if ( !searchCriteria.isEmpty() ){
            sql += buildSqlFromSearchCriteria(searchCriteria);
        }
        var vehicles = new ArrayList<Vehicle>();
        jdbcTemplate.query(sql, (res, i) ->
                vehicles.add(Vehicle.builder()
                        .vehicleId(res.getLong("vehicle_id"))
                        .dealerId(res.getLong("dealer_id"))
                        .code(res.getString("code"))
                        .make(res.getString("make"))
                        .model(res.getString("model"))
                        .kw(res.getInt("kw"))
                        .color(res.getString("color"))
                        .year(res.getInt("year"))
                        .price(res.getInt("price"))
                        .createdAt(res.getTimestamp("created_at").toInstant())
                        .build())
        );
        return vehicles;
    }

    String buildSqlFromSearchCriteria(Map<String, String> searchCriteria){
        var textColumns = Arrays.asList("make", "model", "color", "code");
        StringBuilder sqlToAppend = new StringBuilder(" WHERE ");
        for (Map.Entry<String, String> sc : searchCriteria.entrySet()){
            var andClauseExists = sqlToAppend.toString().contains("=");
            if (textColumns.contains(sc.getKey())){
                sqlToAppend
                        .append(andClauseExists ? " AND " : "")
                        .append(sc.getKey())
                        .append("='")
                        .append(sc.getValue())
                        .append("'");
            } else {
                sqlToAppend
                        .append(andClauseExists ? " AND " : "")
                        .append(sc.getKey())
                        .append("=")
                        .append(sc.getValue());
            }
        }
        return sqlToAppend.toString();
    }
}
