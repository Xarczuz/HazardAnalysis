package Hazard.HazardAnalysis;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

		primaryStage.setX(primaryScreenBounds.getMinX());
		primaryStage.setY(primaryScreenBounds.getMinY());
		primaryStage.setWidth(primaryScreenBounds.getWidth());
		primaryStage.setHeight(primaryScreenBounds.getHeight() - 5);

		primaryStage.setTitle("Hazard");

		BorderPane border = new BorderPane();
		MainView av = new MainView();
		border = av.view();
		
		
		
		Scene scene = new Scene(border);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Hazard");
		primaryStage.show();
	}

	

	public static void main(String[] args) {
		launch(args);
	}
}
