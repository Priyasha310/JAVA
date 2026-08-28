public class VolatileExample {

    // volatile guarantees visibility of changes between threads.
    // It does NOT make compound operations such as count++ atomic.
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {

            // The worker repeatedly checks the shared flag.
            while (running) {
                // Simulate background work.
            }

            System.out.println("Worker stopped");
        });

        worker.start();

        // Give the worker time to start.
        Thread.sleep(100);

        // Because running is volatile, the worker can observe this change.
        running = false;

        // Wait for the worker to finish.
        worker.join();

        System.out.println("Main thread finished");
    }
}
