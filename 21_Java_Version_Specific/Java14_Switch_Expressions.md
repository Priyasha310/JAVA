# Java 14 — Switch Expression Enhancement

> Interview-focused notes for Java 14.

---

## 1. What Changed in Java 14?

Java 14 made **switch expressions** a standard feature.

Main improvements:

- `switch` can return a value
- Cleaner `->` syntax
- No accidental fall-through with arrow cases
- Multiple values can be handled in one case
- `yield` can return a value from a block inside a switch expression

---

## 2. Traditional `switch`

Before switch expressions:

```java
int day = 2;
String result;

switch (day) {
    case 1:
        result = "Monday";
        break;

    case 2:
        result = "Tuesday";
        break;

    default:
        result = "Invalid";
}
```

### Problems

- More verbose
- Requires `break`
- Easy to accidentally create fall-through
- `switch` was primarily used as a statement

---

## 3. Java 14 — Switch Expression

A `switch` can now directly produce a value:

```java
int day = 2;

String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};

System.out.println(result);
```

Output:

```text
Tuesday
```

Think:

```text
switch
   ↓
match case
   ↓
produce value
```

---

## 4. Arrow `->` Syntax

```java
case 1 -> "Monday";
case 2 -> "Tuesday";
```

With arrow syntax, there is **no fall-through**, so `break` is not required.

```java
int day = 2;

String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    default -> "Invalid";
};
```

Only the matching case executes.

---

## 5. Multiple Values in One Case

```java
int day = 6;

String type = switch (day) {
    case 1, 2, 3, 4, 5 -> "Weekday";
    case 6, 7 -> "Weekend";
    default -> "Invalid";
};

System.out.println(type);
```

Output:

```text
Weekend
```

This is cleaner than repeating cases:

```java
case 1:
case 2:
case 3:
case 4:
case 5:
    ...
```

---

## 6. `yield`

Sometimes a case needs multiple statements.

Use `yield` to provide the value of the switch expression:

```java
int marks = 85;

String result = switch (marks) {
    case 100 -> "Excellent";

    default -> {
        System.out.println("Calculating result...");

        yield marks >= 50
                ? "Pass"
                : "Fail";
    }
};

System.out.println(result);
```

### Remember

```text
yield
→ provides the value of a switch expression
```

---

## 7. `break` vs `yield`

### `break`

Used to exit a traditional switch statement:

```java
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
}
```

### `yield`

Used to provide the result of a switch expression:

```java
String result = switch (day) {
    case 1 -> "Monday";

    default -> {
        yield "Other";
    }
};
```

### Easy memory

```text
break
→ exit switch

yield
→ provide value from switch expression
```

---

## 8. Switch Statement vs Switch Expression

| Switch Statement | Switch Expression |
|---|---|
| Mainly performs actions | Produces a value |
| Traditional syntax commonly uses `break` | Arrow syntax does not need `break` |
| Fall-through is possible with `:` | No fall-through with `->` |
| Does not have to produce a value | Must produce a value |
| Older Java feature | Standardized in Java 14 |

---

## 9. Exhaustiveness

A switch expression must be **exhaustive**.

```java
int day = 3;

String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    case 6 -> "Saturday";
    case 7 -> "Sunday";
    default -> "Invalid";
};
```

Every possible input needs a result.

For values such as `int`, a `default` case is commonly used.

---

## 10. Traditional `:` Syntax Still Works

Java 14 did not remove the traditional syntax.

A switch expression can also use `:`:

```java
String result = switch (day) {
    case 1:
        yield "Monday";

    case 2:
        yield "Tuesday";

    default:
        yield "Invalid";
};
```

For simple cases, arrow syntax is generally cleaner:

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

---

## 11. Practical Example

```java
String role = "ADMIN";

String access = switch (role) {
    case "ADMIN" -> "Full Access";
    case "MANAGER" -> "Manager Access";
    case "USER" -> "Limited Access";
    default -> "No Access";
};

System.out.println(access);
```

Output:

```text
Full Access
```

---

## 12. Why Was Switch Expression Introduced?

Main goals:

- Make `switch` more concise
- Allow `switch` to produce a value
- Reduce accidental fall-through
- Make code easier to read
- Support expression-oriented programming

### Interview answer

> Java 14 standardized switch expressions so that a switch could directly produce a value. The new arrow syntax makes cases concise and avoids fall-through, while `yield` allows a block to provide the value of a switch expression.

---

## 13. Java Version Timeline

For interviews:

```text
Java 12
   ↓
Switch expressions introduced as Preview

Java 13
   ↓
Second Preview
   ↓
yield introduced

Java 14
   ↓
Switch expressions became Standard
```

### Interview question

**When were switch expressions introduced?**

> They were introduced as a preview feature in Java 12 and became a standard feature in Java 14.

---

## 14. Common Interview Questions

### Q1. What changed in switch in Java 14?

> Switch expressions became a standard feature. A switch can now return a value, arrow syntax avoids fall-through, and `yield` can provide a value from a multi-statement switch case.

### Q2. What is a switch expression?

> A switch expression is a switch that produces a value.

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

### Q3. Do we need `break` with `->`?

> No. Arrow-style cases do not fall through, so `break` is not required.

### Q4. What is `yield`?

> `yield` provides the value of a switch expression from a case block containing multiple statements.

### Q5. Difference between `break` and `yield`?

```text
break
→ exits a switch statement/loop

yield
→ produces a value from a switch expression
```

### Q6. Can switch expressions use multiple case labels?

Yes:

```java
case 1, 2, 3 -> "Low";
```

### Q7. Is `default` always required?

Not necessarily. A switch expression must be exhaustive. For some selector types, the compiler can determine that all possibilities are covered; otherwise a `default` is needed.

---

## 15. One-Minute Revision

```text
Java 14
   ↓
Switch expressions became standard
```

```text
Old:
switch statement
→ break
→ fall-through possible

New:
switch expression
→ returns a value
→ -> syntax
→ no fall-through
```

### Basic syntax

```java
String result = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    default -> "Invalid";
};
```

### Multiple values

```java
case 1, 2, 3 -> "Weekday";
```

### Multiple statements

```java
case 1 -> {
    System.out.println("Monday");
    yield "Weekday";
};
```

### Remember

```text
break
→ exit

yield
→ provide value for switch expression
```

---

## 16. Strong Interview Answer

> **Java 14 standardized switch expressions. Unlike the traditional switch statement, a switch expression produces a value. Java 14 also introduced the cleaner arrow syntax, which prevents fall-through, and the `yield` keyword for returning a value from a multi-statement case block.**
