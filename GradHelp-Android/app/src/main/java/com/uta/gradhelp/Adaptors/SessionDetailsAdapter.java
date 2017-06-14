/**
 * SessionDetialsAdapter.java - This class gets the data from the SessionDetailModel class and forwards data to list in calling class.
 */

package com.uta.gradhelp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uta.gradhelp.Application.SessionDetailsModel;
import com.uta.gradhelp.R;

import java.util.ArrayList;

public class SessionDetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<SessionDetailsModel> sessionDetailsModelArrayList;
    ArrayList<String> content;
    private LayoutInflater inflater;

    public SessionDetailsAdapter(Context context, ArrayList<SessionDetailsModel> advisorQueueModel) {
        this.sessionDetailsModelArrayList = advisorQueueModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sessionDetailsModelArrayList.size();
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
            convertView = inflater.inflate(R.layout.advisor_queue_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String showThis = "";

        showThis += sessionDetailsModelArrayList.get(position).getWeek_days()+"\n\n";
        showThis += "Advising hours: " + sessionDetailsModelArrayList.get(position).getAdvising_start_time().substring(0,5) + " hrs to " +sessionDetailsModelArrayList.get(position).getAdvising_end_time().substring(0,5);
        showThis += "hrs\nRoom no. " + sessionDetailsModelArrayList.get(position).getRoom_no();
        showThis += ", " + sessionDetailsModelArrayList.get(position).getBuilding();
        viewHolder.title.setText(showThis);

        return convertView;
    }

    static class ViewHolder {
        TextView title;
    }
}
