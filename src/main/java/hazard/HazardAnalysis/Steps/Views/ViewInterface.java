package hazard.HazardAnalysis.Steps.Views;

import javafx.scene.layout.GridPane;

public interface ViewInterface {
	public GridPane addGridPane();

	public GridPane getGridPane();

	public String getStep();

	public String getStepDescription();
}