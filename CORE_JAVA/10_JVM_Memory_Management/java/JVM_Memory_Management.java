public class JVM_Memory_Management {
    public static void main(String[] args) {
        // Demonstrates how stack and heap memory are used in Java.
        System.out.println("Stack memory example:");
        stackExample();

        System.out.println("\nHeap memory example:");
        heapExample();
    }

    static void stackExample() {
        // Local primitives and references live on the stack.
        int age = 25; // primitive stored in stack frame
        Student s = new Student("Priyasha"); // reference stored in stack, object created on heap
        System.out.println("age = " + age + ", student = " + s.name);
    }

    static void heapExample() {
        // Objects created with 'new' are stored on the heap.
        Student s1 = new Student("Priyasha");
        Student s2 = new Student("Rahul");
        System.out.println("s1 = " + s1.name + ", s2 = " + s2.name);
    }
}

class Student {
    // Instance fields are stored inside heap objects.
    String name;

    Student(String name) {
        // Constructor initializes the object state.
        this.name = name;
    }
}
