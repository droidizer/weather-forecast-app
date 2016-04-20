package com.codechallenge.app.utils;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by greymatter on 20/04/16.
 */
public class AppUtils {

    public static String formatDate(@NonNull final SimpleDateFormat sdf, @NonNull final long position) {

        final Date date = new Date(position * 1000L); // *1000 is to convert seconds to milliseconds
        sdf.setTimeZone(TimeZone.getTimeZone("CET")); // Converting UTC to CET Time Zone
        return sdf.format(date);
    }

    public static int kelvinToCelcius(@NonNull final double temperature) {
        return (int)((temperature) - 273.15); // Temperature metric conversion
    }

}
