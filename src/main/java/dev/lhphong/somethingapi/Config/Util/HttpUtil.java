package dev.lhphong.somethingapi.Config.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {
    private String value;

    private HttpUtil(String value){
        this.value = value;
    }
    public static HttpUtil of(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line ;
        while((line = bufferedReader.readLine()) != null)
            sb.append(line);
        return new HttpUtil(sb.toString());
    }

    public <T> T convertTo(Class<T> tClass) throws IOException {
        return new ObjectMapper().readValue(value.getBytes(),tClass);
    }

    public static String toString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
