package dev.lhphong.somethingapi.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface JwtService {
    public String generateToken(UserDetails userDetails);
    public String extractUsername(String token);
    public boolean isTokenValid(String token,UserDetails userDetails);
}
