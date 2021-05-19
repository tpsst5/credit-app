import java.sql.*;

/**
 * DatabaseActions - Includes static methods used to interact with the database of credit card accounts.
 *
 * @author Tim Shea
 * @version 5/18/21
 */
public class DatabaseActions {

    /**
     * Creates the SQLite database using the file name given as a CLI parameter.
     * @param fileName - String value of fileName
     */
    public static void createDatabase(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("The new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Creates the table 'card' for the credit card accounts.
     * @param fileName - String value of fileName
     */
    public static void createTable(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;

        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "    id INTEGER PRIMARY KEY,\n"
                + "    number TEXT NOT NULL,\n"
                + "    pin TEXT NOT NULL,\n"
                + "    balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Provides the connection to the database.
     * @param fileName - String value of the fileName
     * @return - Connection object for database
     */
    public static Connection connect(String fileName) {

        String url = "jdbc:sqlite:src/main/resources/sqlite/" + fileName;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;

    }

    /**
     * Inserts a credit card account into the database.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @param pin - String value of the pin
     * @param balance - long representing the account balance
     */
    public static void insert(String fileName, String cardNum, String pin, long balance) {

        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, cardNum);
            pstmt.setString(2, pin);
            pstmt.setLong(3, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Gets the balance for a particular credit card account.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @param pinIn - String value of the pin
     * @return - long representing the total balance in the account
     */
    public static long getBalance(String fileName, String cardNum, String pinIn) {
        String sql = "SELECT balance FROM card WHERE number=? AND pin=?";
        long sqlBalance = 0;

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, cardNum);
            pstmt.setString(2, pinIn);
            //
            ResultSet rs = pstmt.executeQuery();
            //
            sqlBalance = rs.getLong("balance");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return sqlBalance;
        }
    }

    /**
     * Overloaded method that gets the balance of an account without requiring the pin.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @return - long representing the total balance in the account
     */
    public static long getBalance(String fileName, String cardNum) {
        String sql = "SELECT balance FROM card WHERE number=?";
        long sqlBalance = 0;

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, cardNum);
            //
            ResultSet rs = pstmt.executeQuery();
            //
            sqlBalance = rs.getLong("balance");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return sqlBalance;
        }
    }

    /**
     * Adds income to an account when the user is logged in.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @param pinIn - String value of the pin
     * @param amt - long amount to be added to account
     */
    public static void addIncome(String fileName, String cardNum, String pinIn, long amt) {
        String sql = "UPDATE card SET balance=? WHERE number=? AND pin=?";
        long existingBalance = DatabaseActions.getBalance(fileName, cardNum, pinIn);

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, existingBalance + amt);
            pstmt.setString(2, cardNum);
            pstmt.setString(3, pinIn);
            // Update
            pstmt.executeUpdate();
            System.out.println("Income was added!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets the card using the card number and account pin.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @param pinIn - String value of the pin
     * @return - true if card account is found
     */
    public static boolean getCard(String fileName, String cardNum, String pinIn) {
        String sql = "SELECT number, pin FROM card WHERE number=? AND pin=?";
        boolean found = false;

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNum);
            pstmt.setString(2, pinIn);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    found = rs.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return found;
        }
    }

    /**
     * Overloaded method that gets a card without requiring the pin.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @return - true if card account is found
     */
    public static boolean getCard(String fileName, String cardNum) {
        String sql = "SELECT number, pin FROM card WHERE number=?";
        boolean found = false;

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNum);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    found = rs.getBoolean(1);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            return found;
        }
    }

    /**
     * Closes a card account when the user is logged in.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of the cardNum
     * @param pinIn - String value of the pin
     */
    public static void closeAccount(String fileName, String cardNum, String pinIn) {

        String sql = "DELETE FROM card WHERE number=? AND pin=?";

        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set values
            pstmt.setString(1, cardNum);
            pstmt.setString(2, pinIn);
            pstmt.executeUpdate();
            System.out.println("The account has been closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Transfers an amount from one account to another.
     * @param fileName - String value of the fileName
     * @param cardNum - String value of card account user is logged into
     * @param pinIn - String value of pin for account user is logged into
     * @param transferToCard - String value of the card number involved in the transfer
     * @param amount - int value of the transfer amount
     */
    public static void transfer(String fileName, String cardNum, String pinIn, String transferToCard, int amount) {
        // Balance of account where funds are coming from
        long transferAcctBalance = DatabaseActions.getBalance(fileName, cardNum, pinIn);

        // Balance of account where funds are going to
        long existingBalance = DatabaseActions.getBalance(fileName, transferToCard);

        // Check if money in account covers transfer amount
        if (transferAcctBalance < amount) {
            System.out.println("Not enough money!");
            return;
        }

        // SQL statements
        String sqlReduceBalance = "UPDATE card SET balance=? WHERE number=?";
        String sqlAddBalance = "UPDATE card SET balance=? WHERE number=?";

        // Reduce funds from account money is coming from (transferAcctBalance)
        try (Connection conn = DatabaseActions.connect(fileName);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlReduceBalance)) {

            // Set values
            pstmt2.setLong(1, transferAcctBalance - amount);
            pstmt2.setString(2, cardNum);
            // Execute
            pstmt2.executeUpdate();

            // Add funds to other card account
            try (PreparedStatement pstmt3 = conn.prepareStatement(sqlAddBalance)) {

                // Set values
                pstmt3.setLong(1, existingBalance + amount);
                pstmt3.setString(2, transferToCard);
                // Execute
                pstmt3.executeUpdate();
                // Print result
                System.out.println("Success!");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
