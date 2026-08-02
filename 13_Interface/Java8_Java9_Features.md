# Java 8 & Java 9 Interface Features (Interview Notes)

Prior to Java 8, interfaces could contain **only abstract methods**.

From Java 8 onwards, interfaces became much more powerful by allowing **default** and **static** methods. Java 9 further introduced **private** methods.

---

# Java 8 Interface Features

## 1. Default Methods

### What is a Default Method?

A **default method** is a method inside an interface that has an implementation.

It is declared using the `default` keyword.

```java
interface Vehicle {

    default void start() {
        System.out.println("Starting vehicle...");
    }
}
```

---

## Why were Default Methods Introduced?

Before Java 8, adding a new method to an existing interface would break all implementing classes.

Example

```java
interface Vehicle {

    void start();

    void stop();      // Newly added
}
```

Now every class implementing `Vehicle` must implement `stop()`.

```java
class Car implements Vehicle {

    public void start() {}

    // Compile Error
    // stop() must also be implemented
}
```

This breaks existing applications.

---

### Solution (Java 8)

Provide a default implementation.

```java
interface Vehicle {

    void start();

    default void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

Now existing classes continue to work without modification.

---

## Advantages of Default Methods

- Backward compatibility.
- Existing implementations do not break.
- Allows interfaces to evolve.
- Reduces duplicate code.

---

## Overriding Default Methods

Implementing classes may override them.

```java
interface Vehicle {

    default void start() {
        System.out.println("Vehicle");
    }
}

class Car implements Vehicle {

    @Override
    public void start() {
        System.out.println("Car");
    }
}
```

---

# Multiple Interface Conflict (Diamond Problem)

Suppose two interfaces contain the same default method.

```java
interface A {

    default void show() {
        System.out.println("A");
    }
}

interface B {

    default void show() {
        System.out.println("B");
    }
}
```

Now,

```java
class Demo implements A, B {

}
```

❌ Compile-time Error

```
Duplicate default methods inherited
```

Java cannot decide which implementation to use.

---

## How to Resolve It?

Override the method.

```java
class Demo implements A, B {

    @Override
    public void show() {

        A.super.show();

        // or

        B.super.show();
    }
}
```

Calling parent interface implementation

```java
A.super.show();
```

or

```java
B.super.show();
```

---

# Extending an Interface with Default Methods

Suppose

```java
interface A {

    default void show() {
        System.out.println("A");
    }
}
```

---

## Case 1 — Child Interface Doesn't Override

```java
interface B extends A {

}
```

`B` automatically inherits the default method.

---

## Case 2 — Child Interface Overrides the Default Method

```java
interface B extends A {

    @Override
    default void show() {
        System.out.println("B");
    }
}
```

Now any implementing class gets B's implementation.

---

## Example

```java
interface A {

    default void display() {
        System.out.println("A");
    }
}

interface B extends A {

    @Override
    default void display() {
        System.out.println("B");
    }
}

class Demo implements B {

}
```

Output

```
B
```

---

# Java 8 Static Methods in Interface

## What is a Static Method?

A static method belongs to the interface itself.

It is **not inherited** by implementing classes.

```java
interface MathUtil {

    static int square(int n) {
        return n * n;
    }
}
```

Call

```java
MathUtil.square(5);
```

---

## Why Static Methods?

Before Java 8, utility methods had to be placed in separate utility classes.

Java 8 allows related utility methods to stay inside the interface.

---

## Rules

- Belongs to the interface.
- Cannot be overridden.
- Cannot be called using object reference.

✔ Correct

```java
MathUtil.square(10);
```

❌ Wrong

```java
obj.square();
```

---

# Java 9 Interface Features

Java 9 introduced

- Private methods
- Private static methods

---

# Private Methods

## Why Introduced?

Suppose multiple default methods contain duplicate code.

Before Java 9

```java
interface Demo {

    default void method1() {
        System.out.println("Common");
    }

    default void method2() {
        System.out.println("Common");
    }
}
```

Duplicate logic.

---

### Java 9 Solution

```java
interface Demo {

    private void common() {
        System.out.println("Common");
    }

    default void method1() {
        common();
    }

    default void method2() {
        common();
    }
}
```

The duplicate code is moved into a private helper method.

---

## Rules

- Accessible only inside the interface.
- Cannot be overridden.
- Cannot be inherited.

---

# Private Static Method

Used when helper logic is required for static methods.

```java
interface Demo {

    private static void helper() {
        System.out.println("Helper");
    }

    static void display() {
        helper();
    }
}
```

---

## Rules

- Accessible only inside the interface.
- Called only by interface methods.
- Not inherited.

---

# Evolution of Interfaces

| Java Version | Features |
|--------------|----------|
| Java 7 | Only abstract methods and constants |
| Java 8 | Default methods, Static methods |
| Java 9 | Private methods, Private Static methods |

---

# Summary of Interface Methods

| Method Type | Body Allowed | Java Version |
|-------------|-------------|--------------|
| Abstract | ❌ No | Java 1 |
| Default | ✅ Yes | Java 8 |
| Static | ✅ Yes | Java 8 |
| Private | ✅ Yes | Java 9 |
| Private Static | ✅ Yes | Java 9 |

---

# Frequently Asked Interview Questions

## Why were Default Methods introduced?

To maintain **backward compatibility**. They allow new methods to be added to existing interfaces without breaking older implementations.

---

## Why not simply use Abstract Classes?

Interfaces support **multiple inheritance**, whereas abstract classes do not.

Default methods provide backward compatibility while preserving multiple inheritance.

---

## Can Default Methods be overridden?

Yes.

Implementing classes and child interfaces can override default methods.

---

## Can Interface Static Methods be overridden?

No.

Static methods belong to the interface, not the implementing class.

---

## What happens if two interfaces have the same default method?

The implementing class **must override** the method to resolve the ambiguity.

```java
class Demo implements A, B {

    @Override
    public void show() {
        A.super.show();
    }
}
```

---

## Can an Interface override another Interface's Default Method?

Yes.

```java
interface B extends A {

    @Override
    default void show() {
        System.out.println("New Implementation");
    }
}
```

---

## Why were Private Methods introduced in Java 9?

To avoid code duplication among multiple default and static methods by extracting common logic into reusable helper methods.

---

## Can Private Interface Methods be accessed outside the Interface?

No.

They are accessible only within the interface.

---

# Most Asked Interview Questions ⭐

1. Why were default methods introduced in Java 8?
2. Explain backward compatibility with an example.
3. What problem do default methods solve?
4. Difference between default and static methods.
5. Can default methods be overridden?
6. Can static interface methods be overridden?
7. How is the default method conflict resolved when implementing multiple interfaces?
8. How can a child interface override a default method?
9. Why were private methods introduced in Java 9?
10. Difference between default, static, private, and private static methods.

---

# Quick Revision

- ✅ Java 8 introduced **default** and **static** methods in interfaces.
- ✅ Java 9 introduced **private** and **private static** methods.
- ✅ Default methods provide **backward compatibility**.
- ✅ Default methods **can be overridden**.
- ✅ Static methods belong to the **interface** and **cannot be overridden**.
- ✅ If two interfaces have the same default method, the implementing class **must override** it.
- ✅ Child interfaces can override inherited default methods.
- ✅ Private methods are helper methods used to eliminate duplicate code inside interfaces.