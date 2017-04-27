/**
 * NetworkCallbackListener.java - This class passes the data to the class which calls the connectionhelper.java.
 * This class acts as an intermediary between calling class and connectionHelper as data cannot be provided back from ConnectionHelper.java.
 */

package com.uta.gradhelp.Interfaces;

public interface NetworkCallbackListener {
    public void onResponse(String response);
}
