package de.bkdev.transformation.transformer;

import java.util.HashSet;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.template.TableContent;

public interface TransformerController {
	public HashSet<Node> makeNodes(TableContent tc);
	public HashSet<Relationship> makeRelationship(TableContent tc, HashSet<Node> nodes);
}
