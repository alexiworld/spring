package com.example.springkmsjdbc.dao.converter;

import com.example.springkmsjdbc.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@Slf4j
@WritingConverter
public class EncryptedStringWritingConverter implements Converter<EncryptedString,String> {
    //@Autowired // did not work and therefore set from outside
    DataEncryptDecrypt encryptDecrypt;

    public void setEncryptDecrypt(DataEncryptDecrypt encryptDecrypt) {
        this.encryptDecrypt = encryptDecrypt;
    }

    @SneakyThrows
    @Override
    public String convert(EncryptedString source) {
        return encryptDecrypt.encrypt(source.toString());
    }
}
