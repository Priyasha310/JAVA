import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTimeoutExample {

    public static void main(String[] args) {

        ThreadPoolExecutor poolExecutor =
                new ThreadPoolExecutor(
                        3,                              // Core pool size
                        3,                              // Maximum pool size
                        1,                              // Keep-alive time
                        TimeUnit.HOURS,                 // Keep-alive time unit
                        new ArrayBlockingQueue<>(10),  // Task queue
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()
                );

        // Submit a task to the thread pool.
        // Future<?> represents the pending result of this Runnable task.
        Future<?> futureObj = poolExecutor.submit(() -> {

            try {
                // The worker thread sleeps for 7 seconds.
                Thread.sleep(7000);

                // This message is printed by the worker thread.
                System.out.println("This is the task which thread will execute");

            } catch (InterruptedException e) {
                // Restore interrupt status when interruption occurs.
                Thread.currentThread().interrupt();
            }
        });

        // The task is still running because it sleeps for 7 seconds.
        // Therefore, isDone() will normally print false here.
        System.out.println("Is Done: " + futureObj.isDone());

        try {

            // Wait for only 2 seconds.
            // The task needs 7 seconds, so this normally throws TimeoutException.
            futureObj.get(2, TimeUnit.SECONDS);

        } catch (TimeoutException e) {

            System.out.println("TimeoutException happened");

        } catch (InterruptedException e) {

            // Restore the interrupt status.
            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            // The submitted task failed with an exception.
            e.printStackTrace();
        }

        try {

            // get() without timeout waits until the task finishes.
            futureObj.get();

            System.out.println("Task completed");

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } catch (ExecutionException e) {

            e.printStackTrace();
        }

        // Always shut down the executor when it is no longer needed.
        poolExecutor.shutdown();
    }
}