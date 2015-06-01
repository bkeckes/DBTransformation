package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.rules.Rules;

public class SchemaController {
	private ArrayList<Tableschema> schemes;
	
	public SchemaController(){
		schemes = new ArrayList<Tableschema>();
	}
	public void addScheme(Tableschema scheme){
		schemes.add(scheme);
	}
	public Tableschema getActualScheme(){
		return schemes.get(schemes.size()-1);
	}
	/**
	 * TODO Exception werfen wenn das Schema nicht gefunden wurde.
	 */
	public Tableschema getScheme(String schemeName){
		
		for(Tableschema e : schemes){
			if(e.getName().equals(schemeName))
				return e;
		}
		return null;
	}
	public ArrayList<Tableschema> getSchemes() {
		return schemes;
	}
	
	public ArrayList<Tableschema> getNodeSchemes() {
		ArrayList<Tableschema> list = new ArrayList<>();
		
		for(Tableschema s : schemes){
			if(Rules.isNode(s))
				list.add(s);
		}
		return list;
	}
}
