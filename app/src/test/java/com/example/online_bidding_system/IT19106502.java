package com.example.online_bidding_system;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IT19106502 {

    public RegistrationPage registrationPage;

    @Before
    public  void setup(){
        registrationPage = new RegistrationPage();
    }

//IT19106502- using unit testing.
    @Test
    public void testValidateNIC(){
        boolean result = registrationPage.ValidateNIC("990293007V");
        assertEquals(true ,result);
    }



}
