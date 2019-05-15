package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.HazardElement;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.MishapVictim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ViewStep6 implements ViewInterface {
	private GridPane thisGp;
	private ObservableList<MishapVictim> victimList = FXCollections.observableArrayList();
	private ObservableList<HazardElement> hazardElementList = FXCollections.observableArrayList();

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
		TableColumn<MishapVictim, String> kind2 = new TableColumn<MishapVictim, String>("Environment Object");
		TableColumn<MishapVictim, String> role2 = new TableColumn<MishapVictim, String>("Possible Victim");
		TableColumn<MishapVictim, String> relator2 = new TableColumn<MishapVictim, String>("Exposure");
		id.setCellValueFactory(new PropertyValueFactory<MishapVictim, Integer>("id"));
		kind2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("kind"));
		role2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("role"));
		relator2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("relator"));
		kind2.setMinWidth(120);
		role2.setMinWidth(120);
		relator2.setMinWidth(120);
		tbVictim.getColumns().addAll(id, role2, kind2, relator2);
		tbVictim.setItems(victimList);
		grid2.add(tbVictim, 0, 1);
		Text category2 = new Text("Hazard Elements");
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
		grid2.add(category2, 1, 0);
		grid2.add(tbKind, 1, 1);
		Text category3 = new Text("Hazards");
		category3.getStyleClass().add("heading");
		final TableView<Kind> tbKindToRole = new TableView<Kind>();
		tbKindToRole.setMinWidth(800);
		tbKindToRole.setMaxWidth(800);
		tbKindToRole.setMaxHeight(200);
		TableColumn<Kind, Integer> id3 = new TableColumn<Kind, Integer>("ID");
		id3.setCellValueFactory(new PropertyValueFactory<Kind, Integer>("id"));
		ObservableList<Kind> kindToRoleList = FXCollections.observableArrayList();
		tbKindToRole.setItems(kindToRoleList);
		Button btnAddLink = new Button("+");
		Button btnRemoveLink = new Button("-");
		GridPane gridTextAndBtn = new GridPane();
		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);
		grid2.setHgap(10);
		grid.add(grid2, 0, 0);
		grid.add(gridTextAndBtn, 0, 1);
		grid.add(tbKindToRole, 0, 2);
		return grid;
	}

	@Override
	public GridPane getGridPane() {
		updateVictimList();
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

	public void updateVictimList() {
		DataBaseConnection.sql("Select * from mishapvictim", "mishapvictim", victimList);
	}
}
