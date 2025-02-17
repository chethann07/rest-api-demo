package com.restapi.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JavaUtil {

    private static ObjectMapper mapper;

    @Autowired
    public JavaUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static <T> T Deserialization(String requestBody, Class<T> InputClass){
        try{
            return mapper.readValue(requestBody, InputClass);
        }catch (Exception e){
            throw new RuntimeException("Failed to Deserialize", e);
        }
    }

    public static <T> T Deserialization(Object inputObject, Class<T> InputClass){
        try{
            return mapper.convertValue(inputObject, InputClass);
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Failed to Deserialize", e);
        }
    }

    public static String Serialization(Object inputObject){
        try{
            return mapper.writeValueAsString(inputObject);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to Serialize", e);
        }
    }
}
