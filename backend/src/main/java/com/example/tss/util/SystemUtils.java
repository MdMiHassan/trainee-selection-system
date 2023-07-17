package com.example.tss.util;

import java.sql.Date;
import java.sql.Timestamp;

public class SystemUtils {
    public static Timestamp getCurrentTimeStamp(){
       return new Timestamp(System.currentTimeMillis());
    }
    public static Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }
}
