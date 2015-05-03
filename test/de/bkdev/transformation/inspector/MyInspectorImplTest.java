package de.bkdev.transformation.inspector;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.graph.template.GDBTemplate;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.Table;

public class MyInspectorImplTest {

	@Test
	public void testeFunktionalitaetFuerNode() {
		Table table = new Table("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		InspectorController inspector = new MyInspectorImpl();
		GDBTemplate template = inspector.getObject(table);
		assertEquals("Node", template.identify());
		
	}
	
	@Test
	public void testeFunktionalitaetFuerRelation() {
		Table table = new Table("US");
		table.addProperty(new Property(false, true, "varchar(20)", "id"));
		table.addProperty(new Property(false, true, "varchr(128)", "name"));
		
		InspectorController inspector = new MyInspectorImpl();
		GDBTemplate template = inspector.getObject(table);
		assertEquals("Relation", template.identify());
	}
	
	@Test
	public void testeFunktionalitaetFuerRelation2() {
		Table table = new Table("CT");
		table.addProperty(new Property(true, false, "int", "id"));
		table.addProperty(new Property(false, true, "varchr(128)", "blog"));
		table.addProperty(new Property(false, true, "varchr(128)", "user"));
		table.addProperty(new Property(false, true, "varchr(128)", "msg"));
		table.addProperty(new Property(false, true, "varchr(12)", "date"));
		
		InspectorController inspector = new MyInspectorImpl();
		GDBTemplate template = inspector.getObject(table);
		assertEquals("Relation", template.identify());
	}
	
	@Test
	public void testeBeispiel(){
		Table us = new Table("US");
		us.addProperty(new Property(true, false, "String", "uid"));
		us.addProperty(new Property(false, false, "String", "uname"));
		
		Table bg = new Table("BG");
		bg.addProperty(new Property(true, false, "String", "bid"));
		bg.addProperty(new Property(false, false, "String", "bname"));
		bg.addProperty(new Property(false, false, "String", "admin"));
		
		Table fr = new Table("FR");
		fr.addProperty(new Property(false, true, "String", "fuser"));
		fr.addProperty(new Property(false, true, "String", "fblog"));
		
		Table ct = new Table("CT");
		ct.addProperty(new Property(true, false, "String", "cid"));
		ct.addProperty(new Property(false, true, "String", "cblog"));
		ct.addProperty(new Property(false, true, "String", "cuser"));
		ct.addProperty(new Property(false, false, "String", "msg"));
		ct.addProperty(new Property(false, false, "String", "date"));
		
		Table tg = new Table("TG");
		tg.addProperty(new Property(false, true, "String", "tuser"));
		tg.addProperty(new Property(false, true, "String", "tcomment"));
		
		InspectorController inspector = new MyInspectorImpl();
		
		assertEquals("Node", inspector.getObject(us).identify());
		assertEquals("Node", inspector.getObject(bg).identify());
		assertEquals("Relation", inspector.getObject(fr).identify());
		assertEquals("Relation", inspector.getObject(ct).identify());
		assertEquals("Relation", inspector.getObject(tg).identify());
		
		
		
	}

}
