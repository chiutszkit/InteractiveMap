package com.example.derek.interactivemap.adapter;

import java.util.ArrayList;

import com.example.derek.interactivemap.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StickyListHeaderAdapter extends BaseAdapter implements StickyListHeadersAdapter {
	
	private ArrayList<String> list;
    private LayoutInflater inflater;
    
    public StickyListHeaderAdapter(Context context, ArrayList<String> countries) {
        inflater = LayoutInflater.from(context);
        this.list = countries;
        //countries = context.getResources().getStringArray(R.array.countries);
    }
    
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sticky_list_item_content, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.sticky_list_item_text);
            convertView.setTag(holder);
            
        } else {
            holder = (ViewHolder) convertView.getTag();
            
        }
        
        String text = "" + list.get(position);
        
        holder.text.setText(text);

        return convertView;
    }

    @Override 
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.sticky_list_item_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.sticky_list_header_text);
            convertView.setTag(holder);
            
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
            
        }
        //set header text as first char in name
        String headerText = "" + list.get(position).subSequence(0, 1).charAt(0);
        
        holder.text.setText(headerText);
        
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
    	return list.get(position).subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
		TextView text;
    }

}
