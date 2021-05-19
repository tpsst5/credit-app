import java.util.Scanner;

/**
 * AccountUI - User interface for a single account that can be used once logged in.
 *
 * @author Tim Shea
 * @version 5/18/21
 */
public class AccountUI {

    /**
     * User interface for when logged into account.
     * @param fileName - String value of the file name
     * @param cardNum - String value of the card number
     * @param pinIn - String value of the account pin
     * @return boolean value 'quit' - false to quit
     */
    public boolean startAccountUI(String fileName, String cardNum, String pinIn) {

        Scanner scan = new Scanner(System.in);
        int input;
        boolean quit = false;

        while (true) {
            System.out.print("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit\n" +
                    ">");
            input = scan.nextInt();
            System.out.println();

            if (input == 1) {
                System.out.println("Balance: " + DatabaseActions.getBalance(fileName, cardNum, pinIn));
                System.out.println();
            } else if (input == 2) {
                System.out.print("Enter income:\n>");
                long addedAmt = scan.nextLong();
                DatabaseActions.addIncome(fileName, cardNum, pinIn, addedAmt);
                System.out.println();
            } else if (input == 3) {
                System.out.print("Transfer\n" +
                        "Enter card number:\n" +
                        ">");
                String transferToCard = scan.next();
                // Check if card user wishes to transfer from is valid and has the funds
                if (!passesLuhnTest(transferToCard)) {
                    System.out.println("Probably you made a mistake in the card number. Please try again!\n");
                } else if (transferToCard.equals(cardNum)) {
                    System.out.println("You can't transfer money to the same account!\n");
                } else if (!DatabaseActions.getCard(fileName, transferToCard)) {
                    System.out.println("Such a card does not exist.\n");
                } else {
                    System.out.print("Enter how much money you want to transfer:\n" +
                            ">");
                    int amountToTransfer = scan.nextInt();
                    DatabaseActions.transfer(fileName, cardNum, pinIn, transferToCard, amountToTransfer);
                    System.out.println();
                }

            } else if (input == 4) {
                DatabaseActions.closeAccount(fileName, cardNum, pinIn);
                System.out.println();
                break;
            } else if (input == 5) {
                System.out.println("You have successfully logged out!");
                System.out.println();
                break;
            } else if (input == 0) {
                quit = true;
                break;
            }

        }

        return quit;

    }

    /**
     * Checks whether the input card number passes the Luhn algorithm test.
     * @param cardNum - String value of card number
     * @return boolean - true if card number passes the Luhn algorithm
     */
    public boolean passesLuhnTest(String cardNum) {
        StringBuilder cardLuhnCopy = new StringBuilder();
        int newNum;
        int total = 0;

        for (int i = 0; i < cardNum.length(); i++) {
            // Check for odd index
            if ((i + 1) % 2 != 0) {
                newNum = Character.getNumericValue(cardNum.charAt(i)) * 2;
                if (newNum > 9) {
                    newNum -= 9;
                }
                cardLuhnCopy.append(newNum);
            } else {
                cardLuhnCopy.append(cardNum.charAt(i));
            }
        }

        for (int i = 0; i < cardLuhnCopy.length(); i++) {
            total += Character.getNumericValue(cardLuhnCopy.charAt(i));
        }

        // Check whether card number entered passes the Luhn algorithm test
        return (total % 10 == 0);

    }

}
