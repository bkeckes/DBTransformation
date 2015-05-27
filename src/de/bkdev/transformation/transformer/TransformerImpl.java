package de.bkdev.transformation.transformer;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.RunRules;

import de.bkdev.transformation.parser.DatabaseReader;
import de.bkdev.transformation.storage.graph.KeyValuePair;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentLayer;
import de.bkdev.transformation.storage.relational.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.TableContent;


public class TransformerImpl implements TransformerController{

	private static final Logger log4j = LogManager.getLogger(TransformerImpl.class
	        .getName());
	/**
	 * macht normale Nodes
	 */
	@Override
	public ArrayList<Node> makeNodes(ArrayList<TableContent> tableList) {
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(TableContent e : tableList){
			log4j.info("Make Nodes for '" + e.getTableScheme().getName() + "'");
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
			log4j.info("Make n:m Relationships for '" + rel.getTableScheme().getName() +"'");
			for(ContentLayer layer : rel.getLayer()){
				PropertyValueTupel firstfk = layer.getForeignKeyAt(0);
				PropertyValueTupel secondfk = layer.getForeignKeyAt(1);
				Node firstN = null;
				Node secondN = null;
				
				try{
					firstN = getNodeWithPrimaryKeyValue(firstfk, getNodesWithScheme(nodes, firstfk.getProperty().getRefTable()));
					//secondN = getNodeWithPrimaryKeyValue(secondfk, getNodesWithScheme(nodes, secondfk.getProperty().getRefTable()));
				}catch(RuntimeException e){
					log4j.error("Reference from '" + firstN.getLabel() + "' to '" + firstfk.getProperty().getRefTable() + "' could not found in Nodes");
					e.printStackTrace();
				}
				
				try{
					//firstN = getNodeWithPrimaryKeyValue(firstfk, getNodesWithScheme(nodes, firstfk.getProperty().getRefTable()));
					secondN = getNodeWithPrimaryKeyValue(secondfk, getNodesWithScheme(nodes, secondfk.getProperty().getRefTable()));
					
				}catch(RuntimeException e){
					log4j.error("Reference from '" + secondN.getLabel() + "' to '" + secondfk.getProperty().getRefTable() + "' could not found in Nodes");
					e.printStackTrace();
				}
				//Kante wird erstellt.
				Relationship newRelationship = new Relationship(rel.getTableScheme().getName(), new NodeTupel(firstN, secondN));
				
				//Attribute (falls vorhanden) werden hinzugefügt (Aber nicht die FKs).
				for(PropertyValueTupel tupel : layer.getAttributesWithoutFks()){
					newRelationship.addProperty(tupel.getProperty(), tupel.getValue());
				}
				
				relationships.add(newRelationship);
			}
		}
		return relationships;
	}
	
	public ArrayList<Node> getNodesWithScheme(ArrayList<Node> nodes, String schemename){
		ArrayList<Node> list = new ArrayList<>();
		for(Node node : nodes){
			if(node.getLabel().equals(schemename)){
				list.add(node);
			}
		}
		
		if(list.isEmpty()){
			log4j.error("Schemaname '" + schemename + "' not found ");
			throw new NullPointerException();
		}
			
		return list;
	}
	public ArrayList<Node> getNodesWithoutScheme(ArrayList<Node> nodes, String schemename){
		ArrayList<Node> list = new ArrayList<>();
		for(Node node : nodes){
			if(!node.getLabel().equals(schemename)){
				list.add(node);
			}
		}
		return list;
	}
	
	/**
	 * Geht alle Nodes durch und Vergleicht ob der PK gleich dem PK des tupels ist. Wenn ja wird die Node zurück gegeben.
	 * @param tupel
	 * @param nodes
	 * @return foundNode
	 */
	public Node getNodeWithPrimaryKeyValue(PropertyValueTupel tupel, ArrayList<Node> nodes){
		for(Node node : nodes){
			if(node.getPrimaryKey().getValue().equals(tupel.getValue()) && node.getPrimaryKey().getProperty().getType().equals(tupel.getProperty().getType())){
				return node;
			}
		}
		throw new RuntimeException();
	}

	/**
	 * macht Relationen wenn PrimaryKey in einer anderen Node erwähnt wird.
	 * 
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
						log4j.info("Make 1:1 or 1:N Relationship from '" + found.getLabel() +"' to '" + n.getLabel() + "'");
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
