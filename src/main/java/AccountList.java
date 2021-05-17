import java.util.Arrays;

public class AccountList {

    // Fields
    private Account[] accountArray;

    // Constructor for AccountList object
    public AccountList() {
        accountArray = new Account[0];
    }

    // Add account
    public void addAccount(Account account) {
        accountArray = Arrays.copyOf(accountArray, accountArray.length + 1);
        accountArray[accountArray.length - 1] = account;
    }

    // Get account
    public Account getAccount(long cardNum, String pin) {
        Account acct = null;

        // Iterate through accounts to find match
        for (Account currAcct : accountArray) {
            if (currAcct.getCardNum() == cardNum && currAcct.getPin().equals(pin)) {
                acct = currAcct;
                System.out.println("You have successfully logged in!");
                break;
            }
        }

        if (acct == null) {
            System.out.println("Wrong card number or PIN!");
        }

        System.out.println();

        return acct;
    }

}
