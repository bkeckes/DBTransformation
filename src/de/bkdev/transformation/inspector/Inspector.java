package de.bkdev.transformation.inspector;

import de.bkdev.transformation.storage.graph.GraphObject;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.Table;

public class Inspector {
	
	public Inspector(){
		
	}
	
	public GraphObject getObject(Table table){
		
		return new Node("US");
	}
}
