package de.bkdev.transformation.storage.graph.template;

import java.util.Hashtable;

public abstract class GDBTemplate {
	protected String label;
	
	//type, name
	private Hashtable<String, String> property;
	
	public GDBTemplate(String label){
		this.label = label;
		property = new Hashtable<String, String>();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public abstract String identify();
}
