package dev.lhphong.somethingapi.Services.Impl;

import dev.lhphong.somethingapi.Services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;
@Service
public class JwtServiceImpl implements JwtService {
    private final String SecretKey = "0bca2d204e8f7e596f361f881487206a96cbe9590333d195f8e487abf28c9c62";
    @Override
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SecretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SecretKey.getBytes())).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = userDetails.getUsername();
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }
}
