package ru.ilyshkafox.jolt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String escapeJson(Object arg) {
        try {
            return objectMapper.writeValueAsString(arg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Object unescapeJson(String arg) {
        try {
            return objectMapper.readValue((String) arg, Object.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
