package com.example.springkms.service;

import com.example.springkms.dao.entity.VehicleEntity;
import com.example.springkms.dao.repositories.VehicleRepository;
import com.example.springkms.model.Vehicle;
import com.example.springkms.model.VehicleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;


    public VehicleResponse process(Vehicle vehicle){
        VehicleEntity vehicleEntity = map(vehicle);
        VehicleEntity saveVehicleEntity = vehicleRepository.save(vehicleEntity);

        VehicleResponse response = new VehicleResponse();
        response.setId(saveVehicleEntity.getId());

        return response;
    }


    private VehicleEntity map(Vehicle vehicle) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVinNumber(vehicle.getVinNumber());
        vehicleEntity.setType(vehicle.getType());
        vehicleEntity.setModel(vehicle.getModel());
        vehicleEntity.setMake(vehicle.getMake());
        vehicleEntity.setYear(vehicle.getYear());
        vehicleEntity.setCreatedOn(LocalDateTime.now());
        vehicleEntity.setCreatedUser("AlexiworlD");
        return vehicleEntity;

    }

    private Vehicle map(VehicleEntity VehicleEntity) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVinNumber(VehicleEntity.getVinNumber());
        vehicle.setType(VehicleEntity.getType());
        vehicle.setModel(VehicleEntity.getModel());
        vehicle.setMake(VehicleEntity.getMake());
        vehicle.setYear(VehicleEntity.getYear());
        return vehicle;

    }

    public Vehicle fetchVehicle(Long id) {
        Optional<VehicleEntity> result = vehicleRepository.findById(id);
        if(result.isPresent()){
            return map(result.get());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No vehicle found with id "+id);

    }
}