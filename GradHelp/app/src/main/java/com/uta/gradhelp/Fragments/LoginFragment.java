/**
 * LoginFragment.java - java class for fragment_login.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.uta.gradhelp.Activities.AdvisorHomeActivity;
import com.uta.gradhelp.Activities.HomeActivity;
import com.uta.gradhelp.Application.GradHelp;
import com.uta.gradhelp.Application.LoginResponseModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;
import com.uta.gradhelp.Services.BackgroundQueueService;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends android.support.v4.app.Fragment {

    View rootView;
    TextInputEditText netIDEditText, passwordEditText;
    CheckBox stayLoggedInCheckBox;
    String netID, password;
    Boolean stayLoggedIn = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Drawable backgrndImg = getResources().getDrawable(R.drawable.ic_logo);
        backgrndImg.setAlpha(60);

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rootView = view;
        netIDEditText = (TextInputEditText) rootView.findViewById(R.id.netID);
        passwordEditText = (TextInputEditText) rootView.findViewById(R.id.password);
        stayLoggedInCheckBox = (CheckBox) rootView.findViewById(R.id.stayLoggedInCheckBox);

        rootView.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

        //On click of login button checks if advisor or student is logging in.
        rootView.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                netID = netIDEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();
                stayLoggedIn = stayLoggedInCheckBox.isChecked();
                rootView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                netIDEditText.setEnabled(false);
                passwordEditText.setEnabled(false);
                if (stayLoggedIn) {
                    GradHelp.getInstance().getSharedPreferences().edit()
                            .putString("netID", netID)
                            .putString("password", password)
                            .putBoolean("stayLoggedIn", stayLoggedIn)
                            .apply();
                } else {
                    GradHelp.getInstance().getSharedPreferences().edit()
                            .remove("netID")
                            .remove("password")
                            .remove("stayLoggedIn")
                            .putBoolean("stayLoggedIn", stayLoggedIn)
                            .apply();
                }

                //Code to authenticate uer when loggin in.
                new ConnectionHelper(getActivity(), "checkLogin", netID, password, new NetworkCallbackListener() {
                    @Override
                    public void onResponse(String response) {
                        rootView.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        netIDEditText.setEnabled(true);
                        passwordEditText.setEnabled(true);

                        System.out.println("response in login activity -==-=-=-=-=->>>>> " + response);
                        try {
                            JSONObject responseObject = new JSONObject(response);
                            if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                                if (responseObject.get("message").toString().equalsIgnoreCase("true")) {
                                    LoginResponseModel loginResponseModel = new LoginResponseModel(responseObject.getString("maverick_Id"), responseObject.getString("net_id"), responseObject.getString("last_name"), responseObject.getString("department"), responseObject.getString("first_name"), responseObject.getString("message").equals("true"), responseObject.getInt("isAdvisor") == 1);

                                    GradHelp.getInstance().setLoginResponseModel(loginResponseModel);
                                    if (GradHelp.getInstance().getLoginResponseModel().getAdvisor())
                                        startActivity(new Intent(getActivity(), AdvisorHomeActivity.class));
                                    else {
                                        startActivity(new Intent(getActivity(), HomeActivity.class));
                                        getActivity().startService(new Intent(getActivity(), BackgroundQueueService.class));

                                    }

                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Incorrect username and/or password", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Problem connecting to server", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }).execute();
            }
        });
    }
}
