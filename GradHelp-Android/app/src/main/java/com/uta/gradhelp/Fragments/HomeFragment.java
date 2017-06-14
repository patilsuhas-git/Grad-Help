/**
 * HomeFragment.java : This is the java file for fragment_home.xml.
 */

package com.uta.gradhelp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.uta.gradhelp.Activities.BookAppointmentActivity;
import com.uta.gradhelp.Activities.FAQActivity;
import com.uta.gradhelp.Activities.FeedbackActivity;
import com.uta.gradhelp.Application.GradHelp;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends android.support.v4.app.Fragment {

    View rootView;
    static TextView status;
    public static boolean fetchStatusFromServer = true;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        rootView = view;
        status = (TextView) rootView.findViewById(R.id.status);

        rootView.findViewById(R.id.bookAppointmentButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueueFragment.cancelTimer();
                startActivity(new Intent(getActivity(), BookAppointmentActivity.class));
                getActivity().finish();
            }
        });

        rootView.findViewById(R.id.faqButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueueFragment.cancelTimer();
                startActivity(new Intent(getActivity(), FAQActivity.class));
                getActivity().finish();
            }
        });

        rootView.findViewById(R.id.feedbackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QueueFragment.cancelTimer();
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                getActivity().finish();
            }
        });
    }
    //This method gets called when the connected fragment is again visited.
    @Override
    public void onResume() {
        super.onResume();
        if (fetchStatusFromServer) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting info from server...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            //Call to ConnectionHelper class to fetch the status.
            new ConnectionHelper(getActivity(), "getStatus", GradHelp.getInstance().getNetID(), new NetworkCallbackListener() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    System.out.println("Here: " + response);
                    if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            if (jsonObject.has("message"))
                                status.setText(jsonObject.getString("message"));
                            else {
                                status.setText("You have an appointment with Dr. " + jsonObject.getString("adv_name") + " on " + GradHelp.getInstance().getFormattedDate(jsonObject.getString("session_date")) + " in room no. " + jsonObject.getString("room_no") + " in " + jsonObject.getString("building"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        status.setText("Problem connecting to server");
                        Toast.makeText(getActivity(), "Problem connecting to server", Toast.LENGTH_SHORT).show();
                    }
                }
            }).execute();
        } else {
            try {
                JSONObject jsonObject = new JSONObject(BookAppointmentActivity.sendJSON);
                status.setText("You have an appointment with Dr. " + jsonObject.get("adv_name") + " on " + jsonObject.get("session_date"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        fetchStatusFromServer = true;
    }
}
