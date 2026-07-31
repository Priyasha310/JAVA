public class Constructors {
    public static void main(String[] args) {
        System.out.println("Default constructor example:");
        StudentDefault sd = new StudentDefault();

        System.out.println("\nUser-defined no-argument constructor example:");
        StudentNoArg sna = new StudentNoArg();

        System.out.println("\nParameterized constructor example:");
        StudentParam sp = new StudentParam(101, "Priyasha");
        System.out.println("id = " + sp.id + ", name = " + sp.name);
    }
}

class StudentDefault {
    // Compiler provides a default constructor when no constructors are defined.
}

class StudentNoArg {
    StudentNoArg() {
        System.out.println("Student Created");
    }
}

class StudentParam {
    int id;
    String name;

    StudentParam(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
