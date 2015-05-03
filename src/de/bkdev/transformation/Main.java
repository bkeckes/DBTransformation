package de.bkdev.transformation;

import de.bkdev.transformation.storage.graph.Node;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = new Node("Person");
		node.addProperty("name", "benni");
		node.addProperty("birth", "1988");
		System.out.println(node.toString());
		
		GDBConnection con = new GDBConnection();

	}

}
