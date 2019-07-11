package com.example.springboot.dataservice;

import static org.junit.Assert.assertTrue;

import com.example.springboot.dataservice.model.Attendance;
import com.example.springboot.dataservice.model.Status;
import com.example.springboot.job.SpringBootJobTwo;
import org.junit.Test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes={SpringBootJobTwo.class})
public class AppTest 
{
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void checkBand4RaiseToDM() throws Exception

    {

        Attendance requestVO = new Attendance();
        requestVO.setEmpID("676003");
        requestVO.setCountOfUnknownStatusForThreeHours(7);
        requestVO.setBand(4);

        String baseUrl = "http://localhost:" + randomServerPort + "/checkAttendance";
        URI uri = new URI(baseUrl);
        ResponseEntity<Status> resp =
                restTemplate.postForEntity(uri,requestVO, Status.class);
//Verify request succeed
        Assert.assertEquals(200, resp.getStatusCodeValue());

        Assert.assertEquals("Dear Employee your more than 3 hours exits in last 90 days is more than 5.Email will be sent to your DM and HR",resp.getBody().getStatus());

    }


    @Test
    public void checkBand4RaiseToManager() throws Exception

    {

        Attendance requestVO = new Attendance();
        requestVO.setEmpID("676003");
        requestVO.setCountOfUnknownStatusForThreeHours(4);
        requestVO.setBand(4);

        String baseUrl = "http://localhost:" + randomServerPort + "/checkAttendance";
        URI uri = new URI(baseUrl);
        ResponseEntity<Status> resp =
                restTemplate.postForEntity(uri,requestVO, Status.class);
//Verify request succeed
        Assert.assertEquals(200, resp.getStatusCodeValue());

        Assert.assertEquals("Dear Employee your more than 3 hours exits in last 90 days is more than 3.Email will be sent to your manager",resp.getBody().getStatus());

    }
    
    @Test
    public void checkBand4Good() throws Exception
    {

        Attendance requestVO = new Attendance();
        requestVO.setEmpID("676003");
        requestVO.setCountOfUnknownStatusForThreeHours(2);
        requestVO.setBand(4);

        String baseUrl = "http://localhost:" + randomServerPort + "/checkAttendance";
        URI uri = new URI(baseUrl);
        ResponseEntity<Status> resp =
                restTemplate.postForEntity(uri,requestVO, Status.class);
//Verify request succeed
        Assert.assertEquals(200, resp.getStatusCodeValue());

        Assert.assertEquals("Great your more than 3 hours exits in last 90 days is with in Limits",resp.getBody().getStatus());

    }


}
