package hazard.HazardAnalysis.Graph;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;

public class RelatorGraph extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -965262014458195774L;

	public RelatorGraph(String relator) throws SQLException {
		super("Possible Victim");
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
		style.put(mxConstants.STYLE_ROUNDED, true);
		style.put(mxConstants.STYLE_SHADOW, true);
		style.put(mxConstants.STYLE_AUTOSIZE, 1);
		style.put(mxConstants.STYLE_FONTSIZE, 14);
		style = graph.getStylesheet().getDefaultEdgeStyle();
		style.put(mxConstants.STYLE_STROKECOLOR, "black");
		graph.getModel().beginUpdate();
		HashMap<String, Object> diagram = new HashMap<String, Object>();
		try {
			ResultSet rs = DataBaseConnection.sql(
					"Select relatortorole.relator, roletoplay.role,roletoplay.kind from relatortorole,roletoplay where relatortorole.relatorid =(Select relator.id from relator where relator.relator='"
							+ relator + "') and roletoplay.roleid = relatortorole.roleid;");
			Object r = null;
			int index = 0;
			while (rs.next()) {
				String role = rs.getString("role");
				String kin = rs.getString("kind");
				if (index == 0) {
					String rel = rs.getString("relator");
					r = graph.insertVertex(parent, null, rel, 100, 100, 120, 40, "fillColor=white;");
					index++;
				}
				Object rr = null;
				if (!diagram.containsKey(role)) {
					rr = graph.insertVertex(parent, null, role, 100, 100, 120, 40, "fillColor=#C4C4C4;");
					diagram.put(role, rr);
					graph.insertEdge(parent, null, "", diagram.get(role), r);
				}
				Object k = graph.insertVertex(parent, null, kin, 100, 100, 120, 40, "fillColor=#69D4D0;");
				diagram.put(kin, k);
				graph.insertEdge(parent, null, "", diagram.get(kin), diagram.get(role));
			}
		} finally {
			graph.getModel().endUpdate();
		}
		mxHierarchicalLayout graphComponent = new mxHierarchicalLayout(graph);
		graphComponent.execute(parent);
		mxGraphComponent graphComponents = new mxGraphComponent(graphComponent.getGraph());
		getContentPane().add(graphComponents);
	}
}