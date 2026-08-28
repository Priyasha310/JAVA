/**
 * ThreadConceptsDemo.java
 * 
 * Comprehensive demonstration of key multithreading concepts:
 * 1. Thread.join() - Waiting for another thread to complete
 * 2. Thread Priority - Setting execution priority hints (1-10)
 * 3. Daemon Threads - Background threads that don't prevent JVM exit
 * 
 * Run with: javac ThreadConceptsDemo.java && java ThreadConceptsDemo
 */

public class ThreadConceptsDemo {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("========================================");
        System.out.println("MULTITHREADING CONCEPTS DEMONSTRATION");
        System.out.println("========================================\n");

        // Example 1: Thread.join()
        System.out.println(">>> EXAMPLE 1: Thread.join()\n");
        demonstrateJoin();

        System.out.println("\n----------------------------------------\n");

        // Example 2: Thread Priority
        System.out.println(">>> EXAMPLE 2: Thread Priority\n");
        demonstratePriority();

        System.out.println("\n----------------------------------------\n");

        // Example 3: Daemon Threads
        System.out.println(">>> EXAMPLE 3: Daemon Threads\n");
        demonstrateDaemon();

        System.out.println("\n========================================");
        System.out.println("ALL EXAMPLES COMPLETED");
        System.out.println("========================================\n");
    }

    // ==================== EXAMPLE 1: Thread.join() ====================

    /**
     * Demonstrates Thread.join() behavior
     * 
     * join() makes the current thread wait until another thread finishes.
     * This is useful when one thread needs to wait for another to complete
     * before proceeding with dependent work.
     * 
     * Output order:
     * - Worker finished
     * - Main thread continues
     */
    private static void demonstrateJoin() throws InterruptedException {

        System.out.println("Concept: join() makes current thread wait for another thread\n");

        // Create a worker thread that simulates work
        Thread worker = new Thread(() -> {

            try {
                // Simulate some work by sleeping for 2 seconds
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Restore the interrupt status if interrupted
                Thread.currentThread().interrupt();
            }

            System.out.println("[Worker Thread] Work completed!");

        });

        System.out.println("[Main Thread] Starting worker thread...");
        worker.start();

        System.out.println("[Main Thread] Calling join() to wait for worker to finish...");
        
        // Main thread waits until worker thread completes
        // join() blocks the main thread until worker.run() finishes
        worker.join();

        // This line executes only after worker finishes (due to join())
        System.out.println("[Main Thread] Resuming execution (worker is done)");
    }

    // ==================== EXAMPLE 2: Thread Priority ====================

    /**
     * Demonstrates Thread Priority behavior
     * 
     * Every Java thread has a priority that influences thread scheduling.
     * Available priorities:
     * - Thread.MIN_PRIORITY = 1
     * - Thread.NORM_PRIORITY = 5 (default)
     * - Thread.MAX_PRIORITY = 10
     * 
     * IMPORTANT: Higher priority does NOT guarantee earlier execution.
     * Priority is just a scheduling hint; actual execution order depends on OS scheduler.
     */
    private static void demonstratePriority() {

        System.out.println("Concept: Thread priority is a scheduling hint (1-10)\n");
        System.out.println("MIN_PRIORITY = " + Thread.MIN_PRIORITY);
        System.out.println("NORM_PRIORITY = " + Thread.NORM_PRIORITY);
        System.out.println("MAX_PRIORITY = " + Thread.MAX_PRIORITY);
        System.out.println();

        // Create a low-priority thread
        Thread lowPriorityThread = new Thread(() -> {

            // Task for the low-priority thread
            System.out.println("[Low Priority Thread] Executing (priority = " 
                + Thread.currentThread().getPriority() + ")");

        }, "LowPriorityThread");

        // Create a high-priority thread
        Thread highPriorityThread = new Thread(() -> {

            // Task for the high-priority thread
            System.out.println("[High Priority Thread] Executing (priority = " 
                + Thread.currentThread().getPriority() + ")");

        }, "HighPriorityThread");

        // Set priority values
        // Must be set BEFORE starting the thread (or can be set anytime before execution)
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);   // Priority 1
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);  // Priority 10

        System.out.println("Starting low priority thread (priority = 1)...");
        System.out.println("Starting high priority thread (priority = 10)...\n");

        // Start both threads
        lowPriorityThread.start();
        highPriorityThread.start();

        try {
            // Wait for both threads to complete
            lowPriorityThread.join();
            highPriorityThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nNote: Execution order may vary. Higher priority is NOT a guarantee.");
        System.out.println("Priority is just a hint to the OS scheduler.");
    }

    // ==================== EXAMPLE 3: Daemon Threads ====================

    /**
     * Demonstrates Daemon Thread behavior
     * 
     * A daemon thread is a background thread that:
     * - Performs background tasks (monitoring, cleanup, logging, etc.)
     * - Does NOT prevent the JVM from exiting
     * - Must be marked as daemon BEFORE calling start()
     * - Is automatically terminated when all user (non-daemon) threads finish
     */
    private static void demonstrateDaemon() throws InterruptedException {

        System.out.println("Concept: Daemon threads don't prevent JVM exit\n");
        System.out.println("Setting up: 1 daemon thread (background work) + main thread (user thread)\n");

        // Create a daemon thread that runs in a loop
        Thread daemonThread = new Thread(() -> {

            // Loop to simulate continuous background work
            int taskCount = 0;
            while (taskCount < 10) {

                try {
                    // Simulate background work by sleeping 500ms between iterations
                    Thread.sleep(500);
                    taskCount++;
                    System.out.println("[Daemon Thread] Running background task #" + taskCount);

                } catch (InterruptedException e) {

                    // Restore the interrupt status and stop the loop
                    Thread.currentThread().interrupt();
                    System.out.println("[Daemon Thread] Interrupted, stopping");
                    return;
                }
            }

            System.out.println("[Daemon Thread] Completed all tasks");

        });

        // IMPORTANT: Set daemon status BEFORE calling start()
        // If you call setDaemon() after start(), it will throw IllegalStateException
        daemonThread.setDaemon(true);
        System.out.println("Marked thread as daemon: isDaemon() = " + daemonThread.isDaemon());

        // Start the daemon thread
        daemonThread.start();
        System.out.println("Daemon thread started\n");

        try {
            // Main/user thread performs its work for 2.5 seconds
            System.out.println("[Main Thread] User thread running...");
            Thread.sleep(2500);

        } catch (InterruptedException e) {

            // Restore the interrupt status
            Thread.currentThread().interrupt();
        }

        // Main thread finishes
        System.out.println("[Main Thread] User thread finished");
        System.out.println("\nBehavior: Daemon thread is forcefully terminated.");
        System.out.println("JVM exits because all non-daemon threads have finished.");
        System.out.println("Remaining daemon thread(s) are killed without notice.");
    }
}

/**
 * ==================== KEY INTERVIEW POINTS ====================
 * 
 * 1. THREAD.JOIN()
 *    - Current thread waits for another thread to finish
 *    - Blocks until target thread completes
 *    - Useful for sequential dependencies
 *    - Example: Main thread waits for initialization thread
 * 
 * 2. THREAD PRIORITY
 *    - Range: 1 (MIN) to 10 (MAX), default is 5 (NORM)
 *    - Higher priority = scheduling hint for higher execution frequency
 *    - Does NOT guarantee execution order
 *    - Set before or anytime before thread execution
 *    - Affected by OS scheduler and system load
 * 
 * 3. DAEMON THREADS
 *    - Background threads that don't prevent JVM exit
 *    - MUST call setDaemon(true) BEFORE start()
 *    - Calling after start() throws IllegalStateException
 *    - JVM terminates when all non-daemon threads finish
 *    - NOT suitable for critical operations:
 *      * Don't save critical data
 *      * Don't complete important transactions
 *      * Don't write essential files
 *    - Good for: monitoring, logging, cleanup, background services
 * 
 * ==================== COMPARISON TABLE ====================
 * 
 * Concept    | Purpose                 | Key Behavior
 * -----------+-----------------------+---------------------------------
 * join()     | Wait for thread end    | Current thread blocks
 * Priority   | Influence scheduling   | Just a hint, not guaranteed
 * Daemon     | Background support     | JVM doesn't wait for it
 */
