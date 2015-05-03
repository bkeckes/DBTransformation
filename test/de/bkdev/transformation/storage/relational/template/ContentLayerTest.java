package de.bkdev.transformation.storage.relational.template;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.relational.Property;

public class ContentLayerTest {

	@Test
	public void testLayer() {
		ContentLayer layer = new ContentLayer();
		assertEquals(0, layer.getValueCount());
		
		try{
			layer.addValue(new Property(false, true, "int", "id"), "cid");
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(1, layer.getValueCount());
		
		try{
			layer.addValue(new Property(false, true, "char", "name"), "date");
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(2, layer.getValueCount());
	}
	
	@Test
	public void testFehlverhalten() {
		ContentLayer layer = new ContentLayer();
		assertEquals(0, layer.getValueCount());
		
		try{
			layer.addValue(new Property(false, true, "int", "id"), "cid");
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(1, layer.getValueCount());
		
		try{
			layer.addValue(new Property(false, true, "char", "id"), "date");
			fail("There should be an Exception");
		} catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(1, layer.getValueCount());
	}

}
