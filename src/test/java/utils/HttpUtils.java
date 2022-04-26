package utils;

import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class HttpUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T convertMvcResult(MvcResult mvcResult, Class<T> obj) throws IOException {
        String string = mvcResult.getResponse().getContentAsString();
        return OBJECT_MAPPER.readValue(string, obj);
    }
}