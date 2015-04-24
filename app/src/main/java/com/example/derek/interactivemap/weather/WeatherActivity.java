package com.example.derek.interactivemap.weather;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.derek.interactivemap.R;

public class WeatherActivity extends FragmentActivity{
	
	private WeatherFragment weatherFragment;
	public static final String KEY_EXTRA_COUNTRY_NAME = "country_name";
	
	@Override
	protected void onCreate(Bundle savedInstanceStates) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceStates);
		setContentView(R.layout.weather_activity);
		
		if(savedInstanceStates == null){
			
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();

			weatherFragment = new WeatherFragment();

			transaction.add(R.id.weather_container, weatherFragment,
					"overview_list");

			transaction.commit();
			
		}
		
	}
	
}
