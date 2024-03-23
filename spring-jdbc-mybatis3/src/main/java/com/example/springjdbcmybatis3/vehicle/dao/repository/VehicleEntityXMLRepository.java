package com.example.springjdbcmybatis3.vehicle.dao.repository;

import com.example.springjdbcmybatis3.vehicle.dao.entity.VehicleEntity;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <code>VehicleRepository</code> interface is extension of the <code>CrudRepository</>
 * interface. It is limited to demonstrating the field encryption capabilities. Do not
 * use it as a reference application.
 */
@Repository
@Transactional(readOnly = true)
public class VehicleEntityXMLRepository {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    // This query is added for compatibility with JPA version of the same application
    // in the short period of time for developing of the field level encryption prototype.
    // @Query("SELECT last_value FROM \"VEHICLE_id_seq\"")
    public Long findNextId() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Long id = session.selectOne(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findNextId");
            return id;
        }
    }

    @Transactional(readOnly = false)
    public Optional<VehicleEntity> save(VehicleEntity entity) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int res = session.insert(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.save", entity);
            return Optional.ofNullable(res > 0 ? entity : null);
        }
    }

    public Optional<VehicleEntity> findById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntity vehicleEntity = session.selectOne(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findById", id);
            return Optional.ofNullable(vehicleEntity);
        }
    }

    public Optional<VehicleEntity> findByVinNumber(String vin) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VehicleEntity vehicleEntity = session.selectOne(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findByVinNumber", vin);
            return Optional.ofNullable(vehicleEntity);
        }
    }

    public List<VehicleEntity> findAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<VehicleEntity> vehicleEntities = session.selectList(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findAll");
            return vehicleEntities;
        }
    }

    public List<VehicleEntity> findAllByCreatedBy(String createdBy) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<VehicleEntity> vehicleEntities = session.selectList(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findAllCreatedBy", createdBy);
            return vehicleEntities;
        }
    }

    public List<VehicleEntity> findAllByYear(String year) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<VehicleEntity> vehicleEntities = session.selectList(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.findAllByYear", year);
            return vehicleEntities;
        }
    }

    public boolean deleteById(Long id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            int res = session.delete(
                    "com.example.springjdbcmybatis3.vehicle.dao.VehicleEntityXMLMapper.deleteById", id);
            return res > 0;
        }
    }
}
