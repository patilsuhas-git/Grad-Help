/**
 * FAQModel.java - Class for the activity.
 * @author  Suhas Patil
 */

package com.uta.gradhelp.Application;

public class FAQModel {
    private int ques_no;
    private String ques;
    private String ans;

    public FAQModel(int ques_no, String ques, String ans) {
        this.ques_no = ques_no;
        this.ques = ques;
        this.ans = ans;
    }

    public void setQues_no(int ques_no) {
        this.ques_no = ques_no;
    }

    public int getQues_no() {
        return ques_no;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getQues() {
        return ques;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getAns() {
        return ans;
    }

}
