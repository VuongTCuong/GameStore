package com.gamestore.gamestore.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gamestore.gamestore.entity.User;
import com.gamestore.gamestore.exception.MainErrorException;
import com.gamestore.gamestore.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;



@Service
public class JwtService {
    
    @Value("${jwt.secretkey}")
    private String secretKey;

    
    public String generateToken(String account){
        Map<String,Object> claims = new HashMap<>();       
        return Jwts.builder()
                   .addClaims(claims)
                   .setSubject(account)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                   .signWith(getKey())
                   .compact();
    }
    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

   
    public boolean validateToken(String token, UserDetails userDetails){
        String account = extractUsername(token);
        return (account.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken_v2(String token, UserRepository userRepo){
        String account = extractUsername(token);

        User user = userRepo.findByAccount(account).orElseThrow(()-> new MainErrorException("Không tìm thấy người dùng"));
        return (account.equals(user.getAccount()) && !isTokenExpired(token)); 
    }


    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
    
}
