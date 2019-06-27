package com.example.springboot.dataservice.controller;

import java.io.IOException;

import com.example.springboot.dataservice.manager.DroolManager;
import com.example.springboot.dataservice.model.Attendance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

@RestController
public class DroolController {

    @Autowired
    DroolManager droolManager;

    private static final Logger log = LoggerFactory
            .getLogger(DroolController.class);


   @PostMapping(path ="/checkAttendance", consumes="application/json"   ,produces = "application/json" )
    public String checkAttendance(@RequestBody Attendance attendance)
            throws IOException, InterruptedException {

        log.info("in checkAttendance---->");

        return droolManager.check(attendance);
    }
}
