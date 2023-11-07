package com.example.springkms.controller;

import com.example.springkms.model.Vehicle;
import com.example.springkms.model.VehicleResponse;
import com.example.springkms.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/vehicle")
public class VehicleController {

    @Autowired
    VehicleService service;

    @PostMapping
    public VehicleResponse saveVehicle(@RequestBody Vehicle vehicle){
        VehicleResponse response = service.process(vehicle);
        return response;
    }

    @GetMapping("/{id}")
    public Vehicle getVehicle(@PathVariable("id") Long id){
        Vehicle response = service.fetchVehicle(id);
        return response;
    }

}