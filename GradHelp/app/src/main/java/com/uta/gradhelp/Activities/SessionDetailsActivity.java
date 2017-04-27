/*
* SessionDetailsActivity.java : Java class for activity_session_details activity.
* */

package com.uta.gradhelp.Activities;

import android.os.Bundle;
import android.widget.ListView;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.uta.gradhelp.Adaptors.SessionDetailsAdapter;
import com.uta.gradhelp.Application.SessionDetailsModel;
import com.uta.gradhelp.R;

import java.util.ArrayList;

public class SessionDetailsActivity extends Activity {
    ArrayList<SessionDetailsModel> sessionDetailsModelArrayList;
    ListView listView;
    SessionDetailsAdapter advisorQueueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionDetailsModelArrayList = getIntent().getParcelableArrayListExtra("sessionDetails");
        listView = (ListView) findViewById(R.id.session_list);
        advisorQueueAdapter = new SessionDetailsAdapter(this, sessionDetailsModelArrayList);
        listView.setAdapter(advisorQueueAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_session_details;
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
