# Pattern Matching for `instanceof`

## 1. What is Pattern Matching for `instanceof`?

Pattern matching for `instanceof` lets Java **check a type and declare/cast the variable in one step**.

### Traditional approach

```java
Object obj = "Hello";

if (obj instanceof String) {
    String str = (String) obj;
    System.out.println(str.length());
}
```

### Pattern matching approach

```java
Object obj = "Hello";

if (obj instanceof String str) {
    System.out.println(str.length());
}
```

`str` is automatically treated as a `String`.

---

## 2. Syntax

```java
if (object instanceof Type variable) {
    // use variable
}
```

Example:

```java
Object obj = "Java";

if (obj instanceof String str) {
    System.out.println(str.toUpperCase());
}
```

- `obj` → object being checked
- `String` → type pattern
- `str` → pattern variable

---

## 3. Why use it?

### Without pattern matching

```java
if (obj instanceof Integer) {
    Integer number = (Integer) obj;
    System.out.println(number + 10);
}
```

### With pattern matching

```java
if (obj instanceof Integer number) {
    System.out.println(number + 10);
}
```

### Benefits

- Less boilerplate
- No explicit cast
- Better readability
- Reduces casting mistakes

---

## 4. Pattern Variable Scope

The pattern variable is available where Java can guarantee that the pattern matched.

```java
Object obj = "Hello";

if (obj instanceof String str) {
    System.out.println(str.length());
}
```

`str` is available inside the `if` block.

---

## 5. Using `&&`

Pattern matching works naturally with `&&`.

```java
Object obj = "Hello";

if (obj instanceof String str && str.length() > 3) {
    System.out.println(str);
}
```

The second condition can use `str` because the first condition must be true.

---

## 6. Using `||`

Be careful with `||`.

```java
if (obj instanceof String str || str.length() > 3) {
    // Compile-time error
}
```

The right side may execute when `obj` is not a `String`, so `str` is not guaranteed to exist.

---

## 7. Negation with `!`

Pattern variables can be used with negation:

```java
Object obj = "Hello";

if (!(obj instanceof String str)) {
    return;
}

System.out.println(str.length());
```

After the `if`, Java knows that `str` must be a `String`.

---

## 8. Multiple Type Checks

```java
Object obj = 100;

if (obj instanceof String str) {
    System.out.println("String: " + str);
} else if (obj instanceof Integer number) {
    System.out.println("Integer: " + number);
}
```

---

## 9. Example with Inheritance

```java
class Animal {
}

class Dog extends Animal {
    void bark() {
        System.out.println("Bark");
    }
}

Animal animal = new Dog();

if (animal instanceof Dog dog) {
    dog.bark();
}
```

No explicit cast is required.

---

## 10. Interview Point

Pattern matching for `instanceof` combines:

```text
Type checking
     +
Type casting
     +
Variable declaration
```

Traditional:

```java
if (obj instanceof String) {
    String str = (String) obj;
}
```

Pattern matching:

```java
if (obj instanceof String str) {
}
```

---

## 11. Java Version

Pattern matching for `instanceof` became a **final feature in Java 16**.

> **Java 16 → Pattern Matching for `instanceof` became standard.**

---

## 12. Quick Interview Answer

Pattern matching for `instanceof` allows us to check an object's type and automatically cast it to that type in a single expression.

```java
if (obj instanceof String str) {
    System.out.println(str.length());
}
```

It removes the need for an explicit cast and improves readability.
