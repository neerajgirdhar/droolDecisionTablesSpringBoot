package com.example.springboot.dataservice.security;

import com.example.springboot.dataservice.securitymodels.JWTAuthenticationToken;
import com.example.springboot.dataservice.securitymodels.JWTUser;
import com.example.springboot.dataservice.securitymodels.JWTUserDetailAsPerSpringFramework;
import com.example.springboot.dataservice.securityvalidator.JWTValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JWTValidator jwtValidator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {


        JWTAuthenticationToken convertTokenIntoJWT = (JWTAuthenticationToken)authentication;
        String tokenString =  convertTokenIntoJWT.getToken();
        System.out.println("Token String--->> "+tokenString);
        JWTUser user = jwtValidator.validate(tokenString);
        if(user ==null)
        {
            throw new RuntimeException("Invalid JWT token");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
        JWTUserDetailAsPerSpringFramework jwtUserDetailAsPerSpringFramework =  new JWTUserDetailAsPerSpringFramework(user.getUserName(),user.getUserId(),tokenString,authorities);
        return jwtUserDetailAsPerSpringFramework;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
