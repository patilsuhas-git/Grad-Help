/**
 * LoginResponseModel.java : Class structures data in the desired format so that the columns of the table could be fetched exclusively.
 * Contains all the getters and setters as per columns in the table.
 */

package com.uta.gradhelp.Application;

public class LoginResponseModel {
    private String maverick_Id;
    private String net_id;
    private String last_name;
    private String department;
    private String first_name;
    private Boolean result, isAdvisor;


    public LoginResponseModel(String maverick_Id, String net_id, String last_name, String department, String first_name, Boolean result, Boolean isAdvisor) {
        this.maverick_Id = maverick_Id;
        this.net_id = net_id;
        this.last_name = last_name;
        this.department = department;
        this.first_name = first_name;
        this.result = result;
        this.isAdvisor = isAdvisor;
    }

    public Boolean getAdvisor() {
        return isAdvisor;
    }

    public void setAdvisor(Boolean advisor) {
        isAdvisor = advisor;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public void setMaverick_Id(String maverick_Id) {
        this.maverick_Id = maverick_Id;
    }

    public String getMaverick_Id() {
        return maverick_Id;
    }

    public void setNet_id(String net_id) {
        this.net_id = net_id;
    }

    public String getNet_id() {
        return net_id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

}