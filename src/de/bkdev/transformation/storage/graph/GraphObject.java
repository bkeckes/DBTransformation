package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	private Hashtable<String, String> property;
	private ArrayList<KeyValuePair> attr;
	
	public GraphObject(){
		property = new Hashtable<String, String>();
		attr = new ArrayList<KeyValuePair>();
	}
	
	public Hashtable<String, String> getPropertySet() {
		return property;
	}
	
	//TODO Element soll nicht eingefügt werden wenn key und value schon exisitiert.
	//		Wenn sich value ändert soll Element überschrieben werden.
	public void addProperty(String key, String value){
		property.put(key, value);
		attr.add(new KeyValuePair(key, value));
	}
	
	public int getPropertyCount(){
		return attr.size();
	}
	
	public String getPropertyString(String key){
		
		return key + ":'" + property.get(key) + "'";
	}
	
	public String getAllPropertysInString(){
		String temp = "";
		for(int i=0; i<attr.size(); i++){
			temp += attr.get(i).getKey() + ":'" + attr.get(i).getValue() + "', ";
		}
		return temp.substring(0, temp.length()-2);
	}
	
	/*
	 * public String getAllPropertysInString(){
	 
		String temp="";
		Enumeration<String> e = property.keys();
		
		while(e.hasMoreElements()){
			temp += getPropertyString(e.nextElement()) +", ";
		}
		
		return temp.substring(0, temp.length()-2);
	}*/
	public String getPropertyStringForStatement(){
		String temp="";
		Enumeration<String> e = property.keys();
		
		while(e.hasMoreElements()){
			//e.nextElement()
			temp += getPropertyString(e.nextElement()) +" ";
		}
		
		return temp;
	}
	
	
	public String getPropertyValue(String key){
		return property.get(key);
	}
}
