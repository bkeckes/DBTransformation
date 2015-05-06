package de.bkdev.transformation.inspector;

import de.bkdev.transformation.storage.graph.template.GDBNode;
import de.bkdev.transformation.storage.graph.template.GDBRelation;
import de.bkdev.transformation.storage.graph.template.GDBTemplate;
import de.bkdev.transformation.storage.relational.Table;

public class InspectorImpl implements InspectorController{

	@Override
	public GDBTemplate getObject(Table table) {

		if(!table.isTableValid())
			return null;
		
		
		if(table.hasPrimaryKey()){
			if(table.getForeignKeyCount()>0){
				return new GDBRelation(table.getName());
			}
			else{
				return new GDBNode(table.getName());
			}
		}
		else{
			if(table.getForeignKeyCount()>0){
				return new GDBRelation(table.getName());
			}
		}
		return null;
	}


}
