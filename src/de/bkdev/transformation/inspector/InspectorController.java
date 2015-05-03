package de.bkdev.transformation.inspector;

import de.bkdev.transformation.storage.graph.template.GDBTemplate;
import de.bkdev.transformation.storage.relational.Table;

public interface InspectorController {
	public GDBTemplate getObject(Table table);
}
