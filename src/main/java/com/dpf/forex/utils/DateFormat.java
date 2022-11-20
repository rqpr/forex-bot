package com.dpf.forex.utils;

public class DateFormat {
    public static String format(String date){
        String[] split = date.substring(0, 10).split("\\.");
        return split[0] + "-" + split[1] + "-" + split[2] + " " + date.substring(11, 18);
    }
}
