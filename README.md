# Credit-app

# Documentation

## `public class Main`

Credit Card App - Generates credit cards using the Luhn algorithm and stores them in a SQLite database created using command line arguments at start up.

 * **Author:** Tim Shea
 * **Version:** 5/18/21

## `public static void main(String[] args)`

Main driver method for the application.

 * **Parameters:** `args` — - '-fileName, SQL-databaseName'

## `public class Account`

Account - Represents a single credit card account.

 * **Author:** Tim Shea
 * **Version:** 5/18/21

## `public Account()`

Constructor to create an Account instance.

## `public long getCardNum()`

Getter for cardNum.

 * **Returns:** long - cardNum

## `public void setCardNum()`

Setter for cardNum.

## `public String getPin()`

Getter for pin number.

 * **Returns:** String - pin

## `public void setPin()`

Setter for pin.

## `public long getBalance()`

Getter for balance amount.

 * **Returns:** long - balance

## `public class AccountUI`

AccountUI - User interface for a single account that can be used once logged in.

 * **Author:** Tim Shea
 * **Version:** 5/18/21

## `public boolean startAccountUI(String fileName, String cardNum, String pinIn)`

User interface for when logged into account.

 * **Parameters:**
   * `fileName` — - String value of the file name
   * `cardNum` — - String value of the card number
   * `pinIn` — - String value of the account pin
 * **Returns:** boolean value 'quit' - false to quit

## `public boolean passesLuhnTest(String cardNum)`

Checks whether the input card number passes the Luhn algorithm test.

 * **Parameters:** `cardNum` — - String value of card number
 * **Returns:** boolean - true if card number passes the Luhn algorithm

## `public class DatabaseActions`

DatabaseActions - Includes static methods used to interact with the database of credit card accounts.

 * **Author:** Tim Shea
 * **Version:** 5/18/21

## `public static void createDatabase(String fileName)`

Creates the SQLite database using the file name given as a CLI parameter.

 * **Parameters:** `fileName` — - String value of fileName

## `public static void createTable(String fileName)`

Creates the table 'card' for the credit card accounts.

 * **Parameters:** `fileName` — - String value of fileName

## `public static Connection connect(String fileName)`

Provides the connection to the database.

 * **Parameters:** `fileName` — - String value of the fileName
 * **Returns:** - Connection object for database

## `public static void insert(String fileName, String cardNum, String pin, long balance)`

Inserts a credit card account into the database.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
   * `pin` — - String value of the pin
   * `balance` — - long representing the account balance

## `public static long getBalance(String fileName, String cardNum, String pinIn)`

Gets the balance for a particular credit card account.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
   * `pinIn` — - String value of the pin
 * **Returns:** - long representing the total balance in the account

## `public static long getBalance(String fileName, String cardNum)`

Overloaded method that gets the balance of an account without requiring the pin.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
 * **Returns:** - long representing the total balance in the account

## `public static void addIncome(String fileName, String cardNum, String pinIn, long amt)`

Adds income to an account when the user is logged in.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
   * `pinIn` — - String value of the pin
   * `amt` — - long amount to be added to account

## `public static boolean getCard(String fileName, String cardNum, String pinIn)`

Gets the card using the card number and account pin.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
   * `pinIn` — - String value of the pin
 * **Returns:** - true if card account is found

## `public static boolean getCard(String fileName, String cardNum)`

Overloaded method that gets a card without requiring the pin.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
 * **Returns:** - true if card account is found

## `public static void closeAccount(String fileName, String cardNum, String pinIn)`

Closes a card account when the user is logged in.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of the cardNum
   * `pinIn` — - String value of the pin

## `public static void transfer(String fileName, String cardNum, String pinIn, String transferToCard, int amount)`

Transfers an amount from one account to another.

 * **Parameters:**
   * `fileName` — - String value of the fileName
   * `cardNum` — - String value of card account user is logged into
   * `pinIn` — - String value of pin for account user is logged into
   * `transferToCard` — - String value of the card number involved in the transfer
   * `amount` — - int value of the transfer amount
