package com.example.springjdbcmybatis3;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This app demonstrates how to integrate MyBatis in Spring Data JDBC and allow mix support
// of JDBC repositories and MyBatis mappers. There are similar examples to this focused on
// integrating MyBatis 2, 3, Spring Data JDBC, Spring Data JPA with Spring Boot framework.
//
// The information below is from some previous work and is left-over because is found
// relevant to the subject. Developer trying to understand the integration should focus on
// todo package and related java and xml mappers, configuration, main app and test set up.
// Good design requires controller to call a service, and the service to call JDBC repo or
// MyBatis mappers (as this is done with vehicle package) but to keep the code concise and
// easy to follow in the case of todo package, the controller invokes the repo/mappers
// directly. See payload.sh for examples on how to call the TODO APIs.
//
// H2:
// CREATE TABLE VEHICLE  ("id" INT PRIMARY KEY auto_increment, "created_by" VARCHAR(255), "created_on" TIMESTAMP, "make" VARCHAR(255), "mode_year" VARCHAR(255), "model" VARCHAR(255), "type" VARCHAR(255), "vin" VARCHAR(255))
//
// Postgres:
// docker run --rm --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres
// docker exec -ti postgres bash
// root@90e461c00a35:/# psql -U postgres
//
// postgres=# CREATE TABLE "VEHICLE"  ("id" SERIAL PRIMARY KEY, "created_by" VARCHAR(255), "created_on" TIMESTAMP, "make" VARCHAR(255), "mode_year" VARCHAR(255), "model" VARCHAR(255), "type" VARCHAR(255), "vin" VARCHAR(255));
// CREATE TABLE
// postgres=# SELECT * FROM "VEHICLE";
// postgres=# DELETE FROM "VEHICLE";
// DELETE 3
//
// cURL:
// curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload1.json
// curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload2.json
// curl -H "Content-type: application/json" localhost:8080/rest/v1/vehicle -d @payload3.json
// curl localhost:8080/rest/v1/vehicle/
// curl localhost:8080/rest/v1/vehicle/1
// curl localhost:8080/rest/v1/vehicle/2
// curl localhost:8080/rest/v1/vehicle/3
// curl localhost:8080/rest/v1/vehicle/year/2023
// curl localhost:8080/rest/v1/vehicle/vin/vin123
// curl localhost:8080/rest/v1/vehicle/vin/vin456
// curl localhost:8080/rest/v1/vehicle/vin/vin789

@SpringBootApplication
@Log4j2
public class MainApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
