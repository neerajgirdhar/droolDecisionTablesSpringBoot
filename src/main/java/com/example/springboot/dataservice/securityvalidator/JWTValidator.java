package com.example.springboot.dataservice.securityvalidator;

import com.example.springboot.dataservice.securitymodels.JWTUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JWTValidator {
    @Autowired
    JWTLoader jwtLoader;
    String secretKey = "jwtexample";

    public JWTUser validate(String token) {

        JWTUser jwtUser = null;
        List<String> jwtCompleteList = jwtLoader.loadCredentialAndCreatJWT();
        if (jwtCompleteList.contains(token)) {

            try {
                Claims claim = Jwts.parser().setSigningKey(secretKey)
                        .parseClaimsJws(token).getBody();




                System.out.println(claim.getSubject());
                System.out.println((String) claim.get("userId"));
                System.out.println((String) claim.get("role"));

                jwtUser = new JWTUser();
                jwtUser.setUserName(claim.getSubject());
                jwtUser.setUserId(Long.parseLong((String) claim.get("userId")));
                jwtUser.setRole((String) claim.get("role"));
            } catch (Exception e) {
                System.out.println("Exception--->" + e);
            }


        } else {
            System.out.println("Token passed in request is not generated with right information");
            throw new RuntimeException("Token Sent is not valid and is not in the list of specified tokens");
        }
        return jwtUser;
    }
}

