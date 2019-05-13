package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ViewStep6 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();

	public ViewStep6() {
		this.thisGp = addGridPane();
	}

	private void addCauseEvent(Button btnAdd, TableView<Hazard> tbHazard, ObservableList<Cause> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbHazard.getSelectionModel().getSelectedIndex();
				if (index < 0)
					return;
				Hazard h = tbHazard.getItems().get(index);
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Cause");
				dialog.setHeaderText("Enter a new cause to the Hazard:\n"+h.getHazard()+"\n"+h.getHazardDescription());
				dialog.getDialogPane().setMaxWidth(600);	
				TextArea ta = new TextArea();
				ta.setPromptText("Descrption of the cause.");
				GridPane gp = new GridPane();
				gp.setPadding(new Insets(15, 15, 15, 15));
				ta.setPadding(new Insets(10, 5, 5, 5));
				gp.add(ta, 0, 2);
				dialog.getDialogPane().setContent(gp);
				EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent event) {
						if (dialog.getResult() != null && index > -1 && !ta.getText().isEmpty()) {
							DataBaseConnection.insertCause(ta.getText(), h.getId());
							DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + h.getId() + ";",
									"cause", list);
						}
					}
				};
				dialog.setOnCloseRequest(eventHandler);
				dialog.show();
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
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

	@SuppressWarnings("unchecked")
	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("gridpane");
		Text category1 = new Text("Hazards");
		category1.getStyleClass().add("heading");
		grid.add(category1, 0, 0);
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
		addClickEventToTbHazard(tbHazard, causeList);
		updateHazardList();
		tbHazard.setItems(hazardList);
		grid.add(tbHazard, 0, 1);
		Text category2 = new Text("Pre-initiating events that might lead to the hazard");
		category2.getStyleClass().add("heading");
		grid.add(category2, 0, 3);
		final TableView<Cause> tbCause = new TableView<Cause>();
		tbCause.setMinWidth(800);
		tbCause.setMaxHeight(200);
		TableColumn<Cause, String> cause = new TableColumn<Cause, String>("Pre-initiating event for hazard");
		cause.setMinWidth(800);
		cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		tbCause.getColumns().addAll(cause);
		tbCause.setItems(causeList);
		grid.add(tbCause, 0, 4);
		Button btnAdd = new Button("Add Event");
		addCauseEvent(btnAdd, tbHazard, causeList);
		Button btnRemove = new Button("Remove Event");
		removeCauseEvent(btnRemove, tbCause, causeList);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		grid.add(gridBtn1, 0, 2);
		return grid;
	}

	@Override
	public GridPane getGridPane() {
		updateHazardList();
		return this.thisGp;
	}

	private void removeCauseEvent(Button btnRemove, TableView<Cause> tbCause, ObservableList<Cause> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbCause.getSelectionModel().getSelectedIndex();
				if (index > -1) {
					int id = tbCause.getItems().get(index).getId();
					DataBaseConnection.delete("cause", id);
					DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + id + ";", "cause", list);
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}

	@Override
	public String getStep() {
		return "Step 6";
	}

	@Override
	public String getStepDescription() {
		return "Explore all the possible pre-initiating events that can bring about the specific\r\n"
				+ "hazardous situation by going through the hazard element, harmtruthmakers,\r\n"
				+ "and exposures, respectively.";
	}
}
