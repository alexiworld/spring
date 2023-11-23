package com.example.springkmsmybatis3.service.impl;

import com.example.springkmsmybatis3.dao.entity.VehicleEntity;
import com.example.springkmsmybatis3.dao.mapper.VehicleEntityJavaMapper;
import com.example.springkmsmybatis3.model.EncrappedString;
import com.example.springkmsmybatis3.model.EncryptedString;
import com.example.springkmsmybatis3.model.Vehicle;
import com.example.springkmsmybatis3.model.VehicleResponse;
import com.example.springkmsmybatis3.service.VehicleService;
import com.example.springkmsmybatis3.utils.ModelUtils;
import lombok.extern.log4j.Log4j2;
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
    VehicleEntityJavaMapper vehicleEntityJavaMapper;

    public VehicleResponse process(Vehicle vehicle){
        VehicleEntity vehicleEntity = ModelUtils.map(vehicle);

        SqlTable sqlTable = SqlTable.of("\"VEHICLE\"");
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
        int res = vehicleEntityJavaMapper.save(insertStatement);

        VehicleResponse response = new VehicleResponse();
        response.setId(insertStatement.getRow().getId());
        return response;
    }

    public Vehicle fetchVehicle(Long id) {
        Optional<Vehicle> result = Optional.ofNullable(vehicleEntityJavaMapper.findById(id))
                .map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with id "+id);
    }

    public Vehicle fetchVehicleByVin(String vinNumber) {
        Optional<Vehicle> result =
                Optional.ofNullable(vehicleEntityJavaMapper.findByVinNumber(EncrappedString.to(vinNumber)))
                        .map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with vin "+vinNumber);
    }

    public List<Vehicle> fetchVehicles() {
        Iterable<VehicleEntity> result = vehicleEntityJavaMapper.findAll();
        return StreamSupport.stream(result.spliterator(), false).map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByYear(String year) {
        List<VehicleEntity> result = vehicleEntityJavaMapper.findAllByYear(year);
        return result.stream().map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByAgent(String agent) {
        List<VehicleEntity> result = vehicleEntityJavaMapper.findAllByCreatedBy(EncryptedString.to(agent));
        return result.stream().map(ModelUtils::map).toList();
    }

    public void deleteVehicle(Long id) {
        vehicleEntityJavaMapper.deleteById(id);
    }

}