package de.bkdev.transformation.storage.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.Property;

public class RelationshipTest {

	@Test
	public void testeRelationen(){
		Node n = new Node("US");
		n.addProperty(new Property(false, false, "char", "uid"), "u01");
		n.addProperty(new Property(false, false, "char", "name"), "Date");
		
		Node b = new Node("BG");
		b.addProperty(new Property(false, false, "char", "bid"), "b02");
		b.addProperty(new Property(false, false, "char", "title"), "IT-Systems");
		
		Relationship r = new Relationship("isAdminFrom", new NodeTupel(n, b));
		r.addProperty(new Property(false, false, "char", "since"), "begin");
//		System.out.println(r.toString());
		
		assertEquals("Date", r.getStart().getPropertyValue("name"));
//		System.out.println(r.getStart());
//		System.out.println(r.getEnd());
		assertEquals("b02", r.getEnd().getPropertyValue("bid"));
	}
}
