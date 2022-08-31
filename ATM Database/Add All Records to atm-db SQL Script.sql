USE `atm-db`;

-- These delete any previous records from all of the tables. ----------------------------------------------------------------------------
DELETE FROM operators;
DELETE FROM customers;
DELETE FROM atm_access_status;
DELETE FROM account_recovery_requests;
DELETE FROM accounts;
DELETE FROM transactions;
DELETE FROM deposits;
DELETE FROM transfers;
DELETE FROM withdrawals;

-- This populates the operators table. -----------------------------------------------------------------------------------------------------
INSERT INTO operators
		(OperatorID, FirstName, LastName, Password)
VALUES
		(DEFAULT, "Donald", "Trump", "MAGA"),
        (DEFAULT, "Tannaz", "Damavandi", "LoveCPP");

-- This populates the customers table. ----------------------------------------------------------------------------------------------------
INSERT INTO customers
		(CustomerID, FirstName, LastName, DOB, Address, Phone, Email, PIN)
VALUES
        (DEFAULT, "Dexxer", "Medina", "2001-06-15", "643 Park Avenue", "3329876183", "ddmedina@cpp.edu", 1945),
        (DEFAULT, "Anthony", "Zhang", "2002-11-02", "8990 Holt Avenue", "8996543297", "anthonyzhang@cpp.edu", 2211),
        (DEFAULT, "Anthony", "Sepulveda", "2000-02-15", "211456 Rodeo Drive", "2137685862", "asepulveda1@cpp.edu", 9561),
        (DEFAULT, "Rosalinda", "Chamale", "2002-06-08", "2121 Melrose Place", "9998776513", "rchamale@cpp.edu", 9908);

-- This populates the atm_access_status table. ------------------------------------------------------------------------------------------------
-- Note: Initially, only Sepulveda will be unable to access the ATM (i.e., he is locked out)
INSERT INTO atm_access_status
		(StatusID, CustomerID, Locked)
VALUES
        (DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Medina"), 0),
        (DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Zhang"), 0),
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Sepulveda"), 1),
        (DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Chamale"), 0);
        
-- This populates the accounts table. ----------------------------------------------------------------------------------------------------------
-- Note: Initially, all four customers will each have a Checking and Savings accounts.
INSERT INTO accounts
		(AccountID, CustomerID, CreationDate, AccountType, CardID, Balance, Frozen, AccountNickname)
VALUES
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Medina"), "2010-06-29", "Checking", 996755543, 485, 0, "Grocery Money"),
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Medina"), "2014-03-30", "Savings", 988865440, 590, 0, "Retirement"),
        
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Zhang"), "2017-08-21", "Checking", 999980320, 215, 0, "Partying Money"),
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Zhang"), "2010-03-18", "Savings", 999800545, 3040, 0, "Rainy Day"),
        
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Sepulveda"), "2019-03-13", "Checking", 999980320, 6645, 0, "Petty Cash"),
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Sepulveda"), "2020-12-21", "Savings", 916675432, 12010, 0, "Security Fund"),
        
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Chamale"), "2015-12-01", "Checking", 999908765, 750, 0, "For Utilities"),
		(DEFAULT, (SELECT CustomerID FROM customers WHERE LastName = "Chamale"), "2000-06-18", "Savings", 990087654, 1420, 1, "College Fund");
        
-- This populates the account_recovery_requests table. ------------------------------------------------------------------------------------------
-- Note: Initially, Chamale's "College Fund" account will be the only one frozen.
INSERT INTO account_recovery_requests
		(RequestID, AccountID)
VALUES
		(DEFAULT, (SELECT AccountID FROM accounts WHERE Frozen = 1));
        
-- This populates the transactions table. -------------------------------------------------------------------------------------------------------
-- Note: Initally, all four customers will each have three transactions recorded: a Withdrawal, a Deposit, and a Transfer
INSERT INTO transactions
		(TransactionID, AccountID, Date, Type)
VALUES
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Grocery Money"), "2021-03-15", "Withdrawal"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Grocery Money"), "2021-03-16", "Deposit"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Grocery Money"), "2021-03-17", "Transfer"),
        
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Partying Money"), "2021-03-18", "Withdrawal"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Partying Money"), "2021-03-19", "Deposit"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Partying Money"), "2021-03-20", "Transfer"),
        
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Petty Cash"), "2021-03-21", "Withdrawal"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Petty Cash"), "2021-03-22", "Deposit"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "Petty Cash"), "2021-03-23", "Transfer"),
        
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "For Utilities"), "2021-03-24", "Withdrawal"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "For Utilities"), "2021-03-25", "Deposit"),
		(DEFAULT, (SELECT AccountID FROM accounts WHERE  AccountNickname = "For Utilities"), "2021-03-26", "Transfer");

-- This populates the withdrawals table. --------------------------------------------------------------------------------------------------------
INSERT INTO withdrawals
		(WithdrawalID, TransactionID, Amount)
VALUES
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-15"), 350),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-18"), 900),
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-21"), 100),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-24"), 200);
        
-- This populates the deposits table. -----------------------------------------------------------------------------------------------------------
INSERT INTO deposits
		(DepositID, TransactionID, Amount)
VALUES
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-16"), 450),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-19"), 950),
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-22"), 200),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-25"), 335);

-- This populates the transfers table. ----------------------------------------------------------------------------------------------------------
INSERT INTO transfers
		(TransferID, TransactionID, Amount, DestinationAccount)
VALUES
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-17"), 50, (SELECT AccountID FROM accounts WHERE AccountNickname  = "Retirement")),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-20"), 40, (SELECT AccountID FROM accounts WHERE AccountNickname  = "Rainy Day")),
		(DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-23"), 20, (SELECT AccountID FROM accounts WHERE AccountNickname  = "Security Fund")),
        (DEFAULT, (SELECT TransactionID FROM transactions WHERE  date = "2021-03-26"), 15, (SELECT AccountID FROM accounts WHERE AccountNickname  = "College Fund"));