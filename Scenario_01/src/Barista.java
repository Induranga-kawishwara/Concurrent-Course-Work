class Barista extends Thread {
    private final CoffeeShop coffeeShop;

    public Barista(CoffeeShop coffeeShop) {
        this.coffeeShop = coffeeShop;
    }

    @Override
    public void run() {
        try {
            while (true) {
                coffeeShop.prepareOrder();
                Thread.sleep(1000); // Simulate time to prepare an order
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}