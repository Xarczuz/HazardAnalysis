package hazard.HazardAnalysis.Steps.Views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ViewStep9 implements ViewInterface{
public ViewStep9(ViewStep1 viewStep1, BorderPane border, GridPane gridPane) {
		// TODO Auto-generated constructor stub
	}

	//	private void addClickEventExport(Button export) {
//	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//		@Override
//		public void handle(MouseEvent e) {
//
//			DataBaseConnection.exportData();
//		}
//	};
//	export.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
//}
	@Override
	public GridPane addGridPane() {
		// TODO Auto-generated method stub
		
		
//		Button export = new Button("Export to Excel");
//		addClickEventExport(export);
//		grid.add(export, 5, 5);

		return null;
	}

	@Override
	public GridPane getGridPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorderPane getMainView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPane getNextGridPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GridPane getPrevGridPane() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNextGp(GridPane nextGp) {
		// TODO Auto-generated method stub
		
	}

}
