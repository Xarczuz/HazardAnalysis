package hazard.HazardAnalysis.Steps.Views;

import java.awt.Frame;
import java.io.IOException;
import java.sql.SQLException;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Graph.RelatorGraph;
import hazard.HazardAnalysis.Graph.SystemGraph;
import hazard.HazardClasses.MishapVictim;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.PossibleVictim;
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

public class ViewStep5 implements ViewInterface {
	private GridPane thisGp;
	ObservableList<PossibleVictim> possibleVictimList = FXCollections.observableArrayList();
	ObservableList<MishapVictim> victimList = FXCollections.observableArrayList();

	public ViewStep5() {
		this.thisGp = addGridPane();
	}

	private void addGraphBtnEvent(Button btnGraph) {
		EventHandler<MouseEvent> eh = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SystemGraph frame;
				try {
					frame = new SystemGraph();
					frame.setResizable(true);
					frame.setSize(500, 250);
					frame.setExtendedState(Frame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		btnGraph.addEventHandler(MouseEvent.MOUSE_CLICKED, eh);
	}

	@SuppressWarnings("unchecked")
	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("gridpane");
		Text category = new Text("Possible Mishap Victims double click for graph");
		category.getStyleClass().add("heading");
		grid.add(category, 0, 0);
		final TableView<PossibleVictim> tbPossibleVictim = new TableView<PossibleVictim>();
		tbPossibleVictim.setMinWidth(800);
		tbPossibleVictim.setMaxHeight(200);
		TableColumn<PossibleVictim, String> kind = new TableColumn<PossibleVictim, String>("Environment Object");
		TableColumn<PossibleVictim, String> role = new TableColumn<PossibleVictim, String>("Possible Victim");
		TableColumn<PossibleVictim, String> relator = new TableColumn<PossibleVictim, String>("Exposure");
		kind.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("kind"));
		role.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("role"));
		relator.setCellValueFactory(new PropertyValueFactory<PossibleVictim, String>("relator"));
		kind.setMinWidth(200);
		role.setMinWidth(200);
		relator.setMinWidth(200);
		tbPossibleVictim.getColumns().addAll(role, kind, relator);
		updatePossibleVictimList();
		tbPossibleVictim.setItems(possibleVictimList);
		grid.add(tbPossibleVictim, 0, 1);
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					int index = tbPossibleVictim.getSelectionModel().getSelectedIndex();
					if (index >= 0) {
						PossibleVictim pv = tbPossibleVictim.getItems().get(index);
						RelatorGraph frame;
						try {
							frame = new RelatorGraph(pv.getRelator());
							frame.setResizable(true);
							frame.setSize(800, 400);
							frame.setVisible(true);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		tbPossibleVictim.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		Text category2 = new Text("Mishap Victims");
		category2.getStyleClass().add("heading");
		grid.add(category2, 0, 3);
		final TableView<MishapVictim> tbVictim = new TableView<MishapVictim>();
		tbVictim.setMinWidth(800);
		tbVictim.setMaxHeight(200);
		TableColumn<MishapVictim, Integer> id = new TableColumn<MishapVictim, Integer>("ID");
		TableColumn<MishapVictim, String> kind2 = new TableColumn<MishapVictim, String>("Environment Object");
		TableColumn<MishapVictim, String> role2 = new TableColumn<MishapVictim, String>("Possible Victim");
		TableColumn<MishapVictim, String> relator2 = new TableColumn<MishapVictim, String>("Exposure");
		id.setCellValueFactory(new PropertyValueFactory<MishapVictim, Integer>("id"));
		kind2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("kind"));
		role2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("role"));
		relator2.setCellValueFactory(new PropertyValueFactory<MishapVictim, String>("relator"));
		kind2.setMinWidth(200);
		role2.setMinWidth(200);
		relator2.setMinWidth(200);
		tbVictim.getColumns().addAll(id, role2, kind2, relator2);
		tbVictim.setItems(victimList);
		grid.add(tbVictim, 0, 4);
		Button btnAdd = new Button("+");
		addVictimEvent(btnAdd, tbPossibleVictim, victimList);
		Button btnRemove = new Button("-");
		removeVictimEvent(btnRemove, tbVictim);
		Button btnGraph = new Button("Generate System Diagram");
		addGraphBtnEvent(btnGraph);
		GridPane gridBtn1 = new GridPane();
		gridBtn1.add(btnAdd, 0, 0);
		gridBtn1.add(btnRemove, 1, 0);
		gridBtn1.add(btnGraph, 2, 0);
		grid.add(gridBtn1, 0, 2);
		return grid;
	}

	private void addVictimEvent(Button btnAdd, TableView<PossibleVictim> tbPossibleVictim,
			ObservableList<MishapVictim> victimList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tbPossibleVictim.getSelectionModel().getSelectedIndex() < 0) {
					return;
				}
				int index = tbPossibleVictim.getSelectionModel().getSelectedIndex();
				PossibleVictim pv = tbPossibleVictim.getItems().get(index);
				DataBaseConnection.insertMishapVictim(pv.getRoleID(), pv.getRole(), pv.getKindID(), pv.getKind(),
						pv.getRelatorID(), pv.getRelator());
				updateVictimList();
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	@Override
	public GridPane getGridPane() {
		updatePossibleVictimList();
		updateVictimList();
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 5";
	}

	@Override
	public String getStepDescription() {
		return "Since the occurrence of a mishap event must have more than one mishap victim to participate in the event, this step identifies all the possible mishap victims.";
	}

	private void removeVictimEvent(Button btnRemove, TableView<MishapVictim> tbVictim) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (tbVictim.getItems().size() != 0) {
					int index = tbVictim.getSelectionModel().selectedIndexProperty().get();
					if (index != -1) {
						Play o = tbVictim.getItems().remove(index);
						DataBaseConnection.delete("mishapvictim", o.getId());
						updateVictimList();
					}
				}
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public void updateVictimList() {
		DataBaseConnection.sql("Select * from mishapvictim", "mishapvictim", victimList);
	}

	public void updatePossibleVictimList() {
		DataBaseConnection.sql(
				"select roletoplay.role,roletoplay.kind,relatortorole.relator,roletoplay.roleid,roletoplay.kindid,relatortorole.relatorid from roletoplay, relatortorole where relatortorole.roleid = roletoplay.roleid",
				"possiblevictim", possibleVictimList);
	}
}
