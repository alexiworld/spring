package com.example.springkmsjdbc.dao.converter;

import com.example.springkmsjdbc.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

// This class is applicable to Spring Data JPA but unfortunately not to Spring Data JDBC. To see it in
// action have a look at springkms project, which uses JPA to encrypt/decrypt entity fields (columns).
@Deprecated
public class AttributeConverter { //implements jakarta.persistence.AttributeConverter<String, String> {

    @Autowired
    DataEncryptDecrypt encryptDecrypt;

    @SneakyThrows
    //@Override
    public String convertToDatabaseColumn(String o) {
        return encryptDecrypt.encrypt(o);
    }

    @SneakyThrows
    //@Override
    public String convertToEntityAttribute(String o) {
        return encryptDecrypt.decrypt(o);
    }
}
