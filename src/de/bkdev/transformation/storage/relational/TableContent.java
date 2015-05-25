package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

/**
 * Diese Klasse ist für die Verwaltung der einzelnen Datensätze zuständig.
 * @author Benjamin Keckes
 *
 */
public class TableContent {
	private Tablescheme table;
	private ArrayList<ContentLayer> layer;
	
	public ArrayList<ContentLayer> getLayer() {
		return layer;
	}

	private ContentLayer currentLayer;
	
	public TableContent(Tablescheme table){
		this.table = table;
		this.layer = new ArrayList<ContentLayer>();
	}
	
	public void addContentLayer(){
		
		//if(isCurrentLayerValid())
			
		
		currentLayer = new ContentLayer();
		layer.add(currentLayer);
	}
	
	public void addAttributeToCurrentLayerByIndex(int index, String value){
		if(currentLayer==null || isCurrentLayerValid())
			addContentLayer();
		
		currentLayer.addValue(table.getPropertyByIndex(index), value);
		
	}
	public void addAttributeToCurrentLayer(String key, String value) {
		if(currentLayer==null || isCurrentLayerValid())
			addContentLayer();
		
		Property p = null;
		
		p = table.getPropertyByName(key);
		
		
		currentLayer.addValue(p, value);

		
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

	public Tablescheme getTableScheme() {
		return table;
	}

	public ContentLayer getCurrentLayer() {
		return currentLayer;
	}
}
