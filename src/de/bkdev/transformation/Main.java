package de.bkdev.transformation;

import de.bkdev.transformation.storage.graph.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Main {

	/**
	 * @param args
	 */
	
	private static final Logger log4j = LogManager.getLogger(Main.class
	        .getName());
	
	public static void main(String[] args) {
		Node node = new Node("Person");
		node.addProperty("name", "benni");
		node.addProperty("birth", "1988");
		System.out.println(node.toString());
		
		//GDBConnection con = new GDBConnection();
		
		
		log4j.trace("This is a trace message.");
		log4j.debug("This is  a debug message.");
		log4j.info("This is an info message.");
		log4j.error("This is an error message");

	}

}
