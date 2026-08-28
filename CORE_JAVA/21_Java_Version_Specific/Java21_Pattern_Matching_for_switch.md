# Pattern Matching for `switch`

## 1. What is Pattern Matching for `switch`?

Pattern matching for `switch` allows a `switch` to match values based on their **type**, rather than only matching constants.

Example:

```java
Object obj = "Hello";

switch (obj) {
    case String str -> System.out.println("String: " + str);
    case Integer number -> System.out.println("Integer: " + number);
    default -> System.out.println("Other type");
}
```

`str` and `number` are automatically cast to their respective types.

---

## 2. Basic Syntax

```java
switch (object) {
    case Type variable -> {
        // use variable
    }
    default -> {
        // fallback
    }
}
```

Example:

```java
Object obj = 100;

switch (obj) {
    case String str -> System.out.println(str.length());
    case Integer number -> System.out.println(number + 10);
    default -> System.out.println("Unknown type");
}
```

---

## 3. Switch Expression

Pattern matching works very well with switch expressions.

```java
Object obj = "Java";

String result = switch (obj) {
    case String str -> "String: " + str;
    case Integer number -> "Integer: " + number;
    default -> "Other";
};

System.out.println(result);
```

The switch directly produces a value.

---

## 4. Conditional Pattern Matching with `when`

Modern Java supports guarded patterns using `when`.

```java
Object obj = "Hello";

switch (obj) {
    case String str when str.length() > 5 ->
        System.out.println("Long string");

    case String str ->
        System.out.println("Short string");

    default ->
        System.out.println("Other");
}
```

The `when` condition acts as an additional condition for the pattern.

---

## 5. Pattern Matching with `null`

A switch can explicitly handle `null`.

```java
Object obj = null;

switch (obj) {
    case null -> System.out.println("Null value");
    case String str -> System.out.println("String");
    default -> System.out.println("Other");
}
```

This makes null handling explicit.

---

## 6. Exhaustiveness

A switch expression must be exhaustive.

```java
Object obj = "Hello";

String result = switch (obj) {
    case String str -> "String";
    case Integer number -> "Integer";
    default -> "Other";
};
```

`default` handles all remaining types.

---

## 7. Sealed Classes + Pattern Matching

Pattern matching is especially useful with sealed classes.

```java
sealed interface Shape
        permits Circle, Rectangle {
}

final class Circle implements Shape {
    double radius;
}

final class Rectangle implements Shape {
    double width;
}
```

Now:

```java
Shape shape = new Circle();

String result = switch (shape) {
    case Circle c -> "Circle";
    case Rectangle r -> "Rectangle";
};
```

No `default` is required because all permitted subclasses are covered.

This is called **exhaustive pattern matching**.

---

## 8. Case Order Matters

More specific patterns should come before broader patterns.

### Correct

```java
switch (obj) {
    case Integer number -> System.out.println("Integer");
    case Number number -> System.out.println("Number");
    default -> System.out.println("Other");
}
```

### Incorrect

```java
switch (obj) {
    case Number number -> System.out.println("Number");
    case Integer number -> System.out.println("Integer");
}
```

The `Integer` case is unreachable because every `Integer` is already a `Number`.

---

## 9. `instanceof` vs `switch` Pattern Matching

| Feature | `instanceof` Pattern Matching | `switch` Pattern Matching |
|---|---|---|
| Type checking | Yes | Yes |
| Automatic casting | Yes | Yes |
| Multiple types | `if/else` chain | Multiple `case`s |
| Expression result | No | Yes |
| Exhaustiveness | No | Yes |
| Sealed class support | Limited | Excellent |

---
When we're using swtich with primitive datatypes, wrapper types, enum, string it is more effective than if-else
but when using wtih object it is same as if else.
---

### When to use which?

Use `instanceof` when there are only a few conditions:

```java
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

Use `switch` when there are multiple possible types:

```java
switch (obj) {
    case String str -> System.out.println(str.length());
    case Integer number -> System.out.println(number + 10);
    default -> System.out.println("Other");
}
```

---

## 10. Java Version

Pattern matching for `switch` became a **final feature in Java 21**.

> **Java 21 → Pattern Matching for `switch` became standard.**

---

## 11. Quick Interview Answer

Pattern matching for `switch` allows a switch to match an object's type and automatically bind it to a variable.

```java
Object obj = "Java";

switch (obj) {
    case String str -> System.out.println(str.length());
    case Integer number -> System.out.println(number + 10);
    default -> System.out.println("Other");
}
```

It improves readability and is especially useful with **switch expressions, sealed classes, and exhaustive type matching**.
