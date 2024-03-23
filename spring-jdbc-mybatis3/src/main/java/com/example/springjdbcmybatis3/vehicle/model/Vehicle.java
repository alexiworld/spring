package com.example.springjdbcmybatis3.vehicle.model;

import lombok.Data;

@Data
public class Vehicle {
    private String vinNumber;
    private String type;
    private String model;
    private String make;
    private String year;
    private String agent; // Added to demonstrate the enc/dec
}