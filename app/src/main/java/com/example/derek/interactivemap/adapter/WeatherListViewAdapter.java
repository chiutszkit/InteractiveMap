package com.example.derek.interactivemap.adapter;

import java.util.List;

import zh.wang.android.apis.yweathergetter4a.WeatherInfo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.derek.interactivemap.R;

public class WeatherListViewAdapter extends BaseAdapter{
	private final Context context;
	private List<WeatherInfo.ForecastInfo> forecastInfoList;
	
	public WeatherListViewAdapter(Context context,  List<WeatherInfo.ForecastInfo> forecastInfoList){
		this.context = context;
		this.forecastInfoList = forecastInfoList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return forecastInfoList.size();
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
		View rowView = inflater.inflate(R.layout.weather_list_view_item_layout, parent, false);
		TextView textTitleView = (TextView) rowView.findViewById(R.id.weather_date_text);
		TextView textView = (TextView) rowView.findViewById(R.id.weather_temperature_text);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.weather_icon);
		textTitleView.setText(forecastInfoList.get(position).getForecastDate());
		textView.setText(forecastInfoList.get(position).getForecastTempLowC() + " - " + forecastInfoList.get(position).getForecastTempHighC() + "°C");
		imageView.setImageBitmap(forecastInfoList.get(position).getForecastConditionIcon());
		  
		return rowView;
	}
	
}
