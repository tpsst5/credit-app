import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Fields
        Scanner scan = new Scanner(System.in);
        AccountList acctList = new AccountList();
        boolean checkQuit = false;
        int input = -1;
        String dbFileName = args[1];

        // Create database if needed
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
                acctList.addAccount(account);
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

                Account userAcct = acctList.getAccount(cardNum, pin);

                if (userAcct != null) {
                    checkQuit = userAcct.userInterface();
                }

                if (checkQuit) {
                    input = 0;
                }
            }

        } while (input != 0);

        System.out.println("Bye!");

    }

}
