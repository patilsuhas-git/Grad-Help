/**
 * AppointmentFragment.java : This is the java file for fragment_appointment.xml.
 */

package com.uta.gradhelp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uta.gradhelp.R;

public class AppointmentFragment extends android.support.v4.app.Fragment {

    public static int queuePosition = 0;
    View rootView;
    TextView position;
    TextView status, empty, uBar;
    static Context context;
    Handler h;
    Runnable r;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_appointment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        context = view.getContext();

        position = (TextView) rootView.findViewById(R.id.position);
        status = (TextView) rootView.findViewById(R.id.bBar);
        empty = (TextView) rootView.findViewById(R.id.empty);
        uBar = (TextView) rootView.findViewById(R.id.uBar);

        String showThis = HomeFragment.status.getText().toString();
        showThis = showThis.replace("You have an appointment with", "in queue for");

        position.setText("" + queuePosition);
        status.setText(showThis);

        h = new Handler();
        r = new Runnable() {
            @Override
            public void run() {

                if (queuePosition == 0) {
                    empty.setVisibility(View.VISIBLE);
                    status.setVisibility(View.GONE);
                    uBar.setVisibility(View.GONE);
                    position.setVisibility(View.GONE);

                } else {
                    empty.setVisibility(View.GONE);
                    status.setVisibility(View.VISIBLE);
                    uBar.setVisibility(View.VISIBLE);
                    position.setVisibility(View.VISIBLE);
                }

                h.postDelayed(r, 10000);
            }
        };
        h.post(r);

    }

}
