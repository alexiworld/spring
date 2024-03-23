package com.example.springkmsmybatis2.serializer;

import com.example.springkmsmybatis2.model.EncryptedString;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EncryptedSerializer extends StdSerializer<EncryptedString> {

    protected EncryptedSerializer(Class<EncryptedString> t) {
        super(t);
    }

    public EncryptedSerializer() {
        this(null);
    }

    @Override
    public void serialize(EncryptedString encryptedString, JsonGenerator gen, SerializerProvider provider) throws IOException {
        //gen.writeStartObject();
        gen.writeString(encryptedString.toString());
    }
}