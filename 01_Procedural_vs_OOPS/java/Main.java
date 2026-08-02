// Student Management System - Add student, Update student, Delete student, Search student
// StudentManagementSystem/
// │
// ├── Student.java          // POJO
// ├── StudentService.java   // Business logic
// └── Main.java             // Driver class

public class Main {

    public static void main(String[] args) {

        // Create the student service that handles CRUD operations.
        StudentService service = new StudentService();

        // Add some initial students to the system.
        service.addStudent(new Student(101, "Rahul", 22, "CSE"));
        service.addStudent(new Student(102, "Priya", 21, "ECE"));
        service.addStudent(new Student(103, "Amit", 23, "IT"));

        // Display all students currently stored.
        System.out.println("\nAll Students");
        service.displayStudents();

        // Search for a student by ID and print the result.
        System.out.println("\nSearching Student");
        System.out.println(service.searchStudent(102));

        // Update the student details for the student with ID 102.
        System.out.println("\nUpdating Student");
        service.updateStudent(102, "Priya Sharma", 22, "Computer Science");

        // Verify that the update was successful.
        System.out.println(service.searchStudent(102));

        // Remove a student from the system by ID.
        System.out.println("\nDeleting Student");
        service.deleteStudent(101);

        // Display the remaining students after deletion.
        System.out.println("\nRemaining Students");
        service.displayStudents();
    }
}