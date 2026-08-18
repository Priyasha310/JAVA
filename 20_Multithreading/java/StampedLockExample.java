import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {

    // Shared values protected by the StampedLock.
    private double x = 10;
    private double y = 20;

    // StampedLock returns a stamp when a lock is acquired.
    private final StampedLock lock = new StampedLock();

    public double distanceFromOrigin() {

        // Start with an optimistic read.
        // No normal read lock is acquired at this point.
        long stamp = lock.tryOptimisticRead();

        // Read the shared values optimistically.
        double currentX = x;
        double currentY = y;

        // Validate whether a writer changed the data during the read.
        if (!lock.validate(stamp)) {

            // Optimistic read failed, so acquire a normal read lock.
            stamp = lock.readLock();

            try {
                // Read the values again under the read lock.
                currentX = x;
                currentY = y;

            } finally {
                // Release the read lock using its stamp.
                lock.unlockRead(stamp);
            }
        }

        // Return a calculation based on the validated values.
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

    public void move(double newX, double newY) {

        // Acquire exclusive write access.
        long stamp = lock.writeLock();

        try {
            // Update both values while holding the write lock.
            x = newX;
            y = newY;

        } finally {
            // Release the write lock using its stamp.
            lock.unlockWrite(stamp);
        }
    }

    public static void main(String[] args) {

        StampedLockExample example = new StampedLockExample();

        // Perform an optimistic read.
        System.out.println("Distance: " + example.distanceFromOrigin());

        // Update the shared data using the write lock.
        example.move(30, 40);

        // Read the updated data.
        System.out.println(
            "Distance after move: " + example.distanceFromOrigin()
        );
    }
}
