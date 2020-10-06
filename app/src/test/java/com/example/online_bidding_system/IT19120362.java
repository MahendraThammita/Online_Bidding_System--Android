package com.example.online_bidding_system;

import com.example.online_bidding_system.HelperClasser.BiddingAdapters.TimeCalculations;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class IT19120362 {

    private TimeCalculations timeCalculations;

    @Before
    public  void setup(){
        timeCalculations = new TimeCalculations("14:26:00" , "2020-10-02");
    }

    //IT19120362
    //This test case Tests the time and date given in initializing the class with current time and date
    //and the calculate the dime difference with given date time with the current datetime
    //hence output can be vary with the current dateTime
    @Test
    public void test_calcTimeDifInMIn_Hrs_Str(){
        String result = timeCalculations.calcTimeDifInMIn_Hrs_Str();
        assertEquals("15:58" ,result);
    }

    //IT19120362
    //This method checks the difference with given time equals or greater than to five hours.
    //if it less than five hours it returns false and if it is greater than five hours it will return true
    //This method also will depend on the current dateTime
    @Test
    public void test_isDeleteable(){
        Boolean result = timeCalculations.isDeleteable();
        assertEquals(false ,result);
    }

    //IT19120362
    //this method checks a given date is before the actual date and if it id before
    //this wull return true and otherwise it will return false
    @Test
    public void test_isExpired(){
        Boolean result = timeCalculations.isDeleteable();
        assertEquals(false ,result);
    }

    //IT19120362
    //this function gets two Local date string and alocal time string and concatinate into one local date time object
    @Test
    public void test_calcFinalDateTime(){
        String  result = timeCalculations.calcFinalDateTime();
        assertEquals("2020-10-02T14:26" ,result);
    }



}
