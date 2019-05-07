package hazard.HazardAnalysis;

import javax.swing.JFrame;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import hazard.HazardClasses.PossibleVictim;

public class PossibleVictimGraph extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -965262014458195774L;

	public PossibleVictimGraph(PossibleVictim pv) {
		super("Possible Victim");
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		try {
			graph.setAutoSizeCells(true);
			Object v1 = graph.insertVertex(parent, null, pv.getKind(), 20, 20, 100, 40,"fillColor=blue;");
			Object v2 = graph.insertVertex(parent, null, pv.getRole(), 240, 150, 100, 40,"fillColor=grey;");
			Object v3 = graph.insertVertex(parent, null, pv.getRelator(), 240, 150, 100, 40,"fillColor=white;");
			Object v4 = graph.insertVertex(parent, null, pv.getRole2(), 240, 150, 100, 40,"fillColor=grey;");
			Object v5 = graph.insertVertex(parent, null, pv.getKind2(), 240, 150, 100, 40,"fillColor=blue;");
			graph.insertEdge(parent, null, "", v1, v2);
			graph.insertEdge(parent, null, "", v2, v3);
			graph.insertEdge(parent, null, "", v5, v4);
			graph.insertEdge(parent, null, "", v4, v3);
		} finally {
			graph.getModel().endUpdate();
		}
		mxHierarchicalLayout graphComponent = new mxHierarchicalLayout(graph);
		graphComponent.execute(parent);
		mxGraphComponent graphComponents = new mxGraphComponent(graphComponent.getGraph());
		getContentPane().add(graphComponents);
	}
}