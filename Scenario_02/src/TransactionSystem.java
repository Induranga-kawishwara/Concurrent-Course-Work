import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return false;
        }

        BankAccount firstLock = fromAccountId < toAccountId ? fromAccount : toAccount;
        BankAccount secondLock = fromAccountId < toAccountId ? toAccount : fromAccount;

        firstLock.lock();
        secondLock.lock();

        try {
            if (fromAccount.getBalance() >= amount) {
                fromAccount.withdraw(amount);
                toAccount.deposit(amount);
                System.out.println("Transferred " + amount + " from Account " + fromAccountId + " to Account " + toAccountId);
                return true;
            } else {
                System.out.println("Insufficient funds in Account " + fromAccountId);
                return false;
            }
        } finally {
            secondLock.unlock();
            firstLock.unlock();
        }
    }

    public void printAccountBalances() {
        for (BankAccount account : accounts.values()) {
            System.out.println("Account " + account.getId() + " Balance: " + account.getBalance());
        }
    }
}