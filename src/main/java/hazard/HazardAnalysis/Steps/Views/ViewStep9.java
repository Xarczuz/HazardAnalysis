package hazard.HazardAnalysis.Steps.Views;

import java.io.File;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewStep9 implements ViewInterface {
	private GridPane thisGp;
	private Stage pStage;

	public ViewStep9(Stage pStage) {
		this.thisGp = addGridPane();
		this.pStage = pStage;
	}

	private void addExportEvent(Button btnExport, ProgressIndicator p1) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				p1.setProgress(-1D);
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("New Excel");
				fileChooser.setInitialFileName(".xlsx");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
				File file = fileChooser.showSaveDialog(pStage);
				Alert a = new Alert(AlertType.INFORMATION);
				if (file != null) {
					DataBaseConnection.exportData(file);
					a.setTitle("Export Done");
					a.setContentText("Export Done");
					a.showAndWait();
					p1.setProgress(1);
				} else {
					a.setTitle("Export Failed");
					a.setContentText("Export Failed");
					a.show();
					p1.setProgress(0);
				}
			}
		};
		btnExport.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	@Override
	public GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("gridpane");
		ProgressIndicator p1 = new ProgressIndicator();
		grid.add(p1, 0, 1);
		p1.setProgress(0);
		Button btnExport = new Button("Export to excel");
		addExportEvent(btnExport, p1);
		grid.add(btnExport, 0, 0);
		return grid;
	}

	@Override
	public GridPane getGridPane() {
		return this.thisGp;
	}

	@Override
	public String getStep() {
		return "Step 9";
	}

	@Override
	public String getStepDescription() {
		return "You can export you project to excel.";
	}
}
