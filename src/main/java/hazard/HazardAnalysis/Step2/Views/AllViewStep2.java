package hazard.HazardAnalysis.Step2.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Step3.Views.AllViewStep3;
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

public class AllViewStep2 {
	GridPane thisGp, prevGp;
	BorderPane border;
	AllViewStep3 av3;

	public AllViewStep2(BorderPane border, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.border = border;
		this.av3 = new AllViewStep3(border, getGridPane());
	}

	public GridPane getGridPane() {
		return this.thisGp;
	}

	public GridPane getPrevGridPane() {
		return this.prevGp;
	}

	public BorderPane getMainView() {
		return this.border;
	}

	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));

		Text category = new Text("Kind");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);

		final TableView<Kind> tbKind = new TableView<Kind>();
		tbKind.setMinWidth(300);
		TableColumn<Kind, Integer> id = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind = new TableColumn<Kind, String>("Kind");
		id.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kind.setMinWidth(200);
		tbKind.getColumns().addAll(id, kind);
		ObservableList<Kind> kindList = FXCollections.observableArrayList();
		DataBaseConnection.selectAll("kind", kindList);
	
		tbKind.setItems(kindList);
		grid.add(tbKind, 0, 1);

		Text category2 = new Text("Roles");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(300);
		tbRole.setMaxHeight(200);
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(200);
		tbRole.getColumns().addAll(id2, role);
		ObservableList<Role> roleList = FXCollections.observableArrayList();
		//DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		//addClickEvent(tbRole);
		
		GridPane gridRoles = new GridPane();
		gridRoles.add(category2, 0, 0);
		gridRoles.add(tbRole, 0, 1);

		Text category3 = new Text("Roles it can play    ");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		final TableView<Role> tbRoleToPlay = new TableView<Role>();
		tbRoleToPlay.setMinWidth(300);
		tbRoleToPlay.setMaxHeight(200);
		TableColumn<Role, Integer> id3 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role2 = new TableColumn<Role, String>("Role");
		id3.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role2.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role2.setMinWidth(200);
		tbRoleToPlay.getColumns().addAll(id3, role2);
		ObservableList<Role> roleToPlayList = FXCollections.observableArrayList();
		//DataBaseConnection.selectAll("role", roleToPlayList);
		tbRoleToPlay.setItems(roleToPlayList);
		//addClickEvent(tbRoleToPlay);
		
		Button btnAddLink = new Button("+");
		Button btnRemoveLink = new Button("-");
		GridPane gridTextAndBtn = new GridPane();

		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);

		gridRoles.add(gridTextAndBtn, 0, 2);
		gridRoles.add(tbRoleToPlay, 0, 3);

		grid.add(gridRoles, 1, 1);

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text("• SDF-Step 2: For each kind object obtained in SDF-Step 1, identify all the\r\n"
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
		
		addClickEventToKindTable(tbKind,roleList);
		
		return grid;
	}
	private void addClickEventToKindTable(TableView<Kind> tb, ObservableList<Role> roleList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int index = tb.getSelectionModel().selectedIndexProperty().get();
				System.out.println(index);
				DataBaseConnection.sql("Select * from role;", "role", roleList);
			}
		};
		tb.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}
	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				getMainView().setCenter(av3.getGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
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
}
