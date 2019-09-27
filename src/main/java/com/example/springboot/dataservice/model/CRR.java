package com.example.springboot.dataservice.model;

import lombok.Data;

@Data
public class CRR {

    private String empID;
    private String empName;
    private String unit;
    private int jobLevel;
    private int countPOC;
    private String descriptionPOC;
    private int countInterviewDrives;
    private String descriptionInterviewDrives;
    private int countRFP;
    private String descriptionRFP;
    private int countSessionTaken;
    private String descriptionSessionTaken;
    private int countClientAppreciation;
    private String descriptionClientAppreciation;
    private int countBlogs;
    private String descriptionBlogs;
    private int countTrainingCertification;
    private String descriptionTrainingCertification;
    private int countDealsWon;
    private String descriptionDealsWon;
    private int dealsWonWorthInMillion;
    private boolean escalation;
    private int rating;
    private int score;

    public boolean checkBand(int jobLevel) {
        boolean valid_jobLevel = false;

        if (this.jobLevel == jobLevel) {
            valid_jobLevel = true;
        }
        return valid_jobLevel;
    }

    public int checkBand4Criteria() {
        int criteria = 0;
        if (this.escalation) {
            return criteria;
        } else {

            criteria = criteria + countTrainingCertification + countClientAppreciation;
        }
        this.score = criteria;
        return criteria;
    }


    public int checkBand5Criteria() {
        int criteria = 0;
        if (this.escalation) {
            return criteria;
        } else {

            criteria = criteria + checkBand4Criteria() + countInterviewDrives + countPOC;
        }
        this.score = criteria;
        return criteria;
    }

    public int checkBand6Criteria() {
        int criteria = 0;
        if (this.escalation) {
            return criteria;
        } else {

            criteria = criteria + checkBand5Criteria() + countRFP + countSessionTaken + countBlogs;
        }
        this.score = criteria;
        return criteria;
    }

    public int checkBand7Criteria() {
        int criteria = 0;
        if (this.escalation) {
            return criteria;
        } else {

            criteria = criteria + checkBand6Criteria() + countDealsWon;
            if (dealsWonWorthInMillion <= 5) {
                criteria = criteria + 1;
            }
            if (dealsWonWorthInMillion > 5 && dealsWonWorthInMillion <= 12) {
                criteria = criteria + 2;
            }
            if (dealsWonWorthInMillion > 12) {
                criteria = criteria + 3;
            }
            this.score = criteria;
            return criteria;
        }

    }
}
/*

{
	"empID":"676033",
    "empName":"Neeraj Girdhar",
	"unit":"FS-STAR",
	"jobLevel":"6",
    "countPOC":"2",
    "descriptionPOC":"Done POC on Dockers Kubernetes and Drools Decison Tables",
    "countInterviewDrives":"2",
    "descriptionInterviewDrives":"PArticiapted in 2 interview drives in for ADM and STAR",
    "countRFP":"1",
    "descriptionRFP":"HSBC RFP",
    "countSessionTaken":"0",
    "descriptionSessionTaken":"No Technical Session Taken",
    "countClientAppreciation":"2",
    "descriptionClientAppreciation":"Got client appreciation for FXIP and Risk Portfolio",
    "countBlogs":"0",
    "descriptionBlogs":"No blogs witten",
    "countTrainingCertification":"1",
    "descriptionTrainingCertification":"Done Training for Angular 7",
    "countDealsWon":"0",
    "descriptionDealsWon":"No Deals",
    "dealsWonWorth":"0",
    "escalation":"false",
	}
*/