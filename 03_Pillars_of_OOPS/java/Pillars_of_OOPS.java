public class Pillars_of_OOPS {

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("2 + 3 = " + calc.add(2, 3));
        System.out.println("2 + 3 + 4 = " + calc.add(2, 3, 4));

        Employee employee = new Employee();
        employee.setSalary(5000);
        System.out.println("Salary = " + employee.getSalary());

        Car car = new CarImplementation();
        car.applyBrake();
        car.pressHorn();

        Animal animal = new Dog();
        animal.sound();

        C c = new C();
        System.out.println("Created class C implementing A and B: " + c.getClass().getSimpleName());
    }
}

interface Car {
    void applyBrake();
    void pressHorn();
}

class CarImplementation implements Car {
    @Override
    public void applyBrake() {
        System.out.println("Brake applied.");
    }

    @Override
    public void pressHorn() {
        System.out.println("Horn pressed.");
    }
}

class Employee {
    private int salary;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        if (salary > 0) {
            this.salary = salary;
        }
    }
}

interface A {}
interface B {}

class C implements A, B {}

class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
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
