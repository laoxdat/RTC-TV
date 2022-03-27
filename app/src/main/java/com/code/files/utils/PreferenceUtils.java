package com.code.files.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.code.files.database.DatabaseHelper;
import com.code.files.service.SubscriptionStatusUpdateTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PreferenceUtils {
    public static final String TAG = "PreferenceUtils";


    public static boolean isActivePlan(Context context) {
        String status = getSubscriptionStatus(context);
        Log.e("Status", status);
        return status.equals("active");
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.USER_LOGIN_STATUS, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.USER_LOGIN_STATUS, false);
    }

    public static boolean isMandatoryLogin(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getConfigurationData().getAppConfig().getMandatoryLogin();
    }

    public static String getSubscriptionStatus(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getActiveStatusData().getStatus();
    }

    public static long getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getTimeInMillis();
    }

    public static long getExpireTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
        String currentDateAndTime = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(currentDateAndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);

        return calendar.getTimeInMillis();
    }

//    public static boolean isValid(Context context) {
//        String savedTime = getUpdatedTime(context);
//        long currentTime = getCurrentTime();
//        return Long.parseLong(savedTime) > currentTime;
//    }

    private static String getUpdatedTime(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return String.valueOf(db.getActiveStatusData().getExpireTime());
    }

    public static void updateSubscriptionStatus(final Context context) {
        SubscriptionStatusUpdateTask task = new SubscriptionStatusUpdateTask(context, getUserId(context));
        task.execute();
    }

    public static void clearSubscriptionSavedData(Context context) {
        //now save to sharedPreference
        DatabaseHelper db = new DatabaseHelper(context);
        db.deleteAllActiveStatusData();
    }

    public static String getUserId(Context context) {
        DatabaseHelper db = new DatabaseHelper(context);
        return db.getUserData() != null ? db.getUserData().getUserId() : null;
    }

}
