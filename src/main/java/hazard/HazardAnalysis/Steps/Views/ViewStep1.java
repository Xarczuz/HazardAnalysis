package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewStep1 {
	private GridPane thisGp;
	private BorderPane border;
	private ViewStep2 av2;
	private ViewStep3 av3;
	private ViewStep4 av4;
	private ViewStep5 av5;
	private ViewStep6 av6;
	private ViewStep7 av7;
	private ViewStep8 av8;
	private ViewStep9 av9;
	private Stage pStage;

	public ViewStep1(Stage pStage, BorderPane border) {
		this.pStage = pStage;
		this.thisGp = addGridPane();
		this.border = border;
		this.av2 = new ViewStep2(this, border, getGridPane());
		this.av3 = new ViewStep3(this, border, getAv2().getGridPane());
		this.av4 = new ViewStep4(this, border, getAv3().getGridPane());
		this.av5 = new ViewStep5(this, border, getAv4().getGridPane());
		this.av6 = new ViewStep6(this, border, getAv5().getGridPane());
		this.av7 = new ViewStep7(this, border, getAv6().getGridPane());
		this.av8 = new ViewStep8(this, border, getAv7().getGridPane());
		this.av9 = new ViewStep9(this, border, getAv8().getGridPane());
		this.av2.setNextGp(this.av3.getGridPane());
		this.av3.setNextGp(this.av4.getGridPane());
		this.av4.setNextGp(this.av5.getGridPane());
		this.av5.setNextGp(this.av6.getGridPane());
		this.av6.setNextGp(this.av7.getGridPane());
		this.av7.setNextGp(this.av8.getGridPane());
		this.av8.setNextGp(this.av9.getGridPane());
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
				TextField t = new TextField();
				CheckBox st = new CheckBox("Start");
				CheckBox rt = new CheckBox("runtime");
				CheckBox sd = new CheckBox("ShutDown");
				GridPane gp = new GridPane();
				gp.setPadding(new Insets(5, 5, 5, 5));
				t.setPadding(new Insets(5, 5, 5, 5));
				gp.add(t, 0, 0);
				gp.add(st, 0, 1);
				gp.add(rt, 0, 2);
				gp.add(sd, 0, 3);
				dialog.getDialogPane().setContent(gp);
				EventHandler<DialogEvent> eventHandler = new EventHandler<DialogEvent>() {
					@Override
					public void handle(DialogEvent event) {
						if (!t.getText().isEmpty()) {
							DataBaseConnection.insertRoloeOrKind(s.toLowerCase(), t.getText(), st.isSelected(),
									rt.isSelected(), sd.isSelected());
							list.clear();
							DataBaseConnection.selectAll(s.toLowerCase(), list);
							getAv2().updateTbKind();
							getAv3().updateTbRole();
							getAv4().updateTbRole();
						}
					}
				};
				dialog.setOnCloseRequest(eventHandler);
				dialog.show();
				t.requestFocus();
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tb.getItems().size() != 0) {
					int index = tb.getSelectionModel().selectedIndexProperty().get();
					if (index != -1) {
						Play o = (Play) tb.getItems().remove(index);
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

	@SuppressWarnings("unchecked")
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setStyle("-fx-border-width: 0 5 5 5; -fx-border-color: black black black black;");
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
		Text category = new Text("Kind");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);
		final TableView<Kind> tbKind = new TableView<Kind>();
		tbKind.setMaxWidth(350);
		TableColumn<Kind, Integer> id = new TableColumn<Kind, Integer>("ID");
		TableColumn<Kind, String> kind = new TableColumn<Kind, String>("Kind");
		TableColumn<Kind, CheckBox> kStart = new TableColumn<Kind, CheckBox>("Start");
		TableColumn<Kind, CheckBox> kRuntime = new TableColumn<Kind, CheckBox>("RunTime");
		TableColumn<Kind, CheckBox> kShutdown = new TableColumn<Kind, CheckBox>("Shutdown");
		id.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		kind.setCellValueFactory(new PropertyValueFactory<Kind, String>("kind"));
		kStart.setCellValueFactory(new PropertyValueFactory<Kind, CheckBox>("cbstart"));
		kStart.setStyle("-fx-alignment: CENTER;");
		kRuntime.setCellValueFactory(new PropertyValueFactory<Kind, CheckBox>("cbruntime"));
		kRuntime.setStyle("-fx-alignment: CENTER;");
		kShutdown.setCellValueFactory(new PropertyValueFactory<Kind, CheckBox>("cbshutdown"));
		kShutdown.setStyle("-fx-alignment: CENTER;");
		id.setMaxWidth(30);
		kind.setMinWidth(100);
		tbKind.getColumns().addAll(id, kind, kStart, kRuntime, kShutdown);
		ObservableList<Kind> kindList = FXCollections.observableArrayList();
		DataBaseConnection.selectAll("kind", kindList);
		tbKind.setItems(kindList);
		grid.add(tbKind, 0, 1);
		grid.add(addButtonsToTable(tbKind, kindList, "Kind"), 0, 2);
		Text category2 = new Text("Role");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category2, 1, 0);
		final TableView<Role> tbRole = new TableView<Role>();
		tbRole.setMaxWidth(350);
		TableColumn<Role, Integer> id2 = new TableColumn<Role, Integer>("ID");
		TableColumn<Role, String> role = new TableColumn<Role, String>("Role");
		TableColumn<Role, CheckBox> rStart = new TableColumn<Role, CheckBox>("Start");
		TableColumn<Role, CheckBox> rRuntime = new TableColumn<Role, CheckBox>("RunTime");
		TableColumn<Role, CheckBox> rShutdown = new TableColumn<Role, CheckBox>("Shutdown");
		id2.setCellValueFactory(new PropertyValueFactory<Role, Integer>("id"));
		role.setCellValueFactory(new PropertyValueFactory<Role, String>("role"));
		rStart.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbstart"));
		rStart.setStyle("-fx-alignment: CENTER;");
		rRuntime.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbruntime"));
		rRuntime.setStyle("-fx-alignment: CENTER;");
		rShutdown.setCellValueFactory(new PropertyValueFactory<Role, CheckBox>("cbshutdown"));
		rShutdown.setStyle("-fx-alignment: CENTER;");
		id2.setMaxWidth(30);
		role.setMinWidth(100);
		tbRole.getColumns().addAll(id2, role, rStart, rRuntime, rShutdown);
		ObservableList<Role> roleList = FXCollections.observableArrayList();
		DataBaseConnection.selectAll("role", roleList);
		tbRole.setItems(roleList);
		grid.add(tbRole, 1, 1);
		grid.add(addButtonsToTable(tbRole, roleList, "Role"), 1, 2);
		Text description = new Text("Step 1");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text(
				"Identify the kind and role objects explicitly presented in the\n" + "system description.");
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
				av2.updateTbKind();
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	public ViewStep2 getAv2() {
		av2.updateTbKind();
		return av2;
	}

	public ViewStep3 getAv3() {
		av3.updateTbRole();
		return av3;
	}

	public ViewStep4 getAv4() {
		av4.updateTbRole();
		return av4;
	}

	public ViewStep5 getAv5() {
		av5.updatePossibleVictimList();
		av5.updateHazardList();
		return av5;
	}

	public ViewStep6 getAv6() {
		return av6;
	}

	public ViewStep7 getAv7() {
		return av7;
	}

	public ViewStep8 getAv8() {
		return this.av8;
	}

	public ViewStep9 getAv9() {
		return this.av9;
	}

	public GridPane getGridPane() {
		return this.thisGp;
	}

	public BorderPane getMainView() {
		return this.border;
	}

	public Stage getpStage() {
		return pStage;
	}
}
