package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableschemaTest {

	
	
	@Test
	public void testeTabellenName(){
		Tableschema table = new Tableschema("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals("US", table.getTableName());
	}
	
	@Test
	public void testePrimaryKey(){
		Tableschema table = new Tableschema("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals("id", table.getPrimaryKey().getName());
		assertEquals("varchar(20)", table.getPrimaryKey().getType());	
	}
	
	@Test
	public void testeForeignKey(){
		Tableschema table = new Tableschema("US");
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
		Tableschema table = new Tableschema(null);
		table.addProperty(new Property(true, new TableReference("", ""), "int", "name"));
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle2(){
		Tableschema table = new Tableschema("");
		table.addProperty(new Property(true, new TableReference("", ""), "int", "name"));
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		assertFalse(table.isTableValid());
	}
	@Test
	public void testeInvalideTabelle3(){
		Tableschema table = new Tableschema("BG");
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
		Tableschema table = new Tableschema("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertTrue(table.hasPrimaryKey());
	}
	
	@Test
	public void testeAufFalschenPrimaryKey(){
		Tableschema table = new Tableschema("US");
		table.addProperty(new Property(false, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertFalse(table.hasPrimaryKey());
	}
}
