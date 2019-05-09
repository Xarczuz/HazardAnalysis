package hazard.HazardAnalysis.Steps.Views;

import java.io.File;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class ViewStep9 implements ViewInterface {
	private GridPane prevGp, thisGp, nextGp;
	private BorderPane mainView;
	private ViewStep1 vs1;

	public ViewStep9(ViewStep1 viewStep1, BorderPane border, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.mainView = border;
		this.vs1 = viewStep1;
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

	private void addExportEvent(Button btnExport, ProgressIndicator p1) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				p1.setProgress(-1D);
				FileChooser fileChooser = new FileChooser();
//				File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
//				fileChooser.setInitialDirectory(new File(jarDir.getAbsolutePath().replace("%20", " ")));
				fileChooser.setTitle("New Excel");
				fileChooser.setInitialFileName(".xlsx");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
				File file = fileChooser.showSaveDialog(vs1.getpStage());
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
		grid.getStylesheets().add("resources/center.css");
		ProgressIndicator p1 = new ProgressIndicator();
		grid.add(p1, 0, 1);
		p1.setProgress(0);
		Button btnExport = new Button("Export to excel");
		addExportEvent(btnExport, p1);
		grid.add(btnExport, 0, 0);
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

	@Override
	public void setNextGp(GridPane nextGp) {
		this.nextGp = nextGp;
	}
}
