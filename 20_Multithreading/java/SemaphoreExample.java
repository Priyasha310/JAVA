import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    // Only two threads can hold permits at the same time.
    private static final Semaphore semaphore = new Semaphore(2);

    private static void useResource() {

        try {
            // Acquire one permit.
            // If no permit is available, this thread waits.
            semaphore.acquire();

            try {
                System.out.println(
                    Thread.currentThread().getName() + " acquired permit"
                );

                // Simulate using a limited resource.
                Thread.sleep(1000);

            } finally {
                // Always return the permit after using the resource.
                semaphore.release();

                System.out.println(
                    Thread.currentThread().getName() + " released permit"
                );
            }

        } catch (InterruptedException e) {

            // Restore interrupt status if the thread is interrupted.
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[5];

        // Create five workers competing for only two permits.
        for (int i = 0; i < threads.length; i++) {

            threads[i] = new Thread(
                SemaphoreExample::useResource,
                "Worker-" + (i + 1)
            );

            threads[i].start();
        }

        // Wait for every worker to finish.
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(
            "Available permits: " + semaphore.availablePermits()
        );
    }
}
