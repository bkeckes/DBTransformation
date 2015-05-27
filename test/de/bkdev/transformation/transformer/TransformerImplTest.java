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
		schemes.getActualScheme().addProperty(new Property(false, false, "varchr(128)", "name"));
		
		schemes.addScheme(new Tablescheme("BG"));
		schemes.getActualScheme().addProperty(new Property(true, false, "varchar(20)", "bid"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "name"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "admin"));
		
		schemes.addScheme(new Tablescheme("FR"));
		schemes.getActualScheme().addProperty(new Property(false, "US", "varchar(20)", "fuser"));
		schemes.getActualScheme().addProperty(new Property(false, "BG", "varchar(20)", "fblog"));
		
		schemes.addScheme(new Tablescheme("TG"));
		schemes.getActualScheme().addProperty(new Property(false, "US", "varchar(20)", "tuser"));
		schemes.getActualScheme().addProperty(new Property(false, "CT", "varchar(20)", "tcomment"));
		
		schemes.addScheme(new Tablescheme("CT"));
		schemes.getActualScheme().addProperty(new Property(true, false, "varchar(20)", "cid"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "cblog"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "cuser"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "msg"));
		schemes.getActualScheme().addProperty(new Property(false, false, "varchar(20)", "date"));
		
		
		
		contents.addContent(new TableContent(schemes.getScheme("US")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Date");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "u02");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Hunt");
		
		contents.addContent(new TableContent(schemes.getScheme("BG")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "b01");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Informatics2");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "u01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "b02");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Biotech");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "u01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "b03");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Science");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "u02");
		
		contents.addContent(new TableContent(schemes.getScheme("FR")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b01");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b02");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b03");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "u02");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "b01");
		
		contents.addContent(new TableContent(schemes.getScheme("TG")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("tuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("tcomment", "c01");
		
		contents.addContent(new TableContent(schemes.getScheme("CT")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("cid", "c01");
		contents.getActualContent().addAttributeToCurrentLayer("cblog", "b01");
		contents.getActualContent().addAttributeToCurrentLayer("cuser", "u01");
		contents.getActualContent().addAttributeToCurrentLayer("msg", "Das ist toll");
		contents.getActualContent().addAttributeToCurrentLayer("date", "11.01.2012");
		
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
