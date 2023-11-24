package com.example.springkmsmybatis2.dao;

import com.example.springkmsmybatis2.dao.entity.VehicleEntity;
import com.example.springkmsmybatis2.model.EncryptedString;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class VehicleEntityDAO extends SqlMapClientDaoSupport {

    @Autowired
    public VehicleEntityDAO(@Qualifier(value = "sqlMapClient") SqlMapClient sqlMapClient) {
        super.setSqlMapClient(sqlMapClient);
    }

    // This query is added for compatibility with JPA version of the same application
    // in the short period of time for developing of the field level encryption prototype.
    // @Query("SELECT last_value FROM \"VEHICLE_id_seq\"")
    public Long findNextId() {
        Long id = (Long) getSqlMapClientTemplate().queryForObject(
                "com.example.springkmsmybatis2.findNextId");
        return id;
    }

    @Transactional(readOnly = false)
    public VehicleEntity save(VehicleEntity entity) {
        Long id = (Long) getSqlMapClientTemplate().insert(
                "com.example.springkmsmybatis2.save", entity);
        entity.setId(id);
        return entity;
    }

    public Optional<VehicleEntity> findById(Long id) {
        VehicleEntity vehicleEntity = (VehicleEntity) getSqlMapClientTemplate().queryForObject(
                "com.example.springkmsmybatis2.findById", id);
        return Optional.ofNullable(vehicleEntity);
    }

    public Optional<VehicleEntity> findByVinNumber(String vin) {
        VehicleEntity vehicleEntity = (VehicleEntity) getSqlMapClientTemplate().queryForObject(
                "com.example.springkmsmybatis2.findByVinNumber", vin);
        return Optional.ofNullable(vehicleEntity);
    }

    public List<VehicleEntity> findAll() {
        List<VehicleEntity> vehicleEntities = getSqlMapClientTemplate().queryForList(
                "com.example.springkmsmybatis2.findAll");
        return vehicleEntities;
    }

    public List<VehicleEntity> findAllByCreatedBy(EncryptedString createdBy) {
        List<VehicleEntity> vehicleEntities = getSqlMapClientTemplate().queryForList(
                "com.example.springkmsmybatis2.findAllCreatedBy", createdBy);
        return vehicleEntities;
    }

    public List<VehicleEntity> findAllByYear(String year) {
        List<VehicleEntity> vehicleEntities = getSqlMapClientTemplate().queryForList(
                "com.example.springkmsmybatis2.findAllByYear", year);
        return vehicleEntities;
    }

    public boolean deleteById(Long id) {
        int res = getSqlMapClientTemplate().delete("com.example.springkmsmybatis2.deleteById", id);
        return res > 0;
    }

}
