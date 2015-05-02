package de.bkdev.transformation.storage;

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
	
	public GraphObject(){
		property = new Hashtable<String, String>();
	}
	
	public Hashtable<String, String> getPropertySet() {
		return property;
	}
	
	//TODO Element soll nicht eingefügt werden wenn key und value schon exisitiert.
		//		Wenn sich value ändert soll Element überschrieben werden.
	public void addProperty(String key, String value){
		property.put(key, value);
	}
	
	public int getPropertyCount(){
		return property.size();
	}
	
	public String getPropertyString(String key){
		return key + ":'" + property.get(key) + "'";
	}
	
	public String getAllPropertysInString(){
		String temp="";
		Enumeration<String> e = property.keys();
		
		while(e.hasMoreElements()){
			temp += getPropertyString(e.nextElement()) +" ";
		}
		
		return temp;
	}
	
	public String getPropertyValue(String key){
		return property.get(key);
	}
}
