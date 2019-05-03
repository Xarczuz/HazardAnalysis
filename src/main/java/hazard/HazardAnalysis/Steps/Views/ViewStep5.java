package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.PossibleVictim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewStep5 implements ViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	private ViewStep1 av1;
	ObservableList<PossibleVictim> possibleVictimList = FXCollections.observableArrayList();
	ObservableList<Hazard> hazardList = FXCollections.observableArrayList();

	public void updatePossibleVictimList() {
		DataBaseConnection.sql("SELECT DISTINCT\r\n" + "kind.kind,e1.role,e1.relator,e2.role,k2.kind\r\n" + "FROM\r\n"
				+ " relatortorole e1,kind,roletoplay,kind k2,roletoplay r2\r\n"
				+ "INNER JOIN relatortorole e2 ON e1.relatorid = e2.relatorid \r\n"
				+ "   AND (e1.roleid <> e2.roleid AND e1.role <> e2.role) where roletoplay.kindid = kind.id and roletoplay.roleid = e1.roleid and  r2.kindid = k2.id and r2.roleid = e2.roleid;",
				"possiblevictim", possibleVictimList);
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT id,hazard,harm FROM hazard;", "hazard", hazardList);

	}

	public ViewStep5(ViewStep1 viewStep1, BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = mainView;
		this.av1 = viewStep1;
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

		Text category = new Text("Possible Mishap Vicitms");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);
		final TableView<PossibleVictim> tbPossibleVictim = new TableView<PossibleVictim>();
		tbPossibleVictim.setMinWidth(800);
		tbPossibleVictim.setMaxHeight(200);
		TableColumn<PossibleVictim, String> kind = new TableColumn<PossibleVictim, String>("Environment Object");
		TableColumn<PossibleVictim, String> role = new TableColumn<PossibleVictim, String>("Hazard Element");
		TableColumn<PossibleVictim, String> relator = new TableColumn<PossibleVictim, String>("Exposure");
		TableColumn<PossibleVictim, String> role2 = new TableColumn<PossibleVictim, String>("Possible Vicitm");
		TableColumn<PossibleVictim, String> kind2 = new TableColumn<PossibleVictim, String>("Environment Object");
		kind.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("kind"));
		role.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("role"));
		relator.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("relator"));
		role2.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("role2"));
		kind2.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("kind2"));
		tbPossibleVictim.getColumns().addAll(kind, role, relator, kind2, role2);

		updatePossibleVictimList();
		tbPossibleVictim.setItems(possibleVictimList);
		grid.add(tbPossibleVictim, 0, 1);

		Text category2 = new Text("Mishap Victims");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category2, 0, 3);
		final TableView<Hazard> tbVictim = new TableView<Hazard>();
		tbVictim.setMinWidth(800);
		tbVictim.setMaxHeight(200);
		TableColumn<Hazard, Integer> id = new TableColumn<Hazard, Integer>("ID");
		TableColumn<Hazard, String> hazard = new TableColumn<Hazard, String>("Hazard");
		TableColumn<Hazard, String> hazardDescription = new TableColumn<Hazard, String>("Hazard Description");

		hazard.setMinWidth(400);
		hazardDescription.setMinWidth(350);
		id.setCellValueFactory(new PropertyValueFactory<Hazard, Integer>("id"));
		hazard.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazard"));
		hazardDescription.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazardDescription"));

		tbVictim.getColumns().addAll(id, hazard, hazardDescription);
		updateHazardList();
		tbVictim.setItems(hazardList);
		grid.add(tbVictim, 0, 4);

		Button btnAdd = new Button("+");
		addVictimEvent(btnAdd, tbPossibleVictim, hazardList);
		Button btnRemove = new Button("-");
		removeVictimEvent(btnRemove, tbVictim);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		grid.add(gridBtn1, 0, 2);

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 3, 0);
		Text step5 = new Text(
				"• Step 5: Since the occurrence of a mishap event must have more than one mishap victim to participate in the event, this step identifies all the possible mishap victims. Furthermore, the analysts\n"
						+ "continue with brainstorming possible harms that can threaten the victims, including but not limited to, physical damages, chemical injuries, fatal illness,\r\n"
						+ "explosion, etc.");
		step5.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step5.setWrappingWidth(300);
		grid.add(step5, 3, 1);

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
				av1.getAv6().updateHazardList();
				getMainView().setCenter(getNextGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	private void addVictimEvent(Button btnAdd, TableView<PossibleVictim> tbPossibleVictim,
			ObservableList<Hazard> hazardList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tbPossibleVictim.getSelectionModel().getSelectedIndex() < 0) {
					return;
				}
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Mishap Victim");
				dialog.setHeaderText(
						"Enter a new title for the possible harm that can happen \nto the mishap victim and a more detailed description of it.");

				TextField t = new TextField();
				TextArea ta = new TextArea();
				t.setPromptText("Title of the possible harm.");
				ta.setPromptText("Descrption of the possible harm.");
				GridPane gp = new GridPane();
				gp.setPadding(new Insets(15, 15, 15, 15));
				t.setPadding(new Insets(10, 5, 5, 5));

				ta.setPadding(new Insets(10, 5, 5, 5));
				gp.add(t, 0, 0);
				gp.add(ta, 0, 1);

				dialog.getDialogPane().setContent(gp);

				EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent event) {
						int index = tbPossibleVictim.getSelectionModel().getSelectedIndex();
						if (dialog.getResult() != null && index > -1 && !t.getText().isEmpty()
								&& !ta.getText().isEmpty()) {

							PossibleVictim pv = tbPossibleVictim.getItems().get(index);

							String hazard = pv.getRelator() + "(" + pv.getRole() + ":" + pv.getKind() + ")" + "("
									+ pv.getRole2() + "<" + t.getText() + ">:" + pv.getKind2() + ")";
							String harm = ta.getText();
							DataBaseConnection
									.insert("INSERT INTO hazard (hazard,harm) VALUES('" + hazard + "','" + harm + "')");

							DataBaseConnection.sql("SELECT id,hazard,harm FROM hazard;", "hazard", hazardList);

						}
					}
				};

				dialog.setOnCloseRequest(eventHandler);
				dialog.show();
				t.requestFocus();

			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

	}

	private void removeVictimEvent(Button btnRemove, TableView<Hazard> tbVictim) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tbVictim.getItems().size() != 0) {
					int index = tbVictim.getSelectionModel().selectedIndexProperty().get();
					if (index != -1) {
						Play o = (Play) tbVictim.getItems().remove(index);
						DataBaseConnection.delete("hazard", o.getId());
					}
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

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

	public void setNextGp(GridPane nextGp) {
		this.nextGp = nextGp;
	}
}
