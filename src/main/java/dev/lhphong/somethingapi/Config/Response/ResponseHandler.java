package dev.lhphong.somethingapi.Config.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler{
    public static ResponseEntity<Object> responseBuilder(
            String message,
            HttpStatus httpStatus,
            Object data
    ){
        Map<String , Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", httpStatus);
        response.put("data", data);
        return new ResponseEntity<>(response,httpStatus);
    }
}
