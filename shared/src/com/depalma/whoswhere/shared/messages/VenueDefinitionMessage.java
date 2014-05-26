package com.depalma.whoswhere.shared.messages;

import java.util.ArrayList;

import com.depalma.whoswhere.shared.CoordinatesD;
import com.depalma.whoswhere.shared.Message;

public class VenueDefinitionMessage extends Message {

	private String vName;
	private ArrayList<CoordinatesD> geomDef;
	
	public void setName(String name) {
		vName = name;
	}
	
	public String getName() {
		return vName;
	}
	
	public void setGeom(ArrayList<CoordinatesD> coords) {
		geomDef = coords;
	}
	
	public ArrayList<CoordinatesD> getGeom() {
		return geomDef;
	}
}
