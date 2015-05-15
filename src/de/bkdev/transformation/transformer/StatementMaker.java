package de.bkdev.transformation.transformer;

import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;

public class StatementMaker {

	/**
	 * TODO
	 */
	public static String makeNodeStatement(Node node) {
		return "CREATE (n:" + node.getLabel() + " {" + node.getAllPropertysInString() + "});";
	}

	public static String makeCypherStatementFromNodes(HashSet<Node> nodes) {
		String temp="";
		for(Node n : nodes){
			temp += makeNodeStatement(n)+"\n";
		}
		return temp;
	}

}
