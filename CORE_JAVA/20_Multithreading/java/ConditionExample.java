import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    // Shared value produced by the Producer and consumed by the Consumer.
    private static int value;

    // Indicates whether a value is available for consumption.
    private static boolean available = false;

    // Explicit lock associated with the Condition.
    private static final ReentrantLock lock = new ReentrantLock();

    // Condition used by the Consumer to wait for a value.
    private static final Condition valueAvailable = lock.newCondition();

    static class Producer implements Runnable {

        @Override
        public void run() {

            // A Condition must be used while holding its associated lock.
            lock.lock();

            try {
                // Produce the shared value.
                value = 100;
                available = true;

                System.out.println("Produced: " + value);

                // Wake one thread waiting on this condition.
                valueAvailable.signal();

            } finally {
                // Always release the lock.
                lock.unlock();
            }
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {

            // Acquire the associated lock before calling await().
            lock.lock();

            try {

                // Re-check the condition after every wake-up.
                while (!available) {

                    try {
                        // await() releases the lock while waiting.
                        // The lock is re-acquired before await() returns.
                        valueAvailable.await();

                    } catch (InterruptedException e) {

                        // Restore interrupt status and stop the consumer.
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Consume the produced value.
                System.out.println("Consumed: " + value);

                available = false;

            } finally {
                // Release the associated lock.
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // Start the consumer first so it can wait for a value.
        Thread consumer = new Thread(new Consumer(), "Consumer");
        consumer.start();

        // Give the consumer time to enter await().
        Thread.sleep(100);

        // Start the producer, which creates the value and signals the consumer.
        Thread producer = new Thread(new Producer(), "Producer");
        producer.start();

        // Wait for both threads to complete.
        consumer.join();
        producer.join();
    }
}
