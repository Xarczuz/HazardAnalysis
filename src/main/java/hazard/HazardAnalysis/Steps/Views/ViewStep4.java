package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ViewStep4 implements ViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	ObservableList<Role> roleList = FXCollections.observableArrayList();

	private ViewStep1 av1;

	public ViewStep4(ViewStep1 viewStep1, BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.av1 = viewStep1;
		this.prevGp = prevGp;
		this.mainView = mainView;
	}

	private void addClickEventToRoleTable(TableView<Role> tb, ObservableList<Kind> kindList,
			ObservableList<Kind> kindToRoleList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tb.getSelectionModel().selectedIndexProperty().get();
				if (index > -1) {
					int id = tb.getItems().get(index).getId();
					DataBaseConnection.sql(
							"SELECT * FROM kind WHERE NOT EXISTS(SELECT * FROM roletoplay WHERE kind.id=roletoplay.kindid AND "
									+ id + "=roletoplay.roleid);",
							"kind", kindList);
					DataBaseConnection.sql("SELECT * FROM roletoplay WHERE roleid=" + id + ";", "kindtorole",
							kindToRoleList);
				}
			}
		};
		tb.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
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

	@Override
	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));

		Text category = new Text("Roles");
		category.setId("tt");
		category.setStyle("#tt{color:red;}");
		category.applyCss();
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

		Text category2 = new Text("Kind");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final TableView<Kind> tbKind = new TableView<Kind>();
		tbKind.setMinWidth(350);
		tbKind.setMaxHeight(200);
		TableColumn<Kind, Integer> id2 = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind = new TableColumn<Kind, String>("Kind");
		id2.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kind.setMinWidth(200);
		tbKind.getColumns().addAll(id2, kind);
		ObservableList<Kind> kindList = FXCollections.observableArrayList();

		tbKind.setItems(kindList);
		GridPane gridKinds = new GridPane();
		gridKinds.add(category2, 0, 0);
		gridKinds.add(tbKind, 0, 1);

		Text category3 = new Text("Kind that can play the role");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final TableView<Kind> tbKindToRole = new TableView<Kind>();
		tbKindToRole.setMinWidth(350);
		tbKindToRole.setMaxHeight(200);
		TableColumn<Kind, Integer> id3 = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind2 = new TableColumn<Kind, String>("Kind");
		id3.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind2.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kind2.setMinWidth(200);
		tbKindToRole.getColumns().addAll(id3, kind2);
		ObservableList<Kind> kindToRoleList = FXCollections.observableArrayList();
		tbKindToRole.setItems(kindToRoleList);

		Button btnAddLink = new Button("+");
		addLinkEvent(btnAddLink, tbRole, tbKind, kindToRoleList);
		Button btnRemoveLink = new Button("-");
		addRemoveLinkEvent(btnRemoveLink, kindList, tbRole, tbKindToRole);
		GridPane gridTextAndBtn = new GridPane();

		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);

		gridKinds.add(gridTextAndBtn, 0, 2);
		gridKinds.add(tbKindToRole, 0, 3);

		grid.add(gridKinds, 1, 1);
		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step4 = new Text("• SDF-Step 4: For each role object obtained in SDF-Step 1, SDF-Step 2 and\r\n"
				+ "SDF-Step 3, identify all the kind objects that can play the role, considering\r\n"
				+ "the system description.");
		step4.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step4.setWrappingWidth(400);
		grid.add(step4, 2, 1);

		Button btnBack = new Button("Back");
		Button btnNextStep = new Button("Next Step");

		GridPane gridBtn = new GridPane();
		gridBtn.add(addEventToGoToPrevStep(btnBack), 0, 0);
		gridBtn.add(addNextStepEvent(btnNextStep), 2, 0);

		grid.add(gridBtn, 2, 2);

		addClickEventToRoleTable(tbRole, kindList, kindToRoleList);
		return grid;
	}

	private void addLinkEvent(Button btnAddLink, TableView<Role> tbRole, TableView<Kind> tbKind,
			ObservableList<Kind> kindToRoleList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int roleIndex = tbRole.getSelectionModel().selectedIndexProperty().get();
				int kindIndex = tbKind.getSelectionModel().selectedIndexProperty().get();
				if (roleIndex >= 0 && kindIndex >= 0) {
					Role r = tbRole.getItems().get(roleIndex);
					Kind k = tbKind.getItems().remove(kindIndex);
					DataBaseConnection.insert("INSERT INTO roletoplay (role,roleid,kind,kindid) VALUES('" + r.getRole()
							+ "'," + r.getId() + ",'" + k.getKind() + "'," + k.getId() + ")");
					kindToRoleList.add(k);
				}
			}
		};
		btnAddLink.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				av1.getAv5().updatePossibleVictimList();
				av1.getAv5().updateHazardList();
				getMainView().setCenter(getNextGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	private void addRemoveLinkEvent(Button btnRemoveLink, ObservableList<Kind> kindList, TableView<Role> tbRole,
			TableView<Kind> tbKindToRole) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int tbRoleToPlayIndex = tbKindToRole.getSelectionModel().selectedIndexProperty().get();
				int tbRoleIndex = tbRole.getSelectionModel().selectedIndexProperty().get();
				if (tbRoleToPlayIndex >= 0 && tbRoleIndex >= 0) {
					Kind k = tbKindToRole.getItems().remove(tbRoleToPlayIndex);
					Role r = tbRole.getItems().get(tbRoleIndex);
					DataBaseConnection.deleteARoleToPlay("roletoplay", String.valueOf(r.getId()),
							String.valueOf(k.getId()));
					kindList.add(k);
				}
			}
		};
		btnRemoveLink.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
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

	@Override
	public void setNextGp(GridPane nextGp) {
		this.nextGp = nextGp;
	}

	public void updateTbRole() {
		DataBaseConnection.selectAll("role", roleList);
	}
}
