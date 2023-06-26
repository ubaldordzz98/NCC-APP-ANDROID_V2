package com.janus.rodeo.Helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import androidx.core.graphics.ColorUtils;
import com.janus.rodeo.R;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {
    public static Date getHourNow(String timeZone) {
        String format = "HH:mm:ss";
        Date date;
        String formatDate = getDateWithFormat(format, timeZone);
        try {
            return date = new SimpleDateFormat(format).parse(formatDate);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getDateNow(String timeZone){
        String format = "yyyy-MM-dd HH:mm:ss";
        Date date;
        String formatDate = getDateWithFormat(format, timeZone);
        try{
            return date = new SimpleDateFormat(format).parse(formatDate);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date getActualDateWithoutHour(String timeZone){
        String format = "yyyy-MM-dd";
        Date date;
        String formatDate = getDateWithFormat(format, timeZone);
        try{
            return date = new SimpleDateFormat(format).parse(formatDate);
        }catch (Exception ex){
            return null;
        }
    }

    public static Date addMinutesActualDate(String timeZone, int minutes){
        String format = "yyyy-MM-dd HH:mm:ss";
        Date date;
        String formatDate = getDateWithFormat(format, timeZone);
        try{
            return date = new SimpleDateFormat(format).parse(formatDate + minutes);
        }catch (Exception ex){
            return null;
        }
    }

    public static Date getLastDate(String timeZone, int days){
        String format = "yyyy-MM-dd";
        String dateFormat = getDateWithFormat(format, timeZone);
        Date date;

        try {
            date = new SimpleDateFormat(format).parse(dateFormat);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, days);
            return calendar.getTime();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getStringDate(String format, Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate = dateFormat.format(date);
        return strDate;
    }

    @SuppressLint("SimpleDateFormat")
    private static String getDateWithFormat(String format, String timeZone) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(date);
    }

    public static boolean getPermission(String userPermission,String permissionType, Context context){
        String permissionValue;

        switch (permissionType) {
            case"Administrator":
                permissionValue = context.getResources().getString(R.string.Administrator);
                break;
            case "Gerente":
                permissionValue = context.getResources().getString(R.string.Gerente);
                break;
            case "Inventarios":
                permissionValue = context.getResources().getString(R.string.Inventarios);
                break;
            default:
                permissionValue = "N/V";
                break;
        }

        if(userPermission.equals(permissionValue)) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertToShortDate(String dateStr) {
        DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a ");
        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date convertedDate = sourceFormat.parse(dateStr);
            return destFormat.format(convertedDate);
        } catch (Exception ex) {
            return dateStr;
        }
    }

    public static String convertToShortDateHard(String dateStr){
        String date = dateStr.substring(0,10);
        return date;
    }

    public static String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        char[] hexData = hex.toCharArray();
        for (int count = 0; count < hexData.length - 1; count += 2) {
            int firstDigit = Character.digit(hexData[count], 16);
            int lastDigit = Character.digit(hexData[count + 1], 16);
            int decimal = firstDigit * 16 + lastDigit;
            sb.append((char)decimal);
        }
        return sb.toString();
    }

    public static String cleanEPC(String epc){
        String cleanEPC = "";
        epc = epc.replace(" ","");
        int charPosition = epc.indexOf("5A");
        if(charPosition<= 0){
            charPosition = epc.indexOf("41");
        }
        cleanEPC = epc.substring(0,charPosition+2);
        return cleanEPC;
    }

    public static String removeWhitespace(String epc){
        String cleanEPC = "";
        cleanEPC = epc.replace(" ","");
        return cleanEPC;
    }

    public static String removeDoubleZero(String epc){
        String cleanEPC = "";
        cleanEPC = epc.replace("00","");
        return cleanEPC;
    }

    public static int getTextColor(String backgroundColor){
        int foregroundColor = Color.WHITE;
        double contrast = ColorUtils.calculateContrast(foregroundColor,Color.parseColor(backgroundColor));
        if (contrast > 1.5f) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    public static byte[] bitmapToByteArray(Bitmap image){
        Bitmap bmp = image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap byteArrayToBitmap(byte[] byteImage){
        Bitmap bmp = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        return bmp;
    }

    public static String byteArrayToBase64(byte[] byteImage){
        return Base64.encodeToString(byteImage, Base64.DEFAULT);
    }

}