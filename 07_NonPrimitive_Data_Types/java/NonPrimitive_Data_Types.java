// Demonstrates reference vs primitive behavior and String immutability in Java.
public class NonPrimitive_Data_Types {

    static void changePrimitive(int x) {
        x = 100; // Only the local copy changes.
    }

    static void changeReference(Student s) {
        s.name = "Rahul"; // The same object is modified.
    }

    public static void main(String[] args) {
        // Primitive type example: pass-by-value uses a copy of the value.
        int num = 10;
        changePrimitive(num);
        System.out.println("num = " + num); // prints 10

        // Reference type example: pass-by-value copies the reference.
        Student st = new Student();
        st.name = "Aman";
        changeReference(st);
        System.out.println("st.name = " + st.name); // prints Rahul

        // String immutability example.
        String s = "Java";
        s.concat(" Programming");
        System.out.println("After concat without assignment: " + s); // still Java

        s = s.concat(" Programming");
        System.out.println("After concat with assignment: " + s); // Java Programming

        // String literal vs new String()
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");

        System.out.println("s1 == s2: " + (s1 == s2));       // true, same SCP object
        System.out.println("s1 == s3: " + (s1 == s3));       // false, different heap object
        System.out.println("s1.equals(s3): " + s1.equals(s3)); // true, same contents
    }
}

class Student {
    String name;
}
