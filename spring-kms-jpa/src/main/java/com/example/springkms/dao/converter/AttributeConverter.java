package com.example.springkms.dao.converter;

import com.example.springkms.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

public class AttributeConverter implements jakarta.persistence.AttributeConverter<String, String> {

    @Autowired
    DataEncryptDecrypt encryptDecrypt;

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(String o) {
        return encryptDecrypt.encrypt(o);
    }

    @SneakyThrows
    @Override
    public String convertToEntityAttribute(String o) {
        return encryptDecrypt.decrypt(o);
    }
}
