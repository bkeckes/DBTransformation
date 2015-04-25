package de.bkdev.transformation;

import java.util.HashSet;
import java.util.Iterator;

public class Node {
	private String label;
	private HashSet<Property> propertySet;
	
	public Node(String label){
		this.label = label;
		propertySet = new HashSet<Property>();
	}
	public String toString(){
		String temp = "("+label+")->";
		Iterator<Property> it = propertySet.iterator();
		while(it.hasNext()){
			temp += it.next().toString()+" ";
		}
		return temp;
	}
	
	public int getPropertyCount(){
		return propertySet.size();
	}
	
	//TODO Element soll nicht eingefügt werden wenn key und value schon exisitiert.
	//		Wenn sich value ändert soll Element überschrieben werden.
	public void addProperty(Property property){
		if(!propertySet.contains(property))
			propertySet.add(property);
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public HashSet<Property> getPropertySet() {
		return propertySet;
	}
	
	
}
