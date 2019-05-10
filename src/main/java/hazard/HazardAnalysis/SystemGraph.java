package hazard.HazardAnalysis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;

public class SystemGraph extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307392183311600543L;

	public SystemGraph() {
		super("System Diagram");
		JScrollPane scrPane = new JScrollPane();
		this.add(scrPane);
		scrPane.getHorizontalScrollBar().setUnitIncrement(50);
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		Map<String, Object> style = graph.getStylesheet().getDefaultVertexStyle();
		style.put(mxConstants.STYLE_ROUNDED, true);
		style.put(mxConstants.STYLE_SHADOW, true);
		style.put(mxConstants.STYLE_AUTOSIZE, 1);
		style.put(mxConstants.STYLE_FONTSIZE, 14);
//		style.put(mxConstants.STYLE_GRADIENTCOLOR, "yellow");
		style = graph.getStylesheet().getDefaultEdgeStyle();
		style.put(mxConstants.STYLE_STROKECOLOR, "black");
		graph.getModel().beginUpdate();
		HashMap<String, Object> diagram = new HashMap<String, Object>();
		try {
			ResultSet rs = DataBaseConnection.sql("SELECT * FROM relator;");
			while (rs.next()) {
				String s = rs.getString("relator");
				Object o = graph.insertVertex(parent, null, s, 100, 100, 120, 40, "fillColor=white;");
				diagram.put(s, o);
			}
			rs = DataBaseConnection.sql("SELECT * FROM role;");
			while (rs.next()) {
				String s = rs.getString("role");
				Object o = graph.insertVertex(parent, null, s, 100, 100, 120, 40, "fillColor=#C4C4C4;");
				diagram.put(s, o);
			}
			rs = DataBaseConnection.sql("SELECT * FROM kind;");
			while (rs.next()) {
				String s = rs.getString("kind");
				Object o = graph.insertVertex(parent, null, s, 100, 100, 120, 40, "fillColor=#69D4D0;");
				diagram.put(s, o);
			}
			rs = DataBaseConnection.sql("SELECT * FROM roletoplay;");
			while (rs.next()) {
				String kind = rs.getString("kind");
				String role = rs.getString("role");
				graph.insertEdge(parent, null, "", diagram.get(kind), diagram.get(role));
			}
			rs = DataBaseConnection.sql("SELECT * FROM relatortorole;");
			while (rs.next()) {
				String relator = rs.getString("relator");
				String role = rs.getString("role");
				graph.insertEdge(parent, null, "", diagram.get(role), diagram.get(relator));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			graph.getModel().endUpdate();
		}
		mxHierarchicalLayout graphComponent = new mxHierarchicalLayout(graph);
		graphComponent.setFineTuning(true);
		graphComponent.execute(parent);
		mxGraphComponent graphComponents = new mxGraphComponent(graphComponent.getGraph());
		getContentPane().add(graphComponents);
	}
}