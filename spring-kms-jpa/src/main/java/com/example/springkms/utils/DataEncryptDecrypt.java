package com.example.springkms.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@Component
public class DataEncryptDecrypt implements InitializingBean {

    @Autowired
    DataKeyProvider dataKeyProvider;

    Key key;

    public String encrypt(String object) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = SerializationUtils.serialize(object);
        return Base64.getEncoder().encodeToString(cipher.doFinal(bytes));
    }

    public String decrypt(String decryptString) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(decryptString));
        return (String) SerializationUtils.deserialize(bytes);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        key = new SecretKeySpec(dataKeyProvider.getDataKey(), "AES");
    }
}
