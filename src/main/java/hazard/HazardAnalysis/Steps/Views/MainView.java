package hazard.HazardAnalysis.Steps.Views;

import java.io.File;

import hazard.HazardAnalysis.DataBase.CreateDataBase;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainView {
	private BorderPane border = new BorderPane();
	private ViewStep1 av1;
	private ViewStep2 av2;
	private ViewStep3 av3;
	private ViewStep4 av4;
	private ViewStep5 av5;
	private ViewStep6 av6;
	private ViewStep7 av7;
	private ViewStep8 av8;
	private ViewStep9 av9;
	private ViewStep10 av10;
	private Stage pStage;
	private int currentStep;
	private Text step, description;

	private Button addEventToGoToPrevStep(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (currentStep != 1)
					currentStep--;
				changeStepTexts(currentStep);
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-border-width: 0 0 5 0; -fx-border-color: black black black black;");
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
					CreateDataBase.createNewTable();
					av1 = new ViewStep1();
					currentStep = 1;
					loadCenterViews();
					border.setLeft(addVBox());
					border.setRight(addRightVBox());
					border.setCenter(av1.getGridPane());
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
					av1 = new ViewStep1();
					currentStep = 1;
					loadCenterViews();
					border.setLeft(addVBox());
					border.setRight(addRightVBox());
					border.setCenter(av1.getGridPane());
				}
			}
		};
		btnNew.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNew;
	}

	private Button addNextStepEvent(Button btnNextStep) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (currentStep != 10)
					currentStep++;
				changeStepTexts(currentStep);
			}
		};
		btnNextStep.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		return btnNextStep;
	}

	public VBox addRightVBox() {
		VBox vbox = new VBox();
		vbox.setMinWidth(300);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		step = new Text(av1.getStep());
		step.getStyleClass().add("heading");
		description = new Text(av1.getStepDescription());
		description.getStyleClass().add("texts");
		description.setWrappingWidth(300);
		vbox.getChildren().addAll(step, description);
		Button btnBack = new Button("Back");
		Button btnNextStep = new Button("Next Step");
		GridPane gridBtn = new GridPane();
		gridBtn.add(addEventToGoToPrevStep(btnBack), 0, 0);
		gridBtn.add(addNextStepEvent(btnNextStep), 2, 0);
		vbox.getChildren().add(gridBtn);
		return vbox;
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setMinWidth(150);
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Text title = new Text("Navigation:");
		title.getStyleClass().add("heading");
		vbox.getChildren().add(title);
		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Step 1"), new Hyperlink("Step 2"),
				new Hyperlink("Step 3"), new Hyperlink("Step 4"), new Hyperlink("Step 5"), new Hyperlink("Step 6"),
				new Hyperlink("Step 7"), new Hyperlink("Step 8"), new Hyperlink("Step 9"), new Hyperlink("Step 10") };
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(1);
			}
		};
		options[0].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(2);
			}
		};
		options[1].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(3);
			}
		};
		options[2].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(4);
			}
		};
		options[3].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(5);
			}
		};
		options[4].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(6);
			}
		};
		options[5].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(7);
			}
		};
		options[6].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(8);
			}
		};
		options[7].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(9);
			}
		};
		options[8].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				changeStepTexts(10);
			}
		};
		options[9].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
		for (int i = 0; i < options.length; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
			options[i].setStyle("-fx-text-fill: white;");
		}
		return vbox;
	}

	private void changeStepTexts(int currentStep) {
		this.currentStep = currentStep;
		switch (currentStep) {
		case 1:
			border.setCenter(getAv1().getGridPane());
			step.setText(getAv1().getStep());
			description.setText(getAv1().getStepDescription());
			currentStep = 1;
			break;
		case 2:
			border.setCenter(getAv2().getGridPane());
			step.setText(getAv2().getStep());
			description.setText(getAv2().getStepDescription());
			currentStep = 2;
			break;
		case 3:
			border.setCenter(getAv3().getGridPane());
			step.setText(getAv3().getStep());
			description.setText(getAv3().getStepDescription());
			currentStep = 3;
			break;
		case 4:
			border.setCenter(getAv4().getGridPane());
			step.setText(getAv4().getStep());
			description.setText(getAv4().getStepDescription());
			currentStep = 4;
			break;
		case 5:
			border.setCenter(getAv5().getGridPane());
			step.setText(getAv5().getStep());
			description.setText(getAv5().getStepDescription());
			currentStep = 5;
			break;
		case 6:
			border.setCenter(getAv6().getGridPane());
			step.setText(getAv6().getStep());
			description.setText(getAv6().getStepDescription());
			currentStep = 6;
			break;
		case 7:
			border.setCenter(getAv7().getGridPane());
			step.setText(getAv7().getStep());
			description.setText(getAv7().getStepDescription());
			currentStep = 7;
			break;
		case 8:
			border.setCenter(getAv8().getGridPane());
			step.setText(getAv8().getStep());
			description.setText(getAv8().getStepDescription());
			currentStep = 8;
			break;
		case 9:
			border.setCenter(getAv9().getGridPane());
			step.setText(getAv9().getStep());
			description.setText(getAv9().getStepDescription());
			currentStep = 9;
			break;
		case 10:
			border.setCenter(getAv10().getGridPane());
			step.setText(getAv10().getStep());
			description.setText(getAv10().getStepDescription());
			currentStep = 10;
			break;
		default:
			break;
		}
	}

	public ViewStep1 getAv1() {
		return av1;
	}

	public ViewStep2 getAv2() {
		return av2;
	}

	public ViewStep3 getAv3() {
		return av3;
	}

	public ViewStep4 getAv4() {
		return av4;
	}

	public ViewStep5 getAv5() {
		return av5;
	}

	public ViewStep6 getAv6() {
		return av6;
	}

	public ViewStep7 getAv7() {
		return av7;
	}

	public ViewStep8 getAv8() {
		return this.av8;
	}

	public ViewStep9 getAv9() {
		return this.av9;
	}

	public ViewStep10 getAv10() {
		return this.av10;
	}

	private void loadCenterViews() {
		this.av2 = new ViewStep2();
		this.av3 = new ViewStep3();
		this.av4 = new ViewStep4();
		this.av5 = new ViewStep5();
		this.av6 = new ViewStep6();
		this.av7 = new ViewStep7();
		this.av8 = new ViewStep8();
		this.av9 = new ViewStep9();
		this.av10 = new ViewStep10(this.pStage);
	}

	public BorderPane view(Stage primaryStage) {
		this.pStage = primaryStage;
		border.getStyleClass().add("borderpane");
		border.getStylesheets().add("css.css");
		HBox hbox = addHBox();
		border.setTop(hbox);
		return border;
	}
}
