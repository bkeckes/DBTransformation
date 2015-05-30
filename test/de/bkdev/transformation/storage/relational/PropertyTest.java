package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyTest {
	
	@Test
	public void testePropertyGueltigkeit(){
		Property p = new Property(true, null, "int", "");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, null, "", "");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, null, "", "name");
		assertFalse(p.isPropertyValid());
		
		p = new Property(true, new TableReference("Us",  "as"), "char", "name");
		assertFalse(p.isPropertyValid());
		
		p = new Property(false, null, "int", "alter");
		assertTrue(p.isPropertyValid());
		
		p = new Property(false, new TableReference("Us",  "as"), "int", "alter");
		assertTrue(p.isPropertyValid());
		assertTrue(p.isForeignKey());
		
		p = new Property(true, null, "int", "alter");
		assertTrue(p.isPropertyValid());
		
		assertTrue(p.isPrimaryKey());
	}
	
	@Test
	public void testePrimaryKey(){
		Property p = new Property(true, null, "int", "");
		assertTrue(p.isPrimaryKey());
	}
}
