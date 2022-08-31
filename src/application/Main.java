package application;


///////////////////////////////////////////// These are imports needed for Main ////////////////////////////////////////////////////
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This is where the application execution begins //////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Main extends Application {
	
	
	// This loads a Window. ----------------------------------------------------------------------------------------------
	@Override
	public void start(Stage primaryStage) throws Exception {
		//showATMWindow(primaryStage);
		 showLoginWindow(primaryStage);
		//showOperatorWindow(primaryStage);
			
	}
	
	
	// This shows the ATM Window. ----------------------------------------------------------------------------------------
	public void showATMWindow(Stage primaryStage) throws IOException {
		Parent atmWindow = FXMLLoader.load(getClass().getResource("ATM Window.fxml"));
		Scene atmScene = new Scene(atmWindow, 1100, 700);
		atmScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(atmScene);
		primaryStage.show();	
	}
	
	
	// This shows the Login Window. --------------------------------------------------------------------------------------
	public void showLoginWindow(Stage primaryStage) throws IOException {
			Parent loginWindow = FXMLLoader.load(getClass().getResource("Login Window.fxml"));			
			Scene loginScene = new Scene(loginWindow);
			loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.setTitle("Bronco Banking");
			primaryStage.show();
	} 
	
	
	// This shows the Operator Window. -----------------------------------------------------------------------------------
	public void showOperatorWindow(Stage primaryStage) throws IOException {
		Parent operatorWindow = FXMLLoader.load(getClass().getResource("Operator Window.fxml"));			
		Scene operatorScene = new Scene(operatorWindow);
		operatorScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(operatorScene);
		primaryStage.show();	
	} 
	
	
	// This is the main method. --------------------------------------------------------------------------------------------
	public static void main(String[] args) {

		launch(args);
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
} // End of Main class. /////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////