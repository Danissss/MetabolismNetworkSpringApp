package com.example.MetabolismNetwork.Helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintStatus {
	
	/**
	 * print status time 
	 * @param Task
	 */
	public static void PrintStatusMessage(String Task, String query) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
		LocalDateTime ldt = LocalDateTime.now();  
		String now = dtf.format(ldt);
		
		
		System.out.println(String.format("========== %s was running %s at time => %s ==============", Task, query, now));
		
	}
}
