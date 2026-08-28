public class Concrete_Abstract_Super_Sub_Object_Nested_Class {
    public static void main(String[] args) {
        // Concrete class example: a normal class with fully implemented methods.
        System.out.println("-- Concrete Class Example --");
        Employee emp = new Employee();
        emp.work();

        // Abstract class example: cannot instantiate directly, but can be extended.
        // Dog is both a super class extension example and a subclass implementation.
        System.out.println("\n-- Abstract Class / Super Class / Sub Class Example --");
        Dog dog = new Dog();
        dog.eat();
        dog.sound();

        // Runtime polymorphism: a superclass reference holds a subclass object.
        Animal animal = new Dog();
        System.out.println("Runtime polymorphism with Animal reference:");
        animal.eat();
        animal.sound();

        // Object class example: every class implicitly extends java.lang.Object.
        System.out.println("\n-- Object Class Example --");
        MyObject obj = new MyObject("Priyasha");
        System.out.println(obj);

        // Nested class examples: static nested class, member inner class, local inner class, anonymous class.
        System.out.println("\n-- Nested Class Examples --");
        Outer.StaticNested staticNested = new Outer.StaticNested();
        staticNested.display();

        Outer outer = new Outer("outer-data");
        Outer.Inner inner = outer.new Inner();
        inner.display();

        Outer.Displayable local = outer.createLocalInner();
        local.display();

        Outer.Displayable anon = outer.createAnonymous();
        anon.display();
    }
}

/**
 * Concrete class example: a regular class with an implemented method.
 */
class Employee {
    void work() {
        System.out.println("Employee is working");
    }
}

/**
 * Abstract class example: serves as a base class with some shared behavior.
 * It can declare abstract methods that subclasses must implement.
 */
abstract class Animal {
    Animal() {
        System.out.println("Animal Constructor");
    }

    abstract void sound();

    void eat() {
        System.out.println("Animal eats");
    }
}

/**
 * Dog is a subclass of Animal and provides a concrete implementation
 * of the abstract sound() method.
 */
class Dog extends Animal {
    Dog() {
        System.out.println("Dog Constructor");
    }

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

/**
 * Simple class demonstrating Object class inheritance and overriding toString().
 */
class MyObject {
    private final String name;

    MyObject(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyObject{name='" + name + "'}";
    }
}

/**
 * Outer class containing nested class examples.
 */
class Outer {
    private final String outerData;

    Outer(String outerData) {
        this.outerData = outerData;
    }

    /**
     * Static nested class: behaves like a static member of the outer class.
     * It can be instantiated without an outer instance.
     */
    static class StaticNested {
        void display() {
            System.out.println("Static Nested Class");
        }
    }

    /**
     * Member inner class: requires an instance of the outer class.
     * It can access instance members of the outer class.
     */
    class Inner {
        void display() {
            System.out.println("Member Inner Class can access outerData = " + outerData);
        }
    }

    /**
     * Interface used to demonstrate local and anonymous inner classes.
     */
    interface Displayable {
        void display();
    }

    /**
     * Local inner class example: defined inside a method and scoped to that method.
     */
    Displayable createLocalInner() {
        class Local implements Displayable {
            @Override
            public void display() {
                System.out.println("Local Inner Class");
            }
        }

        return new Local();
    }

    /**
     * Anonymous inner class example: defined and instantiated in one expression.
     */
    Displayable createAnonymous() {
        return new Displayable() {
            @Override
            public void display() {
                System.out.println("Anonymous Inner Class");
            }
        };
    }
}
