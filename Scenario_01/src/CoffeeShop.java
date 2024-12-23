import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class CoffeeShop {
    private final BlockingQueue<String> orderQueue;

    public CoffeeShop(int capacity) {
        this.orderQueue = new LinkedBlockingQueue<>(capacity);
    }

    public void placeOrder(String order) throws InterruptedException {
        orderQueue.put(order); // Blocks if the queue is full
        System.out.println("Order placed: " + order);
    }

    public void prepareOrder() throws InterruptedException {
        String order = orderQueue.take(); // Blocks if the queue is empty
        System.out.println("Order prepared: " + order);
    }
}