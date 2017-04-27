/**
 *QueueModel.java : Class structures data in the desired format so that the columns of the table could be fetched exclusively.
 * Contains all the getters and setters as per columns in the table.
 */

package com.uta.gradhelp.Application;

public class QueueModel {
    private String adv_netid;
    private String reason;
    private String session_date;
    private String stud_name;
    private String unqident;
    private String stud_netid;
    private String adv_name;
    private int stud_mavid;
    private int adv_complete;

    public QueueModel(String adv_netid, String reason, String session_date, String stud_name, String unqident, String stud_netid, String adv_name, int stud_mavid, int adv_complete) {
        this.adv_netid = adv_netid;
        this.reason = reason;
        this.session_date = session_date;
        this.stud_name = stud_name;
        this.unqident = unqident;
        this.stud_netid = stud_netid;
        this.adv_name = adv_name;
        this.stud_mavid = stud_mavid;
        this.adv_complete = adv_complete;
    }

    public void setAdv_netid(String adv_netid) {
        this.adv_netid = adv_netid;
    }

    public String getAdv_netid() {
        return adv_netid;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }

    public String getSession_date() {
        return session_date;
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

    public void setAdv_name(String adv_name) {
        this.adv_name = adv_name;
    }

    public String getAdv_name() {
        return adv_name;
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

}
