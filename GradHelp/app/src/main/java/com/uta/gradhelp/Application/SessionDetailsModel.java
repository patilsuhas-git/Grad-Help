/**
 *SessionDetailsModel.java : Class structures te data in the desired format so that the columns of the table could be fetched exclusively.
 * Contains all the getters and setters.
 */

package com.uta.gradhelp.Application;

import android.os.Parcel;
import android.os.Parcelable;

public class SessionDetailsModel implements Parcelable {
    private String advising_start_time;
    private String advising_end_time;
    private String week_days;
    private int room_no;
    private String building;

    public SessionDetailsModel(String advising_start_time, String advising_end_time, String week_days, int room_no, String building) {
        this.advising_start_time = advising_start_time;
        this.advising_end_time = advising_end_time;
        this.week_days = week_days;
        this.room_no = room_no;
        this.building = building;
    }

    public void setAdvising_start_time(String advising_start_time) {
        this.advising_start_time = advising_start_time;
    }

    public String getAdvising_start_time() {
        return advising_start_time;
    }

    public void setAdvising_end_time(String advising_end_time) {
        this.advising_end_time = advising_end_time;
    }

    public String getAdvising_end_time() {
        return advising_end_time;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
    }

    public String getWeek_days() {
        return week_days;
    }

    public void setRoom_no(int room_no) {
        this.room_no = room_no;
    }

    public int getRoom_no() {
        return room_no;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuilding() {
        return building;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private SessionDetailsModel(Parcel in) {
        this.advising_start_time = in.readString();
        this.advising_end_time = in.readString();
        this.week_days = in.readString();
        this.room_no = in.readInt();
        this.building = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(advising_start_time);
        dest.writeString(advising_end_time);
        dest.writeString(week_days);
        dest.writeInt(room_no);
        dest.writeString(building);
    }

    public static final Parcelable.Creator<SessionDetailsModel> CREATOR = new Parcelable.Creator<SessionDetailsModel>() {
        public SessionDetailsModel createFromParcel(Parcel in) {
            return new SessionDetailsModel(in);
        }

        public SessionDetailsModel[] newArray(int size) {
            return new SessionDetailsModel[size];

        }
    };
}
