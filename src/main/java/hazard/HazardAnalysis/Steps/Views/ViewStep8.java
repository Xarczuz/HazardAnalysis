package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.Mitigation;
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

public class ViewStep8 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();
	private ObservableList<Mitigation> mitigationList = FXCollections.observableArrayList();

	public ViewStep8() {
		this.thisGp = addGridPane();
	}

	private void addClickEventToTbHazard(TableView<Hazard> tbHazard) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbHazard.getSelectionModel().selectedIndexProperty().get();
				if (index > -1) {
					int id = tbHazard.getItems().get(index).getId();
					DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + id + ";", "cause", causeList);
					DataBaseConnection.sql("SELECT * FROM mitigation WHERE mitigation.hazardid=" + id + ";",
							"mitigation", mitigationList);
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
		addClickEventToTbHazard(tbHazard);
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
		final TableView<Mitigation> tbMitigation = new TableView<Mitigation>();
		tbMitigation.setMinWidth(400);
		tbMitigation.setMaxWidth(400);
		tbMitigation.setMaxHeight(200);
		TableColumn<Mitigation, String> mitigation = new TableColumn<Mitigation, String>("Mitigation");
		mitigation.setMinWidth(400);
		mitigation.setCellValueFactory(new PropertyValueFactory<Mitigation, String>("mitigation"));
		tbMitigation.getColumns().addAll(mitigation);
		tbMitigation.setItems(mitigationList);
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
		addMitigationEvent(btnAdd, tbHazard, mitigationList);
		Button btnRemove = new Button("Remove Mitigation");
		removeMitigationEvent(btnRemove, tbMitigation, mitigationList);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		g2.add(gridBtn1, 1, 3);
		return grid;
	}

	private void addMitigationEvent(Button btnAdd, TableView<Hazard> tbHazard, ObservableList<Mitigation> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbHazard.getSelectionModel().getSelectedIndex();
				if (index < 0)
					return;
				Hazard h = tbHazard.getItems().get(index);
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Mitigation");
				dialog.setHeaderText(
						"Enter a new mitigation to the Hazard:\n" + h.getHazard() + "\n" + h.getHazardDescription());
				dialog.getDialogPane().setMaxWidth(600);
				TextArea ta = new TextArea();
				ta.setPromptText("Descrption of the mitigation.");
				GridPane gp = new GridPane();
				gp.setPadding(new Insets(15, 15, 15, 15));
				ta.setPadding(new Insets(10, 5, 5, 5));
				gp.add(ta, 0, 2);
				dialog.getDialogPane().setContent(gp);
				EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent event) {
						if (dialog.getResult() != null && index > -1 && !ta.getText().isEmpty()) {
							DataBaseConnection.insertMitigation(ta.getText(), h.getId());
							DataBaseConnection.sql(
									"SELECT * FROM mitigation WHERE mitigation.hazardid=" + h.getId() + ";",
									"mitigation", list);
						}
					}
				};
				dialog.setOnCloseRequest(eventHandler);
				dialog.show();
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
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
		return "Find ways to prevent the hazard from happening by looking at the causes that leads to the hazard.";
	}

	private void removeMitigationEvent(Button btnRemove, TableView<Mitigation> tbMitigation,
			ObservableList<Mitigation> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbMitigation.getSelectionModel().getSelectedIndex();
				if (index > -1) {
					int id = tbMitigation.getItems().get(index).getId();
					DataBaseConnection.delete("mitigation", id);
					DataBaseConnection.sql("SELECT * FROM mitigation WHERE mitigation.hazardid=" + id + ";",
							"mitigation", list);
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}
}
