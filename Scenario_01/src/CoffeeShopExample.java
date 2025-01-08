public class CoffeeShopExample {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop(5); // CoffeeShop with a queue capacity of 5 orders

        // Create barista threads
        Thread barista1 = new Thread(new Barista(coffeeShop, "Barista 1"));
        Thread barista2 = new Thread(new Barista(coffeeShop, "Barista 2"));

        // Start barista threads
        barista1.start();
        barista2.start();

        // Create and start customer threads
        for (int i = 1; i <= 10; i++) {
            String customerName = "Customer " + i;
            Thread customer = new Thread(new Customer(coffeeShop, customerName));
            customer.start();

            try {
                Thread.sleep(200); // Simulate customer arrival time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle interruption
            }
        }
    }
}