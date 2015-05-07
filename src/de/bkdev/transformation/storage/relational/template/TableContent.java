package de.bkdev.transformation.storage.relational.template;

import java.util.HashSet;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyException;
import de.bkdev.transformation.storage.relational.Table;
/**
 * Diese Klasse ist für die Verwaltung der einzelnen Datensätze zuständig.
 * @author Benjamin Keckes
 *
 */
public class TableContent {
	private Table table;
	private HashSet<ContentLayer> layer;
	
	public HashSet<ContentLayer> getLayer() {
		return layer;
	}

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
		
		Property p = null;
		try{
			p = table.getPropertyByName(key);
		}
		catch(PropertyException e){
			e.printStackTrace();
		}
		
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

	public Table getTable() {
		return table;
	}

	public ContentLayer getCurrentLayer() {
		return currentLayer;
	}
}
