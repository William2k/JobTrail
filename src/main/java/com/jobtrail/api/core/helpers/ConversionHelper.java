package com.jobtrail.api.core.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversionHelper {
    public static String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> type) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, type);
    }
}
