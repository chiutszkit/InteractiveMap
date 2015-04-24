package com.example.derek.interactivemap.overview;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.adapter.HistoryListViewAdapter;
import com.example.derek.interactivemap.sqlitehelper.HistoryDBController;
import com.example.derek.interactivemap.sqlitehelper.HistoryItem;
import com.example.derek.interactivemap.weather.WeatherActivity;

public class OverviewHistoryFragment extends Fragment{
	
	private List<HistoryItem> countries = new ArrayList<HistoryItem> ();
	private HistoryListViewAdapter historyListViewAdapter;
	private HistoryDBController dbController;
	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.overview_history_fragment, container, false);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		dbController = new HistoryDBController(getActivity());
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		countries = dbController.getAll();
		
		listView = (ListView) getView().findViewById(R.id.history_list_view);
		
		if(historyListViewAdapter == null){
			historyListViewAdapter = new HistoryListViewAdapter(getActivity(), countries, R.drawable.delete_icon);
			listView.setAdapter(historyListViewAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent();
					intent.setClass(getActivity(), WeatherActivity.class);
					intent.putExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME, countries.get(position).getCountryName());
					startActivity(intent);
					
				}
				
			});
		}
		else{
			historyListViewAdapter.setList(countries);
			
			historyListViewAdapter.notifyDataSetChanged();
		}
	}
}
