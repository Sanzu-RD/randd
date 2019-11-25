package com.souchy.randd.ebishoal.opaline.api;

import java.util.HashMap;
import java.util.List;

import com.souchy.randd.commons.tealwaters.logging.Log;

public class News {
	
	News(){
		
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> get() {
		Log.info("Opaline.News.get");
		return Opaline.get(Opaline.target("news"), List.class); 
	}
	
}
