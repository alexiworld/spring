package com.example.springkmsjdbc.service.impl;

import com.example.springkmsjdbc.dao.converter.EncryptedString;
import com.example.springkmsjdbc.dao.entity.VehicleEntity;
import com.example.springkmsjdbc.dao.repositories.VehicleRepository;
import com.example.springkmsjdbc.model.Vehicle;
import com.example.springkmsjdbc.model.VehicleResponse;
import com.example.springkmsjdbc.service.VehicleService;
import com.example.springkmsjdbc.utils.ModelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    public VehicleResponse process(Vehicle vehicle){
        VehicleEntity vehicleEntity = ModelUtils.map(vehicle);
        VehicleEntity saveVehicleEntity = vehicleRepository.save(vehicleEntity);

        VehicleResponse response = new VehicleResponse();
        response.setId(saveVehicleEntity.getId());
        return response;
    }

    public Vehicle fetchVehicle(Long id) {
        Optional<Vehicle> result = vehicleRepository.findById(id).map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with id "+id);
    }

    public Vehicle fetchVehicleByVin(String vinNumber) {
        Optional<Vehicle> result = vehicleRepository.findByVinNumber("toenc(" + vinNumber + ")").map(ModelUtils::map);
        if(result.isPresent()){
            return result.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with vin "+vinNumber);
    }

    public List<Vehicle> fetchVehicles() {
        Iterable<VehicleEntity> result = vehicleRepository.findAll();
        return StreamSupport.stream(result.spliterator(), false).map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByYear(String year) {
        List<VehicleEntity> result = vehicleRepository.findAllByYear(year);
        return result.stream().map(ModelUtils::map).toList();
    }

    public List<Vehicle> fetchVehiclesByAgent(String agent) {
        List<VehicleEntity> result = vehicleRepository.findAllByCreatedBy(EncryptedString.to(agent));
        return result.stream().map(ModelUtils::map).toList();
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

}