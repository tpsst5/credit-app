import java.util.Random;

/**
 * Account - Represents a single credit card account.
 *
 * @author Tim Shea
 * @version 5/18/21
 */
public class Account {

    // Fields
    private long cardNum;
    private String pin;
    private final Random random = new Random();
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
        int tempNum;
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

}
