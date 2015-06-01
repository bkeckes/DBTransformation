package de.bkdev.transformation.rules;


import de.bkdev.transformation.storage.relational.Tableschema;

public class Rules {
	
	public static boolean isNode(Tableschema table){
		if(!table.isTableValid())
			return false;
		
		
		if(table.hasPrimaryKey()){
			if(table.getForeignKeyCount()!=2){
				if(table.getForeignKeyCount() != (table.getPropertyCount()-1))
						return true;
				else
						return false;
			}
			else{
				if(table.getNonKeyPropertyCount() >0)
					return true;
				else
					return false;
			}
		}else{
			if(table.getForeignKeyCount()!=2){
				return true;
			}
			else{
				return false;
			}
		}
	
	}
	/*
	 * TODO
	 */
	public static boolean isRelationship(Tableschema table){
		return !isNode(table);
	}
}
