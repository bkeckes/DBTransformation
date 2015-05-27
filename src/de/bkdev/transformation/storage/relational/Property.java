package de.bkdev.transformation.storage.relational;

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
	private String refTable;
	
	public Property(boolean p, boolean f, String type, String name){
		this.isPrimaryKey = p;
		this.isForeignKey = f;
		this.type = type;
		this.name = name;
		this.refTable="";
	}
	public Property(boolean p, String refTable, String type, String name){
		this(p, false, type, name);
		
		
		if(!refTable.isEmpty()){
			this.refTable = refTable;
			this.isForeignKey = true;
		}else{
			this.isForeignKey = false;
		}
	}
	
	public Property(boolean p, boolean f, String refTable, String type, String name){
		this(p, f, type, name);
		this.refTable = refTable;
	}

	public String getRefTable() {
		return refTable;
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
