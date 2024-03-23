package com.example.springjdbcmybatis3.vehicle.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

// There is not much sense of maintaining the entity. Vehicle DTO can be used with MyBatis. Unless
// there are fields, which are named or typed differently for some good reason and conversion b/n
// entity and DTO is needed or preferred.
@Data
public class VehicleEntity {

    private static AtomicLong counter = new AtomicLong();

    private Long id; // = counter.incrementAndGet();
    private EncrappedString vinNumber;
    //private HashedString vinNumberHash; // override setVinNumber to set the plain value in this field.
    private String type;
    private String model;
    private String make;
    private String year;
    private LocalDateTime createdOn;
    private EncryptedString createdBy;

}