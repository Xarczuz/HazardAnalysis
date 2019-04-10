package hazard.HazardAnalysis.Step1.Views;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class RunTimeView {
	public static GridPane addGridPane() {
		GridPane grid = new GridPane();
		Text text = new Text();
		text.setText("START VIEW");
		grid.add(text, 1, 1);
		//TODO
		return grid;
	}
//TODO
}
