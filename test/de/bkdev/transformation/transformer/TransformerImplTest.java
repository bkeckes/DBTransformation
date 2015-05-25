package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentController;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.SchemeController;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.Tablescheme;


public class TransformerImplTest {

	@Test
	public void testeNodeMaker() {
		Tablescheme tablescheme = new Tablescheme("BJ");
		tablescheme.addProperty(new Property(true, false, "varchar(20)", "id"));
		tablescheme.addProperty(new Property(false, false, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(tablescheme);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		tc.addAttributeToCurrentLayer("name", "hunt");
		
		ArrayList<TableContent> tableList = new ArrayList<>();
		tableList.add(tc);
		
		TransformerController transformer = new TransformerImpl();
		
		ArrayList<Node> nodes= transformer.makeNodes(tableList);
		
		
		/*if(inspector.transformTableToGraph(tablescheme).identify().equals("Node"))
				nodes = transformer.makeNodes(tableList);
		*/
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		assertEquals(2, nodes.size());
	}
	
	
	
	@Test
	public void testeZusammenSpielKanteUndKnoten(){
		SchemeController schemes = new SchemeController();
		ContentController contents = new ContentController();
		
		schemes.addScheme(new Tablescheme("US"));
		schemes.getActualScheme().addProperty(new Property(true, false, "varchar(20)", "uid"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchr(128)", "uname"));
		
		schemes.addScheme(new Tablescheme("BG"));
		schemes.getActualScheme().addProperty(new Property(true, false, "varchar(20)", "bid"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "bname"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "admin"));
		
		schemes.addScheme(new Tablescheme("FR"));
		schemes.getActualScheme().addProperty(new Property(false, true, "varchar(20)", "fuser"));
		schemes.getActualScheme().addProperty(new Property(false, true, "varchar(20)", "fblog"));
		
		
		contents.addContent(new TableContent(schemes.getScheme("US")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("uname", "Date");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "u02");
		contents.getActualContent().addAttributeToCurrentLayer("uname", "Hunt");
		
		contents.addContent(new TableContent(schemes.getScheme("BG")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("bid", "b01");
		contents.getActualContent().addAttributeToCurrentLayer("bname", "Informatics");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "u01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("bid", "b02");
		contents.getActualContent().addAttributeToCurrentLayer("bname", "Biotech");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "u01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("bid", "b03");
		contents.getActualContent().addAttributeToCurrentLayer("bname", "Science");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "u02");
		
		contents.addContent(new TableContent(schemes.getScheme("FR")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b03");
		
		TransformerController transformer = new TransformerImpl();
		
		ArrayList<Node> nodes					= transformer.makeNodes(contents.getNodes());
		ArrayList<Relationship> relationships 	= transformer.makeRelationship(contents.getRelationships(), nodes);
		ArrayList<Relationship> relationships2 	= transformer.makeRelationshipsWithProperties(nodes);
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships2));
		
		assertEquals("US", relationships.get(1).getStart().getLabel());
		assertEquals("BG", relationships.get(1).getEnd().getLabel());
		
		assertEquals("uid", relationships.get(1).getStart().getPrimaryKey().getKey());
		/*
		 * TODO ähnliches wie Tablelist. Der Inhalt von contents muss nach nodes gefiltert werden und erstellt werden.
		 * Anschließend werden die Relationships erstellt.
		 */
	}
	
	@Test
	public void testeRelationshipMaker(){
		Property id = new Property(true, false, "char", "id");
		Property name = new Property(true, false, "char", "name");
		
		Node node1 = new Node("Mensch");
		node1.addProperty(id, "u01");
		node1.addProperty(name, "benni");
		
		Node node2 = new Node("Mensch");
		node2.addProperty(id, "u02");
		node2.addProperty(name, "franz");
		
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node1);
		nodes.add(node2);
		
		PropertyValueTupel pv = new PropertyValueTupel(new Property(false, true, "char", "id"), "u01");
		
		TransformerImpl transformer = new TransformerImpl();
		
		assertEquals("Mensch", transformer.getNodeWithPrimaryKeyValue(pv, nodes).getLabel());
		
	}

}
