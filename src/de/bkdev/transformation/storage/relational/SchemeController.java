package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.rules.Rules;

public class SchemeController {
	private ArrayList<Tablescheme> schemes;
	
	public SchemeController(){
		schemes = new ArrayList<Tablescheme>();
	}
	public void addScheme(Tablescheme scheme){
		schemes.add(scheme);
	}
	public Tablescheme getActualScheme(){
		return schemes.get(schemes.size()-1);
	}
	/**
	 * TODO Exception werfen wenn das Schema nicht gefunden wurde.
	 */
	public Tablescheme getScheme(String schemeName){
		
		for(Tablescheme e : schemes){
			if(e.getName().equals(schemeName))
				return e;
		}
		return null;
	}
	public ArrayList<Tablescheme> getSchemes() {
		return schemes;
	}
	
	public ArrayList<Tablescheme> getNodeSchemes() {
		ArrayList<Tablescheme> list = new ArrayList<>();
		
		for(Tablescheme s : schemes){
			if(Rules.isNode(s))
				list.add(s);
		}
		return list;
	}
}
