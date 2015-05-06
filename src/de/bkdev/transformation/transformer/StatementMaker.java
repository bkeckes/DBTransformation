package de.bkdev.transformation.transformer;

import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;

public class StatementMaker {

	/**
	 * TODO
	 */
	private static String makeNodeStatement(Node node) {
		return node.toString();
	}

	public static String makeCypherStatementFromNodes(HashSet<Node> nodes) {
		String temp="";
		for(Node n : nodes){
			temp += makeNodeStatement(n)+"\n";
		}
		return temp;
	}

}
