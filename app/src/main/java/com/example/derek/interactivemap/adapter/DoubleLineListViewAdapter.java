/**
 * Created by Derek on 25/3/15.
 */
package com.example.derek.interactivemap.adapter;

import com.example.derek.interactivemap.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoubleLineListViewAdapter extends BaseAdapter{
    private final Context context;
    private final int[] icons;
    private final int[] titles;
    private final int[] texts;

    public DoubleLineListViewAdapter(Context context, int[] icons, int[] titles, int[] texts){
        this.context = context;
        this.icons = icons;
        this.titles = titles;
        this.texts = texts;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.about_list_view_item_layout, parent, false);
        TextView textTitleView = (TextView) rowView.findViewById(R.id.about_list_view_item_title);
        TextView textView = (TextView) rowView.findViewById(R.id.about_list_view_item_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.about_list_view_item_icon);
        textTitleView.setText(context.getString(titles[position]));
        textView.setText(context.getString(texts[position]));
        imageView.setImageResource(icons[position]);

        return rowView;
    }

}

