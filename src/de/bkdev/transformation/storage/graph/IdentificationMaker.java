package de.bkdev.transformation.storage.graph;


public class IdentificationMaker {
	public static int nodeId;
	public static int relationshipId;
	
	public static String makeNewNodeID(){
		return "n" +(++nodeId);
	}
	
	public static String makeRelationshipID(){
		return "r" +(++relationshipId);
	}
	
}
