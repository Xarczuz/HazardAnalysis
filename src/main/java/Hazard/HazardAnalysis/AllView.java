package Hazard.HazardAnalysis;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AllView {
	GridPane gp;

	public AllView() {
		this.gp = addGridPane();
	}

	public GridPane getGridPane() {
		return this.gp;
	}

	public GridPane addGridPane() {
		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));

		Text category = new Text("Kind");
		category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category, 0, 0);
		final ListView<String> lv = new ListView<String>();
		lv.setMinWidth(300);
		grid.add(lv, 0, 1);
		grid.add(addButtonsToLists(lv), 0, 2);

		Text category2 = new Text("Role");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category2, 1, 0);
		final ListView<String> lv2 = new ListView<String>();
		lv2.setMinWidth(300);
		grid.add(lv2, 1, 1);
		grid.add(addButtonsToLists(lv2), 1, 2);

		Text category3 = new Text("Relator");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(category3, 2, 0);
		final ListView<String> lv3 = new ListView<String>();
		lv3.setMinWidth(300);
		grid.add(lv3, 2, 1);
		grid.add(addButtonsToLists(lv3), 2, 2);

		return grid;
	}

	private GridPane addButtonsToLists(final ListView<String> lv) {

		Button btnAdd = new Button();
		btnAdd.setText("Add");
		Button btnRemove = new Button();
		btnRemove.setText("Remove");
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				lv.getItems().add("test");
			}
		};
		btnAdd.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (lv.getItems().size() != 0)
					lv.getItems().remove(lv.getItems().size() - 1);
			}
		};
		btnRemove.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		GridPane grid = new GridPane();
		grid.add(btnAdd, 0, 0);
		grid.add(btnRemove, 2, 0);

		return grid;
	}
}
