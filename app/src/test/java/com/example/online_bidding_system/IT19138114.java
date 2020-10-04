package com.example.online_bidding_system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IT19138114 {


    public Antiques_Edit antiques_edit;

    @Before
    public  void setup(){
        antiques_edit = new Antiques_Edit();
    }

    @Test
    public void testValidateDate(){
        boolean result = antiques_edit.ValidateDate("2020-10-12");
        assertEquals(true ,result);
    }

}
