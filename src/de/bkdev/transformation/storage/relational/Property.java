package de.bkdev.transformation.storage.relational;

/**
 * Stellt eine Eigenschaft der Tabelle dar.
 * @author Benjamin Keckes
 *
 */
public class Property {
	private boolean isPrimaryKey;
	private boolean isForeignKey;
	private String type;	//TODO Type sollte ein enum sein dementsprechend Attributdatentypen richtig zuweisen.
	private String name;
	private TableReference reference;
	
	
	public Property(boolean p, TableReference ref, String type, String name){
		this.isPrimaryKey = p;
		this.reference = ref;
		this.type = type;
		this.name = name;
		
	}

	public TableReference getReference(){
		return reference;
	}
	public String getRefTable() {
		return reference.getTable();
	}
	public String getName() {
		return name;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public boolean isForeignKey() {
		if(this.reference==null)
			return false;
		return true;
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
		if(reference!=null && !reference.isValid())
			return false;
		
		
		return true;
	}
	
}
