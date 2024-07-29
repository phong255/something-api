package dev.lhphong.somethingapi.Config.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lhphong.somethingapi.Config.Constant.ServiceErrors;
import dev.lhphong.somethingapi.Config.Response.ServiceResponse;
import dev.lhphong.somethingapi.Config.Util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Date;
@Slf4j
@ControllerAdvice
@Order(0)
public class ResponseFilter implements ResponseBodyAdvice<Object> {
    private final HttpServletRequest httpServletRequest;

    public ResponseFilter(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)
                ||
                methodParameter.getContainingClass().isAnnotationPresent(RestControllerAdvice.class)
        ) {
            if (o == null) o = new Object();

            if (o.getClass() != ServiceResponse.class) {
                o = new ServiceResponse(new Date(), ServiceErrors.SUCCESS, o);
            }
        }
        String data = "[RESPONSE]: " + HttpUtil.toString(o);
        log.info(data);
        return o;
    }
}
