package de.bkdev.transformation;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import de.bkdev.transformation.parser.DatabaseReader;

public class Transformator {

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

		try ( Transaction tx = graphDb.beginTx() )
		{
			//erst alles loeschen
			//graphDb.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r;");
			
			for(String cons : reader.getConstStatements()){
				//graphDb.execute(cons);
				System.out.println(cons);
			}
			
			for(String node : reader.getNodeStatements()){
				System.out.println(node);
				graphDb.execute(node);
				
			}
				
			for(String rel : reader.getRelStatements()){
				graphDb.execute(rel);
				System.out.println(rel);
			}
		    tx.success();
		}

	}

	

}
