package com.example.springboot.dataservice.security;

import com.example.springboot.dataservice.securitymodels.JWTAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter  extends AbstractAuthenticationProcessingFilter {


    public JWTFilter() {
        super("/secure/**");
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {


        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if ("OPTIONS".equals(request.getMethod())) {
            System.out.println("Browser Request--->");
            response.setStatus(HttpServletResponse.SC_OK);
            return null;
        }else {
            String header = request.getHeader("Authorization");
            System.out.println("header---> " + header);
            if (header == null || !header.startsWith("Token ")) {
                throw new RuntimeException("Token is missing");

            }
            String actualJWTToken = header.substring(6);
            System.out.println("---------->" + actualJWTToken);
            JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(actualJWTToken);
            return getAuthenticationManager().authenticate(jwtAuthenticationToken);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request,response);

    }


}
