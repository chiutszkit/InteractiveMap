package com.example.derek.interactivemap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.derek.interactivemap.R;

public class SingleLineListViewAdapter extends BaseAdapter {
	  private final Context context;
	  private final int[] icons;
	  private final int[] texts;
	  
	  public SingleLineListViewAdapter(Context context, int[] icons, int[] texts){
		  this.context = context;
		  this.icons = icons;
		  this.texts = texts;
	  }
	  
	  @Override
	  public int getCount() {
		  // TODO Auto-generated method stub
		  return icons.length;
	  }
	  
	  @Override
	  public Object getItem(int arg0) {
		  // TODO Auto-generated method stub
		  return null;
	  }
	  
	  @Override
	  public long getItemId(int arg0) {
		  // TODO Auto-generated method stub
		  return arg0;
	  }
		
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		  LayoutInflater inflater = (LayoutInflater) context
				  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View rowView = inflater.inflate(R.layout.home_list_view_item_layout, parent, false);
		  TextView textView = (TextView) rowView.findViewById(R.id.list_view_text_view);
		  ImageView imageView = (ImageView) rowView.findViewById(R.id.list_view_item_icon);
		  textView.setText(context.getString(texts[position]));
		  imageView.setImageResource(icons[position]);
		  
		  return rowView;
	  }
} 
