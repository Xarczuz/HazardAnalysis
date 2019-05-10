package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class ViewStep8 implements ViewInterface {
	private GridPane thisGp;

	public ViewStep8() {
		this.thisGp = addGridPane();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("gridpane");
		final TableView<Hazard> tbHazard = new TableView<Hazard>();
		tbHazard.setMinWidth(800);
		tbHazard.setMaxHeight(200);
		TableColumn<Hazard, Integer> id = new TableColumn<Hazard, Integer>("ID");
		TableColumn<Hazard, String> hazard = new TableColumn<Hazard, String>("Hazard");
		TableColumn<Hazard, String> hazardDescription = new TableColumn<Hazard, String>("Hazard Description");
		hazard.setMinWidth(400);
		hazardDescription.setMinWidth(350);
		id.setCellValueFactory(new PropertyValueFactory<Hazard, Integer>("id"));
		hazard.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazard"));
		hazardDescription.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazardDescription"));
		tbHazard.getColumns().addAll(id, hazard, hazardDescription);
		final TableView<Cause> tbCause = new TableView<Cause>();
		tbCause.setMinWidth(800);
		tbCause.setMaxHeight(200);
		TableColumn<Cause, String> cause = new TableColumn<Cause, String>("Pre-initiating event for hazard");
		cause.setMinWidth(400);
		cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		tbCause.getColumns().addAll(cause);
		return grid;
	}

	@Override
	public GridPane getGridPane() {
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 8";
	}

	@Override
	public String getStepDescription() {
		// TODO Auto-generated method stub
		return "Step 8";
	}
}
