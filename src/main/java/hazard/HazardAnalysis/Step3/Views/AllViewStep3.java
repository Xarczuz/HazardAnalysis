package hazard.HazardAnalysis.Step3.Views;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AllViewStep3 {
	GridPane thisGp, prevGp;
	BorderPane border;
	public AllViewStep3(BorderPane border, GridPane prevGp) {
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

		Text description = new Text("Description");
		description.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(description, 2, 0);
		Text step1 = new Text(" • SDF-Step 3: For each role object obtained in SDF-Step 1 and SDFStep 2,\r\n" + 
				" identify the relator that connects this role, and specify all the other roles\r\n" + 
				" connected by the identified relator, considering the system description and\r\n" + 
				" the analysts’ expertise.");
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
