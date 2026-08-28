import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    // Shared data protected by the read/write lock.
    private int value = 0;

    // Provides separate read and write locks.
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public int read() {

        // Multiple threads can hold the read lock concurrently
        // when no writer holds the write lock.
        lock.readLock().lock();

        try {
            System.out.println(
                Thread.currentThread().getName() + " reading: " + value
            );

            return value;

        } finally {
            // Release the read lock.
            lock.readLock().unlock();
        }
    }

    public void write(int newValue) {

        // Write access is exclusive.
        lock.writeLock().lock();

        try {
            System.out.println(
                Thread.currentThread().getName() + " writing: " + newValue
            );

            value = newValue;

        } finally {
            // Release the write lock.
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReadWriteLockExample example = new ReadWriteLockExample();

        Thread reader1 = new Thread(example::read, "Reader-1");
        Thread reader2 = new Thread(example::read, "Reader-2");
        Thread writer = new Thread(() -> example.write(100), "Writer");

        // Start multiple readers and one writer.
        reader1.start();
        reader2.start();
        writer.start();

        // Wait until all threads finish.
        reader1.join();
        reader2.join();
        writer.join();
    }
}
