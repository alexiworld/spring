package com.example.springjdbcmybatis3.vehicle.dao.mapper;

import com.example.springjdbcmybatis3.vehicle.dao.entity.VehicleEntity;
import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;

public interface VehicleEntityJavaMapper {

  // Mapping how-to is found at https://medium.com/@hsvdahiya/mybatis-annotations-result-mapping-spring-79944ff74b84
  @Results(id = "vehicleEntityResultMap", value = {
      @Result(property="id", column="id"),
      @Result(property="vinNumber", column="vin"),
      @Result(property="type", column="type"),
      @Result(property="model", column="model"),
      @Result(property="make", column="make"),
      @Result(property="year", column="mode_year"),
      @Result(property="createdOn", column="created_on"),
      @Result(property="createdBy", column="created_by")
  })
  @Select("SELECT * FROM vehicle WHERE id = #{id}")
  VehicleEntity findById(@Param("id") Long id);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM vehicle v WHERE v.vin = #{vinNumber}")
  VehicleEntity findByVinNumber(@Param("vinNumber") String vinNumber);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM vehicle")
  List<VehicleEntity> findAll();

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM vehicle v WHERE v.created_by = #{createdBy}")
  List<VehicleEntity> findAllByCreatedBy(@Param("createdBy") String createdBy);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM vehicle v WHERE v.mode_year = #{year}")
  List<VehicleEntity> findAllByYear(@Param("year") String year);

  @Delete("SELECT * FROM vehicle v WHERE v.id = #{id}")
  boolean deleteById(@Param("id") Long id);

  @InsertProvider(type= SqlProviderAdapter.class, method="insert")
  @Options(useGeneratedKeys=true, keyProperty="row.id") // if this fails, the keyProperty must be set to "id"
  int save(InsertStatementProvider<VehicleEntity> insertStatement);

}