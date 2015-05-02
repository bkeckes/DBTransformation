package de.bkdev.transformation.storage;

/**
 * Important for Relationship
 * @author Benjamin Keckes
 *
 */
public class NodeTupel {
	private Node start;
	private Node end;
	
	public NodeTupel(Node s, Node e) {
		this.start = s;
		this.end = e;
	}

	public Node getStart() {
		return start;
	}

	public Node getEnd() {
		return end;
	}
	
	
}
