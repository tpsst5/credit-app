import java.util.Random;
import java.lang.StringBuilder;
import java.util.Scanner;

/**
 * Represents a single credit card account.
 */
public class Account {

    // Fields
    private long cardNum;
    private String pin;
    private Random random = new Random();
    private long balance;

    /**
     * Constructor to create an Account instance.
     */
    public Account() {
        setCardNum();
        setPin();
        this.balance = 0;
    }

    /**
     * Getter for cardNum.
     * @return long - cardNum
     */
    public long getCardNum() {
        return this.cardNum;
    }

    /**
     * Setter for cardNum.
     */
    public void setCardNum() {
        StringBuilder acctNum = new StringBuilder();
        // BIN
        acctNum.append("400000");
        int luhnTotal = 0;
        int tempNum = 0;
        int checksum = 0;

        // Insert account number without checksum(last digit)
        for (int i = 0; i < 9; i++) {
            acctNum.append(random.nextInt(10));
        }

        // Use Luhn algorithm to calculate checksum
        for (int i = 0; i < acctNum.length(); i++) {
            if ((i+1) % 2 != 0) {
                tempNum = Character.getNumericValue(acctNum.charAt(i)) * 2;
                if (tempNum > 9) {
                    tempNum -= 9;
                }
                luhnTotal += tempNum;
            } else {
                luhnTotal += Character.getNumericValue((acctNum.charAt(i)));
            }
        }

        // Check if total needs checksum greater than 0
        if (luhnTotal % 10 != 0) {
            checksum = 10  - (luhnTotal % 10);
        }

        acctNum.append(checksum);

        this.cardNum = Long.parseLong(String.valueOf(acctNum));
    }

    /**
     * Getter for pin number.
     * @return String - pin
     */
    public String getPin() {
        return this.pin;
    }

    /**
     * Setter for pin.
     */
    public void setPin() {
        StringBuilder newPin = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            newPin.append(random.nextInt(10));
        }

        this.pin = String.valueOf(newPin);
    }

    /**
     * Getter for balance amount.
     * @return long - balance
     */
    public long getBalance() {
        return this.balance;
    }

    /**
     * Setter for balance(Currently not used).
     * @param balanceInput - long value of balanceInput
     * @return long - balance
     */
    public long setBalance(long balanceInput) {
        return this.balance = balanceInput;
    }

    /**
     * Represents the user interface once logged into a card account.
     * @return boolean - value quit(Set to true once log out requested)
     */
    public boolean userInterface() {
        Scanner scan = new Scanner(System.in);
        int input = -1;
        boolean quit = false;

        while (true) {
            System.out.print("1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit\n" +
                    ">");
            input = scan.nextInt();
            System.out.println();

            if (input == 1) {
                System.out.println("Balance: " + this.getBalance());
                System.out.println();
            } else if (input == 2) {
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

}
