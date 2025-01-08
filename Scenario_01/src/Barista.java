class Barista implements Runnable {
    // Reference to the CoffeeShop instance
    private final CoffeeShop coffeeShop;

    // Constructor to initialize the CoffeeShop instance
    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    // The run method to be executed when the thread starts
    @Override
    public void run() {
        try {
            // Infinite loop to continuously prepare orders
            while (true) {
                // Call the method to prepare an order
                coffeeShop.prepareOrder();
                // Pause for 1 second to simulate preparation time
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    }
}