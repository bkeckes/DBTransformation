package de.bkdev.transformation.transformer;

import java.util.ArrayList;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.Tableschema;

public class StatementMaker {

	/**
	 * TODO
	 */
	public static String getNodeIdentifer(Node node){
		return node.getPrimaryKey().getKey()+node.getPrimaryKey().getValue();
	}
	public static String makeNodeStatement(Node node) {
		
		return "CREATE (" + getNodeIdentifer(node) + ":" + node.getLabel() + " {" + node.getAllPropertysInString() + "})";
	}
	public static String makeRelationshipStatement(Relationship rel){
		String props = "";
		if(rel.getPropertyCount()>0)
			props = " {" + rel.getAllPropertysInString() + "}";
		
		return "CREATE (" + getNodeIdentifer(rel.getStart()) + ")-[" + rel.getRelID() + ":" + rel.getLabel().toUpperCase() + props + "]->(" + getNodeIdentifer(rel.getEnd()) + ")";
	}

	public static ArrayList<String> makeCypherStatementFromNodes(ArrayList<Node> nodes) {
		ArrayList<String> temp = new ArrayList<>();
		for(Node n : nodes){
			try{
				temp.add(makeNodeStatement(n));
			}catch(NullPointerException e){
				e.printStackTrace();
			}
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
	
	public static ArrayList<String> makeConstraints(ArrayList<Tableschema> schemas){
		ArrayList<String> list = new ArrayList<>();
		String temp="";
		String cName = "";
		for(Tableschema s : schemas){
			cName = "const" + s.getName();
			try{
				list.add("CREATE CONSTRAINT ON (" + cName + ":" + s.getName() + ") ASSERT " + cName + "." + s.getPrimaryKey().getName() + " IS UNIQUE");
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static String makeSingleRelationshipStatement(Relationship rel){
		String props = "";
		String temp ="";
		if(rel.getPropertyCount()>0)
			props = " {" + rel.getAllPropertysInString() + "}";
		
		temp = "MATCH (a:" + rel.getStart().getLabel() + " { " + rel.getStart().getPrimaryKey().getKey() + ":'" + rel.getStart().getPrimaryKey().getValue() + "'})";
		temp += ", (b:" + rel.getEnd().getLabel() + " { " + rel.getEnd().getPrimaryKey().getKey() + ":'" + rel.getEnd().getPrimaryKey().getValue() + "'})";
		temp += " MERGE (a)-[r:" + rel.getLabel().toUpperCase() + props + "]->(b)";
		return temp; //"CREATE (" + getNodeIdentifer(rel.getStart()) + ")-[" + rel.getRelID() + ":" + rel.getLabel().toUpperCase() + props + "]->(" + getNodeIdentifer(rel.getEnd()) + ")";
	}
	public static ArrayList<String> makeCypherStatementFromSingleRelationships(ArrayList<Relationship> rels){
		ArrayList<String> temp = new ArrayList<>();
		for(Relationship r : rels){
			temp.add(makeSingleRelationshipStatement(r));
		}
		return temp;
	}

}
