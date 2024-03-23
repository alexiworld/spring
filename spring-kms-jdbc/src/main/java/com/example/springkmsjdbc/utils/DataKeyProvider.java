package com.example.springkmsjdbc.utils;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class DataKeyProvider {

    @Autowired
    AWSKMS awsKmsClient;

    String encryptedData = "AQIDAHiigRrIFeY4izZd5ACxcOUuAJ5z2OW4OnVVZXT7FW0NyAHPyttJgcUSA487fTEnlmQUAAAAfjB8BgkqhkiG9w0BBwagbzBtAgEAMGgGCSqGSIb3DQEHATAeBglghkgBZQMEAS4wEQQMmYMexWjc3A+xG1aSAgEQgDtneArTNJvp7osrO1sh+ZGTLx4C+GUnbO5Sxh0KRM0nyCUL1De3pQieaoSDNxQh2CBo5J/okpnRjShlOQ==";

    public byte[] getDataKey() {
        DecryptRequest req = new DecryptRequest().withCiphertextBlob(ByteBuffer.wrap(Base64.getDecoder().decode(encryptedData)));
        DecryptResult decryptResult = awsKmsClient.decrypt(req);
        return decryptResult.getPlaintext().array();
    }

    public byte[] getHardKey() {
        return "secret-key-12345".getBytes(StandardCharsets.UTF_8);
    }

}
