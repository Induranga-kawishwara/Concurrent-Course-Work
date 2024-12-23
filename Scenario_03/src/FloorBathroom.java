public class FloorBathroom {
    public static void main(String[] args) {
        Bathroom bathroom = new Bathroom(6); // 6 stalls

        for (int i = 1; i <= 100; i++) {
            new Person(bathroom, "Person " + i).start();
        }
    }
}