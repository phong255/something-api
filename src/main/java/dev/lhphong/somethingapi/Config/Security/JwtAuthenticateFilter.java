package dev.lhphong.somethingapi.Config.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lhphong.somethingapi.Config.Constant.ServiceError;
import dev.lhphong.somethingapi.Config.Response.ServiceResponse;
import dev.lhphong.somethingapi.Config.Util.HttpUtil;
import dev.lhphong.somethingapi.Config.exception.AccessDenyException;
import dev.lhphong.somethingapi.Services.JwtService;
import dev.lhphong.somethingapi.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticateFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(path.contains("home")){
            filterChain.doFilter(request,response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try{
            if(!StringUtils.isEmpty(authHeader) && StringUtils.startsWith(authHeader,"Bearer ")){
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
        }catch (AccessDenyException exception){
            log.error(String.format("AccessDenyException: %s", exception.getMessage()), exception);
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(
                HttpUtil.toString(
                        new ServiceResponse(
                                new Date(),
                                new ServiceError(HttpStatus.FORBIDDEN.value(),exception.getMessage())
                        )
                )
            );
            return;
        }catch (Exception ex){
            log.error(String.format("Exception: %s", ex.getMessage()), ex);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(
                    HttpUtil.toString(
                            new ServiceResponse(
                                    new Date(),
                                    new ServiceError(HttpStatus.BAD_REQUEST.value(),ex.getMessage())
                            )
                    )
            );
            return;
        }

        if(StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
            if(userDetails == null){
                log.error("Phiên đăng nhập hết hạn.");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().println(
                        HttpUtil.toString(
                                new ServiceResponse(
                                        new Date(),
                                        new ServiceError(HttpStatus.BAD_REQUEST.value(),"Phiên đăng nhập hết hạn.")
                                )
                        )
                );
                return;
            }

            //---- check valid token -----
            if(jwtService.isTokenValid(token,userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken access_token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                access_token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(access_token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
