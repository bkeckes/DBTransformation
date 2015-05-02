package de.bkdev.transformation;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Representor of a Graph-Node.
 * @author Benjamin Keckes
 *
 */
public class Node extends GraphObject{
	private String label;
	
	public Node(String label){
		super();
		this.label = label;
	}
	public String toString(){
		return "(" + label + ")->" + this.getAllPropertysInString();
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	
}
