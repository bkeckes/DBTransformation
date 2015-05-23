package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.Property;

import org.junit.Test;

public class StatementMakerTest {

	@Test
	public void testNodeMaker() {
		Node node = new Node("Human");
		node.addProperty(new Property(false, false, "char", "id"), "1");
		node.addProperty(new Property(false, false, "char", "name"), "benni");
		node.addProperty(new Property(false, false, "char", "birth"), "1988");
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
	
		
		//assertEquals("CREATE (n:Human {id:'1', name:'benni', birth:'1988'});",StatementMaker.makeNodeStatement(node));
	}				  

}
