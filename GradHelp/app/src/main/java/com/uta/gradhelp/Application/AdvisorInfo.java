/**
 * AdvisorInfo.java - Class for the activity.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdvisorInfo {
    private String netID, firstName, lastName, startTime, endTime, week_days, room_no, building;
    private Boolean enabled = true;

    public AdvisorInfo(String netID, String firstName, String lastName) {
        this.netID = netID;
        this.firstName = firstName;
        this.lastName = lastName;
     /*   this.startTime = startTime;
        this.endTime = endTime;
        this.week_days = week_days;
        this.room_no = room_no;
        this.building = building;*/
    }

    public AdvisorInfo(String netID, String firstName, String lastName, String startTime, String endTime, String week_days, String room_no, String building) {
        this.netID = netID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.week_days = week_days;
        this.room_no = room_no;
        this.building = building;
    }


    public String getNetID() {
        return netID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeek_days() {
        return week_days;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        enabled = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()).equalsIgnoreCase(week_days);
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
