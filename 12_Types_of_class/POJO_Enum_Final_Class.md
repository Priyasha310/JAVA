# POJO Class (Plain Old Java Object)

## What is a POJO Class?

A **POJO (Plain Old Java Object)** is a simple Java class used to represent data.

It is mainly used as a **data carrier (DTO/Model/Bean)**.

---

## Characteristics

- Contains private instance variables.
- Provides public getter and setter methods.
- Has a public default (no-argument) constructor (or parameterized constructors as needed).
- Contains no business logic.
- Does not require any special framework-specific annotations.
- Can be instantiated using the `new` keyword.

---

## Example

```java
class Student {

    private int id;
    private String name;

    public Student() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```

---

## Interview Points

- POJO is a simple Java object.
- Mainly used for transferring data.
- Frameworks like Spring/Hibernate often use POJOs (they may later add annotations, but the core POJO concept is a plain class).

---

# Enum Class

## What is an Enum?

An **Enum (Enumeration)** is a special type in Java used to represent a **fixed set of constants**.

Example:

```java
enum Day {

    MONDAY,
    TUESDAY,
    WEDNESDAY
}
```

Instead of using

```java
String day = "MONDAY";
```

we write

```java
Day day = Day.MONDAY;
```

This provides compile-time type safety.

---

## Why Use Enum?

Without Enum

```java
String status = "OPEN";
```

Problems

- Typing mistakes
- Invalid values
- No type safety

With Enum

```java
Status status = Status.OPEN;
```

Only predefined constants are allowed.

---

## Characteristics

- Represents a fixed set of constants.
- Constants are implicitly **public static final**.
- Cannot be instantiated using `new`.
- Constructor is always private.
- Can have:
  - Variables
  - Constructors
  - Methods
- Can implement interfaces.
- Cannot extend another class because every enum already extends `java.lang.Enum`.

---

# Normal Enum

```java
enum Day {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}
```

---

## Commonly Used Methods

### values()

Returns all enum constants.

```java
for (Day d : Day.values()) {

    System.out.println(d);
}
```

---

### ordinal()

Returns the position of the constant.

```java
System.out.println(Day.MONDAY.ordinal());
```

Output

```text
0
```

---

### valueOf()

Converts String to Enum.

```java
Day day = Day.valueOf("MONDAY");
```

---

### name()

Returns the constant name.

```java
System.out.println(Day.MONDAY.name());
```

Output

```text
MONDAY
```

---

# Enum with Custom Values

Enums can have variables, constructors and methods.

```java
enum Day {

    MONDAY(101, "Weekday"),
    TUESDAY(102, "Weekday");

    private int code;
    private String type;

    Day(int code, String type) {

        this.code = code;
        this.type = type;
    }

    public int getCode() {

        return code;
    }
}
```

---

## Why Use Custom Values?

Useful when every constant has additional information.

Examples

- Status Codes
- HTTP Codes
- Error Codes
- Employee Roles

---

# Method Override by Enum Constant

Each enum constant can override a method independently.

```java
enum Day {

    MONDAY {

        @Override
        public void display() {

            System.out.println("Monday");
        }
    },

    TUESDAY;

    public void display() {

        System.out.println("Default");
    }
}
```

---

## When to Use?

When different enum constants require different behaviour.

---

# Abstract Method in Enum

Enums can declare abstract methods.

Every constant **must** implement them.

```java
enum Day {

    MONDAY {

        public void work() {

            System.out.println("Office");
        }
    },

    SUNDAY {

        public void work() {

            System.out.println("Holiday");
        }
    };

    public abstract void work();
}
```

---

## Interview Point

If an enum declares an abstract method, **every constant must provide its own implementation**.

---

# Enum Implementing Interface

Enums cannot extend classes.

However, they **can implement interfaces**.

```java
interface Printable {

    String print();
}

enum Day implements Printable {

    MONDAY;

    @Override
    public String print() {

        return name().toLowerCase();
    }
}
```

---

## Why Can Enum Implement an Interface?

Every enum already extends

```java
java.lang.Enum
```

Since Java doesn't support multiple inheritance, enums cannot extend another class.

Interfaces are the only way to achieve additional behaviour.

---

# Advantages of Enum

- Compile-time type safety.
- Prevents invalid values.
- Improves readability.
- Better than String constants.
- Can contain variables, constructors and methods.
- Used extensively in switch statements.

---
# Why is Enum Better than int/String Constants?

Suppose we want to check whether a day is a weekend.

### Using int Constants

```java
class WeekConstants {

    static final int SATURDAY = 6;
    static final int SUNDAY = 7;
}

public static boolean isWeekend(int day) {

    return day == WeekConstants.SATURDAY ||
           day == WeekConstants.SUNDAY;
}
```

Usage

```java
isWeekend(6);      // ✔ true
isWeekend(7);      // ✔ true
isWeekend(100);    // Compiles, but logically invalid
```

### Problems

- No type safety.
- Any integer value can be passed.
- Poor readability.
- Compiler cannot prevent invalid values.

---

### Using Enum

```java
enum Day {

    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

public static boolean isWeekend(Day day) {

    return day == Day.SATURDAY ||
           day == Day.SUNDAY;
}
```

Usage

```java
isWeekend(Day.WEDNESDAY);   // false

isWeekend(Day.SUNDAY);      // true
```

Now this is **not possible**:

```java
isWeekend(100);      // Compile-time Error
```

---

## Advantages of Enum over int/String Constants

- Compile-time type safety.
- Prevents invalid values.
- Better readability.
- Better maintainability.
- Object-oriented (can have variables, methods, constructors).
- Used extensively in switch statements.

---

## Interview Answer

**Why should we prefer Enum over int or String constants?**

Enums provide compile-time type safety and prevent invalid values from being passed. They also improve readability, are object-oriented, and can contain variables, constructors, and methods, making them much more powerful than traditional constants.

---

# Enum vs Constant Class

Using Constants

```java
class Status {

    public static final String OPEN = "OPEN";
}
```

Using Enum

```java
enum Status {

    OPEN,
    CLOSED
}
```

Enum is preferred because:

- Type-safe
- Readable
- Object-oriented
- Supports methods and constructors

---
# Final Class

## What is a Final Class?

A **final class** is a class that **cannot be inherited**.

It is declared using the `final` keyword.

---

## Syntax

```java
public final class TestClass {

}
```

Attempting to inherit it results in a compile-time error.

```java
public class Child extends TestClass {

}
```

❌ Compile-time Error

```
Cannot inherit from final class
```

---

## Why Use a Final Class?

- Prevent inheritance.
- Prevent modification of implementation.
- Improve security.
- Ensure class behavior remains unchanged.

---

## Real-world Examples

Java provides several final classes:

- `String`
- `Math`
- `System`
- `Integer`
- `Boolean`

Example:

```java
public final class String {

}
```

No class can extend `String`.

---

## Advantages

- Prevents inheritance.
- Improves security.
- Preserves implementation.
- Useful for immutable classes.

---

## Final Class vs Final Method

| Final Class | Final Method |
|-------------|--------------|
| Cannot be inherited | Cannot be overridden |
| No subclass can exist | Subclass can exist, but cannot override that method |

---

## Interview Questions

### What is an Enum?

A special Java type used to represent a fixed set of constants.

---

### Can an Enum have constructors?

Yes.

Constructors are always **private**.

---

### Can we create an object of an Enum?

No.

Enum objects are created automatically by the JVM.

---

### Can an Enum extend another class?

No.

Every enum already extends `java.lang.Enum`.

---

### Can an Enum implement an interface?

Yes.

---

### Can an Enum contain variables and methods?

Yes.

Enums can contain:

- Variables
- Constructors
- Methods
- Static methods
- Abstract methods

---

### Which methods are commonly used in Enum?

- `values()`
- `valueOf()`
- `name()`
- `ordinal()`

---

### Difference between `name()` and `ordinal()`

| name() | ordinal() |
|----------|-----------|
| Returns constant name | Returns position/index |
| String | int |

---

### Difference between `values()` and `valueOf()`

| values() | valueOf() |
|-----------|-----------|
| Returns all constants | Returns a constant by name |

---

### Why is Enum better than String constants?

- Compile-time type safety.
- Prevents invalid values.
- Easier to maintain.
- Supports OOP features like methods and constructors.


### What is a Final Class?

A Final Class is a class that cannot be inherited by any other class.

---

### Why is String a Final Class?

Because String is immutable. Making it final prevents subclasses from changing its behavior, ensuring security, consistency, and immutability.

---

### Can a Final Class contain methods?

Yes.

A final class can contain:

- Instance methods
- Static methods
- Final methods
- Constructors
- Variables

Only inheritance is prohibited.

---

### Can a Final Class be instantiated?

Yes.

```java
TestClass obj = new TestClass();
```

A final class **cannot be extended**, but it **can be instantiated**.

---

# Most Asked Interview Questions ⭐

1. What is an Enum?
2. Why use Enum instead of String constants?
3. Can an Enum have constructors?
4. Why is an Enum constructor private?
5. Can an Enum extend another class?
6. Can an Enum implement an interface?
7. Can an Enum have abstract methods?
8. Explain `values()`, `valueOf()`, `name()`, and `ordinal()`.
9. Enum with custom values.
10. Advantages of Enum over constants.
11. What is a Final Class?
12. Why is String declared as final?
13. Can a Final Class be instantiated?
14. Can a Final Class contain methods?
15. Difference between Final Class and Final Method.


---

# Quick Revision

- ✅ POJO → Simple Java object used to carry data.
- ✅ Enum → Fixed set of constants.
- ✅ Enum constants are implicitly `public static final`.
- ✅ Enum constructor is always private.
- ✅ Enum cannot be instantiated.
- ✅ Enum extends `java.lang.Enum`.
- ✅ Enum can implement interfaces.
- ✅ Enum can have variables, constructors, methods and abstract methods.
- ✅ Common methods: `values()`, `valueOf()`, `name()`, `ordinal()`.
- ✅ Prefer Enum over String constants for type safety.
- ✅ Declared using `final`.
- ✅ Cannot be inherited.
- ✅ Can be instantiated.
- ✅ Can contain constructors, methods, and variables.
- ✅ Used to prevent modification through inheritance.
- ✅ Examples: `String`, `Math`, `System`, `Integer`.
