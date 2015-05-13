package de.bkdev.transformation.storage.relational.template;

import de.bkdev.transformation.storage.relational.Property;

public class PropertyValueTupel {
	private Property prop;
	private String value;
	
	public PropertyValueTupel(Property p, String val) {
		this.prop = p;
		this.value = val;
	}

	public Property getProperty() {
		return prop;
	}

	public String getValue() {
		return value;
	}
	
	
}
