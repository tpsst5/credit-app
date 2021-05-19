import java.util.Scanner;

/**
 * Credit Card App - Generates credit cards using the Luhn algorithm and stores them in a SQLite database created
 * using command line arguments at start up.
 *
 * @author Tim Shea
 * @version 5/18/21
 */
public class Main {

    /**
     * Main driver method for the application.
     * @param args - '-fileName, SQL-databaseName'
     */
    public static void main(String[] args) {

        // Fields
        Scanner scan = new Scanner(System.in);
        boolean quit;
        int input;
        String dbFileName = args[1];

        // Create the database if needed
        DatabaseActions.createDatabase(dbFileName);

        // Create table
        DatabaseActions.createTable(dbFileName);

        do {
            System.out.print("1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit\n" +
                    ">");

            input = scan.nextInt();
            System.out.println();

            // Check user input selection
            if (input == 1) {
                Account account = new Account();

                DatabaseActions.insert(dbFileName, String.valueOf(account.getCardNum()),
                        account.getPin(), account.getBalance());
                System.out.println("Your card has been created\n" +
                        "Your card number:\n" +
                        account.getCardNum() + "\n" +
                        "Your card PIN:\n" +
                        account.getPin() + "\n");
            } else if (input == 2) {
                // Get card number
                System.out.print("Enter your card number:\n" +
                        ">");
                long cardNum = Long.parseLong(scan.next());

                // Get PIN
                System.out.print("Enter your PIN:\n" +
                        ">");
                String pin = scan.next();
                System.out.println();

                boolean hasAccount = DatabaseActions.getCard(dbFileName, String.valueOf(cardNum), pin);

                if (hasAccount) {
                    System.out.println("You have successfully logged in!");
                    System.out.println();
                    AccountUI accountUI = new AccountUI();
                    quit = accountUI.startAccountUI(dbFileName, String.valueOf(cardNum), pin);

                    if (quit) {
                        input = 0;
                    }

                } else {
                    System.out.println("Wrong card number or PIN!");
                }

            }

        } while (input != 0);

        System.out.println("Bye!");

    }

}
