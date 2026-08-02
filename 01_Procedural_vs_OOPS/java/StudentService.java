import java.util.ArrayList;
import java.util.List;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    // Add Student
    public void addStudent(Student student) {

        if (searchStudent(student.getId()) != null) {
            System.out.println("Student already exists.");
            return;
        }

        students.add(student);
        System.out.println("Student added successfully.");
    }

    // Search Student
    public Student searchStudent(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Update Student
    public void updateStudent(int id, String name, int age, String department) {

        Student student = searchStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.setName(name);
        student.setAge(age);
        student.setDepartment(department);

        System.out.println("Student updated successfully.");
    }

    // Delete Student
    public void deleteStudent(int id) {

        Student student = searchStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        students.remove(student);

        System.out.println("Student deleted successfully.");
    }

    // Display All Students
    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }
}