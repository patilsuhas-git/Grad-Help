/**
 * FeedbackActivity.java - Java class for activity_feedback activity.
 */

package com.uta.gradhelp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.uta.gradhelp.Adaptors.FeedbackAdapter;
import com.uta.gradhelp.Application.FeedbackModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedbackActivity extends Activity {


    ArrayList<FeedbackModel> feedbackModelArrayList;
    ListView listView;
    FeedbackAdapter arrayAdapter;
    Boolean isAdvisor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackModelArrayList = new ArrayList<>();

        try {
            isAdvisor = getIntent().getBooleanExtra("isAdvisor", false);
        } catch (Exception e) {
            isAdvisor = false;
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.feedback_list);
        arrayAdapter = new FeedbackAdapter(this, feedbackModelArrayList, isAdvisor);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting info from server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        new ConnectionHelper(this, isAdvisor ? "getAdvisorFeedback" : "getFeedback", new NetworkCallbackListener() {
            @Override
            public void onResponse(String response) {
                if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //The display for student and advisor are differenciated here.
                            if (!isAdvisor) {
                                feedbackModelArrayList.add(new FeedbackModel(jsonObject.getString("stud_netid"), jsonObject.getString("stud_name"), jsonObject.getString("adv_name"), jsonObject.getString("unqident"), jsonObject.getString("session_date"), jsonObject.getString("feedback")));
                            }
                            else{
                                feedbackModelArrayList.add(new FeedbackModel(jsonObject.getString("adv_netid"), jsonObject.getString("stud_netid"), jsonObject.getString("stud_name"), jsonObject.getString("adv_name"), jsonObject.getString("unqident"), jsonObject.getString("session_date"), jsonObject.getString("feedback"), jsonObject.getString("appt_no")));
                            }
                        }
                        //System.out.println(title.size());
                        arrayAdapter.notifyDataSetChanged();
                        listView.setAdapter(arrayAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FeedbackActivity.this, "Problem Connecting to server", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isAdvisor){
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, AdvisorHomeActivity.class));
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return null;
    }
}
