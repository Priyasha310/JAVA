## 1. Why can a single Java file have only one public class?

Main method should be inside the public class, and the filename must match the public class name.

In Java, a single file can have only one public class because of the way the Java compiler and runtime environment are designed. The public class is meant to be the main entry point for the program, and it is used to define the structure and behavior of the application. If multiple public classes were allowed in a single file, it would create ambiguity about which class should be used as the entry point for the program.

## 2. Why Shouldn't We Use float/double for Money?

float and double follow the IEEE 754 floating-point standard.
They store numbers in binary (base-2) rather than decimal (base-10).
Many decimal values (like 0.1 or 0.2) cannot be represented exactly in binary, which causes rounding errors.

Example:
System.out.println(0.3f - 0.1f);
Output:
0.20000002 instead of 0.2

Therefore, For financial or currency calculations, use: **BigDecimal** instead of float or double.

# Java Interview Question: String Constant Pool (SCP), Heap, String Immutability, `==` vs `equals()`

## Interview Question

**What is the output of the following program? Explain how many objects are created, where they are stored (Heap/String Constant Pool), and why.**

```java
public class Test {

    public static void main(String[] args) {

        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = new String("Java");

        s1 = s1.concat(" Programming");

        System.out.println(s1 == s2);
        System.out.println(s2 == s3);
        System.out.println(s3 == s4);

        System.out.println(s2.equals(s3));
        System.out.println(s3.equals(s4));
    }
}
```

---

# Step 1: Object Creation

### 1. String Literal

```java
String s1 = "Java";
```

Creates **one object** in the **String Constant Pool (SCP).**

```
SCP
┌──────────────┐
│    "Java"    │
└──────────────┘
```

**Objects Created:** 1

---

### 2. Another String Literal

```java
String s2 = "Java";
```

No new object is created because `"Java"` already exists in the SCP.

```
SCP
┌──────────────┐
│    "Java"    │
└──────────────┘
      ▲      ▲
      │      │
     s1     s2
```

**Objects Created:** Still 1

---

### 3. Using `new String()`

```java
String s3 = new String("Java");
```

Java performs two steps:

1. Looks for `"Java"` in the SCP.
2. Creates a **new String object in the Heap.**

```
SCP
┌──────────────┐
│    "Java"    │
└──────────────┘

Heap
┌──────────────┐
│    "Java"    │
└──────────────┘
      ▲
      │
     s3
```

**Objects Created:** 2

---

### 4. Another `new String()`

```java
String s4 = new String("Java");
```

Creates another Heap object.

```
SCP
┌──────────────┐
│    "Java"    │
└──────────────┘

Heap
┌──────────────┐
│    "Java"    │ ◄── s3
└──────────────┘

┌──────────────┐
│    "Java"    │ ◄── s4
└──────────────┘
```

**Objects Created:** 3

---

### 5. String Immutability

```java
s1 = s1.concat(" Programming");
```

Since Strings are immutable, Java **does not modify** the original `"Java"` object.

Instead, it creates a **new String object**.

```
SCP

┌──────────────┐
│    "Java"    │ ◄── s2
└──────────────┘

┌──────────────────────┐
│ "Java Programming"   │ ◄── s1
└──────────────────────┘

Heap

┌──────────────┐
│    "Java"    │ ◄── s3
└──────────────┘

┌──────────────┐
│    "Java"    │ ◄── s4
└──────────────┘
```

**Total Objects Created:** 4


# Explanation

## 1. `s1 == s2`

```java
System.out.println(s1 == s2);
```

**Output**

```text
false
```

Reason:

```
s1 → "Java Programming"

s2 → "Java"
```

Different objects.

---

## 2. `s2 == s3`

```java
System.out.println(s2 == s3);
```

**Output**

```text
false
```

Reason:

- `s2` points to the SCP object.
- `s3` points to the Heap object.

Different references.

---

## 3. `s3 == s4`

```java
System.out.println(s3 == s4);
```

**Output**

```text
false
```

Reason:

Both are different Heap objects.

---

## 4. `s2.equals(s3)`

```java
System.out.println(s2.equals(s3));
```

**Output**

```text
true
```

Reason:

Both contain the same characters:

```
Java
```

---

## 5. `s3.equals(s4)`

```java
System.out.println(s3.equals(s4));
```

**Output**

```text
true
```

Reason:

Both Strings have identical contents.

---

# Final Memory Diagram

```
                         JAVA HEAP
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│               String Constant Pool (SCP)                    │
│                                                             │
│   ┌──────────────┐                                          │
│   │    "Java"    │ ◄────────────── s2                       │
│   └──────────────┘                                          │
│                                                             │
│   ┌──────────────────────┐                                  │
│   │ "Java Programming"   │ ◄────────────── s1               │
│   └──────────────────────┘                                  │
│                                                             │
│-------------------------------------------------------------│
│                     Normal Heap                             │
│                                                             │
│   ┌──────────────┐                                          │
│   │    "Java"    │ ◄────────────── s3                       │
│   └──────────────┘                                          │
│                                                             │
│   ┌──────────────┐                                          │
│   │    "Java"    │ ◄────────────── s4                       │
│   └──────────────┘                                          │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

# Key Concepts

- **String literals** are stored in the **String Constant Pool (SCP)**.
- Duplicate literals reuse the existing object.
- **`new String()`** always creates a **new object in the Heap**.
- Strings are **immutable**; methods like `concat()` create a new object instead of modifying the existing one.
- `==` compares **references (memory addresses)**.
- `equals()` compares **String contents**.

---

# Interview One-Liner

> **String literals are stored in the String Constant Pool and shared to save memory, whereas `new String()` always creates a new Heap object. Since Strings are immutable, any modification creates a new object. `==` compares references, while `equals()` compares contents.**