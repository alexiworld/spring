package com.example.springkmsmybatis2.config;

import com.example.springkmsmybatis2.model.EncrappedString;
import com.example.springkmsmybatis2.model.EncryptedString;
import com.example.springkmsmybatis2.serializer.EncrappedDeserializer;
import com.example.springkmsmybatis2.serializer.EncrappedSerializer;
import com.example.springkmsmybatis2.serializer.EncryptedDeserializer;
import com.example.springkmsmybatis2.serializer.EncryptedSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerializationConfig {

    @Bean
    @SuppressWarnings({"rawtypes", "unchecked"})
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(EncryptedString.class, new EncryptedSerializer());
        simpleModule.addDeserializer(EncryptedString.class, new EncryptedDeserializer());
        simpleModule.addSerializer(EncrappedString.class, new EncrappedSerializer());
        simpleModule.addDeserializer(EncrappedString.class, new EncrappedDeserializer());
        mapper.registerModule(simpleModule);
        return mapper;
    }

}