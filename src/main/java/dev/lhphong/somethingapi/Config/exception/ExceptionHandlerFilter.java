package dev.lhphong.somethingapi.Config.exception;

import dev.lhphong.somethingapi.Config.Constant.ServiceErrors;
import dev.lhphong.somethingapi.Config.Response.ServiceResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerFilter {

    @Resource
    private HttpServletRequest request;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServiceResponse> handlerException(Exception exception){
        log.error(String.format("Exception: %s", exception.getMessage()), exception);
        return ResponseEntity.ok(new ServiceResponse(new Date(), ServiceErrors.ERROR));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ServiceResponse> handlerException(CustomException exception){
        log.error(String.format("CustomException: %s", exception.getMessage()), exception);
        return ResponseEntity.ok(new ServiceResponse(new Date(), ServiceErrors.ERROR));
    }

    @ExceptionHandler(AccessDenyException.class)
    public ResponseEntity<ServiceResponse> handlerException(AccessDenyException exception){
        log.error(String.format("AccessDenyException: %s", exception.getMessage()), exception);
        return ResponseEntity.ok(new ServiceResponse(new Date(), ServiceErrors.BAD_REQUEST));
    }
}
