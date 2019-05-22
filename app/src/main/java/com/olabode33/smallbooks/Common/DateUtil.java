package com.olabode33.smallbooks.Common;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final String TAG = "DateUtil";
    private Date convertedToDate;

    private SimpleDateFormat fullDateStringFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    private SimpleDateFormat slimDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
    private SimpleDateFormat noDaySlimDateFormate = new SimpleDateFormat("MMM dd, yyyy");
    private SimpleDateFormat dayTimeDateFormat = new SimpleDateFormat("hh:mm:ss a");

    public DateUtil(Date fullDate) {
        try {
            this.convertedToDate = convertedToDate;
        } catch (Exception e){
            Log.d(TAG, "Error Create DateUtil Object: " + e.getMessage());
        }
    }

    public String getSlimDateFormat() {
        String slimDate = slimDateFormat.format(convertedToDate);

        return slimDate;
    }

    public String getDayTimeDateFormat() {
        String dayTime = dayTimeDateFormat.format(convertedToDate);

        return dayTime;
    }

    public String getNoDaySlimDateFormat(){
        String noDay = noDaySlimDateFormate.format(convertedToDate);
        return noDay;
    }

    public static String getCurrentDateDefaultFormat(){
        String currentDate = defaultDateFormat.format(new Date());
        return currentDate;
    }
}
