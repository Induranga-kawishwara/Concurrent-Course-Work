
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class FloorBathroom {
    // Maximum number of stalls available in the bathroom
    private static final int MAX_OCCUPANCY = 6;
    // Total number of users (students and employees) who will use the bathroom
    private static final int MAX_USERS = 100;
    // Semaphore to control access to the stalls
    private static final Semaphore bathroomStalls = new Semaphore(MAX_OCCUPANCY);
    // Queue to keep track of which stalls are available
    private static final Queue<Integer> availableStalls = new LinkedList<>();
    // Lock object to ensure thread safety when accessing the queue
    private static final Object stallLock = new Object();

    public static void main(String[] args) {
        try {
            // Check if the number of stalls or users is valid
            if (MAX_OCCUPANCY <= 0 || MAX_USERS <= 0) {
                throw new IllegalArgumentException("Number of stalls or users cannot be zero or negative.");
            }

            // Initialize the queue with all available stalls
            for (int i = 1; i <= MAX_OCCUPANCY; i++) {
                availableStalls.add(i);
            }

            // Create and start a thread for each user
            for (int i = 1; i <= MAX_USERS; i++) {
                // Alternate between students and employees
                String userType = (i % 2 == 0) ? "Student" : "Employee";
                int userId = i;

                // Start a new thread for the user
                new Thread(() -> useBathroom(userId, userType)).start();
            }
        } catch (IllegalArgumentException e) {
            // Print an error message if initialization fails
            System.out.println("Initialization Error: " + e.getMessage());
        }
    }

    private static void useBathroom(int userId, String userType) {
        int stallNumber = 0;

        try {
            // Try to acquire a stall
            bathroomStalls.acquire();

            synchronized (stallLock) {
                // Get an available stall from the queue
                stallNumber = availableStalls.poll();
                System.out.println(userType + " id: " + userId + " is using stall " + stallNumber + " : now available stalls : " + availableStalls.size());
            }

            // Simulate the time spent in the bathroom
            Thread.sleep((long) (Math.random() * 2000) + 3000); // Simulate 3-5 seconds usage

            synchronized (stallLock) {
                // Add the stall back to the queue after use
                availableStalls.add(stallNumber);
                System.out.println(userType + " id: " + userId + " has finished using stall " + stallNumber + " : now available stalls : " + availableStalls.size());
            }
        } catch (InterruptedException e) {
            // Print an error message if the thread is interrupted
            System.out.println(userType + " id: " + userId + " was interrupted: " + e.getMessage());
        } finally {
            // Release the semaphore
            bathroomStalls.release();
        }
    }
}
