package de.bkdev.transformation.storage.graph;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;

public class RelationshipTest {

	@Test
	public void testeRelationen(){
		Node n = new Node("US");
		n.addProperty("uid", "u01");
		n.addProperty("name", "Date");
		
		Node b = new Node("BG");
		b.addProperty("bid", "b02");
		b.addProperty("title", "IT-Systems");
		
		Relationship r = new Relationship("isAdminFrom", new NodeTupel(n, b));
		r.addProperty("since", "begin");
//		System.out.println(r.toString());
		
		assertEquals("Date", r.getStart().getPropertyValue("name"));
//		System.out.println(r.getStart());
//		System.out.println(r.getEnd());
		assertEquals("b02", r.getEnd().getPropertyValue("bid"));
	}
}
