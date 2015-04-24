package com.example.derek.interactivemap.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.derek.interactivemap.R;

@SuppressLint("SetJavaScriptEnabled")
public class SearchWebFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.search_web_fragment, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		String keyword = getArguments().getString("search");
		
		String myURL = "https://www.google.com/search?q=" + keyword;       
        WebView myBrowser=(WebView)getView().findViewById(R.id.search_web_view);

        WebSettings websettings = myBrowser.getSettings();
        websettings.setSupportZoom(true);
        websettings.setBuiltInZoomControls(true); 
        websettings.setJavaScriptEnabled(true);
       
        myBrowser.setWebViewClient(new WebViewClient());

        myBrowser.loadUrl(myURL);
	}
	
}
