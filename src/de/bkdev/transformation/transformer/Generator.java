package de.bkdev.transformation.transformer;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.RunRules;

import de.bkdev.transformation.DatabaseReader;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.NodeTupel;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentLayer;
import de.bkdev.transformation.storage.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.TableReference;
import de.bkdev.transformation.storage.relational.Tableschema;


public class Generator{

	private static final Logger log4j = LogManager.getLogger(Generator.class
	        .getName());
	/**
	 * macht normale Nodes
	 */
	
	public ArrayList<Node> makeNodes(ArrayList<TableContent> tableList) {
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		for(TableContent e : tableList){
			log4j.info("Make Nodes for '" + e.getTableSchema().getTableName() + "'");
			for(ContentLayer c : e.getLayer()){
				nodes.add(new Node(e.getTableSchema().getTableName(), c.getAttributes()));
			}
		}
		
		return nodes;
	}
	
	
	/**
	 * Macht n:m Relationen
	 */

	public ArrayList<Relationship> makeManyToManyRelationships(ArrayList<TableContent> rels, ArrayList<Node> nodes) {
		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		
		for(TableContent rel : rels){
			log4j.info("Make n:m Relationships for '" + rel.getTableSchema().getTableName() +"'");
			for(ContentLayer layer : rel.getLayer()){
				PropertyValueTupel firstfk = layer.getForeignKeyAt(0);
				PropertyValueTupel secondfk = layer.getForeignKeyAt(1);
				Node firstN = null;
				Node secondN = null;
				
				try{
					//firstN = getNodeWithPrimaryKeyValue(firstfk, getNodesWithScheme(nodes, firstfk.getProperty().getRefTable()));
					firstN = getNodeWithSameKey(nodes, firstfk);
					
					//secondN = getNodeWithPrimaryKeyValue(secondfk, getNodesWithScheme(nodes, secondfk.getProperty().getRefTable()));
				}catch(RuntimeException e){
					log4j.error("Reference from '" + firstN.getLabel() + "' to '" + firstfk.getProperty().getRefTable() + "' could not found in Nodes");
					e.printStackTrace();
				}
				
				try{
					//firstN = getNodeWithPrimaryKeyValue(firstfk, getNodesWithScheme(nodes, firstfk.getProperty().getRefTable()));
					secondN = getNodeWithSameKey(nodes, secondfk);
					
				}catch(RuntimeException e){
					log4j.error("Reference from '" + secondN.getLabel() + "' to '" + secondfk.getProperty().getRefTable() + "' could not found in Nodes");
					e.printStackTrace();
				}
				//Kante wird erstellt.
				Relationship newRelationship = new Relationship(rel.getTableSchema().getTableName(), new NodeTupel(firstN, secondN));
				
				//Attribute (falls vorhanden) werden hinzugefuegt (Aber nicht die FKs).
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
	 * Geht alle Nodes durch und Vergleicht ob der PK gleich dem PK des tupels ist. Wenn ja wird die Node zurueck gegeben.
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
	 * macht Relationen wenn FK in einer anderen Node erwaehnt wird.
	 * Also 1:1 und 1:n
	 */

	public ArrayList<Relationship> makeOneToManyRelationships(ArrayList<Node> nodes) {
		ArrayList<Relationship> relationships = new ArrayList<Relationship>();
		
		for(Node n : nodes){
			for(PropertyValueTupel kv : n.getPropertySet()){
				if(kv.getProperty().isForeignKey()){
					
					Node found = findAttributeInNodesAsPrimaryKey(kv, nodes, kv.getProperty().getRefTable());
					// Node found = findAttributeInNodesAsPrimaryKey(kv, nodes, n.getLabel());
					 
					if(found!=null){
						relationships.add(new Relationship(kv.getKey(), new NodeTupel(n, found)));
						//log4j.info("Make 1:1 or 1:N Relationship from '" + n.getLabel() +"' to '" + found.getLabel() + "'");
						
						//relationships.add(new Relationship(kv.getKey(), new NodeTupel(found, n)));
						//log4j.info("Make 1:1 or 1:N Relationship from '" + found.getLabel() +"' to '" + n.getLabel() + "'");
					}else if(!kv.getProperty().getRefTable().isEmpty()){
						log4j.error("Could not make relationship from '" + kv.getValue() + "' to '" + kv.getProperty().getRefTable() + "'");
					}
					
				}
			}
		}
		return relationships;
	}
	
	public Node findAttributeInNodesAsPrimaryKey(PropertyValueTupel toFind, ArrayList<Node> nodes, String inScheme){
		
		
		for(Node n : nodes){
			if(n.getLabel().equals(inScheme)){
				//if(n.getPrimaryKey().getKey().equals(toFind.getKey()) && n.getPrimaryKey().getValue().equals(toFind.getValue()))
				//	return n;
				for(PropertyValueTupel kv : n.getPropertySet()){
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
	
	public Node getNodeWithSameKey(ArrayList<Node> nodes, PropertyValueTupel fk){
		TableReference ref = fk.getTableReference();
		for(Node n: nodes){
			if(n.getLabel().equals(ref.getTable())){
				if(n.getPrimaryKey().getValue().equals(fk.getValue())){
					return n;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Durchsucht alle Nodes (TODO Ausser in angegebener Tabelle) ob sie diesen Wert als PK haben. 
	 * @return
	 
	public Node findAttributeInNodesAsPrimaryKey(PropertyValueTupel toFind, ArrayList<Node> nodes, String notInhisScheme){
		
		for(Node n : nodes){
			if(!n.getLabel().equals(notInhisScheme)){
				for(PropertyValueTupel kv : n.getPropertySet()){
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
	*/
}
