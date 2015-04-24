package com.example.derek.interactivemap.favorite;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.derek.interactivemap.HomeActivity;
import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.adapter.FavoriteListViewAdapter;
import com.example.derek.interactivemap.overview.OverviewActivity;
import com.example.derek.interactivemap.search.SearchPlaceActivity;
import com.example.derek.interactivemap.sqlitehelper.DBItem;
import com.example.derek.interactivemap.sqlitehelper.DBController;
import com.example.derek.interactivemap.weather.WeatherActivity;

public class FavoriteActivity extends Activity{
	
	private DBController controller;
	private FavoriteListViewAdapter favoriteListViewAdapter;
	private List<DBItem> items = new ArrayList<DBItem>();
	private ListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite_activity);
		
		// 建立資料庫物件
	    controller = new DBController(getApplicationContext());
		
		if(controller.getCount() == 0){
			showEmptyListDialog();
		}
		
		items = controller.getAll();
		
		listView = (ListView) findViewById(R.id.favorite_list_view);
		
		favoriteListViewAdapter = new FavoriteListViewAdapter(this, items, R.drawable.delete_icon);
		listView.setAdapter(favoriteListViewAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(FavoriteActivity.this, WeatherActivity.class);
				intent.putExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME, items.get(position).getCountryName());
				startActivity(intent);
				
			}
			
		});
		
	}
	
	private void showEmptyListDialog(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(R.string.no_favorites_title);
		alertDialog.setMessage(R.string.no_faovrites_text);
		alertDialog.setNegativeButton(R.string.ok, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(FavoriteActivity.this, HomeActivity.class);
				startActivity(intent);
				FavoriteActivity.this.finish();
			}
		});
		alertDialog.setNeutralButton(R.string.overview_option, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(FavoriteActivity.this, OverviewActivity.class);
				startActivity(intent);
				FavoriteActivity.this.finish();
				
			}
		});
		alertDialog.setPositiveButton(R.string.search_option, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(FavoriteActivity.this, SearchPlaceActivity.class);
				startActivity(intent);
				FavoriteActivity.this.finish();
				
			}
		});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
	
}
