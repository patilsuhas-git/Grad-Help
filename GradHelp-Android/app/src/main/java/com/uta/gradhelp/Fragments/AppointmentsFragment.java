/**
 * AppointmentsFragment.java : This is the java file for fragment_advisor_queue.xml.
 */
package com.uta.gradhelp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uta.gradhelp.Activities.AppointmentDetails;
import com.uta.gradhelp.Adaptors.AdvisorQueueAdapter;
import com.uta.gradhelp.Application.AdvisorQueueModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppointmentsFragment extends Fragment {
    View rootView;
    ListView listView;
    ArrayList<AdvisorQueueModel> advisorQueueModelArrayList;
    AdvisorQueueAdapter arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advisor_queue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;

        listView = (ListView) rootView.findViewById(R.id.advisorQueue);
        advisorQueueModelArrayList = new ArrayList<>();
        arrayAdapter = new AdvisorQueueAdapter(getActivity(), advisorQueueModelArrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0)
                    startActivity(new Intent(getActivity(), AppointmentDetails.class).putExtra("appointment", advisorQueueModelArrayList.get(i)));
            }
        });

        System.out.println("first here onviewcreated");
    }

    //This method gets called when fragment_advisor_queue.xml is visited from a different activity. Usually when back button
    //is pressed.
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("first here onresume");
        advisorQueueModelArrayList.clear();
        arrayAdapter.notifyDataSetChanged();
        new ConnectionHelper(getActivity(), "advisorQueue", new NetworkCallbackListener() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            advisorQueueModelArrayList.add(new AdvisorQueueModel(jsonObject.getString("reason"), jsonObject.getString("stud_name"), jsonObject.getString("unqident"), jsonObject.getString("stud_netid"), jsonObject.getInt("stud_mavid"), jsonObject.getInt("adv_complete")));
                            arrayAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), "Problem connecting to server", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }
}
