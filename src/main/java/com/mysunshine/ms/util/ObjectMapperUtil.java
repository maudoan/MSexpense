package com.mysunshine.ms.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectMapperUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtil.class);

    private ObjectMapperUtil() {
    }

    public static <T> T objectMapper(String json, Class<?> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object o = mapper.readValue(json, type);
            return (T) o;
        } catch (Exception var4) {
            var4.printStackTrace();
            logger.error(var4.toString());
            return null;
        }
    }

    public static <T> List<T> listMapper(String json, Class<?> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Object> o = (List)mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, type));
            return (List<T>) o;
        } catch (Exception var4) {
            logger.error(var4.getMessage(), var4);
            return Collections.emptyList();
        }
    }

    public static <T> String toJsonString(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception var2) {
            logger.error(var2.getMessage(), var2);
            return "";
        }
    }
}
