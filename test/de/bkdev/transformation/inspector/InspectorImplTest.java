package de.bkdev.transformation.inspector;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.graph.template.GDBTemplate;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyException;
import de.bkdev.transformation.storage.relational.Tablescheme;
import de.bkdev.transformation.storage.relational.template.TableContent;

public class InspectorImplTest {

	@Test
	public void testeFunktionalitaetFuerNode() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		InspectorController inspector = new InspectorImpl();
		GDBTemplate template = inspector.transformTableToGraph(table);
		assertEquals("Node", template.identify());
		
	}
	
	@Test
	public void testeFunktionalitaetFuerRelation() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(false, true, "varchar(20)", "id"));
		table.addProperty(new Property(false, true, "varchr(128)", "name"));
		
		InspectorController inspector = new InspectorImpl();
		GDBTemplate template = inspector.transformTableToGraph(table);
		assertEquals("Relation", template.identify());
	}
	
	@Test
	public void testeFunktionalitaetFuerRelation2() {
		Tablescheme table = new Tablescheme("CT");
		table.addProperty(new Property(true, false, "int", "id"));
		table.addProperty(new Property(false, true, "varchr(128)", "blog"));
		table.addProperty(new Property(false, true, "varchr(128)", "user"));
		table.addProperty(new Property(false, true, "varchr(128)", "msg"));
		table.addProperty(new Property(false, true, "varchr(12)", "date"));
		
		InspectorController inspector = new InspectorImpl();
		GDBTemplate template = inspector.transformTableToGraph(table);
		assertEquals("Relation", template.identify());
	}
	
	@Test
	public void testeBeispiel(){
		Tablescheme us = new Tablescheme("US");
		us.addProperty(new Property(true, false, "String", "uid"));
		us.addProperty(new Property(false, false, "String", "uname"));
		
		Tablescheme bg = new Tablescheme("BG");
		bg.addProperty(new Property(true, false, "String", "bid"));
		bg.addProperty(new Property(false, false, "String", "bname"));
		bg.addProperty(new Property(false, false, "String", "admin"));
		
		Tablescheme fr = new Tablescheme("FR");
		fr.addProperty(new Property(false, true, "String", "fuser"));
		fr.addProperty(new Property(false, true, "String", "fblog"));
		
		Tablescheme ct = new Tablescheme("CT");
		ct.addProperty(new Property(true, false, "String", "cid"));
		ct.addProperty(new Property(false, true, "String", "cblog"));
		ct.addProperty(new Property(false, true, "String", "cuser"));
		ct.addProperty(new Property(false, false, "String", "msg"));
		ct.addProperty(new Property(false, false, "String", "date"));
		
		Tablescheme tg = new Tablescheme("TG");
		tg.addProperty(new Property(false, true, "String", "tuser"));
		tg.addProperty(new Property(false, true, "String", "tcomment"));
		
		InspectorController inspector = new InspectorImpl();
		
		assertEquals("Node", inspector.transformTableToGraph(us).identify());
		assertEquals("Node", inspector.transformTableToGraph(bg).identify());
		assertEquals("Relation", inspector.transformTableToGraph(fr).identify());
		assertEquals("Relation", inspector.transformTableToGraph(ct).identify());
		assertEquals("Relation", inspector.transformTableToGraph(tg).identify());

	}
	
	@Test
	public void testeFalschenBezeichner(){
		Tablescheme us = new Tablescheme("US");
		us.addProperty(new Property(true, false, "String", "uid"));
		us.addProperty(new Property(false, false, "String", "uname"));
		
		TableContent tc = new TableContent(us);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		
	}
	@Test
	public void testNodeTransformation(){
		Tablescheme us = new Tablescheme("US");
		us.addProperty(new Property(true, false, "String", "uid"));
		us.addProperty(new Property(false, false, "String", "uname"));
		
		TableContent tc = new TableContent(us);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("uid", "c01");
		tc.addAttributeToCurrentLayer("uname", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("uid", "c02");
		tc.addAttributeToCurrentLayer("uname", "hunt");
	}

}
