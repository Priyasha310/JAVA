// Demonstrates basic garbage collection concepts in Java.
// This class shows when objects become eligible for GC based on reachability.
public class Garbage_Collection {
    public static void main(String[] args) {
        // Example 1: After the only reference is removed, the object becomes unreachable.
        System.out.println("Example 1: Object becomes unreachable when reference is set to null.");
        Student s = new Student("Priyasha");
        s = null; // The Student object has no reachable references and becomes eligible for GC.
        System.out.println("Student reference set to null.");

        // Example 2: The object is still reachable through another reference, so it is not eligible for GC.
        System.out.println("\nExample 2: Object is still reachable through another reference.");
        Student s1 = new Student("Priyasha");
        Student s2 = s1; // s2 now refers to the same object as s1.
        s1 = null;      // s1 no longer references the object, but s2 still does.
        System.out.println("s2.name = " + s2.name);

        // Requesting garbage collection is only a hint to the JVM. It may or may not run immediately.
        System.out.println("\nRequesting garbage collection (not guaranteed):");
        System.gc();
        System.out.println("System.gc() has been requested.");
    }
}

// Represents a simple object used to demonstrate reachability and GC.
class Student {
    // Instance field that is stored inside the object on the heap.
    String name;

    Student(String name) {
        this.name = name;
        System.out.println("Created Student: " + name);
    }

    @Override
    protected void finalize() throws Throwable {
        // finalize() may execute before the object is reclaimed, but it is deprecated and should not be relied upon.
        System.out.println("Student object is being finalized: " + name);
        super.finalize();
    }
}
