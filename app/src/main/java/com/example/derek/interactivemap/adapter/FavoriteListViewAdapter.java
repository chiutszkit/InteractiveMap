package com.example.derek.interactivemap.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.favorite.FavoriteActivity;
import com.example.derek.interactivemap.sqlitehelper.DBItem;
import com.example.derek.interactivemap.sqlitehelper.DBController;

public class FavoriteListViewAdapter extends BaseAdapter {
	  private final Context context;
	  private List<DBItem> items;
	  private int deleteIconDrawable;
	  private DBController controller;
	  
	  public FavoriteListViewAdapter(Context context, List<DBItem> items, int deleteIconDrawable){
		  this.context = context;
		  this.items = items;
		  this.deleteIconDrawable = deleteIconDrawable;
	  }
	  
	  @Override
	  public int getCount() {
		  // TODO Auto-generated method stub
		  return items.size();
	  }
	  
	  @Override
	  public DBItem getItem(int position) {
		  // TODO Auto-generated method stub
		  return items.get(position);
	  }
	  
	  @Override
	  public long getItemId(int arg0) {
		  // TODO Auto-generated method stub
		  return arg0;
	  }
		
	  @Override
	  public View getView(final int position, View convertView, ViewGroup parent) {
		  LayoutInflater inflater = (LayoutInflater) context
				  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View rowView = inflater.inflate(R.layout.home_list_view_item_layout, parent, false);
		  TextView textView = (TextView) rowView.findViewById(R.id.list_view_text_view);
		  ImageView imageView = (ImageView) rowView.findViewById(R.id.list_view_item_icon);
		  textView.setText(items.get(position).getCountryName());
		  imageView.setImageResource(deleteIconDrawable);
		  
		  imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				controller = new DBController(context);
				
				controller.delete(items.get(position).getId());
				
				items.remove(position);
				
				notifyDataSetChanged();
			}
		});
		  
		  return rowView;
	  }
} 
