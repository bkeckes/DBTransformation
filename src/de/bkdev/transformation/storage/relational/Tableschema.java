package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Tableschema {
	private final String tableName;
	private List<Property> propertyList;
	
	public Tableschema(String n){
		this.tableName = n;
		this.propertyList = new ArrayList<Property>();
	}
	
	public void addProperty(Property prop){
		if(prop!=null)
			propertyList.add(prop);
	}

	public String getTableName() {
		return tableName;
	}
	
	public boolean hasPrimaryKey(){
		for(Property p : propertyList){
			if(p.isPrimaryKey())
				return true;
		}
		return false;
	}
	
	public Property getPrimaryKey(){
		Iterator<Property> it = propertyList.iterator();
		
		while(it.hasNext()){
			Property p = it.next();
			if(p.isPrimaryKey())
				return p;
		}
		throw new NullPointerException("No PK found in '" + this.tableName + "'");
	}
	
	public int getForeignKeyCount(){
		Iterator<Property> it = propertyList.iterator();
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
		if(tableName!=null && !tableName.isEmpty()){
			
			//Sind mindestens 2 Eigenschaften vorhanden?
			if(propertyList.size()<2)
				return false;
			
			//Sind alle Propertys in Ordnung?
			for(Property p : propertyList){
				if(!p.isPropertyValid())
					return false;
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public Property getPropertyByName(String name) {
		for(Property p : propertyList){
			if(p.getName().equals(name))
				return p;
		}
		return null;
		
	}
	
	public int getNonKeyPropertyCount(){
		int count=0;
		for(Property p : propertyList){
			if(!p.isPrimaryKey() && !p.isForeignKey())
				count++;
		}
		return count;
	}
	public int getPropertyCount(){
		return propertyList.size();
	}
	
	public String getPropertyNameByIndex(int index){
		return propertyList.get(index).getName();
	}
	
	public Property getPropertyByIndex(int index){
		return propertyList.get(index);
	}
}
