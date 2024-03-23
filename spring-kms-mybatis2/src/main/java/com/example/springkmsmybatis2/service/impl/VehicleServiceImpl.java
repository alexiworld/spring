package com.example.springkmsmybatis2.service.impl;

import com.example.springkmsmybatis2.dao.entity.VehicleEntity;
import com.example.springkmsmybatis2.dao.VehicleEntityDAO;
import com.example.springkmsmybatis2.model.EncryptedString;
import com.example.springkmsmybatis2.model.Vehicle;
import com.example.springkmsmybatis2.model.VehicleResponse;
import com.example.springkmsmybatis2.service.VehicleService;
import com.example.springkmsmybatis2.utils.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service("vehicleServiceRepoImpl")
@Log4j2
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleEntityDAO vehicleEntityDAO;

    public VehicleResponse process(Vehicle vehicle){
        VehicleEntity vehicleEntity = ModelUtils.map(vehicle);
        VehicleEntity saveVehicleEntity = vehicleEntityDAO.save(vehicleEntity);

        VehicleResponse response = new VehicleResponse();
        response.setId(saveVehicleEntity.getId());
        return response;
    }

    public Vehicle fetchVehicle(Long id) {
        Optional<Vehicle> result = vehicleEntityDAO.findById(id).map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with id "+id);
    }

    public Vehicle fetchVehicleByVin(String vinNumber) {
        Optional<Vehicle> result = vehicleEntityDAO.findByVinNumber(vinNumber).map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with vin "+vinNumber);
    }

    public List<Vehicle> fetchVehicles() {
        List<VehicleEntity> result = vehicleEntityDAO.findAll();
        return result.stream().map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByYear(String year) {
        List<VehicleEntity> result = vehicleEntityDAO.findAllByYear(year);
        return result.stream().map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByAgent(String agent) {
        List<VehicleEntity> result = vehicleEntityDAO.findAllByCreatedBy(EncryptedString.to(agent));
        return result.stream().map(ModelUtils::map).toList();
    }

    public void deleteVehicle(Long id) {
        vehicleEntityDAO.deleteById(id);
    }

}