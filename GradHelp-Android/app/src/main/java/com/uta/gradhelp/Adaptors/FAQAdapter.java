/**
 * FAQAdapter.java - This class gets the data from the FAQModel class and forwards data to list in calling class.
 */

package com.uta.gradhelp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uta.gradhelp.R;

import java.util.ArrayList;

public class FAQAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> title;
    ArrayList<String> content;
    private LayoutInflater inflater;

    public FAQAdapter(Context context, ArrayList<String> title, ArrayList<String> content) {
        this.content = content;
        this.context = context;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.size();
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
            convertView = inflater.inflate(R.layout.faq_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.upDown = (ImageView) convertView.findViewById(R.id.upDown);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(title.get(position));
        viewHolder.content.setText(content.get(position));
        viewHolder.upDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.contentVisible) {
                    viewHolder.contentVisible = false;
                    viewHolder.content.setVisibility(View.GONE);
                    viewHolder.upDown.setImageResource(android.R.drawable.arrow_down_float);
                } else {
                    viewHolder.contentVisible = true;
                    viewHolder.content.setVisibility(View.VISIBLE);
                    viewHolder.upDown.setImageResource(android.R.drawable.arrow_up_float);
                }
            }
        });


        return convertView;
    }

    static class ViewHolder {
        TextView title, content;
        ImageView upDown;
        boolean contentVisible = false;
    }
}
