package com.strongexplorers.schedulemanagement.activities.com.strongexplorers.schedulemanagement.utils;

import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class HelperUtils {
    public static String getDateString(long dateInMilli) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Log.e("HelperUtilsget: ", ""+format);
        return format.format(new Date(dateInMilli));
    }
}
