import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, 5000);
        BankAccount account2 = new BankAccount(2, 3000);
        BankAccount account3 = new BankAccount(3, 7000);

        List<BankAccount> accountList = Arrays.asList(account1, account2, account3);
        TransactionSystem transactionSystem = new TransactionSystem(accountList);

        Thread t1 = new Thread(() -> transactionSystem.transfer(1, 2, 1000), "Thread 1");
        Thread t2 = new Thread(() -> transactionSystem.transfer(2, 3, 1500), "Thread 2");
        Thread t3 = new Thread(() -> transactionSystem.transfer(3, 1, 2000), "Thread 3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        transactionSystem.printAccountBalances();
    }
}