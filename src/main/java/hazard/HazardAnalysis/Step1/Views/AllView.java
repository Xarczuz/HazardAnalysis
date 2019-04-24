package hazard.HazardAnalysis.Step1.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Step2.Views.AllViewStep2;
import java.util.Optional;

import hazard.HazardClasses.Hazard;
import hazard.HazardClasses.Kind;
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

public class AllView {
	GridPane thisGp;
	BorderPane border;
	AllViewStep2 av2;

	public AllView(BorderPane border) {
		this.thisGp = addGridPane();
		this.border = border;
		this.av2 = new AllViewStep2(border, getGridPane());
	}

	public GridPane getGridPane() {
		return this.thisGp;
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
		grid.add(addButtonsToTable(tbKind, kindList, "Kind"), 0, 2);

		Text category2 = new Text("Role");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category2, 1, 0);
		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMinWidth(300);
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		role.setMinWidth(200);
		tbRole.getColumns().addAll(id2, role);
		ObservableList<Role> roleList = FXCollections.observableArrayList();

		DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		grid.add(tbRole, 1, 1);
		grid.add(addButtonsToTable(tbRole, roleList, "Role"), 1, 2);

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text("• SDF-Step 1: Identify the kind and role objects explicitly presented in the\n"
				+ "system description.");
		step1.setFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		step1.setWrappingWidth(400);
		grid.add(step1, 2, 1);

		Button btnNextStep = new Button("Next Step");
		grid.add(addNextStepEvent(btnNextStep), 2, 3);

		return grid;
	}

	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				getMainView().setCenter(av2.getGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}



	private <E> GridPane addButtonsToTable(final TableView<E> tb, ObservableList<E> list, String s) {

		Button btnAdd = new Button();
		btnAdd.setText("Add");
		Button btnRemove = new Button();
		btnRemove.setText("Remove");
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Add");
				dialog.setHeaderText("Enter a new " + s);
				dialog.setContentText(s + ":");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()) {
					DataBaseConnection.insert(s.toLowerCase(), result.get());
					list.clear();
					DataBaseConnection.selectAll(s.toLowerCase(), list);
					av2.updateTbKind();
				}
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tb.getItems().size() != 0 ) {
					int index = tb.getSelectionModel().selectedIndexProperty().get();
					if(index!=-1) {
						Hazard o = (Hazard) tb.getItems().remove(index);
						DataBaseConnection.delete(s, o.getId());		
					}
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		GridPane grid = new GridPane();
		grid.add(btnAdd, 0, 0);
		grid.add(btnRemove, 2, 0);

		return grid;
	}
}
