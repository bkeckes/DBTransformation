package de.bkdev.transformation.storage.graph;


/**
 * Relation.
 * @author Benjamin Keckes
 *
 */
public class Relationship extends GraphObject{

	private String label;
	private NodeTupel startAndEnd;
	//private final String relID;
	
	public Relationship(String label, NodeTupel startAndEnd){
		super();
		this.label = label;
		this.startAndEnd = startAndEnd;
		//this.relID = IdentificationMaker.makeRelationshipID();
	}

	public String getRelID() {
		//return relID;
		return "r";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Node getStart(){
		return startAndEnd.getStart();
	}
	
	public Node getEnd(){
		return startAndEnd.getEnd();
	}
	
	public String toString(){
		String tempLabel="";
		if(this.label!=null && !this.label.equals(""))
			tempLabel = "[:" + this.label + "]";
		
		return "(" + startAndEnd.getStart().getLabel() + ") - " + tempLabel + "-> (" + startAndEnd.getEnd().getLabel() + ")";
				
	}
	
//	public String getProperty(){
//		
//		return propertySet.contains(o)
//	}
	
	
}
