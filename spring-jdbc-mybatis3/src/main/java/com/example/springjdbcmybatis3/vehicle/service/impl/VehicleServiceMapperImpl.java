package com.example.springjdbcmybatis3.vehicle.service.impl;

import com.example.springjdbcmybatis3.vehicle.dao.entity.VehicleEntity;
import com.example.springjdbcmybatis3.vehicle.dao.mapper.VehicleEntityJavaMapper;
import com.example.springjdbcmybatis3.vehicle.model.Vehicle;
import com.example.springjdbcmybatis3.vehicle.model.VehicleResponse;
import com.example.springjdbcmybatis3.vehicle.service.VehicleService;
import com.example.springjdbcmybatis3.vehicle.utils.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service("vehicleServiceMapperImpl")
@Log4j2
public class VehicleServiceMapperImpl implements VehicleService {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public VehicleResponse process(Vehicle vehicle) { // See TodoMapper for alternative way to insert record
        VehicleEntity vehicleEntity = ModelUtils.map(vehicle);

        SqlTable sqlTable = SqlTable.of("vehicle");
        InsertStatementProvider<VehicleEntity> insertStatement = SqlBuilder.insert(vehicleEntity)
                .into(sqlTable)
                // The id mapping must not be specified here; otherwise, error for null will be reported.
                // This does not have anything to do with getting the generated ID back. This is done by
                // adding @Options(useGeneratedKeys=true, keyProperty="row.id") annotation to mapper's
                // save method. Have a look at the VehicleEntityJavaMapper interface.
                //.map(SqlColumn.of("id", sqlTable)).toProperty("id")
                .map(SqlColumn.of("vin", sqlTable)).toProperty("vinNumber")
                .map(SqlColumn.of("type", sqlTable)).toProperty("type")
                .map(SqlColumn.of("model", sqlTable)).toProperty("model")
                .map(SqlColumn.of("make", sqlTable)).toProperty("make")
                .map(SqlColumn.of("mode_year", sqlTable)).toProperty("year")
                .map(SqlColumn.of("created_on", sqlTable)).toProperty("createdOn")
                .map(SqlColumn.of("created_by", sqlTable)).toProperty("createdBy")
                .build()
                .render(RenderingStrategies.MYBATIS3);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            int res = mapper.save(insertStatement);
            VehicleResponse response = new VehicleResponse();
            response.setId(insertStatement.getRow().getId());
            return response;
        }
    }

    public Vehicle fetchVehicle(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            Optional<Vehicle> result = Optional.ofNullable(mapper.findById(id))
                    .map(ModelUtils::map);
            if (result.isPresent()) {
                return result.get();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vehicle found with id " + id);
        }
    }

    public Vehicle fetchVehicleByVin(String vinNumber) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            Optional<Vehicle> result =
                    Optional.ofNullable(mapper.findByVinNumber(vinNumber))
                            .map(ModelUtils::map);
            if (result.isPresent()) {
                return result.get();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No vehicle found with vin " + vinNumber);
        }
    }

    public List<Vehicle> fetchVehicles() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            Iterable<VehicleEntity> result = mapper.findAll();
            return StreamSupport.stream(result.spliterator(), false).map(ModelUtils::map).toList();
        }
    }

    public List<Vehicle> fetchVehiclesByYear(String year) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            List<VehicleEntity> result = mapper.findAllByYear(year);
            return result.stream().map(ModelUtils::map).toList();
        }
    }

    public List<Vehicle> fetchVehiclesByAgent(String agent) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            List<VehicleEntity> result = mapper.findAllByCreatedBy(agent);
            return result.stream().map(ModelUtils::map).toList();
        }
    }

    public void deleteVehicle(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntityJavaMapper mapper = session.getMapper(VehicleEntityJavaMapper.class);
            mapper.deleteById(id);
        }
    }

}