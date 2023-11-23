package com.example.springkmsmybatis3.dao.mapper;

import com.example.springkmsmybatis3.dao.entity.VehicleEntity;
import com.example.springkmsmybatis3.model.EncrappedString;
import com.example.springkmsmybatis3.model.EncryptedString;
import org.apache.ibatis.annotations.*;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import java.util.List;

public interface VehicleEntityJavaMapper {

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
  @Select("SELECT * FROM \"VEHICLE\" WHERE id = #{id}")
  VehicleEntity findById(@Param("id") Long id);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM \"VEHICLE\" v WHERE v.vin = #{vinNumber}")
  VehicleEntity findByVinNumber(@Param("vinNumber") EncrappedString vinNumber);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM \"VEHICLE\"")
  List<VehicleEntity> findAll();

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM \"VEHICLE\" v WHERE v.created_by = #{createdBy}")
  List<VehicleEntity> findAllByCreatedBy(@Param("createdBy") EncryptedString createdBy);

  @ResultMap("vehicleEntityResultMap")
  @Select("SELECT * FROM \"VEHICLE\" v WHERE v.mode_year = #{year}")
  List<VehicleEntity> findAllByYear(@Param("year") String year);

  @Delete("SELECT * FROM \"VEHICLE\" v WHERE v.id = #{id}")
  boolean deleteById(@Param("id") Long id);

  @InsertProvider(type= SqlProviderAdapter.class, method="insert")
  @Options(useGeneratedKeys=true, keyProperty="row.id")
  int save(InsertStatementProvider<VehicleEntity> insertStatement);

}