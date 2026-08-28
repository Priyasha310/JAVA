import java.util.ArrayList;
import java.util.List;

// Parent class
abstract class Person {

    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Polymorphic method
    public abstract void displayRole();

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


// Child class
class Doctor extends Person {

    private String specialization;

    public Doctor(int id, String name, int age, String specialization) {
        super(id, name, age);
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void diagnose(Patient patient) {
        System.out.println(
                "Dr. " + getName() +
                        " is diagnosing patient " +
                        patient.getName()
        );
    }

    @Override
    public void displayRole() {
        System.out.println(
                getName() + " is a Doctor - " +
                        specialization
        );
    }
}


// Child class
class Patient extends Person {

    private String disease;
    private Doctor doctor;

    public Patient(int id, String name, int age, String disease) {
        super(id, name, age);
        this.disease = disease;
    }

    public String getDisease() {
        return disease;
    }

    public void assignDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void viewDoctor() {

        if (doctor == null) {
            System.out.println(
                    "No doctor assigned to " + getName()
            );
            return;
        }

        System.out.println(
                "Doctor assigned to " +
                        getName() +
                        " : Dr. " +
                        doctor.getName()
        );
    }

    @Override
    public void displayRole() {
        System.out.println(
                getName() +
                        " is a Patient - Disease: " +
                        disease
        );
    }
}


// Child class
class Receptionist extends Person {

    public Receptionist(int id, String name, int age) {
        super(id, name, age);
    }

    public void registerPatient(Patient patient) {
        System.out.println(
                "Patient " +
                        patient.getName() +
                        " registered successfully by " +
                        getName()
        );
    }

    @Override
    public void displayRole() {
        System.out.println(
                getName() +
                        " is a Receptionist"
        );
    }
}


// Main class
public class HospitalManagement {

    public static void main(String[] args) {

        // Create Doctor
        Doctor doctor = new Doctor(
                101,
                "Rahul",
                35,
                "Cardiologist"
        );

        // Create Patient
        Patient patient = new Patient(
                201,
                "Priya",
                28,
                "Chest Pain"
        );

        // Create Receptionist
        Receptionist receptionist = new Receptionist(
                301,
                "Anita",
                30
        );


        // Receptionist registers patient
        receptionist.registerPatient(patient);


        // Assign doctor to patient
        patient.assignDoctor(doctor);


        // Doctor diagnoses patient
        doctor.diagnose(patient);


        // Patient views assigned doctor
        patient.viewDoctor();


        // Polymorphism
        System.out.println("\n--- Hospital Staff ---");

        List<Person> people = new ArrayList<>();

        people.add(doctor);
        people.add(patient);
        people.add(receptionist);

        for (Person person : people) {
            person.displayRole();
        }
    }
}