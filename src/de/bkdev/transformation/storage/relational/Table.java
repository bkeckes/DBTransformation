package de.bkdev.transformation.storage.relational;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Table {
	private String name;
	private Set<Property> property;
	
	public Table(String n){
		this.name = n;
		this.property = new HashSet<Property>();
	}
	
	public void addProperty(Property prop){
		if(prop!=null)
			property.add(prop);
	}

	public String getName() {
		return name;
	}
	
	public Property getPrimaryKey(){
		Iterator<Property> it = property.iterator();
		
		while(it.hasNext()){
			Property p = it.next();
			if(p.isPrimaryKey())
				return p;
		}
		return null;
	}
	
	public int getForeignKeyCount(){
		Iterator<Property> it = property.iterator();
		int count=0;
		while(it.hasNext()){
			Property p = it.next();
			if(p.isForeignKey())
				count++;
		}
		return count;
	}
	
	public boolean isTableValid(){
		//Ist Name gesetzt?
		if(name!=null && !name.isEmpty()){
			
			//Sind alle Propertys in Ordnung?
			for(Property p : property){
				if(!p.isPropertyValid())
					return false;
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	
}
