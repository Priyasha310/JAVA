import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerExample {

    // Shared buffer used by both Producer and Consumer.
    private static final Queue<Integer> buffer = new LinkedList<>();

    // Maximum number of elements allowed in the buffer.
    private static final int CAPACITY = 5;

    // Producer adds items to the shared buffer.
    static class Producer implements Runnable {

        @Override
        public void run() {

            for (int i = 1; i <= 10; i++) {

                synchronized (buffer) {

                    // Wait while the buffer is full.
                    while (buffer.size() == CAPACITY) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            // Restore interrupt status and stop the thread.
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // Add the produced item to the buffer.
                    buffer.add(i);

                    System.out.println(
                        "Produced: " + i
                        + " | Buffer size: " + buffer.size()
                    );

                    // Wake up waiting Producer/Consumer threads.
                    buffer.notifyAll();
                }
            }
        }
    }

    // Consumer removes items from the shared buffer.
    static class Consumer implements Runnable {

        @Override
        public void run() {

            for (int i = 1; i <= 10; i++) {

                synchronized (buffer) {

                    // Wait while the buffer is empty.
                    while (buffer.isEmpty()) {
                        try {
                            buffer.wait();
                        } catch (InterruptedException e) {
                            // Restore interrupt status and stop the thread.
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // Remove the next item from the buffer.
                    int item = buffer.remove();

                    System.out.println(
                        "Consumed: " + item
                        + " | Buffer size: " + buffer.size()
                    );

                    // Wake up waiting Producer/Consumer threads.
                    buffer.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {

        // Create Producer and Consumer threads.
        Thread producer = new Thread(new Producer(), "Producer");
        Thread consumer = new Thread(new Consumer(), "Consumer");

        producer.start();
        consumer.start();
    }
}
