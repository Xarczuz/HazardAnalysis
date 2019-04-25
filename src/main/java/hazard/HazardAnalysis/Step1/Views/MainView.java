package hazard.HazardAnalysis.Step1.Views;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class  MainView {

	BorderPane border = new BorderPane();
	AllView allView = new AllView(border);

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button btnNew = new Button("New");
		btnNew.setPrefSize(100, 20);

		Button btnLoad = new Button("Load");
		btnLoad.setPrefSize(100, 20);
		
		Button btnSave = new Button("Save");
		btnSave.setPrefSize(100, 20);

		hbox.getChildren().addAll(btnNew, btnLoad, btnSave);

		return hbox;
	}

	public void addStackPane(HBox hb) {
		StackPane stack = new StackPane();

		Rectangle helpIcon = new Rectangle(30.0, 25.0);
		helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.web("#4977A3")), new Stop(0.5, Color.web("#B0C6DA")),
						new Stop(1, Color.web("#9CB6CF")), }));
		helpIcon.setStroke(Color.web("#D0E6FA"));
		helpIcon.setArcHeight(3.5);
		helpIcon.setArcWidth(3.5);

		Text helpText = new Text("?");
		helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		helpText.setFill(Color.WHITE);
		helpText.setStroke(Color.web("#7080A0"));

		stack.getChildren().addAll(helpIcon, helpText);
		stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
		StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

		hb.getChildren().add(stack); // Add to HBox from Example 1-2
		HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
	
		vbox.setMinWidth(150);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Text title = new Text("Modes");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);

		Hyperlink options[] = new Hyperlink[] { new Hyperlink("All"), new Hyperlink("Start"), new Hyperlink("Runtime"),
				new Hyperlink("ShutDown") };

		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				border.setCenter(allView.getGridPane());
			}
		};
		options[0].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				border.setCenter(StartView.addGridPane());
			}
		};
		options[1].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		for (int i = 0; i < 4; i++) {

			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

		return vbox;
	}

	public BorderPane view() {

		HBox hbox = addHBox();
		border.setTop(hbox);
		border.setLeft(addVBox());
		
		addStackPane(hbox); // Add stack to HBox in top region

		border.setCenter(allView.getGridPane());

		return border;
	}

}
