class Customer implements Runnable {
    private final CoffeeShop coffeeShop; // Reference to the shared CoffeeShop
    private final String name; // Customer's name

    public Customer(CoffeeShop coffeeShop, String name) {
        this.coffeeShop = coffeeShop;
        this.name = name;
    }

    @Override
    public void run() {
        coffeeShop.placeOrder(name); // Customers place orders in the CoffeeShop
    }
}