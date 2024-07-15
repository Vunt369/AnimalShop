package com.example.petshop.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateHelper {
    public static LocalDateTime parserFromDateString(String dateString) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                return LocalDateTime.parse(dateString, formatter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeDiff(LocalDateTime targetDate) {
        LocalDateTime currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now();
            Duration duration = Duration.between(targetDate, currentDateTime);
            Period period = Period.between(targetDate.toLocalDate(), currentDateTime.toLocalDate());

            long days = period.getDays();
            long months = period.getMonths();
            long years = period.getYears();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;
            if (years > 0) return years + "yrs";
            if (months > 0) return months + "mths";
            if (days > 0) return days + "days";
            if (hours > 0) return hours + "hrs";
            if (minutes > 0) return minutes + "mins";
        }
        return "now";
    }
}
