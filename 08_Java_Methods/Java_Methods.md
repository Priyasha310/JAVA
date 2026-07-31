# Java Methods

## What is a Method?

A **method** is a block of code that performs a **specific task**. It can be called whenever required, promoting **code reusability**, **modularity**, and **better code organisation**.

### Advantages of Methods

- Code Reusability
- Modularity
- Easy Maintenance
- Better Readability
- Reduces Code Duplication

---

# Method Syntax

```java
accessModifier returnType methodName(parameterList) throws ExceptionType {
    // Method Body
}
```

---

## Components of a Method

| Component | Description |
|-----------|-------------|
| **Access Modifier** | Defines the accessibility of the method (`public`, `private`, `protected`, `default`) |
| **Return Type** | Specifies the type of value returned by the method (`int`, `String`, `void`, etc.) |
| **Method Name** | Name used to invoke the method |
| **Parameter List** | Input values accepted by the method |
| **throws** *(Optional)* | Declares exceptions that the method may throw |
| **Method Body** | Contains the implementation (statements) |

---

## Example

```java
public int add(int a, int b) {
    return a + b;
}
```

---

# Methods based on who defines it

## 1. System-Defined Methods

System-defined methods are **predefined methods provided by Java libraries (JDK/JRE)**. Developers can directly use these methods without writing their implementation.

### Examples

```java
System.out.println("Hello");
Math.sqrt(25);
str.length();
str.toUpperCase();
obj.toString();
obj.equals(other);
```

### Common System-Defined Methods

- `main()`
- `toString()`
- `equals()`
- `hashCode()`
- `length()`
- `charAt()`
- `substring()`
- `Math.sqrt()`
- `Math.max()`
- `System.out.println()`

> **Note:** These methods are provided by Java libraries (JDK/JRE). Developers simply invoke them.

---

## 2. User-Defined Methods

User-defined methods are created by programmers to perform application-specific tasks.

### Example

```java
public class Test {

    public static void greet() {
        System.out.println("Welcome to Java");
    }

    public static void main(String[] args) {
        greet();
    }
}
```

### Output

```text
Welcome to Java
```

---

# Methods based on polymorphism (method behavior)

## 1. Overloaded Methods

Method Overloading means having **multiple methods with the same name but different parameter lists**.

Methods can differ by:
- Number of parameters
- Type of parameters
- Order of parameters

---

### Example 1: Different Number of Parameters

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

---

### Example 2: Different Parameter Types

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}
```

---

### Example 3: Different Parameter Order

```java
class Example {

    void display(int id, String name) {

    }

    void display(String name, int id) {

    }
}
```

---

### Invalid Overloading

Methods **cannot** be overloaded by changing **only the return type**.

```java
int add(int a, int b) {
    return a + b;
}

// ❌ Compile-time Error
double add(int a, int b) {
    return a + b;
}
```

**Reason:** Java identifies overloaded methods using the **method signature (method name + parameter list)**. The return type is **not** part of the method signature.

---

## 2. Overridden Methods

Method Overriding occurs when a **subclass provides its own implementation** of a method that is already defined in its parent class.

The overriding method must have:
- Same method name
- Same parameter list
- Same return type (or covariant return type)
- Cannot have a more restrictive access modifier

Method Overriding is used to achieve **Runtime Polymorphism (Dynamic Method Dispatch)**.

---

### Example

```java
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

public class Test {

    public static void main(String[] args) {
        Animal obj = new Dog();
        obj.sound();
    }
}
```

### Output

```text
Dog barks
```

---

### Memory Representation

```text
Animal obj
      │
      ▼
+----------------+
| Dog Object     |
+----------------+
        │
        ▼
Calls Dog's sound()
```

Although the reference type is `Animal`, the actual object is `Dog`, so Java invokes the overridden method at **runtime**.

---

## Rules for Method Overriding

- Method name must be the same.
- Parameter list must be the same.
- Return type must be the same or covariant.
- Access modifier cannot be more restrictive than the parent method.
- `final` methods cannot be overridden.
- `static` methods cannot be overridden (they are hidden).
- `private` methods cannot be overridden because they are not inherited.
- Constructors cannot be overridden.

---

## Importance of `@Override`

The `@Override` annotation tells the compiler that the method is intended to override a parent method.

If the method signature is incorrect, the compiler reports an error.

### Example

```java
class Animal {
    void sound() {
    }
}

class Dog extends Animal {

    @Override
    void sound() {
    }
}
```

Using `@Override` is considered a best practice.

---

## Method Overloading vs Method Overriding

| Method Overloading | Method Overriding |
|--------------------|-------------------|
| Same class (or inherited class) | Parent and Child class |
| Same method name | Same method name |
| Different parameter list | Same parameter list |
| Compile-time polymorphism | Runtime polymorphism |
| Inheritance not mandatory | Inheritance is mandatory |

---

# Methods Based on Object/Class Association

This classification is based on **whether a method belongs to the class or to an object (instance) of the class**.

## 1. Static Method
A **static method** belongs to the **class**, not to any specific object.
It can be called **without creating an object** using the class name.
Static Methods cannot access non static variables and methods
Static Methods cannot be overridden.

---
### When to declare methods static 
Methods should be declared static when they do not depend on instance variables or instance methods of the class.
Utility or helper methods that perform operations without needing to access instance data are good candidates for static methods.

### Example

```java
class Calculator {

    static void display() {
        System.out.println("Static Method");
    }
}

public class Test {

    public static void main(String[] args) {
        Calculator.display();
    }
}
```

### Output

```text
Static Method
```

---

### Characteristics

- Belongs to the class.
- Called using the class name.
- Object creation is not required.
- Can access only static members directly.
- Cannot use the `this` or `super` keyword.

---

### Common Static Methods

```java
Math.sqrt()
Math.max()
Integer.parseInt()
System.currentTimeMillis()
```

---

## 2. Instance Method
An **instance method** belongs to an **object** of a class.

To invoke an instance method, an object must first be created.

---

### Example

```java
class Student {

    void study() {
        System.out.println("Studying...");
    }
}

public class Test {

    public static void main(String[] args) {
        Student s = new Student();
        s.study();
    }
}
```

### Output

```text
Studying...
```

---

### Characteristics

- Belongs to an object.
- Requires object creation.
- Can access both instance and static members.
- Can use the `this` and `super` keywords.

---

## Static vs Instance Methods

| Static Method | Instance Method |
|---------------|-----------------|
| Belongs to the class | Belongs to an object |
| Object not required | Object required |
| Called using class name | Called using object reference |
| Can access only static members directly | Can access both static and instance members |
| Cannot use `this` or `super` | Can use `this` and `super` |

---

# Methods Based on Implementation

This classification is based on **whether the method provides an 

## 1. Concrete Method
A **concrete method** is a method that **contains an implementation (method body)**.

It performs a specific task when called.

---

### Example

```java
class Animal {

    void sound() {
        System.out.println("Animal makes a sound");
    }
}
```

---

### Characteristics

- Has a method body.
- Can be invoked directly.
- Can exist in both normal and abstract classes.

---

## 2. Abstract Method
An **abstract method** is declared **without a method body**.

It specifies **what should be done**, but not **how it should be done**.

Subclasses (child class) are responsible for providing the implementation.

---

### Syntax

```java
abstract returnType methodName();
```

---

### Example

```java
abstract class Animal {
    abstract void sound();
}
```

```java
class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

### Output

```text
Dog barks
```

---

### Rules for Abstract Methods

- Declared using the `abstract` keyword.
- Do not have a method body.
- Must be inside an abstract class or interface.
- Must be implemented by the first concrete subclass.
- Cannot be `private`, `static`, or `final`.

---

## Concrete vs Abstract Methods

| Concrete Method | Abstract Method |
|-----------------|-----------------|
| Has implementation | No implementation |
| Can be called directly | Cannot be called directly |
| Exists in normal or abstract classes | Exists only in abstract classes or interfaces |
| No need for subclasses to implement | Must be implemented by subclasses |

---

# Methods Based on Inheritance Restrictions

This classification is based on **whether a method can be overridden by subclasses**.

## 1. Final Method
A **final method** cannot be overridden by subclasses.

It ensures that the method implementation remains unchanged.

---

### Example

```java
class Animal {

    final void sleep() {
        System.out.println("Sleeping...");
    }
}

class Dog extends Animal {

    // ❌ Compile-time Error
    // void sleep() { }
}
```

---

### Characteristics

- Declared using the `final` keyword.
- Cannot be overridden.
- Can be inherited.
- Used when behaviour should remain unchanged.

---

## 2. Non-final Method
A **non-final method** can be overridden by subclasses.

This enables **Runtime Polymorphism**.

---

### Example

```java
class Animal {

    void sound() {
        System.out.println("Animal Sound");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog Barks");
    }
}
```

### Output

```text
Dog Barks
```

---

### Characteristics

- Can be overridden.
- Supports Runtime Polymorphism.
- Used when different subclasses require different implementations.

---

## Final vs Non-final Methods

| Final Method | Non-final Method |
|---------------|------------------|
| Cannot be overridden | Can be overridden |
| Behaviour is fixed | Behaviour can change in subclasses |
| Prevents Runtime Polymorphism | Supports Runtime Polymorphism |
| Declared using `final` | No `final` keyword |

---
# Variable Arguements
varargs allows a method to accept zero or more arguments of a specified type. It is denoted by an ellipsis (`...`) in the parameter list. Only one argument can be declared as varargs, and it must be the last parameter in the method signature.

Example:
```java
void printNumbers(int... numbers) {
    for (int number : numbers) {
        System.out.println(number);
    }
}
```

# Interview Questions

### Q1. What is the difference between a static method and an instance method?

- A **static method** belongs to the class and can be called without creating an object.
- An **instance method** belongs to an object and requires object creation.

---

### Q2. Can a static method access instance variables directly?

**No.**

A static method can directly access only static members because it belongs to the class, not to any object.

---

### Q3. What is an abstract method?

An abstract method is a method declared without a body. It must be implemented by a concrete subclass.

---

### Q4. Can an abstract method be `final`?

**No.**

An abstract method must be overridden, whereas a final method cannot be overridden. Therefore, a method cannot be both `abstract` and `final`.

---

### Q5. What is the difference between a concrete method and an abstract method?

- A **concrete method** has an implementation.
- An **abstract method** has only a declaration and must be implemented by subclasses.

---

### Q6. What is a final method?

A final method is a method that cannot be overridden by subclasses.

---

### Q7. Why do we use final methods?

To prevent modification of critical behaviour and ensure the implementation remains unchanged.

---

# Quick Revision

- ✅ **Static methods** belong to the class.
- ✅ **Instance methods** belong to objects.
- ✅ **Concrete methods** have an implementation.
- ✅ **Abstract methods** declare behaviour without implementation.
- ✅ **Final methods** cannot be overridden.
- ✅ **Non-final methods** can be overridden and support Runtime Polymorphism.

# Access Modifiers

Access modifiers control the visibility of methods.

| Modifier | Same Class | Same Package | Subclass (Different Package) | Other Package |
|----------|:----------:|:------------:|:----------------------------:|:-------------:|
| **public** | ✅ | ✅ | ✅ | ✅ |
| **protected** | ✅ | ✅ | ✅ | ❌ |
| **default** *(no modifier)* | ✅ | ✅ | ❌ | ❌ |
| **private** | ✅ | ❌ | ❌ | ❌ |

---

## 1. Public Method

Accessible from **any class** in **any package**.

```java
public void display() {
    System.out.println("Public Method");
}
```

---

## 2. Private Method

Accessible **only within the same class**.

```java
private void calculateSalary() {
}
```

---

## 3. Protected Method

Accessible:

- Within the same package.
- By subclasses in different packages.

```java
protected void showDetails() {
}
```

---

## 4. Default Method (Package-Private)

If no access modifier is specified, the method is accessible **only within the same package**.

```java
void printData() {
}
```

---

# Package

## Definition

A **package** is a namespace that groups related classes and interfaces.

It helps to:

- Organise code
- Avoid class name conflicts
- Provide access protection

---

## Example

```java
package com.company.employee;

public class Employee {
}
```

---

# Method Naming Conventions

Java follows the **camelCase** naming convention for method names.

### Rules

- Method names should be **verbs** because they represent actions.
- Start with a **lowercase** letter.
- Capitalise the first letter of each subsequent word.

# Methods Based on Parameters and Return Type

## 1. No Parameters and No Return Value

```java
public void greet() {
    System.out.println("Hello");
}
```

---

## 2. Parameters but No Return Value

```java
public void greet(String name) {
    System.out.println("Hello " + name);
}
```

---

## 3. No Parameters but Return Value

```java
public int getNumber() {
    return 100;
}
```

---

## 4. Parameters and Return Value

```java
public double calculateArea(double radius) {
    return 3.14 * radius * radius;
}
```

---

# Method Call

```java
public class Test {

    public static void main(String[] args) {
        Test obj = new Test();
        obj.greet();
    }

    public void greet() {
        System.out.println("Hello");
    }
}
```

### Output

```text
Hello
```

---

# `void` vs Return Type

| `void` | Return Type |
|---------|-------------|
| Returns nothing | Returns a value |
| No `return` statement required | `return` statement is mandatory |

### Example

```java
void print() {

}
```

```java
int square(int n) {
    return n * n;
}
```

---

# Parameter vs Argument

## Parameter

A variable declared in the method definition.

```java
public void display(String name)
```

Here,

```java
String name
```

is a **parameter**.

---

## Argument

The actual value passed during the method call.

```java
display("Priyasha");
```

Here,

```java
"Priyasha"
```

is the **argument**.

---

## Example

```java
public class Test {

    public static void greet(String name) {
        System.out.println("Hello " + name);
    }

    public static void main(String[] args) {
        greet("Priyasha");
    }
}
```

Parameter

```java
String name
```

Argument

```java
"Priyasha"
```

---

# Interview Questions

### Q1. Can we override a `static` method?

**No.**

Static methods belong to the class, not the object. They are **hidden**, not overridden.

---

### Q2. Can we override a `final` method?

**No.**

A `final` method cannot be overridden.

---

### Q3. Can we override a `private` method?

**No.**

Private methods are not inherited, so they cannot be overridden.

---

### Q4. What is method overloading?

Method overloading is defining multiple methods with the same name but different parameter lists (number, type, or order of parameters).

---

### Q5. Can methods be overloaded only by changing the return type?

**No.**

Changing only the return type does **not** constitute method overloading because Java considers only the **method signature (name + parameters)**.

---

### Q6. Why do we use methods?

- Code reusability
- Readability
- Modularity
- Easy maintenance
- Reduced code duplication

---

### Q7. What are the components of a method?

- Access Modifier
- Return Type
- Method Name
- Parameters
- Method Body
- Optional `throws` clause

---

### Q8. What is the difference between a parameter and an argument?

- **Parameter:** Variable declared in the method definition.
- **Argument:** Actual value passed during the method call.

---

### Q9. What is the difference between `void` and a return type?

- `void` methods do not return any value.
- Methods with a return type must return a value.

---

### Q10. What is a package?

A package is a namespace that groups related classes and interfaces, helping organise code, avoid naming conflicts, and control access.

---

# Quick Revision

- ✅ Methods are reusable blocks of code.
- ✅ Java methods can be system-defined, user-defined, or overloaded.
- ✅ Method overloading depends on the parameter list, not the return type.
- ✅ Method names follow camelCase.
- ✅ Parameters are declared in the method; arguments are passed during the call.
- ✅ `void` returns nothing; other return types return a value.
- ✅ Access modifiers control method visibility.
- ✅ Packages organise related classes and prevent naming conflicts.
