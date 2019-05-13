package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ViewStep8 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();

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
		tbHazard.setMaxWidth(800);
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
		addClickEventToTbHazard(tbHazard, causeList);
		updateHazardList();
		tbHazard.setItems(hazardList);
		final TableView<Cause> tbCause = new TableView<Cause>();
		tbCause.setMinWidth(400);
		tbCause.setMaxWidth(400);
		tbCause.setMaxHeight(200);
		TableColumn<Cause, String> cause = new TableColumn<Cause, String>("Pre-initiating event for hazard");
		cause.setMinWidth(400);
		cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		tbCause.getColumns().addAll(cause);
		tbCause.setItems(causeList);
		final TableView<Cause> tbMitigation = new TableView<Cause>();
		tbMitigation.setMinWidth(400);
		tbMitigation.setMaxWidth(400);
		tbMitigation.setMaxHeight(200);
		TableColumn<Cause, String> mitigation = new TableColumn<Cause, String>("Pre-initiating event for hazard");
		mitigation.setMinWidth(400);
		mitigation.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		tbMitigation.getColumns().addAll(mitigation);
		tbMitigation.setItems(causeList);
		Text category1 = new Text("Hazards");
		category1.getStyleClass().add("heading");
		Text category2 = new Text("Causes");
		category2.getStyleClass().add("heading");
		Text category3 = new Text("Mitigation");
		category3.getStyleClass().add("heading");
		grid.add(category1, 0, 0);
		grid.add(tbHazard, 0, 1);
		GridPane g2 = new GridPane();
		g2.setHgap(10);
		g2.add(category2, 0, 1);
		g2.add(tbCause, 0, 2);
		g2.add(category3, 1, 1);
		g2.add(tbMitigation, 1, 2);
		grid.add(g2, 0, 2);
		Button btnAdd = new Button("Add Mitigation");
		//TODO
//		addCauseEvent(btnAdd, tbHazard, causeList);
		Button btnRemove = new Button("Remove Mitigation");
//		removeCauseEvent(btnRemove, tbCause, causeList);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		g2.add(gridBtn1, 1,3);
		return grid;
	}

	private void addClickEventToTbHazard(TableView<Hazard> tbHazard, ObservableList<Cause> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbHazard.getSelectionModel().selectedIndexProperty().get();
				if (index > -1) {
					int id = tbHazard.getItems().get(index).getId();
					DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + id + ";", "cause", list);
				}
			}
		};
		tbHazard.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
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
		//TODO
		return "Find ways to prevent the hazard from happening by looking at the causes that lead to the hazard.";
	}
}
