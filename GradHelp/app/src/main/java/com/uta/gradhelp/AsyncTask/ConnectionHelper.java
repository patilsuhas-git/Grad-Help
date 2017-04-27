/**
 * ConnectionHelper.java - Class for connecting backend with frontend. This class is responsible for fetching data from back end.
 *                          An external library from David Webb is used to connect to back end system.
 */

package com.uta.gradhelp.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.goebl.david.Webb;
import com.uta.gradhelp.Application.GradHelp;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.goebl.david.Webb.create;

public class ConnectionHelper extends AsyncTask<Void, Void, Void> {

    private String response = "", line;
    private NetworkCallbackListener networkCallbackListener;
    private Context context;
    private String netID, password, mode, mavID, firstName, lastName, department, json, data;
    Boolean isAdvisor;
    Calendar calendar;
    Date date, date2;
    DateFormat dateFormat;
    String todayDate;

    // Parameterized constructor.
    public ConnectionHelper(Context context, String mode, String netID, String password, NetworkCallbackListener networkCallbackListener) {
        this.context = context;
        this.networkCallbackListener = networkCallbackListener;
        this.netID = netID;
        this.password = password;
        this.mode = mode;
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date2 = new Date();
        todayDate = (dateFormat.format(date2));
    }

    // Parameterized constructor.
    public ConnectionHelper(Context context, String mode, String netID, String password, String mavID, String firstName, String lastName, String department, Boolean isAdvisor, NetworkCallbackListener networkCallbackListener) {
        this.context = context;
        this.networkCallbackListener = networkCallbackListener;
        this.netID = netID;
        this.password = password;
        this.mavID = mavID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.isAdvisor = isAdvisor;
        this.mode = mode;
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date2 = new Date();
        todayDate = (dateFormat.format(date2));
    }

    // Parameterized constructor.
    public ConnectionHelper(Context context, String mode, String data, NetworkCallbackListener networkCallbackListener) {
        this.context = context;
        this.networkCallbackListener = networkCallbackListener;
        this.data = this.json = this.netID = data;
        this.mode = mode;
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date2 = new Date();
        todayDate = (dateFormat.format(date2));
    }

    // Parameterized constructor.
    public ConnectionHelper(Context context, String mode, NetworkCallbackListener networkCallbackListener) {
        this.context = context;
        this.networkCallbackListener = networkCallbackListener;
        this.mode = mode;
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date2 = new Date();
        todayDate = (dateFormat.format(date2));
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //Overridden method runs in back ground of the application. Has the logic for connection to the backend.
    @Override
    protected Void doInBackground(Void... params) {
        calendar = Calendar.getInstance();
        date = calendar.getTime();
        todayDate = (dateFormat.format(date2));

        //Code for authenticating the logging in user by querying the Usersdetail table.
        if (mode.equalsIgnoreCase("checkLogin")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("forAuth", true);
                jsnObj.put("net_id", netID);
                jsnObj.put("password", password);

                System.out.println("This is checkLogin =======>>>>>" + jsnObj);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/users")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result doinbg -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    JSONObject object = new JSONObject();
                    for (int n = 0; n < result.length(); n++) {
                        object = result.getJSONObject(n);
                    }
                    response = object.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code for registering the new user. Inserts the details of user in Usersdetail table.
        if (mode.equalsIgnoreCase("register")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("forReg", true);
                jsnObj.put("forFCMToken", true);
                jsnObj.put("net_id", netID);
                jsnObj.put("password", password);
                jsnObj.put("maverick_Id", mavID);
                jsnObj.put("first_name", firstName);
                jsnObj.put("last_name", lastName);
                jsnObj.put("department", department);
                jsnObj.put("isAdvisor", isAdvisor);

                jsnObj.put("fcm_token", GradHelp.getInstance().getFCMToken());
                System.out.println("jsnObj in register ========>>>>>> " + jsnObj);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/users")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();

                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    JSONObject object = new JSONObject();
                    for (int n = 0; n < result.length(); n++) {
                        object = result.getJSONObject(n);
                    }
                    response = object.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code for getting the status of loggd in user from the Usersdetail table.
        if (mode.equalsIgnoreCase("getStatus")) {
            try {

                JSONObject jsnObj = new JSONObject();
                jsnObj.put("forStudentStatus", true);
                jsnObj.put("stud_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id());
                jsnObj.put("session_date", todayDate);
                jsnObj.put("weekday", new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).toLowerCase());
                System.out.println("Here: " + jsnObj.toString());

                Webb webb = create();
                //Callout to back end.
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result get status-==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch the advisor names of a department based on the department of the logged in student.
        if (mode.equalsIgnoreCase("getAdvisorNames")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("department", GradHelp.getInstance().getLoginResponseModel().getDepartment());
                jsnObj.put("forAdvisorName", true);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/users")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to insert the record for logged in students in the Appointmentdetails table.
        if (mode.equalsIgnoreCase("makeAppointment")) {
            String sendThis = json;
            try {
                JSONObject jsonObject = new JSONObject(sendThis);
                jsonObject.put("forAppointmentConfirm", true);
                System.out.println("Sending this: " + jsonObject.toString());
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsonObject)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString().replace("[", "").replace("]", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to check if the slots for a particular advisor are full or not.
        if (mode.equalsIgnoreCase("checkSlots")) {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("forCheckSlot", true);
                jsonObject.put("adv_netid", data);
                Webb webb = create();
                JSONObject result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                                        .ensureSuccess()
                                        .body(jsonObject)
                                        .asJsonObject()
                                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch all the FAQ questions and answer.
        if (mode.equalsIgnoreCase("getFAQ")) {
            try {
                Webb webb = create();
                JSONArray result = webb.get("http://gradhelp-abbas133.rhcloud.com/api/faq").ensureSuccess().asJsonArray().getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to search a particular FAQ.
        if (mode.equalsIgnoreCase("getSearchFAQ")) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("searchText", data);
                System.out.println(jsonObject);
                Webb webb = Webb.create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/faq")
                        .ensureSuccess()
                        .body(jsonObject)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch records associated to an advisor for that day.
        if (mode.equalsIgnoreCase("advisorQueue")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("adv_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id());
                jsnObj.put("forAdvisorQueue", true);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("length of response " + result.length());
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch the count of appointments that are made on a particular day to an advisor.
        if (mode.equalsIgnoreCase("advisorHome")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("adv_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id());
                jsnObj.put("forAdvisorStatus", true);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch the appointmeent details like venue, timings and building where avising would be held.
        if (mode.equalsIgnoreCase("advisorHomeDetails")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("adv_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id());
                jsnObj.put("isForAdvisorSessionDetail", true);

                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to complete the appointment and update a particular appointment record in appointmentdetail table.
        if (mode.equalsIgnoreCase("completeAppointment")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("unqident", netID); //because of constructor naming...it is uniqident.
                jsnObj.put("adv_complete", true);
                jsnObj.put("forAdvisorQueueUpdate", true);
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();
                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch all the appointment records to display on the student view.
        if (mode.equalsIgnoreCase("getQueue")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("stud_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id()); //because of constructor naming...it is uniqident.
                jsnObj.put("session_date", todayDate);
                jsnObj.put("forStudentQueue", true);

                System.out.println(jsnObj.toString());
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();

                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to update the feedback at the backend. This feedback is registered against a appointment.
        if (mode.equalsIgnoreCase("sendFeedback")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("feedback", netID);          //because of constructor naming...it is feedback.
                jsnObj.put("unqident", password);       //because of constructor naming...it is uniqident.
                jsnObj.put("forFeedbackUpdate", true);

                System.out.println(jsnObj.toString());
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();

                System.out.println("Result -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Fetch the records to show as feedback to students.
        if (mode.equalsIgnoreCase("getFeedback")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("stud_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id()); //because of constructor naming...it is feedback.
                jsnObj.put("forStudentFeedBack", true);

                System.out.println("aalo" + jsnObj.toString());
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();

                System.out.println("Result  -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }

        //Code to fetch feedback to show to advisor.
        if (mode.equalsIgnoreCase("getAdvisorFeedback")) {
            try {
                JSONObject jsnObj = new JSONObject();
                jsnObj.put("adv_netid", GradHelp.getInstance().getLoginResponseModel().getNet_id()); //because of constructor naming...it is feedback.
                jsnObj.put("forAdvisorViewFeedback", true);

                System.out.println("aalo" + jsnObj.toString());
                Webb webb = create();
                JSONArray result = webb.post("http://gradhelp-abbas133.rhcloud.com/api/appointments")
                        .ensureSuccess()
                        .body(jsnObj)
                        .asJsonArray()
                        .getBody();

                System.out.println("Result  -==-=-=-=-=-=>>>>" + result);
                if (result.toString().isEmpty()) {
                    response = "errorOccurred";
                } else {
                    response = result.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "errorOccurred";
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        networkCallbackListener.onResponse(response);
    }
}