package de.bkdev.transformation.storage.graph;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Representor of a Graph-Node.
 * @author Benjamin Keckes
 *
 */
public class Node extends GraphObject{
	private String label;
	private String nodeID;
	
	public Node(String label){
		super();
		this.label = label;
		this.nodeID = NodeIDMaker.makeNewNodeID();
	}
	public String getNodeID() {
		return nodeID;
	}
	public String toString(){
		return "(" + label + ")->" + this.getAllPropertysInString();
	}
	
	public String getLabel() {
		return label;
	}
		
}
