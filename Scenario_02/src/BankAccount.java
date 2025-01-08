import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final int id; // Unique account ID
    private double balance; // Account balance
    private final Lock lock = new ReentrantLock(); // Lock for thread-safe operations

    public BankAccount(int id, double initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        lock.lock(); // Acquire lock for thread-safe read
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public void deposit(double amount) {
        lock.lock(); // Acquire lock for thread-safe operation
        try {
            balance += amount;
            System.out.println("Deposited " + amount + " into account " + id + " : current balance: " + balance);
        } finally {
            lock.unlock(); // Release lock
        }
    }

    public void withdraw(double amount) {
        lock.lock(); // Acquire lock for thread-safe operation
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew " + amount + " from account " + id + " : current balance: " + balance);
            } else {
                System.out.println("Insufficient funds to withdraw " + amount + " from account " + id
                        + " : current balance: " + balance);
            }
        } finally {
            lock.unlock(); // Release lock
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}
