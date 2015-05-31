package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import de.bkdev.transformation.parser.DatabaseReader;
import de.bkdev.transformation.storage.relational.Property;


/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	private static final Logger log4j = LogManager.getLogger(GraphObject.class
	        .getName());
	
	private ArrayList<KeyValuePair> attr;
	
	public GraphObject(){
		attr = new ArrayList<KeyValuePair>();
	}
	
	public ArrayList<KeyValuePair> getPropertySet() {
		return attr;
	}
	
	public void addProperty(Property key, String value){
		attr.add(new KeyValuePair(key, value));
	}
	
	public int getPropertyCount(){
		return attr.size();
	}
	
	public String getPropertyString(String key){
		for(KeyValuePair e : attr){
			if(e.getKey().equals(key))
				return e.getKey() + ":'" + e.getValue() + "'";
		}
		return "";
	}
	
	public String getProperyValue(String key){
		for(KeyValuePair e : attr){
			if(e.getKey().equals(key))
				return e.getValue();
		}
		return "";
	}
	
	public String getAllPropertysInString(){
		String temp = "";
		for(int i=0; i<attr.size(); i++){
			try{
				temp += attr.get(i).getKey() + ":'" + attr.get(i).getValue().replace("'", "") + "', ";
			}catch(NullPointerException e){
				log4j.error("Could not find key in "+ temp);
			}
		}
		return temp.substring(0, temp.length()-2);
	}
	
	
	public String getPropertyStringForStatement(){
		String temp ="";
		for(KeyValuePair e : attr){
			//e.nextElement()
			temp += getPropertyString(e.getValue()) + " ";
		}
		
		return temp;
	}
	
	
	public String getPropertyValue(String key){
		for(KeyValuePair e : attr){
			if(e.getKey().equals(key))
				return e.getValue();
		}
		return null;
	}
	
	public KeyValuePair getPrimaryKey(){
		for(KeyValuePair e: attr){
			if(e.isPrimaryKey())
				return e;
		}
		throw new NullPointerException("No PK found " + this.toString());
	}
	
}
