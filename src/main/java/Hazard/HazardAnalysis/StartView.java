package Hazard.HazardAnalysis;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class StartView {
	public static GridPane addGridPane() {
		GridPane grid = new GridPane();
		Text text = new Text();
		text.setText("START VIEW");
		grid.add(text, 1, 1);
		return grid;
	}
}
