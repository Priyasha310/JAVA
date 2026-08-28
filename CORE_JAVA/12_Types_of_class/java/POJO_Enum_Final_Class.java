public class POJO_Enum_Final_Class {
    public static void main(String[] args) {
        // POJO example: simple data carrier with getters and setters.
        // A POJO is a plain object with private fields and public accessors.
        System.out.println("POJO example:");
        StudentPOJO student = new StudentPOJO();
        student.setId(101);
        student.setName("Priyasha");
        System.out.println("Student id=" + student.getId() + ", name=" + student.getName());

        // Enum example: fixed set of constants with type safety.
        // Enums are useful when a variable should hold one of a known set of values.
        System.out.println("\nEnum example:");
        Day today = Day.MONDAY;
        System.out.println("Today is " + today);
        System.out.println("Ordinal of " + today + " = " + today.ordinal());
        System.out.println("valueOf(\"FRIDAY\") = " + Day.valueOf("FRIDAY"));
        System.out.println("Is weekend? " + isWeekend(Day.SUNDAY));

        // Enum with custom values: each constant can hold extra data and behavior.
        System.out.println("\nEnum with custom values:");
        DayCode dayCode = DayCode.SATURDAY;
        System.out.println(dayCode.name() + " code=" + dayCode.getCode() + " type=" + dayCode.getType());

        // Final class example: cannot be extended by another class.
        System.out.println("\nFinal class example:");
        FinalExample example = new FinalExample("immutable");
        System.out.println("FinalExample value = " + example.getValue());
    }

    /**
     * Simple helper method to determine if a day is a weekend.
     */
    public static boolean isWeekend(Day day) {
        return day == Day.SATURDAY || day == Day.SUNDAY;
    }
}

/**
 * Plain Old Java Object (POJO) example.
 * It uses private fields and public getters/setters.
 */
class StudentPOJO {
    private int id;
    private String name;

    public StudentPOJO() {
        // Default constructor for the POJO.
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Enum representing days of the week.
 */
enum Day {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

/**
 * Enum with custom fields for code and type.
 * This shows how enums can store extra state.
 */
enum DayCode {
    MONDAY(101, "Weekday"),
    TUESDAY(102, "Weekday"),
    WEDNESDAY(103, "Weekday"),
    THURSDAY(104, "Weekday"),
    FRIDAY(105, "Weekday"),
    SATURDAY(106, "Weekend"),
    SUNDAY(107, "Weekend");

    private final int code;
    private final String type;

    DayCode(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}

/**
 * Final class example. A final class cannot be subclassed.
 */
final class FinalExample {
    private final String value;

    FinalExample(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
