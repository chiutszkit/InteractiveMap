package com.example.derek.interactivemap.utils;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.database.MatrixCursor;
import android.support.v4.widget.SimpleCursorAdapter;

import com.example.derek.interactivemap.R;

public class SearchUtil {
	public static SimpleCursorAdapter getCursorAdapter(Context mContext,ArrayList<String> suggestionArray,String keyword)
	{
		String[] columnNames = {"_id","text"};
	    MatrixCursor cursor = new MatrixCursor(columnNames);
	    String[] temp = new String[columnNames.length];
	    int id = 0;
	    for(String item : suggestionArray){
	    	if(item.toLowerCase(Locale.getDefault()).startsWith(keyword.trim().toLowerCase(Locale.getDefault())))
	    	{
		        temp[0] = Integer.toString(id++);
		            temp[1] = item;
		        cursor.addRow(temp);
	    	}
	    }               
	    String[] from = {"text"}; 
	    int[] to = {R.id.text};
	    
		SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(mContext, R.layout.search_item, cursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		return dataAdapter;
	}
	public static String getItemTag(MatrixCursor item)
	{
		return item.getString(1);
	}
}