package com.example.springkms.config;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;

@Component
public class AESEncryptor {

    byte[] iv = new byte[] {11, 13, -2, 0, 45, 1, 17, 127, 44, -45, -54, 3};
    private String dataKey = "RMQKCUMWQUrr6sffD5liumsEXZbG38y1uzgcda+C3rU=";

    public static void main(String[] args) throws GeneralSecurityException {
        AESEncryptor encryptor = new AESEncryptor();
        System.out.println(encryptor.decrypt(encryptor.encrypt("111111111111111111111111111111111111111")));
    }

    private Key getKey() {
        Key key = new SecretKeySpec(Base64.getDecoder().decode(dataKey.getBytes()), "AES");
        return key;
    }

    private Cipher getEncryptionCipher() throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        return cipher;
    }

    private Cipher getDecryptionCipher() throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey());
        return cipher;
    }

    public String encrypt(Object object) throws GeneralSecurityException {
        byte[] bytes = SerializationUtils.serialize(object);
        return Base64.getEncoder().encodeToString(getEncryptionCipher().doFinal(bytes));
    }

    // TODO:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/SerializationUtils.html
    // Deprecated.
    // This utility uses Java Object Serialization, which allows arbitrary code to be run and is known for being the source of many Remote Code Execution (RCE) vulnerabilities.
    // Prefer the use of an external tool (that serializes to JSON, XML, or any other format) which is regularly checked and updated for not allowing RCE.
    public Object decrypt(String dataString) throws GeneralSecurityException {
        byte[] bytes = getDecryptionCipher().doFinal(Base64.getDecoder().decode(dataString));
        return SerializationUtils.deserialize(bytes);
    }

}