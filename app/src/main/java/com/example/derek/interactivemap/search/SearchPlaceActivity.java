package com.example.derek.interactivemap.search;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.OnSuggestionListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.derek.interactivemap.R;
import com.example.derek.interactivemap.sqlitehelper.DBController;
import com.example.derek.interactivemap.sqlitehelper.DBItem;
import com.example.derek.interactivemap.utils.SearchUtil;
import com.example.derek.interactivemap.weather.WeatherActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchPlaceActivity extends ActionBarActivity
	implements OnInfoWindowClickListener, LocationListener{
	
	private SearchView search;
	private TextView result;
	private GoogleMap googleMap;
	private MarkerOptions markerOptions;
	private LatLng latLng;
	private Marker targetMarker;
	
	private DBController controller;
	private List<DBItem> allFavorites = new ArrayList<DBItem>();
	private List<Marker> favoriteMarker = new ArrayList<Marker>();
	private Marker customMarker;
	private float[] markerColor = {BitmapDescriptorFactory.HUE_AZURE,
			BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_CYAN,
			BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA,
			BitmapDescriptorFactory.HUE_ORANGE, BitmapDescriptorFactory.HUE_RED,
			BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_VIOLET,
			BitmapDescriptorFactory.HUE_YELLOW};

    private LocationManager locationManager;
    private String locationProvider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.search_place_activity);
		
		controller = new DBController(getApplicationContext());
		
		SupportMapFragment supportMapFragment = (SupportMapFragment)
        getSupportFragmentManager().findFragmentById(R.id.map);
 
        // Getting a reference to the map
        googleMap = supportMapFragment.getMap();
		googleMap.setOnInfoWindowClickListener(this);
        
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

	        @Override
	        public void onMapClick(LatLng point) {
	            // TODO Auto-generated method stub
	        	if(customMarker != null)
	        		customMarker.remove();
	        	
	        	customMarker = googleMap.addMarker(new MarkerOptions().position(point)
		        .title(point.latitude + "," + point.longitude));
	        }
	    });
		
		result = (TextView) findViewById(R.id.status_text);
		result.setText(getString(R.string.map_help_message));

        //get the location manager
        this.locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


        //define the location manager criteria
        Criteria criteria = new Criteria();

        this.locationProvider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(locationProvider);

        //initialize the location
        if(location != null) {

            onLocationChanged(location);
        }
		
		checkAllFavorites();
	}
	
	private void checkAllFavorites() {
		
		allFavorites = controller.getAll();
		
		for(int i=0; i<allFavorites.size(); i++){
			
			if(allFavorites.get(i).getLatitude()!=null ||allFavorites.get(i).getLongitude()!=null){
				double latInDouble = Double.valueOf(allFavorites.get(i).getLatitude().trim()).doubleValue();
				double lonInDouble = Double.valueOf(allFavorites.get(i).getLongitude().trim()).doubleValue();
				
				LatLng latLng = new LatLng(latInDouble, lonInDouble);
				
				int jRandom = (int) (Math.random()*10);
				
				favoriteMarker.add(googleMap.addMarker(new MarkerOptions()
			        .position(latLng)
			        .title(allFavorites.get(i).getCountryName())
			        .icon(BitmapDescriptorFactory.defaultMarker(markerColor[jRandom]))));
				
			}
		}
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
 
        //SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
 
		MenuItem searchMenuItem = menu.findItem(R.id.action_search);	
		
		search = (SearchView) MenuItemCompat
				.getActionView(searchMenuItem);
        
        //search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
 
        search.setOnQueryTextListener(new OnQueryTextListener() { 
 
            @Override 
            public boolean onQueryTextChange(String query) {
 
            	setSuggestionForSearch(query);
 
                return true; 
 
            }

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			} 
 
        });
	 
	    
	 
	    return true;
		
	}

	protected void setSuggestionForSearch(String query) {
		// TODO Auto-generated method stub
		ArrayList<String> suggestionArray;
		
		suggestionArray = getListOfCountries();
		
		search.setSuggestionsAdapter(SearchUtil
				.getCursorAdapter(SearchPlaceActivity.this, suggestionArray,
						query));
		
		search.setOnSuggestionListener(new OnSuggestionListener() {

			@Override
			public boolean onSuggestionClick(int position) {

				showSearchResult(SearchUtil
						.getItemTag((MatrixCursor) search
								.getSuggestionsAdapter().getItem(position)));

				return false;
			}

			@Override
			public boolean onSuggestionSelect(int position) {
				return false;
			}
		});
			
	}

	protected void showSearchResult(String itemTag) {
		// TODO Auto-generated method stub
		// clear inputted text
		search.setQuery("", false);
		search.clearFocus();
		
		result.setText(itemTag);
		
		new GeocoderTask().execute(itemTag);
		
		Toast.makeText(this.getBaseContext(),getString(R.string.map_searching_message),Toast.LENGTH_SHORT).show();
	}

    @Override
    public void onLocationChanged(Location location) {

        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
        this.googleMap.moveCamera(center);

        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        this.googleMap.animateCamera(zoom);
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
	    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{
	 
	        @Override
	        protected List<Address> doInBackground(String... locationName) {
	            // Creating an instance of Geocoder class
	            Geocoder geocoder = new Geocoder(getBaseContext());
	            List<Address> addresses = new ArrayList<>();
	 
	            try {
	                // Getting a maximum of 3 Address that matches the input text
	                addresses = geocoder.getFromLocationName(locationName[0], 3);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            return addresses;
	        }
	 
	        @Override
	        protected void onPostExecute(List<Address> addresses) {
	 
	            if(addresses==null || addresses.size()==0){
	                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
                    return;
	            }
	 
	            // Clears all the existing markers on the map
	            //googleMap.clear();
	            if(targetMarker != null)
	            	targetMarker.remove();
	            
	            
	            // Adding Markers on Gsoogle Map for each matching address
	            for(int i=0;i<addresses.size();i++) {

                    Address address = (Address) addresses.get(i);

                    // Creating an instance of GeoPoint, to display in Google Map
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
	 
	               /* String addressText = String.format("%s, %s",
	                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
	                address.getCountryName());*/

                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(address.getCountryName());

                    targetMarker = googleMap.addMarker(markerOptions);

                    // Locate the first location
                    if (i == 0) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 12.0f));
                    }
                }
	        }
	    }

		@Override
		public void onInfoWindowClick(Marker marker) {
			// TODO Auto-generated method stub
			if(marker.equals(customMarker)){
				//provide the address based on latitude and longitude
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(this, Locale.getDefault());
				try {
					addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
					
					if(addresses != null){
						
						if(addresses.size()>0){
						
							Address address = (Address) addresses.get(0);
							
							Intent intent = new Intent();
							intent.setClass(SearchPlaceActivity.this, WeatherActivity.class);
							intent.putExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME, address.getCountryName());
							startActivity(intent);
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else{
				Intent intent = new Intent();
				intent.setClass(SearchPlaceActivity.this, WeatherActivity.class);
				intent.putExtra(WeatherActivity.KEY_EXTRA_COUNTRY_NAME, marker.getTitle());
				startActivity(intent);
			}
		}
	
}

	
