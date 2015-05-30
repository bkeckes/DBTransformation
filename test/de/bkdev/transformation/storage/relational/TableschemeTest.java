package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableschemeTest {

	
	
	@Test
	public void testeTabellenName(){
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals("US", table.getName());
	}
	
	@Test
	public void testePrimaryKey(){
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals("id", table.getPrimaryKey().getName());
		assertEquals("varchar(20)", table.getPrimaryKey().getType());	
	}
	
	@Test
	public void testeForeignKey(){
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals(0, table.getForeignKeyCount());
		table.addProperty(new Property(false, new TableReference("", ""), "int", "user"));
		assertEquals(1, table.getForeignKeyCount());
		table.addProperty(new Property(false, new TableReference("", ""), "int", "blog"));
		assertEquals(2, table.getForeignKeyCount());
		
	}
	
	@Test
	public void testeInvalideTabelle(){
		Tablescheme table = new Tablescheme(null);
		table.addProperty(new Property(true, new TableReference("", ""), "int", "name"));
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle2(){
		Tablescheme table = new Tablescheme("");
		table.addProperty(new Property(true, new TableReference("", ""), "int", "name"));
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle3(){
		Tablescheme table = new Tablescheme("BG");
		assertFalse(table.isTableValid());
		
		table.addProperty(null);
		assertFalse(table.isTableValid());
		
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertTrue(table.isTableValid());
		
	}
	
	@Test
	public void testeAufPrimaryKey(){
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertTrue(table.hasPrimaryKey());
	}
	
	@Test
	public void testeAufFalschenPrimaryKey(){
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(false, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertFalse(table.hasPrimaryKey());
	}
}
