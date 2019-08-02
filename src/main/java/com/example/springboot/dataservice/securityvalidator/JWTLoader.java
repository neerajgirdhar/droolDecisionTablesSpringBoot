package com.example.springboot.dataservice.securityvalidator;

import com.example.springboot.dataservice.securitymodels.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class JWTLoader {
    @Autowired
    JWTGenerator jwtGenerator;
    @PostConstruct
    public List<String> loadCredentialAndCreatJWT()
    {
        List<String> jwtList =new ArrayList();


        String csvFile = "D://SecurityUser//user.csv";

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] user = line.split(cvsSplitBy);

                JWTUser preloadedUser =    new JWTUser();
                preloadedUser.setUserName(user[0]);
                preloadedUser.setUserId(Long.parseLong(user[1]));
                        preloadedUser.setRole(user[2]);
                jwtList.add(jwtGenerator.generate(preloadedUser));

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jwtList;
    }
}
