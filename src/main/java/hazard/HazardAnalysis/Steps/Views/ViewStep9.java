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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ViewStep9 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();

	public ViewStep9() {
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
		tbCause.setMinWidth(800);
		tbCause.setMaxWidth(800);
		tbCause.setMaxHeight(200);
		TableColumn<Cause, String> cause = new TableColumn<Cause, String>("Causes");
		cause.setMinWidth(400);
		cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		TableColumn<Cause, String> mitigation = new TableColumn<Cause, String>("Mitigation");
		mitigation.setMinWidth(400);
		mitigation.setCellValueFactory(new PropertyValueFactory<Cause, String>("mitigation"));
		tbCause.getColumns().addAll(cause, mitigation);
		tbCause.setItems(causeList);
		tbCause.setRowFactory(new Callback<TableView<Cause>, TableRow<Cause>>() {
			@Override
			public TableRow<Cause> call(TableView<Cause> param) {
				final TableRow<Cause> row = new TableRow<Cause>() {
					private void removeCssClass() {
						if (getStyleClass().contains("true")) {
							getStyleClass().remove("true");
						}
						if (getStyleClass().contains("false")) {
							getStyleClass().remove("false");
						}
						if (getStyleClass().contains("table-row-cell")) {
							getStyleClass().remove("table-row-cell");
						}
					}

					@Override
					protected void updateItem(Cause item, boolean empty) {
						super.updateItem(item, empty);
						if (!hazardList.isEmpty() && !empty) {
							if (!item.getRisk().isEmpty()) {
								removeCssClass();
								if (!empty && item.getRisk().contentEquals("true")) {
									getStyleClass().add("true");
								} else if (!empty && item.getRisk().contentEquals("false")) {
									getStyleClass().add("false");
								}
							}
							if (!empty && item.getRisk().isEmpty()) {
								removeCssClass();
								getStyleClass().add("table-row-cell");
							}
						}
						if (empty) {
							removeCssClass();
							getStyleClass().add("table-row-cell");
						}
					}
				};
				return row;
			}
		});
		Text category1 = new Text("Hazards");
		category1.getStyleClass().add("heading");
		Text category2 = new Text("Causes and Mitigation");
		category2.getStyleClass().add("heading");
		grid.add(category1, 0, 0);
		grid.add(tbHazard, 0, 1);
		GridPane g2 = new GridPane();
		g2.setHgap(10);
		g2.add(category2, 0, 1);
		g2.add(tbCause, 0, 2);
		grid.add(g2, 0, 2);
		Button btnAdd = new Button("Add Mitigation");
		addMitigationEvent(btnAdd, tbCause, tbHazard);
		Button btnRemove = new Button("Remove Mitigation");
		removeMitigationEvent(btnRemove, tbCause, tbHazard);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		g2.add(gridBtn1, 0, 3);
		return grid;
	}

	private void addMitigationEvent(Button btnAdd, TableView<Cause> tbCause, TableView<Hazard> tbHazard) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbCause.getSelectionModel().getSelectedIndex();
				int indexH = tbHazard.getSelectionModel().getSelectedIndex();
				if (index < 0 || indexH < 0)
					return;
				Cause c = tbCause.getItems().get(index);
				Hazard h = tbHazard.getItems().get(indexH);
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Mitigation");
				dialog.setHeaderText("Enter a new mitigation to the Cause:\n" + c.getCause());
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
							DataBaseConnection.insertMitigationToCause(ta.getText(), c.getId());
							DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + h.getId() + ";",
									"cause", causeList);
						} else if (dialog.getResult() != null && ta.getText().isEmpty()) {
							event.consume();
						}
					}
				};
				dialog.setOnCloseRequest(eventHandler);
				dialog.show();
				ta.requestFocus();
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	@Override
	public GridPane getGridPane() {
		updateHazardList();
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 9";
	}

	@Override
	public String getStepDescription() {
		return "Find ways to prevent the hazard from happening by mitigating the causes that leads to the hazard.";
	}

	private void removeMitigationEvent(Button btnRemove, TableView<Cause> tbCause, TableView<Hazard> tbHazard) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbCause.getSelectionModel().getSelectedIndex();
				int indexH = tbHazard.getSelectionModel().getSelectedIndex();
				if (index > -1 && indexH > -1) {
					int id = tbCause.getItems().get(index).getId();
					Hazard h = tbHazard.getItems().get(indexH);
					DataBaseConnection.insertMitigationToCause("", id);
					DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + h.getId() + ";", "cause",
							causeList);
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}
}
