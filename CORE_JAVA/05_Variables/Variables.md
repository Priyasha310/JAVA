# Java Variables & Data Types - Handy Notes

# Variables in Java

### Definition

A **variable** is a named memory location (container) that stores data during program execution.

### Syntax

```java
dataType variableName = value;
```

Example:

```java
int age = 25;
String name = "John";
```

---

# Java is Statically Typed

Every variable **must be declared with a data type before it is used**.

```java
int age = 20;      // ✅
age = 30;          // ✅

// age = "Twenty"; // ❌ Compile-time Error
```

---

# Java is Strongly Typed

Once a variable is declared with a specific type, it **cannot store values of another type** unless explicitly converted.

```java
int age = 20;

// age = "Twenty";   // ❌ Error

age = (int)20.5;     // ✅ Explicit conversion
```

---

# Variable Naming Rules

1. Can contain **letters, digits, underscores (_), and dollar signs ($)**.
2. Variable names are **case-sensitive**.
3. Must begin with a **letter, underscore (_), or dollar sign ($)**.
4. Cannot begin with a digit.
5. Cannot be a Java keyword.
6. Follow **camelCase** naming convention.

Example:

```java
studentName
totalMarks
employeeSalary
```

### Constant Naming Convention

Use **UPPER_CASE** with underscores.

```java
final int MAX_VALUE = 100;
```


# Types of Variables in Java based on datatypes:
1. Primitives: byte, short, int, long, float, double, char, boolean
2. Reference/Object: String, Arrays, Classes, Interfaces, etc.


# Types of Variables in Java based on **where they are declared**:

Variables in Java are classified based on **where they are declared**.

| Type                             | Declared In                           | Lifetime                        | Scope                         |
| -------------------------------- | ------------------------------------- | ------------------------------- | ----------------------------- |
| Local Variable                   | Inside a method/block                 | Until the method/block finishes | Within that method/block      |
| Instance Variable                | Inside a class, outside methods       | As long as the object exists    | Accessible through the object |
| Class Variable (Static Variable) | Inside a class using `static`         | As long as the class is loaded  | Shared by all objects         |
| Method Parameter                 | Inside a method's parameter list      | During method execution         | Within that method            |
| Constructor Parameter            | Inside a constructor's parameter list | During constructor execution    | Within that constructor       |

---

# 1. Local Variable

A variable declared **inside a method, constructor, or block**.

* Must be initialized before use.
* Exists only during the execution of that block.

```java
void display() {
    int age = 20;   // Local Variable
}
```

---

# 2. Instance Variable (Object Variable)

A variable declared **inside a class but outside methods, constructors, and blocks**.

* Each object gets its own copy.
* Created when an object is created.
* Destroyed when the object is garbage collected.

```java
class Student {

    int rollNo;      // Instance Variable
    String name;     // Instance Variable
}
```

Example:

```java
Student s1 = new Student();
Student s2 = new Student();

s1.rollNo = 101;
s2.rollNo = 102;
```

Each object stores its own value.

---

# 3. Class Variable (Static Variable)

A variable declared using the `static` keyword.

* Only **one copy** exists.
* Shared by all objects of the class.
* Created when the class is loaded.

```java
class Student {

    static String college = "ABC College";
}
```

Example:

```java
Student s1 = new Student();
Student s2 = new Student();

System.out.println(Student.college);
```

Both objects share the same `college` value.

---

# 4. Method Parameter

A variable declared in a **method's parameter list**.

* Receives values when the method is called.
* Scope is limited to that method.

```java
void display(String name) {   // Method Parameter
    System.out.println(name);
}
```

Here, `name` is the method parameter.

---

# 5. Constructor Parameter

A variable declared in a **constructor's parameter list**.

* Used to initialize object state during object creation.
* Scope is limited to the constructor.

```java
class Student {

    String name;

    Student(String name) {     // Constructor Parameter
        this.name = name;
    }
}
```

Here, `name` is the constructor parameter.

---

# Summary Table

| Variable Type         | Example                |
| --------------------- | ---------------------- |
| Local Variable        | `int age = 20;`        |
| Instance Variable     | `String name;`         |
| Class Variable        | `static int count;`    |
| Method Parameter      | `display(String name)` |
| Constructor Parameter | `Student(String name)` |

---

# Interview Memory Trick

* **Local** → Inside a method
* **Instance** → One copy per object
* **Class (static)** → One copy shared by all objects
* **Method Parameter** → Input to a method
* **Constructor Parameter** → Input while creating an object

---
# Type Casting

Type casting means converting one data type into another.

---

## 1. Widening Conversion (Implicit)

Smaller data type → Larger data type

```text
byte
   ↓
short
   ↓
int
   ↓
long
   ↓
float
   ↓
double
```

Example:

```java
int num = 100;

double d = num;
```

No data loss occurs.

---

## 2. Narrowing Conversion (Explicit)

Larger data type → Smaller data type

```text
double
   ↓
float
   ↓
long
   ↓
int
   ↓
short
   ↓
byte
```

Example:

```java
double d = 20.8;

int num = (int)d;
```

Output:

```text
20
```

Decimal part is lost.

---

# Narrowing Overflow Example

```java
int i = 129;

byte b = (byte)i;

System.out.println(b);
```

Output

```text
-127
```

### Why?

A `byte` stores values only from:

```text
-128 to 127
```

When `129` is cast to `byte`, it **overflows** and wraps around to the beginning of the byte range (two's complement representation), resulting in `-127`.

---

# Promotion During Arithmetic Operations

When performing arithmetic operations, Java automatically promotes smaller integer types (`byte`, `short`, and `char`) to `int`.

Example:

```java
byte a = 10;
byte b = 20;

// byte c = a + b;   // ❌ Error

int c = a + b;       // ✅
```

Reason:

The result of `a + b` is automatically promoted to an `int`.

---

# Wrapper Classes in Java

## What are Wrapper Classes?

Wrapper classes are **object representations of primitive data types**. They "wrap" a primitive value inside an object.

Since Java Collections and many APIs work only with objects, wrapper classes allow primitive values to be treated as objects.

---

## Primitive Types and Their Wrapper Classes

| Primitive Type | Wrapper Class |
|---------------|---------------|
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

---

# Why Do We Need Wrapper Classes?

## 1. Collections Framework

Java Collections (`ArrayList`, `HashMap`, `HashSet`, etc.) can store **only objects**, not primitive data types.

### ❌ Invalid

```java
ArrayList<int> list = new ArrayList<>();
```

### ✅ Valid

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
```

---

## 2. Utility Methods

Wrapper classes provide many useful methods.

### Example

```java
String str = "100";
int num = Integer.parseInt(str);
System.out.println(num + 50);
```

**Output**

```text
150
```

Some commonly used methods:

```java
Integer.parseInt("123");
Integer.valueOf("123");
Integer.max(10,20);
Integer.min(10,20);
Double.parseDouble("12.5");
Character.isDigit('5');
Boolean.parseBoolean("true");
```

---

## 3. Null Values

Primitive types cannot store `null`, but wrapper classes can.

```java
Integer age = null;
```

This is useful while working with databases, APIs, and frameworks.

---

# Auto-boxing

## Definition

**Auto-boxing** is the automatic conversion of a **primitive data type** into its corresponding **wrapper class object**.

---

## Example

```java
int i = 10;

Integer j = i;     // Auto-boxing
```

Internally, Java converts it to:

```java
Integer j = Integer.valueOf(i);
```

---

## Memory Representation

```text
Primitive Variable

i
│
▼
10

↓

Auto-boxing

Integer Object

j
│
▼
+------------+
| Integer    |
| value = 10 |
+------------+
```

---

# Unboxing

## Definition

**Unboxing** is the automatic conversion of a **wrapper class object** back into its corresponding **primitive data type**.

---

## Example

```java
Integer j = 10;

int k = j;      // Unboxing
```

Internally, Java converts it to:

```java
int k = j.intValue();
```

---

## Memory Representation

```text
Integer Object

+------------+
| value = 10 |
+------------+
      ▲
      │
      j

↓

Unboxing

k
│
▼
10
```

---

# Auto-boxing and Unboxing Together

```java
public class Test {

    public static void main(String[] args) {
        int i = 10;
        Integer j = i;   // Auto-boxing

        int k = j;        // Unboxing

        System.out.println(i);
        System.out.println(j);
        System.out.println(k);
    }
}
```

### Output

```text
10
10
10
```

---

# Wrapper Class Example with Collections

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);   // Auto-boxing
int num = list.get(0);   // Unboxing
```

Internally,

```java
list.add(Integer.valueOf(10));

int num = list.get(0).intValue();
```

---

# Interview Questions

### Q1. What is Auto-boxing?

**Answer:**

Automatic conversion of a primitive data type into its corresponding wrapper class object.

---

### Q2. What is Unboxing?

**Answer:**

Automatic conversion of a wrapper class object into its corresponding primitive type.

---

### Q3. Why do we need Wrapper Classes?

- Collections Framework
- Utility methods
- Nullable values
- Generic programming

---

### Q4. Can an `ArrayList` store primitive data types?

**Answer:**

No.

It stores only objects.

Hence we use wrapper classes.

---

# `final` Keyword in Java

## Definition

The `final` keyword is used to:

- Make a variable constant.
- Prevent method overriding.
- Prevent class inheritance.

---

# 1. Final Variable

A `final` variable can be assigned **only once**.

### Example

```java
final int AGE = 25;

// AGE = 30; ❌ Compile-time Error
```

### Constant Example

```java
public class Constants {

    public static final double PI = 3.14159;
}
```

---

# 2. Final Method

A `final` method **cannot be overridden** by subclasses.

### Example

```java
class Animal {

    final void sound() {
        System.out.println("Animal Sound");
    }
}

class Dog extends Animal {

    // ❌ Compile-time Error
    // void sound() {}
}
```

---

# 3. Final Class

A `final` class **cannot be inherited**.

### Example

```java
final class Vehicle {

}

// ❌ Compile-time Error
// class Car extends Vehicle {}
```

---

# Real-World Examples of Final Classes

- `String`
- `Math`
- `Integer`
- `Double`

These classes are final to maintain security, consistency, and immutability.

---

# Summary Table

| Usage | Description |
|--------|-------------|
| `final` Variable | Cannot be reassigned |
| `final` Method | Cannot be overridden |
| `final` Class | Cannot be extended |

---

# Interview Questions

### Q1. What is the `final` keyword?

**Answer:**

The `final` keyword is used to make variables constant, prevent method overriding, and prevent class inheritance.

---

### Q2. Can a `final` variable be modified?

**Answer:**

No. Once assigned, it cannot be reassigned.

---

### Q3. Can we override a `final` method?

**Answer:**

No.

---

### Q4. Can we inherit a `final` class?

**Answer:**

No.

---

### Q5. Is `String` a final class?

**Answer:**

Yes.

It is declared as a `final` class to preserve immutability and prevent inheritance.

---

# Quick Revision

### Variable

* Named memory location.
* Stores data during execution.

### Java

* Statically Typed
* Strongly Typed

### Casting

* Widening → Automatic
* Narrowing → Explicit

### Floating Point

* `float` → 32-bit (~6–7 digits)
* `double` → 64-bit (~15–16 digits)

### Money Calculations

* ❌ float
* ❌ double
* ✅ BigDecimal

### Arithmetic Promotion

`byte`, `short`, and `char` are promoted to `int` during arithmetic operations.

---


- ✅ Wrapper classes convert primitive values into objects.
- ✅ Auto-boxing: Primitive → Wrapper.
- ✅ Unboxing: Wrapper → Primitive.
- ✅ Wrapper classes are required for Collections and utility methods.
- ✅ `final` variable → Constant.
- ✅ `final` method → Cannot be overridden.
- ✅ `final` class → Cannot be inherited.

# Interview One-Liners

* A **variable** is a named memory location that stores data.
* Java is **statically typed**, so every variable must be declared with a data type.
* Java is **strongly typed**, so incompatible types require explicit conversion.
* Primitive variables store **values**, while reference variables store **references (memory addresses)**.
* Use **BigDecimal** instead of `float` or `double` for precise financial calculations.
* Java automatically promotes `byte`, `short`, and `char` to `int` during arithmetic operations.
