package de.bkdev.transformation.storage.relational;

public class TableReference {
	private String tableName;
	private String attributeName;
	
	public TableReference(String table, String attribute){
		this.tableName = table;
		this.attributeName = attribute;
	}

	public String getTable() {
		return tableName;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
	@Override
	public String toString(){
		return tableName + "." + attributeName;
	}
	
	public boolean isValid(){
		if(tableName==null)
			return false;
		else if(attributeName==null)
			return false;
		else
			return true;
	}
}
