public class FloorBathroom {

    private int availableStalls;

    public FloorBathroom(int numStalls) {
        if (numStalls <= 0) {
            throw new IllegalArgumentException("Number of bathroom stalls must be greater than 0.");
        }
        // Constants for bathroom configuration
        this.availableStalls = numStalls;
    }

    // Synchronized method to enter a stall
    public synchronized void useStall(String userName) throws InterruptedException {
        while (availableStalls == 0) {
            System.out.println(userName + " is waiting for a stall.");
            wait();
        }
        availableStalls--;
        System.out.println(userName + " has entered a stall. Stalls available: " + availableStalls);
    }

    // Synchronized method to leave a stall
    public synchronized void leaveStall(String userName) {
        availableStalls++;
        System.out.println(userName + " has left a stall. Stalls available: " + availableStalls);
        notifyAll();
    }

    public static void main(String[] args) {
        final int NUM_STALLS = 6;
        final int NUM_USERS = 100;

        FloorBathroom bathroom = new FloorBathroom(NUM_STALLS);

        Runnable userTask = () -> {
            String userName = Thread.currentThread().getName();
            try {
                bathroom.useStall(userName);
                Thread.sleep((long) (Math.random() * 1000)); // Simulate bathroom usage time
                bathroom.leaveStall(userName);
            } catch (InterruptedException e) {
                System.out.println(userName + " was interrupted.");
            }
        };

        // Simulate 100 users
        for (int i = 1; i <= NUM_USERS; i++) {
            Thread user = new Thread(userTask, "User-" + i);
            user.start();
        }

        // Test exception handling for invalid stall count
        try {
            new FloorBathroom(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}
