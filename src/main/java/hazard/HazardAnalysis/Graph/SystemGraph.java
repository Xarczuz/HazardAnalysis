package hazard.HazardAnalysis.Graph;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;

public class SystemGraph extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307392183311600543L;

	public SystemGraph() throws IOException {
		super("System Diagram");
		JScrollPane scrPane = new JScrollPane();
		this.add(scrPane);
		scrPane.getHorizontalScrollBar().setUnitIncrement(150);
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
			ResultSet rs = DataBaseConnection.sql("SELECT * FROM relator;");
			int y = 100;
			while (rs.next()) {
				String s = rs.getString("relator");
				y += 100;
				Object o = graph.insertVertex(parent, null, s, 500, y, 120, 40, "fillColor=white;");
				diagram.put(s, o);
			}
			y = 100;
			rs = DataBaseConnection.sql("SELECT * FROM role;");
			while (rs.next()) {
				String s = rs.getString("role");
				y += 100;
				Object o = graph.insertVertex(parent, null, s, 300, y, 120, 40, "fillColor=#C4C4C4;");
				diagram.put(s, o);
			}
			rs = DataBaseConnection.sql("SELECT * FROM kind;");
			y = 100;
			while (rs.next()) {
				String s = rs.getString("kind");
				y += 100;
				Object o = graph.insertVertex(parent, null, s, 100, y, 120, 40, "fillColor=#69D4D0;");
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
		graphComponent.setInterRankCellSpacing(50);
		graphComponent.setIntraCellSpacing(20);
		graphComponent.setFineTuning(true);
		graphComponent.setMoveParent(true);
		graphComponent.execute(parent);
		mxGraphComponent graphComponents = new mxGraphComponent(graphComponent.getGraph());
		getContentPane().add(graphComponents);
		BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);
		ImageIO.write(image, "PNG", new File("graph.png"));
	}
}
