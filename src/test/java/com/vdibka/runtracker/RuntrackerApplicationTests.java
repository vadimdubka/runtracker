package com.vdibka.runtracker;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RuntrackerApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	public static void main(String[] args) {
        Date dateToConvert = new Date();
        Date date = new Date(dateToConvert.getTime());
        System.out.println(dateToConvert);
        System.out.println(date);
    }
    
    private static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
    
}


