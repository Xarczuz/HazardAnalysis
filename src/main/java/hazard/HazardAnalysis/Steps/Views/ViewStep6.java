package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.HazardElement;
import hazard.HazardClasses.MishapVictim;
import hazard.HazardClasses.Play;
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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ViewStep6 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<MishapVictim> victimList = FXCollections.observableArrayList();
	private ObservableList<HazardElement> hazardElementList = FXCollections.observableArrayList();
	private ObservableList<Hazard> hazardList = FXCollections.observableArrayList();

	public ViewStep6() {
		this.thisGp = addGridPane();
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		GridPane grid2 = new GridPane();
		grid.getStyleClass().add("gridpane");
		Text category = new Text("Mishap Victims");
		category.getStyleClass().add("heading");
		grid2.add(category, 0, 0);
		final TableView<MishapVictim> tbVictim = new TableView<MishapVictim>();
		tbVictim.setMinWidth(400);
		tbVictim.setMaxWidth(400);
		tbVictim.setMaxHeight(200);
		TableColumn<MishapVictim, Integer> id = new TableColumn<MishapVictim, Integer>("ID");
		TableColumn<MishapVictim, String> kind = new TableColumn<MishapVictim, String>("Environment Object");
		TableColumn<MishapVictim, String> role = new TableColumn<MishapVictim, String>("Mishap Victim");
		TableColumn<MishapVictim, String> relator = new TableColumn<MishapVictim, String>("Exposure");
		id.setCellValueFactory(new PropertyValueFactory<MishapVictim, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("kind"));
		role.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("role"));
		relator.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("relator"));
		kind.setMinWidth(120);
		role.setMinWidth(120);
		relator.setMinWidth(120);
		tbVictim.getColumns().addAll(id, role, kind, relator);
		tbVictim.setItems(victimList);
		clickEventToTbVictim(tbVictim);
		grid2.add(tbVictim, 0, 1);
		Text category2 = new Text("Hazard Elements");
		category2.getStyleClass().add("heading");
		final TableView<HazardElement> tbHazardElement = new TableView<HazardElement>();
		tbHazardElement.setMinWidth(400);
		tbHazardElement.setMaxWidth(400);
		tbHazardElement.setMaxHeight(200);
		TableColumn<HazardElement, String> role2 = new TableColumn<HazardElement, String>("Hazard Element");
		TableColumn<HazardElement, String> kind2 = new TableColumn<HazardElement, String>("Environment Object");
		role2.setCellValueFactory(new PropertyValueFactory<HazardElement, String>("role"));
		kind2.setCellValueFactory(new PropertyValueFactory<HazardElement, String>("kind"));
		role2.setMinWidth(200);
		kind2.setMinWidth(200);
		tbHazardElement.getColumns().addAll(role2, kind2);
		tbHazardElement.setItems(hazardElementList);
		grid2.add(category2, 1, 0);
		grid2.add(tbHazardElement, 1, 1);
		Text category3 = new Text("Hazards");
		category3.getStyleClass().add("heading");
		final TableView<Hazard> tbHazard = new TableView<Hazard>();
		tbHazard.setMinWidth(800);
		tbHazard.setMaxHeight(200);
		TableColumn<Hazard, Integer> id2 = new TableColumn<Hazard, Integer>("ID");
		TableColumn<Hazard, String> hazard = new TableColumn<Hazard, String>("Hazard");
		TableColumn<Hazard, String> hazardDescription = new TableColumn<Hazard, String>("Hazard Description");
		hazard.setMinWidth(400);
		hazardDescription.setMinWidth(350);
		id2.setCellValueFactory(new PropertyValueFactory<Hazard, Integer>("id"));
		hazard.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazard"));
		hazardDescription.setCellValueFactory(new PropertyValueFactory<Hazard, String>("hazardDescription"));
		tbHazard.getColumns().addAll(id2, hazard, hazardDescription);
		tbHazard.setItems(hazardList);
		Button btnAddLink = new Button("+");
		addHazardEvent(btnAddLink, tbVictim, tbHazardElement);
		Button btnRemoveLink = new Button("-");
		removeHazardEvent(btnRemoveLink, tbHazard);
		GridPane gridTextAndBtn = new GridPane();
		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);
		grid2.setHgap(10);
		grid.add(grid2, 0, 0);
		grid.add(gridTextAndBtn, 0, 1);
		grid.add(tbHazard, 0, 2);
		return grid;
	}

	private void clickEventToTbVictim(TableView<MishapVictim> tbVictim) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int index = tbVictim.getSelectionModel().getSelectedIndex();
				if (index > -1) {
					MishapVictim mv = tbVictim.getItems().get(index);
					DataBaseConnection.sql(
							"SELECT roletoplay.role,roletoplay.kind FROM relatortorole, roletoplay WHERE relatortorole.relatorid ="
									+ mv.getRelatorID() + " AND relatortorole.roleid = roletoplay.roleid;",
							"hazardelement", hazardElementList);
				}
			}
		};
		tbVictim.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	private void addHazardEvent(Button btnAdd, TableView<MishapVictim> tbVictim,
			TableView<HazardElement> tbHazardElement) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbVictim.getSelectionModel().getSelectedIndex();
				int index2 = tbHazardElement.getSelectionModel().getSelectedIndex();
				if (index < 0 || index2 < 0)
					return;
				MishapVictim mv = tbVictim.getItems().get(index);
				HazardElement he = tbHazardElement.getItems().get(index2);
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add Hazard");
				dialog.setHeaderText("");
				dialog.getDialogPane().setMaxWidth(600);
				Text description = new Text();
				TextField t = new TextField();
				TextArea ta = new TextArea();
				description.setText(mv.getRelator() + "(" + mv.getRole() + ":" + mv.getKind() + ")" + "(" + he.getRole()
						+ "<HarmTruthMaker>" + ":" + he.getKind() + ")");
				t.setPromptText("HarmTruthMaker");
				ta.setPromptText("Descrption of the Harm.");
				GridPane gp = new GridPane();
				gp.setPadding(new Insets(15, 15, 15, 15));
				t.setPadding(new Insets(10, 5, 5, 5));
				ta.setPadding(new Insets(10, 5, 5, 5));
				t.textProperty().addListener((observable, oldValue, newValue) -> {
					description.setText(mv.getRelator() + "(" + mv.getRole() + ":" + mv.getKind() + ")" + "("
							+ he.getRole() + "<" + newValue + ">" + ":" + he.getKind() + ")");
				});
				gp.add(description, 0, 0);
				gp.add(t, 0, 1);
				gp.add(ta, 0, 2);
				gp.setVgap(10);
				dialog.getDialogPane().setContent(gp);
				EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent event) {
						if (dialog.getResult() != null && index > -1 && !t.getText().isEmpty()
								&& !ta.getText().isEmpty()) {
							String hazard = mv.getRelator() + "(" + mv.getRole() + ":" + mv.getKind() + ")" + "("
									+ he.getRole() + "<" + t.getText() + ">" + ":" + he.getKind() + ")";
							String harm = ta.getText();
							DataBaseConnection.insertHazard(hazard, harm);
							DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
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

	private void removeHazardEvent(Button btnRemove, TableView<Hazard> tbHazard) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tbHazard.getItems().size() != 0) {
					int index = tbHazard.getSelectionModel().selectedIndexProperty().get();
					if (index != -1) {
						Play o = tbHazard.getItems().remove(index);
						DataBaseConnection.delete("hazard", o.getId());
					}
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	@Override
	public GridPane getGridPane() {
		updateVictimList();
		updateHazardList();
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 6";
	}

	@Override
	public String getStepDescription() {
		return "Continue with brainstorming possible harms that can threaten the victims, including but not limited to, physical damages, chemical injuries, fatal illness, "
				+ "explosion, etc.";
	}

	public void updateHazardList() {
		DataBaseConnection.sql("SELECT * FROM hazard;", "hazard", hazardList);
	}

	public void updateVictimList() {
		DataBaseConnection.sql("Select * from mishapvictim", "mishapvictim", victimList);
	}
}
