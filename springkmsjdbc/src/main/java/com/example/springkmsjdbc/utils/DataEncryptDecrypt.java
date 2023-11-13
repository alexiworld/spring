package com.example.springkmsjdbc.utils;

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

    private Cipher getEncryptionCipher() throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher;
    }

    private Cipher getDecryptionCipher() throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher;
    }

    public String encrypt(String object) throws GeneralSecurityException {
        byte[] bytes = SerializationUtils.serialize(object);
        return Base64.getEncoder().encodeToString(getEncryptionCipher().doFinal(bytes));
    }

    // TODO:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/SerializationUtils.html
    // Deprecated.
    // This utility uses Java Object Serialization, which allows arbitrary code to be run and is known for being the source of many Remote Code Execution (RCE) vulnerabilities.
    // Prefer the use of an external tool (that serializes to JSON, XML, or any other format) which is regularly checked and updated for not allowing RCE.
    public String decrypt(String decryptString) throws GeneralSecurityException {
        byte[] bytes = getDecryptionCipher().doFinal(Base64.getDecoder().decode(decryptString));
        return (String) SerializationUtils.deserialize(bytes);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //byte[] iv = new byte[] {11, 13, -2, 0, 45, 1, 17, 127, 44, -45, -54, 3};
        //String dataKey = "RMQKCUMWQUrr6sffD5liumsEXZbG38y1uzgcda+C3rU=";
        //key = new SecretKeySpec(Base64.getDecoder().decode(dataKey.getBytes()), "AES");

        // Using KMS
        //key = new SecretKeySpec(dataKeyProvider.getDataKey(), "AES");

        // Using hard coded key for local development
        key = new SecretKeySpec(dataKeyProvider.getHardKey(), "AES");

    }
}
