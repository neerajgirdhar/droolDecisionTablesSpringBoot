package com.example.springboot.dataservice.model;

import lombok.Data;
import org.springframework.util.StringUtils;


@Data
public class Attendance {

    private String empID;
    private int countOfUnknownStatusForThreeHours;
    private int band;
    private int status;

    public boolean checkBand(int band)
    {
        boolean valid_band= false;

        if(this.band==band)
        {
            valid_band =true;
        }
    return valid_band;
    }


}
