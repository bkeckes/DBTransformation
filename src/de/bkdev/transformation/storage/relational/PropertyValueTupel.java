package de.bkdev.transformation.storage.relational;


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
