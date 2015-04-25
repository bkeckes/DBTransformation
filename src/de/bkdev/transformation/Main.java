package de.bkdev.transformation;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = new Node("Person");
		node.addProperty(new Property("name", "benni"));
		
		System.out.println(node.toString());

	}

}
