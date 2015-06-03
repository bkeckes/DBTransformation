package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.rules.Rules;

public class SchemaController {
	private ArrayList<Tableschema> schemas;
	
	public SchemaController(){
		schemas = new ArrayList<Tableschema>();
	}
	public void addSchema(Tableschema scheme){
		schemas.add(scheme);
	}
	public Tableschema getActualScheme(){
		return schemas.get(schemas.size()-1);
	}
	/**
	 * TODO Exception werfen wenn das Schema nicht gefunden wurde.
	 */
	public Tableschema getSchema(String schemeName){
		
		for(Tableschema e : schemas){
			if(e.getTableName().equals(schemeName))
				return e;
		}
		return null;
	}
	public ArrayList<Tableschema> getSchemas() {
		return schemas;
	}
	
	public ArrayList<Tableschema> getNodeSchemas() {
		ArrayList<Tableschema> list = new ArrayList<>();
		
		for(Tableschema s : schemas){
			if(Rules.isNode(s))
				list.add(s);
		}
		return list;
	}
}
