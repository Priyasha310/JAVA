// Student Management System - Single File Version
public class StudentManagementSystem {

    public static void main(String[] args) {

        StudentService service = new StudentService();

        service.addStudent(new Student(101, "Rahul", 22, "CSE"));
        service.addStudent(new Student(102, "Priya", 21, "ECE"));
        service.addStudent(new Student(103, "Amit", 23, "IT"));

        System.out.println("\nAll Students");
        service.displayStudents();

        System.out.println("\nSearching Student");
        System.out.println(service.searchStudent(102));

        System.out.println("\nUpdating Student");
        service.updateStudent(102, "Priya Sharma", 22, "Computer Science");

        System.out.println(service.searchStudent(102));

        System.out.println("\nDeleting Student");
        service.deleteStudent(101);

        System.out.println("\nRemaining Students");
        service.displayStudents();
    }

    static class Student {
        private int id;
        private String name;
        private int age;
        private String department;

        public Student(int id, String name, int age, String department) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.department = department;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", department='" + department + '\'' +
                    '}';
        }
    }

    static class StudentService {
        private final java.util.List<Student> students = new java.util.ArrayList<>();

        public void addStudent(Student student) {
            if (searchStudent(student.getId()) != null) {
                System.out.println("Student already exists.");
                return;
            }

            students.add(student);
            System.out.println("Student added successfully.");
        }

        public Student searchStudent(int id) {
            for (Student student : students) {
                if (student.getId() == id) {
                    return student;
                }
            }
            return null;
        }

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

        public void deleteStudent(int id) {
            Student student = searchStudent(id);

            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            students.remove(student);
            System.out.println("Student deleted successfully.");
        }

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
}
