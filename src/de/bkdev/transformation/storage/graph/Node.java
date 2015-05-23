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
	private final String nodeID;
	
	public Node(String label){
		super();
		this.label = label;
		this.nodeID = IdentificationMaker.makeNewNodeID();
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
	
	
	/*public boolean isPKValue(){
		
	}
	*/	
}
