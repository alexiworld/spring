package com.example.springkmsjdbc.dao.repositories;

import com.example.springkmsjdbc.dao.converter.EncryptedString;
import com.example.springkmsjdbc.dao.entity.VehicleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVinNumber(String vin);

    List<VehicleEntity> findAllByYear(String year);

    List<VehicleEntity> findAllByCreatedBy(EncryptedString createBy);

}