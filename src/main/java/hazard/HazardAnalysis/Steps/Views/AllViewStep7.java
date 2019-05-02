package hazard.HazardAnalysis.Steps.Views;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class AllViewStep7 {
	private GridPane prevGp, thisGp;

	public AllViewStep7(GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
	}

	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
		Button export = new Button("Export to Excel");
		addClickEventExport(export);
		grid.add(export, 5, 5);
		return grid;
	}

	private void addClickEventExport(Button export) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				DataBaseConnection.exportData();
			}
		};
		export.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
	}

	public GridPane getPrevGridPane() {
		return this.prevGp;
	}

	public GridPane getGridPane() {
		return this.thisGp;
	}

}
