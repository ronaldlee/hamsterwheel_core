package com.hamsterwheel.util;

public class TimeUtil {
	
	static public String formatMillis(long millis) {
		StringBuilder    buf=new StringBuilder(12);
		String           tmp;
		
		if(millis<0) { buf.append('-'); millis=Math.abs(millis); }
		tmp=("0" + (millis/3600000));          buf.append((tmp.length()>2) ? tmp.substring(1) : tmp);
		buf.append(":");
		tmp=("0" + ((millis%3600000)/60000));  buf.append(tmp.substring(tmp.length()-2));
		buf.append(":");
		tmp=("0" + ((millis%60000)/1000));     buf.append(tmp.substring(tmp.length()-2));
		buf.append(".");
		tmp=("00" + (millis%1000));            buf.append(tmp.substring(tmp.length()-3));
		return buf.toString();
	}
	
	static public String formatMillisNice(long millis) {
		StringBuilder buf=new StringBuilder();
		String tmp;
		
		if (millis<0) { 
			buf.append('-'); millis=Math.abs(millis); 
		}
		
		long hr = millis/3600000;
		if (hr > 0) {
			tmp = "0" + hr;          
			buf.append((tmp.length()>2) ? tmp.substring(1) : tmp);
			buf.append(" hr");
		}
		
		long min = (millis%3600000)/60000;
		
		if (min > 0) {
			tmp="0" + min;  
			buf.append(tmp.substring(tmp.length()-2));
			buf.append(" mins");
		}
		
		long secs = (millis%60000)/1000;
		if (secs > 0) {
			tmp="0" + secs;     
			buf.append(tmp.substring(tmp.length()-2));
			buf.append(" secs");
		}
		
		return buf.toString();
	}
}
