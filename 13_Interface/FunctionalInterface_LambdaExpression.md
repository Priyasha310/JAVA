# Functional Interface & Lambda Expressions (Interview Notes)

Introduced in **Java 8** to support **Functional Programming** and **Lambda Expressions**.

---

# What is a Functional Interface?

A **Functional Interface** is an interface that contains **exactly one abstract method (SAM - Single Abstract Method)**.

It may also contain:

- Default methods
- Static methods
- Private methods (Java 9)
- Methods inherited from `Object`

These do **not** affect its functional interface property.

---

## Syntax

```java
@FunctionalInterface
interface Calculator {

    int add(int a, int b);
}
```

> `@FunctionalInterface` is **optional**, but recommended. It allows the compiler to verify that the interface contains only one abstract method.

---

## Why Do We Need Functional Interfaces?

Functional Interfaces enable:

- Lambda Expressions
- Method References
- Functional Programming
- Cleaner and concise code

---

# Different Ways to Implement a Functional Interface

## 1. Using a Normal Class

```java
interface Greeting {

    void sayHello();
}

class GreetingImpl implements Greeting {

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
}
```

Usage

```java
Greeting greeting = new GreetingImpl();
greeting.sayHello();
```

---

## 2. Using an Anonymous Inner Class (Before Java 8)

```java
Greeting greeting = new Greeting() {

    @Override
    public void sayHello() {
        System.out.println("Hello");
    }
};

greeting.sayHello();
```

---

## 3. Using Lambda Expression (Java 8) ⭐

```java
Greeting greeting = () -> System.out.println("Hello");

greeting.sayHello();
```

This is the preferred approach in modern Java.

---

# What is a Lambda Expression?

A **Lambda Expression** is a concise way to implement the **single abstract method** of a Functional Interface.

Instead of writing a separate class or anonymous class, the implementation is written inline.

---

## Syntax

```java
(parameters) -> expression
```

or

```java
(parameters) -> {

    // method body
}
```

Example

```java
Calculator add = (a, b) -> a + b;
```

---

# Why Were Lambda Expressions Introduced?

Before Java 8

- Too much boilerplate code.
- Anonymous inner classes were verbose.
- Harder to read.

Java 8 introduced Lambda Expressions to:

- Reduce boilerplate.
- Improve readability.
- Enable Functional Programming.
- Simplify collection operations (Streams API).

---

# Functional Interface with Lambda

```java
@FunctionalInterface
interface Calculator {

    int add(int a, int b);
}
```

Implementation

```java
Calculator calculator = (a, b) -> a + b;

System.out.println(calculator.add(10, 20));
```

Output

```
30
```

---

# Rules of Functional Interfaces

A Functional Interface:

- Must contain exactly **one abstract method**.
- Can contain any number of:
  - Default methods
  - Static methods
  - Private methods
- Can extend another interface.
- Can have methods inherited from `Object`.

---

# Functional Interface Extending Another Interface

This is a commonly asked interview topic.

---

## Scenario 1 — Parent has ONE Abstract Method ✔

```java
interface LivingThing {

    void breathe();
}

@FunctionalInterface
interface Bird extends LivingThing {

    void breathe();
}
```

Total abstract methods = **1**

✔ Valid Functional Interface

---

## Scenario 2 — Parent has One Abstract Method, Child Adds Another ❌

```java
interface LivingThing {

    void breathe();
}

@FunctionalInterface
interface Bird extends LivingThing {

    void fly();
}
```

Total abstract methods = **2**

- `breathe()`
- `fly()`

❌ Not a Functional Interface

Compiler Error

```
Multiple non-overriding abstract methods found
```

---

## Scenario 3 — Parent Already Functional, Child Does Not Add Methods ✔

```java
interface Animal {

    void sound();
}

@FunctionalInterface
interface Dog extends Animal {

}
```

Still contains only one abstract method.

✔ Valid

---

## Scenario 4 — Child Overrides Parent Method ✔

```java
interface Animal {

    void sound();
}

@FunctionalInterface
interface Dog extends Animal {

    @Override
    void sound();
}
```

Still one abstract method.

✔ Valid

---

## Scenario 5 — Parent Has Default Method ✔

```java
interface Animal {

    default void sleep() {

    }
}

@FunctionalInterface
interface Dog extends Animal {

    void sound();
}
```

Default methods are **not counted**.

Only one abstract method exists.

✔ Valid

---

# Types of Functional Interfaces (java.util.function)

Java provides several predefined Functional Interfaces.

---

# 1. Consumer<T>

Consumes data but **returns nothing**.

Method

```java
void accept(T t)
```

Example

```java
Consumer<String> consumer =
        name -> System.out.println(name);

consumer.accept("Priyasha");
```

Use Case

- Printing
- Logging
- Updating objects

---

# 2. Supplier<T>

Supplies data.

Takes **no input** and returns a value.

Method

```java
T get()
```

Example

```java
Supplier<String> supplier =
        () -> "Hello";

System.out.println(supplier.get());
```

Use Case

- Lazy initialization
- Object creation
- Random value generation

---

# 3. Function<T, R>

Accepts one input and returns one output.

Method

```java
R apply(T t)
```

Example

```java
Function<String, Integer> function =
        str -> str.length();

System.out.println(function.apply("Java"));
```

Output

```
4
```

Use Case

- Data transformation
- Mapping
- Streams

---

# 4. Predicate<T>

Accepts one input and returns **boolean**.

Method

```java
boolean test(T t)
```

Example

```java
Predicate<Integer> predicate =
        age -> age >= 18;

System.out.println(predicate.test(20));
```

Output

```
true
```

Use Case

- Filtering
- Validation
- Searching

---

# Consumer vs Supplier vs Function vs Predicate

| Interface | Input | Output | Main Method | Common Use Case |
|-----------|-------|--------|-------------|-----------------|
| Consumer<T> | Yes | No | `accept()` | Consume/Print |
| Supplier<T> | No | Yes | `get()` | Generate Data |
| Function<T,R> | Yes | Yes | `apply()` | Transform Data |
| Predicate<T> | Yes | Boolean | `test()` | Filtering/Validation |

---

# Functional Interface vs Normal Interface

| Functional Interface | Normal Interface |
|----------------------|------------------|
| Exactly one abstract method | Any number of abstract methods |
| Supports Lambda Expressions | Cannot be implemented using Lambda |
| Also called SAM Interface | General interface |

---

# Functional Interface vs Abstract Class

| Functional Interface | Abstract Class |
|----------------------|----------------|
| One abstract method | Multiple abstract/concrete methods |
| Supports Lambda | Does not support Lambda |
| Multiple inheritance | Single inheritance |
| No constructors | Can have constructors |

---

# Common Interview Questions

## What is a Functional Interface?

A Functional Interface is an interface that contains exactly one abstract method. It is also called a **SAM (Single Abstract Method) Interface** and is mainly used with Lambda Expressions.

---

## Is `@FunctionalInterface` mandatory?

No.

It is optional but recommended because the compiler validates that only one abstract method exists.

---

## Can a Functional Interface have default methods?

Yes.

Default methods are **not counted** as abstract methods.

---

## Can a Functional Interface have static methods?

Yes.

They belong to the interface and do not affect the SAM rule.

---

## Can a Functional Interface extend another Interface?

Yes.

As long as the total number of abstract methods remains exactly **one**.

---

## Can a Functional Interface have Object class methods?

Yes.

Methods like `toString()`, `equals()`, and `hashCode()` are inherited from `Object` and are **not counted** as abstract methods.

---

## Why are Lambda Expressions possible only with Functional Interfaces?

Because the compiler needs exactly **one abstract method** to determine which method the Lambda Expression should implement.

If multiple abstract methods exist, the compiler cannot determine the target method.

---

# Most Asked Interview Questions ⭐

1. What is a Functional Interface?
2. What is a SAM Interface?
3. Why was `@FunctionalInterface` introduced?
4. Different ways to implement a Functional Interface.
5. Why were Lambda Expressions introduced?
6. Why can Lambda Expressions implement only Functional Interfaces?
7. Explain Functional Interface inheritance scenarios.
8. What happens if a child interface adds another abstract method?
9. Difference between Consumer, Supplier, Function, and Predicate.
10. Difference between Functional Interface and Abstract Class.

---

# Quick Revision

- ✅ Functional Interface contains exactly **one abstract method (SAM)**.
- ✅ `@FunctionalInterface` is optional but recommended.
- ✅ Functional Interfaces support Lambda Expressions.
- ✅ A Functional Interface can contain default, static, and private methods.
- ✅ It can extend another interface only if the total abstract methods remain **one**.
- ✅ Lambda Expressions provide concise implementations of Functional Interfaces.
- ✅ Four commonly used predefined Functional Interfaces are:
  - `Consumer<T>`
  - `Supplier<T>`
  - `Function<T, R>`
  - `Predicate<T>`