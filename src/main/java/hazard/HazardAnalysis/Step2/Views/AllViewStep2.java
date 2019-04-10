package hazard.HazardAnalysis.Step2.Views;

import hazard.HazardAnalysis.Step3.Views.AllViewStep3;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AllViewStep2 {
	GridPane thisGp, prevGp;
	BorderPane border;

	public AllViewStep2(BorderPane border, GridPane prevGp) {
		this.thisGp = addGridPane();
		this.prevGp = prevGp;
		this.border = border;
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
	
		Text category2 = new Text("Roles");
		category2.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		final ListView<String> lv2 = new ListView<String>();
		lv2.setMinWidth(300);
		lv2.setMaxHeight(200);
		
		Text category3 = new Text("Roles it can play    ");
		category3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		final ListView<String> lv3 = new ListView<String>();
		lv3.setMinWidth(300);
		lv3.setMaxHeight(200);
		GridPane gridRoles = new GridPane();

		gridRoles.add(category2, 0, 0);
		gridRoles.add(lv2, 0, 1);
		
		Button btnAddLink = new Button("+");
		Button btnRemoveLink = new Button("-");
		GridPane gridTextAndBtn = new GridPane();
		
		
		gridTextAndBtn.add(category3, 0, 0);
		gridTextAndBtn.add(btnAddLink, 2, 0);
		gridTextAndBtn.add(btnRemoveLink, 3, 0);
		
		gridRoles.add(gridTextAndBtn, 0, 2);
		gridRoles.add(lv3, 0, 3);
		
		grid.add(gridRoles, 1, 1);
		
		
		
		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text("• SDF-Step 2: For each kind object obtained in SDF-Step 1, identify all the\r\n"
				+ "roles it can play, considering the system description.");
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
			public void handle(MouseEvent e) {
				AllViewStep3 av3 = new AllViewStep3(border,getGridPane());
				getMainView().setCenter(av3.getGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	private Button addEventToGoToPrevStep(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				getMainView().setCenter(getPrevGridPane());
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}
}
