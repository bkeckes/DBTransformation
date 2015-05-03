package de.bkdev.transformation.storage.graph.template;

public class GDBRelation extends GDBTemplate{

	public GDBRelation(String label) {
		super(label);
	}

	@Override
	public String identify() {
		return "Relation";
	}
	
	
	
}
