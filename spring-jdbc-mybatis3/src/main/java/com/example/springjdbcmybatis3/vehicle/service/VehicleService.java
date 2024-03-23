package com.example.springjdbcmybatis3.vehicle.service;

import com.example.springjdbcmybatis3.vehicle.model.Vehicle;
import com.example.springjdbcmybatis3.vehicle.model.VehicleResponse;

import java.util.List;

public interface VehicleService {

    public VehicleResponse process(Vehicle vehicle);

    public Vehicle fetchVehicle(Long id);

    public Vehicle fetchVehicleByVin(String vinNumber);

    public List<Vehicle> fetchVehicles();

    public List<Vehicle> fetchVehiclesByYear(String year);

    public List<Vehicle> fetchVehiclesByAgent(String agent);

    public void deleteVehicle(Long id);

}