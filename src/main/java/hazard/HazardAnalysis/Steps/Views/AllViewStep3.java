package hazard.HazardAnalysis.Steps.Views;

import java.util.Optional;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.Relator;
import hazard.HazardClasses.Role;
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

public class AllViewStep3 implements AllViewInterface{
	GridPane prevGp, thisGp,nextGp;
	BorderPane mainView;

	ObservableList<Role> roleList = FXCollections.observableArrayList();
	
	public AllViewStep3(BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = mainView;
	}

	private void addClickEventToRoleTable(TableView<Role> tbRole, ObservableList<Relator> relatorList,
			ObservableList<Relator> relatorToRoleList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tbRole.getSelectionModel().selectedIndexProperty().get();
				int id = tbRole.getItems().get(index).getId();

				DataBaseConnection.sql(
						"SELECT * FROM relator WHERE NOT EXISTS(SELECT * FROM relatortorole WHERE relator.id=relatortorole.relatorid AND "
								+ id + "=relatortorole.roleid);",
						"relator", relatorList);
				DataBaseConnection.sql("SELECT * FROM relatortorole WHERE roleid=" + id + ";", "relatortorole",
						relatorToRoleList);
			}
		};
		tbRole.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	private Button addEventToGoToPrevStep(Button btnBack) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(getPrevGridPane());
			}
		};
		btnBack.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnBack;
	}

	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));

		Text category = new Text("Roles");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);

		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(350);
		TableColumn<Role, Integer> id = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(200);
		tbRole.getColumns().addAll(id, role);

		DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		grid.add(tbRole, 0, 1);

		Text category2 = new Text("Relators ");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		final TableView<Relator> tbRelator = new TableView<Relator>();
		tbRelator.setMinWidth(350);
		tbRelator.setMaxHeight(200);
		TableColumn<Relator, Integer> id2 = new TableColumn<Relator, Integer>("ID");
		TableColumn<Relator, String> relator = new TableColumn<Relator, String>("Relator");
		id2.setCellValueFactory(new PropertyValueFactory<Relator, Integer>("id"));
		relator.setCellValueFactory(new PropertyValueFactory<Relator, String>("relator"));
		relator.setMinWidth(200);
		tbRelator.getColumns().addAll(id2, relator);
		ObservableList<Relator> relatorList = FXCollections.observableArrayList();
		tbRelator.setItems(relatorList);

		Button btnAdd = new Button("Add");
		addRelatorEventToBtn(btnAdd, "relator", relatorList);
		Button btnRemove = new Button("Remove");
		removeRelatorEventToBtn(btnRemove, "relator", tbRelator);
		GridPane gridTextAndBtn1 = new GridPane();

		gridTextAndBtn1.add(category2, 0, 0);
		gridTextAndBtn1.add(btnAdd, 2, 0);
		gridTextAndBtn1.add(btnRemove, 3, 0);
		GridPane gridRelators = new GridPane();

		gridRelators.add(gridTextAndBtn1, 0, 0);
		gridRelators.add(tbRelator, 0, 1);

		Text category3 = new Text("Relator that the role have ");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final TableView<Relator> tbRelatorToRole = new TableView<Relator>();
		tbRelatorToRole.setMinWidth(350);
		tbRelatorToRole.setMaxHeight(200);
		TableColumn<Relator, Integer> id3 = new TableColumn<Relator, Integer>("ID");
		TableColumn<Relator, String> relator2 = new TableColumn<Relator, String>("Relator");
		id3.setCellValueFactory(new PropertyValueFactory<Relator, Integer>("id"));
		relator2.setCellValueFactory(new PropertyValueFactory<Relator, String>("relator"));
		relator2.setMinWidth(200);
		tbRelatorToRole.getColumns().addAll(id3, relator2);
		ObservableList<Relator> relatorToRoleList = FXCollections.observableArrayList();
		tbRelatorToRole.setItems(relatorToRoleList);

		Button btnAddLink = new Button("+");
		addLinkEvent(btnAddLink, tbRelator, tbRole, relatorToRoleList);
		Button btnRemoveLink = new Button("-");
		addRemoveLinkEvent(btnRemoveLink, relatorList, tbRole, tbRelatorToRole);
		GridPane gridTextAndBtn2 = new GridPane();

		gridTextAndBtn2.add(category3, 0, 0);
		gridTextAndBtn2.add(btnAddLink, 2, 0);
		gridTextAndBtn2.add(btnRemoveLink, 3, 0);

		gridRelators.add(gridTextAndBtn2, 0, 2);
		gridRelators.add(tbRelatorToRole, 0, 3);

		grid.add(gridRelators, 1, 1);

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text("• SDF-Step 3: For each role object obtained in SDF-Step 1 and SDFStep 2,\r\n"
				+ "identify the relator that connects this role, and specify all the other roles\r\n"
				+ "connected by the identified relator, considering the system description and\r\n"
				+ "the analysts’ expertise.");
		step1.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step1.setWrappingWidth(400);
		grid.add(step1, 2, 1);

		Button btnBack = new Button("Back");
		Button btnNextStep = new Button("Next Step");

		GridPane gridBtn = new GridPane();
		gridBtn.add(addEventToGoToPrevStep(btnBack), 0, 0);
		gridBtn.add(addNextStepEvent(btnNextStep), 2, 0);

		grid.add(gridBtn, 2, 2);
		addClickEventToRoleTable(tbRole, relatorList, relatorToRoleList);
		return grid;
	}

	private void addLinkEvent(Button btnAddLink, TableView<Relator> tbRelator, TableView<Role> tbRole,
			ObservableList<Relator> relatorToRoleList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int relatorIndex = tbRelator.getSelectionModel().selectedIndexProperty().get();
				int roleIndex = tbRole.getSelectionModel().selectedIndexProperty().get();
				if (roleIndex >= 0 && relatorIndex >= 0) {
					Relator rel = tbRelator.getItems().remove(relatorIndex);
					Role rol = tbRole.getItems().get(roleIndex);
					DataBaseConnection.insert("INSERT INTO relatortorole (relator,relatorid,role,roleid) VALUES('" + rel.getRelator()
							+ "'," + rel.getId() + ",'" + rol.getRole() + "'," + rol.getId() + ");");
					relatorToRoleList.add(rel);
				}

			}
		};
		btnAddLink.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(getNextGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	private void addRelatorEventToBtn(Button btnAddLink1, String s, ObservableList<Relator> list) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TextInputDialog dialog = new TextInputDialog("");

				dialog.setTitle("Add Relator");
				dialog.setHeaderText("Enter a new relator");
				dialog.setContentText("Relator:");

				Optional<String> result = dialog.showAndWait();

				if (result.isPresent()) {
					
					DataBaseConnection.insertRelator(s.toLowerCase(), result.get());
					list.clear();
					DataBaseConnection.selectAll(s.toLowerCase(), list);
	
				}

			}
		};
		btnAddLink1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

	}
	private void addRemoveLinkEvent(Button btnRemoveLink, ObservableList<Relator> relatorList, TableView<Role> tbRole,
			TableView<Relator> tbRelatorToRole) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int tbRelatorToRoleIndex = tbRelatorToRole.getSelectionModel().selectedIndexProperty().get();
				int roleIndex = tbRole.getSelectionModel().selectedIndexProperty().get();
				if (tbRelatorToRoleIndex >= 0 && roleIndex >= 0) {
					Relator rel = tbRelatorToRole.getItems().remove(tbRelatorToRoleIndex);
					Role role = tbRole.getItems().get(roleIndex);
					DataBaseConnection.deleteRelatorToRole("relatortorole", String.valueOf(role.getId()), String.valueOf(rel.getId()));
					relatorList.add(rel);
				}
			}
		};
		btnRemoveLink.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public GridPane getGridPane() {
		return this.thisGp;
	}

	public BorderPane getMainView() {
		return this.mainView;
	}

	public GridPane getNextGridPane() {
		return this.nextGp;
	}
	public GridPane getPrevGridPane() {
		return this.prevGp;
	}
	private void removeRelatorEventToBtn(Button btnRemoveLink1, String s, TableView<Relator> tb) {

		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tb.getItems().size() != 0) {
					int index = tb.getSelectionModel().selectedIndexProperty().get();
					if (index != -1) {
						Hazard o = tb.getItems().remove(index);
						DataBaseConnection.delete(s, o.getId());
					}
				}
			}
		};
		btnRemoveLink1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	public void setNextGp( GridPane nextGp) {
		 	this.nextGp = nextGp;
		
	}
	public void updateTbRole() {
		DataBaseConnection.selectAll("role", roleList);
	}
}
