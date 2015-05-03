package de.bkdev.transformation;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;



public class GDBConnection {
	GraphDatabaseService gdb = new GraphDatabaseFactory().
			newEmbeddedDatabase("C:/Users/Benni/Documents/Neo4j/default.graphdb");
	
	Node n1 = gdb.createNode();
	Node n2  = gdb.createNode();
	//Relationship e12 = n1.createRelationshipTo(n2, Edget);
	
//	n1.setProperty("name", "Rome");
//	n2.setProperty("name", "Lazio");
//	e12.setProperty("type", "locatedIn");
}
