package com.bet99.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static String getCurrentISODate() {
        return ms2OffsetDateTime(System.currentTimeMillis()).format(
                ISO_DATE);
    }

    public static OffsetDateTime ms2OffsetDateTime(final long ms) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(ms),
                ZoneId.systemDefault());
    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(Constants.TODO_DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
