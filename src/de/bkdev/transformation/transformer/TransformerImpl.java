package de.bkdev.transformation.transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.template.ContentLayer;
import de.bkdev.transformation.storage.relational.template.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.template.TableContent;

public class TransformerImpl implements TransformerController{

	@Override
	public ArrayList<Node> makeNodes(ArrayList<TableContent> tableList) {
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(TableContent e : tableList){
			for(ContentLayer c : e.getLayer()){
				nodes.add(makeNode(e.getTableScheme().getName(), c));
			}
		}
		
		return nodes;
	}
	
	private Node makeNode(String label, ContentLayer layer){
		Node node = new Node(label);
		
		for(PropertyValueTupel tupel : layer.getAttributes()){
			node.addProperty(tupel.getProperty(), tupel.getValue());
		}
		
		return node;
	}
	
	@Override
	public ArrayList<Relationship> makeRelationship(ArrayList<TableContent> rels, ArrayList<Node> nodes) {
		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		
		for(TableContent rel : rels){
			for(ContentLayer layer : rel.getLayer()){
				PropertyValueTupel firstfk = layer.getForeignKeyAt(0);
				PropertyValueTupel secondfk = layer.getForeignKeyAt(1);
				
				Node firstN = getNodeWithPrimaryKeyValue(firstfk, nodes);
				Node secondN = getNodeWithPrimaryKeyValue(secondfk, nodes);
				
				relationships.add(new Relationship(rel.getTableScheme().getName(), new NodeTupel(firstN, secondN)));
			}
		}
		return relationships;
	}
	
	public Node getNodeWithPrimaryKeyValue(PropertyValueTupel tupel, ArrayList<Node> nodes){
		for(Node node : nodes){
			if(node.getPrimaryKey().getValue().equals(tupel.getValue())){
				return node;
			}
		}
		return null;
	}

}
