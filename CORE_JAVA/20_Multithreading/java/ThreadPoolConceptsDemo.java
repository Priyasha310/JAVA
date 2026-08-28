import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolConceptsDemo {

    public static void main(String[] args) throws InterruptedException {

        /*
         * ThreadPoolExecutor configuration:
         *
         * corePoolSize    = 2
         * maximumPoolSize = 4
         * queue capacity  = 2
         *
         * Task flow:
         *
         * 1. Create worker threads up to corePoolSize.
         * 2. Once core threads are busy, tasks go into the queue.
         * 3. When the queue is full, create extra threads up to maximumPoolSize.
         * 4. When both the maximum threads and queue are full,
         *    the RejectedExecutionHandler is used.
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                              // Core pool size
                4,                              // Maximum pool size
                5,                              // Keep-alive time for extra threads
                TimeUnit.SECONDS,               // Keep-alive time unit
                new ArrayBlockingQueue<>(2),   // Bounded task queue
                new CustomThreadFactory(),      // Custom thread creation
                new ThreadPoolExecutor.AbortPolicy() // Reject overloaded tasks
        );

        // ---------------------------------------------------------
        // 1. SUBMIT TASKS
        // ---------------------------------------------------------

        /*
         * Submit 6 tasks.
         *
         * With:
         *   core = 2
         *   queue = 2
         *   max = 4
         *
         * Typical behavior:
         *
         * Task 1 -> Thread 1
         * Task 2 -> Thread 2
         * Task 3 -> Queue
         * Task 4 -> Queue
         * Task 5 -> Thread 3
         * Task 6 -> Thread 4
         */
        for (int i = 1; i <= 6; i++) {

            final int taskId = i;

            try {
                executor.submit(() -> {

                    System.out.println(
                            "Task " + taskId +
                            " started by " +
                            Thread.currentThread().getName()
                    );

                    try {
                        // Simulate a long-running task.
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {

                        // Restore the interrupt flag when interrupted.
                        Thread.currentThread().interrupt();

                        System.out.println(
                                "Task " + taskId + " was interrupted"
                        );
                    }

                    System.out.println(
                            "Task " + taskId +
                            " completed by " +
                            Thread.currentThread().getName()
                    );
                });

                System.out.println(
                        "Task " + taskId +
                        " submitted | Active Threads = " +
                        executor.getActiveCount() +
                        " | Queue Size = " +
                        executor.getQueue().size()
                );

            } catch (RejectedExecutionException e) {

                /*
                 * This happens when:
                 *
                 * - Maximum threads are already running
                 * - Queue is full
                 */
                System.out.println(
                        "Task " + taskId + " was rejected"
                );
            }
        }

        // ---------------------------------------------------------
        // 2. THREAD POOL INFORMATION
        // ---------------------------------------------------------

        System.out.println("\n--- Thread Pool Information ---");

        System.out.println(
                "Core Pool Size: " +
                executor.getCorePoolSize()
        );

        System.out.println(
                "Maximum Pool Size: " +
                executor.getMaximumPoolSize()
        );

        System.out.println(
                "Current Pool Size: " +
                executor.getPoolSize()
        );

        System.out.println(
                "Active Threads: " +
                executor.getActiveCount()
        );

        System.out.println(
                "Queued Tasks: " +
                executor.getQueue().size()
        );

        // ---------------------------------------------------------
        // 3. WAIT FOR TASKS
        // ---------------------------------------------------------

        Thread.sleep(7000);

        /*
         * Extra threads can be removed after being idle for
         * keepAliveTime (5 seconds).
         */
        System.out.println("\n--- After Tasks Finish ---");

        System.out.println(
                "Current Pool Size: " +
                executor.getPoolSize()
        );

        // ---------------------------------------------------------
        // 4. GRACEFUL SHUTDOWN
        // ---------------------------------------------------------

        /*
         * shutdown():
         *
         * - Stops accepting new tasks.
         * - Allows already submitted tasks to complete.
         */
        executor.shutdown();

        System.out.println(
                "Shutdown initiated: " +
                executor.isShutdown()
        );

        /*
         * Wait until all tasks finish and the executor terminates.
         */
        if (executor.awaitTermination(10, TimeUnit.SECONDS)) {

            System.out.println("Executor terminated");

        } else {

            /*
             * If tasks did not finish within the timeout,
             * attempt a forceful/best-effort shutdown.
             */
            System.out.println(
                    "Executor did not terminate in time"
            );

            executor.shutdownNow();
        }
    }

    // =============================================================
    // CUSTOM THREAD FACTORY
    // =============================================================

    /*
     * ThreadFactory controls how worker threads are created.
     *
     * Here we:
     * - Give threads meaningful names.
     * - Maintain a unique thread number.
     * - Create normal (non-daemon) threads.
     */
    static class CustomThreadFactory implements ThreadFactory {

        private final AtomicInteger threadNumber =
                new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {

            Thread thread = new Thread(
                    runnable,
                    "payment-worker-" +
                    threadNumber.getAndIncrement()
            );

            // Normal application thread.
            thread.setDaemon(false);

            // Set an explicit priority.
            thread.setPriority(Thread.NORM_PRIORITY);

            return thread;
        }
    }
}
