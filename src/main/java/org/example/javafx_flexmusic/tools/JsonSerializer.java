package org.example.javafx_flexmusic.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T deserialize(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }
}
