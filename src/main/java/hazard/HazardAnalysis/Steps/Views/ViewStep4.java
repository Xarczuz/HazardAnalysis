package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Role;
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

public class ViewStep4 implements ViewInterface {
	private GridPane thisGp;
	ObservableList<Role> roleList = FXCollections.observableArrayList();

	public ViewStep4() {
		this.thisGp = addGridPane();
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

	@Override
	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("gridpane");
		Text category = new Text("Roles");
		category.getStyleClass().add("heading");
		grid.add(category, 0, 0);
		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(400);
		tbRole.setMaxWidth(400);
		TableColumn<Role, Integer> id = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(300);
		tbRole.getColumns().addAll(id, role);
		DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		grid.add(tbRole, 0, 1);
		Text category2 = new Text("Kind");
		category2.getStyleClass().add("heading");
		final TableView<Kind> tbKind = new TableView<Kind>();
		tbKind.setMinWidth(400);
		tbKind.setMaxWidth(400);
		tbKind.setMaxHeight(200);
		TableColumn<Kind, Integer> id2 = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind = new TableColumn<Kind, String>("Kind");
		id2.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kind.setMinWidth(300);
		tbKind.getColumns().addAll(id2, kind);
		ObservableList<Kind> kindList = FXCollections.observableArrayList();
		tbKind.setItems(kindList);
		GridPane gridKinds = new GridPane();
		gridKinds.add(category2, 0, 0);
		gridKinds.add(tbKind, 0, 1);
		Text category3 = new Text("Kinds that plays the Role ");
		category3.getStyleClass().add("heading");
		final TableView<Kind> tbKindToRole = new TableView<Kind>();
		tbKindToRole.setMinWidth(400);
		tbKindToRole.setMaxWidth(400);
		tbKindToRole.setMaxHeight(200);
		TableColumn<Kind, Integer> id3 = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind2 = new TableColumn<Kind, String>("Kind");
		id3.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind2.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kind2.setMinWidth(300);
		tbKindToRole.getColumns().addAll(id3, kind2);
		ObservableList<Kind> kindToRoleList = FXCollections.observableArrayList();
		tbKindToRole.setItems(kindToRoleList);
		Button btnAddLink = new Button("+");
		addLinkEvent(btnAddLink, tbRole, tbKind, kindToRoleList);
		Button btnRemoveLink = new Button("-");
		addRemoveLinkEvent(btnRemoveLink, kindList, tbRole, tbKindToRole);
		GridPane gridTextAndBtn = new GridPane();
		GridPane gridBtn = new GridPane();
		gridKinds.setVgap(4);
		gridBtn.add(btnAddLink, 0, 0);
		gridBtn.add(btnRemoveLink, 1, 0);
		gridTextAndBtn.add(gridBtn, 0, 0);
		gridTextAndBtn.add(category3, 0, 1);
		gridKinds.add(gridTextAndBtn, 0, 2);
		gridKinds.add(tbKindToRole, 0, 3);
		grid.add(gridKinds, 1, 1);
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
		updateTbRole();
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 4";
	}

	@Override
	public String getStepDescription() {
		return "For each role object obtained in Step 1, Step 2 and\r\n"
				+ "Step 3, identify all the kind objects that can play the role, considering\r\n"
				+ "the system description.";
	}

	public void updateTbRole() {
		DataBaseConnection.selectAll("role", roleList);
	}
}
