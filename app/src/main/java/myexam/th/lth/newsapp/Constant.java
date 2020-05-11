package myexam.th.lth.newsapp;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

    public static final String API_KEY = "e6beeacd2f4fa4999f083bf53af5c5e6";
    public static Location LOCATION_CURRENT = null;

    public static String convertToDate(int dt){
        Date date = new Date( dt*1000L );
        SimpleDateFormat simple = new SimpleDateFormat( " EEEE, " );
        SimpleDateFormat ss1 = new SimpleDateFormat( " dd/MM/yyyy " );
        String format = simple.format( date );
        String formatDate = ss1.format( date );
        String days = format+"ngày"+formatDate;
        return days;
    }

    public static String getDateOfWeek(int dt){
        Date date = new Date( dt*1000L );
        SimpleDateFormat simple = new SimpleDateFormat( "EEEE" );
        String format = simple.format( date );
        return format;
    }

    public static String getDate(int dt){
        Date date = new Date( dt*1000L );
        SimpleDateFormat simple = new SimpleDateFormat( " dd/MM/yyyy " );
        String format = simple.format( date );
        String days = "Ngày"+format;
        return days;
    }

    public static String getSunrise(int dt){
        Date date = new Date( dt*1000L );
        SimpleDateFormat simple = new SimpleDateFormat( "HH:mm" );
        String format = simple.format( date );
        return format;
    }

    public static String getSunset(int dt){
        Date date = new Date( dt*1000L );
        SimpleDateFormat simple = new SimpleDateFormat( "HH:mm" );
        String format = simple.format( date );
        return format;
    }

}
