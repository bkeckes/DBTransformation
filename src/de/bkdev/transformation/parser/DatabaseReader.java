package de.bkdev.transformation.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import de.bkdev.transformation.storage.graph.Node;
import de.bkdev.transformation.storage.graph.Relationship;
import de.bkdev.transformation.storage.relational.ContentController;
import de.bkdev.transformation.storage.relational.Property;
import de.bkdev.transformation.storage.relational.SchemeController;
import de.bkdev.transformation.storage.relational.TableContent;
import de.bkdev.transformation.storage.relational.Tablescheme;
import de.bkdev.transformation.transformer.StatementMaker;
import de.bkdev.transformation.transformer.TransformerController;
import de.bkdev.transformation.transformer.TransformerImpl;

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
	
	private SchemeController schemes = new SchemeController();
	private ContentController contents = new ContentController();
	
	
	public SchemeController readSchemes(Connection connection, String dbName){
		
		return null;
	}
	public DatabaseReader(String db){
		try {

    		final Connection connection = DriverManager.getConnection(db);
    		

    	    final SchemaCrawlerOptions options = new SchemaCrawlerOptions();

    	    options.setSchemaInfoLevel(SchemaInfoLevel.minimum());
    	    options.setRoutineInclusionRule(new ExcludeAll());

    	    final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, new SchemaCrawlerOptions());
    	    
    	    final Schema delSchema = catalog.getSchema("delikat");
    	    
    	    System.out.println("Lese DB: " + delSchema.getFullName());
    	    for (final Table table: catalog.getTables(delSchema)){
    	    	
    	    	schemes.addScheme(new Tablescheme(removeMarks(table.getName())));
    	    	
    	    	for (final Column column: table.getColumns()){
    	    		schemes.getActualScheme().addProperty(new Property( column.isPartOfPrimaryKey(), 
    	    															column.isPartOfForeignKey(), 
    	    															column.getColumnDataType().getFullName(), 
    	    															column.getName()));
    	    	}
    	    	
    	    	contents.addContent(new TableContent(schemes.getScheme(schemes.getActualScheme().getName())));
    	    	contents = readContent(connection, table.getName(), contents, schemes.getActualScheme().getPropertyCount());


    	    	TransformerController transformer = new TransformerImpl();
    			
    			ArrayList<Node> nodes					= transformer.makeNodes(contents.getNodes());
    			ArrayList<Relationship> relationships 	= transformer.makeRelationship(contents.getRelationships(), nodes);
    			ArrayList<Relationship> relationships2 	= transformer.makeRelationshipsWithProperties(nodes);
    			
    			System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
    			System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships));
    			System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships2));
    	    	
    	    	/*
    	    	 System.out.print("o--> " + table);
    	    	 
    	    	if (table instanceof View){
    	    		System.out.println(" (VIEW)");
    	    	}
		        else{
		        	System.out.println();
		        }
    	    	for (final Column column: table.getColumns()){
		          System.out.println("     o--> " + column + " ("
		                             + column.getColumnDataType() + ")"
		                             + isPK(column.isPartOfPrimaryKey())
		                             + isFK(column.isPartOfForeignKey()));
		        }
    	    	
    	    	readContent(connection, table);
    	    	*/
    	    }

    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	} catch (SchemaCrawlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Tablescheme scheme : schemes.getSchemes()){
			System.out.println("Schema: "+scheme.getName() + ", c: "+scheme.getPropertyCount());
		}
		for(TableContent content : contents.getNodes()){
			System.out.println("Conent fuer: "+content.getTableScheme().getName() +" - " + content.getLayerCount());
		}
	}
	
	public static void main(String[] args) {
		new DatabaseReader("jdbc:mysql://localhost/delikat?" + "user=root&password=admin");
    	
	}
	
	private String isPK(boolean p){
		if(p)
			return "PK";
		return "";
	}
	  private String isFK(boolean p){
		  if(p)
			  return "FK";
		  return "";
	  }
	  
	  private ContentController readContent(Connection conn, String table, ContentController contents, int propCount){
		  
		 
		  try{
			  Statement statement = conn.createStatement();
			  String query = "SELECT * FROM " + table;
			  ResultSet resultSet = statement.executeQuery(query);
			  while (resultSet.next()){
				  contents.getActualContent().addContentLayer();
				  //System.out.println(resultSet.getString(1));
				  
				  for(int i=1; i<propCount; i++){
					  contents.getActualContent().addAttributeToCurrentLayer(contents.getActualContent().getTableScheme().getPropertyNameByIndex(i), resultSet.getString(i));
				  }
			  }  
		  
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contents;
	  }
	  
	  public String removeMarks(String name){
		  return name.replace("`", "");
	  }
}
