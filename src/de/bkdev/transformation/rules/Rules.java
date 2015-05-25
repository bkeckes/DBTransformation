package de.bkdev.transformation.rules;


import de.bkdev.transformation.storage.relational.Tablescheme;

public class Rules {
	
	public static boolean isNode(Tablescheme table){
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
	public static boolean isRelationship(Tablescheme table){
		return !isNode(table);
	}
}
