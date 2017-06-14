/**
 * AdvisorQueueAdapter.java - This class gets the data from the AdvisorQueueModel class and forwards data to list in calling class.
 */

package com.uta.gradhelp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uta.gradhelp.Application.AdvisorQueueModel;
import com.uta.gradhelp.R;

import java.util.ArrayList;

public class AdvisorQueueAdapter extends BaseAdapter {
    Context context;
    ArrayList<AdvisorQueueModel> advisorQueueModel;
    ArrayList<String> content;
    private LayoutInflater inflater;

    public AdvisorQueueAdapter(Context context, ArrayList<AdvisorQueueModel> advisorQueueModel) {
        this.advisorQueueModel = advisorQueueModel;
        this.context = context;
    }

    @Override
    public int getCount() {
        return advisorQueueModel.size();
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

        viewHolder.title.setText("Mav ID: "+advisorQueueModel.get(position).getStud_mavid()+"\nStudent Name: "+advisorQueueModel.get(position).getStud_name());


        return convertView;
    }

    static class ViewHolder {
        TextView title;
    }
}
