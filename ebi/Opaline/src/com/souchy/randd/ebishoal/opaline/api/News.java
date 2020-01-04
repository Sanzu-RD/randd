package com.souchy.randd.ebishoal.opaline.api;

import java.util.HashMap;
import java.util.List;

public class News {
	
	News(){
		
	}
	
	@SuppressWarnings("unchecked")
	public List<HashMap<String, String>> get() {
		//Log.info("Opaline.News.get");
		return Opaline.get("news", List.class); 
	}
	
}
