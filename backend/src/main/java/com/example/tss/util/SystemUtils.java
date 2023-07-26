package com.example.tss.util;

import java.sql.Timestamp;

public class SystemUtils {
    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
