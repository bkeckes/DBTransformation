package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import de.bkdev.transformation.inspector.InspectorController;
import de.bkdev.transformation.inspector.InspectorImpl;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.Table;
import de.bkdev.transformation.storage.relational.template.TableContent;

public class TransformerImplTest {

	@Test
	public void testeAnzahlNodes() {
		Table table = new Table("BJ");
		table.addProperty(new Property(true, false, "varchar(20)", "id"));
		table.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(table);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		tc.addAttributeToCurrentLayer("name", "hunt");
		
		TransformerController transformer = new TransformerImpl();
		HashSet<Node> nodes=null;
		InspectorController inspector = new InspectorImpl();
		
		if(inspector.transformTableToGraph(table).identify().equals("Node"))
				nodes = transformer.makeNodes(tc);
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		assertEquals(2, nodes.size());
	}
	
	@Test
	public void testeRelation(){
		Table table = new Table("FR");
		table.addProperty(new Property(false, true, "varchar(20)", "fuser"));
		table.addProperty(new Property(false, true, "varchar(20)", "fblog"));
		
		TableContent tc = new TableContent(table);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("fuser", "u01");
		tc.addAttributeToCurrentLayer("fblog", "b01");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("fuser", "u01");
		tc.addAttributeToCurrentLayer("fblog", "b02");
		
		TransformerController transformer = new TransformerImpl();
		HashSet<Node> relation=null;
		InspectorController inspector = new InspectorImpl();
		
		relation = transformer.makeNodes(tc);
	}

}
