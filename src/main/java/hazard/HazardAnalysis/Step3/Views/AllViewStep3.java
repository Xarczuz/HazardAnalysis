package hazard.HazardAnalysis.Step3.Views;

import hazard.HazardAnalysis.Step4.Views.AllViewStep4;
import hazard.HazardClasses.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AllViewStep3 {
	GridPane thisGp, prevGp;
	BorderPane border;
	AllViewStep4 av4;

	public AllViewStep3(BorderPane border, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.border = border;
		this.av4 = new AllViewStep4(border, getGridPane());
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

		Text category = new Text("Roles");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);

		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(300);
		
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(200);
		tbRole.getColumns().addAll(id2, role);
		ObservableList<Role> roleList = FXCollections.observableArrayList();
		// DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		grid.add(tbRole, 0, 1);

		Text category2 = new Text("Relators ");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		final ListView<String> lv2 = new ListView<String>();
		lv2.setMinWidth(300);
		lv2.setMaxHeight(200);

		Button btnAddLink1 = new Button("Add");

//		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent e) {
//				TextInputDialog dialog = new TextInputDialog("");
//
//				dialog.setTitle("Add Relator");
//				dialog.setHeaderText("Enter a new relator");
//				dialog.setContentText("Relator:");
//
//				Optional<String> result = dialog.showAndWait();
//
//				if (result.isPresent()) {
//					lv2.getItems().add(result.get());
//				}
//
//			}
//		};
//		btnAddLink1.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		Button btnRemoveLink1 = new Button("Remove");
		GridPane gridTextAndBtn1 = new GridPane();

		gridTextAndBtn1.add(category2, 0, 0);
		gridTextAndBtn1.add(btnAddLink1, 2, 0);
		gridTextAndBtn1.add(btnRemoveLink1, 3, 0);
		GridPane gridRelators = new GridPane();

		gridRelators.add(gridTextAndBtn1, 0, 0);
		gridRelators.add(lv2, 0, 1);

		Text category3 = new Text("Relators that the role have ");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final ListView<String> lv3 = new ListView<String>();
		lv3.setMinWidth(300);
		lv3.setMaxHeight(200);

		Button btnAddLink = new Button("+");
		Button btnRemoveLink = new Button("-");
		GridPane gridTextAndBtn2 = new GridPane();

		gridTextAndBtn2.add(category3, 0, 0);
		gridTextAndBtn2.add(btnAddLink, 2, 0);
		gridTextAndBtn2.add(btnRemoveLink, 3, 0);

		gridRelators.add(gridTextAndBtn2, 0, 2);
		gridRelators.add(lv3, 0, 3);

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
		return grid;
	}

	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(av4.getGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
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
}
