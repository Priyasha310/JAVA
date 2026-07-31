public class Java_Methods {

    public static void main(String[] args) {
        // User-defined method example
        greet();

        // Method overloading examples
        System.out.println("add(int, int) = " + Calculator.add(2, 3));
        System.out.println("add(int, int, int) = " + Calculator.add(2, 3, 4));
        System.out.println("add(double, double) = " + Calculator.add(2.5, 3.5));

        // Method overriding example
        Animal obj = new Dog();
        obj.sound();

        // Static method example
        Calculator.display();

        // Instance method example
        Student student = new Student();
        student.study();

        // Abstract method example
        Animal2 animal2 = new Dog2();
        animal2.sound();
    }

    public static void greet() {
        System.out.println("Welcome to Java");
    }
}

class Calculator {
    static void display() {
        System.out.println("Static Method");
    }

    static int add(int a, int b) {
        return a + b;
    }

    static int add(int a, int b, int c) {
        return a + b + c;
    }

    static double add(double a, double b) {
        return a + b;
    }
}

class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Student {
    void study() {
        System.out.println("Studying...");
    }
}

abstract class Animal2 {
    abstract void sound();
}

class Dog2 extends Animal2 {
    @Override
    void sound() {
        System.out.println("Dog2 barks");
    }
}
