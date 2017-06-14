package com.uta.gradhelp.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.uta.gradhelp.Activities.HomeActivity;
import com.uta.gradhelp.Adaptors.QueueAdapter;
import com.uta.gradhelp.Application.QueueModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BackgroundQueueService extends Service {
    static ArrayList<QueueModel> queueResponseArrayList;
    static QueueAdapter queueResponseArrayAdapter;
    public static int queuePosition = Integer.MAX_VALUE;
    int oldQueuePosition = queuePosition;
    private boolean firstTime = true;

    public BackgroundQueueService() {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (getApplicationContext() != null) {
            System.out.println("service started");
            new ConnectionHelper(getApplicationContext(), "getQueue", new NetworkCallbackListener() {
                @Override
                public void onResponse(String response) {
                    if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                        //System.out.println("Q fragment: " + response);

                        queueResponseArrayList.clear();
                        queueResponseArrayAdapter.notifyDataSetChanged();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (!jsonObject.has("message"))
                                    queueResponseArrayList.add(new QueueModel(
                                            jsonObject.getString("adv_netid"),
                                            jsonObject.getString("reason"),
                                            jsonObject.getString("session_date"),
                                            jsonObject.getString("stud_name"),
                                            jsonObject.getString("unqident"),
                                            jsonObject.getString("stud_netid"),
                                            jsonObject.getString("adv_name"),
                                            jsonObject.getInt("stud_mavid"),
                                            jsonObject.getInt("adv_complete")
                                    ));

                            }

                            queueResponseArrayAdapter.notifyDataSetChanged();
                            System.out.println("queuePosition=======>>>>"+ queuePosition);
                            System.out.println("oldQueuePosition=======>>>>"+ oldQueuePosition);
                            if (queuePosition != oldQueuePosition) {
                                oldQueuePosition = queuePosition;
                                if (!firstTime)
                                    showNotification();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Toast.makeText(getApplicationContext(), "Problem Connecting to server", Toast.LENGTH_SHORT).show();
                    }
                    firstTime = false;
                    startService(new Intent(BackgroundQueueService.this, BackgroundQueueService.class));
                }
            }).execute();
        }
        return START_STICKY;
    }

    private void showNotification() {
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.warning).setContentIntent(null); //Required on Gingerbread and below
        int TAG = 0;
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), HomeActivity.class), 0));
        //.setContentIntent(null);
        TAG = 1;
        builder.setContentTitle("Queue Updated");
        bigTextStyle.setBigContentTitle("Queue Updated");
        builder.setContentText("Queue has been updated.");
        bigTextStyle.bigText("Queue has been updated and your appointment is coming");
        builder.setTicker("Queue Updated");
        builder.setStyle(bigTextStyle);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(TAG, builder.build());
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        if (wl.isHeld())
            wl.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queueResponseArrayList = new ArrayList<>();
        queueResponseArrayAdapter = new QueueAdapter(getApplicationContext(), queueResponseArrayList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
