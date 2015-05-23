package de.bkdev.transformation.storage.relational.template;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.relational.Property;

public class ContentLayerTest {

	@Test
	public void testLayer() {
		ContentLayer layer = new ContentLayer();
		assertEquals(0, layer.getValueCount());
		
		
		layer.addValue(new Property(false, true, "int", "id"), "cid");
	
		assertEquals(1, layer.getValueCount());
		
		layer.addValue(new Property(false, true, "char", "name"), "date");
		
		assertEquals(2, layer.getValueCount());
	}
	/**
	 * TODO
	 */
	/*@Test
	public void testFehlverhalten() {
		ContentLayer layer = new ContentLayer();
		assertEquals(0, layer.getValueCount());
		
		layer.addValue(new Property(false, true, "int", "id"), "cid");

		assertEquals(1, layer.getValueCount());
		layer.addValue(new Property(false, true, "char", "id"), "date");


		assertEquals(1, layer.getValueCount());
	}
	*/
	
	@Test
	public void testForeignKeyGetter(){
		ContentLayer layer = new ContentLayer();
		
		layer.addValue(new Property(false, true, "int", "fuser"), "c01");
		layer.addValue(new Property(false, true, "int", "fblod"), "b01");
		
		
		PropertyValueTupel prop = layer.getForeignKeyAt(0);
		assertEquals("fuser", prop.getProperty().getName());
		assertEquals("c01", prop.getValue());
	}
	
	@Test
	public void testForeignKeyGetter2(){
		ContentLayer layer = new ContentLayer();
		
		layer.addValue(new Property(false, true, "int", "fuser"), "c01");
		layer.addValue(new Property(false, true, "int", "fblod"), "b01");
		
		
		PropertyValueTupel prop = layer.getForeignKeyAt(0);
		assertEquals("fuser", prop.getProperty().getName());

		PropertyValueTupel second = layer.getForeignKeyAt(1);
		assertEquals("fblod", second.getProperty().getName());
	}
	
	@Test
	public void testForeignKeyGetterMitAnderenWerten(){
		ContentLayer layer = new ContentLayer();
		
		layer.addValue(new Property(true, false, "int", "id"), "1");
		layer.addValue(new Property(false, true, "int", "fuser"), "c01");
		layer.addValue(new Property(false, false, "int", "date"), "18.12.");
		layer.addValue(new Property(false, true, "int", "fblod"), "b01");
		
		
		PropertyValueTupel prop = layer.getForeignKeyAt(0);
		assertEquals("fuser", prop.getProperty().getName());

		PropertyValueTupel second = layer.getForeignKeyAt(1);
		assertEquals("fblod", second.getProperty().getName());
	}
	
	@Test
	public void testForeignKeyCount(){
		ContentLayer layer = new ContentLayer();
		
		assertEquals(0, layer.getForeignKeyCount());
		layer.addValue(new Property(true, false, "int", "id"), "1");
		layer.addValue(new Property(false, true, "int", "fuser"), "c01");
		assertEquals(1, layer.getForeignKeyCount());
		layer.addValue(new Property(false, false, "int", "date"), "18.12.");
		layer.addValue(new Property(false, true, "int", "fblod"), "b01");
		
		
		assertEquals(2, layer.getForeignKeyCount());
	}


	@Test
	public void testForeignKeys(){
		ContentLayer layer = new ContentLayer();
		layer.addValue(new Property(true, false, "int", "id"), "1");
		layer.addValue(new Property(false, true, "int", "fuser"), "c01");
		layer.addValue(new Property(false, true, "int", "fblod"), "b01");
		
		PropertyValueTupel first = layer.getForeignKeyAt(0);
		assertEquals("fuser", first.getProperty().getName());
		
		PropertyValueTupel second = layer.getForeignKeyAt(1);
		assertEquals("fblod", second.getProperty().getName());
		
	}
}
