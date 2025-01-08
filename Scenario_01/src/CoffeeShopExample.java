public class CoffeeShopExample {
    public static void main(String[] args) {
        // Create a CoffeeShop instance with a capacity of 5 orders
        CoffeeShop coffeeShop = new CoffeeShop(5); // Shared resource with capacity 5

        // Create two Barista threads to prepare orders
        Thread barista1 = new Thread(new Barista(coffeeShop), "Barista 1");
        Thread barista2 = new Thread(new Barista(coffeeShop), "Barista 2");

        // Start the Barista threads
        barista1.start();
        barista2.start();

        // Create and start 10 Customer threads to place orders
        for (int i = 1; i <= 10; i++) {
            Thread customer = new Thread(new Customer(coffeeShop), "Customer " + i);
            customer.start();
            try {
                // Pause for 200 milliseconds to simulate customer arrival time
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // Restore the interrupted status
                Thread.currentThread().interrupt();
            }
        }
    }
}