package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;
import org.junit.Test;

public class StatementMakerTest {

	@Test
	public void testNodeMaker() {
		Node node = new Node("Human");
		node.addProperty("id", "1");
		node.addProperty("name", "benni");
		node.addProperty("birth", "1988");
		
		HashSet<Node> nodes = new HashSet<Node>();
		nodes.add(node);
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		assertEquals("CREATE (n:Human {id:'1', name:'benni', birth:'1988'});",StatementMaker.makeNodeStatement(node));
	}				  

}
