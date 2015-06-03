package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.rules.Rules;

public class ContentController {
	private ArrayList<TableContent> contentList; 
	
	
	public ContentController(){
		contentList = new ArrayList<TableContent>();
	}
	
	public void addContent(TableContent tc){
		contentList.add(tc);
	}
	
	public TableContent getActualContent(){
		return contentList.get(contentList.size()-1);
	}
	
	public boolean isNode(Tableschema schema){
		return Rules.isNode(schema);
	}
	
	public ArrayList<TableContent> getContent(){
		return contentList;
	}
	
	
	public ArrayList<TableContent> getNodes(){
		
		ArrayList<TableContent> nodes = new ArrayList<TableContent>();
		
		for(TableContent e : contentList){
			if(Rules.isNode(e.getTableSchema())){
				
				nodes.add(e);
				
				
			}
				
		}
		return nodes;
	}
	public ArrayList<TableContent> getRelationships(){
		
		ArrayList<TableContent> rels = new ArrayList<TableContent>();
		
		for(TableContent e : contentList){
			if(Rules.isRelationship(e.getTableSchema()))
				rels.add(e);
		}
		return rels;
	}
	
	
}
