package com.example.springkmsmybatis2;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This app demonstrates how to encrypt/decrypt entity fields for Spring Data JDBC based project. Developer found that
// JPA converter solution is not applicable here, and therefore, a different approach had to be applied. There are two
// ways experimented:
// 1) Use enc() to denote an encrypted field, and toenc() to indicate the need of encrypted action.
//    BeforeConvertCallback<VehicleEntity> onBeforeConvert() will be used to encrypt specific field of the entity type.
//    StrWritingConverter.convert will be used to encrypt any text wrapped in toenc(). Discouraged as being run for every possible entity type,
//    having one or more text fields.
//    AfterConvertCallback<VehicleEntity> onAfterConvert() will be used to decrypt every text wrapped in enc(). Entity specific, performance OK.
// 2) Use EncryptedString (or similarly named type) and registering Writing and Reading converters.
// Out the two approaches, option 2) is the most preferred and recommended way as the code is more clear and readable, and converting is done
// exactly when needed.
// Note that defining VehicleWritingConverter and VehicleReadingConverter with pair <VehicleEntity, VehicleEntity> is not working, as the Spring
// Data JDBC converters are meant to convert from Java type to database supported type and versa, and VehicleEntity is not supported database type.

// Sources:
// https://mybatis.org/mybatis-3/getting-started.html
// https://mybatis.org/spring/getting-started.html
// https://mybatis.org/mybatis-3/configuration.html
// https://segmentfault.com/a/1190000042329719/en
//
// Other sources:
// https://github.com/Weasley-J/mybatis-encrypt-spring-boot-parent/tree/main/mybatis-encrypt-spring-boot-starter
// https://github.com/ColinZou/mybatis-column-encryption
// https://www.baeldung.com/spring-mybatis
// https://stackoverflow.com/questions/56593722/how-to-specify-typehandler-for-setmyenum-in-mybatis
// https://ibatis.apache.org/docs/dotnet/datamapper/ch03s05.html
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
public class SpringKMSMyBatis2Application {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SpringKMSMyBatis2Application.class, args);
    }

}
