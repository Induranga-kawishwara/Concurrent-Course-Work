import java.util.Map;
import java.util.HashMap;
import java.util.List;

class TransactionSystem {
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
            System.out.println("Invalid account IDs for transfer");
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
                    return true;
                } else {
                    System.out.println("Transfer failed: insufficient funds in account " + fromAccountId);
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
        System.out.println("Reversing transaction: " + amount + " from Account " + toAccountId + " to Account " + fromAccountId);
        transfer(toAccountId, fromAccountId, amount);
    }

    public void printAccountBalances() {
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + " balance: " + account.getBalance());
        }
    }
}
