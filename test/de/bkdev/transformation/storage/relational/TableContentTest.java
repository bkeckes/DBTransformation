package de.bkdev.transformation.storage.relational;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.TableContent;import de.bkdev.transformation.storage.relational.Tablescheme;

public class TableContentTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeVerhalten() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(table);
		
		assertEquals(0, tc.getLayerCount());
		
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		
		assertEquals(1, tc.getLayerCount());
		
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		tc.addAttributeToCurrentLayer("name", "hunt");
		
		assertEquals(2, tc.getLayerCount());
	}
	
	@Test
	public void testeVerhalten2() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(table);
		
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		
		tc.addAttributeToCurrentLayer("id", "c02");		
		assertFalse(tc.isLayerSetValid());
		tc.addAttributeToCurrentLayer("name", "hunt");
		assertTrue(tc.isCurrentLayerValid());
	}
	
	@Test
	public void testeFehlverhalten() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(table);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		
		
		tc.addAttributeToCurrentLayer("id", "hunt");
		//exception.expect(TableException.class);
		
		
	}
	
	@Test
	public void testeVerhalten3() {
		Tablescheme table = new Tablescheme("US");
		table.addProperty(new Property(true, null, "varchar(20)", "id"));
		table.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(table);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		tc.addAttributeToCurrentLayer("name", "hunt");
		
		
		
	}

}
