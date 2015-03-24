package com.mycompany.listviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by VIanoshchuk on 24.03.2015.
 */
public class AnswerListAdapter extends BaseAdapter {
    private String[] data;
    private Context context;

    public AnswerListAdapter(Context context, String[] data1) {
        super();
        this.data = data1;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = LayoutInflater.from(context).
                inflate(R.layout.answer_row_layout, parent, false);

        TextView text1 = (TextView) rowView.findViewById(R.id.text_answer);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon_answer);

        text1.setText(data[position]);
        icon.setImageResource(R.mipmap.ic_next);

        return rowView;
    }

}
