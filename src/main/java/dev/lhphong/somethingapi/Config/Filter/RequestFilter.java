package dev.lhphong.somethingapi.Config.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Order(-1)
@Slf4j
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        long begin = System.currentTimeMillis();

        String requestInfoBuilder = "[IP]: " + getRemoteIp(httpServletRequest) +
                ", [" + httpServletRequest.getMethod() + "]" +
                ", [URI]: " + httpServletRequest.getRequestURI() +
                ", [QUERY]: " + httpServletRequest.getQueryString();
        log.info(requestInfoBuilder);

        filterChain.doFilter(servletRequest, servletResponse);

        String responseInfoBuilder = "[IN]: " + (System.currentTimeMillis() - begin) + " ms";
        log.info(responseInfoBuilder);
    }

    private String getRemoteIp(HttpServletRequest httpServletRequest) {
        String remoteIp = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(remoteIp)) {
            return httpServletRequest.getRemoteAddr();
        }
        return remoteIp;
    }
}
