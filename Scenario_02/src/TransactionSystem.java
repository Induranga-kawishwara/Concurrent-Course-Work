import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionSystem {
    private final Map<Integer, BankAccount> accounts = new HashMap<>();

    public TransactionSystem(List<BankAccount> accountList) {
        for (BankAccount account : accountList) {
            accounts.put(account.getId(), account);
        }
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        BankAccount fromAccount = accounts.get(fromAccountId);
        BankAccount toAccount = accounts.get(toAccountId);

        if (fromAccount == null || toAccount == null) {
            System.out.println("Invalid account IDs: " + fromAccountId + ", " + toAccountId);
            return false;
        }

        BankAccount firstLock = fromAccountId < toAccountId ? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        firstLock.lock();
        try {
            secondLock.lock();
            try {
                if (fromAccount.getBalance() >= amount) {
                    fromAccount.withdraw(amount);
                    toAccount.deposit(amount);
                    System.out.println("Transferred " + amount + " from account " + fromAccountId + " to account " + toAccountId);
                    System.out.println("Current balances -> Account " + fromAccountId + ": " + fromAccount.getBalance()
                            + ", Account " + toAccountId + ": " + toAccount.getBalance());
                    System.out.println("\n");
                    return true;
                } else {
                    System.out.println("Transfer failed: insufficient funds in account " + fromAccountId);
                    System.out.println("\n");
                    return false;
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public void reverseTransaction(int fromAccountId, int toAccountId, double amount) {
        System.out.println("Reversing transaction: " + amount + " from account " + fromAccountId + " to account " + toAccountId);
        if (transfer(toAccountId, fromAccountId, amount)) {
            System.out.println("Reversal successful. After reversal -> Account " + fromAccountId + ": "
                    + accounts.get(fromAccountId).getBalance() + ", Account " + toAccountId + ": "
                    + accounts.get(toAccountId).getBalance());
        } else {
            System.out.println("Reversal failed: Insufficient funds in account " + toAccountId);
        }
    }

    public void printAccountBalances() {
        System.out.println("\nFinal Account Balances:");
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + ": " + account.getBalance());
        }
    }
}
