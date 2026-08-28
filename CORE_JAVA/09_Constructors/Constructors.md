# Constructors in Java

## What is a Constructor?

A **constructor** is a special member of a class that is automatically invoked when an object is created.

Its primary purpose is to **initialize the object's state**.

> **Note:** A constructor is **not a method** because it has **no return type** and its name must be the same as the class name.

---

# Syntax

```java
class ClassName {

    ClassName() {
        // Initialization code
    }
}
```

---

# Example

```java
class Student {

    Student() {
        System.out.println("Constructor Called");
    }
}

public class Test {

    public static void main(String[] args) {
        Student s = new Student();
    }
}
```

### Output

```text
Constructor Called
```

---

# Memory Representation

```text
Student s = new Student();

               new
                │
                ▼
        +----------------+
        | Student Object |
        +----------------+
                ▲
                │
      Constructor Executes
```

Whenever an object is created using the `new` keyword:

1. Memory is allocated.
2. Constructor is invoked automatically.
3. Object gets initialized.

---

# Characteristics of a Constructor

- Same name as the class.
- No return type (not even `void`).
- Invoked automatically during object creation.
- Used to initialize objects.
- Can be overloaded.
- Cannot be inherited.
- Cannot be overridden.
- Cannot be `static`, `final`, or `abstract`.

---

# Types of Constructors

Java mainly has two types of constructors:

1. Default Constructor
2. Parameterized Constructor

Additionally, interviews often discuss:
- User-defined No-Argument Constructor
- Copy Constructor (User-defined)

---

# 1. Default Constructor

## Definition

A **Default Constructor** is automatically provided by the **Java compiler** **only if** no constructor is written in the class.

It has:

- No parameters
- Empty body

---

## Example

```java
class Student {

}
```

Compiler internally creates

```java
class Student {

    Student() {
    }
}
```

---

## Memory

```text
Student s = new Student();

↓

Compiler calls

Student(){

}
```

---

## Important Note

If you write **any constructor**, Java **does not** generate the default constructor.

---

## Example

```java
class Student {

    Student(int id){
    }
}

Student s = new Student();
```

### Output

```text
Compile-time Error

Constructor Student() is undefined
```

---

# 2. User-Defined No-Argument Constructor

## Definition

A constructor written by the programmer with **no parameters**.

Unlike the default constructor, it can contain custom initialization logic.

---

## Example

```java
class Student {

    Student() {
        System.out.println("Student Created");
    }
}
```

### Output

```text
Student Created
```

---

## Difference: Default vs No-Argument Constructor

| Default Constructor | No-Argument Constructor |
|---------------------|------------------------|
| Created by compiler | Created by programmer |
| Empty body | Can contain custom logic |
| Generated only if no constructor exists | Explicitly written |

---

# 3. Parameterized Constructor

## Definition

A constructor that accepts parameters to initialize an object with different values.

---

## Example

```java
class Student {

    int id;
    String name;

    Student(int id, String name){
        this.id = id;
        this.name = name;
    }
}
```

Object Creation

```java
Student s = new Student(101, "Priyasha");
```

Memory

```text
Student Object

+-------------------+
| id = 101          |
| name = Priyasha   |
+-------------------+
```

---

## Advantages

- Initializes objects with different values.
- Eliminates repetitive setter calls.
- Produces fully initialized objects.

---

# 4. Copy Constructor (User-Defined)

> **Note:** Java does **not** provide a built-in copy constructor like C++. It must be written by the programmer.

## Example

```java
class Student {

    int id;
    String name;

    Student(int id, String name){
        this.id = id;
        this.name = name;
    }

    Student(Student s){
        this.id = s.id;
        this.name = s.name;
    }
}
```

Object Creation

```java
Student s1 = new Student(101, "Priyasha");

Student s2 = new Student(s1);
```

Both objects have the same data but occupy different memory locations.

---

# Constructor Overloading

A class can have multiple constructors with different parameter lists.

```java
class Student {

    Student(){
    }

    Student(int id){
    }

    Student(int id, String name){
    }
}
```

This is called **Constructor Overloading**.

---

# Constructor Chaining

One constructor can call another constructor in the same class using `this()`.

```java
class Student {

    Student(){
        this(101);
    }

    Student(int id){
        System.out.println(id);
    }
}
```

### Output

```text
101
```

### Rules

- `this()` must be the first statement.
- Used to avoid duplicate code.

---

# `this()` vs `super()`

| `this()` | `super()` |
|-----------|-----------|
| Calls another constructor in the same class | Calls parent class constructor |
| Used for constructor chaining | Used for parent initialization |
| First statement in constructor | Also first statement |

---

# Constructor vs Method

| Constructor | Method |
|-------------|--------|
| Initializes objects | Performs operations |
| Same name as class | Any valid identifier |
| No return type | Must have a return type (`void` or other) |
| Called automatically | Called explicitly |
| Cannot be inherited | Can be inherited (depending on access modifier) |
| Cannot be overridden | Can be overridden |

---
# Constructor Overloading

## Definition

**Constructor Overloading** means defining **multiple constructors** in the same class with **different parameter lists**.

The constructors may differ in:

- Number of parameters
- Type of parameters
- Order of parameters

Java determines which constructor to invoke based on the arguments passed during object creation.

---

## Example

```java
class Student {

    Student() {
        System.out.println("Default Constructor");
    }

    Student(int id) {
        System.out.println("Student ID: " + id);
    }

    Student(int id, String name) {
        System.out.println(id + " " + name);
    }
}

public class Test {

    public static void main(String[] args) {

        Student s1 = new Student();

        Student s2 = new Student(101);

        Student s3 = new Student(101, "Priyasha");
    }
}
```

### Output

```text
Default Constructor
Student ID: 101
101 Priyasha
```

---

## Memory Representation

```text
new Student()
        │
        ▼
Calls Student()

-----------------------------

new Student(101)
        │
        ▼
Calls Student(int)

-----------------------------

new Student(101,"Priyasha")
        │
        ▼
Calls Student(int,String)
```

---

## Why Constructor Overloading?

- Initialize objects in different ways.
- Improves flexibility.
- Avoids creating multiple initialization methods.

---

## Interview One-Liner

> Constructor overloading allows a class to initialize objects in multiple ways using constructors with different parameter lists.

---

# Private Constructor

## Definition

A **private constructor** is a constructor declared using the `private` access modifier.

It **prevents object creation from outside the class**.

---

## Example

```java
class Student {

    private Student() {
        System.out.println("Private Constructor");
    }

    public static void createObject() {
        new Student();
    }
}

public class Test {

    public static void main(String[] args) {
        Student.createObject();
    }
}
```

### Output

```text
Private Constructor
```

---

## What Happens?

```java
Student s = new Student();
```

### Output

```text
Compile-time Error

Student() has private access in Student
```

---

## Memory Diagram

```text
Outside Class

main()

      │
      ▼

new Student()

      │
      ▼

❌ Access Denied


---------------------------

Inside Student Class

createObject()

      │
      ▼

new Student()

      │
      ▼

✅ Object Created
```

---

## Why Do We Use a Private Constructor?

### 1. Singleton Design Pattern

Ensures that only **one object** of a class can be created.

```java
class Singleton {

    private static Singleton obj = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return obj;
    }
}
```

---

### 2. Utility Classes

Classes like `Math` contain only static methods.

There is no need to create their objects.

```java
final class Utility {

    private Utility() {
    }

    static void print() {
    }
}
```

---

## Interview One-Liner

> A private constructor prevents object creation from outside the class and is commonly used in Singleton and Utility classes.

---

# Constructor Chaining

## Definition

**Constructor Chaining** is the process of invoking one constructor from another constructor.

It helps to **reuse initialization code** and avoid duplication.

Java supports two types:

1. Constructor chaining using `this()`
2. Constructor chaining using `super()`

---

# 1. Constructor Chaining using `this()`

`this()` calls another constructor in the **same class**.

### Example

```java
class Student {

    Student() {
        this(101);
        System.out.println("Default Constructor");
    }

    Student(int id) {
        System.out.println("Student ID: " + id);
    }
}

public class Test {

    public static void main(String[] args) {
        new Student();
    }
}
```

### Output

```text
Student ID: 101
Default Constructor
```

---

### Execution Flow

```text
new Student()

       │
       ▼

Student()

       │
       ▼

this(101)

       │
       ▼

Student(int)

       │
       ▼

Returns to Student()
```

---

# 2. Constructor Chaining using `super()`

`super()` calls the constructor of the **parent class**.

### Example

```java
class Animal {

    Animal() {
        System.out.println("Animal Constructor");
    }
}

class Dog extends Animal {

    Dog() {
        super();
        System.out.println("Dog Constructor");
    }
}

public class Test {

    public static void main(String[] args) {
        new Dog();
    }
}
```

### Output

```text
Animal Constructor
Dog Constructor
```

---

### Execution Flow

```text
new Dog()

      │
      ▼

Dog()

      │
      ▼

super()

      │
      ▼

Animal()

      │
      ▼

Returns to Dog()
```

---

# Rules of Constructor Chaining

- `this()` must be the **first statement** in a constructor.
- `super()` must also be the **first statement**.
- A constructor can contain **either** `this()` **or** `super()`, **not both**.
- If neither is written, Java automatically inserts `super()`.

---

## Invalid Example

```java
Student() {

    System.out.println("Hello");
    this(10);   // ❌ Compile-time Error
}
```

Reason:

`this()` must always be the first statement.

---

## `this()` vs `super()`

| `this()` | `super()` |
|-----------|-----------|
| Calls another constructor in the same class | Calls the parent class constructor |
| Used for constructor chaining within the same class | Used for parent class initialization |
| First statement in constructor | First statement in constructor |
| Cannot be used with `super()` in the same constructor | Cannot be used with `this()` in the same constructor |

---

# Interview Questions

### Q1. What is a constructor?

A constructor is a special member of a class that initializes an object when it is created.

---

### Q2. How many types of constructors are there in Java?

Primarily:

- Default Constructor
- Parameterized Constructor

Additionally:

- User-defined No-Argument Constructor
- Copy Constructor (user-defined)

---

### Q3. What is the difference between a default constructor and a no-argument constructor?

- **Default Constructor:** Generated by the compiler.
- **No-Argument Constructor:** Written explicitly by the programmer.

---

### Q4. Can constructors be overloaded?

**Yes.**

Constructors can be overloaded by changing the parameter list.

---

### Q5. Can constructors be overridden?

**No.**

Constructors are not inherited, so they cannot be overridden.

---

### Q6. What happens if I define a parameterized constructor only?

The compiler will **not** generate a default constructor.

Creating an object using `new ClassName()` will result in a compile-time error unless you define a no-argument constructor.

---

### Q7. What is constructor chaining?

Constructor chaining is the process of invoking one constructor from another using `this()` or invoking the parent constructor using `super()`.

---

### Q8. Does Java have a copy constructor?

No.

Unlike C++, Java does not provide a built-in copy constructor. It must be implemented manually.


### Q9. What is Constructor Overloading?

Constructor overloading is defining multiple constructors with different parameter lists in the same class.

---

### Q10. Why do we use Constructor Overloading?

To initialize objects in different ways depending on the data provided.

---

### Q11. Why do we use a Private Constructor?

To restrict object creation. It is mainly used in the Singleton design pattern and Utility classes.

---

### Q12. What is Constructor Chaining?

Constructor chaining is the process of calling one constructor from another using `this()` or `super()`.

---

### Q13. What is the difference between `this()` and `super()`?

- `this()` calls another constructor in the same class.
- `super()` calls the constructor of the parent class.

---

### Q14. Can we use both `this()` and `super()` in the same constructor?

**No.**

Both must be the first statement, so only one of them can be used.

---

---

# Quick Revision

- ✅ Constructor initializes an object.
- ✅ Constructor name must be the same as the class name.
- ✅ Constructor has no return type.
- ✅ Constructors are invoked automatically during object creation.
- ✅ Types: Default, User-defined No-Argument, Parameterized, Copy (user-defined).
- ✅ Constructors can be overloaded but cannot be overridden.
- ✅ `this()` calls another constructor in the same class.
- ✅ `super()` calls the parent class constructor.
- ✅ Constructor Overloading → Multiple constructors with different parameter lists.
- ✅ Private Constructor → Prevents object creation from outside the class.
- ✅ Constructor Chaining → Calling one constructor from another.
- ✅ `this()` → Calls a constructor in the same class.
- ✅ `super()` → Calls the parent class constructor.
- ✅ `this()` and `super()` must always be the first statement in a constructor.
- ✅ Only one of `this()` or `super()` can appear in a constructor.