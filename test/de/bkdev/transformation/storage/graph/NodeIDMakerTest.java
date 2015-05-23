package de.bkdev.transformation.storage.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeIDMakerTest {

	@Test
	public void test() {
		String nID = NodeIDMaker.makeNewNodeID();
		//assertEquals("n1", NodeIDMaker.makeNewNodeID());
		assertNotSame(nID,  NodeIDMaker.makeNewNodeID());
	}
	
	
	
}
