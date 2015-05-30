package de.bkdev.transformation.storage.relational;

public class TableReference {
	private String table;
	private String attribute;
	
	public TableReference(String table, String attribute){
		this.table = table;
		this.attribute = attribute;
	}

	public String getTable() {
		return table;
	}

	public String getAttribute() {
		return attribute;
	}
	
	@Override
	public String toString(){
		return table + "." + attribute;
	}
}
