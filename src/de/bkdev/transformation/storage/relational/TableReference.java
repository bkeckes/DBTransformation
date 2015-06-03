package de.bkdev.transformation.storage.relational;

public class TableReference {
	private String table;
	private String attributeName;
	
	public TableReference(String table, String attribute){
		this.table = table;
		this.attributeName = attribute;
	}

	public String getTable() {
		return table;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
	@Override
	public String toString(){
		return table + "." + attributeName;
	}
	
	public boolean isValid(){
		if(table==null)
			return false;
		else if(attributeName==null)
			return false;
		else
			return true;
	}
}
