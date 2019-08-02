package com.example.springboot.dataservice.manager;

import com.example.springboot.dataservice.model.Attendance;
import com.example.springboot.dataservice.model.CRR;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieContainerSessionsPool;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroolManager {


    KieContainerSessionsPool kieContainerSessionsPool;

    @PostConstruct
    public void init()
    {
        KieServices service  = KieServices.Factory.get();
        KieFileSystem kieFileSystem =  service.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("com/missingAttendance/rules/MissingMoreThan3Hours.xls"));

        kieFileSystem.write(ResourceFactory.newClassPathResource("com/crr/rules/CRR.xls"));


        KieBuilder kieBuilder = service.newKieBuilder(kieFileSystem).buildAll();
        if(kieBuilder.getResults().getMessages(Message.Level.ERROR).size()>0){
            List<Message> errors = kieBuilder.getResults().getMessages(Message.Level.ERROR);
           String error =  errors.stream().map(Message::getText).collect(Collectors.joining("\n"));
        throw new RuntimeException("Error Parsing rule"+error);
        }
        KieContainer container = service.newKieContainer(service.getRepository().getDefaultReleaseId());
        kieContainerSessionsPool = container.newKieSessionsPool(10);

    }

    public String check(Attendance attendance)
    {
        String message="OK";
        this.kieContainerSessionsPool.newStatelessKieSession().execute(attendance);
        if(attendance.getStatus()==0)
        {
            message ="--->Great your more than 3 hours exits in last 90 days is with in Limits";
        }
        if(attendance.getStatus()==1)
        {
            message ="Dear Employee your more than 3 hours exits in last 90 days is more than 3.Email will be sent to your manager";
        }
        if(attendance.getStatus()==2)
        {
            message ="Dear Employee your more than 3 hours exits in last 90 days is more than 5.Email will be sent to your DM and HR";
        }

        return message;
    }


    public String checkCRR(CRR crr)
    {
        String message="OK";
        this.kieContainerSessionsPool.newStatelessKieSession().execute(crr);


        if(crr.getRating()==0)
        {
            message ="Your Expected Rating is NeedsImprovement with the score "+crr.getScore();
        }
        if(crr.getRating()==1)
        {
            message ="Your Expected Rating is MetExpecation with the score "+crr.getScore();
        }
        if(crr.getRating()==2)
        {
            message ="Your Expected Rating is Commendable with the score "+crr.getScore();
        }

        if(crr.getRating()==3)
        {
            message ="Your Expected Rating is Outstanding with the score "+crr.getScore();
        }

        return message;
    }
}
