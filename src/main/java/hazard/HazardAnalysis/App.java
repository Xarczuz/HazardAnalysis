package hazard.HazardAnalysis;

import hazard.HazardAnalysis.DataBase.CreateDataBase;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.Step1.Views.MainView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * 3.4.1 Step 3.4.1: System Description Formalization The first step is to
 * formalize a system description from natural language into HO-style models. In
 * this step, the analysts will identify the objects described by the system
 * description and clarify the relations between the objects in accordance to
 * the system description and their expertise. The aim of this step is to
 * achieve a clear understanding of the system from a real-world perspective.
 * The formalization can be conducted by going through the following steps:
 * 
 * 
 * • SDF-Step 1: Identify the kind and role objects explicitly presented in the
 * system description.
 * 
 * • SDF-Step 2: For each kind object obtained in SDF-Step 1, identify all the
 * roles it can play, considering the system description.
 * 
 * • SDF-Step 3: For each role object obtained in SDF-Step 1 and SDFStep 2,
 * identify the relator that connects this role, and specify all the other roles
 * connected by the identified relator, considering the system description and
 * the analysts’ expertise.
 * 
 * • SDF-Step 4: For each role object obtained in SDF-Step 1, SDF-Step 2 and
 * SDF-Step 3, identify all the kind objects that can play the role, considering
 * the system description.
 * 
 * 
 * We use the UC01, as illustrated in Section 3.1, to further illustrate this
 * step. We can identify Robot, Robot Handle, Battery, Patient as kind objects
 * according to the description. The Patient can play two roles BeingSupported
 * and BeingLifted. The Robot Handle can play two roles BalanceSupporter and
 * ObjectLifter. The Robot can play the ElectricityConsumer role, and the
 * Battery can play the ElectricitySource role. The BalanceSupport, LiftUp, and
 * ElectricityConsumption relators can be further identified. The BalanceSupport
 * relator 28 Chapter 3. An Ontological Approach to Safety Analysis connects the
 * BalanceSupporter and BeingSupported roles, played by Robot and Patient
 * respectively. The LiftUp relator connects the ObjectLifter and BeingLifted
 * roles, played by Robot Handle and Patient respectively. The
 * ElectricityConsumption relator connects the ElectricitySource and
 * ElectricityConsumer roles, played by Battery and Robot respectively. After
 * performing the SDF-Step 1 to SDF-Step 4, we shall obtain the formalized
 * description for the UC01, as shown in Figure 3.5.
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

		DataBaseConnection.setDatabase("test.db");
		CreateDataBase.setDatabase("test.db");
//		CreateDataBase.createNewDatabase();
//		CreateDataBase.createNewTable();

		BorderPane border = new BorderPane();
		MainView av = new MainView();
		border = av.view();
		Scene scene = new Scene(border);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Hazard");
		primaryStage.show();
//		DataBaseConnection.insert("kind","test");
//		DataBaseConnection.delete("kind", 4);

//		DataBaseConnection.selectAll("Role",null);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
