package com.example.springboot.dataservice.controller;

import com.example.springboot.dataservice.model.Attendance;
import com.example.springboot.dataservice.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BasicController {

    private static final Logger log = LoggerFactory
            .getLogger(BasicController.class);

    @GetMapping(path ="/secure/authenticateWithJWT")
    public String authenticateWithJWT()
            throws IOException, InterruptedException {

        log.info("in authenticateWithJWT---->");

        return "You have validated the JWT Token";
    }
}
