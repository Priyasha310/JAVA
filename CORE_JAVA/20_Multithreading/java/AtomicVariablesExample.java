import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariablesExample {

    // AtomicInteger provides thread-safe atomic operations on an int.
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {

        // get() returns the current value.
        System.out.println("Initial: " + counter.get());

        // incrementAndGet() increments first and returns the new value.
        System.out.println(
            "incrementAndGet(): " + counter.incrementAndGet()
        );

        // getAndIncrement() returns the old value and then increments.
        System.out.println(
            "getAndIncrement(): " + counter.getAndIncrement()
        );

        // addAndGet(10) adds 10 and returns the new value.
        System.out.println(
            "addAndGet(10): " + counter.addAndGet(10)
        );

        // compareAndSet() updates only when the current value
        // is equal to the expected value.
        boolean updated = counter.compareAndSet(12, 100);

        System.out.println("CAS successful: " + updated);
        System.out.println("Final value: " + counter.get());
    }
}
