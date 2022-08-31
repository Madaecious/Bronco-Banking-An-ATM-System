package application;


/////////////////////////////////////// These are imports needed for the ATM Controller. ////////////////////////////////////////////////////
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This controls the events triggered by GUI action in the ATM Window. /////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ATMController {
	
	
	///////////////////////////////////// This section contains all of the FXML injections. ///////////////////////////////////////////////
	
	
	// These concern the Customer's Specifics. -------------------------------------------------------------------------------
	@FXML private Label labelCustomerName;
	@FXML private Label labelCustomerIDNumber;
	
	
	// This is the Date and Time label. ---------------------------------------------------------------------------------------
	@FXML private Label labelDate;
	
	
	// These concern Account Specifics. ----------------------------------------------------------------------------------------
	@FXML private ImageView imageViewLogo;
	@FXML private Label lblAccountNumber;
	@FXML private Label lblAccountNickname;
	@FXML private Label lblConnectedCard;
	@FXML private Label lblAccountType;
	@FXML private Label lblAccountStatus;
	@FXML private VBox vBoxAccountLocked;
	@FXML private VBox vBoxAccountFrozen;
	@FXML private VBox vBoxDestinationAccountFrozen;
	@FXML private HBox hBoxAccountGlance;
	
	
	// These concern Recent Activity. -------------------------------------------------------------------------------------------
	@FXML private Label lblDepositsThisMonth;
	@FXML private Label lblWithdrawalsThisMonth;
	@FXML private Label lblTranfersThisMonth;
	@FXML private Label lblAvailableBalance;
	
	
	// These concern the Options. ----------------------------------------------------------------------------------------------
	@FXML private VBox vBoxOptions;
	@FXML private Label labelSelectAccount;
	@FXML private Label labelMakeDeposit;
	@FXML private Label labelMakeWithdrawal;
	@FXML private Label labelMakeAccountTransfer;
	@FXML private Label labelViewAccountSummary;
	@FXML private Label labelRequestAccountRecovery;
		
	
	// These concern the Account Summary. ---------------------------------------------------------------------------------------
	@FXML private HBox hBoxViewAccountSummary;
	@FXML private VBox vBoxAccountSummary;
	@SuppressWarnings("rawtypes")
	@FXML private ListView listViewDeposits;
	@SuppressWarnings("rawtypes")
	@FXML private ListView listViewWithdrawals;
	@SuppressWarnings("rawtypes")
	@FXML private ListView listViewTransfers;
	@FXML private Label labelDepositsTotal;
	@FXML private Label labelWithdrawalsTotal;
	@FXML private Label labelTransfersTotal;
	
	
	// These concern the Accounts to select from. -------------------------------------------------------------------------------
	@FXML private VBox vBoxSelectAccount;
	@FXML private ListView<String> listViewAccounts;
	
	
	// These concern the Amount to Transfer. ------------------------------------------------------------------------------------
	@FXML private HBox hBoxMakeAccountTransfer;
	@FXML private HBox hBoxTransferAmount;
	@FXML private TextField textFieldTransferAmount;
	@FXML private Label labelTransferSuccessful;
	@FXML private ListView<String> listViewDestination;
	
	
	// These concern the Amount to Deposit. -------------------------------------------------------------------------------------
	@FXML private HBox hBoxMakeDeposit;
	@FXML private VBox vBoxDepositAmount;
	@FXML private TextField textFieldDepositAmount;
	@FXML private Label labelDepositSuccessful;
	
	
	// These concern the Receipt Question. ---------------------------------------------------------------------------------------
	// @FXML private HBox hBoxReceiptInfo;
	@FXML private VBox vBoxRecieptInfo;
	@FXML private VBox vBoxThankYou;
	@FXML private Label labelEmailAddress;
	
	
	// These concern the Amount to Withdraw. -------------------------------------------------------------------------------------
	@FXML private HBox hBoxMakeWithdrawal;
	@FXML private VBox vBoxWithrawalAmount;
	@FXML private Label labelInsufficientFunds;
	@FXML private TextField textFieldWithdrawalAmount;
	@FXML private Button buttonConfirmWithdrawal;
	
	
	// This concerns the Request to Recover Account. ------------------------------------------------------------------------------
	@FXML private HBox hBoxRequestAccountRecovery;
	@FXML private VBox vBoxRequestToRecoverAccount;
	@FXML private Label labelAccountToRecover;
	
	
	// This concerns Finished. ----------------------------------------------------------------------------------------------------
	@FXML private HBox vBoxFinished;	
	
	
	//////////////////////// This section contains all of the constants and variables used in the ATM Controller. /////////////////////////
	
	
	// These are constants used in the ATM Controller. -----------------------------------------------------------------------------
	private static final String NULL_STRING = "";
	private static final int NONE_SELECTED = -1;
	
	
	// These are the variables used in the ATM Controller. --------------------------------------------------------------------------
	private String customerName = NULL_STRING;
	private int customerID = NONE_SELECTED;
	
	
	// These are variables related to the account. ----------------------------------------------------------------------------------
	private int accountID = NONE_SELECTED;
	private String accountNickname = NULL_STRING;
	private int accountCard = NONE_SELECTED;
	private String accountType = NULL_STRING;
	private int accountStatus = NONE_SELECTED;
	
	
	// This is the array list for holding all of a customer's accounts. ---------------------------------------------------------------
	private ArrayList<String> accounts = new ArrayList<String>();
	
	
	// This is the variable that holds the account an amount will be transferred to. --------------------------------------------------
	private int transferAccountID = NONE_SELECTED;
	
	
	// These are the array lists for holding all of the deposits, withdrawals, and transfers of an account. ---------------------------
	private ArrayList<String> deposits = new ArrayList<String>();
	private ArrayList<String> withdrawals = new ArrayList<String>();
	private ArrayList<String> transfers = new ArrayList<String>();
	
	
	// These are bottom line variables. -----------------------------------------------------------------------------------------------
	private float depositsThisMonth = NONE_SELECTED;
	private float withdrawalsThisMonth = NONE_SELECTED;
	private float transfersThisMonth = NONE_SELECTED;
	private float accountBalance = NONE_SELECTED;
	private float totalDepositsSum = NONE_SELECTED;
	private float totalWithdrawalsSum = NONE_SELECTED;
	private float totalTransfersSum = NONE_SELECTED;
	
	
	// This variable indicates whether a transaction has been made. --------------------------------------------------------------------
	private boolean transactionMade = false;
	
	
	///////////////////////////////// This section contains all of the ATM Controller's methods. //////////////////////////////////////////
	
	
	// This initializes the ATM Window with the logged-in customer's information. ------------------------------------------------------
	public void setup(int loggedInCustomerID) {
		
		customerID = loggedInCustomerID;
		
		// This sets the labels for the customer's name and ID.
		customerName = DatabaseOps.getCustomerName(customerID);
		labelCustomerName.setText(customerName);
		labelCustomerIDNumber.setText(String.format("ID # %09d", customerID));
	
		// This sets the date label.
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
		String currentDate = sdf.format(date);
		labelDate.setText(currentDate);
		
		// This gets the customer's checking account number.
		accountID = DatabaseOps.returnCheckingAccount(customerID);
		
		// This populates the account list view with the customer's accounts.
		populateAccountListView();
		
		// This retrieves all the information associated with the customer's checking account.
		getAccountData();
		
		// This sets all of the labels and list views on the form.
		populateAccountSummaryListViews();
		setAccountSummaryLabels();
		populateDestinationAccount();
		updateAllBottomLines();
		
		// This makes the Select Account subform visible.
		selectAccountLabelClicked();
		
		
		// This handles frozen accounts.
		addressFrozenAccounts();
	}
	
	
	
	// This addresses frozen account functionality. ----------------------------------------------------------------------------
	public void addressFrozenAccounts() {
		
		if(accountStatus == 0) {
			labelMakeDeposit.setDisable(false);
			labelMakeWithdrawal.setDisable(false);
			labelMakeAccountTransfer.setDisable(false);
			labelViewAccountSummary.setDisable(false);
		}
		setAccountRecoveryLabel();
		
	}
	
	
	
	// This sets the account recovery label to either disabled or enabled. -----------------------------------------------------
	public void setAccountRecoveryLabel() {
		
		// 1 means account is frozen or recovery request was sent, 0 means account is active.
		if(accountStatus == 0) labelRequestAccountRecovery.setDisable(true);
		else labelRequestAccountRecovery.setDisable(false);
		
	}
	
	

	// This handles the Select Account button being clicked. -------------------------------------------------------------------
	public void selectAccountButtonClicked() {
		
		listViewDeposits.getItems().clear();
		listViewWithdrawals.getItems().clear();
		listViewTransfers.getItems().clear();
		accountNickname = listViewAccounts.getSelectionModel().getSelectedItem();
		accountID = DatabaseOps.returnAccountIDFromNickname(customerID, accountNickname);
		getAccountData();
		populateAccountSummaryListViews();
		setAccountSummaryLabels();
		populateDestinationAccount();
		updateAllBottomLines();
		addressFrozenAccounts();	
		
	}
	
	
	
	// This gets all of the values from the database associated with the selected account. -------------------------------------
	public void getAccountData() {
		
		accountNickname = DatabaseOps.returnAccountNickname(accountID);
		accountCard  = DatabaseOps.returnAccountCard(accountID);
		accountType = DatabaseOps.returnAccountType(accountID);
		accountStatus = DatabaseOps.returnAccountStatus(accountID);
		deposits = DatabaseOps.getAllDeposits(accountID);
		withdrawals = DatabaseOps.getAllWithdrawals(accountID);
		transfers = DatabaseOps.getAllTransfers(accountID);
		depositsThisMonth = DatabaseOps.getThisMonthsDepositsSum(accountID);
		withdrawalsThisMonth = DatabaseOps.getThisMonthsWithdrawalsSum(accountID);
		transfersThisMonth = DatabaseOps.getThisMonthsTransfersSum(accountID);
		accountBalance = DatabaseOps.returnBalance(accountID);
		
	}
	
	
	
	
	
	// This populates the accounts list view. ---------------------------------------------------------------------------------
	public void populateAccountListView() {
		accounts = DatabaseOps.returnAccountNicknames(customerID);
		for(int i = 0; i < accounts.size(); i++) {
			listViewAccounts.getItems().add(accounts.get(i));
		}
	}
	
	
	
	
	// This handles the Select Account label being clicked. -------------------------------------------------------------------
	public void selectAccountLabelClicked() {
		hideAllOptionContainers();
		vBoxSelectAccount.setVisible(true);
	}
	
	
	
	
	// This handles the Make Deposit label being clicked. ----------------------------------------------------------------------
	public void makeDepositLabelClicked() {
		hideAllOptionContainers();
		if(DatabaseOps.checkAccountFrozen(accountID)) vBoxAccountFrozen.setVisible(true);
		else vBoxDepositAmount.setVisible(true);
	}

	
	
	
	// This handles the Make Withdrawal label being clicked. -------------------------------------------------------------------
	public void makeWithdrawalLabelClicked() {
		hideAllOptionContainers();
		if(DatabaseOps.checkAccountFrozen(accountID)) vBoxAccountFrozen.setVisible(true);
		else vBoxWithrawalAmount.setVisible(true);
	}

	
	
	
	// This handles the Make Account Transfer label being clicked. -------------------------------------------------------------
	public void MakeAccountTransferLabelClicked() {
		hideAllOptionContainers();	
		if(DatabaseOps.checkAccountFrozen(accountID)) vBoxAccountFrozen.setVisible(true);
		else hBoxTransferAmount.setVisible(true);
	}

	
	
	
	// This handles the View Account Summary label being clicked. --------------------------------------------------------------
	public void viewAccountSummaryLabelClicked() {
		hideAllOptionContainers();
		vBoxAccountSummary.setVisible(true);
	}


	
	
	// This handles the Request Account Recovery label being clicked. ----------------------------------------------------------
	public void requestAccountRecoveryLabelClicked() {
		hideAllOptionContainers();
		vBoxRequestToRecoverAccount.setVisible(true);
		labelAccountToRecover.setText(String.format("%06d", accountID));
		accountStatus = 0;
		labelRequestAccountRecovery.setDisable(true);
	}

	
	
	// This populates the transfer destination list view with every account other than currently selected account. -----------
	public void populateDestinationAccount() {
		
		if(!listViewDestination.getItems().isEmpty()) {
			listViewDestination.getItems().clear();
		}
		
		for(int i = 0; i < accounts.size(); i++) {
			if(!accounts.get(i).equals(accountNickname))
				listViewDestination.getItems().add(accounts.get(i));
		}
	}
	
	
	

	
	// This populates the Deposits, Withdrawals, and Transfers list views in the Account Summary. ------------------------------
	@SuppressWarnings("unchecked")
	public void populateAccountSummaryListViews() {
		
		Double numberValue = 0.0;
		String stringValue = NULL_STRING;
		
		// This first clears the list views of their previous values
	    listViewDeposits.getItems().clear();
	    listViewWithdrawals.getItems().clear();
	    listViewTransfers.getItems().clear();
		
		// This populates the deposits list view.
		for(int i = 0; i < deposits.size(); i++) {
			numberValue = Double.parseDouble(deposits.get(i));
			stringValue = String.format("%17s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(numberValue));
			listViewDeposits.getItems().add(stringValue);
		}
		
		
		// This populates the withdrawals list view.
		for(int i = 0; i < withdrawals.size(); i++) {
			numberValue = Double.parseDouble(withdrawals.get(i));
			stringValue = String.format("%17s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(numberValue));
			listViewWithdrawals.getItems().add(stringValue);
		}	
		
		// This populates the transfers list view.
		for(int i = 0; i < transfers.size(); i++) {
			numberValue = Double.parseDouble(transfers.get(i));
			stringValue = String.format("%17s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(numberValue));
			listViewTransfers.getItems().add(stringValue);
		}
		
	}	
	
	
	// This handles the Confirm Amount to Deposit button being clicked in the ATM Window. --------------------------------------
	public void selectAmountToDepositButtonClicked() {
		
			labelDepositSuccessful.setVisible(false);
			float amount = 0;
			
			// Accept only numbers.
			try {
				amount = Float.parseFloat(textFieldDepositAmount.getText());
			} 
			catch (NumberFormatException e) {
				textFieldDepositAmount.setText(NULL_STRING);
			    return;
			}
			
			// Accept only positive values.
			if(amount <= 0) {
				textFieldDepositAmount.setText(NULL_STRING);
			    return;
			}

			DatabaseOps.deposit(accountID, amount);
			transactionMade = true;
			labelDepositSuccessful.setVisible(true);
			// DatabaseOps.getThisMonthsDepositsSum(accountID);
			updateAllBottomLines();
			reloadAccountSummary();
			
	}
	
	
	
	// This handles the Transfer button being clicked. -------------------------------------------------------------------------
	public void transferAmountButtonClicked() {
			float amount = 0;
			
			// Accept only numbers.
			try {
				amount = Float.parseFloat(textFieldTransferAmount.getText());
			} 
			catch (NumberFormatException e) {
				textFieldTransferAmount.setText(NULL_STRING);
			    return;
			    
			}
			
			// Accept only positive values.
			if(amount <= 0) {
				textFieldTransferAmount.setText(NULL_STRING);
			    return;
			}
			
			// Check for sufficient funds.
			if(!sufficientFunds(accountID, Float.parseFloat(textFieldTransferAmount.getText()))) {
				// an insufficient funds indicator must display
				return;
			}
			
			String destNickname = listViewDestination.getSelectionModel().getSelectedItem();
			transferAccountID = DatabaseOps.returnAccountIDFromNickname(customerID, destNickname);
			
			//check if destination account is frozen
			if(DatabaseOps.checkAccountFrozen(transferAccountID))
				{
				hideAllOptionContainers();
				vBoxDestinationAccountFrozen.setVisible(true);
				}
			else {
				DatabaseOps.transfer(accountID, transferAccountID, amount);
				transactionMade = true;
				labelTransferSuccessful.setVisible(true);
				updateAllBottomLines();
				reloadAccountSummary();
			}
				
	}
	

	
	
	
	
	// This handles the Confirm Amount Withdrawal button being clicked. --------------------------------------------------------
	public void confirmAmountWithdrawalButtonClicked() {
			float amount = 0;
			
			// Accept only numbers.
			try {
				amount = Float.parseFloat(textFieldWithdrawalAmount.getText());
			} 
			catch (NumberFormatException e) {
				textFieldWithdrawalAmount.setText(NULL_STRING);
			    return;
			}

			// Accept only positive values.
			if(amount <= 0) {
				textFieldWithdrawalAmount.setText(NULL_STRING);
				return;
			}

			// Check for sufficient funds.
			if(!sufficientFunds(accountID, Float.parseFloat(textFieldWithdrawalAmount.getText()))) {
				// an insufficient funds indicator must display
				return;
			}			

			DatabaseOps.withdraw(accountID, amount);
			transactionMade = true;
			updateAllBottomLines();
			reloadAccountSummary();
	}
	

	
	
	
	// This handles the $100 withdrawal button being clicked. ------------------------------------------------------------------
	public void $100AmountToWithdrawButtonClicked() {
		labelInsufficientFunds.setVisible(false);
		if(sufficientFunds(accountID, (float) 100.0)) {
		DatabaseOps.withdraw(accountID, 100);
		updateAllBottomLines();
		} else labelInsufficientFunds.setVisible(true);
		
	}
	
	
	// This handles the $200 withdrawal button being clicked. ------------------------------------------------------------------
	public void $200AmountToWithdrawButtonClicked() {
		labelInsufficientFunds.setVisible(false);
		if(sufficientFunds(accountID, (float) 200.0)) {
		DatabaseOps.withdraw(accountID, 200);
		updateAllBottomLines();
		reloadAccountSummary();
		} else labelInsufficientFunds.setVisible(true);
		
	}
	
	
	
	// This handles the $500 withdrawal button being clicked. ------------------------------------------------------------------
	public void $500AmountToWithdrawButtonClicked() {
		labelInsufficientFunds.setVisible(false);
		if(sufficientFunds(accountID, (float) 500.0)) {
		DatabaseOps.withdraw(accountID, 500);
		updateAllBottomLines();
		reloadAccountSummary();
		} else labelInsufficientFunds.setVisible(true);
		
	}
	
	
	
	
	
	// This handles the $1000 withdrawal button being clicked. -----------------------------------------------------------------
	public void $1000AmountToWithdrawButtonClicked() {
		labelInsufficientFunds.setVisible(false);
		if(sufficientFunds(accountID, (float) 1000.0)) {
		DatabaseOps.withdraw(accountID, 1000);
		updateAllBottomLines();
		reloadAccountSummary();
		} else labelInsufficientFunds.setVisible(true);

	}
	

	
	
	
	// This checks if there are sufficient funds in the account to perform the transaction. ------------------------------------
	public boolean sufficientFunds(int accountID, float amount) {
		if(DatabaseOps.returnBalance(accountID) > amount) return true;
		else return false;
	}
	

	
	
	
	// This handles the Specific Amount Withdrawal button being clicked. -------------------------------------------------------
	public void specificAmountWithdrawalButtonClicked() {
		textFieldWithdrawalAmount.setVisible(true);
		buttonConfirmWithdrawal.setVisible(true);
		reloadAccountSummary();
	}
	
	
	
	
	// This returns to the Login Window. ---------------------------------------------------------------------------------------
	public void returnToLoginWindow() throws InterruptedException {
		Thread.sleep(3000);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login Window.fxml"));
			Parent loginWindow = loader.load();
			Scene loginScene = new Scene(loginWindow);
			Stage window = (Stage)(imageViewLogo.getScene().getWindow());				
			window.setScene(loginScene);
			window.centerOnScreen();
			window.show();
		}
		catch (IOException e) {
				e.printStackTrace();
		}

	}
	
	
	
	
	// This displays the goodbye message. ------------------------------------------------------------------------------------
	public void sayGoodbye() {
		hideAllOptionContainers();
		if(transactionMade == true) {
			labelEmailAddress.setText(DatabaseOps.getCustomerEmail(customerID));
			vBoxRecieptInfo.setVisible(true);
		}
		vBoxThankYou.setVisible(true);
	}
	

	
	// This hides all Option Containers. ---------------------------------------------------------------------------------------
	public void hideAllOptionContainers() {
		vBoxSelectAccount.setVisible(false);
		vBoxDepositAmount.setVisible(false);
		vBoxWithrawalAmount.setVisible(false);
		hBoxTransferAmount.setVisible(false);
		vBoxAccountSummary.setVisible(false);
		vBoxRequestToRecoverAccount.setVisible(false);
		vBoxRecieptInfo.setVisible(false);
		vBoxAccountFrozen.setVisible(false);
		vBoxDestinationAccountFrozen.setVisible(false);
	}
	
	
	
	
	// This updates all of the bottom lines.  ----------------------------------------------------------------------------------
	public void updateAllBottomLines() {
		depositsThisMonth = DatabaseOps.getThisMonthsDepositsSum(accountID);
		withdrawalsThisMonth = DatabaseOps.getThisMonthsWithdrawalsSum(accountID);
		transfersThisMonth = DatabaseOps.getThisMonthsTransfersSum(accountID);
		accountBalance = DatabaseOps.returnBalance(accountID);
		totalDepositsSum = DatabaseOps.getDepositsSum(accountID);
		totalWithdrawalsSum = DatabaseOps.getWithdrawalsSum(accountID);
		totalTransfersSum = DatabaseOps.getTransfersSum(accountID);
		
		lblDepositsThisMonth.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(depositsThisMonth)));
		lblWithdrawalsThisMonth.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(withdrawalsThisMonth)));
		lblTranfersThisMonth.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(transfersThisMonth)));
		lblAvailableBalance.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(accountBalance)));
		labelDepositsTotal.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalDepositsSum)));
		labelWithdrawalsTotal.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalWithdrawalsSum)));
		labelTransfersTotal.setText(String.format("%12s", NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(totalTransfersSum)));

	}
	
	
	// This sets the account summary labels. ---------------------------------------------------------------------------------------------------------
	public void setAccountSummaryLabels() {
		String id = String.format("%09d", accountID);
		lblAccountNumber.setText(id);
		lblAccountNickname.setText(accountNickname);
		lblConnectedCard.setText(Integer.toString(accountCard));
		lblAccountType.setText(accountType);
		
		if(accountStatus == 1) lblAccountStatus.setText("Frozen");
		else lblAccountStatus.setText("Active");
		
		lblDepositsThisMonth.setText("$" + String.valueOf(depositsThisMonth));
		lblWithdrawalsThisMonth.setText("$" + String.valueOf(withdrawalsThisMonth));
		lblTranfersThisMonth.setText("$" + String.valueOf(transfersThisMonth));
		lblAvailableBalance.setText("$" + accountBalance);
	}
	
	// This just properly updates the Account Summaries page. ------------------------------------------------------------------------------------------
	public void reloadAccountSummary() {
		listViewDeposits.getItems().clear();
		listViewWithdrawals.getItems().clear();
		listViewTransfers.getItems().clear();
		getAccountData();
		populateAccountSummaryListViews();
	}
	

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
} // This is the end of the ATM Controller Class. //////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////