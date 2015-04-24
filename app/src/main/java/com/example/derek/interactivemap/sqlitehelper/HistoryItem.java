package com.example.derek.interactivemap.sqlitehelper;

public class HistoryItem {
	
	private String countryName;
	private Long id;
	
	
	public HistoryItem(){

	}
	
	public HistoryItem(String countryName){
		this.countryName = countryName;
	}
	
	public String getCountryName(){
		return countryName;
	}
	public void setCountryName(String countryName){
		this.countryName = countryName;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public Long getId(){
		return id;
	}
	
}
