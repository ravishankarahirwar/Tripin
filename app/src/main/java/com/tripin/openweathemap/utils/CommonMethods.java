package com.tripin.openweathemap.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CommonMethods {

    public static ProgressHUD mProgressHUD;

    public static void isProgressShow(Context mContext) {

        if (mProgressHUD == null) {

            mProgressHUD = ProgressHUD.show(mContext,
                    CommonMessages.msgPleaseWait, false, false, null);
        } else {
            if (!mProgressHUD.isShowing()) {
                mProgressHUD = ProgressHUD.show(mContext,
                        CommonMessages.msgPleaseWait, false, false, null);
            }
        }
    }


    public static void isProgressHide() {
        if (mProgressHUD != null) {

            if (mProgressHUD.isShowing()) {
                mProgressHUD.dismiss();
            }
        }
    }



    public static void showToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static void isProgressShowNoMessage(Context mContext) {
        if (mProgressHUD == null) {
            mProgressHUD = ProgressHUD.show(mContext, null, false, false, null);
        } else {
            if (!mProgressHUD.isShowing()) {
                mProgressHUD = ProgressHUD.show(mContext, null, false, false,
                        null);
            }
        }
    }



    public static String getDate(long milliSeconds, String formate) {
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);
        return formatter.format(calendar.getTime());
    }



    //user these function for chat and date display in header
    public static String timestamptotime_date(long timestamp) {

        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        // SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a"); // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    //user in chat with display sending and from time with am/pm formate in 12 hr
    public static String Timestamptotime_To_AM_PM(long timestamp) {

        Date date = new Date(timestamp * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // the format of your date


        String formattedDate = sdf.format(date);
        return formattedDate;
    }


    public static String getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis * 1000);

        Calendar now = Calendar.getInstance();


        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today";
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday";
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return timestamptotime_date(smsTimeInMilis);
        } else {
            return timestamptotime_date(smsTimeInMilis);
        }

    }

    public static String numberFormat(double number) {

        String[] suffix = new String[]{"", "k", "m", "b", "t"};
        int MAX_LENGTH = 4;

        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while (r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")) {
            r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
        }
        return r;
    }


    public static String CurrentTime()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    public static String TimestampTOCurrentTime(long timestemp)
    {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        java.util.Date currenTimeZone=new java.util.Date((long)timestemp*1000);
        return sdf.format(currenTimeZone);
    }






}
