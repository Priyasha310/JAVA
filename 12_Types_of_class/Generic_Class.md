# Generic Class

## What is a Generic Class?

A **Generic Class** is a class that can work with different data types using **type parameters**. Instead of hardcoding a specific data type, the actual type is specified when creating the object.

Introduced in **Java 5**.

---

## Why Do We Need Generic Classes?

Without Generics:

- No compile-time type safety.
- Explicit type casting is required.
- Higher chance of `ClassCastException`.
- Duplicate code for different data types.

With Generics:

- Compile-time Type Safety
- Code Reusability
- Eliminates Explicit Type Casting
- Better Readability

---

## Syntax

```java
class Box<T> {

}
```

`T` is called a **Type Parameter**.

---

## Example

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

## Multiple Type Parameters

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

## Bounded Generic Class

Restricts the allowed types.

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
Calculator<String>   // Compile-time Error
```

---

# Why Did Generic Methods Come into the Picture?

A Generic Class makes **the entire class generic**.

However, in many scenarios, **only one or two methods need to work with different data types**, while the rest of the class has nothing to do with Generics.

Making the whole class generic in such cases is unnecessary.

To solve this problem, Java provides **Generic Methods**.

---

## Example

Suppose we have a utility class.

```java
class Printer {

    public void print(String value) {

        System.out.println(value);
    }
}
```

Now we also want to print:

- Integer
- Double
- Boolean

One approach is **method overloading**.

```java
void print(String s)

void print(Integer i)

void print(Double d)

void print(Boolean b)
```

This leads to duplicate code.

Instead, we can write a **single Generic Method**.

```java
class Printer {

    public <T> void print(T value) {

        System.out.println(value);
    }
}
```

Usage

```java
Printer p = new Printer();

p.print("Java");

p.print(100);

p.print(10.5);

p.print(true);
```

The compiler automatically infers the type.

---

## Syntax of Generic Method

The type parameter is declared **before the return type**.

```java
public <T> void display(T value) {

}
```

---

## Scope of Type Parameter

For a **Generic Class**

```java
class Box<T> {

}
```

`T` is available throughout the class.

For a **Generic Method**

```java
public <T> void display(T value) {

}
```

`T` is available **only inside that method**.

---

## Generic Class vs Generic Method

| Generic Class | Generic Method |
|----------------|----------------|
| Entire class becomes generic | Only one method becomes generic |
| Type parameter is available throughout the class | Type parameter is available only inside that method |
| Suitable when most methods use the same generic type | Suitable when only one or a few methods need generic behavior |
| Example: `class Box<T>` | Example: `public <T> void print(T value)` |

---

# Type Erasure

Java implements Generics using **Type Erasure**.

During compilation, generic type information is removed.

```java
Box<String>

↓

Box
```

At runtime, the JVM works with the raw type.

---

# Restrictions of Generic Classes

- Cannot create an object of type parameter.

```java
new T();     // ❌
```

- Cannot create generic arrays.

```java
T[] arr = new T[10];     // ❌
```

- Cannot use primitive types.

```java
Box<int>      // ❌

Box<Integer>  // ✅
```

- Static members cannot use the type parameter.

```java
class Box<T>{

    static T value;   // ❌
}
```

Reason: Static members belong to the class, whereas `T` belongs to object instances.

---

# Interview Points

- Introduced in Java 5.
- Provides compile-time type safety.
- Eliminates explicit casting.
- Reduces `ClassCastException`.
- Generic Class is used when **the entire class** depends on a generic type.
- Generic Method is used when **only a particular method** needs generic behavior.
- Generic Methods can also be `static`.
- Uses Type Erasure internally.
- Supports bounded types using `extends`.
- Generics work only with reference types.

---

# Most Asked Interview Questions

1. What is a Generic Class?
2. Why were Generics introduced?
3. Why do we need Generic Methods if Generic Classes already exist?
4. Generic Class vs Generic Method.
5. What is Type Erasure?
6. Why don't Generics support primitive types?
7. What are bounded Generics?
8. Why can't we write `new T()`?
9. Why can't Generic Classes have `static T`?
10. Can Generic Methods be static?

---

# Quick Revision

- ✅ Generic Class → Entire class is generic.
- ✅ Generic Method → Only one method is generic.
- ✅ Generic Method was introduced to avoid making the entire class generic unnecessarily.
- ✅ Type parameter in a Generic Method is declared before the return type.
- ✅ Generic Class → `class Box<T>`
- ✅ Generic Method → `public <T> void print(T value)`
- ✅ Uses Type Erasure internally.
- ✅ Works only with reference types.