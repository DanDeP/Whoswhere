package com.depalma.whoswhere.shared;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -6096549670163590620L;
	
	private long millisTime;
	
	public long getMillisTime() {
		return millisTime;
	}
	
	public void setMillisTime(long millis) {
		millisTime = millis;
	}
}
