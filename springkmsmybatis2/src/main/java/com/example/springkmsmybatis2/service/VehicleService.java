package com.example.springkmsmybatis2.service;

import com.example.springkmsmybatis2.model.Vehicle;
import com.example.springkmsmybatis2.model.VehicleResponse;

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