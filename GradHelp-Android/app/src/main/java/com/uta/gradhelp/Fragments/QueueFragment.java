/**
 * QueueFragment.java : This is the java file for fragment_queue.xml.
 */

package com.uta.gradhelp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.uta.gradhelp.Adaptors.QueueAdapter;
import com.uta.gradhelp.Application.QueueModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QueueFragment extends android.support.v4.app.Fragment {

    View rootView;
    ListView listView;
    static TimerTask timerTask;
    static Timer timer;
    static   ArrayList<QueueModel> queueResponseArrayList;
    static  QueueAdapter queueResponseArrayAdapter;
    Boolean test = true;
    static Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_queue, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        context = view.getContext();
        listView = (ListView) rootView.findViewById(R.id.queue);

        queueResponseArrayList = new ArrayList<>();
        queueResponseArrayAdapter = new QueueAdapter(getActivity(), queueResponseArrayList);
        listView.setAdapter(queueResponseArrayAdapter);

        // Initializes queue after every 10 secs.
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                doThis();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 10000);

    }

    //This method fetches the records from AppointmentDetails table every 10 secs.
    private static void doThis() {
        System.out.println("Running in 10 seconds");
        new ConnectionHelper(context, "getQueue", new NetworkCallbackListener() {
            @Override
            public void onResponse(String response) {
                if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                    System.out.println("Q fragment: " + response);

                    queueResponseArrayList.clear();
                    queueResponseArrayAdapter.notifyDataSetChanged();

                    // queueResponseArrayList is filled up with appointment details records from backend.
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "Problem Connecting to server", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null)
            timer.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
    }

    //This method called when Student queue activity is visited.
    @Override
    public void onResume() {
        super.onResume();
        reScheduleTimer();
    }

    public static void cancelTimer(){
        if (timer != null){
            timer.cancel();
            timerTask.cancel();
            System.out.println("Timer cancelled");}
    }

    //This method invokes a timer. Currently set to 10 secs, after 10secs records are pulled from backend.
    public static void reScheduleTimer() {
        // Cancel previous timer first
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                doThis();
            }
        };
        timer.schedule(timerTask, 0, 10000);
    }
}
