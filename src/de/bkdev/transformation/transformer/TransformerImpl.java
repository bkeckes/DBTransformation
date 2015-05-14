package de.bkdev.transformation.transformer;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.template.ContentLayer;
import de.bkdev.transformation.storage.relational.template.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.template.TableContent;

public class TransformerImpl implements TransformerController{

	@Override
	public HashSet<Node> makeNodes(TableContent tc) {
		HashSet<Node> nodes = new HashSet<Node>();
		Node currentNode;
		for(ContentLayer c : tc.getLayer()){
			
			nodes.add(makeNode(tc.getTable().getName(), c));
			
		}
		
		return nodes;
	}
	
	private Node makeNode(String label, ContentLayer layer){
		Node node = new Node(label);
		
		for(PropertyValueTupel tupel : layer.getAttributes()){
			node.addProperty(tupel.getProperty().getName(), tupel.getValue());
		}
		
		return node;
	}

	@Override
	public HashSet<Relationship> makeRelationship(TableContent tc,
			HashSet<Node> nodes) {
		HashSet<Relationship> rels = new HashSet<Relationship>();
		
		for(ContentLayer c : tc.getLayer()){
			PropertyValueTupel first = c.getFirstForeignKey();
			String firstID = first.getProperty().getName();
			String secondID =  c.getForeignKeyAfter(first).getProperty().getName();
			
			
		}
		
		return null;
	}

}
