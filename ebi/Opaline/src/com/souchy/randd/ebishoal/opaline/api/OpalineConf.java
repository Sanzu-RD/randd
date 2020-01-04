package com.souchy.randd.ebishoal.opaline.api;

import com.souchy.randd.commons.tealwaters.io.files.JsonConfig;

public class OpalineConf extends JsonConfig {
	
	public String ip = "localhost";
	public int port = 8000;
	
	public int connectionTimeout = 200;
	public int readTimeout = 200;
	
	public String getTarget() {
		return "http://" + ip + ":" + port;
	}
	
}
