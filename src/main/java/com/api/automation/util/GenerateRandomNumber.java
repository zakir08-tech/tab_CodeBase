package com.api.automation.util;
 
import java.util.Random;
 
public class GenerateRandomNumber {
	public static int generateRandomNumber(int rangeMax, int rangeMin){
	    Random randomNumer = new Random();
	    int getNewTransID = randomNumer.nextInt((rangeMax - rangeMin) + 1) + rangeMin;
	    return getNewTransID;
	}
}