// EmployeePayroll.java

abstract class Employee {

    private int eId;
    private String eName;

    // Constructor
    public Employee(int eId, String eName) {
        this.eId = eId;
        this.eName = eName;
    }

    public int getId() {
        return eId;
    }

    public String getName() {
        return eName;
    }

    // Every employee type must implement its own salary calculation.
    public abstract int calculateSalary();
}


// Permanent Employee
class Permanent extends Employee {

    private int salary;

    public Permanent(int eId, String eName, int salary) {
        super(eId, eName);
        this.salary = salary;
    }

    @Override
    public int calculateSalary() {
        return salary;
    }
}


// Contract Employee
class Contract extends Employee {

    private int hoursWorked;
    private int hourlyRate;

    public Contract(int eId, String eName, int hoursWorked, int hourlyRate) {
        super(eId, eName);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public int calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}


// Main class
public class EmployeePayroll_Abstract {

    public static void main(String[] args) {

        // Runtime polymorphism
        Employee permanent =
                new Permanent(1, "Rahul", 10000);

        Employee contract =
                new Contract(2, "Priya", 5, 1000);

        System.out.println(
                "Employee ID: " + permanent.getId()
                + ", Name: " + permanent.getName()
                + ", Salary: " + permanent.calculateSalary()
        );

        System.out.println(
                "Employee ID: " + contract.getId()
                + ", Name: " + contract.getName()
                + ", Salary: " + contract.calculateSalary()
        );
    }
}