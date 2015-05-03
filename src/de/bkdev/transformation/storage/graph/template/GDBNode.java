package de.bkdev.transformation.storage.graph.template;

public class GDBNode extends GDBTemplate {

	public GDBNode(String label) {
		super(label);
	}

	@Override
	public String identify() {
		return "Node";
	}
	
}
