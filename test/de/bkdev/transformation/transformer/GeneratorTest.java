package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentController;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.PropertyValueTupel;
import de.bkdev.transformation.storage.relational.TableReference;
import de.bkdev.transformation.storage.relational.SchemaController;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.Tableschema;


public class GeneratorTest {

	@Test
	public void testeNodeMaker() {
		Tableschema tablescheme = new Tableschema("BJ");
		tablescheme.addProperty(new Property(true, null, "varchar(20)", "id"));
		tablescheme.addProperty(new Property(false, null, "varchr(128)", "name"));
		
		TableContent tc = new TableContent(tablescheme);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c01");
		tc.addAttributeToCurrentLayer("name", "date");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("id", "c02");
		tc.addAttributeToCurrentLayer("name", "hunt");
		
		ArrayList<TableContent> tableList = new ArrayList<>();
		tableList.add(tc);
		
		Generator transformer = new Generator();
		
		ArrayList<Node> nodes= transformer.makeNodes(tableList);
		
		
		/*if(inspector.transformTableToGraph(tablescheme).identify().equals("Node"))
				nodes = transformer.makeNodes(tableList);
		*/
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		assertEquals(2, nodes.size());
	}
	
	
	
	@Test
	public void testeZusammenSpielKanteUndKnoten(){
		SchemaController schemes = new SchemaController();
		ContentController contents = new ContentController();
		
		schemes.addSchema(new Tableschema("US"));
		schemes.getActualScheme().addProperty(new Property(true, null, "varchar(20)", "uid"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchr(128)", "name"));
		
		schemes.addSchema(new Tableschema("BG"));
		schemes.getActualScheme().addProperty(new Property(true, null, "varchar(20)", "bid"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchar(20)", "name"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "admin"));
		
		schemes.addSchema(new Tableschema("FR"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "fuser"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("BG", "bid"), "varchar(20)", "fblog"));
		
		schemes.addSchema(new Tableschema("TG"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "tuser"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("CT", "cid"), "varchar(20)", "tcomment"));
		
		schemes.addSchema(new Tableschema("CT"));
		schemes.getActualScheme().addProperty(new Property(true, null, "varchar(20)", "cid"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("BG", "bid"), "varchar(20)", "cblog"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("US", "uid"), "varchar(20)", "cuser"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchar(20)", "msg"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchar(20)", "date"));
		
		
		
		contents.addContent(new TableContent(schemes.getSchema("US")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "1");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Date");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("uid", "2");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Hunt");
		
		contents.addContent(new TableContent(schemes.getSchema("BG")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "1");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Informatics2");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "2");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Biotech");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayerByIndex(0, "3");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(1, "Science");
		contents.getActualContent().addAttributeToCurrentLayerByIndex(2, "2");
		
		contents.addContent(new TableContent(schemes.getSchema("FR")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "1");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "1");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "2");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "1");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "3");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("fuser", "2");
		contents.getActualContent().addAttributeToCurrentLayer("fblog", "1");
		
		contents.addContent(new TableContent(schemes.getSchema("TG")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("tuser", "2");
		contents.getActualContent().addAttributeToCurrentLayer("tcomment", "1");
		
		contents.addContent(new TableContent(schemes.getSchema("CT")));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("cid", "1");
		contents.getActualContent().addAttributeToCurrentLayer("cblog", "1");
		contents.getActualContent().addAttributeToCurrentLayer("cuser", "1");
		contents.getActualContent().addAttributeToCurrentLayer("msg", "Das ist toll");
		contents.getActualContent().addAttributeToCurrentLayer("date", "11.01.2012");
		
		
		//5 verschiedene Tabellen 
		assertEquals(5, contents.getContent().size());
		
		//2 User
		assertEquals("Erwarte 2 User", 2, contents.getContent().get(0).getLayerCount());
		assertEquals("Das sind User Nodes", true, contents.isNode(contents.getContent().get(0).getTableSchema()));
		
		//3 Blogs
		assertEquals("Erwarte 3 Blogs", 3, contents.getContent().get(1).getLayerCount());
		assertEquals("Das sind Blog Nodes", true, contents.isNode(contents.getContent().get(1).getTableSchema()));
		
		//1 Kommentar
		assertEquals("Erwarte 1 Kommentar", 1, contents.getContent().get(4).getLayerCount());
		assertEquals("Das ist K-Node", true, contents.isNode(contents.getContent().get(4).getTableSchema()));
		
		
		
		//3 Tabellen sind Nodes
		assertEquals(3, contents.getNodes().size());
		
		//2 Tabellen sind Kanten
		assertEquals(2, contents.getRelationships().size());
		
		
		
		Generator transformer = new Generator();
		
		ArrayList<Node> nodes					= transformer.makeNodes(contents.getNodes());
		
		ArrayList<Relationship> relationships 	= transformer.makeManyToManyRelationships(contents.getRelationships(), nodes);
		ArrayList<Relationship> relationships2 	= transformer.makeOneToManyRelationships(nodes);
		
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
		Property id = new Property(true, null, "char", "id");
		Property name = new Property(true, null, "char", "name");
		
		Node node1 = new Node("Mensch");
		node1.addProperty(id, "u01");
		node1.addProperty(name, "benni");
		
		Node node2 = new Node("Mensch");
		node2.addProperty(id, "u02");
		node2.addProperty(name, "franz");
		
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(node1);
		nodes.add(node2);
		
		PropertyValueTupel pv = new PropertyValueTupel(new Property(false, new TableReference("US", "uid"), "char", "id"), "u01");
		
		Generator transformer = new Generator();
		
		assertEquals("Mensch", transformer.getNodeWithPrimaryKeyValue(pv, nodes).getLabel());
		
	}
	
	@Test
	public void testeEinsZuVieleRelationsships(){
		SchemaController schemes = new SchemaController();
		ContentController contents = new ContentController();
		
		schemes.addSchema(new Tableschema("USER"));
		schemes.getActualScheme().addProperty(new Property(true, null, "int", "id"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchr(128)", "name"));
		
		contents.addContent(new TableContent(schemes.getActualScheme()));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "1");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Ben");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "2");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Rey");
		
		
		schemes.addSchema(new Tableschema("BLOG"));
		schemes.getActualScheme().addProperty(new Property(true, null, "int", "id"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchr(128)", "name"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("USER", "id"), "int", "admin"));
		
		contents.addContent(new TableContent(schemes.getActualScheme()));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "1");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Science");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "2");
		contents.getActualContent().addAttributeToCurrentLayer("name", "French");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "3");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Math");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "2");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "4");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Bio");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "1");
		
		//2 Tabellen
		assertEquals(2, contents.getContent().size());
		
		assertEquals("Das sind User Nodes", true, contents.isNode(contents.getContent().get(0).getTableSchema()));
		assertEquals("Das sind Blog Nodes", true, contents.isNode(contents.getContent().get(1).getTableSchema()));
		
		Generator transformer = new Generator();
		ArrayList<Node> nodes					= transformer.makeNodes(contents.getNodes());
		ArrayList<Relationship> ntom 	= transformer.makeManyToManyRelationships(contents.getRelationships(), nodes);
		ArrayList<Relationship> oneToMany 	= transformer.makeOneToManyRelationships(nodes);
		
		
		assertEquals("Keine N to M Rels", 0, ntom.size());
		
		assertEquals("Ben", oneToMany.get(0).getEnd().getPropertyValue("name"));
		assertEquals("Science", oneToMany.get(0).getStart().getPropertyValue("name"));
		
		assertEquals("Ben", oneToMany.get(1).getEnd().getPropertyValue("name"));
		assertEquals("French", oneToMany.get(1).getStart().getPropertyValue("name"));
		
		assertEquals("Rey", oneToMany.get(2).getEnd().getPropertyValue("name"));
		assertEquals("Math", oneToMany.get(2).getStart().getPropertyValue("name"));
		
		assertEquals("Ben", oneToMany.get(3).getEnd().getPropertyValue("name"));
		assertEquals("Bio", oneToMany.get(3).getStart().getPropertyValue("name"));
	}
	
	@Test
	public void testeEinsZuVieleRelationsships2(){
		SchemaController schemes = new SchemaController();
		ContentController contents = new ContentController();
		
		schemes.addSchema(new Tableschema("USER"));
		schemes.getActualScheme().addProperty(new Property(true, null, "int", "id"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchr(128)", "name"));
		
		contents.addContent(new TableContent(schemes.getActualScheme()));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "1");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Ben");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "2");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Rey");
		
		
		schemes.addSchema(new Tableschema("BLOG"));
		schemes.getActualScheme().addProperty(new Property(true, null, "int", "id"));
		schemes.getActualScheme().addProperty(new Property(false, null, "varchr(128)", "name"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("USER", "id"), "int", "admin"));
		
		contents.addContent(new TableContent(schemes.getActualScheme()));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "1");
		contents.getActualContent().addAttributeToCurrentLayer("name", "Science");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "1");
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "2");
		contents.getActualContent().addAttributeToCurrentLayer("name", "French");
		contents.getActualContent().addAttributeToCurrentLayer("admin", "1");

		schemes.addSchema(new Tableschema("COMMENT"));
		schemes.getActualScheme().addProperty(new Property(true, null, "int", "id"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("BLOG", "id"), "int", "cblog"));
		schemes.getActualScheme().addProperty(new Property(false, new TableReference("USER", "id"), "int", "cuser"));
		schemes.getActualScheme().addProperty(new Property(false, null, "char", "msg"));
		
		contents.addContent(new TableContent(schemes.getActualScheme()));
		contents.getActualContent().addContentLayer();
		contents.getActualContent().addAttributeToCurrentLayer("id", "1");
		contents.getActualContent().addAttributeToCurrentLayer("cblog", "1");
		contents.getActualContent().addAttributeToCurrentLayer("cuser", "1");
		contents.getActualContent().addAttributeToCurrentLayer("msg", "wow");
		
		//2 Tabellen
		assertEquals(3, contents.getContent().size());
		
		assertEquals("Das sind User Nodes", true, contents.isNode(contents.getContent().get(0).getTableSchema()));
		assertEquals("Das sind Blog Nodes", true, contents.isNode(contents.getContent().get(1).getTableSchema()));
		assertEquals("Das sind Kommentar Nodes", true, contents.isNode(contents.getContent().get(2).getTableSchema()));
		
		Generator transformer = new Generator();
		ArrayList<Node> nodes			= transformer.makeNodes(contents.getNodes());
		ArrayList<Relationship> ntom 	= transformer.makeManyToManyRelationships(contents.getRelationships(), nodes);
		ArrayList<Relationship> oneToMany 	= transformer.makeOneToManyRelationships(nodes);
		
		
		assertEquals("Keine N to M Rels", 0, ntom.size());
		//assertEquals("Science", oneToMany.get(2).getStart().getPropertyValue("name"));
		//assertEquals("wow", oneToMany.get(2).getEnd().getPropertyValue("msg"));
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(ntom));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(oneToMany));
	}
	
	

}
