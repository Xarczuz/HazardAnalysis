package hazard.HazardAnalysis.Steps.Views;

import java.util.Optional;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ViewStep7 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();

	public ViewStep7() {
		this.thisGp = addGridPane();
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
		tbHazard.setRowFactory(new Callback<TableView<Hazard>, TableRow<Hazard>>() {
			@Override
			public TableRow<Hazard> call(TableView<Hazard> param) {
				final TableRow<Hazard> row = new TableRow<Hazard>() {
					@Override
					protected void updateItem(Hazard item, boolean empty) {
						super.updateItem(item, empty);
						if (!hazardList.isEmpty() && !empty) {
							if (!item.getRisk().isEmpty()) {
								if (!empty && item.getRisk().contentEquals("true")) {
									setStyle("-fx-background-color: #006400;");
								} else if (!empty && item.getRisk().contentEquals("false")) {
									setStyle("-fx-background-color: #8B0000;");
								}
							}
						}
					}
				};
				return row;
			}
		});
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
		cause.setMinWidth(400);
		cause.setCellValueFactory(new PropertyValueFactory<Cause, String>("cause"));
		tbCause.getColumns().addAll(cause);
		tbCause.setItems(causeList);
		grid.add(tbCause, 0, 4);
		Button btnAdd = new Button("Add Severity And Probability");
		addSeverityAndProbabilityEvent(btnAdd, tbHazard);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		grid.add(gridBtn1, 0, 2);
		return grid;
	}

	private void addSeverityAndProbabilityEvent(Button btnAdd, TableView<Hazard> tbHazard) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbHazard.getSelectionModel().getSelectedIndex();
				if (index < 0) {
					return;
				}
				int id = tbHazard.getItems().get(index).getId();
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Severity And Probability");
				dialog.setHeaderText("Enter the Severity of the Hazard And the Probability of it happening.");
				ObservableList<String> options = FXCollections.observableArrayList("High-75%", "Medium-50%", "Low-25%");
				final ComboBox<String> comboBox = new ComboBox<String>(options);
				ObservableList<String> options2 = FXCollections.observableArrayList("High-75%", "Medium-50%",
						"Low-25%");
				final ComboBox<String> comboBox2 = new ComboBox<String>(options2);
				Text severity = new Text("Severity");
				Text probability = new Text("Probability");
				GridPane gp = new GridPane();
				gp.add(severity, 0, 0);
				gp.add(probability, 1, 0);
				gp.add(comboBox, 0, 1);
				gp.add(comboBox2, 1, 1);
				Text riskEvaluation = new Text("Risk Evaluation:");
				Text riskEvaluationNr = new Text();
				gp.add(riskEvaluation, 3, 1);
				gp.add(riskEvaluationNr, 4, 1);
				CheckBox ch = new CheckBox("Accept Risk");
				ch.setPadding(new Insets(15, 15, 15, 15));
				gp.add(ch, 5, 1);
				dialog.getDialogPane().setContent(gp);
				comboBox.valueProperty().addListener(new ChangeListener<String>() {
					@SuppressWarnings("rawtypes")
					@Override
					public void changed(ObservableValue ov, String t, String t1) {
						if (comboBox2.getValue() != null) {
							double d = returnRiskValue(comboBox2.getValue()) * returnRiskValue(t1);
							riskEvaluationNr.setText(String.valueOf(d));
						}
					}
				});
				comboBox2.valueProperty().addListener(new ChangeListener<String>() {
					@SuppressWarnings("rawtypes")
					@Override
					public void changed(ObservableValue ov, String t, String t1) {
						if (comboBox.getValue() != null) {
							double d = returnRiskValue(comboBox.getValue()) * returnRiskValue(t1);
							riskEvaluationNr.setText(String.valueOf(d));
						}
					}
				});
				Optional<String> op = dialog.showAndWait();
				if (op.isPresent() && !riskEvaluationNr.getText().contentEquals("")) {
					DataBaseConnection.sqlUpdate("UPDATE hazard SET severity=" + returnRiskValue(comboBox.getValue())
							+ ", probability=" + returnRiskValue(comboBox2.getValue()) + ", riskevaluation="
							+ (returnRiskValue(comboBox2.getValue()) * returnRiskValue(comboBox.getValue())) + ", risk="
							+ ch.isSelected() + " where hazard.id=" + id + ";");
				}
				updateHazardList();
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
		return "Step 7";
	}

	@Override
	public String getStepDescription() {
		return "For each Hazard and it's Pre-initiating events determine a Severity and a Probability for the Hazard.";
	}

	private Double returnRiskValue(String s) {
		if (s.toLowerCase().contains("high"))
			return .75D;
		if (s.toLowerCase().contains("medium"))
			return .50D;
		if (s.toLowerCase().contains("low"))
			return .25D;
		return 0D;
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}
}
