package de.bkdev.transformation.transformer;

import java.util.ArrayList;
import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.template.TableContent;
import de.bkdev.transformation.storage.relational.template.TableList;

public interface TransformerController {
	public ArrayList<Node> makeNodes(TableList tableList);
	public HashSet<Relationship> makeRelationship(TableContent tc, HashSet<Node> nodes);
}
