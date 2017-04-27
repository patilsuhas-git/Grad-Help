/**
 * AppointmentDetails.java - Java class for activity_appointment_details avtivity.
 */

package com.uta.gradhelp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.uta.gradhelp.Application.AdvisorQueueModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

public class AppointmentDetails extends Activity {

    AdvisorQueueModel advisorQueueModel;
    TextView mavID, name, reason;
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        advisorQueueModel = (AdvisorQueueModel) getIntent().getSerializableExtra("appointment");
        mavID = (TextView) findViewById(R.id.mavID);
        name = (TextView) findViewById(R.id.name);
        reason = (TextView) findViewById(R.id.reason);
        confirmButton = (Button) findViewById(R.id.confirmButton);

        mavID.setText("Student Mav ID:\n" + advisorQueueModel.getStud_mavid());
        name.setText("Student Name:\n" + advisorQueueModel.getStud_name());
        reason.setText("Meeting Reason:\n" + advisorQueueModel.getReason());


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                new ConnectionHelper(AppointmentDetails.this, "completeAppointment", advisorQueueModel.getUnqident(), new NetworkCallbackListener() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        response = "{}";
                        if (response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                            Toast.makeText(AppointmentDetails.this, "Connection Problem", Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                        }
                    }
                }).execute();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_appointment_details;
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
