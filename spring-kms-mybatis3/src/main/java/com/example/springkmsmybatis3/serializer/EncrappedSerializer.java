package com.example.springkmsmybatis3.serializer;

import com.example.springkmsmybatis3.model.EncrappedString;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class EncrappedSerializer extends StdSerializer<EncrappedString> {

    protected EncrappedSerializer(Class<EncrappedString> t) {
        super(t);
    }

    public EncrappedSerializer() {
        this(null);
    }

    @Override
    public void serialize(EncrappedString encryptedString, JsonGenerator gen, SerializerProvider provider) throws IOException {
        //gen.writeStartObject();
        gen.writeString(encryptedString.toString());
    }
}