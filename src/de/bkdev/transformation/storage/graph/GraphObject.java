package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import de.bkdev.transformation.DatabaseReader;
import de.bkdev.transformation.storage.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.Property;


/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	private static final Logger log4j = LogManager.getLogger(GraphObject.class
	        .getName());
	
	private ArrayList<PropertyValueTupel> attributeList;
	
	public void setAttributeList(ArrayList<PropertyValueTupel> attributeList) {
		this.attributeList = attributeList;
	}

	public GraphObject(){
		attributeList = new ArrayList<PropertyValueTupel>();
	}
	
	public ArrayList<PropertyValueTupel> getPropertySet() {
		return attributeList;
	}
	
	public void addProperty(Property key, String value){
		attributeList.add(new PropertyValueTupel(key, value));
	}
	
	public int getPropertyCount(){
		return attributeList.size();
	}
	
	public String getPropertyString(String key){
		for(PropertyValueTupel e : attributeList){
			if(e.getKey().equals(key))
				return e.getKey() + ":'" + e.getValue() + "'";
		}
		return "";
	}

	
	public String getAllPropertysInString(){
		String temp = "";
		for(PropertyValueTupel e : attributeList){
			String value = e.getValue().replace("'", "");
			try{
				if(isNumeric(value))
					temp += e.getKey() + ":" + value + ", ";
				else
					temp += e.getKey() + ":'" + value + "', ";
			}catch(NullPointerException a){
				log4j.error("Could not find key in "+ temp);
			}
		}
		return temp.substring(0, temp.length()-2);
	}
	
	
	public String getPropertyStringForStatement(){
		String temp ="";
		for(PropertyValueTupel e : attributeList){
			//e.nextElement()
			temp += getPropertyString(e.getValue()) + " ";
		}
		
		return temp;
	}
	
	
	public String getPropertyValue(String key){
		for(PropertyValueTupel e : attributeList){
			if(e.getKey().equals(key))
				return e.getValue();
		}
		return null;
	}
	
	public PropertyValueTupel getPrimaryKey(){
		for(PropertyValueTupel e: attributeList){
			if(e.isPrimaryKey())
				return e;
		}
		throw new NullPointerException("No PK found " + this.toString());
	}
	
	private boolean isNumeric(String possibleNumber) {
		if(possibleNumber.charAt(0)=='0')
			return false;
		try  
		  {  
		    double d = Double.parseDouble(possibleNumber);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
			
		  return true;  
	}
}
