package com.jobtrail.api.core.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;

public class ConversionHelper {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void objectMapperInit() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> type) throws JsonProcessingException {
        return objectMapper.readValue(json, type);
    }

    public static <T> List<T> jsonToListObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<T>>(){});
    }

    public static String listToString(List<String> list, String delimiter) {
        StringBuilder sb = new StringBuilder();

        for(String str : list) {
            if(list.get(list.size() - 1) == str) {
                sb.append(str);
            } else {
                sb.append(str).append(delimiter).append(" ");
            }
        }

        return sb.toString();
    }
}
