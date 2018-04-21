package com.oluwatayo.apps.medmanager.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static long getDateInMilliseconds(String dateString){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}
