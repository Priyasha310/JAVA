# Generic Class in Java

Introduced in **Java 5**, Generics allow classes, interfaces, and methods to work with different data types while providing **compile-time type safety**.

---

# What is a Generic Class?

A **Generic Class** is a class that can work with different data types using **type parameters**.

Instead of hardcoding a data type, the actual type is specified when creating the object.

---

## Why Do We Need Generic Classes?

Without Generics

- No compile-time type safety.
- Explicit type casting is required.
- Higher chance of `ClassCastException`.
- Duplicate code for different data types.

With Generics

- Compile-time Type Safety.
- Code Reusability.
- Eliminates Explicit Type Casting.
- Better Readability.
- Reduces Runtime Errors.

---

# How to Define a Generic Class

## Syntax

```java
class Box<T> {

}
```

`T` is called a **Type Parameter**.

Common naming conventions

| Type Parameter | Meaning |
|---------------|---------|
| T | Type |
| E | Element |
| K | Key |
| V | Value |
| N | Number |

---

# Generic Class Example

```java
class Box<T> {

    private T value;

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
```

Usage

```java
Box<String> box = new Box<>();

box.setValue("Java");

String str = box.getValue();
```

---

# How It Works

```java
Box<String> box = new Box<>();
```

Compiler internally understands

```text
T → String
```

Similarly

```java
Box<Integer> box = new Box<>();
```

Compiler understands

```text
T → Integer
```

Thus, the same class works with multiple data types.

---

# Multiple Type Parameters

A Generic Class can have more than one type parameter.

```java
class Pair<K, V> {

    private K key;
    private V value;

    Pair(K key, V value) {

        this.key = key;
        this.value = value;
    }
}
```

Usage

```java
Pair<Integer, String> student =
        new Pair<>(101, "Priyasha");
```

---

# Generic Class Inheritance

Generic classes fully support inheritance.

```java
class Box<T> {

    T value;
}

class GiftBox<T> extends Box<T> {

}
```

Usage

```java
GiftBox<String> gift = new GiftBox<>();
```

---

# Non-Generic Subclass

A generic parent can have a non-generic child.

```java
class Box<T> {

    T value;
}

class StringBox extends Box<String> {

}
```

Usage

```java
StringBox box = new StringBox();
```

Here,

```text
T → String
```

The type is permanently fixed.

---

# Generic Subclass

The child class can also remain generic.

```java
class Box<T> {

    T value;
}

class NumberBox<T> extends Box<T> {

}
```

It may even introduce additional type parameters.

```java
class Pair<K, V> {

}

class Employee<K, V> extends Pair<K, V> {

}
```

---

# Why Did Generic Methods Come into the Picture?

A Generic Class makes **the entire class generic**.

Sometimes only one or two methods need generic behavior.

Making the whole class generic becomes unnecessary.

Java solves this using **Generic Methods**.

---

## Example

Instead of writing

```java
void print(String value)

void print(Integer value)

void print(Double value)

void print(Boolean value)
```

we write one generic method.

```java
class Printer {

    public <T> void print(T value) {

        System.out.println(value);
    }
}
```

Usage

```java
Printer printer = new Printer();

printer.print("Java");

printer.print(100);

printer.print(10.5);

printer.print(true);
```

---

# Generic Method Syntax

Type parameter is declared **before the return type**.

```java
public <T> void display(T value) {

}
```

---

# Scope of Type Parameters

Generic Class

```java
class Box<T> {

}
```

`T` is available throughout the class.

---

Generic Method

```java
public <T> void print(T value) {

}
```

`T` exists only inside that method.

---

# Generic Class vs Generic Method

| Generic Class | Generic Method |
|----------------|----------------|
| Entire class becomes generic | Only one method becomes generic |
| Type parameter available throughout the class | Type parameter available only inside the method |
| Used when most methods use the generic type | Used when only one or a few methods need Generics |
| Example: `class Box<T>` | Example: `public <T> void print(T value)` |

---

# Raw Types

## What is a Raw Type?

A **Raw Type** is a generic class used **without specifying a type parameter**.

Example

```java
Box box = new Box();
```

instead of

```java
Box<String> box = new Box<>();
```

---

## Problems with Raw Types

- Loses type safety.
- Produces compiler warnings.
- Can cause `ClassCastException`.

Example

```java
Box box = new Box();

box.setValue(100);

String s = (String) box.getValue();
```

Runtime Error

```
ClassCastException
```

---

# Bounded Generics

Restricts the allowed types.

Syntax

```java
<T extends ClassName>
```

Example

```java
class Calculator<T extends Number> {

    T number;
}
```

Allowed

```java
Calculator<Integer>

Calculator<Double>

Calculator<Float>
```

Not Allowed

```java
Calculator<String>
```

---

# Upper Bound

Upper Bound allows only a class and its subclasses.

Syntax

```java
<T extends Number>
```

Accepted

```text
Number

Integer

Double

Float

Long
```

---

# Multiple Bounds

A type parameter may extend one class and implement multiple interfaces.

Syntax

```java
<T extends ClassA & InterfaceA & InterfaceB>
```

Example

```java
class Demo<T extends Number & Comparable<T>> {

}
```

Rules

- Only one class allowed.
- Class must come first.
- Multiple interfaces allowed.

---

# Wildcards

Wildcards represent **unknown generic types**.

Syntax

```java
?
```

Example

```java
List<?> list;
```

---

# Unbounded Wildcard

Accepts any generic type.

```java
List<?>
```

Example

```java
void print(List<?> list)
```

Accepts

```java
List<String>

List<Integer>

List<Employee>
```

---

# Upper Bounded Wildcard

Accepts a class and its subclasses.

Syntax

```java
<? extends Number>
```

Example

```java
List<? extends Number> list;
```

Accepted

```java
List<Integer>

List<Double>

List<Float>
```

Best for **Reading**.

---

# Lower Bounded Wildcard

Accepts a class and its parent classes.

Syntax

```java
<? super Integer>
```

Accepted

```java
List<Integer>

List<Number>

List<Object>
```

Best for **Writing**.

---

# PECS Principle

One of the most asked interview questions.

```
Producer Extends
Consumer Super
```

Meaning

| Wildcard | Best Used For |
|----------|---------------|
| `extends` | Reading (Producer) |
| `super` | Writing (Consumer) |

---

Reading Example

```java
List<? extends Number> numbers;
```

Safe to read.

Cannot safely add values.

---

Writing Example

```java
List<? super Integer> numbers;
```

Safe to add `Integer`.

Reading returns `Object`.

---

# Type Erasure

Java implements Generics using **Type Erasure**.

During compilation,

```java
Box<String>
```

becomes

```java
Box
```

At runtime, generic type information is removed.

The JVM works with the **raw type**.

Purpose

- Backward compatibility.
- No runtime overhead.

---

# Restrictions of Generics

## Cannot create object of type parameter

```java
new T();      // ❌
```

---

## Cannot create Generic Arrays

```java
T[] arr = new T[10];      // ❌
```

---

## Cannot use Primitive Types

```java
Box<int>      // ❌

Box<Integer>  // ✅
```

---

## Static Members Cannot Use Type Parameter

```java
class Box<T> {

    static T value;     // ❌
}
```

Reason

Static members belong to the class.

Type parameters belong to object instances.

---

# Interview Points

- Introduced in Java 5.
- Provides compile-time type safety.
- Eliminates explicit casting.
- Reduces `ClassCastException`.
- Generic Class is used when the entire class depends on a generic type.
- Generic Method is used when only specific methods require generic behavior.
- Generic Methods can be static.
- Supports inheritance.
- Supports multiple type parameters.
- Supports bounded types.
- Supports wildcards.
- Uses Type Erasure internally.
- Works only with reference types.
- Raw types should be avoided.

---

# Most Asked Interview Questions ⭐

1. What is a Generic Class?
2. Why were Generics introduced?
3. What are Raw Types?
4. Why should Raw Types be avoided?
5. Generic Class vs Generic Method.
6. Generic Class inheritance.
7. Difference between Generic and Non-Generic subclass.
8. What are Bounded Generics?
9. Difference between `<T extends Number>` and `<? extends Number>`?
10. What are Wildcards?
11. Difference between Upper Bound and Lower Bound.
12. What is PECS?
13. What is Type Erasure?
14. Why can't we write `new T()`?
15. Why can't Generic Classes have `static T`?
16. Why don't Generics support primitive types?
17. Can Generic Methods be static?

---

# Quick Revision

- ✅ Generic Class works with multiple data types.
- ✅ Generic Methods make only one method generic.
- ✅ Raw Types remove type safety.
- ✅ `extends` is used for bounded generics.
- ✅ Multiple bounds support one class and multiple interfaces.
- ✅ `<?>` → Unbounded Wildcard.
- ✅ `<? extends T>` → Upper Bounded Wildcard.
- ✅ `<? super T>` → Lower Bounded Wildcard.
- ✅ **PECS** → Producer Extends, Consumer Super.
- ✅ Uses Type Erasure internally.
- ✅ Works only with reference types.
- ✅ Introduced in Java 5.