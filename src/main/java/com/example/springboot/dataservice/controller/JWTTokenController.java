package com.example.springboot.dataservice.controller;

import com.example.springboot.dataservice.model.Attendance;
import com.example.springboot.dataservice.model.Status;
import com.example.springboot.dataservice.securitymodels.JWTUser;
import com.example.springboot.dataservice.securityvalidator.JWTGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController

public class JWTTokenController {

    @Autowired
    JWTGenerator jwtGenerator;
    private static final Logger log = LoggerFactory
            .getLogger(JWTTokenController.class);


    @PostMapping(path ="/getToken", consumes="application/json"   ,produces = "application/json" )
    public String getToken(@RequestBody JWTUser jwtUser)
            throws IOException, InterruptedException {

        log.info("in getToken---->");


        return jwtGenerator.generate(jwtUser);
    }

}
