package com.example.springjdbcmybatis3.vehicle.utils;

import com.example.springjdbcmybatis3.vehicle.dao.entity.VehicleEntity;
import com.example.springjdbcmybatis3.vehicle.model.Vehicle;

import java.time.LocalDateTime;

public class ModelUtils {

    public static VehicleEntity map(Vehicle vehicle) {
        VehicleEntity vehicleEntity = new VehicleEntity();
        vehicleEntity.setVinNumber(vehicle.getVinNumber());
        vehicleEntity.setType(vehicle.getType());
        vehicleEntity.setModel(vehicle.getModel());
        vehicleEntity.setMake(vehicle.getMake());
        vehicleEntity.setYear(vehicle.getYear());
        vehicleEntity.setCreatedOn(LocalDateTime.now());
        vehicleEntity.setCreatedBy(vehicle.getAgent());
        return vehicleEntity;

    }

    public static Vehicle map(VehicleEntity vehicleEntity) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVinNumber(vehicleEntity.getVinNumber());
        vehicle.setType(vehicleEntity.getType());
        vehicle.setModel(vehicleEntity.getModel());
        vehicle.setMake(vehicleEntity.getMake());
        vehicle.setYear(vehicleEntity.getYear());
        vehicle.setAgent(vehicleEntity.getCreatedBy());
        return vehicle;
    }

}
