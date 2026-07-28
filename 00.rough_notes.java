1. Procedural programming vs OOPS
2. Object and class
-Object has 2 things: properties/state & behavior/function
Class: blueprint from which Object can be created

class Student { 
    int rollno; string name;
}

Pillars of OOPS:
1. Data Abstraction: implementation hiding 
It hides internal implementation and shows only essential functionality to the user.
It can be achieved through interface and abstract class.
Advantage: increases security and confidentiality.

example in real life: in car, break pedal(shown) -> we press it () -> car speed reduce. HOW? -> hidden from user (ABSTRACTED) to us 
self thought: aam khao guthli mat geeno 

interface::
interface Car {
    public applyBreak();
    public pressHorn();
}

class CarImplementation implements Car{
    public applyBreak(){
        //all logic
    }

}

2. Data Encapsulation:  data hiding
Encapsulation is a process or technique that binds the data(member variables) and behavior(methods) together in a single unit.
To completely encapsulate a class in Java, follow two primary steps:
Declare the class variables as private to hide them from direct outside access.
Provide public getter and setter methods to control how those variables are read and modified.
Core Benefits
Data Validation: Setters filter out bad data before it alters the internal state (e.g., stopping negative deposits).
Flexibility & Read-Only/Write-Only: Omit setter methods entirely to make class variables read-only.
Maintainability: You can alter the internal data structures or logic inside the class without breaking external applications using it.
Loosely Coupled Code: External classes only interact with the defined public API rather than the messy implementation details.

Access Modifiers: private, public, protected, default

3. Inheritance:
Inheritance in Java is a core OOP concept that allows a class to acquire properties and behaviors from another class. 
Advantage: achieve polymorphism
Single:
A
│
B

Multilevel:
A
│
B
│
C

Hierarchical:
   A
  / \
 B   C

Multiple (Not allowed with classes) - (Diamond problem):
A   B
 \ /
  C

through interface we can solve this Diamond problem:
Hybrid (Using interfaces):
Interface A   Interface B
      \       /
       Class C
           |
        Class D



| Type                         | Supported in Java? | Description                                                                                                              |
| ---------------------------- | ------------------ | ------------------------------------------------------------------------------------------------------------------------ |
| **Single Inheritance**       | ✅ Yes              | One child class inherits from one parent class.                                                                          |
| **Multilevel Inheritance**   | ✅ Yes              | A class inherits from a class, which itself inherits from another class.                                                 |
| **Hierarchical Inheritance** | ✅ Yes              | Multiple child classes inherit from the same parent class.                                                               |
| **Multiple Inheritance**     | ❌ Not with classes | A class cannot inherit from multiple classes to avoid ambiguity (Diamond Problem). It is supported using **interfaces**. |
| **Hybrid Inheritance**       | ❌ Not with classes | Combination of multiple inheritance types. Achieved in Java using **interfaces**, not classes.                           |

4. polymorphism: many from
Same method behaves differently in different situtaion.

Types:
Compile-time Polymorphism / Static Polymorphism /	Method Overloading	- resolved At compile time
Run-time Polymorphism / Dynamic Polymorphism / Method Overriding -resolved At runtime

1. Compile-time Polymorphism (Method Overloading)
Same method name, but different parameters (number, type, or order).
The compiler decides which method to call.
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
---
FALSE CASE - different return type - because during compile time, it does not consider return type
int add(int a, int b) {
        return a + b;
    }

string add(int a, int b) {
    return a + b + c;
}
---

2. Run-time Polymorphism (Method Overriding)
A child class provides its own implementation of a method defined in the parent class.
The method to execute is determined at runtime based on the actual object.
for Overriding - we look for parameter name, return type

class Animal {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.sound(); // Output: Dog barks 
        //self added: if it was not presnet in class Dog then it would have looked to 
        //class Animal since Dog **extends** Animal 
    }
}

Java - platform independent, object-oriented, robust, secure, multithreaded, high performance, distributed, dynamic programming language.
WORA - Write Once Run Anywhere

JVM - Java Virtual Machine  -> platform dependent -> has JIT (just in-time) compiler and JIT compiler converts bytecode to machine code
JRE - Java Runtime Environment -> platform dependent -> JVM + libraries + other files
JDK - Java Development Kit -> platform dependent -> JRE + development tools (compiler, debugger, etc.)

JSE - Java Standard Edition ->  JDK + API : Core Java API (java.lang, java.util, java.io, etc.)
JEE - Java Enterprise Edition -> JSE + API for enterprise applications: transaction management, messaging, web services, etc.- servelets
JME - Java Micro Edition ->  JSE + API for mobile and embedded devices: apis for mobile applications, IoT devices, etc.

java program -> compiler -> bytecode -> JVM -> machine code

public static void main(String[] args) {
    System.out.println("Hello, World!");
}

Variables in Java: container that holds data values during program execution.

Java is a statically typed language, which means that every variable must be declared with a data type before it can be used.
Java is a strongly typed language, which means that once a variable is declared with a specific data type, it cannot hold values of other data types without explicit conversion.

Datatype variableName = value;

Variable Naming Rules:
1. Variable names can contain unicode letters, digits, underscores, and dollar signs.
2. case-sensitive.
3. can start with a letter, underscore, or dollar sign, but cannot start with a digit.
4. variable names cannot be a reserved keyword in Java.
5. small case letters are used for variable names, and camelCase is often used for multi-word variable names (e.g., studentName).
6. for constant variable name, it is a common convention to use uppercase letters with underscores to separate words (e.g., MAX_VALUE).

Types of Variables in Java:
1. Primitives: byte, short, int, long, float, double, char, boolean
2. Reference/Object: String, Arrays, Classes, Interfaces, etc.

char - 2 bytes, 16 bits, Unicode character
byte - 1 byte, 8 bits, range: -128 to 127 -> signed 2 compliment
short - 2 bytes, 16 bits, range: -2^15 to 2^15 - 1 -
int - 4 bytes, 32 bits, range: -2^31 to 2^31 - 1
long - 8 bytes, 64 bits, range: -2^63 to 2^63 - 1 -> signed 2 compliment
float - 4 bytes, 32 bits, range: ±1.4e-45 to ±3.4028235e38, precision: 6-7 decimal digits
double - 8 bytes, 64 bits, range: ±4.9e-324 to ±1.7976931348623157e308, precision: 15 decimal digits
boolean - 1 bit, values: true or false

Integral types (byte, short, int, long). Floating-point types (float, double). Character type (char). Boolean type (boolean).

We should not use float and double for precise calculations, such as currency or financial calculations, because it can lead to rounding errors. Instead, we should use BigDecimal for such cases.

Both float (32-bit) and double (64-bit) are IEEE 754 floating-point data types. They represent numbers using binary fractional parts (base-2) rather than decimal parts (base-10). Because of this, common decimal values like $0.10 or $0.20 cannot be represented exactly in binary, leading to accumulated errors over multiple transactions

0.3f - 0.1f = 0.20000002=> 0.2f (not equal)

float f = 3.14; // error, because 3.14 is treated as double by default
float f = 3.14f; // correct, using 'f' suffix to indicate float literal

double d = 3.14; // correct, double is the default type for floating-point literals
double d = 3.14d; // correct, using 'd' suffix to indicate double literal (optional)


Types of variables:

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
* Do not have default values.
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
* They have default values (0 for numeric types, false for boolean, null for reference types) if not explicitly initialized.

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

A variable declared in a **method`s parameter list**.

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

A variable declared in a **constructor`s parameter list**.

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

CONVERSION:
Widening Conversion (Implicit Casting):
byte -> short -> int -> long -> float -> double

Narrowing Conversion (Explicit Casting):
double -> float -> long -> int -> short -> byte

int i = 129;
byte b = (byte) i; // Explicit casting (narrowing conversion)
System.out.println(b); // Output: -127 (due to overflow, as byte can only hold values from -128 to 127)
//it goes into overflow and wraps around to the negative side of the range. (goes in loop and starts from -128 again)

Promotion during Arithmetic Operations:
When performing arithmetic operations, Java automatically promotes smaller data types to larger data types to prevent data loss.

Types of Reference Variables in Java:
Class, String, Interface, Array, Enum, Annotation, Record are reference types in Java. They store references (memory addresses) to the actual data rather than the data itself.

Pass by Value vs Pass by Reference:
In Java, all primitive data types are passed by value, meaning a copy of the variable`s value is passed to methods. For reference types, the reference (memory address) is passed by value, meaning the method receives a copy of the reference, but both the original and the copy point to the same object in memory.

String Immutability:
In Java, `String` objects are immutable, meaning once a `String` object is created, its value cannot be changed. Any operation that seems to modify a `String` actually creates a new `String` object.

String Pool:
Java maintains a special memory region called the "String Pool" to optimize memory usage for `String` objects. When a new `String` is created using string literals, the JVM checks the pool first. If an identical `String` already exists, it returns a reference to that `String` instead of creating a new one.

Case 1: String Literals (Uses String Constant Pool)

String s1 = "stestingo";
String s2 = "stestingo";

                    JAVA HEAP
┌───────────────────────────────────────────────┐
│                                               │
│           String Constant Pool (SCP)          │
│      ┌─────────────────────────────┐          │
│      │         "stestingo"         │          │
│      └─────────────────────────────┘          │
│              ▲                 ▲              │
│              │                 │              │
│             s1                s2              │
│                                               │
└───────────────────────────────────────────────┘

Case 2: Using new String()
String s3 = new String("stestingo");

                    JAVA HEAP
┌────────────────────────────────────────────────────────┐
│                                                        │
│      String Constant Pool (SCP)                        │
│      ┌─────────────────────────────┐                   │
│      │         "stestingo"         │                   │
│      └─────────────────────────────┘                   │
│                                                        │
│                                                        │
│      Normal Heap                                       │
│      ┌─────────────────────────────┐                   │
│      │         "stestingo"         │◄────── s3         │
│      └─────────────────────────────┘                   │
│                                                        │
└────────────────────────────────────────────────────────┘

Interface variables:

WRAPPER CLASSES: auto-boxing and unboxing

Auto-boxing: automatic conversion of primitive data types into their corresponding wrapper class objects. For example, converting an `int` to an `Integer`.
example:
```java
int i = 10;
Integer j = i; // Auto-boxing
```

Unboxing: automatic conversion of wrapper class objects back into their corresponding primitive data types. For example, converting an `Integer` back to an `int`.
example:
```java
int i = 10;
Integer j = i; // Auto-boxing
int k = j; // Unboxing
```

Why we need wrapper classes:
1. Collections: Java's collection framework (like ArrayList, HashMap) can only store objects, not primitives. Wrapper classes allow us to use primitive types in collections.
2. Utility Methods: Wrapper classes provide utility methods for converting between types, parsing strings, and other operations.

final keyword: used to declare constants, prevent method overriding, and inheritance of classes.
Example:
```java
final class Constants {
    public static final double PI = 3.14159;
}

JAVA METHODS:
Methods are blocks of code that perform a specific task and can be called upon when needed. They help in code reusability, organization, and modularity.

Declaring a Method:
```java
accessModifier returnType methodName(parameterList) throws ExceptionType {
    // method body
}
```

Access Modifiers: public, private, protected, default (no modifier)

public: can be accessed from anywhere in any package.
private: can only be accessed within the same class.
protected: can be accessed by other classes within the same package and by subclasses in different packages.
default (no modifier): can be accessed only within the same package.

NOTE: package is a namespace that organizes classes and interfaces. It helps avoid name conflicts and can control access.

Naming Conventions for Methods:
1. Method names should be verbs, in mixed case with the first letter lowercase and the first letter of each subsequent word capitalized (camelCase). For example: calculateTotal(), printReport().

Types of methods:
1. System defined methods: methods that are predefined in Java, such as `main()`, `toString()`, `equals()`, etc. Provided by JRE.
2. User defined methods: methods that are defined by the user to perform specific tasks.
3. Overloaded methods: methods that have the same name but different parameter lists (number, type, or order of parameters).
4. Static methods: methods that belong to the class rather than an instance of the class. They can be called without creating an object of the class.
5. Instance methods: methods that belong to an instance of a class. They can access instance variables and are called on objects of the class.
6. Final methods: methods that cannot be overridden by subclasses. They are declared using the `final` keyword.

### When to declare methods static?
Methods should be declared static when they do not depend on instance variables or instance methods of the class.
Utility or helper methods that perform operations without needing to access instance data are good candidates for static methods. 

Variable arguments (varargs): allows a method to accept zero or more arguments of a specified type. It is denoted by an ellipsis (`...`) in the parameter list. Only one argument can be declared as varargs, and it must be the last parameter in the method signature.
Example:
```java
void printNumbers(int... numbers) {
    for (int number : numbers) {
        System.out.println(number);
    }
}
```
