/**
 * AnnotationsExample.java
 * Demonstrates custom annotations, repeatable annotations, field-level annotations,
 * inherited annotations and usage of built-in annotations like @Deprecated.
 * Shows how to read annotations at runtime using reflection.
 */
import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
// `Info` is a runtime annotation intended for types (classes).
// It is marked @Inherited so subclasses will inherit this annotation.
@interface Info {
    // who wrote the class
    String author() default "unknown";
    // semantic version string for the class
    String version() default "1.0";
    // free-form tags for filtering or discovery
    String[] tags() default {};
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
// `FieldInfo` is a simple field-level annotation used to attach metadata to fields.
@interface FieldInfo {
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Roles.class)
// `Role` is a repeatable annotation that can be applied multiple times to a type.
// For example: @Role("admin") @Role("user")
@interface Role {
    String value();
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// Container annotation used by the `@Repeatable` mechanism.
@interface Roles {
    Role[] value();
}

// Apply `Info` metadata and two `Role` annotations to the Person class.
@Info(author = "Alice", version = "1.2", tags = {"annotations", "example"})
@Role("admin")
@Role("user")
class Person {
    // Mark `id` with FieldInfo to show how to read field-level metadata.
    @FieldInfo("identifier")
    private int id;
    private String name;

    // This method is intentionally marked deprecated to demonstrate the
    // use of a built-in annotation. Calling it will compile with a warning.
    @Deprecated
    public void oldMethod() {
        System.out.println("Deprecated method called");
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

// Employee extends Person. Because `Info` is @Inherited, Employee will also
// present the `Info` annotation at runtime unless overridden.
class Employee extends Person {
    public Employee(int id, String name) {
        super(id, name);
    }
}

public class AnnotationsExample {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Person.class;
        // ----- Read and display type-level `Info` annotation -----
        if (cls.isAnnotationPresent(Info.class)) {
            Info info = cls.getAnnotation(Info.class);
            System.out.println("Author: " + info.author());
            System.out.println("Version: " + info.version());
            System.out.println("Tags: " + String.join(",", info.tags()));
        }

        // ----- Read repeatable `Role` annotations applied to the class -----
        Role[] roles = cls.getAnnotationsByType(Role.class);
        System.out.print("Roles: ");
        for (Role r : roles) {
            System.out.print(r.value() + " ");
        }
        System.out.println();

        // ----- Inspect field-level annotations using reflection -----
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(FieldInfo.class)) {
                FieldInfo fi = f.getAnnotation(FieldInfo.class);
                System.out.println("Field " + f.getName() + " -> " + fi.value());
            }
        }

        // ----- Demonstrate @Inherited: check subclass for inherited Info -----
        System.out.println("\nChecking @Inherited on subclass Employee:");
        Info infoOnEmployee = Employee.class.getAnnotation(Info.class);
        if (infoOnEmployee != null) {
            System.out.println("Employee author: " + infoOnEmployee.author());
        } else {
            System.out.println("No Info annotation on Employee");
        }

        // ----- Use of deprecated API: calling `oldMethod()` shows intent -----
        Person p = new Person(1, "Bob");
        p.oldMethod();
    }
}
