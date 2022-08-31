package application;


/////////////////////////////////////// These are imports needed for the Login Controller. //////////////////////////////////////////////////
import java.io.IOException;
import java.util.HashMap;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This controls the events triggered by GUI action in the Login Window. ///////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class LoginController {

	///////////////////////////////////// This section contains all of the FXML injections. ////////////////////////////////////////////////
	
	// This is necessary to access the controls on the Login Window. ----------------------------------------------------------
	@FXML private ImageView imageViewChamale;
	@FXML private ImageView imageViewZhang;
	@FXML private ImageView imageViewSepulveda;
	@FXML private ImageView imageViewMedina;
	@FXML private VBox VBoxChamale;
	@FXML private VBox VBoxZhang;
	@FXML private VBox VBoxSepulveda;
	@FXML private VBox VBoxMedina;
	@FXML private GridPane gridPaneCards;	
	@FXML private VBox vBoxEnterPIN;
	@FXML private PasswordField passwordFieldPIN;
	@FXML private Button buttonEnterPIN;
	@FXML private VBox vBoxWelcome;
	@FXML private VBox vBoxAccountLocked;
	@FXML private VBox vBoxWrongPIN;	
	@FXML private Label labelEnterPin;
	
	
	//////////////////////// This section contains all of the constants and variables used in the Login Controller. ///////////////////////
	
	// This is a label on the login window welcoming whoever swipes their debit/credit card. ---------------------------------
	public Label welcomeLabel;
	
	
	// These are constants used in the logging in process. ---------------------------------------------------------------------
	private static final String NULLSTRING = "";
	private static final int NONE_SELECTED = -1;
	private static final int ENTER_STRING = 1;
	private static final int WRONG_PIN = 2;
	private static final int ACCOUNT_LOCKED = 3;
	
	
	// These are variables used in the logging in process. --------------------------------------------------------------------
	private int customerID = NONE_SELECTED;
	private String targetCustomer = NULLSTRING;
	private String targetCustomerID = NULLSTRING;
	private String currentGreeting = NULLSTRING;
	private ImageView currentCard;
	private HashMap<Integer, Integer> pinAttempts;
	
	// This is necessary for the timer. ---------------------------------------------------------------------------------------
	private static final Duration DELAY = Duration.seconds(1);
	private final Timeline wrongPINTimer = new Timeline(
			new KeyFrame(DELAY,
						 actionEvent -> {
							 if(!vBoxAccountLocked.isVisible())
								 setStatusIndicatorsToNormal();
						 }
	));
	
	
	///////////////////////////////// This section contains all of the Login Controller's methods. ////////////////////////////////////////
	
	
	@FXML
	public void initialize() {
		
		pinAttempts = new HashMap<>(6);
	
		passwordFieldPIN.setOnKeyPressed(
				arg0 -> {
					if(arg0.getCode() == KeyCode.ENTER) {
						try {
							buttonEnterPINClicked();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				});
		
	}
	
	
	// This handles the Chamale Card being clicked on in the Login Window. -----------------------------------------------------
	public void ChamaleCardClicked() {
		
		resetDisplay();
		if(DatabaseOps.CustomerIsLockedOut(DatabaseOps.getCustomerID("Chamale"))) {
			disableCard("Chamale");
			customerLockedOutDisplay();
			imageViewChamale.setEffect(boxBlur);
			return;
		}
		currentCard = imageViewChamale;
		currentGreeting = "Hi Rosalinda!";
		welcomeLabel.setText(currentGreeting);
		targetCustomer = "Chamale";
		customerID = DatabaseOps.getCustomerID(targetCustomer);
		if(!pinAttempts.containsKey(customerID))
			pinAttempts.put(customerID, 0);
		
	}
	
	
	// This handles the Zhang Card being clicked on in the Login Window. -------------------------------------------------------
	public void ZhangCardClicked() {
		
		resetDisplay();
		if(DatabaseOps.CustomerIsLockedOut(DatabaseOps.getCustomerID("Zhang"))) {
			disableCard("Zhang");
			customerLockedOutDisplay();
			imageViewZhang.setEffect(boxBlur);
			return;
		}
		currentCard = imageViewZhang;
		currentGreeting = "Hi Anthony!";
		welcomeLabel.setText(currentGreeting);
		targetCustomer = "Zhang";
		customerID = DatabaseOps.getCustomerID(targetCustomer);
		if(!pinAttempts.containsKey(customerID))
			pinAttempts.put(customerID, 0);
		
	}
	
	
	// This handles the Sepulveda Card being clicked on in the Login Window. ---------------------------------------------------
	public void SepulvedaCardClicked() {
		
		resetDisplay();
		if(DatabaseOps.CustomerIsLockedOut(DatabaseOps.getCustomerID("Sepulveda"))) {
			disableCard("Sepulveda");
			customerLockedOutDisplay();
			imageViewSepulveda.setEffect(boxBlur);
			return;
		}
		currentCard = imageViewSepulveda;
		currentGreeting = "Hi Anthony!";
		welcomeLabel.setText(currentGreeting);
		targetCustomer = "Sepulveda";
		customerID = DatabaseOps.getCustomerID(targetCustomer);
		if(!pinAttempts.containsKey(customerID))
			pinAttempts.put(customerID, 0);
		
	}
	
	
	// This handles the Medina Card being clicked on in the Login Window. ------------------------------------------------------
	public void MedinaCardClicked() {
		
		resetDisplay();
		if(DatabaseOps.CustomerIsLockedOut(DatabaseOps.getCustomerID("Medina"))) {
			disableCard("Medina");
			customerLockedOutDisplay();
			imageViewMedina.setEffect(boxBlur);
			return;
		}
		currentCard = imageViewMedina;
		currentGreeting = "Hi Dexxer!";
		welcomeLabel.setText(currentGreeting);
		targetCustomer = "Medina";
		customerID = DatabaseOps.getCustomerID(targetCustomer);
		if(!pinAttempts.containsKey(customerID))
			pinAttempts.put(customerID, 0);
		
	}
	
	
	// This handles the "PIN Enter" button being clicked in the Login Window. ----------------------------------------------------
	public void buttonEnterPINClicked() throws InterruptedException {
		
		targetCustomerID = Integer.toString(customerID);
		
		// If either a card wasn't clicked or a password wasn't entered then do nothing.
		if(customerID == NONE_SELECTED) {
			welcomeLabel.setTextFill(Color.RED);
			welcomeLabel.setText("ERROR");
			labelEnterPin.setTextFill(Color.RED);
			labelEnterPin.setText("Please Enter Card");
			return;
		}
		
		if(passwordFieldPIN.getText() == NULLSTRING) return;
		
		try {
			// If the PIN is correct then advance to the ATM window.
			if(checkPIN(Integer.parseInt(passwordFieldPIN.getText()))) {
				openATMScreen(customerID);
				return;
			}

		} catch(NumberFormatException num) {
			// Necessary in case the customer enters an invalid string, so treat it as wrong PIN.
		}
		
		// If the PIN is incorrect then permit the user another attempt.
		setStatusIndicators(WRONG_PIN);
		passwordFieldPIN.setText(NULLSTRING);
					
		// Increment the number of PIN attempts.
		int numPINAttempts = pinAttempts.get(customerID) + 1;
		pinAttempts.put(customerID, numPINAttempts);
			
		// If the PIN attempts is 3 then lock customer out of the ATM.
		if(numPINAttempts >= 3) lockCustomerOut();
			
		
	}


	// This sets the status indicators for locking the customer out of the ATM. ----------------------------------------------------
	private void customerLockedOutDisplay() {
		customerID = NONE_SELECTED;
		welcomeLabel.setTextFill(Color.RED);
		labelEnterPin.setTextFill(Color.RED);
		welcomeLabel.setText("Locked Card");
		buttonEnterPIN.setDisable(true);
		passwordFieldPIN.setDisable(true);
		labelEnterPin.setText("Advise Bank Teller");
	}
	
	
	// This resets the window to its defaults. ------------------------------------------------------------------------------------
	private void resetDisplay() {
		welcomeLabel.setTextFill(Color.BLACK);
		labelEnterPin.setTextFill(Color.BLACK);
		buttonEnterPIN.setDisable(false);
		passwordFieldPIN.setDisable(false);
		labelEnterPin.setText("Please Enter PIN");
		setStatusIndicatorsToNormal();
	}
	
	
	
	// This locks a customer out of the ATM. --------------------------------------------------------------------------------------
	public void lockCustomerOut() {
		setStatusIndicators(ACCOUNT_LOCKED);
		disableCard(targetCustomer);
		customerLockedOutDisplay();
		currentCard.setEffect(boxBlur);
		DatabaseOps.lockCustomerOutOfATM(targetCustomerID);

	}
	
	
	
	// This blurs out the customer's card if he or she is locked out of the ATM. -----------------------------------------------
	BoxBlur boxBlur = new BoxBlur();
	
	
	
	// This sets the status indicators --------------------------------------------------------------------------------------------
	public void setStatusIndicators(int status) {
				
		switch(status) {
		
			case ENTER_STRING: // This sets the greeting to either welcome or hi John Doe.
				if(customerID == NONE_SELECTED) welcomeLabel.setText("Welcome");
				else welcomeLabel.setText(currentGreeting);
				vBoxWelcome.setVisible(true);
				break;
				
			case WRONG_PIN: // This sets the status indicator to wrong pin.
				makeStatusIndicatorsInvisible();
				vBoxWrongPIN.setVisible(true);
				handleWrongPINTimer();
				break;
				
			case ACCOUNT_LOCKED: // This sets the status indicator to account locked.
				makeStatusIndicatorsInvisible();
				vBoxAccountLocked.setVisible(true);
				break;
		}
		
	}


	
	// This sets the status indicators back to normal when the password field is entered. ----------------------------------------
	public void setStatusIndicatorsToNormal() {
		if(vBoxWrongPIN.isVisible() || vBoxAccountLocked.isVisible()) {
			makeStatusIndicatorsInvisible();
			setStatusIndicators(ENTER_STRING);
		}	
	}



	// This sets all status indicators to invisible. -----------------------------------------------------------------------------
	public void makeStatusIndicatorsInvisible() {
		vBoxWelcome.setVisible(false);
		vBoxWrongPIN.setVisible(false);
		vBoxAccountLocked.setVisible(false);
	}
	
	
	
	// This checks to see it the input PIN is correct. ---------------------------------------------------------------------------
	public boolean checkPIN(int inputPIN) {
		int storedPIN = DatabaseOps.getStoredPIN(targetCustomerID);
		if(inputPIN == storedPIN) return true;
		else return false;
	}
	
	
	
	// This disables cards on the window for locked-out customers.  -------------------------------------------------------------
	public void disableCard(String targetCustomer) {
		
		switch(targetCustomer) {
		case "Chamale":
			VBoxChamale.setDisable(true);
			break;
		case "Zhang":
			VBoxZhang.setDisable(true);
			break;
		case "Sepulveda":
			VBoxSepulveda.setDisable(true);
			break;
		case "Medina":
			VBoxMedina.setDisable(true);
			break;	
		}
		
	}
	
	
	
	// This opens the ATM screen. ----------------------------------------------------------------------------------------------
	public void openATMScreen(int customerID) {
		
		try {
			
			Stage window = (Stage)(imageViewChamale.getScene().getWindow());
			window.hide();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ATM Window.fxml"));
			Parent atmWindow = loader.load();
			ATMController controller = loader.getController();
			controller.setup(customerID);
			Scene atmScene = new Scene(atmWindow);
			
			window.setScene(atmScene);
			window.centerOnScreen();
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// This opens the Operator screen. ----------------------------------------------------------------------------------------
	public void openOperatorScreen() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Operator Window.fxml"));
			Parent operatorWindow = loader.load();
			Scene operatorScene = new Scene(operatorWindow);
			Stage window = (Stage)(imageViewChamale.getScene().getWindow());
			window.setScene(operatorScene);
			window.centerOnScreen();
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// This handles the timer for Wrong PIN status indicator. -----------------------------------------------------------------
	private void handleWrongPINTimer() {
		if(wrongPINTimer.getStatus() == Animation.Status.STOPPED) wrongPINTimer.play();
		else wrongPINTimer.playFromStart();
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
} // End of Login Controller Class. ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
