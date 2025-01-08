import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Initialize accounts
        BankAccount account1 = new BankAccount(1, 5000);
        BankAccount account2 = new BankAccount(2, 3000);
        BankAccount account3 = new BankAccount(3, 7000);

        // Create TransactionSystem
        TransactionSystem transactionSystem = new TransactionSystem(Arrays.asList(account1, account2, account3));

        // Simulate transactions
        Thread t1 = new Thread(() -> transactionSystem.transfer(1, 2, 1500), "Transaction 1");
        Thread t2 = new Thread(() -> transactionSystem.transfer(2, 3, 2000), "Transaction 2");
        Thread t3 = new Thread(() -> transactionSystem.reverseTransaction(1, 2, 1000), "Reversal Transaction");

        // Start transactions
        t1.start();
        t2.start();
        t3.start();

        // Wait for transactions to complete
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Print final balances
        transactionSystem.printAccountBalances();
    }
}
