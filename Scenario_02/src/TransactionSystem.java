import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionSystem {
    // A map to store bank accounts with their IDs as keys
    private final Map<Integer, BankAccount> accounts = new HashMap<>();

    // Constructor to initialize the system with a list of bank accounts
    public TransactionSystem(List<BankAccount> accountList) {
        for (BankAccount account : accountList) {
            accounts.put(account.getId(), account); // Add each account to the map
        }
    }

    // Method to transfer money between two accounts
    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        BankAccount fromAccount = accounts.get(fromAccountId); // Get the source account
        BankAccount toAccount = accounts.get(toAccountId); // Get the destination account

        // Check if both accounts exist
        if (fromAccount == null || toAccount == null) {
            System.out.println("Invalid account IDs: " + fromAccountId + ", " + toAccountId);
            return false;
        }

        // Determine the order of locking to avoid deadlock
        BankAccount firstLock = fromAccountId < toAccountId ? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        firstLock.lock(); // Lock the first account
        try {
            secondLock.lock(); // Lock the second account
            try {
                // Check if the source account has enough balance
                if (fromAccount.getBalance() >= amount) {
                    fromAccount.withdraw(amount); // Withdraw the amount from the source account
                    toAccount.deposit(amount); // Deposit the amount to the destination account
                    System.out.println("Transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId);
                    System.out.println("Current balances -> Account " + fromAccountId + ": " + fromAccount.getBalance()
                            + ", Account " + toAccountId + ": " + toAccount.getBalance());
                    System.out.println("\n");
                    return true; // Transfer successful
                } else {
                    System.out.println("Transfer failed: insufficient funds in account " + fromAccountId);
                    System.out.println("\n");
                    return false; // Transfer failed due to insufficient funds
                }
            } finally {
                secondLock.unlock(); // Always unlock the second account
            }
        } finally {
            firstLock.unlock(); // Always unlock the first account
        }
    }

    // Method to reverse a transaction by transferring money back
    public void reverseTransaction(int fromAccountId, int toAccountId, double amount) {
        System.out.println("Reversing transaction: " + amount + " from account " + fromAccountId + " to account " + toAccountId);
        if (transfer(toAccountId, fromAccountId, amount)) { // Attempt to transfer the amount back
            System.out.println("Reversal successful. After reversal -> Account " + fromAccountId + ": "
                    + accounts.get(fromAccountId).getBalance() + ", Account " + toAccountId + ": "
                    + accounts.get(toAccountId).getBalance());
        } else {
            System.out.println("Reversal failed: Insufficient funds in account " + toAccountId);
        }
    }

    // Method to print the balances of all accounts
    public void printAccountBalances() {
        System.out.println("\nFinal Account Balances:");
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + ": " + account.getBalance());
        }
    }
}