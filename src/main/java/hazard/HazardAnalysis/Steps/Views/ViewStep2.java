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

public class ViewStep2 implements ViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	ObservableList<Kind> kindList = FXCollections.observableArrayList();

	public ViewStep2(ViewStep1 viewStep1, BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = mainView;
	}

	private void addClickEventToKindTable(TableView<Kind> tb, ObservableList<Role> roleList,
			ObservableList<Role> roleToPlayList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tb.getSelectionModel().selectedIndexProperty().get();
				if (index > -1) {
					int id = tb.getItems().get(index).getId();
					DataBaseConnection.sql(
							"SELECT * FROM role WHERE NOT EXISTS(SELECT * FROM roletoplay WHERE role.id=roletoplay.roleid AND "
									+ id + "=roletoplay.kindid);",
							"role", roleList);
					DataBaseConnection.sql("SELECT * FROM roletoplay WHERE kindid=" + id + ";", "roletoplay",
							roleToPlayList);
				}
			}
		};
		tb.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	private Button addEventToGoToPrevStep(Button btnPrevStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(getPrevGridPane());
			}
		};
		btnPrevStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnPrevStep;
	}

	@Override
	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
		grid.setStyle("-fx-border-width: 0 5 5 5; -fx-border-color: black black black black;");
		Text category = new Text("Kind");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);
		final TableView<Kind> tbKind = new TableView<Kind>();
		tbKind.setMaxWidth(350);
		TableColumn<Kind, Integer> id = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind = new TableColumn<Kind, String>("Kind");
		TableColumn<Kind, Boolean> start = new TableColumn<Kind, Boolean>("Start");
		TableColumn<Kind, Boolean> runtime = new TableColumn<Kind, Boolean>("RunTime");
		TableColumn<Kind, Boolean> shutdown = new TableColumn<Kind, Boolean>("ShutDown");
		id.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		start.setCellValueFactory(new PropertyValueFactory<Kind, Boolean>("start"));
		runtime.setCellValueFactory(new PropertyValueFactory<Kind, Boolean>("runtime"));
		shutdown.setCellValueFactory(new PropertyValueFactory<Kind, Boolean>("shutdown"));
		kind.setMinWidth(100);
		tbKind.getColumns().addAll(id, kind, start, runtime, shutdown);
		DataBaseConnection.selectAll("kind", kindList);
		tbKind.setItems(kindList);
		grid.add(tbKind, 0, 1);
		Text category2 = new Text("Roles");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(350);
		tbRole.setMaxHeight(200);
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(200);
		tbRole.getColumns().addAll(id2, role);
		ObservableList<Role> roleList = FXCollections.observableArrayList();
		tbRole.setItems(roleList);
		GridPane gridRoles = new GridPane();
		gridRoles.add(category2, 0, 0);
		gridRoles.add(tbRole, 0, 1);
		Text category3 = new Text("Roles it can play    ");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final TableView<Role> tbRoleToPlay = new TableView<Role>();
		tbRoleToPlay.setMinWidth(350);
		tbRoleToPlay.setMaxHeight(200);
		TableColumn<Role, Integer> id3 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role2 = new TableColumn<Role, String>("Role");
		id3.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role2.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role2.setMinWidth(200);
		tbRoleToPlay.getColumns().addAll(id3, role2);
		ObservableList<Role> roleToPlayList = FXCollections.observableArrayList();
		tbRoleToPlay.setItems(roleToPlayList);
		Button btnAddLink = new Button("+");
		addLinkEvent(btnAddLink, tbRole, tbKind, roleToPlayList);
		Button btnRemoveLink = new Button("-");
		addRemoveLinkEvent(btnRemoveLink, roleList, tbKind, tbRoleToPlay);
		GridPane gridTextAndBtn = new GridPane();
		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);
		gridRoles.add(gridTextAndBtn, 0, 2);
		gridRoles.add(tbRoleToPlay, 0, 3);
		grid.add(gridRoles, 1, 1);
		Text description = new Text("Step 2");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text("For each kind object obtained in Step 1, identify all the\r\n"
				+ "roles it can play, considering the system description.");
		step1.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step1.setWrappingWidth(400);
		grid.add(step1, 2, 1);
		Button btnBack = new Button("Back");
		Button btnNextStep = new Button("Next Step");
		GridPane gridBtn = new GridPane();
		gridBtn.add(addEventToGoToPrevStep(btnBack), 0, 0);
		gridBtn.add(addNextStepEvent(btnNextStep), 2, 0);
		grid.add(gridBtn, 2, 2);
		addClickEventToKindTable(tbKind, roleList, roleToPlayList);
		return grid;
	}

	private void addLinkEvent(Button btnAddLink, TableView<Role> tbRole, TableView<Kind> tbKind,
			ObservableList<Role> roleToPlayList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int roleIndex = tbRole.getSelectionModel().selectedIndexProperty().get();
				int kindIndex = tbKind.getSelectionModel().selectedIndexProperty().get();
				if (roleIndex >= 0 && kindIndex >= 0) {
					Role r = tbRole.getItems().remove(roleIndex);
					Kind k = tbKind.getItems().get(kindIndex);
					DataBaseConnection.insert("INSERT INTO roletoplay (role,roleid,kind,kindid) VALUES('" + r.getRole()
							+ "'," + r.getId() + ",'" + k.getKind() + "'," + k.getId() + ")");
					roleToPlayList.add(r);
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

	private void addRemoveLinkEvent(Button btnRemoveLink, ObservableList<Role> roleList, TableView<Kind> tbKind,
			TableView<Role> tbRoleToPlay) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int tbRoleToPlayIndex = tbRoleToPlay.getSelectionModel().selectedIndexProperty().get();
				int kindIndex = tbKind.getSelectionModel().selectedIndexProperty().get();
				if (tbRoleToPlayIndex >= 0 && kindIndex >= 0) {
					Role r = tbRoleToPlay.getItems().remove(tbRoleToPlayIndex);
					Kind k = tbKind.getItems().get(kindIndex);
					DataBaseConnection.deleteARoleToPlay("roletoplay", String.valueOf(r.getId()),
							String.valueOf(k.getId()));
					roleList.add(r);
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

	public void updateTbKind() {
		DataBaseConnection.selectAll("kind", kindList);
	}
}
