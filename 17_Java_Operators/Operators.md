# Java Operators — Interview Notes

## 1. Operator, Operand and Expression

### Operator

An **operator** is a symbol that performs an operation on one or more operands.

```java
int result = 10 + 20;
```

Here:

- `+` → operator
- `10`, `20` → operands
- `10 + 20` → expression

### Operand

An **operand** is the value or variable on which an operator operates.

```java
a + b
```

`a` and `b` are operands.

### Expression

An **expression** is a combination of variables, values, operators, and method calls that evaluates to a value.

```java
int result = a + b * 10;
```

---

# 2. Types of Operators in Java

Java operators can be broadly classified as:

```text
Java Operators
│
├── Arithmetic
├── Relational
├── Logical
├── Unary
├── Assignment
├── Bitwise
├── Shift
├── Ternary
└── instanceof
```

---

# 3. Arithmetic Operators

Used for mathematical calculations.

| Operator | Meaning | Example |
|---|---|---|
| `+` | Addition | `a + b` |
| `-` | Subtraction | `a - b` |
| `*` | Multiplication | `a * b` |
| `/` | Division | `a / b` |
| `%` | Modulus / remainder | `a % b` |

### Example

```java
int a = 10;
int b = 3;

System.out.println(a + b); // 13
System.out.println(a - b); // 7
System.out.println(a * b); // 30
System.out.println(a / b); // 3
System.out.println(a % b); // 1
```

### Interview Point: Integer Division

```java
System.out.println(10 / 3);
```

Output:

```text
3
```

Because both operands are integers, Java performs integer division and discards the fractional part.

```java
System.out.println(10.0 / 3);
```

Output is approximately:

```text
3.3333333333333335
```

---

## `+` with Strings

The `+` operator is also used for **String concatenation**.

```java
String name = "Java";

System.out.println("Hello " + name);
```

Output:

```text
Hello Java
```

### Important Interview Trap

```java
System.out.println(10 + 20 + "Java");
```

Output:

```text
30Java
```

Evaluation occurs left to right.

```text
10 + 20 → 30
30 + "Java" → "30Java"
```

But:

```java
System.out.println("Java" + 10 + 20);
```

Output:

```text
Java1020
```

Once String concatenation begins, subsequent `+` operations are performed as concatenation.

---

# 4. Relational Operators

Relational operators compare two values and return a `boolean`.

| Operator | Meaning |
|---|---|
| `==` | Equal to |
| `!=` | Not equal to |
| `>` | Greater than |
| `<` | Less than |
| `>=` | Greater than or equal to |
| `<=` | Less than or equal to |

### Example

```java
int a = 10;
int b = 20;

System.out.println(a == b); // false
System.out.println(a != b); // true
System.out.println(a < b);  // true
System.out.println(a > b);  // false
```

### Important: `==` with Objects

For objects, `==` compares **references**, not object contents.

```java
String s1 = new String("Java");
String s2 = new String("Java");

System.out.println(s1 == s2);       // false
System.out.println(s1.equals(s2));  // true
```

- `==` → reference identity for objects
- `.equals()` → logical/content equality when the class overrides it appropriately

---

# 5. Logical Operators

Logical operators are mainly used with boolean expressions.

| Operator | Meaning |
|---|---|
| `&&` | Logical AND |
| `||` | Logical OR |
| `!` | Logical NOT |

### AND `&&`

Returns `true` only when both conditions are true.

```java
int age = 25;

System.out.println(age >= 18 && age <= 60);
```

```text
true && true → true
```

### OR `||`

Returns `true` when at least one condition is true.

```java
int age = 15;

System.out.println(age < 18 || age > 60);
```

```text
true || false → true
```

### NOT `!`

Reverses a boolean value.

```java
boolean active = true;

System.out.println(!active);
```

Output:

```text
false
```

---

## Short-Circuit Evaluation

This is an important interview topic.

### `&&`

If the left side is `false`, Java does not evaluate the right side.

```java
false && expression
```

Result:

```text
false
```

### `||`

If the left side is `true`, Java does not evaluate the right side.

```java
true || expression
```

Result:

```text
true
```

### Example

```java
int a = 0;

if (a != 0 && 10 / a > 1) {
    // ...
}
```

`10 / a` is never evaluated because:

```java
a != 0
```

is `false`.

Therefore, no `ArithmeticException` occurs.

---

# 6. Unary Operators

Unary operators work on **one operand**.

| Operator | Meaning |
|---|---|
| `+` | Unary plus |
| `-` | Unary minus |
| `++` | Increment |
| `--` | Decrement |
| `!` | Logical NOT |
| `~` | Bitwise complement |

### Example

```java
int a = 10;

System.out.println(-a); // -10
System.out.println(+a); // 10
```

---

## Pre-Increment vs Post-Increment

### Pre-Increment

```java
int a = 10;
int b = ++a;
```

Steps:

```text
a becomes 11
b gets 11
```

Result:

```text
a = 11
b = 11
```

### Post-Increment

```java
int a = 10;
int b = a++;
```

Steps:

```text
b gets 10
a becomes 11
```

Result:

```text
a = 11
b = 10
```

### Same concept applies to `--`

```java
--a  // decrement first, then use
a--  // use first, then decrement
```

---

# 7. Assignment Operators

Assignment operators assign values to variables.

### Basic Assignment

```java
int a = 10;
```

`=` assigns `10` to `a`.

### Compound Assignment Operators

| Operator | Equivalent |
|---|---|
| `+=` | `a = a + b` |
| `-=` | `a = a - b` |
| `*=` | `a = a * b` |
| `/=` | `a = a / b` |
| `%=` | `a = a % b` |
| `&=` | `a = a & b` |
| `|=` | `a = a | b` |
| `^=` | `a = a ^ b` |
| `<<=` | `a = a << b` |
| `>>=` | `a = a >> b` |
| `>>>=` | `a = a >>> b` |

### Example

```java
int a = 10;

a += 5;

System.out.println(a);
```

Output:

```text
15
```

---

## Interview Trap: Compound Assignment and Casting

```java
byte b = 10;

b += 5;
```

This is valid.

Conceptually:

```java
b = (byte) (b + 5);
```

But:

```java
byte b = 10;

b = b + 5; // Compile-time error
```

Because arithmetic on `byte` is promoted to `int`.

---

# 8. Bitwise Operators

Bitwise operators operate at the **bit level** of integral types.

| Operator | Meaning |
|---|---|
| `&` | Bitwise AND |
| `|` | Bitwise OR |
| `^` | Bitwise XOR |
| `~` | Bitwise complement |

### Example

```java
int a = 5; // 0101
int b = 3; // 0011

System.out.println(a & b);
```

Bitwise operation:

```text
  0101
& 0011
------
  0001
```

Result:

```text
1
```

---

## Bitwise AND `&`

A bit becomes `1` only if both bits are `1`.

```text
1 & 1 → 1
1 & 0 → 0
0 & 1 → 0
0 & 0 → 0
```

---

## Bitwise OR `|`

A bit becomes `1` if at least one bit is `1`.

```text
1 | 1 → 1
1 | 0 → 1
0 | 1 → 1
0 | 0 → 0
```

---

## Bitwise XOR `^`

A bit becomes `1` when the two bits are different.

```text
1 ^ 1 → 0
1 ^ 0 → 1
0 ^ 1 → 1
0 ^ 0 → 0
```

---

## Bitwise Complement `~`

Flips every bit:

```text
0 → 1
1 → 0
```

For signed integer types, the result follows Java's two's-complement representation.

Example:

```java
int a = 5;

System.out.println(~a);
```

Output:

```text
-6
```
Shortcut Formula ⭐

For any integer n:

~n = -(n + 1)
Examples
~5
= -(5 + 1)
= -6
---

# 9. Shift Operators

Shift operators move the bits of an integer value.

| Operator | Meaning |
|---|---|
| `<<` | Left shift |
| `>>` | Signed right shift |
| `>>>` | Unsigned right shift |

---

## Left Shift `<<`

Moves bits to the left and fills the right side with zeros.

```java
int a = 5;

System.out.println(a << 1);
```

Binary concept:

```text
0101 << 1
1010
```

Result:

```text
10
```

For values where overflow does not occur, left shifting by `n` is equivalent to multiplying by `2^n`.

---

## Signed Right Shift `>>`

Shifts bits to the right while preserving the sign bit.

```java
int a = 8;

System.out.println(a >> 1);
```

Result:

```text
4
```

For positive values without overflow concerns:

```text
a >> n ≈ a / 2^n
```

---

## Unsigned Right Shift `>>>`

Shifts bits to the right and fills the left side with zeros.

This is especially important for negative values.

```java
int a = -8;

System.out.println(a >>> 1);
```

The result is a large positive integer because zeros are inserted from the left.

### Interview Difference

```text
>>   → preserves sign
>>>  → fills with zero
```

---

# 10. Ternary Operator

The ternary operator is a compact alternative to a simple `if-else`.

Syntax:

```java
condition ? valueIfTrue : valueIfFalse
```

Example:

```java
int age = 20;

String result =
        age >= 18 ? "Adult" : "Minor";
```

Equivalent `if-else`:

```java
String result;

if (age >= 18) {
    result = "Adult";
} else {
    result = "Minor";
}
```

### Interview Point

The ternary operator is an **expression**, so it produces a value.

It should be used when the logic is simple and readable.

Avoid deeply nested ternary expressions because they reduce readability.

---

# 11. `instanceof` Operator

`instanceof` checks whether an object is an instance of a particular class, subclass, or interface.

Syntax:

```java
object instanceof Type
```

Example:

```java
String name = "Java";

System.out.println(name instanceof String);
```

Output:

```text
true
```

### With Inheritance

```java
class Animal {
}

class Dog extends Animal {
}
```

```java
Dog dog = new Dog();

System.out.println(dog instanceof Dog);    // true
System.out.println(dog instanceof Animal); // true
System.out.println(dog instanceof Object); // true
```

Because:

```text
Dog
 ↓
Animal
 ↓
Object
```

---
```
                    ParentClass
                    /         \
                   /           \
          ChildClass1       ChildClass2


             RandomClass
                 ↑
          unrelated class
```

```
class ParentClass {
}

class ChildClass1 extends ParentClass {
}

class ChildClass2 extends ParentClass {
}

class RandomClass {
}

public class Main {

    public static void main(String[] args) {

        // Parent reference pointing to ChildClass2 object
        ParentClass obj = new ChildClass2();

        System.out.println(obj instanceof ChildClass2);
        System.out.println(obj instanceof ChildClass1);

        // ChildClass1 object
        ChildClass1 childObj = new ChildClass1();

        System.out.println(childObj instanceof ParentClass);

        // String object
        String val = "hello";

        System.out.println(val instanceof String);

        // Parent/Object reference pointing to unrelated object
        Object unknownObject = new RandomClass();

        System.out.println(unknownObject instanceof ChildClass2);
    }
}
```
Output:
```
true
false
true
true
false
```

## `instanceof` with `null`

```java
String s = null;

System.out.println(s instanceof String);
```

Output:

```text
false
```

`null` is not an instance of any type.

---

## Pattern Matching with `instanceof`

Modern Java supports pattern matching:

```java
if (obj instanceof String s) {
    System.out.println(s.length());
}
```

The variable `s` is automatically available after a successful type match in the appropriate scope.

---

# 12. Operator Precedence

Operator precedence determines which operator is evaluated first when multiple operators appear in an expression.

Example:

```java
int result = 10 + 20 * 2;
```

Multiplication has higher precedence than addition.

Therefore:

```text
20 * 2 = 40
10 + 40 = 50
```

Result:

```text
50
```

---

## Important Precedence Order

From higher to lower precedence:

```
| Precedence | Operator Category      | Operators                                                                  | Associativity |
| ---------: | ---------------------- | -------------------------------------------------------------------------- | ------------- |
|          1 | Parentheses / Brackets | `()`, `[]`                                                                 | Left → Right  |
|          2 | Unary: Postfix         | `expr++`, `expr--`                                                         | Left → Right  |
|          3 | Unary: Prefix          | `++expr`, `--expr`, `+expr`, `-expr`, `~`, `!`                             | Right → Left  |
|          4 | Multiplicative         | `*`, `/`, `%`                                                              | Left → Right  |
|          5 | Additive               | `+`, `-`                                                                   | Left → Right  |
|          6 | Bitwise Shift          | `<<`, `>>`, `>>>`                                                          | Left → Right  |
|          7 | Relational             | `<`, `>`, `<=`, `>=`, `instanceof`                                         | Left → Right  |
|          8 | Equality               | `==`, `!=`                                                                 | Left → Right  |
|          9 | Bitwise AND            | `&`                                                                        | Left → Right  |
|         10 | Bitwise XOR            | `^`                                                                        | Left → Right  |
|         11 | Bitwise OR             | `\|`                                                                       | Left → Right  |
|         12 | Logical AND            | `&&`                                                                       | Left → Right  |
|         13 | Logical OR             | `\|\|`                                                                     | Left → Right  |
|         14 | Ternary                | `?:`                                                                       | Right → Left  |
|         15 | Assignment             | `=`, `+=`, `-=`, `*=`, `/=`, `%=`, `&=`, `^=`, `\|=`, `<<=`, `>>=`, `>>>=` | Right → Left  |

### Best Practice

Use parentheses when the intended order is important or unclear.

```java
int result = (10 + 20) * 2;
```

This is clearer than relying only on precedence rules.

---

# 13. `&&` vs `&`

This is a common interview question.

### `&&`

Logical AND with **short-circuit evaluation**.

```java
if (a != 0 && 10 / a > 1) {
}
```

If the first condition is false, the second condition is not evaluated.

### `&`

Can perform bitwise AND on integral values and also logical AND on boolean operands, but **both operands are evaluated** when used with booleans.

Example:

```java
boolean result =
        false & someMethod();
```

`someMethod()` is still evaluated.

### Interview Summary

```text
&& → logical AND + short-circuit
&  → bitwise AND / boolean AND without short-circuit
```

---

# 14. `||` vs `|`

### `||`

Logical OR with short-circuit evaluation.

If the left side is `true`, the right side is not evaluated.

```java
true || someMethod();
```

### `|`

Bitwise OR for integral types and non-short-circuit boolean OR.

```java
true | someMethod();
```

The right side is evaluated.

### Interview Summary

```text
|| → logical OR + short-circuit
|  → bitwise OR / boolean OR without short-circuit
```

---

# 15. Important Operator Interview Traps

## `==` vs `.equals()`

```java
String s1 = new String("Java");
String s2 = new String("Java");

s1 == s2       // false
s1.equals(s2)  // true
```

`==` checks reference identity for objects.

`.equals()` checks logical equality when overridden appropriately.

---

## Integer Division

```java
System.out.println(5 / 2);
```

Output:

```text
2
```

But:

```java
System.out.println(5.0 / 2);
```

Output:

```text
2.5
```

---

## Increment Operators

```java
int a = 5;

System.out.println(a++); // 5
System.out.println(a);   // 6
```

```java
int b = 5;

System.out.println(++b); // 6
System.out.println(b);   // 6
```

---

## String Concatenation

```java
System.out.println(1 + 2 + "3");
```

Output:

```text
33
```

```java
System.out.println("1" + 2 + 3);
```

Output:

```text
123
```

---

# 16. Operator Classification by Number of Operands

Operators can also be classified based on how many operands they require.

### Unary Operator

One operand:

```java
++a
!flag
~a
```

### Binary Operator

Two operands:

```java
a + b
a > b
a && b
```

### Ternary Operator

Three operands:

```java
condition ? a : b
```

---

# 17. Quick Revision Table

| Category | Operators |
|---|---|
| Arithmetic | `+ - * / %` |
| Relational | `< > <= >=` |
| Equality | `== !=` |
| Logical | `&& || !` |
| Unary | `+ - ++ -- ! ~` |
| Assignment | `= += -= *= /= %= &= |= ^= <<= >>= >>>=` |
| Bitwise | `& \| ^ ~` |
| Shift | `<< >> >>>` |
| Ternary | `?:` |
| Type checking | `instanceof` |

---

# 18. Most Important Interview Topics

For a 2–3 YOE Java interview, focus especially on:

1. `==` vs `.equals()`
2. `&&` vs `&`
3. `||` vs `|`
4. Short-circuit evaluation
5. Pre-increment vs post-increment
6. Integer division
7. String concatenation using `+`
8. Compound assignment and implicit casting
9. Bitwise operators
10. `>>` vs `>>>`
11. `instanceof`
12. Operator precedence
13. Unary, binary, and ternary operators
14. `null instanceof Type`
15. `&`, `|`, and `^` at the bit level

---

# 19. One-Minute Interview Summary

> Java operators are symbols used to perform operations on operands. They include arithmetic, relational, logical, unary, assignment, bitwise, shift, ternary, and `instanceof` operators. An important interview distinction is between logical operators such as `&&` and `||`, which support short-circuit evaluation, and `&` and `|`, which do not short-circuit. For objects, `==` compares references while `.equals()` is used for logical equality. Operator precedence determines evaluation order, and parentheses should be used when clarity is important.
