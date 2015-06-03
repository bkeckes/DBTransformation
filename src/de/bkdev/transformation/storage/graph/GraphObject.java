package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import de.bkdev.transformation.DatabaseReader;
import de.bkdev.transformation.storage.relational.Property;


/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	private static final Logger log4j = LogManager.getLogger(GraphObject.class
	        .getName());
	
	private ArrayList<KeyValuePair> attributeList;
	
	public GraphObject(){
		attributeList = new ArrayList<KeyValuePair>();
	}
	
	public ArrayList<KeyValuePair> getPropertySet() {
		return attributeList;
	}
	
	public void addProperty(Property key, String value){
		attributeList.add(new KeyValuePair(key, value));
	}
	
	public int getPropertyCount(){
		return attributeList.size();
	}
	
	public String getPropertyString(String key){
		for(KeyValuePair e : attributeList){
			if(e.getKey().equals(key))
				return e.getKey() + ":'" + e.getValue() + "'";
		}
		return "";
	}

	
	public String getAllPropertysInString(){
		String temp = "";
		for(int i=0; i<attributeList.size(); i++){
			try{
				temp += attributeList.get(i).getKey() + ":'" + attributeList.get(i).getValue().replace("'", "") + "', ";
			}catch(NullPointerException e){
				log4j.error("Could not find key in "+ temp);
			}
		}
		return temp.substring(0, temp.length()-2);
	}
	
	
	public String getPropertyStringForStatement(){
		String temp ="";
		for(KeyValuePair e : attributeList){
			//e.nextElement()
			temp += getPropertyString(e.getValue()) + " ";
		}
		
		return temp;
	}
	
	
	public String getPropertyValue(String key){
		for(KeyValuePair e : attributeList){
			if(e.getKey().equals(key))
				return e.getValue();
		}
		return null;
	}
	
	public KeyValuePair getPrimaryKey(){
		for(KeyValuePair e: attributeList){
			if(e.isPrimaryKey())
				return e;
		}
		throw new NullPointerException("No PK found " + this.toString());
	}
	
}
