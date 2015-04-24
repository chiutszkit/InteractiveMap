package com.example.derek.interactivemap.weather;

import java.util.List;

import zh.wang.android.apis.yweathergetter4a.WeatherInfo;
import zh.wang.android.apis.yweathergetter4a.YahooWeather;
import zh.wang.android.apis.yweathergetter4a.YahooWeather.SEARCH_MODE;
import zh.wang.android.apis.yweathergetter4a.YahooWeatherInfoListener;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.adapter.WeatherListViewAdapter;
import com.example.derek.interactivemap.favorite.FavoriteActivity;
import com.example.derek.interactivemap.sqlitehelper.DBItem;
import com.example.derek.interactivemap.sqlitehelper.DBController;

public class WeatherFragment extends Fragment 
	implements YahooWeatherInfoListener{
	
	private String countryName;
	private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, 5000, true);
	
	private ProgressDialog mProgressDialog;
	private TextView countryNameTextView;
	private TextView temperatureTextView;
	private TextView weatherTextView;
	private ImageView weatherIcon;
	private ListView listView;
	
	private Button favoriteBtn;
	private DBController controller;
	private DBItem dbItem;
	private Button searchBtn;
	
	private String currentLat;
	private String currentLon;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.weather_fragment, container, false);
		
		return view;
	}
	
	private void initView(View view) {
		// TODO Auto-generated method stub
		countryNameTextView = (TextView) view.findViewById(R.id.country_name);
		temperatureTextView = (TextView) view.findViewById(R.id.temperature_text);
		weatherTextView = (TextView) view.findViewById(R.id.weather_text);
		weatherIcon = (ImageView) view.findViewById(R.id.icon);
		listView = (ListView) view.findViewById(R.id.list);
		searchBtn = (Button) view.findViewById(R.id.search_button);
		favoriteBtn = (Button) view.findViewById(R.id.favorite_button);
		
		/*final SingleLineListViewAdapter adapter = new SingleLineListViewAdapter(this, menuDrawable, menuItem);
		listview.setAdapter(adapter);*/
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		initView(getView());
		
		mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
		
		countryName = getActivity().getIntent().getStringExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME);
		
		if(countryName!=null){
			mYahooWeather.setNeedDownloadIcons(true);
			mYahooWeather.setSearchMode(SEARCH_MODE.PLACE_NAME);
			mYahooWeather.queryYahooWeatherByPlaceName(getActivity(), countryName, this);
		}
		else{
			mProgressDialog.cancel();
		}
		
	}

	@Override
	public void gotWeatherInfo(WeatherInfo weatherInfo) {
		// TODO Auto-generated method stub
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.cancel();
		}
		
		if(weatherInfo != null){
			
			// 建立資料庫物件
		    controller = new DBController(getActivity());
			
			countryNameTextView.setText(weatherInfo.getWOEIDCountry() + " " + countryName);
			if(weatherInfo.getCurrentTempC()<0){
				temperatureTextView.setTextColor(getResources().getColor(R.color.blue));
			}
			else if(weatherInfo.getCurrentTempC()>33){
				temperatureTextView.setTextColor(getResources().getColor(R.color.red));
			}
			else{
				temperatureTextView.setTextColor(getResources().getColor(R.color.black));
			}
			temperatureTextView.setText("Now " + weatherInfo.getCurrentTempC() + "°C");
			weatherTextView.setText(weatherInfo.getCurrentText() + "");
			weatherIcon.setImageBitmap(weatherInfo.getCurrentConditionIcon());
			
			currentLat = weatherInfo.getConditionLat();
			currentLon = weatherInfo.getConditionLon();
			
			final WeatherListViewAdapter adapter = new WeatherListViewAdapter(getActivity(), weatherInfo.getForecastInfoList());
			listView.setAdapter(adapter);
			
			favoriteBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(checkFavoriteAddedBefore() == false){
					
						dbItem = new DBItem(countryName, currentLat, currentLon);
						
						controller.insert(dbItem);
						
						showFavoritesAddedDialog();
					}
					else{
						
						showDuplicateDialog();
						
					}
				}
			});
			
			searchBtn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Bundle bundle = new Bundle();
					bundle.putString("search", countryName);
					
					SearchWebFragment mFragment = new SearchWebFragment();
					
					mFragment.setArguments(bundle);
					
					FragmentTransaction transaction = getActivity().getSupportFragmentManager()
							.beginTransaction();
					
					transaction.replace(R.id.weather_container, mFragment);
					
					//allow user to go back to here when back button is clicked
					transaction.addToBackStack(null);
					
					transaction.commit();
					
				}
			});
			
		}
		else{
			
			favoriteBtn.setVisibility(View.INVISIBLE);
			
			countryNameTextView.setText(countryName);
			
		}
	}

	protected void showDuplicateDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle(R.string.favorite_possible_duplicated);
		alertDialog.setMessage(R.string.favorite_possible_duplicated_message);
		alertDialog.setPositiveButton(R.string.ok, null);
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	protected boolean checkFavoriteAddedBefore() {
		
		List<DBItem> all =  controller.getAll();
		
		for(int i = 0; i < all.size(); i++){
			if(all.get(i).getCountryName().contains(countryName)){
				return true;
			}
		}
		
		return false;
	}

	protected void showFavoritesAddedDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle(R.string.favorite_added);
		alertDialog.setMessage(R.string.favorite_added_message);
		alertDialog.setNegativeButton(R.string.ok, null);
		alertDialog.setPositiveButton(R.string.go_to_favorites, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(getActivity(), FavoriteActivity.class);
				startActivity(intent);
				getActivity().finish();
				
			}
		});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
	
}
