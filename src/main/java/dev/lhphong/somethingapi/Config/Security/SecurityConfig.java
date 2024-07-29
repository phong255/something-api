package dev.lhphong.somethingapi.Config.Security;

import dev.lhphong.somethingapi.Config.Constant.RoleConstant;
import dev.lhphong.somethingapi.Services.RoleService;
import dev.lhphong.somethingapi.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticateFilter jwtAuthenticateFilter;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/v1/home/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasAnyAuthority(RoleConstant.EMPLOYEE,RoleConstant.MANAGER)
                                .requestMatchers("/api/v1/client/**").hasAnyAuthority(RoleConstant.CLIENT)
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterAfter(
                        jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userService.userDetailsService());
        daoAuth.setPasswordEncoder(passwordEncoder());
        return daoAuth;
    }

    //-------- Config encoder password -------
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
