package application;


/////////////////////////////////////// These are imports needed for the Operator Controller. //////////////////////////////////////////////////
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//This controls the events triggered by GUI action in the Operator Window. /////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class OperatorController {

	
	///////////////////////////////////// This section contains all of the FXML injections. ////////////////////////////////////////////////////
	
	// These concern controls not on vBoxOptions or paneOperatorLogin (the two subforms). ------------------------------------------------------
    @FXML private Label labelDate;
    @FXML private Label labelCustomer;
    @FXML private Label labelAccountNumber;
    @FXML private Label labelAccessDenied;
    @FXML private VBox vBoxOptions;
    @FXML private Button buttonLogout;
    @FXML private TabPane tabPaneOperator;
    @FXML private Tab tabCustomer;
    @FXML private Tab tabAccount;
    @FXML private Tab tabTransactions;
    
    // These concern the controls on the Customer Tab of the vBoxOptions subform. --------------------------------------------------------------
    @FXML private Label labelCustomerTabCustomer;
    @FXML private Label labelCustomerTabCustomerID;
    @FXML private Label labelCustomerTabDOB;
    @FXML private Label labelCustomerTabHomeAddress;
    @FXML private Label labelCustomerTabPhoneNumber;
    @FXML private Label labelCustomerTabEmailAddress;
    @FXML private Label labelCustomerTabATMAccess;
    @FXML private ListView<String> listViewCustomerTabCustomers;
    @FXML private Button buttonCustomerTabRestoreAccess;
    @FXML private Button buttonSelectCustomer;

    // These concern the controls on the Account Tab of the vBoxOptions subform. -------------------------------------------------------------------
    @FXML private Label labelAccountTabAccountNumber;
    @FXML private Label labelAccountTabNickname;
    @FXML private Label labelAccountTabType;
    @FXML private Label labelAccountTabCreationDate;
    @FXML private Label labelAccountTabAssociatedCard;
    @FXML private Label labelAccountTabAccountFrozen;
    @FXML private Label labelAccountTabBalance;
    @FXML private ListView<String> listViewAccountTabAccounts;
    @FXML private Button buttonAccountTabFreezeAccount;
    @FXML private Button buttonAccountTabRecoverAccount;

    
    // These concern the controls on the Transactions Tab of the vBoxOptions subform. ----------------------------------------------------------------
    @FXML private ListView<String> listViewTransactionsTabDeposits;
    @FXML private ListView<String> listViewTransactionsTabWithdrawals;
    @FXML private ListView<String> listViewTransactionsTabTransfers;

    
    // These concern the controls on the paneOperatorLogin. -------------------------------------------------------------------------------------------
    @FXML private Pane paneOperatorLogin;
    @FXML private Label welcomeLabel;
    @FXML private ComboBox<Integer> comboBoxOperator;
    @FXML private PasswordField passwordFieldPassword;
    @FXML private Button buttonLogin;
    @FXML private VBox vBoxOperatorLogin;
    
    
//////////////////////////// This section contains all of the constants and variables used in the Operator Controller. ///////////////////////////////
    
    
    // These are array lists used in Operator Controller --------------------------------------------------------------------------------------------
    private ArrayList<Integer> operatorIDs = new ArrayList<Integer>();
    private ArrayList<String> customerNames = new ArrayList<String>();
    private ArrayList<String> accountIDs = new ArrayList<String>();
    
	// These are constants used in the Operator Controller. ------------------------------------------------------------------------------------------
	private static final String NULL_STRING = "";
	private static final int NONE_SELECTED = -1;
    
    
	// These are customer-related variables. ---------------------------------------------------------------------------------------------------------
	private String customerFullName = NULL_STRING;
	private int customerID = NONE_SELECTED;
	private String[] nameOfCustomerSplit = new String[2];

	
	// These are account-related variables. ---------------------------------------------------------------------------------------------------------
    private String accountSelected;
    int accountID = NONE_SELECTED;
    String accountNickname = NULL_STRING;
    String accountType = NULL_STRING;
    String accountCreationDate = NULL_STRING;
    int accountAssociatedCard = NONE_SELECTED;
    int accountFrozen = NONE_SELECTED;
    float accountBalance = NONE_SELECTED;
	
	
    // These are operator-related variables. -----------------------------------------------------------------------------------------------------------
	int operatorID = NONE_SELECTED;
    String operatorName = NULL_STRING;
    boolean initialized = false;
    

	///////////////////////////////////////// This section contains all of the Operator Controller's methods. ///////////////////////////////////////////
    

    // This handles a Customer being selected from the Customer list on the Customer tab. ---------------------------------------------------------------
	public void Customer() {
		
    	String firstName = DatabaseOps.returnCustomerInfo(customerID, "firstname");
    	String lastName = DatabaseOps.returnCustomerInfo(customerID, "lastname");
    	String dob = DatabaseOps.returnCustomerInfo(customerID, "date");
    	String address = DatabaseOps.returnCustomerInfo(customerID, "address");
    	String phoneNumber = DatabaseOps.returnCustomerInfo(customerID, "phone");
    	String email = DatabaseOps.returnCustomerInfo(customerID, "email");
    	String atmAccess = DatabaseOps.returnCustomerInfo(customerID, "access");
    	
    	labelCustomerTabCustomer.setText(lastName + ", " + firstName);
    	labelCustomerTabCustomerID.setText(Integer.toString(customerID));
    	labelCustomerTabDOB.setText(dob);
    	labelCustomerTabHomeAddress.setText(address);
    	labelCustomerTabPhoneNumber.setText(phoneNumber);
    	labelCustomerTabEmailAddress.setText(email);
    	labelCustomerTabATMAccess.setText(atmAccess);
    		
    	if (atmAccess.equals("true")) {
    		labelCustomerTabATMAccess.setText("Yes");
    		buttonCustomerTabRestoreAccess.setDisable(true);
    	}
    	else {
    		labelCustomerTabATMAccess.setText("No");
    		buttonCustomerTabRestoreAccess.setDisable(false);
    	}
    		
    }
	
    	


    // This handles an Account being selected from the Accounts list on the Account tab. -----------------------------------------------------------
    public void Account() {
    	
    	String nickname = DatabaseOps.returnAccountInfo(accountID, customerID, "nickname");
    	String type = DatabaseOps.returnAccountInfo(accountID, customerID, "type");
    	String creationDate = DatabaseOps.returnAccountInfo(accountID, customerID, "date");
    	String cardID = DatabaseOps.returnAccountInfo(accountID, customerID, "card");
    	String frozenStatus = DatabaseOps.returnAccountInfo(accountID, customerID, "status");
    	String balance = DatabaseOps.returnAccountInfo(accountID, customerID, "balance");
	
    	labelAccountTabAccountNumber.setText(Integer.toString(accountID));
    	labelAccountTabNickname.setText(nickname);
    	labelAccountTabType.setText(type);
    	labelAccountTabCreationDate.setText(creationDate);
    	labelAccountTabAssociatedCard.setText(cardID);
    	labelAccountTabAccountFrozen.setText(frozenStatus);
    	labelAccountTabBalance.setText(balance);
    	
    	if (frozenStatus.equals("No")) {
    		buttonAccountTabFreezeAccount.setDisable(false);
    		buttonAccountTabRecoverAccount.setDisable(true);
    	}
    	else {
    		buttonAccountTabFreezeAccount.setDisable(true);
    		buttonAccountTabRecoverAccount.setDisable(false);
    	}
	
	}




    // This handles populating the Deposits list on the Transactions tab. --------------------------------------------------------------------------
    public void deposits() {
    	ArrayList<String> entries = new ArrayList<String>();
    	entries = DatabaseOps.returnAllDepositsOperator(accountID);
    	for(int i = 0; i < entries.size(); i++) {
        	listViewTransactionsTabDeposits.getItems().add(entries.get(i));
    	}
    }




    //  This handles populating the Withdrawals list on the Transactions tab. -----------------------------------------------------------------------
    public void withdrawals() {
    	ArrayList<String> entries = new ArrayList<String>();
    	entries = DatabaseOps.returnAllWithdrawalsOperator(accountID);
    	for(int i = 0; i < entries.size(); i++) {
        	listViewTransactionsTabWithdrawals.getItems().add(entries.get(i));
    	}
    }

    

    // This handles populating the Transfers list on the Transactions tab. ---------------------------------------------------------------------------
    public void transfers() {
    	ArrayList<String> entries = new ArrayList<String>();
    	entries = DatabaseOps.returnAllTransfersOperator(accountID);
    	for(int i = 0; i < entries.size(); i++) {
        	listViewTransactionsTabTransfers.getItems().add(entries.get(i));
    	}
    }


    
    // This handles the Restore Access button on the Customer tab being clicked. -------------------------------------------------------------------------
	public void restoreAccessButtonClicked() {
		DatabaseOps.lockedStatus(customerID);
		labelCustomerTabATMAccess.setText("No");
		buttonCustomerTabRestoreAccess.setDisable(true);
	}

	
	
    // This handles the Freeze Account button on the Account tab being clicked. ---------------------------------------------------------------------------
	public void freezeAccountButtonClicked() {
		DatabaseOps.frozenStatus(accountID);
		labelAccountTabAccountFrozen.setText("Yes");
		buttonAccountTabFreezeAccount.setDisable(true);
		buttonAccountTabRecoverAccount.setDisable(false);
	}

	

    // This handles the Recover Account button on the Account tab being clicked. -------------------------------------------------------------------------
	public void recoverAccountButtonClicked() {
		DatabaseOps.recoverAccountStatus(accountID);
		labelAccountTabAccountFrozen.setText("No");
		buttonAccountTabFreezeAccount.setDisable(false);
		buttonAccountTabRecoverAccount.setDisable(true);
	}

	

    // This handles the Login button on the Operator Login pane being clicked. ---------------------------------------------------------------------------
	public void LoginButtonClicked() {
		
		// If either the combobox or password fields are empty, ignore
		if(comboBoxOperator.getItems().isEmpty()) return;
		if(passwordFieldPassword.getText() == null) return;
		
		// Operator get's another attempt at password entry
		if(!checkPass(passwordFieldPassword.getText())) {
			labelAccessDenied.setVisible(true);
			welcomeLabel.setText("Wrong password, try again.");
		}
		
		// If the pass is right then set the other options to visible
		else {
			vBoxOptions.setVisible(true);
			labelAccessDenied.setVisible(false);
			welcomeLabel.setText("Access Granted, " + operatorName);
			buttonLogout.setDisable(false);
			passwordFieldPassword.clear();
			vBoxOperatorLogin.setDisable(true);
			paneOperatorLogin.setVisible(false);

			tabPaneOperator.getSelectionModel().select(tabCustomer);

	    	// For some reason the freeze account button is initialized to be clicked. This solves that problem
	    	buttonAccountTabFreezeAccount.setDisable(true);
	    	buttonAccountTabRecoverAccount.setDisable(true);
	    	
	    	// Disable the Account/Transactions tabs for aesthetic reasons
	    	tabAccount.setDisable(true);
	    	tabTransactions.setDisable(true);
		}	
	}
	
	
	
	// Helper to LoginButtonClicked that checks operator password
	public boolean checkPass(String password) {
		String storedPass = DatabaseOps.getStoredOperatorPass(operatorID);
		if(password.equals(storedPass)) return true;
		else return false;
	}    

    

    // This handles the initialization of the Operator Window. --------------------------------------------------------------------------------------------	
    public void initialization() {
    	
    	// Click customer icon to initialize methods including getting the current date
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		String currentDate = sdf.format(date);
		labelDate.setText(currentDate);
    	
    	
    	// Operator login steps
    	if (!initialized) {    	
	    	// Set the appropriate window visible
	    	paneOperatorLogin.setVisible(true);
	    	
	    	// Get the available operatorIDs for the comboBox
	    	operatorIDs = DatabaseOps.getOperatorID();
	    	
	    	// Add those IDs to the comboBox
	    	for(int i = 0; i < operatorIDs.size(); i++) {
	    		comboBoxOperator.getItems().add(operatorIDs.get(i));
	    	}
	    	
	    	// Change welcome label to operator's name when a combobox entry is clicked
	    	comboBoxOperator.setOnAction((event) -> {
	    	    operatorID = comboBoxOperator.getSelectionModel().getSelectedItem();
	    	    operatorName = DatabaseOps.getOperatorName(operatorID);
	    		welcomeLabel.setText("Hi there, " + operatorName);
	    	    
	    	});
	    	
	    	// This is the initialization for the Customer Tab
	    	// Populates Listview of Customer Tab with Customer names
	    	customerNames = DatabaseOps.returnCustomerNames();
	    	for( int i = 0; i < customerNames.size(); i++) {
	    		listViewCustomerTabCustomers.getItems().add(customerNames.get(i));
	    	}
	    	initialized = true;
        }	    	
    }
    
    
    
    
    // This initializes the Account Tab. ----------------------------------------------------------------------------------------------------
    public void accountTab() {
    	if(!listViewAccountTabAccounts.getItems().isEmpty()) {
			listViewAccountTabAccounts.getItems().clear();
		}  	
    	accountIDs = DatabaseOps.returnAccountIDs(customerID);
    	for( int i = 0; i < accountIDs.size(); i++) {
    		listViewAccountTabAccounts.getItems().add(accountIDs.get(i));
    	}
    }
    
    
    // This handles what occurs when the Select Account Button is clicked. ---------------------------------------------------------------------
    public void selectAccountButtonClicked() {
    	
    	if (listViewAccountTabAccounts.getSelectionModel().isEmpty()) return;

		accountSelected = listViewAccountTabAccounts.getSelectionModel().getSelectedItem();
		accountID = Integer.parseInt(accountSelected);
    	Account();
    	deposits();
    	withdrawals();
    	transfers();
    	labelAccountTabAccountNumber.setVisible(true);
    	labelAccountTabNickname.setVisible(true);
    	labelAccountTabType.setVisible(true);
    	labelAccountTabCreationDate.setVisible(true);
    	labelAccountTabAssociatedCard.setVisible(true);
    	labelAccountTabAccountFrozen.setVisible(true);
    	labelAccountTabBalance.setVisible(true);
    	
    	// Enable the transaction tab
    	tabTransactions.setDisable(false);
    	
    }
   
    
    
    // This handles what occurs when the Select Customer Button is clicked. -----------------------------------------------------------------------
    public void selectCustomerButtonClicked() {
    	
    	clearSummaries();
    	if (listViewCustomerTabCustomers.getSelectionModel().isEmpty()) return;
    	customerFullName = listViewCustomerTabCustomers.getSelectionModel().getSelectedItem();
    	nameOfCustomerSplit = customerFullName.split(" ", 2);
    	customerID = DatabaseOps.getCustomerIDFromFullName(nameOfCustomerSplit[0],nameOfCustomerSplit[1]);
    	labelCustomer.setText(customerFullName);
    	labelAccountNumber.setText(String.format("%09d", DatabaseOps.returnAccountID(customerID, "Checking"))); 
    	Customer();
    	accountTab();
    	labelCustomer.setVisible(true);
    	labelAccountNumber.setVisible(true);
    	labelCustomerTabCustomer.setVisible(true);
    	labelCustomerTabCustomerID.setVisible(true);
    	labelCustomerTabDOB.setVisible(true);
    	labelCustomerTabHomeAddress.setVisible(true);
    	labelCustomerTabPhoneNumber.setVisible(true);
    	labelCustomerTabEmailAddress.setVisible(true);
    	labelCustomerTabATMAccess.setVisible(true);
    	
    	// When switching between customers, previous customer account info was displayed when switching into account tab for first time.
    	// This sets all those labels to be invisible until an account is selected again.
    	labelAccountTabAccountNumber.setVisible(false);
    	labelAccountTabNickname.setVisible(false);
    	labelAccountTabType.setVisible(false);
    	labelAccountTabCreationDate.setVisible(false);
    	labelAccountTabAssociatedCard.setVisible(false);
    	labelAccountTabAccountFrozen.setVisible(false);
    	labelAccountTabBalance.setVisible(false);
    	
    	// Enable the account tab
    	tabAccount.setDisable(false);
    	
    }
	
    
    
    // This is to clear the Transaction Tab's info when switching accounts
    public void clearSummaries() {
    	listViewTransactionsTabDeposits.getItems().clear();
    	listViewTransactionsTabWithdrawals.getItems().clear();
    	listViewTransactionsTabTransfers.getItems().clear();
    }
   

    
	// This handles the Logout button being clicked. ----------------------------------------------------------------------------------------------------
	public void buttonLogoutClicked() {
		
		welcomeLabel.setText("Successfully logged out.");
		vBoxOptions.setVisible(false);
		buttonLogout.setDisable(true);
		vBoxOperatorLogin.setDisable(false);
		paneOperatorLogin.setVisible(true);
		labelAccountNumber.setVisible(false);
		labelCustomer.setVisible(false);
		
		// Reset the customer info labels
    	labelCustomer.setVisible(false);
    	labelAccountNumber.setVisible(false);
    	labelCustomerTabCustomer.setVisible(false);
    	labelCustomerTabCustomerID.setVisible(false);
    	labelCustomerTabDOB.setVisible(false);
    	labelCustomerTabHomeAddress.setVisible(false);
    	labelCustomerTabPhoneNumber.setVisible(false);
    	labelCustomerTabEmailAddress.setVisible(false);
    	labelCustomerTabATMAccess.setVisible(false);
    	
	}  
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
} // End of OperatorController class. //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
