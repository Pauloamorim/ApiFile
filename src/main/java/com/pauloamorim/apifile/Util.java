package com.pauloamorim.apifile;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

	public static BigDecimal convertNanoToSeconds(long start,long end){
		return new BigDecimal((end - start) / 1000000000.0).setScale(2, RoundingMode.HALF_UP);
	}
	
}
