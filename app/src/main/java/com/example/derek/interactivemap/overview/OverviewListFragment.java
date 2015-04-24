package com.example.derek.interactivemap.overview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.adapter.StickyListHeaderAdapter;
import com.example.derek.interactivemap.sqlitehelper.HistoryDBController;
import com.example.derek.interactivemap.sqlitehelper.HistoryItem;
import com.example.derek.interactivemap.weather.WeatherActivity;

public class OverviewListFragment extends Fragment{
	
	private ArrayList<String> countries;
	private List<HistoryItem> historyItems;
	private HistoryDBController DBController;
	private HistoryItem history;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.overview_list_fragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		countries = getListOfCountries();
		Collections.sort(countries);
		
		// 建立資料庫物件
		DBController = new HistoryDBController(getActivity());
		
		StickyListHeadersListView stickyList = (StickyListHeadersListView) getView().findViewById(R.id.list);
		StickyListHeaderAdapter adapter = new StickyListHeaderAdapter(getActivity(), countries);
		stickyList.setAdapter(adapter);
		stickyList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				history = new HistoryItem(countries.get(position));
				
				if(checkHistoryAddedBefore() == false)
					DBController.insert(history);
				
				Intent intent = new Intent();
				intent.setClass(getActivity(), WeatherActivity.class);
				intent.putExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME, countries.get(position));
				startActivity(intent);
			}
		});
	}
	
	protected boolean checkHistoryAddedBefore() {
		
		if(historyItems == null)
			historyItems = new ArrayList<HistoryItem>();
		
		historyItems = DBController.getAll();
		
		for(int i = 0; i < historyItems.size(); i++){
			if(historyItems.get(i).getCountryName().contains(history.getCountryName())){
				Log.v("OverviewList","history added before: " + history.getCountryName());
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<String> getListOfCountries() {
		// TODO Auto-generated method stub
		
		ArrayList<String> items = new ArrayList<String>();
		
		String[] isoCountries = Locale.getISOCountries();
        for (String country : isoCountries) {
            Locale locale = new Locale("en", country);
//          String iso = locale.getISO3Country();
//          String code = locale.getCountry();
            String name = locale.getDisplayCountry();
 
            if (!"".equals(name)) {
                items.add(name);
            }
        }
        
        return items;
        
	}
	
}
