package de.bkdev.transformation.storage.relational.template;

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
	
	public int getValueCount(){
		return attr.size();
	}
	public ArrayList<PropertyValueTupel> getAttributes(){
		return attr;
	}
	public PropertyValueTupel getFirstForeignKey(){
		
		for (PropertyValueTupel p : attr){
			if(p.getProperty().isForeignKey()){
				return p;
			}
		}
		return null;
	}
	
	public PropertyValueTupel getForeignKeyAfter(PropertyValueTupel tupel){
		
		for(int i=attr.indexOf(tupel)+1; i<attr.size(); i++){
			if(attr.get(i).getProperty().isForeignKey())
				return attr.get(i);
		}
		return null;
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
