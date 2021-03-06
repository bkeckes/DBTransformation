package de.bkdev.transformation.storage.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.Property;

public class NodeTest {
	@Test
	public void testeNodeErstellung(){
		Node node = new Node("Person");
		node.addProperty(new Property(false, null, "char", "name"), "benni");
		
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
		node.addProperty(new Property(false, null, "char", "name"), "benni");
		assertEquals(1, node.getPropertyCount());
		//TODO
//		node.addProperty(new Property("name", "benni"));
//		assertEquals(1, node.getPropertyCount());
		node.addProperty(new Property(false, null, "char", "birth"), "1988");
		assertEquals(2, node.getPropertyCount());
	}
	
	
}
