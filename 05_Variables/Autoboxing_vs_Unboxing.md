# Auto-boxing vs Unboxing in Java (Interview Notes)

## What are Wrapper Classes?

Java provides **Wrapper Classes** for all primitive data types. A wrapper class wraps a primitive value inside an object.

| Primitive Type | Wrapper Class |
|---------------|---------------|
| `byte` | `Byte` |
| `short` | `Short` |
| `int` | `Integer` |
| `long` | `Long` |
| `float` | `Float` |
| `double` | `Double` |
| `char` | `Character` |
| `boolean` | `Boolean` |

Wrapper classes are mainly used because Java Collections and Generics work only with objects.

---

# Auto-boxing

## Definition

**Auto-boxing** is the **automatic conversion of a primitive data type into its corresponding wrapper class object** by the Java compiler.

Introduced in **Java 5**.

### Syntax

```java
primitive → Wrapper Object
```

### Example

```java
int num = 100;

Integer obj = num;    // Auto-boxing
```

Internally, the compiler converts it into:

```java
Integer obj = Integer.valueOf(num);
```

---

## Memory Representation

```text
Primitive Variable

num
 │
 ▼
100

↓

Auto-boxing

Heap

+----------------+
| Integer Object |
| value = 100    |
+----------------+
       ▲
       │
      obj
```

---

## Real-world Example

### Collections

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
```

Internally,

```java
list.add(Integer.valueOf(10));

list.add(Integer.valueOf(20));
```

Since `ArrayList` stores only objects, Java automatically converts primitive values into `Integer` objects.

---

# Unboxing

## Definition

**Unboxing** is the **automatic conversion of a wrapper class object into its corresponding primitive data type**.

Introduced in **Java 5**.

### Syntax

```java
Wrapper Object → Primitive
```

### Example

```java
Integer obj = 100;

int num = obj;      // Unboxing
```

Internally,

```java
int num = obj.intValue();
```

---

## Memory Representation

```text
Heap

+----------------+
| Integer Object |
| value = 100    |
+----------------+
       ▲
       │
      obj

↓

Unboxing

num
 │
 ▼
100
```

---

# Auto-boxing and Unboxing Together

```java
public class Test {

    public static void main(String[] args) {

        int i = 10;

        Integer j = i;      // Auto-boxing

        int k = j;          // Unboxing

        System.out.println(i);
        System.out.println(j);
        System.out.println(k);
    }
}
```

### Output

```text
10
10
10
```

---

# Internal Working

### Auto-boxing

```java
Integer number = 10;
```

Compiler converts it to:

```java
Integer number = Integer.valueOf(10);
```

---

### Unboxing

```java
int num = number;
```

Compiler converts it to:

```java
int num = number.intValue();
```

---

# Why Do We Need Auto-boxing?

Without auto-boxing:

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(Integer.valueOf(10));
```

With auto-boxing:

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);
```

The compiler automatically converts `10` into an `Integer` object.

---

# Difference Between Auto-boxing and Unboxing

| Feature | Auto-boxing | Unboxing |
|----------|-------------|----------|
| Definition | Primitive → Wrapper Object | Wrapper Object → Primitive |
| Conversion | Automatic | Automatic |
| Introduced | Java 5 | Java 5 |
| Internal Method | `valueOf()` | `xxxValue()` |
| Example | `Integer i = 10;` | `int n = i;` |
| Purpose | Convert primitive into object | Convert object into primitive |

---

# Real Interview Example

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(100);          // Auto-boxing

int num = list.get(0);  // Unboxing
```

Internally,

```java
list.add(Integer.valueOf(100));

int num = list.get(0).intValue();
```

---

# Common Interview Questions

## Q1. What is Auto-boxing?

### Answer

Auto-boxing is the automatic conversion of a primitive data type into its corresponding wrapper class object by the Java compiler.

For example:

```java
int i = 10;

Integer obj = i;
```

Internally, Java converts it into:

```java
Integer obj = Integer.valueOf(i);
```

---

## Q2. What is Unboxing?

### Answer

Unboxing is the automatic conversion of a wrapper class object into its corresponding primitive data type.

Example:

```java
Integer obj = 10;

int num = obj;
```

Internally,

```java
int num = obj.intValue();
```

---

## Q3. Why were Auto-boxing and Unboxing introduced?

### Answer

Before Java 5, developers had to manually convert between primitive types and wrapper classes using methods like `Integer.valueOf()` and `intValue()`.

Auto-boxing and unboxing were introduced to:

- Reduce boilerplate code.
- Improve code readability.
- Simplify working with Collections and Generics.

---

## Q4. Where are Auto-boxing and Unboxing commonly used?

### Answer

They are commonly used in:

- Collections (`ArrayList`, `HashMap`, `HashSet`)
- Generics
- Method parameters
- Method return values

Example:

```java
ArrayList<Integer> list = new ArrayList<>();

list.add(10);      // Auto-boxing

int x = list.get(0);   // Unboxing
```

---

## Q5. What is the biggest disadvantage of Auto-boxing?

### Answer

Auto-boxing creates **wrapper objects**, which consume more memory than primitive types.

Frequent boxing and unboxing can:

- Increase object creation.
- Put additional pressure on the Garbage Collector.
- Slightly reduce performance in performance-critical applications.

For high-performance code, prefer primitives whenever object semantics are not required.

---

## Q6. Can Unboxing throw an exception?

### Answer

Yes.

If a wrapper object is `null`, unboxing throws a **NullPointerException**.

Example:

```java
Integer num = null;

int x = num;      // NullPointerException
```

Reason:

Internally, Java executes:

```java
num.intValue();
```

Calling `intValue()` on `null` causes a `NullPointerException`.

---

## Q7. Which is faster: Primitive or Wrapper?

### Answer

Primitive data types are faster because:

- They store the actual value.
- No object creation is required.
- No Garbage Collection overhead.
- Less memory consumption.

Wrapper classes involve object creation and may require boxing/unboxing.

---

## Q8. Can an `ArrayList<int>` be created?

### Answer

No.

Generics work only with objects.

Correct way:

```java
ArrayList<Integer> list = new ArrayList<>();
```

---

## Q9. What methods are internally used for Auto-boxing and Unboxing?

### Answer

Auto-boxing:

```java
Integer.valueOf(int)
```

Unboxing:

```java
intValue()
longValue()
doubleValue()
floatValue()
booleanValue()
charValue()
```

---

# Frequently Asked Tricky Questions

### Q1. What is the output?

```java
Integer a = 100;
Integer b = 100;

System.out.println(a == b);
```

**Output**

```text
true
```

**Reason**

Java caches `Integer` objects in the range **-128 to 127** using the Integer Cache.

Both `a` and `b` refer to the same cached object.

---

### Q2. What is the output?

```java
Integer a = 200;
Integer b = 200;

System.out.println(a == b);
```

**Output**

```text
false
```

**Reason**

Values outside the Integer Cache range create separate `Integer` objects.

---

### Q3. What is the output?

```java
Integer a = 100;
Integer b = 100;

System.out.println(a.equals(b));
```

**Output**

```text
true
```

**Reason**

`equals()` compares object values, not references.

---

# Interview Answer (2–3 YOE)

> **Auto-boxing is the automatic conversion of a primitive type into its corresponding wrapper object, while unboxing is the reverse process. These features were introduced in Java 5 to simplify working with Collections and Generics. Internally, auto-boxing uses methods like `Integer.valueOf()`, whereas unboxing uses methods like `intValue()`. Although convenient, excessive boxing and unboxing can create unnecessary objects and impact performance, so primitives are preferred in performance-critical code whenever possible.**

---

# Quick Revision

- ✅ Auto-boxing → Primitive → Wrapper Object
- ✅ Unboxing → Wrapper Object → Primitive
- ✅ Introduced in Java 5
- ✅ Auto-boxing uses `valueOf()`
- ✅ Unboxing uses `xxxValue()`
- ✅ Required for Collections and Generics
- ✅ Wrapper objects consume more memory than primitives
- ✅ Unboxing a `null` wrapper throws `NullPointerException`
- ✅ Integer Cache stores values from **-128 to 127**