package de.bkdev.transformation.storage.relational.template;

import java.util.Enumeration;
import java.util.Hashtable;
import de.bkdev.transformation.storage.relational.*;

public class ContentLayer {
	private Hashtable<Property, String> tupel;
	
	public ContentLayer(){
		tupel = new Hashtable<Property, String>();
	}
	
	public void addValue(Property property, String value) throws TableException{
		if(!tupel.contains(property) && !hasProperty(property.getName()))
			tupel.put(property, value);
		else
			throw new TableException();
	}
	
	public Hashtable<Property, String> getTupel(){
		return tupel;
	}
	
	public int getValueCount(){
		return tupel.size();
	}
	
	private boolean hasProperty(String key){
		Enumeration<Property> propEnum = tupel.keys();
		
		while(propEnum.hasMoreElements()){
			Property prop = propEnum.nextElement();
			if(prop.getName().equals(key))
				return true;
		}
		return false;
	}
}
