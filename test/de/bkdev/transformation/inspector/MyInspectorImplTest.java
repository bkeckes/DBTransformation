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
		table.addProperty(new Property(false, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		InspectorController inspector = new MyInspectorImpl();
		GDBTemplate template = inspector.getObject(table);
		assertEquals("Relation", template.identify());
	}

}
