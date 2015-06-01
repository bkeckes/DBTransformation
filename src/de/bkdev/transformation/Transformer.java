package de.bkdev.transformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;


public class Transformer {

	private static final Logger log4j = LogManager.getLogger(Transformer.class
	        .getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<3){
	  		System.out.println("ERROR: You have to use the Parameter: dburl, dbname, user, password");
	  		return;
	  	}
		String dburl = args[0];
		String dbName =  args[1];
		String user = args[2];
		String pwd = args[3];
			
		DatabaseReader reader = new DatabaseReader(dburl,dbName,user,pwd);
		GraphDatabaseService graphDb = null;
		try{
			graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "C:\\Users\\Benni\\Documents\\Neo4j\\default.graphdb" );
		}catch(RuntimeException e){
			//"Could not connect to GraphDB"
			e.printStackTrace();
		}
		log4j.info("Transaction to Neo4j DB...");
		int nodeCounter = 0;
		int relCounter = 0;
		int constCounter = 0;
		try ( Transaction tx = graphDb.beginTx() )
		{
			//erst alles loeschen
			graphDb.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r;");
			
			for(String cons : reader.getConstStatements()){
				//System.out.println(cons);
				
				try{
					graphDb.execute(cons);
					constCounter++;
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
			for(String node : reader.getNodeStatements()){
				//System.out.println(node);
				graphDb.execute(node);
				nodeCounter++;
			}
				
			for(String rel : reader.getRelStatements()){
				graphDb.execute(rel);
				//System.out.println(rel);
				relCounter++;
			}
		    tx.success();
		}
		log4j.info("Transaction done");
		log4j.info(constCounter + " Constraints, " + nodeCounter + " Nodes and " +relCounter + " Relationships created");
	}

	

}
