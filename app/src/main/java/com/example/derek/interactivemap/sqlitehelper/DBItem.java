package com.example.derek.interactivemap.sqlitehelper;

public class DBItem {
	
	private String countryName;
	private Long id;
	private String latitude;
	private String longitude;
	
	public DBItem(){
		
	}
	
	public DBItem(String countryName, String lat, String lon){
		this.countryName = countryName;
		this.latitude = lat;
		this.longitude = lon;
	}
	
	public String getCountryName(){
		return countryName;
	}
	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
	public String getLatitude(){
		return latitude;
	}
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	
	public String getLongitude(){
		return longitude;
	}
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	
}
