package de.bkdev.transformation.storage.relational;

import java.util.Hashtable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

/**
 * Stellt eine Eigenschaft der Tabelle dar.
 * @author Benjamin Keckes
 *
 */
public class Property {
	private boolean isPrimaryKey;
	private boolean isForeignKey;
	private String type;
	private String name;
	
	public Property(boolean p, boolean f, String type, String name){
		this.isPrimaryKey = p;
		this.isForeignKey = f;
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public boolean isForeignKey() {
		return isForeignKey;
	}

	public String getType() {
		return type;
	}
	
	public boolean isPropertyValid(){
		//Wenn beide gesetzt sind ist es falsch
		if(isPrimaryKey && isForeignKey)
			return false;
		if(type==null || type.isEmpty())
			return false;
		if(name==null || name.isEmpty())
			return false;
		
		return true;
	}
	
}
