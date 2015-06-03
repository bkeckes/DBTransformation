package de.bkdev.transformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.parboiled.common.Reference;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentController;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.SchemaController;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.TableReference;
import de.bkdev.transformation.storage.relational.Tableschema;
import de.bkdev.transformation.transformer.StatementMaker;
import de.bkdev.transformation.transformer.Generator;

import schemacrawler.schema.Column;
import schemacrawler.schema.Catalog;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schema.View;
import schemacrawler.schemacrawler.DatabaseConnectionOptions;
import schemacrawler.schemacrawler.ExcludeAll;
import schemacrawler.schemacrawler.RegularExpressionInclusionRule;
import schemacrawler.schemacrawler.SchemaCrawlerException;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.utility.SchemaCrawlerUtility;

public class DatabaseReader {
	
	private static final Logger log4j = LogManager.getLogger(DatabaseReader.class
		        .getName());
	
	
	private SchemaController schemaController = new SchemaController();
	private ContentController contentController = new ContentController();
	
	private ArrayList<String> constStatements;
	private ArrayList<String> nodeStatements;
	private ArrayList<String> relStatements;
	
	public DatabaseReader(String dburl, String dbName, String user, String password){
		try {

    		final Connection connection = DriverManager.getConnection(dburl + "/" + dbName + "?user=" + user + "&password=" + password);
    		
    		
    	    final SchemaCrawlerOptions options = new SchemaCrawlerOptions();

    	    options.setSchemaInfoLevel(SchemaInfoLevel.minimum());
    	    options.setRoutineInclusionRule(new ExcludeAll());

    	    final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, new SchemaCrawlerOptions());
    	    
    	    final Schema tableSchema = catalog.getSchema(dbName);
    	    
    	    
    	    log4j.info("Reading RDB '" + tableSchema.getFullName() + "'");
    	    
    	    //Alle Tabellen hintereinander
    	    for (final Table table: catalog.getTables(tableSchema)){
    	    	
    	    	schemaController.addSchema(new Tableschema(removeMarks(table.getName())));
    	    	log4j.info("Reading Schema from table '" + removeMarks(table.getName()) + "'");
    	    	
    	    	for (final Column column: table.getColumns()){
    	    		
    	    		TableReference refCol = null;
    	    		
    	    		//Ist es ein Foreignkey?
    	    		if(column.getReferencedColumn()!=null){
    	    			refCol = new TableReference(column.getReferencedColumn().getParent().getName().toString(), column.getReferencedColumn().getName().toString());
    	    			log4j.info("'" + table.getName() + "." + column.getName() + "' has a reference to table '" + refCol + "'");
    	    		}
    	    		
    	    		//Schemateil zum aktuellen Schema hinzufuegen
	    			schemaController.getActualScheme().addProperty(new Property(
	    					column.isPartOfPrimaryKey(), 
	    					refCol,
							column.getColumnDataType().getFullName(), 
							removeMarks(column.getName())));   	    		
    	    		
    	    	}
    	    	
    	    	//Daten werden geholt.
    	    	contentController.addContent(new TableContent(schemaController.getSchema(schemaController.getActualScheme().getTableName())));
    	    	
    	    	log4j.info("Reading Data from table '" + table.getName() + "'");
    	    	contentController = readContent(connection, table.getName(), contentController, schemaController.getActualScheme().getPropertyCount());

    	    }

    	} catch (SQLException ex) {
    	    // handle any errors
    		log4j.error("An SQL ERROR occured while reading the data");
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	    return;
    	} catch (SchemaCrawlerException e) {
			log4j.error("An ERROR occured while reading the schema");
			e.printStackTrace();
			return;
		}
		
		
		
		Generator transformer = new Generator();
		
		//Nodes werden erstellt.
		ArrayList<Node> nodes					= transformer.makeNodes(contentController.getNodes());
		
		//Kanten werden erstellt.
		ArrayList<Relationship> nToMRelationships 	= transformer.makeManyToManyRelationships(contentController.getRelationships(), nodes);
		
		//Kanten aus normalen 1:1 oder 1:n Beziehungen werden erstellt.
		ArrayList<Relationship> oneToManyRelationships 	= transformer.makeOneToManyRelationships(nodes);
		
		
		constStatements = StatementMaker.makeConstraints(schemaController.getNodeSchemas());
		log4j.info("Constraints: " + constStatements.size());
		
		
		
		nodeStatements = StatementMaker.makeCypherStatementFromNodes(nodes);
		log4j.info(nodeStatements.size() + " Nodes identified");		
		//System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		
		
		relStatements = StatementMaker.makeCypherStatementFromSingleRelationships(nToMRelationships);
		relStatements.addAll(StatementMaker.makeCypherStatementFromSingleRelationships(oneToManyRelationships));
		
		log4j.info(nToMRelationships.size() + " 1:1 or 1:n Relationships identified");
	
		log4j.info(oneToManyRelationships.size() + " n:m Relationships identified");
	}
	


	  
	  public ArrayList<String> getConstStatements() {
		return constStatements;
	}




	public ArrayList<String> getNodeStatements() {
		return nodeStatements;
	}




	public ArrayList<String> getRelStatements() {
		return relStatements;
	}




	private ContentController readContent(Connection conn, String table, ContentController contents, int propCount){
		  
		 
		  try{
			  Statement statement = conn.createStatement();
			  String query = "SELECT * FROM " + table;
			  ResultSet resultSet = statement.executeQuery(query);
			  while (resultSet.next()){
				  contents.getActualContent().addContentLayer();
				  //System.out.println(resultSet.getString(1));
				  for(int i=1; i<=propCount; i++){
					  
					  contents.getActualContent().addAttributeToCurrentLayerByIndex(i-1, resultSet.getString(i));
				  }
			  }  
		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	  }
	  
	  public String removeMarks(String name){
		  return name.replace("`", "").replace(" ", "_");
	  }
	  
}
