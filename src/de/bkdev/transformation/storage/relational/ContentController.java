package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

import de.bkdev.transformation.storage.relational.template.TableContent;

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
}
