/**
 * QueueAdapter.java - This class gets the data from the QueueModel class and forwards data to list in calling class.
 */

package com.uta.gradhelp.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uta.gradhelp.Application.GradHelp;
import com.uta.gradhelp.Application.QueueModel;
import com.uta.gradhelp.Fragments.AppointmentFragment;
import com.uta.gradhelp.R;
import com.uta.gradhelp.Services.BackgroundQueueService;

import java.util.ArrayList;

public class QueueAdapter extends BaseAdapter {
    Context context;
    ArrayList<QueueModel> queueResponseArrayList;
    ArrayList<String> content;
    private LayoutInflater inflater;

    public QueueAdapter(Context context, ArrayList<QueueModel> advisorQueueModel) {
        this.queueResponseArrayList = advisorQueueModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return queueResponseArrayList.size();
    }

    @Override
    public QueueModel getItem(int i) {
        return queueResponseArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.advisor_queue_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.underlay = (LinearLayout) convertView.findViewById(R.id.underlay);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Here Maverick id displayed in the queue are masked. As per discussed with TA.
        if (getItem(position).getStud_netid().equalsIgnoreCase(GradHelp.getInstance().getLoginResponseModel().getNet_id())) {
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            //String s = "" + getItem(position).getStud_mavid();
            //s = s.substring(6, s.length());
            viewHolder.underlay.setBackgroundColor(Color.parseColor("#88ff0000"));
            //viewHolder.title.setText("******" + s);
            viewHolder.title.setText("" + getItem(position).getStud_mavid());
            //viewHolder.title.setText(viewHolder.title.getText() + "\nYou are at position " + (position + 1) + " in queue");
            viewHolder.title.setText(viewHolder.title.getText() + "\nThis is your token in the queue");
        } else {
            viewHolder.title.setTextColor(Color.BLACK);
            String s = "" + getItem(position).getStud_mavid();
            s = s.substring(6, s.length());
            viewHolder.underlay.setBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.title.setText("******" + s);
        }

        return convertView;
    }

    // Notification.
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getStud_netid().equalsIgnoreCase(GradHelp.getInstance().getLoginResponseModel().getNet_id())) {
                BackgroundQueueService.queuePosition = i + 1;
                AppointmentFragment.queuePosition = i + 1;
                break;
            }
        }
        if (getCount() == 0) AppointmentFragment.queuePosition = 0;
    }

    static class ViewHolder {
        TextView title;
        LinearLayout underlay;
    }
}
