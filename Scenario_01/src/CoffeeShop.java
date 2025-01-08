import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class CoffeeShop {
    private final Queue<String> orderQueue = new LinkedList<>(); // Queue to store customer orders
    private final int capacity; // Maximum number of orders the queue can hold
    private final Lock lock = new ReentrantLock(); // Lock to manage thread-safe access
    private final Condition full = lock.newCondition(); // Condition for full queue
    private final Condition empty = lock.newCondition(); // Condition for empty queue
    private int orderCounter = 1; // Counter to assign unique order IDs

    public CoffeeShop(int capacity) {
        this.capacity = capacity;
    }

    // Method for customers to place orders
    public void placeOrder(String customerName) {
        lock.lock(); // Acquire the lock
        try {
            while (orderQueue.size() >= capacity) {
                full.await(); // Wait if the queue is full
            }
            String order = "Order id : " + orderCounter++; // Create the order string
            orderQueue.add(customerName + " " + order); // Add the order to the queue
            System.out.println(customerName + " placed order: " + order);
            empty.signalAll(); // Notify baristas that there are orders to prepare
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interruption
        } finally {
            lock.unlock(); // Release the lock
        }
    }

    // Method for baristas to prepare orders
    public String prepareOrder(String baristaName) {
        lock.lock(); // Acquire the lock
        try {
            while (orderQueue.isEmpty()) {
                empty.await(); // Wait if the queue is empty
            }
            String order = orderQueue.poll(); // Retrieve and remove the next order from the queue
            System.out.println(baristaName + " prepared order: Order by " + order);
            full.signalAll(); // Notify customers that there is space in the queue
            return order;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interruption
            return null;
        } finally {
            lock.unlock(); // Release the lock
        }
    }
}