# Interface in Java

## What is an Interface?

An **Interface** is a blueprint of a class that defines **what a class should do**, but not **how it should do it**.

It is primarily used to achieve:

- Abstraction
- Multiple Inheritance
- Polymorphism
- Loose Coupling

An interface specifies a **contract** that implementing classes must follow.

---

# Why Do We Need an Interface?

Suppose different payment methods are available.

Without Interface

```java
class CreditCard {
    void pay() {
    }
}

class UPI {
    void pay() {
    }
}
```

No common contract exists.

---

Using Interface

```java
interface Payment {
    void pay();
}

class CreditCard implements Payment {

    @Override
    public void pay() {
    }
}

class UPI implements Payment {

    @Override
    public void pay() {
    }
}
```

Every payment class must implement `pay()`.

---

## Advantages

- Achieves Abstraction.
- Supports Runtime Polymorphism.
- Supports Multiple Inheritance.
- Promotes Loose Coupling.
- Improves Code Reusability.
- Easier Testing and Maintenance.

---

# Syntax

```java
interface Animal {
    void sound();
}
```

Implementation

```java
class Dog implements Animal {

    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
}
```

---

# Why Interface Helps Achieve Abstraction?

An interface declares **what should be done**, while the implementing class decides **how it is done**.

```java
interface Car {
    void start();
}
```

User only knows

```java
car.start();
```

Internal implementation remains hidden.

---

# Why Interface Helps Achieve Polymorphism?

```java
interface Animal {
    void sound();
}

class Dog implements Animal {
    public void sound() {
        System.out.println("Dog");
    }
}

class Cat implements Animal {

    public void sound() {
        System.out.println("Cat");
    }
}
```

Usage

```java
Animal animal = new Dog();
animal.sound();
```

Output

```
Dog
```

Changing only the object changes the behavior.

```java
animal = new Cat();
animal.sound();
```

Output

```
Cat
```

---

# Why Interface Supports Multiple Inheritance?

Java does **not** allow multiple inheritance with classes.

❌

```java
class C extends A, B {

}
```

However, a class can implement multiple interfaces.

```java
interface Camera {
    void click();
}

interface Music {
    void play();
}

class Phone implements Camera, Music {

    public void click() {
    }

    public void play() {
    }
}
```

This avoids the **Diamond Problem**.

---

# Methods in Interface

From Java 8 onwards, interfaces can contain multiple types of methods.

| Method Type | Java Version |
|-------------|--------------|
| Abstract Method | Java 1 |
| Default Method | Java 8 |
| Static Method | Java 8 |
| Private Method | Java 9 |
| Private Static Method | Java 9 |

---

## 1. Abstract Method

No implementation.

```java
interface Animal {
    void sound();
}
```

By default

```java
public abstract void sound();
```

---

## 2. Default Method (Java 8)

Has implementation.

```java
interface Animal {

    default void sleep() {
        System.out.println("Sleeping");
    }
}
```

Purpose

- Add new functionality without breaking existing implementations.

---

## 3. Static Method (Java 8)

Belongs to the interface.

```java
interface MathUtil {

    static int square(int x) {
        return x * x;
    }
}
```

Call

```java
MathUtil.square(5);
```

---

## 4. Private Method (Java 9)

Used only inside the interface.

```java
interface Demo {

    private void display() {
    }
}
```

Purpose

- Avoid duplicate code between default methods.

---

## 5. Private Static Method (Java 9)

```java
private static void helper() {

}
```

Only accessible within the interface.

---

# Fields in Interface

All interface variables are implicitly

```text
public static final
```

Example

```java
interface Demo {
    int MAX = 100;
}
```

Compiler treats it as

```java
public static final int MAX = 100;
```

Cannot be modified.

---

# Interface Implementation

```java
interface Animal {
    void sound();
}

class Dog implements Animal {

    @Override
    public void sound() {
        System.out.println("Dog");
    }
}
```

---

## Rules

### 1. Overriding method cannot reduce visibility

✔ Correct

```java
public void sound() {

}
```

❌ Wrong

```java
protected void sound() {

}
```

Reason

Interface methods are `public`.

---

### 2. Concrete Class

Must implement **all abstract methods**.

```java
class Dog implements Animal {

    @Override
    public void sound() {

    }
}
```

---

### 3. Abstract Class

May skip implementation.

```java
abstract class AnimalImpl implements Animal {

}
```

---

### 4. Multiple Interface Implementation

```java
interface Camera {
}

interface Music {
}

class Phone implements Camera, Music {
}
```

A class can implement multiple interfaces.

---

# Nested Interface

An interface declared inside another interface or class.

```java
interface Outer {

    interface Inner {
        void display();
    }
}
```

Implementation

```java
class Demo implements Outer.Inner {

    public void display() {
    }
}
```

---

## Why Nested Interface?

- Logical grouping.
- Better encapsulation.
- Used when the inner interface is relevant only to the outer interface/class.

---

# Interface vs Abstract Class

| Feature | Abstract Class | Interface |
|---------|----------------|-----------|
| **Keyword** | `abstract` | `interface` |
| **Inheritance Keyword** | Child class uses `extends` | Implementing class uses `implements` |
| **Purpose** | Used when classes share common state and implementation | Used to define a contract (behavior) |
| **Abstraction** | Supports partial abstraction (0–100%) | Primarily supports abstraction (contract) |
| **Methods** | Can have abstract and concrete methods | Can have abstract, `default`, `static`, and `private` methods (Java 8/9+) |
| **Constructors** | Can have constructors | Cannot have constructors |
| **Instance Variables** | Can have instance variables | Cannot have instance variables |
| **Fields** | Can be `private`, `protected`, `public`, `static`, `final`, etc. | All fields are implicitly `public static final` |
| **Method Access Modifiers** | Methods can be `private`, `protected`, `public`, or package-private | Abstract methods are implicitly `public`; private methods are supported from Java 9 |
| **Variable Access Modifiers** | Any access modifier | Always `public static final` |
| **Inheritance** | Can extend one class and implement multiple interfaces | Can extend one or more interfaces only |
| **Multiple Inheritance** | ❌ Not supported through classes | ✅ Supported through multiple interfaces |
| **Method Implementation** | Can provide implementation for inherited methods | Cannot provide implementation for abstract methods of another interface (except through `default` methods defined in the same interface) |
| **Object Creation** | Cannot be instantiated | Cannot be instantiated |
| **State** | Can maintain object state using instance variables | Cannot maintain object state (only constants) |
| **When to Use** | When child classes share common code and state | When unrelated classes should follow the same contract |

---

# Interface vs Class

| Interface | Class |
|------------|-------|
| Blueprint | Complete implementation |
| Cannot create object | Can create object |
| Defines contract | Provides implementation |
| Supports multiple inheritance | Does not support multiple inheritance |

---

# When to Use Interface?

Use Interface when:

- Multiple classes share the same behavior.
- Multiple inheritance is required.
- Loose coupling is needed.
- API contracts need to be defined.
- Runtime polymorphism is required.

---

# Real-world Examples

Java interfaces

- Runnable
- Comparable
- Comparator
- List
- Set
- Map
- AutoCloseable

---

# Common Interview Questions

## What is an Interface?

An Interface is a blueprint that defines a contract for implementing classes. It is used to achieve abstraction, polymorphism, loose coupling, and multiple inheritance.

---

## Why use Interface instead of Abstract Class?

Use Interface when only behavior (contract) needs to be defined.

Use Abstract Class when behavior and shared implementation are both required.

---

## Can an Interface have constructors?

No.

Interfaces cannot be instantiated.

---

## Can an Interface have variables?

Yes.

All variables are implicitly

```java
public static final
```

---

## Can an Interface have method implementations?

Yes.

Using

- Default methods (Java 8)
- Static methods (Java 8)
- Private methods (Java 9)

---

## Can one class implement multiple interfaces?

Yes.

```java
class A implements X, Y {
}
```

---

## Can an Interface extend another Interface?

Yes.

```java
interface A {
}

interface B extends A {
}
```

Multiple interface inheritance is also allowed.

```java
interface C extends A, B {
}
```

---

## Can an Interface extend a Class?

No.

Interfaces can extend only interfaces.

---

## Can a Class extend an Interface?

No.

Classes implement interfaces.

---

# Most Asked Interview Questions ⭐

1. What is an Interface?
2. Why do we need an Interface?
3. Interface vs Abstract Class.
4. Interface vs Class.
5. Why does Interface support multiple inheritance?
6. Types of methods in Interface.
7. Types of variables in Interface.
8. Why are interface variables `public static final`?
9. Can interfaces have constructors?
10. Can interfaces have private methods?
11. Can one class implement multiple interfaces?
12. Can an interface extend another interface?
13. Nested Interface.
14. Explain interface implementation rules.

---

# Quick Revision

- ✅ Interface defines a contract.
- ✅ Used for abstraction, polymorphism, loose coupling and multiple inheritance.
- ✅ Interface methods are `public abstract` by default.
- ✅ Interface variables are `public static final`.
- ✅ Supports abstract, default, static and private methods.
- ✅ One class can implement multiple interfaces.
- ✅ Interface can extend multiple interfaces.
- ✅ Concrete class must implement all abstract methods.
- ✅ Abstract class is not forced to implement all interface methods.
- ✅ Overriding method cannot have more restrictive access than `public`.