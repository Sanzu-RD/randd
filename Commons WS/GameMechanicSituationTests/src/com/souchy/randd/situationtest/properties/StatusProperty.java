package com.souchy.randd.situationtest.properties;

import com.souchy.randd.situationtest.properties.types.StatusProperties;

public class StatusProperty {

	public final StatusProperties statusProp;
	public int value;
	
	public StatusProperty(StatusProperties statusProp, int value) {
		this.statusProp = statusProp;
		this.value  = value;
	}

}
