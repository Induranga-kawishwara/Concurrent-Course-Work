import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class CoffeeShop {
    // Queue to hold the orders
    private final Queue<String> orderQueue = new LinkedList<>();
    // Maximum capacity of the order queue
    private final int capacity;
    // Lock to control access to the order queue
    private final Lock lock = new ReentrantLock();
    // Condition to signal when the queue is full
    private final Condition full = lock.newCondition();
    // Condition to signal when the queue is empty
    private final Condition empty = lock.newCondition();

    // Constructor to initialize the CoffeeShop with a specific capacity
    public CoffeeShop(int capacity) {
        this.capacity = capacity;
    }

    // Method to place an order in the queue
    public void placeOrder(String order) {
        lock.lock(); // Acquire the lock
        try {
            // Wait if the queue is full
            while (orderQueue.size() >= capacity) {
                full.await();
            }
            // Add the order to the queue
            orderQueue.add(order);
            System.out.println(Thread.currentThread().getName() + " placed order: " + order);
            // Signal that the queue is no longer empty
            empty.signalAll();
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method to prepare an order from the queue
    public String prepareOrder() {
        lock.lock(); // Acquire the lock
        try {
            // Wait if the queue is empty
            while (orderQueue.isEmpty()) {
                empty.await();
            }
            // Remove the order from the queue
            String order = orderQueue.poll();
            System.out.println(Thread.currentThread().getName() + " prepared order: " + order);
            // Signal that the queue is no longer full
            full.signalAll();
            return order;
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock(); // Release the lock
        }
    }
}