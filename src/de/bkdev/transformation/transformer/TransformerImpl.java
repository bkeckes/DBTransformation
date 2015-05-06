package de.bkdev.transformation.transformer;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.template.ContentLayer;
import de.bkdev.transformation.storage.relational.template.TableContent;

public class TransformerImpl implements TransformerController{

	@Override
	public HashSet<Node> makeNodes(TableContent tc) {
		HashSet<Node> nodes = new HashSet<Node>();
		Node currentNode;
		for(ContentLayer c : tc.getLayer()){
			currentNode = new Node(tc.getTable().getName());
			
			Iterator<Entry<Property, String>> it = c.getTupel().entrySet().iterator();
			
			while(it.hasNext()){
				Entry<Property, String> entry = it.next();
				currentNode.addProperty(entry.getKey().getName(), entry.getValue());
			}
			nodes.add(currentNode);
		}
		
		return nodes;
	}

}
