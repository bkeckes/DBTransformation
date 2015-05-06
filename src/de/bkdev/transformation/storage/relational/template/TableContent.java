package de.bkdev.transformation.storage.relational.template;

import java.util.HashSet;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.Table;

public class TableContent {
	private Table table;
	private HashSet<ContentLayer> layer;
	
	private ContentLayer currentLayer;
	
	public TableContent(Table table){
		this.table = table;
		this.layer = new HashSet<ContentLayer>();
	}
	
	public void addContentLayer(){
		
		//if(isCurrentLayerValid())
			
		
		currentLayer = new ContentLayer();
		layer.add(currentLayer);
	}
	public void addAttributeToCurrentLayer(String key, String value) {
		if(currentLayer==null || isCurrentLayerValid())
			addContentLayer();
		
		Property p = table.getPropertyByName(key);
		
		try{
			currentLayer.addValue(p, value);
		}catch(TableException e){
			e.printStackTrace();
		}
		
	}
	
	public boolean isCurrentLayerValid(){
		return isLayerValid(currentLayer);
		//return currentLayer.getValueCount() == table.getPropertyCount();
	}
	
	public boolean isLayerValid(ContentLayer c){
		return c.getValueCount() == table.getPropertyCount();
	}
	
	public int getLayerCount(){
		return layer.size();
	}
	
	public boolean isLayerSetValid(){
		for(ContentLayer c : layer){
			if(!isLayerValid(c))
				return false;
		}
		return true;
	}
	//public ContentLayer getLayer()
}