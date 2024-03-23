package com.example.springkmsjdbc.dao.converter;

import com.example.springkmsjdbc.utils.DataEncryptDecrypt;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@Slf4j
@ReadingConverter
public class EncryptedStringReadingConverter implements Converter<String, EncryptedString> {
    //@Autowired // did not work and therefore set from outside
    DataEncryptDecrypt encryptDecrypt;

    public void setEncryptDecrypt(DataEncryptDecrypt encryptDecrypt) {
        this.encryptDecrypt = encryptDecrypt;
    }

    @SneakyThrows
    @Override
    public EncryptedString convert(String source) {
        return EncryptedString.to(encryptDecrypt.decrypt(source));
    }
}
