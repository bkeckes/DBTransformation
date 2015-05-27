package de.bkdev.transformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
public class Main {

	/**
	 * @param args
	 */
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}
	
	private static final Logger log4j = LogManager.getLogger(Main.class
	        .getName());
	
	public static void main(String[] args) {

		//System.out.println(node.toString());
		
		//GDBConnection con = new GDBConnection();
		test();
		
		log4j.trace("This is a trace message.");
		log4j.debug("This is  a debug message.");
		log4j.info("This is an info message.");
		log4j.error("This is an error message");

	}
	
	public static void test(){
		GraphDatabaseService graphDb;
		Node firstNode;
		Node secondNode;
		Relationship relationship;
		
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "C:\\Users\\Benni\\Documents\\Neo4j\\default.graphdb" );
		registerShutdownHook( graphDb );
		
		try ( Transaction tx = graphDb.beginTx() )
		{
//			firstNode = graphDb.createNode();
//			firstNode.setProperty( "message", "Hello, " );
//			secondNode = graphDb.createNode();
//			secondNode.setProperty( "message", "World!" );
//
//			relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
//			relationship.setProperty( "message", "brave Neo4j " );
			graphDb.execute("CREATE (cid2:ct {cid:'6', cblog:'1', cuser:'1', msg:'hey, sehr cool', date:'2011-02-20'});");
		    tx.success();
		}
	}

	private static void registerShutdownHook(GraphDatabaseService graphDb) {
		// TODO Auto-generated method stub
		
	}

}
