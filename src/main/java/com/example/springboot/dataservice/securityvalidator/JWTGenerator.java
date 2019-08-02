package com.example.springboot.dataservice.securityvalidator;

import com.example.springboot.dataservice.securitymodels.JWTUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {

    public String generate(JWTUser jwtUser)
    {

        Claims claim = Jwts.claims().setSubject(jwtUser.getUserName());
        claim.put("userId",String.valueOf(jwtUser.getUserId()));
        claim.put("role",jwtUser.getRole());

        return  Jwts.builder()
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS512,"jwtexample")
                .compact();

    }
}
