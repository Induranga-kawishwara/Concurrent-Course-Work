class Customer extends Thread {
    private final CoffeeShop coffeeShop;
    private final String order;

    public Customer(CoffeeShop coffeeShop, String order) {
        this.coffeeShop = coffeeShop;
        this.order = order;
    }

    @Override
    public void run() {
        try {
            coffeeShop.placeOrder(order);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
