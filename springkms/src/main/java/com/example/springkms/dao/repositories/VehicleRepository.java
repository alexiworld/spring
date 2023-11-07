package com.example.springkms.dao.repositories;

import com.example.springkms.dao.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {
}