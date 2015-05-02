package de.bkdev.transformation;

import java.util.HashSet;
import java.util.Iterator;

/**
 * This class is for Nodes and Relations.
 * @author Benjamin Keckes
 *
 */
public abstract class GraphObject {
	protected HashSet<Property> propertySet;
	
	public GraphObject(){
		propertySet = new HashSet<Property>();
	}
	
	public HashSet<Property> getPropertySet() {
		return propertySet;
	}
	
	//TODO Element soll nicht eingefügt werden wenn key und value schon exisitiert.
		//		Wenn sich value ändert soll Element überschrieben werden.
	public void addProperty(Property property){
		if(!propertySet.contains(property))
			propertySet.add(property);
	}
	
	public int getPropertyCount(){
		return propertySet.size();
	}
	
	public String getPropertyString(){
		String temp="";
		Iterator<Property> it = propertySet.iterator();
		while(it.hasNext()){
			temp += it.next().toString()+" ";
		}
		return temp;
	}
}
