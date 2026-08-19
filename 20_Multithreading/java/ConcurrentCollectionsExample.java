import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentCollectionsExample {

    public static void main(String[] args) throws InterruptedException {

        // ConcurrentHashMap is designed for concurrent access.
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        // Inserts only when the key is not already present.
        map.putIfAbsent(1, "Java");

        // Creates the value only when the key is absent.
        map.computeIfAbsent(2, key -> "Spring");

        System.out.println("ConcurrentHashMap: " + map);

        // BlockingQueue is useful for Producer-Consumer scenarios.
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);

        // put() adds an element and waits if the queue is full.
        queue.put(10);
        queue.put(20);

        // take() removes an element and waits if the queue is empty.
        System.out.println("BlockingQueue: " + queue.take());

        // ConcurrentLinkedQueue is a non-blocking concurrent queue.
        ConcurrentLinkedQueue<Integer> concurrentQueue =
            new ConcurrentLinkedQueue<>();

        // offer() adds an element.
        concurrentQueue.offer(100);

        // poll() removes an element or returns null when empty.
        System.out.println(
            "ConcurrentLinkedQueue: " + concurrentQueue.poll()
        );
    }
}
