package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyValueTupel;

/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	private ArrayList<KeyValuePair> attr;
	
	public GraphObject(){
		attr = new ArrayList<KeyValuePair>();
	}
	
	public ArrayList<KeyValuePair> getPropertySet() {
		return attr;
	}
	
	//TODO Element soll nicht eingefügt werden wenn key und value schon exisitiert.
	//		Wenn sich value ändert soll Element überschrieben werden.
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
	
	public String getAllPropertysInString(){
		String temp = "";
		for(int i=0; i<attr.size(); i++){
			temp += attr.get(i).getKey() + ":'" + attr.get(i).getValue() + "', ";
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
		return null;
	}
	
}
