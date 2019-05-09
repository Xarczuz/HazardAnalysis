package hazard.HazardAnalysis.Steps.Views;

import java.util.Optional;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Cause;
import hazard.HazardClasses.Hazard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewStep6 implements ViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();
	private ObservableList<Cause> causeList = FXCollections.observableArrayList();
	private ViewStep1 vs1;

	public ViewStep6(ViewStep1 viewStep1, BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = mainView;
		this.vs1 = viewStep1;
	}

	private void addCauseEvent(Button btnAdd, TableView<Hazard> tbHazard, ObservableList<Cause> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Cause");
				dialog.setHeaderText("Enter a new cause");
				dialog.setContentText("Cause:");
				int index = tbHazard.getSelectionModel().getSelectedIndex();
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent() && index > -1) {
					Hazard h = tbHazard.getItems().get(index);
					DataBaseConnection.insertCause(result.get(), h.getId());
					DataBaseConnection.sql("SELECT * FROM cause WHERE cause.hazardid=" + h.getId() + ";", "cause",
							list);
				}
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

	private Button addEventToGoToPrevStep(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(getPrevGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
		grid.setStyle("-fx-border-width: 0 5 5 5; -fx-border-color: black black black black;");
		Text category1 = new Text("Hazards");
		category1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
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
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
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
		Text description = new Text("Step 6");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 3, 0);
		Text step6 = new Text(
				"Explore all the possible pre-initiating events that can bring about the specific\r\n"
						+ "hazardous situation by going through the hazard element, harmtruthmakers,\r\n"
						+ "and exposures, respectively.");
		step6.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step6.setWrappingWidth(300);
		grid.add(step6, 3, 1);
		Button btnAdd = new Button("Add Event");
		addCauseEvent(btnAdd, tbHazard, causeList);
		Button btnRemove = new Button("Remove Event");
		removeCauseEvent(btnRemove, tbCause, causeList);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		grid.add(gridBtn1, 0, 2);
		Button btnBack = new Button("Back");
		Button btnNextStep = new Button("Next Step");
		GridPane gridBtn = new GridPane();
		gridBtn.add(addEventToGoToPrevStep(btnBack), 0, 0);
		gridBtn.add(addNextStepEvent(btnNextStep), 2, 0);
		grid.add(gridBtn, 3, 2);
		return grid;
	}

	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				vs1.getAv7().updateHazardList();
				getMainView().setCenter(getNextGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	@Override
	public GridPane getGridPane() {
		return this.thisGp;
	}

	@Override
	public BorderPane getMainView() {
		return this.mainView;
	}

	@Override
	public GridPane getNextGridPane() {
		return this.nextGp;
	}

	@Override
	public GridPane getPrevGridPane() {
		return this.prevGp;
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

	@Override
	public void setNextGp(GridPane nextGp) {
		this.nextGp = nextGp;
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}
}
