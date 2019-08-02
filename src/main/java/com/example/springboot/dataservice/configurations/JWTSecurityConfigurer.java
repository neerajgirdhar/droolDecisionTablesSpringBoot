package com.example.springboot.dataservice.configurations;

import com.example.springboot.dataservice.security.AutheticationFailureEntryPoint;
import com.example.springboot.dataservice.security.CustomAuthenticationProvider;
import com.example.springboot.dataservice.security.JWTAutheticationSuccesfulHandler;
import com.example.springboot.dataservice.security.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JWTSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    CustomAuthenticationProvider authenticationProvider;
    @Autowired
    AutheticationFailureEntryPoint autheticationFailureEntryPoint;

    @Bean
    public AuthenticationManager getCustomAuthenticationManager(){

        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }


    @Bean
    public JWTFilter getJWTFilter(){
        JWTFilter filter  = new JWTFilter();
        filter.setAuthenticationManager(getCustomAuthenticationManager());
        filter.setAuthenticationSuccessHandler(new JWTAutheticationSuccesfulHandler());

        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
             authorizeRequests().antMatchers("**/secure/**").authenticated().
                and().
                exceptionHandling().authenticationEntryPoint(autheticationFailureEntryPoint).
                and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(getJWTFilter(), UsernamePasswordAuthenticationFilter.class);
   http.headers().cacheControl();
    }
}
