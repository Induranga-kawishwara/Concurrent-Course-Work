import java.util.concurrent.Semaphore;

class Bathroom {
    private final Semaphore stalls;

    public Bathroom(int stallCount) {
        this.stalls = new Semaphore(stallCount);
    }

    public void useBathroom(String person) {
        try {
            stalls.acquire();
            System.out.println(person + " is using a stall.");
            Thread.sleep(1000); // Simulate time in the bathroom
            System.out.println(person + " is leaving the stall.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            stalls.release();
        }
    }
}