/**
 * RegisterFragment.java : This is the java file for fragment_register.xml.
 */

package com.uta.gradhelp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.activities.ViewPagerWithTabsActivity;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterFragment extends android.support.v4.app.Fragment {
    View rootView;
    TextInputEditText netIDEditText, passwordEditText, confirmPasswordEditText, mavIDEditText, firstNameEditText, lastNameEditText;
    AppCompatSpinner departmentSpinner;
    CheckBox isAdvisorCheckBox;
    String netID, password, confirmPassword, mavID, firstName, lastName, department;
    Boolean isAdvisor = false;

    ArrayList<String> departments;
    ArrayAdapter departmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        departments = new ArrayList<>();
        departments.add("Computer Science");
        departments.add("Electrical Engineering");
        departments.add("Industrial Engineering");

        departmentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, departments);
        netIDEditText = (TextInputEditText) rootView.findViewById(R.id.netID);
        passwordEditText = (TextInputEditText) rootView.findViewById(R.id.passwordText);
        confirmPasswordEditText = (TextInputEditText) rootView.findViewById(R.id.confirmPasswordText);
        mavIDEditText = (TextInputEditText) rootView.findViewById(R.id.mavID);
        firstNameEditText = (TextInputEditText) rootView.findViewById(R.id.firstName);
        lastNameEditText = (TextInputEditText) rootView.findViewById(R.id.lastName);
        departmentSpinner = (AppCompatSpinner) rootView.findViewById(R.id.dept);
        isAdvisorCheckBox = (CheckBox) rootView.findViewById(R.id.isAdvisorCheckBox);

        rootView.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fader).setVisibility(View.INVISIBLE);

        departmentSpinner.setAdapter(departmentAdapter);

        rootView.findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                netID = netIDEditText.getText().toString();
                password = passwordEditText.getText().toString();
                confirmPassword = confirmPasswordEditText.getText().toString();
                mavID = mavIDEditText.getText().toString();
                firstName = firstNameEditText.getText().toString();
                lastName = lastNameEditText.getText().toString();
                department = departmentSpinner.getSelectedItem().toString();
                isAdvisor = isAdvisorCheckBox.isChecked();

                if (password.equals(confirmPassword)) {
                    rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.fader).setVisibility(View.VISIBLE);

                    //Call to ConnectionHelper Class to insert user info usersdetails table at backend.
                    new ConnectionHelper(getActivity(), "register", netID, password, mavID, firstName, lastName, department, isAdvisor, new NetworkCallbackListener() {
                        @Override
                        public void onResponse(String response) {
                            rootView.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                            rootView.findViewById(R.id.fader).setVisibility(View.INVISIBLE);

                            if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                                    if (jsonObject.get("message").toString().equalsIgnoreCase("registered")) {
                                        Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                                        ((ViewPagerWithTabsActivity) getActivity()).selectPage(0);
                                    } else {
                                        Toast.makeText(getActivity(), "Error while registering", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Problem connecting to server", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).execute();
                } else {
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    passwordEditText.setError("Passwords do not match");
                    confirmPasswordEditText.setError("Passwords do not match");
                }
            }
        });

    }
}
