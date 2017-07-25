package com.pauloamorim.apifile;

public class Util {

	public static double convertNanoToSeconds(long start,long end){
		return (double)(end - start) / 1000000000.0;
	}
	
}
