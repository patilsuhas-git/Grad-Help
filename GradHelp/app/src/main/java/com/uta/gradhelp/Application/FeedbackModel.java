/**
 * FeedbackModel.java - Class for the activity.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackModel {
    String stud_netid, stud_name, adv_name, unqident, session_date, feedback, adv_netid, appointmentNumber;

    public String getStud_netid() {
        return stud_netid;
    }

    public void setStud_netid(String stud_netid) {
        this.stud_netid = stud_netid;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getAdv_name() {
        return adv_name;
    }

    public void setAdv_name(String adv_name) {
        this.adv_name = adv_name;
    }

    public String getUnqident() {
        return unqident;
    }

    public void setUnqident(String unqident) {
        this.unqident = unqident;
    }

    public String getSession_date() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(session_date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        return formattedTime;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getAdv_netid() {
        return adv_netid;
    }

    public void setAdv_netid(String adv_netid) {
        this.adv_netid = adv_netid;
    }

    public FeedbackModel(String stud_netid, String stud_name, String adv_name, String unqident, String session_date, String feedback) {
        this.adv_name = adv_name;
        this.feedback = feedback;
        this.stud_name = stud_name;
        this.unqident = unqident;
        this.session_date = session_date;
        this.stud_netid = stud_netid;
    }

    public FeedbackModel(String adv_netid, String stud_netid, String stud_name, String adv_name, String unqident, String session_date, String feedback, String appointmentNumber) {
        this.adv_name = adv_name;
        this.feedback = feedback;
        this.stud_name = stud_name;
        this.unqident = unqident;
        this.session_date = session_date;
        this.stud_netid = stud_netid;
        this.adv_netid = adv_netid;
        this.appointmentNumber = appointmentNumber;
    }

    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }
}
