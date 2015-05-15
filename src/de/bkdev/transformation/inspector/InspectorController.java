package de.bkdev.transformation.inspector;

import de.bkdev.transformation.storage.graph.template.GDBTemplate;
import de.bkdev.transformation.storage.relational.Tablescheme;

public interface InspectorController {
	public GDBTemplate transformTableToGraph(Tablescheme table);
}
