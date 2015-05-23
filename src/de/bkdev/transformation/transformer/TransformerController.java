package de.bkdev.transformation.transformer;

import java.util.ArrayList;
import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.TableContent;


public interface TransformerController {
	public ArrayList<Node> makeNodes(ArrayList<TableContent> tableList);
	public ArrayList<Relationship> makeRelationship(ArrayList<TableContent> rels, ArrayList<Node> nodes);
	public ArrayList<Relationship> makeRelationshipsWithProperties(ArrayList<Node> nodes);
}
