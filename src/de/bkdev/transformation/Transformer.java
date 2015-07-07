package de.bkdev.transformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


public class Transformer {

	private static String configFile;
	private static final Logger log4j = LogManager.getLogger(Transformer.class
	        .getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		configFile = "config.properties";
		
		if(args.length>0){
			configFile = args[0];
	  	}
		
		log4j.info("Reading configfile '" + configFile + "'");
		ConfigFileReader.readFile(configFile);
		if(!ConfigFileReader.isFileValid())
			return;
		
		// jdbc:mysql://localhost;
		String dburl = "jdbc:" + ConfigFileReader.getRelationalDatabaseType() + "://" + ConfigFileReader.getRelationalDatabasePath();
		String dbName =  ConfigFileReader.getRelationalDatabaseName();
		String user = ConfigFileReader.getRelationalDatabaseUser();
		String pwd = ConfigFileReader.getRelationalDatabasePassword();
		String tableNames = ConfigFileReader.getTableNames();
			
		log4j.info("Using Relational Database: " + dburl + dbName + ", Graph Database: " + ConfigFileReader.getGraphDatabasePath());
		
		DatabaseReader reader = new DatabaseReader(dburl,dbName,user,pwd, tableNames);
		
		GraphDatabaseService graphDb = null;
		try{
			graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(ConfigFileReader.getGraphDatabasePath());
		}catch(RuntimeException e){
			log4j.error("Could not connect to GraphDB " + ConfigFileReader.getGraphDatabasePath());
			e.printStackTrace();
			return;
		}
		log4j.info("Transaction to Neo4j DB...");
		int nodeCounter = 0;
		int relCounter = 0;
		int constCounter = 0;
		
		try ( Transaction tx = graphDb.beginTx() )
		{
			graphDb.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r");
			tx.success();
			log4j.info("cleaned graphDB from existing nodes and relationships");
		}catch(Exception e){
			log4j.error("Could not perform statement for deleting all existing nodes");
			e.printStackTrace();
		}
		
		try ( Transaction tx = graphDb.beginTx() )
		{	
			
			//Delete all Indizies and Constraints first
			for(String del : reader.getDeleteStatements()){
				try{
					
					graphDb.execute(del);
					System.out.println(del);
					
				}catch(Exception e){
					System.out.println(del + " konnte nicht ausgefuehrt werden");
				}
			}
			tx.success();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		log4j.info("cleaned graphDB from existing constraints");
		try ( Transaction tx = graphDb.beginTx() )
		{	
						
			for(String cons : reader.getConstStatements()){
				try{
					System.out.println(cons);
					graphDb.execute(cons);
					constCounter++;
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			tx.success();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try ( Transaction tx = graphDb.beginTx() )
		{	
						
			for(String node : reader.getNodeStatements()){
				System.out.println(node);
				graphDb.execute(node);
				nodeCounter++;
			}
				
			for(String rel : reader.getRelStatements()){
				graphDb.execute(rel);
				System.out.println(rel);
				relCounter++;
			}
		    tx.success();
		}
		log4j.info("Transaction done");
		log4j.info(constCounter + " Constraints, " + nodeCounter + " Nodes and " +relCounter + " Relationships created");
	}

	

}
