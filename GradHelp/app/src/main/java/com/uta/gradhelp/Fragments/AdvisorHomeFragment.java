/**
 * AdvisorHomeFragment.java : This is the java file for fragment_advisor_home.xml.
 */

package com.uta.gradhelp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uta.gradhelp.Activities.FeedbackActivity;
import com.uta.gradhelp.Activities.SessionDetailsActivity;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.Application.SessionDetailsModel;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdvisorHomeFragment extends android.support.v4.app.Fragment {
    View rootView;
    TextView status;

    ArrayList<SessionDetailsModel> sessionDetailsModelArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_advisor_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        sessionDetailsModelArrayList = new ArrayList<>();
        status = (TextView) rootView.findViewById(R.id.status);

        //Code when sessionDetailButton is clicked. Fetches all the session detail of logged advisor.
        rootView.findViewById(R.id.sessionDetailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectionHelper(getActivity(), "advisorHomeDetails", new NetworkCallbackListener() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                sessionDetailsModelArrayList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    sessionDetailsModelArrayList.add(new SessionDetailsModel(jsonObject.getString("advising_start_time"), jsonObject.getString("advising_end_time"), jsonObject.getString("week_days"), jsonObject.getInt("room_no"), jsonObject.getString("building")));
                                }
                                startActivity(new Intent(getActivity(), SessionDetailsActivity.class).putParcelableArrayListExtra("sessionDetails", sessionDetailsModelArrayList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Problem Connecting to server", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).execute();
            }
        });

        //Code takes us to Advisors feedback activity activity_feedback.xml.
        rootView.findViewById(R.id.advFeedbackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class).putExtra("isAdvisor", true));
            }
        });
    }

    //This method is called when fragment_advisor_home is visited. Makes call to ConnectionHelper.java class to fetch
    //count of records/appointment.
    @Override
    public void onResume() {
        super.onResume();
        new ConnectionHelper(getActivity(), "advisorHome", new NetworkCallbackListener() {
            @Override
            public void onResponse(String response) {
                if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        if (jsonObject.getInt("message") == 0) {
                            status.setText("You have no appointments today");
                        } else {
                            int count = jsonObject.getInt("message");
                            status.setText(count == 1 ? "You have 1 appointment today" : "You have " + count + " appointments today");
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
