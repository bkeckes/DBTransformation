package de.bkdev.transformation.rules;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.TableReference;
import de.bkdev.transformation.storage.relational.Tableschema;

public class RulesTest {

	@Test
	public void testUser() {
		//Node
		Tableschema us = new Tableschema("US");
		us.addProperty(new Property(true, null, "varchar(20)", "uid"));
		us.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		assertEquals(true, Rules.isNode(us));
		
	}
	
	@Test
	public void testBlog() {
		//Node
		Tableschema bg= new Tableschema("BG");
		bg.addProperty(new Property(true, null, "varchar(20)", "bid"));
		bg.addProperty(new Property(false, null, "varchar(20)", "name"));
		bg.addProperty(new Property(false, new TableReference("US","uid"), "varchar(20)", "admin"));
		
		assertEquals(true, Rules.isNode(bg));
		
	}
	
	@Test
	public void testKommentar() {
		//Node
		Tableschema ct = new Tableschema("CT");
		ct.addProperty(new Property(true, null, "varchar(20)", "cid"));
		ct.addProperty(new Property(false, new TableReference("BG", "bid"), "varchar(20)", "cblog"));
		ct.addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "cuser"));
		ct.addProperty(new Property(false, null, "varchar(20)", "msg"));
		ct.addProperty(new Property(false, null, "varchar(20)", "date"));
		
		assertEquals(true, Rules.isNode(ct));
		
	}
	
	@Test
	public void testNM() {
		//Node
		Tableschema fr = new Tableschema("FR");
		fr.addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "user"));
		fr.addProperty(new Property(false, new TableReference("BG", "bid"), "varchar(20)", "blog"));
		
		assertEquals(true, Rules.isRelationship(fr));
		
	}
	
	@Test
	public void testnmMitID() {
		//Node
		Tableschema fr = new Tableschema("FR");
		fr.addProperty(new Property(true, null, "int", "id"));
		fr.addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "user"));
		fr.addProperty(new Property(false, new TableReference("BG", "bid"), "varchar(20)", "blog"));
		
		assertEquals(true, Rules.isRelationship(fr));
		
	}

}
