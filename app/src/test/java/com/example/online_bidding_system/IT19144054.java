package com.example.online_bidding_system;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IT19144054 {


    public fashion_edit_page Fashion_edit;


    @Before
    public void setup() {
        Fashion_edit = new fashion_edit_page();
    }


    @Test
    public void testValidateTime() {


        boolean result = Fashion_edit.ValidateTime("21:06:00");
        assertEquals(true, result);

    }

}



