package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.rules.Rules;

public class ContentController {
	private ArrayList<TableContent> contents; 
	
	
	public ContentController(){
		contents = new ArrayList<TableContent>();
	}
	
	public void addContent(TableContent tc){
		contents.add(tc);
	}
	
	public TableContent getActualContent(){
		return contents.get(contents.size()-1);
	}
	
	public boolean isNode(Tablescheme schema){
		return Rules.isNode(schema);
	}
	
	public ArrayList<TableContent> getContent(){
		return contents;
	}
	
	
	public ArrayList<TableContent> getNodes(){
		
		ArrayList<TableContent> nodes = new ArrayList<TableContent>();
		
		for(TableContent e : contents){
			if(Rules.isNode(e.getTableScheme())){
				
				nodes.add(e);
				
				
			}
				
		}
		return nodes;
	}
	public ArrayList<TableContent> getRelationships(){
		
		ArrayList<TableContent> rels = new ArrayList<TableContent>();
		
		for(TableContent e : contents){
			if(Rules.isRelationship(e.getTableScheme()))
				rels.add(e);
		}
		return rels;
	}
	
	
}
