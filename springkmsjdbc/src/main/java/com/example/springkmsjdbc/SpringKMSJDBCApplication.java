package com.example.springkmsjdbc;

import com.example.springkmsjdbc.dao.converter.EncryptedStringReadingConverter;
import com.example.springkmsjdbc.dao.converter.EncryptedStringWritingConverter;
import com.example.springkmsjdbc.dao.entity.VehicleEntity;
import com.example.springkmsjdbc.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.AfterConvertCallback;
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

import java.security.GeneralSecurityException;
import java.util.List;

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
// https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.repositories
// https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.examples-repo
// https://jpa-buddy.com/blog/spring-data-jpa-to-spring-data-jdbc-a-smooth-ride/
// https://docs.spring.io/spring-data/jdbc/docs/3.1.5/api//org/springframework/data/relational/core/mapping/event/BeforeSaveCallback.html
// https://docs.spring.io/spring-data/jdbc/docs/3.1.5/api//org/springframework/data/relational/core/mapping/event/BeforeConvertCallback.html
// https://docs.spring.io/spring-data/jdbc/docs/3.1.5/api//org/springframework/data/relational/core/mapping/event/AfterConvertCallback.html
// https://codetinkering.com/spring-jdbc-beforesavecallback-example/
// https://spring.io/blog/2021/09/09/spring-data-jdbc-how-to-use-custom-id-generation
// https://stackoverflow.com/questions/72816974/customconversions-not-working-in-spring-data-jdbc
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
public class SpringKMSJDBCApplication {

	@Autowired
	DataEncryptDecrypt encryptDecrypt;

	// StrXXXConverter classes can be extracted and moved out to dao.converter subpackage but are kept here purposely
	// to demonstrate another way of declaring and using them.

	@WritingConverter
	static class StrWritingConverter implements Converter<String,String> {
		//@Autowired // did not work and therefore set from outside
		DataEncryptDecrypt encryptDecrypt;

		public void setEncryptDecrypt(DataEncryptDecrypt encryptDecrypt) {
			this.encryptDecrypt = encryptDecrypt;
		}

		@SneakyThrows
		@Override
		public String convert(String source) {
			//log.debug("[XMARKER] StrWritingConverter : " + source);
			try {
				if (source!= null && source.startsWith("toenc(") && source.endsWith(")")) {
					String pattern = "(toenc\\()(.*)(\\))";
					String valueToEncrypt = source.replaceAll(pattern, "$2"); // source.substring(6, source.length-7)
					source = "enc(" + encryptDecrypt.encrypt(valueToEncrypt) + ")";
				}
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}

			return source;
		}
	}

	@ReadingConverter
	static class StrReadingConverter implements Converter<String,String> {
		//@Autowired // did not work and therefore set from outside
		DataEncryptDecrypt encryptDecrypt;

		public void setEncryptDecrypt(DataEncryptDecrypt encryptDecrypt) {
			this.encryptDecrypt = encryptDecrypt;
		}

		@SneakyThrows
		@Override
		public String convert(String source) {
			//log.debug("[XMARKER] StrReadingConverter : " + source);
			return source;
		}
	}

	@Bean
	public AbstractJdbcConfiguration jdbcConfiguration() {
		return new MySpringBootJdbcConfiguration();
	}

	@Configuration
	static class MySpringBootJdbcConfiguration extends AbstractJdbcConfiguration {
		@Autowired
		DataEncryptDecrypt encryptDecrypt;

		@Override
		protected List<?> userConverters() {
			//log.debug("[XMARKER] userConverters encryptDecrypt: " + encryptDecrypt);

			StrReadingConverter strReadingConverter = new StrReadingConverter();
			strReadingConverter.setEncryptDecrypt(encryptDecrypt);

			StrWritingConverter strWritingConverter = new StrWritingConverter();
			strWritingConverter.setEncryptDecrypt(encryptDecrypt);

			EncryptedStringWritingConverter encryptedStringWritingConverter = new EncryptedStringWritingConverter();
			encryptedStringWritingConverter.setEncryptDecrypt(encryptDecrypt);

			EncryptedStringReadingConverter encryptedStringReadingConverter = new EncryptedStringReadingConverter();
			encryptedStringReadingConverter.setEncryptDecrypt(encryptDecrypt);

			return List.of(strWritingConverter, strReadingConverter, encryptedStringWritingConverter, encryptedStringReadingConverter);
		}

	}

	@Bean
	BeforeSaveCallback<VehicleEntity> beforeSaveCallback() {
		return (vehicle, mutableAggregateChange) -> {
			//log.debug("[XMARKER]  beforeSaveCallback: " + vehicle);
			//log.debug("[XMARKER]  beforeSaveCallback: " + mutableAggregateChange);
			boolean pause = true;
			return vehicle;
		};
	}

	@Bean
	BeforeConvertCallback<VehicleEntity> onBeforeConvert() {
		return (vehicle) -> {
			//log.debug("[XMARKER]  onBeforeConvert: " + vehicle);
			boolean pause = true;
			try {
				vehicle.setVinNumber("enc(" + encryptDecrypt.encrypt(vehicle.getVinNumber()) + ")");
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			return vehicle;
		};
	}

//	@Bean
//	ApplicationListener onApplicationEvent() {
//		return (event) -> log.debug("[XMARKER]  event: " + event);
//	}

	@Bean
	AfterConvertCallback<VehicleEntity> onAfterConvert() {
		return (vehicle) -> {
			//log.debug("[XMARKER]  onAfterConvert: " + vehicle);
			boolean pause = true;
			if (vehicle != null && vehicle.getVinNumber() != null) {
				String vinNumber = vehicle.getVinNumber();
			    if (vinNumber!= null && vinNumber.startsWith("enc(") && vinNumber.endsWith(")")) {
					try {
						String pattern = "(enc\\()(.*)(\\))";
						String encrypted = vinNumber.replaceAll(pattern, "$2"); // source.substring(4, source.length-5)
						String decrypted = decrypted = encryptDecrypt.decrypt(encrypted);
						vehicle.setVinNumber(decrypted);
					} catch (GeneralSecurityException e) {
						e.printStackTrace();
					}
				}
			}
			return vehicle;
		};
	}

//	@Bean // Works too
//	AbstractRelationalEventListener<VehicleEntity> onAfterConvert2() {
//		return new AbstractRelationalEventListener<VehicleEntity>() {
//			@Override
//			protected void onAfterConvert(AfterConvertEvent<VehicleEntity> event) {
//				log.debug("[XMARKER]  onAfterConvert2: " + event.getEntity());
//			}
//		};
//	}

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(SpringKMSJDBCApplication.class, args);
	}

}
