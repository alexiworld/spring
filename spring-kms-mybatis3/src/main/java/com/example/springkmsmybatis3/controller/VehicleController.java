package com.example.springkmsmybatis3.controller;

import com.example.springkmsmybatis3.model.Vehicle;
import com.example.springkmsmybatis3.model.VehicleResponse;
import com.example.springkmsmybatis3.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/vehicle")
public class VehicleController {

    @Autowired
    @Qualifier("vehicleServiceMapperImpl")
    VehicleService service;

    // Demonstrates saving vehicle. The saved vehicle must have both vin number and agent encrypted using Method #1 and #2.
    @PostMapping
    public VehicleResponse saveVehicle(@RequestBody Vehicle vehicle){
        VehicleResponse response = service.process(vehicle);
        return response;
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable("id") Long id){
        Vehicle response = service.fetchVehicle(id);
        return response;
    }

    // Demonstrates searching by encrypted field using Method #1. The result should have all fields decrypted.
    @GetMapping("/vin/{vin}")
    public Vehicle getVehicle(@PathVariable("vin") String vin){
        Vehicle response = service.fetchVehicleByVin(vin);
        return response;
    }

    // Demonstrates fetching all records. The response should have all vehicles with fields decrypted.
    @GetMapping("/")
    public List<Vehicle> getVehicles() {
        List<Vehicle> response = service.fetchVehicles();
        return response;
    }

    // Demonstrates searching by non-encrypted field. The result should have all fields decrypted.
    @GetMapping("/year/{year}")
    public List<Vehicle> getVehiclesByYear(@PathVariable("year") String year) {
        List<Vehicle> response = service.fetchVehiclesByYear(year);
        return response;
    }

    // Demonstrates searching by encrypted field using Method #2. The result should have all fields decrypted.
    @GetMapping("/agent/{agent}")
    public List<Vehicle> getVehiclesByAgent(@PathVariable("agent") String agent) {
        List<Vehicle> response = service.fetchVehiclesByAgent(agent);
        return response;
    }

    // Demonstrates fetching vehicle. The returned vehicle (if any found) must have all fields decrypted.
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable("id") Long id){
        service.deleteVehicle(id);
    }

}