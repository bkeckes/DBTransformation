package de.bkdev.transformation.storage.graph;

import de.bkdev.transformation.storage.relational.Property;

public class KeyValuePair {
	private Property key;
	private String value;
	
	public KeyValuePair(Property key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key.getName();
	}

	public String getValue() {
		return value;
	}
	public boolean isPrimaryKey(){
		return key.isPrimaryKey();
	}
	public boolean isForeignKey(){
		return key.isForeignKey();
	}
	
	public Property getProperty(){
		return key;
	}
}
