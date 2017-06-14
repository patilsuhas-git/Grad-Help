/**
 * FAQActivity.java - Java class for activity_faq activity.
 */

package com.uta.gradhelp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.blunderer.materialdesignlibrary.activities.Activity;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;
import com.uta.gradhelp.Adaptors.FAQAdapter;
import com.uta.gradhelp.Application.FAQModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FAQActivity extends Activity {

    ArrayList<String> title, content; //title content to be used later
    ArrayList<FAQModel> faqModelArrayList;
    ListView listView;
    FAQAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = new ArrayList<>();
        content = new ArrayList<>();
        faqModelArrayList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.faq_list);
        arrayAdapter = new FAQAdapter(this, title, content);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting info from server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        //Fetches all the FAQ for display to user.
        new ConnectionHelper(this, "getFAQ", new NetworkCallbackListener() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        System.out.println(jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            System.out.println(jsonObject.getInt("ques_no"));
                            System.out.println(jsonObject.getString("ques"));
                            System.out.println(jsonObject.getString("ans"));
                            faqModelArrayList.add(new FAQModel(jsonObject.getInt("ques_no"), jsonObject.getString("ques"), jsonObject.getString("ans")));
                            title.add(faqModelArrayList.get(i).getQues());
                            content.add(faqModelArrayList.get(i).getAns());
                        }
                        arrayAdapter.notifyDataSetChanged();
                        listView.setAdapter(arrayAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FAQActivity.this, "Connection problem", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_faq;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }


    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarSearchHandler(this, new OnSearchListener() {
            @Override
            public void onSearched(String text) {
                //Code here search a particular FAQ which users wants to search.
                new ConnectionHelper(FAQActivity.this, "getSearchFAQ", text, new NetworkCallbackListener() {

                    @Override
                    public void onResponse(String response) {
                        if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                System.out.println(jsonArray.length());
                                faqModelArrayList.clear();
                                title.clear();
                                content.clear();
                                if (!jsonArray.getJSONObject(0).getString("message").equalsIgnoreCase("No result found"))
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        System.out.println(jsonObject.getInt("ques_no"));
                                        System.out.println(jsonObject.getString("ques"));
                                        System.out.println(jsonObject.getString("ans"));
                                        faqModelArrayList.add(new FAQModel(jsonObject.getInt("ques_no"), jsonObject.getString("ques"), jsonObject.getString("ans")));
                                        title.add(faqModelArrayList.get(i).getQues());
                                        content.add(faqModelArrayList.get(i).getAns());
                                    }
                                else {
                                    Toast.makeText(FAQActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                                }
                                arrayAdapter.notifyDataSetChanged();
                                listView.setAdapter(arrayAdapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(FAQActivity.this, "Connection problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
