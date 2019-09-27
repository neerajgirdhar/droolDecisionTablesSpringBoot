package com.example.springboot.dataservice.controller;

import java.io.IOException;

import com.example.springboot.dataservice.model.CRR;
import com.example.springboot.dataservice.model.Rating;
import com.example.springboot.dataservice.model.Status;
import com.example.springboot.dataservice.manager.DroolManager;
import com.example.springboot.dataservice.model.Attendance;
import com.example.springboot.dataservice.securitymodels.JWTToken;
import com.example.springboot.dataservice.securitymodels.JWTUser;
import com.example.springboot.dataservice.securitymodels.UserSession;
import com.example.springboot.dataservice.securityvalidator.JWTGenerator;
import com.example.springboot.dataservice.securityvalidator.JWTLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
/*@CrossOrigin( maxAge = 3600)*/
@RestController
public class DroolController {

    @Autowired
    DroolManager droolManager;
    @Autowired
    JWTLoader jwtLoader;
    @Autowired
    JWTGenerator jwtGenerator;

    private static final Logger log = LoggerFactory
            .getLogger(DroolController.class);


   @PostMapping(path ="/checkAttendance", consumes="application/json"   ,produces = "application/json" )
    public Status checkAttendance(@RequestBody Attendance attendance)
            throws IOException, InterruptedException {

        log.info("in checkAttendance---->");
      Status status = new Status();
         status.setStatus(droolManager.check(attendance));
       return status;
    }

    @PostMapping(path ="/secure/checkCRR", consumes="application/json"   ,produces = "application/json" )
    public Rating checkCRR(@RequestBody CRR crr)
            throws IOException, InterruptedException {
        System.out.println("in checkCRR---->"+crr.toString());
        log.info("in checkCRR---->"+crr.toString());
        Rating rating = new Rating();
        rating.setRating(droolManager.checkCRR(crr));
        return rating;
    }




    @PostMapping(path ="/login", consumes="application/json"   ,produces = "application/json" )
    public UserSession login(@RequestBody JWTUser jwtUser)
            throws IOException, InterruptedException {

        log.info("in login---->");
        UserSession userSession = new UserSession();
        userSession.setLoggedIn("false");
        userSession.setUserId(jwtUser.getUserId());
        userSession.setUserName(jwtUser.getUserName());
        userSession.setRole(jwtUser.getRole());
       //
        if (jwtLoader.loadCredentialAndCreatJWT()
                .contains(jwtGenerator.generate(jwtUser))){
            userSession.setLoggedIn("true");
            userSession.setJwttoken(jwtGenerator.generate(jwtUser));
            userSession.setDesription("User is Autheticated Succesfully.");
    }else{
            userSession.setLoggedIn("false");
            userSession.setJwttoken("");
            userSession.setDesription("User Information Passed is not Valid.");
        }
        return userSession;

    }


    @PostMapping(path ="/validate", consumes="application/json"   ,produces = "application/json" )
    public UserSession getToken(@RequestBody JWTToken jwtToken)
            throws IOException, InterruptedException {

        log.info("in validateToken ---->");
        UserSession userSession = new UserSession();
        userSession.setLoggedIn("false");

        //
        if (jwtLoader.loadCredentialAndCreatJWT()
                .contains(jwtToken.getJwttoken())){
            userSession.setLoggedIn("true");
            userSession.setJwttoken(jwtToken.getJwttoken());
            userSession.setDesription("User is Autheticated Succesfully.");
        }else{
            userSession.setLoggedIn("false");
            userSession.setJwttoken("");
            userSession.setDesription("User Information Passed is not Valid.");
        }
        return userSession;

    }
}
