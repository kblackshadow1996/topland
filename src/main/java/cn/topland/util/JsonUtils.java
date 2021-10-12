package cn.topland.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.core.JsonParser.Feature;

/**
 * Json序列化
 */
@Slf4j
public final class JsonUtils {

    private static final TypeReference<HashMap<String, Object>> HASH_MAP_TYPE = new TypeReference<>() {
    };

    private static final TypeReference<ArrayList<Object>> LIST_TYPE = new TypeReference<>() {
    };

    private static final ObjectMapper mapper = new ObjectMapper();

    static {

        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule());
    }

    public static <T> T parse(String json, Class<T> clazz) {
        try {

            return mapper.readValue(json, clazz);
        } catch (IOException e) {

            log.error("Cannot parse json: " + json + ", clazz is " + clazz, e);
            return null;
        }
    }

    public static <T> T parse(String json, TypeReference<T> type) {
        try {

            return mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).readValue(json, type);
        } catch (IOException e) {

            return null;
        }
    }

    public static Map<String, Object> parse(String json) {
        try {

            return mapper.readValue(json, HASH_MAP_TYPE);

        } catch (IOException var2) {

            return new HashMap<>(0);
        }
    }

    public static List<Object> parseObjects(String json) {
        try {

            return mapper.readValue(json, LIST_TYPE);
        } catch (IOException e) {

            return new ArrayList<>(0);
        }
    }

    public static JsonNode read(String json) {

        try {

            return mapper.readTree(json);
        } catch (IOException e) {

            return NullNode.instance;
        }
    }

    public static String toJson(Object object) {

        try {

            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

            return "{}";
        }
    }

    public static JsonNode toJsonNode(Object object) {

        return mapper.convertValue(object, JsonNode.class);
    }

    public static String toJson(Object object, TypeReference<?> type) {

        try {

            return mapper.writerFor(type).writeValueAsString(object);
        } catch (JsonProcessingException e) {

            return "{}";
        }
    }

    private JsonUtils() {
    }
}