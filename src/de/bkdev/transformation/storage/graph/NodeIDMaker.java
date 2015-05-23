package de.bkdev.transformation.storage.graph;


public class NodeIDMaker {
	public static int id;
	public static String makeNewNodeID(){
		return "n" +(++id);
	}
	
}
