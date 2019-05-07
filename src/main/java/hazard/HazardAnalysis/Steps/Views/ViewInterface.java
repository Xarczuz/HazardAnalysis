package hazard.HazardAnalysis.Steps.Views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public interface ViewInterface {
	public GridPane addGridPane();

	public GridPane getGridPane();

	public BorderPane getMainView();

	public GridPane getNextGridPane();

	public GridPane getPrevGridPane();

	public void setNextGp(GridPane nextGp);
}