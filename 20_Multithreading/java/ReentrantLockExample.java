import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    // Explicit lock used to protect the critical section.
    private final ReentrantLock lock = new ReentrantLock();

    public void outerMethod() {

        // Acquire the lock before entering the critical section.
        lock.lock();

        try {
            System.out.println("Inside outerMethod");

            // The same thread can acquire this ReentrantLock again.
            // This demonstrates reentrancy.
            innerMethod();

        } finally {
            // Always unlock in finally.
            lock.unlock();
        }
    }

    private void innerMethod() {

        // The current thread already owns the lock,
        // so this second lock acquisition does not block.
        lock.lock();

        try {
            System.out.println("Inside innerMethod");
        } finally {
            // Release the second lock acquisition.
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        // Create the example object.
        ReentrantLockExample example = new ReentrantLockExample();

        // Execute the reentrant locking example.
        example.outerMethod();
    }
}
