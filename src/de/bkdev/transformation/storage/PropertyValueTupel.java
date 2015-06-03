package de.bkdev.transformation.storage;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.TableReference;


public class PropertyValueTupel{
	private Property prop;
	private String value;
	
	public PropertyValueTupel(Property p, String val) {
		this.prop = p;
		this.value = val;
	}

	public Property getProperty() {
		return prop;
	}
	
	public String getKey() {
		return prop.getName();
	}

	public String getValue() {
		return value;
	}
	
	public boolean isPrimaryKey(){
		return prop.isPrimaryKey();
	}
	public boolean isForeignKey(){
		return prop.isForeignKey();
	}
	
	public TableReference getTableReference(){
		return prop.getReference();
	}
}
