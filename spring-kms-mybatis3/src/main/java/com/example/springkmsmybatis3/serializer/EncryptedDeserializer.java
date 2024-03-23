package com.example.springkmsmybatis3.serializer;

import com.example.springkmsmybatis3.model.EncryptedString;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class EncryptedDeserializer extends StdDeserializer<EncryptedString> {
    protected EncryptedDeserializer(Class<EncryptedString> vc) {
        super(vc);
    }
    public EncryptedDeserializer(){
        this(null);
    }

    @Override
    public EncryptedString deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return EncryptedString.to(p.getCodec().readValue(p, String.class));
    }
}