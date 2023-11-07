package com.example.springkms.dao.entity;

import com.example.springkms.dao.converter.AttributeConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="vehicle")
@Data
public class VehicleEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="vin")
    @Convert(converter= AttributeConverter.class)
    private String vinNumber;

    @Column(name="type")
    private String type;

    @Column(name="model")
    private String model;

    @Column(name="make")
    private String make;

    @Column(name="mode_year")
    private String year;

    @Column(name="created_on", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdOn;

    @Column(name="created_by")
    private String createdUser;

}