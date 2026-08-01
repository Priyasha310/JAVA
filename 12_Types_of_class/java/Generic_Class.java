/**
 * Demonstrates generic classes, bounded generics, and generic methods.
 */
public class Generic_Class {
    public static void main(String[] args) {
        // Generic Class example: Box can store any type safely.
        Box<String> box = new Box<>();
        box.setValue("Java");
        System.out.println("Box value = " + box.getValue());

        // Generic Pair example: Pair defines two type parameters K and V.
        Pair<Integer, String> student = new Pair<>(101, "Priyasha");
        System.out.println("Pair key=" + student.getKey() + ", value=" + student.getValue());

        // Bounded Generic example: Calculator accepts Number subclasses only.
        Calculator<Integer> intCalc = new Calculator<>(10);
        Calculator<Double> doubleCalc = new Calculator<>(5.5);
        System.out.println("intCalc value = " + intCalc.getNumber());
        System.out.println("doubleCalc value = " + doubleCalc.getNumber());

        // Generic Method example: Printer.print can accept any type at call time.
        Printer p = new Printer();
        p.print("Hello");
        p.print(100);
        p.print(10.5);
        p.print(true);
    }
}

/**
 * Generic class example with a single type parameter T.
 * T is the placeholder for the actual type used at instantiation.
 */
class Box<T> {
    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}

/**
 * Generic class with two type parameters: K for key and V for value.
 * Commonly used for pairs and maps.
 */
class Pair<K, V> {
    private K key;
    private V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

/**
 * Bounded generic class that restricts the type argument to subclasses of Number.
 * This ensures Calculator can work only with numeric types.
 */
class Calculator<T extends Number> {
    private T number;

    Calculator(T number) {
        this.number = number;
    }

    public T getNumber() {
        return number;
    }
}

/**
 * Generic method example: the method itself defines a type parameter T.
 * This method can print any object type without casting.
 */
class Printer {
    public <T> void print(T value) {
        System.out.println(value);
    }
}
