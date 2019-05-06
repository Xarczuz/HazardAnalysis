package hazard.HazardAnalysis.Steps.Views;

import java.io.File;

import hazard.HazardAnalysis.DataBase.CreateDataBase;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView {

	BorderPane border = new BorderPane();
	ViewStep1 allView;
	private Stage pStage;

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button btnNew = new Button("New");
		addNewEvent(btnNew);
		btnNew.setPrefSize(100, 20);

		Button btnLoad = new Button("Load");
		addLoadEvent(btnLoad);
		btnLoad.setPrefSize(100, 20);

		hbox.getChildren().addAll(btnNew, btnLoad);

		return hbox;
	}

	private Button addLoadEvent(Button btnLoad) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load database");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DB", "*.db"));
				File file = fileChooser.showOpenDialog(pStage);
				if (file != null) {
					DataBaseConnection.setDatabase(file.getPath());
					CreateDataBase.setDatabase(file.getPath());
					allView = new ViewStep1(pStage,border);
					border.setLeft(addVBox());
					border.setCenter(allView.getGridPane());
				}

			}
		};
		btnLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnLoad;
	}

	private Button addNewEvent(Button btnNew) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("New database");
				fileChooser.setInitialFileName(".db");
				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DB", "*.db"));
				File file = fileChooser.showSaveDialog(pStage);
				if (file != null && file.getName().contains(".db")) {
					DataBaseConnection.setDatabase(file.getPath());
					CreateDataBase.setDatabase(file.getPath());
					CreateDataBase.createNewDatabase();
					CreateDataBase.createNewTable();

//					DataBaseConnection.populateWithTestData();
					allView = new ViewStep1(pStage,border);
					border.setLeft(addVBox());
					border.setCenter(allView.getGridPane());
				}

			}
		};
		btnNew.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNew;
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
		Text title = new Text("Step");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		vbox.getChildren().add(title);

		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Step 1"), new Hyperlink("Step 2"),
				new Hyperlink("Step 3"), new Hyperlink("Step 4"), new Hyperlink("Step 5"), new Hyperlink("Step 6"),
				new Hyperlink("Step 7"), new Hyperlink("Step 8") };

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
				allView.getAv2().updateTbKind();
				border.setCenter(allView.getAv2().getGridPane());
			}
		};
		options[1].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				allView.getAv3().updateTbRole();
				border.setCenter(allView.getAv3().getGridPane());
			}
		};
		options[2].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				allView.getAv4().updateTbRole();
				border.setCenter(allView.getAv4().getGridPane());
			}
		};
		options[3].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				allView.getAv5().updateHazardList();
				allView.getAv5().updatePossibleVictimList();
				border.setCenter(allView.getAv5().getGridPane());
			}
		};
		options[4].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				allView.getAv6().updateHazardList();
				border.setCenter(allView.getAv6().getGridPane());
			}
		};
		options[5].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				allView.getAv7().updateHazardList();
				border.setCenter(allView.getAv7().getGridPane());
			}
		};
		options[6].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				border.setCenter(allView.getAv8().getGridPane());
			}
		};
		options[7].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		for (int i = 0; i < options.length; i++) {

			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

		return vbox;
	}

	public BorderPane view(Stage primaryStage) {
		this.pStage = primaryStage;
		HBox hbox = addHBox();
		border.setTop(hbox);
		addStackPane(hbox); // Add stack to HBox in top region

		return border;
	}

}
