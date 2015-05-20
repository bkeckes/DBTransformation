package de.bkdev.transformation.storage.relational.template;

import java.util.ArrayList;
import java.util.Iterator;

import de.bkdev.transformation.inspector.InspectorController;
import de.bkdev.transformation.inspector.InspectorImpl;
import de.bkdev.transformation.rules.Rules;

public class TableList {
	private ArrayList<TableContent> list;
	
	public TableList(){
		list = new ArrayList<TableContent>();
	}
	
	
	public void add(TableContent tc){
		list.add(tc);
	}
	
	public ArrayList<TableContent> getNodes(){
		Iterator<TableContent> it = list.iterator();
		ArrayList<TableContent> nodes = new ArrayList<TableContent>();
		InspectorController inspector = new InspectorImpl();
		while(it.hasNext()){
			TableContent tc = it.next();
			if(Rules.isNode(tc.getTableScheme()))
				nodes.add(tc);
		}
		return nodes;
	}
}
