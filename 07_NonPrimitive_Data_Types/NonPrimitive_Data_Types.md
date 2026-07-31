# Reference Variables and String in Java

## Types of Reference Variables in Java

Reference variables store the **memory address (reference)** of an object rather than the actual data.

### Common Reference Types

| Reference Type | Description | Example |
|----------------|-------------|---------|
| **Class** | Object of a class | `Student s = new Student();` |
| **String** | Stores sequence of characters | `String name = "Java";` |
| **Interface** | Reference of an interface implementation | `List<String> list = new ArrayList<>();` |
| **Array** | Stores multiple values of the same type | `int[] arr = {1,2,3};` |
| **Enum** | Represents a fixed set of constants | `Day day = Day.MONDAY;` |
| **Annotation** | Metadata for classes/methods | `@Override` |
| **Record** *(Java 16+)* | Immutable data carrier | `record Employee(int id, String name) {}` |

### Memory Representation

```text
Reference Variable
       │
       ▼
+-------------+        +----------------------+
|   student   | -----> | Student Object       |
+-------------+        | id = 101             |
                       | name = "John"        |
                       +----------------------+
```

### Interview One-Liner

> **Reference variables store the address of an object, not the actual object itself.**

---

# Pass by Value vs Pass by Reference

## Does Java Support Pass by Reference?

**No.**

Java is **100% Pass by Value**.

- For **primitive data types**, Java passes a copy of the actual value.
- For **reference types**, Java passes a copy of the reference (memory address), **not the object itself**.

---

## Primitive Type Example

```java
public class Test {

    static void change(int x){
        x = 100;
    }

    public static void main(String[] args){
        int num = 10;
        change(num);
        System.out.println(num);
    }
}
```

### Output

```text
10
```

### Memory Diagram

```text
Before Method Call

num
 │
 ▼
10

↓

change(10)

x
 │
 ▼
10

↓

x = 100

num → 10
x   → 100
```

Only the copy changes.

---

## Reference Type Example

```java
class Student{
    String name;
}

public class Test{

    static void change(Student s){
        s.name = "Rahul";
    }

    public static void main(String[] args){

        Student st = new Student();
        st.name = "Aman";

        change(st);

        System.out.println(st.name);
    }
}
```

### Output

```text
Rahul
```

### Memory Diagram

```text
           Heap

+-----------------------+
| Student Object        |
| name = "Aman"         |
+-----------------------+
        ▲
        │
       st

↓

Method Call

change(st)

        s
        │
        ▼

+-----------------------+
| Student Object        |
| name = "Rahul"        |
+-----------------------+
```

Both `st` and `s` point to the **same object**, so modifying the object's state is visible outside the method.

---

## Important Note

```java
void change(Student s){
    s = new Student();
}
```

This **does not** affect the original object because only the **copy of the reference** is changed.

---

### Interview One-Liner

> **Java is always Pass by Value. For objects, Java passes a copy of the reference, not the object itself.**

---

# String Immutability

## Definition

A **String** is **immutable**, meaning once a String object is created, **its value cannot be changed**.

Any operation that appears to modify a String actually creates a **new String object**.

---

## Example

```java
String s = "Java";
s.concat(" Programming");

System.out.println(s);
```

### Output

```text
Java
```

### Reason

`concat()` returns a new String, but it is not assigned back to `s`.

---

## Correct Way

```java
String s = "Java";
s = s.concat(" Programming");

System.out.println(s);
```

### Output

```text
Java Programming
```

---

## Memory Diagram

### Before `concat()`

```text
SCP

┌──────────────┐
│    "Java"    │
└──────────────┘
      ▲
      │
      s
```

### After `concat()`

```text
SCP

┌──────────────┐
│    "Java"    │
└──────────────┘

┌──────────────────────┐
│ "Java Programming"   │
└──────────────────────┘
          ▲
          │
          s
```

The original `"Java"` object remains unchanged.

---

## Advantages of String Immutability

- Memory optimisation using the String Constant Pool
- Thread safety
- Security (passwords, URLs, database connections)
- Cached hash codes improve performance in collections like `HashMap`

---

### Interview One-Liner

> **Strings are immutable. Any modification creates a new String object instead of changing the existing one.**

---

# String Constant Pool (SCP)

## Definition

The **String Constant Pool (SCP)** is a special memory area inside the **Java Heap** that stores **unique String literals**.

If the same literal already exists, Java reuses the existing object instead of creating a new one.

---

## Case 1: String Literals

```java
String s1 = "stestingo";
String s2 = "stestingo";
```

### Memory Diagram

```text
                    JAVA HEAP
┌───────────────────────────────────────────────┐
│                                               │
│           String Constant Pool (SCP)          │
│                                               │
│      ┌─────────────────────────────┐          │
│      │         "stestingo"         │          │
│      └─────────────────────────────┘          │
│              ▲                 ▲              │
│              │                 │              │
│             s1                s2              │
│                                               │
└───────────────────────────────────────────────┘
```

### Key Points

- Only **one String object** is created.
- Both `s1` and `s2` reference the same object.
- `s1 == s2` → **true**
- `s1.equals(s2)` → **true**

---

## Case 2: Using `new String()`

```java
String s3 = new String("stestingo");
```

### Memory Diagram

```text
                    JAVA HEAP
┌────────────────────────────────────────────────────────┐
│                                                        │
│      String Constant Pool (SCP)                        │
│                                                        │
│      ┌─────────────────────────────┐                   │
│      │         "stestingo"         │                   │
│      └─────────────────────────────┘                   │
│                                                        │
│--------------------------------------------------------│
│                     Normal Heap                        │
│                                                        │
│      ┌─────────────────────────────┐                   │
│      │         "stestingo"         │ ◄──── s3          │
│      └─────────────────────────────┘                   │
│                                                        │
└────────────────────────────────────────────────────────┘
```

### Key Points

- Java first checks the SCP.
- If the literal does not exist, it is added to the SCP.
- `new String()` **always creates a new object in the Heap**.
- Therefore, two objects may exist:
  - One in the SCP
  - One in the Heap

---

## String Literal vs `new String()`

| String Literal | `new String()` |
|---------------|----------------|
| Stored in String Constant Pool | Stored in Heap |
| Reuses existing object | Always creates a new Heap object |
| Memory efficient | Consumes more memory |
| Recommended | Used only when a separate object is explicitly required |

---

# `==` vs `equals()`

| `==` | `equals()` |
|------|------------|
| Compares references (memory addresses) | Compares contents |
| Returns `true` only if both references point to the same object | Returns `true` if both Strings contain the same characters |

### Example

```java
String s1 = "Java";
String s2 = "Java";
String s3 = new String("Java");

System.out.println(s1 == s2);      // true
System.out.println(s1 == s3);      // false

System.out.println(s1.equals(s3)); // true
```

---

# Most Asked Interview Questions

### Q1. Why is String immutable?

**Answer:** To provide security, thread safety, memory optimisation, and efficient hashing.

---

### Q2. What is the String Constant Pool?

**Answer:** A special area inside the Java Heap that stores unique String literals and reuses them to save memory.

---

### Q3. What is the difference between `"Java"` and `new String("Java")`?

- `"Java"` → Stored in the String Constant Pool.
- `new String("Java")` → Creates a new object in the Heap.

---

### Q4. Is Java Pass by Reference?

**Answer:** No. Java is always Pass by Value. For objects, Java passes a copy of the reference.

---

### Q5. Why does `==` return `false` but `equals()` return `true`?

**Answer:** `==` compares object references, whereas `equals()` compares the contents of the String objects.

---

# Quick Revision

- ✅ Reference variables store object addresses.
- ✅ Java is always Pass by Value.
- ✅ Strings are immutable.
- ✅ String literals are stored in the String Constant Pool.
- ✅ `new String()` creates a new Heap object.
- ✅ `==` compares references; `equals()` compares contents.