package de.bkdev.transformation.transformer;

import java.util.ArrayList;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;

public class StatementMaker {

	/**
	 * TODO
	 */
	public static String makeNodeStatement(Node node) {
		return "CREATE (" + node.getNodeID() + ":" + node.getLabel() + " {" + node.getAllPropertysInString() + "})";
	}
	public static String makeRelationshipStatement(Relationship rel){
		String props = "";
		if(rel.getPropertyCount()>0)
			props = " {" + rel.getAllPropertysInString() + "}";
		
		return "CREATE (" + rel.getStart().getNodeID() + ")-[" + rel.getRelID() + ":" + rel.getLabel().toUpperCase() + props + "]->(" + rel.getEnd().getNodeID() + ")";
	}

	public static String makeCypherStatementFromNodes(ArrayList<Node> nodes) {
		String temp="";
		for(Node n : nodes){
			temp += makeNodeStatement(n)+"\n";
		}
		return temp;
	}
	
	public static String makeCypherStatementFromRelationships(ArrayList<Relationship> rels){
		String temp = "";
		for(Relationship r : rels){
			temp += makeRelationshipStatement(r) + "\n";
		}
		return temp;
	}

}
