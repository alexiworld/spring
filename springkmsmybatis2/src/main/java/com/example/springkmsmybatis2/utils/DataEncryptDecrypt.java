package com.example.springkmsmybatis2.utils;

import com.example.springkmsmybatis2.ApplicationContextProvider;
import lombok.SneakyThrows;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Component
public class DataEncryptDecrypt implements InitializingBean {

    @Autowired
    DataKeyProvider dataKeyProvider;

    Key key;

    static DataEncryptDecrypt encryptDecrypt = null;

    public static DataEncryptDecrypt getInstance() {
        if (encryptDecrypt == null) {
            ApplicationContext context = ApplicationContextProvider.getApplicationContext();
            if (context != null) {
                encryptDecrypt = context.getBean("dataEncryptDecrypt", DataEncryptDecrypt.class);
                //encryptDecrypt = context.getBean(DataEncryptDecrypt.class); // another way to fetch it
            }
        }
        return encryptDecrypt;
    }

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

    public String encrypt(String plainString) throws GeneralSecurityException {
        byte[] bytes = SerializationUtils.serialize(plainString);
        return Base64.getEncoder().encodeToString(getEncryptionCipher().doFinal(bytes));
    }

    // TODO:
    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/SerializationUtils.html
    // Deprecated.
    // This utility uses Java Object Serialization, which allows arbitrary code to be run and is known for being the source of many Remote Code Execution (RCE) vulnerabilities.
    // Prefer the use of an external tool (that serializes to JSON, XML, or any other format) which is regularly checked and updated for not allowing RCE.
    public String decrypt(String encryptedString) throws GeneralSecurityException {
        byte[] bytes = getDecryptionCipher().doFinal(Base64.getDecoder().decode(encryptedString));
        return (String) SerializationUtils.deserialize(bytes);
    }

    @SneakyThrows
    public String hash(String plainString) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] salt = getSalt();
        md.update(salt);
        byte[] bytes = md.digest(plainString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
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
