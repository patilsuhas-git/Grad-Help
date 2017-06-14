/**
 * AdvisorQueueModel.java - Class for the activity.
 * @author  Suhas Patil
 */
package com.uta.gradhelp.Application;

import java.io.Serializable;

public class AdvisorQueueModel implements Serializable {
    private String reason;
    private String stud_name;
    private String unqident;
    private String stud_netid;
    private int stud_mavid;
    private int adv_complete;

    public AdvisorQueueModel(String reason, String stud_name, String unqident, String stud_netid, int stud_mavid, int adv_complete) {
        this.reason = reason;
        this.stud_name = stud_name;
        this.unqident = unqident;
        this.stud_netid = stud_netid;
        this.stud_mavid = stud_mavid;
        this.adv_complete = adv_complete;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setUnqident(String unqident) {
        this.unqident = unqident;
    }

    public String getUnqident() {
        return unqident;
    }

    public void setStud_netid(String stud_netid) {
        this.stud_netid = stud_netid;
    }

    public String getStud_netid() {
        return stud_netid;
    }

    public void setStud_mavid(int stud_mavid) {
        this.stud_mavid = stud_mavid;
    }

    public int getStud_mavid() {
        return stud_mavid;
    }

    public void setAdv_complete(int adv_complete) {
        this.adv_complete = adv_complete;
    }

    public int getAdv_complete() {
        return adv_complete;
    }

    @Override
    public String toString() {
        return "{" + getReason() + "," + getStud_name() + "," + getStud_mavid() + "," + getStud_netid() + "}";
    }
}
