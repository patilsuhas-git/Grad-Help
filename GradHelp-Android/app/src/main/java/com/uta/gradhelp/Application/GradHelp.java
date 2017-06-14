/**
 * Gradhelp.java - Class for the activity.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.TypedValue;

import com.uta.gradhelp.Services.BackgroundQueueService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GradHelp extends Application {

    private static GradHelp appController;
    private String netID;
    private LoginResponseModel loginResponseModel;
    private String FCMToken;

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        System.out.println("stopping service");
        stopService(new Intent(this, BackgroundQueueService.class));
    }

    public static synchronized GradHelp getInstance() {
        return appController;
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences("login", Context.MODE_PRIVATE);
    }

    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public static int dpToPixel(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getInstance().getResources().getDisplayMetrics());
    }

    public void setLoginResponseModel(LoginResponseModel loginResponseModel) {
        this.loginResponseModel = loginResponseModel;
    }

    public LoginResponseModel getLoginResponseModel() {
        return loginResponseModel;
    }

    public String getFormattedDate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.format(d);
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    public String getFCMToken() {
        return FCMToken;
    }
}
