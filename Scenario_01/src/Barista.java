class Barista implements Runnable {
    private final CoffeeShop coffeeShop; // Reference to the shared CoffeeShop
    private final String name; // Barista's name

    public Barista(CoffeeShop coffeeShop, String name) {
        this.coffeeShop = coffeeShop;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                coffeeShop.prepareOrder(name); // Baristas prepare orders from the CoffeeShop
                Thread.sleep(1000); // Simulate time taken to prepare an order
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Handle interruption
        }
    }
}