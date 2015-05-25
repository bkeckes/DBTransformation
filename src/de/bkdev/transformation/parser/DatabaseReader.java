package de.bkdev.transformation.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	
	public DatabaseReader(String dburl, String dbName, String user, String password){
		try {

    		final Connection connection = DriverManager.getConnection(dburl + "/" + dbName + "?user=" + user + "&password=" + password);
    		

    	    final SchemaCrawlerOptions options = new SchemaCrawlerOptions();

    	    options.setSchemaInfoLevel(SchemaInfoLevel.minimum());
    	    options.setRoutineInclusionRule(new ExcludeAll());

    	    final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, new SchemaCrawlerOptions());
    	    
    	    final Schema delSchema = catalog.getSchema(dbName);
    	    
    	    System.out.println("Lese DB: " + delSchema.getFullName());
    	    for (final Table table: catalog.getTables(delSchema)){
    	    	
    	    	schemes.addScheme(new Tablescheme(removeMarks(table.getName())));
    	    	
    	    	for (final Column column: table.getColumns()){
    	    		schemes.getActualScheme().addProperty(new Property( column.isPartOfPrimaryKey(), 
    	    															column.isPartOfForeignKey(), 
    	    															column.getColumnDataType().getFullName(), 
    	    															column.getName()));
    	    		
    	    	}
    	    	System.out.println(schemes.getActualScheme().getName()+" FKs"
	    				+ schemes.getActualScheme().getForeignKeyCount());
    	    	
    	    	contents.addContent(new TableContent(schemes.getScheme(schemes.getActualScheme().getName())));
    	    	contents = readContent(connection, table.getName(), contents, schemes.getActualScheme().getPropertyCount());


    	    	
    	    	
    	    	
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
		
		
		
		TransformerController transformer = new TransformerImpl();
		
		ArrayList<Node> nodes					= transformer.makeNodes(contents.getNodes());
		ArrayList<Relationship> relationships 	= transformer.makeRelationship(contents.getRelationships(), nodes);
		ArrayList<Relationship> relationships2 	= transformer.makeRelationshipsWithProperties(nodes);
		
		System.out.println("Nodes: "+nodes.size());
		System.out.println(StatementMaker.makeCypherStatementFromNodes(nodes));
		
		System.out.println("Relationships: "+(relationships.size()+relationships2.size()));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships));
		System.out.println(StatementMaker.makeCypherStatementFromRelationships(relationships2));
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
		  return name.replace("`", "");
	  }
	  
	  public static void main(String[] args) {
			String dburl = args[0];
			String dbName =  args[1];
			String user = args[2];
			String pwd = args[3];
			if(dburl.isEmpty() || dbName.isEmpty() || user.isEmpty() || pwd.isEmpty()){
				System.out.println("Parameter: dburl, dbname, user, password");
				return;
			}
				
			new DatabaseReader(dburl,dbName,user,pwd);
		}
}
