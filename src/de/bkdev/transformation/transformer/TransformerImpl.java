package de.bkdev.transformation.transformer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import de.bkdev.transformation.storage.graph.KeyValuePair;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentLayer;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.Tablescheme;

public class TransformerImpl implements TransformerController{

	/**
	 * macht normale Nodes
	 */
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
	
	/**
	 * Macht n:m Relationen
	 */
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
			if(node.getPrimaryKey().getValue().equals(tupel.getValue()) && node.getPrimaryKey().getProperty().getType().equals(tupel.getProperty().getType())){
				return node;
			}
		}
		return null;
	}

	/**
	 * macht Relationen wenn PrimaryKey in einer anderen Node erwähnt wird.
	 * TODO Muss kein Foreign Key sein?
	 */
	@Override
	public ArrayList<Relationship> makeRelationshipsWithProperties(ArrayList<Node> nodes) {
		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		
		for(Node n : nodes){
			for(KeyValuePair kv : n.getPropertySet()){
				if(!kv.getProperty().isPrimaryKey()){
					Node found = findAttributeInNodesAsPrimaryKey(kv, nodes, n.getLabel());
					if(found!=null){
						relationships.add(new Relationship(kv.getKey(), new NodeTupel(found, n)));
					}
				}
			}
		}
		return relationships;
	}
	
	/**
	 * Durchsucht alle Nodes (TODO Ausser in angegebener Tabelle) ob sie diesen Wert als PK haben. 
	 * @return
	 */
	public Node findAttributeInNodesAsPrimaryKey(KeyValuePair toFind, ArrayList<Node> nodes, String notInhisScheme){
		
		for(Node n : nodes){
			if(!n.getLabel().equals(notInhisScheme)){
				for(KeyValuePair kv : n.getPropertySet()){
					if(kv.getProperty().isPrimaryKey()){
						if(kv.getValue().equals(toFind.getValue())){
							return n;
						}
					}
				}
			}
		}
		return null;
	}

}
