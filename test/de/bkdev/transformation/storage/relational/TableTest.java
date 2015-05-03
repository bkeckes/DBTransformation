package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableTest {

	
	
	@Test
	public void testeTabellenName(){
		Table table = new Table("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertEquals("US", table.getName());
	}
	
	@Test
	public void testePrimaryKey(){
		Table table = new Table("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertEquals("id", table.getPrimaryKey().getName());
		assertEquals("varchar(20)", table.getPrimaryKey().getType());	
	}
	
	@Test
	public void testeForeignKey(){
		Table table = new Table("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertEquals(0, table.getForeignKeyCount());
		table.addProperty(new Property(false, true, "int", "user"));
		assertEquals(1, table.getForeignKeyCount());
		table.addProperty(new Property(false, true, "int", "blog"));
		assertEquals(2, table.getForeignKeyCount());
		
	}
	
	@Test
	public void testeInvalideTabelle(){
		Table table = new Table(null);
		table.addProperty(new Property(true, true, "int", "name"));
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle2(){
		Table table = new Table("");
		table.addProperty(new Property(true, true, "int", "name"));
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle3(){
		Table table = new Table("BG");
		assertFalse(table.isTableValid());
		
		table.addProperty(null);
		assertFalse(table.isTableValid());
		
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertTrue(table.isTableValid());
		
	}
	
	@Test
	public void testeAufPrimaryKey(){
		Table table = new Table("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertTrue(table.hasPrimaryKey());
	}
	
	@Test
	public void testeAufFalschenPrimaryKey(){
		Table table = new Table("US");
		table.addProperty(new Property(false, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		assertFalse(table.hasPrimaryKey());
	}
}
