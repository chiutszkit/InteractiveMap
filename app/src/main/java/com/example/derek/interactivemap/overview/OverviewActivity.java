package com.example.derek.interactivemap.overview;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.example.derek.interactivemap.R;

public class OverviewActivity extends FragmentActivity{
	
	private OverviewPagerAdapter overviewPagerAdapter;
	
	private ViewPager mViewPager;
	
	private ToggleButton overviewToggle;
	
	private ToggleButton historyToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview_activity);
		
		overviewPagerAdapter = new OverviewPagerAdapter(
				getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.overview_pager);
		
		mViewPager.setAdapter(overviewPagerAdapter);
		
		overviewToggle = (ToggleButton) findViewById(R.id.overview_list_toggle);
		historyToggle = (ToggleButton) findViewById(R.id.overview_history_toggle);
		
		//by default
		overviewToggle.setChecked(true);
		
		overviewToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
					historyToggle.setChecked(false);
					
					mViewPager.setCurrentItem(0);

					overviewToggle.setClickable(false);

					historyToggle.setClickable(true);
				}
			}
		});
		
		
		historyToggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					
					overviewToggle.setChecked(false);
					
					mViewPager.setCurrentItem(1);

					historyToggle.setClickable(false);

					overviewToggle.setClickable(true);
				}
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if(position == 0){
					overviewToggle.setChecked(true);
				}else{
					historyToggle.setChecked(true);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
		
}
