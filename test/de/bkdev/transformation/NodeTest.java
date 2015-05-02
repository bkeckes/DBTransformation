package de.bkdev.transformation;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.Node;
import de.bkdev.transformation.Property;

public class NodeTest {
	@Test
	public void testeNodeErstellung(){
		Node node = new Node("Person");
		node.addProperty("name", "benni");
		
//		System.out.println(node.toString());
	}
	
	@Test
	public void testeNodeName(){
		Node node = new Node("Person");
		assertEquals("Ein anderes Label wurde erwartet", "Person", node.getLabel());
	}
	
	@Test 
	public void testeNodeAnzahl(){
		Node node = new Node("Person");
		assertEquals(0, node.getPropertyCount());
		node.addProperty("name", "benni");
		assertEquals(1, node.getPropertyCount());
		//TODO
//		node.addProperty(new Property("name", "benni"));
//		assertEquals(1, node.getPropertyCount());
		node.addProperty("birth", "1988");
		assertEquals(2, node.getPropertyCount());
	}
	
	@Test
	public void testeNodeVerhalten(){
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
