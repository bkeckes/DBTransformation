package de.bkdev.transformation.transformer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.table.TableStringConverter;

import org.junit.Test;

import de.bkdev.transformation.inspector.InspectorController;
import de.bkdev.transformation.inspector.InspectorImpl;
import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.relational.ContentController;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.SchemeController;
import de.bkdev.transformation.storage.relational.Tablescheme;
import de.bkdev.transformation.storage.relational.template.TableContent;
import de.bkdev.transformation.storage.relational.template.TableList;

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
		
		TableList tableList = new TableList();
		tableList.add(tc);
		
		TransformerController transformer = new TransformerImpl();
		
		ArrayList<Node> nodes=null;
		InspectorController inspector = new InspectorImpl();
		
		if(inspector.transformTableToGraph(tablescheme).identify().equals("Node"))
				nodes = transformer.makeNodes(tableList);
		
		
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		assertEquals(2, nodes.size());
	}
	
	@Test
	public void testeRelation(){
		Tablescheme table = new Tablescheme("FR");
		table.addProperty(new Property(false, true, "varchar(20)", "fuser"));
		table.addProperty(new Property(false, true, "varchar(20)", "fblog"));
		
		TableContent tc = new TableContent(table);
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("fuser", "u01");
		tc.addAttributeToCurrentLayer("fblog", "b01");
		tc.addContentLayer();
		tc.addAttributeToCurrentLayer("fuser", "u01");
		tc.addAttributeToCurrentLayer("fblog", "b02");
		
		TableList tableList = new TableList();
		tableList.add(tc);
		
		TransformerController transformer = new TransformerImpl();
		HashSet<Node> relation=null;
		InspectorController inspector = new InspectorImpl();
		
		//relation = transformer.makeNodes(tableList);
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
		
		/*
		 * TODO ähnliches wie Tablelist. Der Inhalt von contents muss nach nodes gefiltert werden und erstellt werden.
		 * Anschließend werden die Relationships erstellt.
		 */
	}

}
