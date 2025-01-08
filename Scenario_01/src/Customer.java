class Customer implements Runnable {
    // Reference to the CoffeeShop instance
    private final CoffeeShop coffeeShop;

    // Constructor to initialize the CoffeeShop instance
    public Customer(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    // The run method to be executed when the thread starts
    @Override
    public void run() {
        // Create an order string with the current thread's name
        String order = "Order by " + Thread.currentThread().getName();
        // Place the order in the CoffeeShop
        coffeeShop.placeOrder(order);
    }
}