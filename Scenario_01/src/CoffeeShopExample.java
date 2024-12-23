public class CoffeeShopExample {
    public static void main(String[] args) {
        CoffeeShop coffeeShop = new CoffeeShop(5); // Queue capacity of 5

        Barista barista1 = new Barista(coffeeShop);
        Barista barista2 = new Barista(coffeeShop);
        barista1.start();
        barista2.start();

        for (int i = 1; i <= 10; i++) {
            new Customer(coffeeShop, "Order " + i).start();
        }
    }
}