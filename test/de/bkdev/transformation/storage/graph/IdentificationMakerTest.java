package de.bkdev.transformation.storage.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class IdentificationMakerTest {

	@Test
	public void test() {
		String nID = IdentificationMaker.makeNewNodeID();
		//assertEquals("n1", NodeIDMaker.makeNewNodeID());
		assertNotSame(nID,  IdentificationMaker.makeNewNodeID());
	}
	
	
	
}
