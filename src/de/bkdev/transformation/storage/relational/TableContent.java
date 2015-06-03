package de.bkdev.transformation.storage.relational;

import java.util.ArrayList;

/**
 * Diese Klasse ist für die Verwaltung der einzelnen Datensätze zuständig.
 * @author Benjamin Keckes
 *
 */
public class TableContent {
	private Tableschema table;
	private ArrayList<ContentLayer> layer;
	
	public ArrayList<ContentLayer> getLayer() {
		return layer;
	}

	private ContentLayer currentLayer;
	
	public TableContent(Tableschema table){
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
		
		//Wenn NULL in RDB dann wird das Attribut nicht in GDB aufgenommen
		if(value!=null)
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

	public Tableschema getTableSchema() {
		return table;
	}

	public ContentLayer getCurrentLayer() {
		return currentLayer;
	}
}
