package com.example.springkmsjdbc.dao.entity;

import com.example.springkmsjdbc.dao.converter.EncryptedString;
import lombok.Data;
import org.springframework.data.annotation.Id;
//import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Table(name="VEHICLE")
@Data
public class VehicleEntity {

    private static AtomicLong counter = new AtomicLong();

    @Id
    @Column("id")
    private Long id; // = counter.incrementAndGet();

    @Column("vin")
    // Neither Converter, nor ColumnTranformer JPA's annotations work with JDBC.
    // Encryption/Requires new approaches.
    //@Convert(converter= AttributeConverter.class)
    //@ColumnTransformer(
    //        read = "PGP_SYM_DECRYPT(vin::BYTEA, 'secret-key-12345')",
    //        write = "PGP_SYM_ENCRYPT (?, 'secret-key-12345')"
    //)
    private String vinNumber;

    @Column("type")
    private String type;

    @Column("model")
    private String model;

    @Column("make")
    private String make;

    @Column("mode_year")
    private String year;

    @Column("created_on")
    private LocalDateTime createdOn;

    @Column("created_by")
    //private String createdBy;
    private EncryptedString createdBy;

}