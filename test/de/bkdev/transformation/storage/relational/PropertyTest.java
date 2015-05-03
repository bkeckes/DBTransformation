package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyTest {
	
	@Test
	public void testePropertyGueltigkeit(){
		Property p = new Property(true, false, "int", "");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, false, "", "");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, false, "", "name");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, true, "char", "name");
		assertFalse(p.isPropertyValid());
		
		p = new Property(false, false, "int", "alter");
		assertTrue(p.isPropertyValid());
		
		p = new Property(false, true, "int", "alter");
		assertTrue(p.isPropertyValid());
		assertTrue(p.isForeignKey());
		
		p = new Property(true, false, "int", "alter");
		assertTrue(p.isPropertyValid());
		
		assertTrue(p.isPrimaryKey());
	}
	
	@Test
	public void testePrimaryKey(){
		Property p = new Property(true, false, "int", "");
		assertTrue(p.isPrimaryKey());
	}
}
