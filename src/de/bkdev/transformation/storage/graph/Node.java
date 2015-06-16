package de.bkdev.transformation.storage.graph;

import java.util.ArrayList;

import de.bkdev.transformation.storage.PropertyValueTupel;


/**
 * Representor of a Graph-Node.
 * @author Benjamin Keckes
 *
 */
public class Node extends GraphObject{
	private String label;
	//private final String nodeID;
	
	public Node(String label){
		super();
		this.label = label;
		//this.nodeID = IdentificationMaker.makeNewNodeID();
	}
	public Node(String label, ArrayList<PropertyValueTupel> attributeList){
		super();
		this.label = label;
		this.setAttributeList(attributeList);
		//this.nodeID = IdentificationMaker.makeNewNodeID();
	}
	public String getNodeID() {
		//return nodeID;
		return "n";
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
