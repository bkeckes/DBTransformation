package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import de.bkdev.transformation.storage.relational.*;

public class ContentLayer {
	private ArrayList<PropertyValueTupel> attr;
	
	public ContentLayer(){
		attr = new ArrayList<PropertyValueTupel>();
	}
	
	public void addValue(Property property, String value){
		
		attr.add(new PropertyValueTupel(property, value));
		
	}
	
	public PropertyValueTupel getPrimaryKey(){
		for(PropertyValueTupel p : attr){
			if(p.getProperty().isPrimaryKey())
				return p;
		}
		return null;
	}
	
	public int getValueCount(){
		return attr.size();
	}
	public ArrayList<PropertyValueTupel> getAttributes(){
		return attr;
	}
	
	
	public PropertyValueTupel getForeignKeyAt(int index){
		ArrayList<PropertyValueTupel> fks = new ArrayList<PropertyValueTupel>();
		
		for(PropertyValueTupel p : attr){
			if(p.getProperty().isForeignKey())
				fks.add(p);
		}
		return fks.get(index);
	}
	
	
	
	public int getForeignKeyCount(){
		int c=0;
		for (PropertyValueTupel p : attr){
			if(p.getProperty().isForeignKey()){
				c++;
			}
		}
		return c;
	}
	
	private boolean hasProperty(String key){
		
		for (PropertyValueTupel p : attr){
			if(p.getProperty().getName().equals(key)){
				return true;
			}
		}
		return false;
	}
}