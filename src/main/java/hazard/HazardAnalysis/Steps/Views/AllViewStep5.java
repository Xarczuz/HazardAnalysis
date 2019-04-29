package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.PossibleVictim;
import hazard.HazardClasses.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AllViewStep5 implements AllViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	ObservableList<PossibleVictim> possibleVictimList = FXCollections.observableArrayList();

	public void updatePossibleVictimList() {
		DataBaseConnection.sql(
				"select kind.kind,role.role,relatortorole.relator from kind,role,relatortorole,roletoplay where (roletoplay.kindid = kind.id and roletoplay.roleid = role.id and relatortorole.roleid = role.id);",
				"possiblevictim", possibleVictimList);
	}

	public AllViewStep5(AllViewStep1 allViewStep1, BorderPane mainView, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = mainView;
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
		final TableView<PossibleVictim> tbRole = new TableView<PossibleVictim>();
		tbRole.setMinWidth(350);
		TableColumn<PossibleVictim, String> kind = new TableColumn<PossibleVictim, String>("Kind");
		TableColumn<PossibleVictim, String> role = new TableColumn<PossibleVictim, String>("Role");
		TableColumn<PossibleVictim, String> relator = new TableColumn<PossibleVictim, String>("Relator");

		kind.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("kind"));
		role.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("role"));
		relator.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("relator"));

//		role.setMinWidth(200);
		tbRole.getColumns().addAll(kind, role, relator);

		updatePossibleVictimList();
		tbRole.setItems(possibleVictimList);
		grid.add(tbRole, 0, 1);

		Button btnAdd = new Button("+");
		Button btnRemove = new Button("-");

		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 0, 1);

		grid.add(gridBtn1, 1, 1);

		Text category2 = new Text("Mishap Victim");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category2, 2, 0);
		final TableView<Role> tbVictim = new TableView<Role>();
		tbVictim.setMaxWidth(350);
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> victim = new TableColumn<Role, String>("Role");
		TableColumn<Role, CheckBox> rStart = new TableColumn<Role, CheckBox>("Start");
		TableColumn<Role, CheckBox> rRuntime = new TableColumn<Role, CheckBox>("RunTime");
		TableColumn<Role, CheckBox> rShutdown = new TableColumn<Role, CheckBox>("Shutdown");

		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		victim.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));

		rStart.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbstart"));
		rStart.setStyle("-fx-alignment: CENTER;");
		rRuntime.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbruntime"));
		rRuntime.setStyle("-fx-alignment: CENTER;");
		rShutdown.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbshutdown"));
		rShutdown.setStyle("-fx-alignment: CENTER;");

		id2.setMaxWidth(30);
		victim.setMinWidth(100);
//		tbVictim.getColumns().addAll(id2, role, rStart, rRuntime, rShutdown);
		ObservableList<Role> victimList = FXCollections.observableArrayList();

//		DataBaseConnection.selectAll("role", roleList);
		tbVictim.setItems(victimList);
		grid.add(tbVictim, 2, 1);
		// grid.add(addButtonsToTable(tbVictim, victimList, "Role"), 1, 2);

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 3, 0);
		Text step4 = new Text(
				"• Step 5: Since the occurrence of a mishap event must have more than one mishap victim\r\n"
						+ "to participate in the event, this step identifies all the possible mishap victims");
		step4.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step4.setWrappingWidth(400);
		grid.add(step4, 3, 1);

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

	public void setNextGp(GridPane nextGp) {
		this.nextGp = nextGp;
	}
}
