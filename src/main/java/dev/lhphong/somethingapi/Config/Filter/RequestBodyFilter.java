package dev.lhphong.somethingapi.Config.Filter;

import dev.lhphong.somethingapi.Config.Util.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

@ControllerAdvice
@Slf4j
@Order(0)
@AllArgsConstructor
public class RequestBodyFilter extends RequestBodyAdviceAdapter {

    private final HttpServletRequest request;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        String data = "[BODY]: " + HttpUtil.toString(body);
        log.info(data);
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    private String getRemoteIp(HttpServletRequest httpServletRequest) {
        String remoteIp = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(remoteIp)) {
            return httpServletRequest.getRemoteAddr();
        }
        return remoteIp;
    }
}
