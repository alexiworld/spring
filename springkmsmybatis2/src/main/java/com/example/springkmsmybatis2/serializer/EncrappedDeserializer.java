package com.example.springkmsmybatis2.serializer;

import com.example.springkmsmybatis2.model.EncrappedString;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class EncrappedDeserializer extends StdDeserializer<EncrappedString> {
    protected EncrappedDeserializer(Class<EncrappedString> vc) {
        super(vc);
    }
    public EncrappedDeserializer(){
        this(null);
    }

    @Override
    public EncrappedString deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return EncrappedString.to(p.getCodec().readValue(p, String.class));
    }
}