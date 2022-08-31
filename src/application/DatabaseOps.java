package application;


/////////////////////////////////////// These are imports needed for the Database Ops ////////////////////////////////////////////////////
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// This controls all of the Database Operations. ///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public final class DatabaseOps {
	
	// These are the constants that will be used in this class. -------------------------------------------------------------------
	private static final String NULLSTRING = "";
	private static final int NONE_SELECTED = -1;
	
	
	// These are the variables that will be used for all of the database queries. -------------------------------------------------
	static private Connection connection = null;
	static private Statement myStatement = null;
	static private ResultSet myResults = null;
	static private PreparedStatement statement = null;
	static private String DBConnection = "jdbc:mysql://localhost:3306/atm-db";
	static private String Username = "root";
	static private String Password = "l9*K7&g6^D5%";

	
	///////////////////////////////// This section contains all of the Database Op's Methods //////////////////////////////////////////////
	
	// This gets all the deposits a customer has made given an account ID. -----------------------------------------------
	public static ArrayList<String> getAllDeposits(int accountID) {

		ArrayList<String> deposits = new ArrayList<String>();
		
		String currentQuery = 
				"SELECT Amount FROM deposits WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ");";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					deposits.add(myResults.getString("Amount"));
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return deposits;
		
	}
	
	
	
	
	// This gets all the withdrawals a customer has made given an account ID. ------------------------------------------------
	public static ArrayList<String> getAllWithdrawals(int accountID) {

		ArrayList<String> withdrawals = new ArrayList<String>();
		
		String currentQuery = 
				"SELECT Amount FROM withdrawals WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ");";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					withdrawals.add(myResults.getString("Amount"));
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return withdrawals;
		
	}
	
	
	// This gets all the transfers a customer has made given an account ID. --------------------------------------------------
	public static ArrayList<String> getAllTransfers(int accountID) {

		ArrayList<String> transfers = new ArrayList<String>();
		
		String currentQuery = 
				"SELECT Amount FROM transfers WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ");";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					transfers.add(myResults.getString("Amount"));
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return transfers;
		
	}
	
	// This gets the deposits the customer made within the last month. -------------------------------------------------------
	public static float getThisMonthsDepositsSum(int accountID) {

		float monthsDepositsSum = 0;
		
		String currentQuery = 
				"SELECT sum(Amount) FROM deposits WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + 
				accountID + " AND MONTH(Date)=MONTH(curdate()));";

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) { 
					monthsDepositsSum = myResults.getFloat(1);
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return monthsDepositsSum;
		
	}
	
	
	// This gets the withdrawals the customer made within the last month. ----------------------------------------------------
	public static float getThisMonthsWithdrawalsSum(int accountID) {

		float monthsWithdrawalsSum = 0;
		String currentQuery = 
				"SELECT sum(Amount) FROM withdrawals WHERE TransactionID IN " +
			    "(SELECT TransactionID FROM transactions WHERE AccountID=" + 
				accountID + " AND MONTH(Date)=MONTH(curdate()));";

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					monthsWithdrawalsSum = myResults.getFloat(1);
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return monthsWithdrawalsSum;
		
	}
	

	// This gets the transfers the customer made within the last month. -------------------------------------------------------
	public static float getThisMonthsTransfersSum(int accountID) {

		float monthsTransfersSum = 0;
		
		String currentQuery = 
				"SELECT sum(Amount) FROM transfers WHERE TransactionID IN " +
			    "(SELECT TransactionID FROM transactions WHERE AccountID=" + 
				accountID + " AND MONTH(Date)=MONTH(curdate()));";

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					monthsTransfersSum = myResults.getFloat(1);
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return monthsTransfersSum;
		
	}
	
	
	
	// This returns the accountID the customer wants to work with. --------------------------------------------------------------
	public static int returnAccountID(int customerID, String type) {
		
		// Note: The parameter "type" can have either the value "Checking" of "Savings"
		String currentQuery = "SELECT * FROM accounts WHERE CustomerID = " + customerID + 
							  " and AccountType = '" + type + "';";
		int accountID = 0;
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					accountID = myResults.getInt("AccountID");
				}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountID;
		
	}
	
	
	
	
	// This gets the customer name given the customer ID. --------------------------------------------------------------------------
	public static String getCustomerName(int customerID)
	{
		String customerName = NULLSTRING;
		String currentQuery = "SELECT FirstName, LastName FROM customers WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customerName = myResults.getString("FirstName");
					customerName = customerName + " " + myResults.getString("LastName");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerName;
		
	}
	
	
	// This gets the customer' DOB given the customer ID. -------------------------------------------------------------------------
	public static String getCustomerDOB(int customerID) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
		
		
		String currentQuery = "SELECT DOB FROM customers WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					date = myResults.getDate("DOB");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String customerDOB = sdf.format(date);
		return customerDOB;
		
	}
	
	
	
	// This gets the account creation date given the account ID. ------------------------------------------------------------------
	public static String getAccountCreationDate(int accountID) {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
		
		String currentQuery = "SELECT CreationDate FROM accounts WHERE AccountID = " + accountID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					date = myResults.getDate("CreationDate");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		String accountCreationDate = sdf.format(date);
		return accountCreationDate;
		
	}
	
	
	
	
	// This gets the customer phone given the customer ID. ------------------------------------------------------------------------
	public static String getCustomerPhone(int customerID) {
		
		String customerPhone = NULLSTRING;
		String currentQuery = "SELECT Phone FROM customers WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customerPhone = myResults.getString("Phone");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerPhone;
		
	}
	
	
	
	
	// This gets the customer email given the customer ID. -------------------------------------------------------------------------
	public static String getCustomerEmail(int customerID) {
		
		String customerEmail = NULLSTRING;
		String currentQuery = "SELECT Email FROM customers WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customerEmail = myResults.getString("Email");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerEmail;
		
	}
	
	
	
	// This returns the customer ATM access status given the customer ID. ------------------------------------------------------------
	public static int getCustomerATMAccess(int customerID) {
		
		int customerATMAccessStatus = NONE_SELECTED;
		String currentQuery = "SELECT Locked FROM atm_access_status WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customerATMAccessStatus = myResults.getInt("Locked");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerATMAccessStatus;

	}
		
	
	// This gets the customer's address given the customer ID. ---------------------------------------------------------------------
	public static String getCustomerAddress(int customerID) {
		
		String customerAddress = NULLSTRING;
		String currentQuery = "SELECT Address FROM customers WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customerAddress = myResults.getString("Address");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerAddress;
		
	}
	
	
	
	// This populates an ArrayList with the nicknames of the accounts. ------------------------------------------------------------
	public static ArrayList<String> returnAccountNicknames(int customerID) {

		ArrayList<String> accounts = new ArrayList<String>();
		String currentQuery = "SELECT AccountNickname FROM accounts WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					accounts.add(myResults.getString("AccountNickname"));
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
		
	}
	
	
	
	// This returns and account nickname given the account ID. --------------------------------------------------------------------------
	public static String returnAccountNickname(int accountID) {

		String accountNickname = NULLSTRING;
		String currentQuery = "SELECT AccountNickname FROM accounts WHERE AccountID = " + accountID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					accountNickname = myResults.getString("AccountNickname");
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accountNickname;
		
	}
	
	
	
	
	
	// This returns the account type given the account ID. ------------------------------------------------------------------------------
	public static String returnAccountType(int accountID) {

		String accountType = NULLSTRING;
		String currentQuery = "SELECT AccountType FROM accounts WHERE AccountID = " + accountID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					accountType = myResults.getString("AccountType"); // there may be multiple accounts
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accountType;
		
	}
	
	
	
	
	// This returns the balance of an account given the account ID. ----------------------------------------------------------------
	public static float returnBalance(int accountID) {

		float accountBalance = 0;
		String currentQuery = "SELECT Balance FROM accounts WHERE AccountID = " + accountID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					accountBalance = myResults.getFloat("Balance");
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accountBalance;
		
	}
	
	
	
	// This returns an array containing the customer's accounts given the customer ID. --------------------------------------------------
	public static ArrayList<String> returnAccountIDs(int customerID) {

		ArrayList<String> accounts = new ArrayList<String>();
		String currentQuery = "SELECT * FROM accounts WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					accounts.add(myResults.getString("AccountID")); // there may be multiple accounts
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
		
	}
	
	

	
	// This returns the bank card associated with a given AccountID. ---------------------------------------------------------------
	public static int returnAccountCard(int accountID) {
		
		String currentQuery =
				"SELECT CardID FROM accounts WHERE AccountID = " + accountID + ";";
		int cardID = 0;
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if(myResults.next()) {
				cardID = myResults.getInt("CardID");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cardID;
		
	}
	
	
	
	
	// This returns the account status (i.e., whether it is frozen or not). ---------------------------------------------------------
	public static int returnAccountStatus(int accountID) {
		
		String currentQuery =
				"SELECT Frozen FROM accounts WHERE AccountID = " + accountID + ";";
		
		int status = 0;

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if(myResults.next()) {
				status = myResults.getInt("Frozen");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return status;
		
	}
		
	
	
	
	
	// This returns a customer's checking account with a given customerID. ----------------------------------------------------------
	public static int returnCheckingAccount(int customerID) {
		
		String currentQuery =
				"SELECT AccountID FROM accounts WHERE CustomerID = " + customerID + " and AccountType = 'Checking';";
		
		int accountID = 0;
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if(myResults.next()) {
				accountID = myResults.getInt("AccountID");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountID;
		
	}
	
	
	// This gets a single customer ID record from the customer's table. --------------------------------------------------------------------------
	public static int getSingleCustomerID() {
		
		String currentQuery = "SELECT CustomerID FROM customers ORDER BY CustomerID ASC LIMIT 1;";
		int customerID = NONE_SELECTED;

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if (myResults.next()) {
				customerID = myResults.getInt("CustomerID");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerID;
	}
	
	
	
	
	
	// This returns the accountID corresponding with a given customerID and AccountNickname. ----------------------------------------
	public static int returnAccountIDFromNickname(int customerID, String nickname) {
		
		int accountID = NONE_SELECTED;
		String currentQuery =
				"SELECT AccountID FROM accounts WHERE CustomerID = " + customerID + " and AccountNickname = '" + nickname + "';";
		
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if(myResults.next()) {
				accountID = myResults.getInt("AccountID");
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountID;
		
	}
	
	//Checks is specific account is frozen. -------------------------------------------------------------------------------------
	public static boolean checkAccountFrozen(int accountID) {
		
		int frozenStatus = NONE_SELECTED;
		String currentQuery = "SELECT Frozen FROM accounts WHERE AccountID = " + accountID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					frozenStatus = myResults.getInt("Frozen");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(frozenStatus == 1) return true;
		else return false;
		
	}

	
	
	
	// This makes a deposits to the account. -------------------------------------------------------------------------------------
	public static void deposit(int accountID, float amount) {

		float prevBalance = returnBalance(accountID);
		float newBalance = prevBalance + amount;
		updateBalance(accountID, newBalance);
		addTransactionRecord(accountID, accountID, amount, "Deposit");
		
	}

	
	
	// This makes a withdrawal from the account. ------------------------------------------------------------------------------
	public static void withdraw(int accountID, float amount) {
		float prevBalance = returnBalance(accountID);
		float newBalance = prevBalance - amount;
		updateBalance(accountID, newBalance);
		addTransactionRecord(accountID, accountID, amount, "Withdrawal");
	}

	
	
	// This adds a deposit record. -------------------------------------------------------------------------------------------------------------
	public static void addDepositRecord(int transactionID, float amount) {
		
		String currentQuery = "INSERT INTO deposits (DepositID, TransactionID, Amount) " + "VALUES (?, ?, ?);";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, 0);
			statement.setInt(2, transactionID);
			statement.setFloat(3, amount);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();}
		
	}
	
	
	
	// This makes an account transfer. ----------------------------------------------------------------------------------------
	public static void transfer(int srcAccount, int destAccount, float amount) {
		
		// This updates the balance of the source account.
		float prevBalance = returnBalance(srcAccount);
		float newBalance = prevBalance - amount;
		updateBalance(srcAccount, newBalance);
		
		// This updates the balance of the destination account.
		prevBalance = returnBalance(destAccount);
		newBalance = prevBalance + amount;
		updateBalance(destAccount, newBalance);
		
		// This adds a transaction record for the transfer.
		addTransactionRecord(srcAccount, destAccount, amount, "Transfer");
		
	}
	

	
	// This updates the balance of an account. -------------------------------------------------------------------------------------------------------
	public static void updateBalance(int accountID, float newBalance) {	
		
		String currentQuery = "UPDATE accounts SET Balance = ? WHERE AccountID = ?;";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setFloat(1, newBalance);
			statement.setInt(2, accountID);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	
	// This adds a transaction record. ---------------------------------------------------------------------------------------
	public static void addTransactionRecord(int srcAccount, int destAccount, float amount, String type) {	
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(date);
		
		int transID = 0;
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement("INSERT INTO transactions (TransactionID, AccountID, Date, Type) "
					    + "VALUES (?, ?, ?, ?);");
			statement.setInt(1, transID);
			statement.setInt(2, srcAccount);
			statement.setString(3, currentDate);
			statement.setString(4, type);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery("SELECT TransactionID FROM transactions ORDER BY TransactionID DESC LIMIT 1;");
			if (myResults.next()) {
				transID = myResults.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (type.equals("Deposit")) addDepositRecord(transID, amount);
		if (type.equals("Withdrawal")) addWithdrawalRecord(transID, amount);
		if (type.equals("Transfer")) addTransferRecord(transID, destAccount, amount);
	
	}

	
	

	
	// This adds a withdrawal record. ------------------------------------------------------------------------------------------------------------
	public static void addWithdrawalRecord(int transactionID, float amount) {

		String currentQuery = "INSERT INTO withdrawals (WithdrawalID, TransactionID, Amount) " + "VALUES (?, ?, ?);";

		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, 0);
			statement.setInt(2, transactionID);
			statement.setFloat(3, amount);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();}
		
	}
	
	
	
	
	
	// This adds a transfer record. -------------------------------------------------------------------------------------------------------------------
	public static void addTransferRecord(int transactionID, int destination, float amount) {

		String currentQuery = "INSERT INTO transfers (TransferID, TransactionID, DestinationAccount, Amount) " + "VALUES (?, ?, ?, ?);";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, 0);
			statement.setInt(2, transactionID);
			statement.setInt(3,  destination);
			statement.setFloat(4, amount);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// This gets the sum of all deposits made given the account ID. --------------------------------------------------------------------------
	public static float getDepositsSum(int accountID) {

		float depositsSum = 0;
		String currentQuery = "SELECT sum(Amount) FROM deposits WHERE TransactionID IN " +
							  "(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ")";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					depositsSum = myResults.getFloat(1);
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return depositsSum;
		
	}
	
	
	
	// This gets the sum of all withdrawals made given the account ID. --------------------------------------------------------------------------
	public static float getWithdrawalsSum(int accountID) {

		float withdrawalsSum = 0;
		String currentQuery = 
				"SELECT sum(Amount) FROM withdrawals WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ")";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					withdrawalsSum = myResults.getFloat(1);
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return withdrawalsSum;
		
	}
	
	
	
	// This gets the sum of all transfers made given the account ID. --------------------------------------------------------------------------
	public static float getTransfersSum(int accountID) {

		float transfersSum = 0;
		String currentQuery = 
				"SELECT sum(Amount) FROM transfers WHERE TransactionID IN " +
				"(SELECT TransactionID FROM transactions WHERE AccountID=" + accountID + ")";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					transfersSum = myResults.getFloat(1);
				}	
				connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return transfersSum;
		
	}

	
	
	
	// This gets the CustomerID for the customer whose Card is clicked. -------------------------------------------------------
	public static int getCustomerID(String lastName) {

		String currentQuery = "SELECT CustomerID FROM customers WHERE LastName = '" + lastName + "';";
		int customerID = NONE_SELECTED;
	
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
			if(myResults.next()) {
				customerID = myResults.getInt("CustomerID");
			}			
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customerID;
		
	}

	
	
	
	// This gets the stored PIN of a given Customer. ---------------------------------------------------------------------------------
	public static int getStoredPIN(String targetCustomer) {
		
		int storedPIN = NONE_SELECTED;
		String currentQuery = "SELECT PIN FROM customers WHERE CustomerID = " + targetCustomer + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					storedPIN = myResults.getInt("PIN");
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return storedPIN;
		
	}
	
//Gets the stored password of the selected operator. -----------------------------------------------------------------------------
	public static String getStoredOperatorPass(int operatorID) {
		
		String storedPass = NULLSTRING;
		String currentQuery = "SELECT Password FROM operators WHERE OperatorID = " + operatorID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					storedPass = myResults.getString("Password");
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return storedPass;
		
	}
	
	
	
	
	// This locks the customer out of the ATM. ----------------------------------------------------------------------------------	
	public static void lockCustomerOutOfATM(String targetCustomer) {
		
		String currentQuery = "UPDATE atm_access_status SET Locked = 1 WHERE CustomerID = " + targetCustomer + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myStatement.executeUpdate(currentQuery);	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	// This determines if the customer is locked out of the ATM. --------------------------------------------------------------
	public static boolean CustomerIsLockedOut(int customerID) {
		
		int lockedStatus = NONE_SELECTED;
		String currentQuery = "SELECT Locked FROM atm_access_status WHERE CustomerID = " + customerID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					lockedStatus = myResults.getInt("Locked");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(lockedStatus == 1) return true;
		else return false;
		
	}
	
	
	
	
	
	// This is for the combination box used by the operators to log in. ------------------------------------------------------------
	public static ArrayList<Integer> getOperatorID()
	{
		ArrayList<Integer> operatorIDs = new ArrayList<Integer>();
		String currentQuery = "SELECT OperatorID FROM operators;";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					operatorIDs.add(myResults.getInt("OperatorID"));
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return operatorIDs;
		
	}
	
	
	
	
	// This returns the operator's name given the operator' ID. ---------------------------------------------------------------
	public static String getOperatorName(int operatorID)
	{
		String operatorName = NULLSTRING;
		String currentQuery = "SELECT FirstName, LastName FROM operators WHERE OperatorID = " + operatorID + ";";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					operatorName = myResults.getString("FirstName");
					operatorName = operatorName + " " + myResults.getString("LastName");
				}
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return operatorName;
		
	}

	
	
	
	// This adds the functionality of the Customer function in OperatorController. --------------------------------------------------
	public static String returnCustomerInfo(int customerID, String infoNeeded) {
		
		String info = NULLSTRING;
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			switch(infoNeeded) {
			case "firstname":
				myResults = myStatement.executeQuery("SELECT FirstName FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "lastname":
				myResults = myStatement.executeQuery("SELECT LastName FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "date":
				myResults = myStatement.executeQuery("SELECT DOB FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "address":
				myResults = myStatement.executeQuery("SELECT Address FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "phone":
				myResults = myStatement.executeQuery("SELECT Phone FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "email":
				myResults = myStatement.executeQuery("SELECT Email FROM customers WHERE CustomerID = " + customerID + ";");
				break;
			case "access":
				myResults = myStatement.executeQuery("SELECT Locked FROM atm_access_status WHERE CustomerID = " + customerID + ";");
			}
			if(myResults.next()) {
				switch(infoNeeded) {
				case "firstname":
				case "lastname":
				case "address":
				case "email":
					info = myResults.getString(1);
					break;
				case "date":
					Date date = myResults.getDate(1);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					info = sdf.format(date); 
					break;
				case "phone":
					long number = myResults.getLong(1);
					info = Long.toString(number);
					break;
				case "access":
					int value = myResults.getInt(1);
					if (value == 0) {
						info = "true";
					}
					else {
						info = "false";
					}
					break;
				}
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return info;
		
	}
	
	// This adds the functionality of the Account function in OperatorController --------------------------------------------------
	public static String returnAccountInfo(int accountID, int customerID, String infoNeeded) {
		
		String info = NULLSTRING;
		try {
				connection = DriverManager.getConnection(DBConnection, Username, Password);
				myStatement = connection.createStatement();
				switch(infoNeeded) {
				case "nickname":
					myResults = myStatement.executeQuery("SELECT AccountNickname FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;	
				case "type":
					myResults = myStatement.executeQuery("SELECT AccountType FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;
				case "date":
					myResults = myStatement.executeQuery("SELECT CreationDate FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;
				case "card":
					myResults = myStatement.executeQuery("SELECT CardID FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;
				case "status":
					myResults = myStatement.executeQuery("SELECT Frozen FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;
				case "balance":
					myResults = myStatement.executeQuery("SELECT Balance FROM accounts WHERE AccountID = " + accountID + " and CustomerID = " + customerID + ";");
					break;
				}
				if(myResults.next()) {
					switch(infoNeeded) {
					case "nickname":
					case "type":
						info = myResults.getString(1);
						break;
					case "date":
						Date date = myResults.getDate(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						info = sdf.format(date); 
						break;
					case "card":
						long number = myResults.getLong(1);
						info = Long.toString(number);
						break;
					case "status":
						int value = myResults.getInt(1);
						if (value == 1) {
							info = "Yes";
						}
						else {
							info = "No";
						}
						break;
					case "balance":
						double bal = myResults.getDouble(1);
						info = "$" + Double.toString(bal);
						break;
					}
				}
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		return info;
	}

	
	
	
	// This returns the customers' names. ------------------------------------------------------------------------------
	public static ArrayList<String> returnCustomerNames() {

		ArrayList<String> customers = new ArrayList<String>();
		String currentQuery = "SELECT FirstName, LastName FROM customers";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				while(myResults.next()) {
					customers.add(myResults.getString("FirstName") + " " + myResults.getString("LastName"));
				}	
			connection.close();	
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
		
	}
	
	
	// This gets the customer ID given the full name. ------------------------------------------------------------------------
	public static int getCustomerIDFromFullName(String firstName, String lastName) {
		int customerID = 0;
		
		String currentQuery = 
				"SELECT CustomerID FROM customers WHERE FirstName = '" + firstName + "' AND LastName = '" + lastName + "'";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery(currentQuery);
				if(myResults.next()) {
					customerID = myResults.getInt("CustomerID");
				}	
				connection.close();	
				
			}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customerID;
		
	}
	
	// Updates the status of a locked account to unlocked (has access to ATM) in the database based on the customerID
	// customerID is linked to the name selected in the list that is in the Customer tab of the Operator window
	// Method is used when the restore access button is clicked in the Operator window
	
	public static void lockedStatus(int customerID) {
		   
		String currentQuery = "UPDATE atm_access_status SET Locked = 0 WHERE CustomerID = ?;";
		
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, customerID);
			statement.executeUpdate();
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	 }
	
	
	
	// Updates the status of an account to being frozen in the database based on the accountID
	// Method is used when the freeze account button is clicked in the Accounts Tab of the operator window
	public static void frozenStatus(int accountID) {
		  
			String currentQuery = "UPDATE accounts SET Frozen = 1 WHERE AccountID = ?;";
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, accountID);
			statement.executeUpdate();
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
	   
	   
	 // Updates the status of an frozen account in the database based on the accountID so that it is no longer frozen 
	 // Method is used when the restore account button is clicked in the Accounts Tab of the operator window 
	 public static void recoverAccountStatus(int accountID) {
		  
		 String currentQuery = "UPDATE accounts SET Frozen = 0 WHERE AccountID = ?;";
		 try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			statement = connection.prepareStatement(currentQuery);
			statement.setInt(1, accountID);
			statement.executeUpdate();
			connection.close();
		 }
		 catch (SQLException e)
		 {
			e.printStackTrace();
		 }
	 }  
	   

	 
	// This is used to retrieve all the deposits from an account for the Operator's Transactions Tab
	public static ArrayList<String> returnAllDepositsOperator(int accountID) {
		
		ArrayList<Date> dates = new ArrayList<Date>();
		ArrayList<Integer> tID = new ArrayList<Integer>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		ArrayList<String> entry = new ArrayList<String>();
			
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery("SELECT * from transactions WHERE AccountID = " + accountID + " and Type = 'Deposit';");
				while(myResults.next()) {
					tID.add(myResults.getInt(1));
					dates.add(myResults.getDate(3));
				}	
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
			   	
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			for (int i = 0; i < tID.size(); i++) {
				myResults = myStatement.executeQuery("SELECT Amount from deposits WHERE TransactionID = " + tID.get(i) + ";");
				if(myResults.next()) {
					amounts.add(myResults.getInt(1));
				}
			}	
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
			   	
		for(int i = 0; i < tID.size(); i++) {
		   	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   	String strDate = dateFormat.format(dates.get(i));
		   	entry.add(Integer.toString(tID.get(i)) + "\t" + strDate + "\t$" + Integer.toString(amounts.get(i)));
		}
			   	
		return entry;
			  	
		}

	
	
	// This is used to retrieve all the withdrawals from an account for the Operator's Transactions Tab -------------------------------------------
	public static ArrayList<String> returnAllWithdrawalsOperator(int accountID) {
		
		   	ArrayList<Date> dates = new ArrayList<Date>();
			ArrayList<Integer> tID = new ArrayList<Integer>();
			ArrayList<Integer> amounts = new ArrayList<Integer>();
			ArrayList<String> entry = new ArrayList<String>();
			
		   	try {
				connection = DriverManager.getConnection(DBConnection, Username, Password);
				myStatement = connection.createStatement();
				myResults = myStatement.executeQuery("SELECT * from transactions WHERE AccountID = " + accountID + " and Type = 'Withdrawal';");
				while(myResults.next()) {
					tID.add(myResults.getInt(1));
					dates.add(myResults.getDate(3));
				}	
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			   	
		   	try {
				connection = DriverManager.getConnection(DBConnection, Username, Password);
				myStatement = connection.createStatement();
				for (int i = 0; i < tID.size(); i++) {
					myResults = myStatement.executeQuery("SELECT Amount from Withdrawals WHERE TransactionID = " + tID.get(i) + ";");
					if(myResults.next()) {
						amounts.add(myResults.getInt(1));
					}
				}	
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		
			for(int i = 0; i < tID.size(); i++) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   		String strDate = dateFormat.format(dates.get(i));
		   		entry.add(Integer.toString(tID.get(i)) + "\t" + strDate + "\t$" + Integer.toString(amounts.get(i)));
		   	}
			   	
			return entry;
		}

	

	
	// This is used to retrieve all the transfers from an account for the Operator's Transactions Tab ---------------------------------------------
	public static ArrayList<String> returnAllTransfersOperator(int accountID) {
		
		ArrayList<Date> dates = new ArrayList<Date>();
		ArrayList<Integer> tID = new ArrayList<Integer>();
		ArrayList<Integer> amounts = new ArrayList<Integer>();
		ArrayList<Integer> accID = new ArrayList<Integer>();
		ArrayList<String> accName = new ArrayList<String>();
		ArrayList<String> entry = new ArrayList<String>();
			
		try {
			connection = DriverManager.getConnection(DBConnection, Username, Password);
			myStatement = connection.createStatement();
			myResults = myStatement.executeQuery("SELECT * from transactions WHERE AccountID = " + accountID + " and Type = 'Transfer';");
				while(myResults.next()) {
					tID.add(myResults.getInt(1));
					dates.add(myResults.getDate(3));
				}	
			connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			   	
		   	try {
				connection = DriverManager.getConnection(DBConnection, Username, Password);
				myStatement = connection.createStatement();
				for (int i = 0; i < tID.size(); i++) {
					myResults = myStatement.executeQuery("SELECT * from Transfers WHERE TransactionID = " + tID.get(i) + ";");
					if(myResults.next()) {
						amounts.add(myResults.getInt(3));
						accID.add(myResults.getInt(4));
					}
				}	
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		   	
		   	try {
				connection = DriverManager.getConnection(DBConnection, Username, Password);
				myStatement = connection.createStatement();
				for (int i = 0; i < tID.size(); i++) {
					myResults = myStatement.executeQuery("SELECT * from accounts WHERE AccountID = " + accID.get(i) + ";");
					if(myResults.next()) {
						accName.add(myResults.getString(8));
					}
				}	
				connection.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			   	
		   	for(int i = 0; i < tID.size(); i++) {
		   		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   		String strDate = dateFormat.format(dates.get(i));
		   		entry.add(Integer.toString(tID.get(i)) + "\t" + strDate + "\t$" + Integer.toString(amounts.get(i)) + "\t\t" + accName.get(i));
		   	}
			   	
			return entry;
			
		}	
	   
	   
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
} // End of Database Ops Class. ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	   
