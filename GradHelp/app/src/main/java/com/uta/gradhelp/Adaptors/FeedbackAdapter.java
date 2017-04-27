/**
 * FeedbackAdapter.java - This class gets the data from the FeedbackModel class and forwards data to list in calling class.
 */

package com.uta.gradhelp.Adaptors;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freecats.demo.view.TextViewExpandableAnimation;
import com.uta.gradhelp.Application.FeedbackModel;
import com.uta.gradhelp.Application.Server;
import com.uta.gradhelp.AsyncTask.ConnectionHelper;
import com.uta.gradhelp.Interfaces.NetworkCallbackListener;
import com.uta.gradhelp.R;

import java.util.ArrayList;

public class FeedbackAdapter extends BaseAdapter {
    Context context;
    ArrayList<FeedbackModel> model;
    Boolean isAdvisor = false;
    private LayoutInflater inflater;

    public FeedbackAdapter(Context context, ArrayList<FeedbackModel> model, Boolean isAdvisor) {
        this.context = context;
        this.model = model;
        this.isAdvisor = isAdvisor;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.feedback_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextInputEditText) convertView.findViewById(R.id.content);
            viewHolder.upDown = (ImageView) convertView.findViewById(R.id.upDown);
            viewHolder.sendFeedback = (Button) convertView.findViewById(R.id.sendFeedback);
            viewHolder.textViewExpandableAnimation = (TextViewExpandableAnimation) convertView.findViewById(R.id.prof_feedback);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        System.out.println(isAdvisor);
        if (!isAdvisor)
            viewHolder.title.setText("Your appointment with Dr. " + model.get(position).getAdv_name() + " on " + model.get(position).getSession_date());
        else {
            viewHolder.title.setVisibility(View.GONE);
            viewHolder.upDown.setVisibility(View.GONE);
            if (model.get(position).getFeedback().equalsIgnoreCase("null"))
                viewHolder.title.setText("");
            else viewHolder.textViewExpandableAnimation.setText(model.get(position).getFeedback());
        }

        //Check if Feedback is showed to advisor or student.
        if (!isAdvisor) {
            if (model.get(position).getFeedback().equalsIgnoreCase("null"))
                viewHolder.content.setText("");
            else viewHolder.content.setText(model.get(position).getFeedback());
        } else {
            viewHolder.sendFeedback.setVisibility(View.GONE);
            viewHolder.content.setEnabled(false);
        }

        viewHolder.upDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.contentVisible) {
                    viewHolder.contentVisible = false;
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.sendFeedback.setVisibility(View.GONE);
                    viewHolder.upDown.setImageResource(android.R.drawable.arrow_down_float);
                } else {
                    viewHolder.contentVisible = true;
                    viewHolder.content.setVisibility(View.VISIBLE);
                    if (!isAdvisor) viewHolder.sendFeedback.setVisibility(View.VISIBLE);
                    else viewHolder.sendFeedback.setVisibility(View.GONE);
                    viewHolder.upDown.setImageResource(android.R.drawable.arrow_up_float);
                }
            }
        });

        //Updates the appointmentDetails table at the backend when sendFeedback button is pressed.
        viewHolder.sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConnectionHelper(context, "sendFeedback", viewHolder.content.getText().toString().trim(), model.get(position).getUnqident(), new NetworkCallbackListener() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equalsIgnoreCase(Server.ERROR_OCCURRED)) {

                        } else {
                            Toast.makeText(context, "Problem Connecting to server", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });


        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextViewExpandableAnimation textViewExpandableAnimation;
        TextInputEditText content;
        ImageView upDown;
        Button sendFeedback;
        boolean contentVisible = false;
    }
}
