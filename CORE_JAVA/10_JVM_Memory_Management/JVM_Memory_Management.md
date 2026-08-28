# JVM Memory Management

## What is JVM Memory Management?

The **Java Virtual Machine (JVM)** is responsible for:

- Allocating memory to objects and variables.
- Managing Stack and Heap memory.
- Automatically reclaiming unused memory using the **Garbage Collector (GC)**.

Unlike languages like C/C++, Java developers **do not manually free memory**. The JVM automatically manages memory.

---

# Types of JVM Memory

The JVM mainly manages two runtime memory areas:

1. Stack Memory
2. Heap Memory

```text
                   JVM
                    │
        ┌───────────┴───────────┐
        │                       │
        ▼                       ▼
   Stack Memory            Heap Memory
(Local Variables)        (Objects)
```

---

# Stack Memory

## Definition

Stack Memory stores:

- Local variables
- Method parameters
- Primitive data types
- References (addresses) to Heap objects
- Method execution information (Stack Frames)

Each thread has **its own Stack Memory**.

---

## Characteristics

- Stores primitive variables.
- Stores references to Heap objects.
- Stores method call information.
- Each method gets its own **Stack Frame**.
- Memory is allocated and deallocated automatically.
- Follows **LIFO (Last In, First Out)**.
- Faster than Heap memory.
- Thread-safe because every thread has its own stack.
- Stack overflow results in:

```text
java.lang.StackOverflowError
```

---

## Stack Memory Example

```java
class Student {
    String name = "Priyasha";
}

public class Test {

    public static void main(String[] args) {
        int age = 25;
        Student s = new Student();
    }
}
```

### Memory Diagram

```text
                STACK MEMORY

+--------------------------------+
| main() Frame                   |
|--------------------------------|
| age = 25                       |
| s --------------------------+  |
+-----------------------------|--+
                              |
                              |
                              ▼

                 HEAP MEMORY

        +----------------------+
        | Student Object       |
        |----------------------|
        | name = "Priyasha"    |
        +----------------------+
```

Notice:

- `age` is stored in Stack.
- `s` is stored in Stack (reference).
- Actual Student object is stored in Heap.

---

# Stack Frames

Each method creates a separate Stack Frame.

```java
public class Test {
    static void method1() {
        int x = 10;
        method2();
    }

    static void method2() {
        int y = 20;
    }

    public static void main(String[] args) {
        method1();
    }
}
```

### Execution

```text
Step 1

+----------------+
| main()         |
+----------------+

↓

Step 2

+----------------+
| method1()      |
+----------------+
| main()         |
+----------------+

↓

Step 3

+----------------+
| method2()      |
+----------------+
| method1()      |
+----------------+
| main()         |
+----------------+

↓

method2() completes

+----------------+
| method1()      |
+----------------+
| main()         |
+----------------+
```

The stack always removes the most recently added frame first (**LIFO**).

---

# Heap Memory

## Definition

Heap Memory stores:

- Objects
- Arrays
- Instance variables
- String Pool (String Constant Pool)

Heap memory is **shared among all threads**.

---

## Characteristics

- Stores objects.
- Shared by all threads.
- Objects remain until they become unreachable.
- Garbage Collector manages Heap memory.
- Slower than Stack memory.
- Heap exhaustion results in:

```text
java.lang.OutOfMemoryError
```

---

## Heap Memory Example

```java
class Student {
    String name;

    Student(String name){
        this.name = name;
    }
}

public class Test {
    public static void main(String[] args){
        Student s1 = new Student("Priyasha");
        Student s2 = new Student("Rahul");
    }
}
```

### Memory Diagram

```text
                STACK

+-------------------------------+
| s1 ----------------------+    |
| s2 ------------------+   |    |
+----------------------|---|----+
                       |   |
                       ▼   ▼

                HEAP MEMORY

      +-----------------------+
      | Student              |
      | name = "Priyasha"    |
      +-----------------------+

      +-----------------------+
      | Student              |
      | name = "Rahul"        |
      +-----------------------+
```

---

# Stack vs Heap

| Stack Memory | Heap Memory |
|---------------|-------------|
| Stores local variables | Stores objects |
| Stores primitive values | Stores instance variables |
| Stores references | Stores actual objects |
| One stack per thread | Shared among all threads |
| Uses LIFO | No fixed order |
| Automatically cleared after method execution | Cleared by Garbage Collector |
| Faster | Slower |

---

### Diagram

```text
Young Generation

+-----------+-----------+-----------+
|   Eden    | Survivor0 | Survivor1 |
+-----------+-----------+-----------+

Most objects are created here.
```

### Characteristics

- Stores short-lived objects.
- Minor Garbage Collection occurs here.
- Most objects die in this region.

---

Old (Tenured) Generation

Objects that survive multiple Minor GCs are promoted to the Old Generation.

### Diagram

```text
Young Generation

      │
Objects survive multiple GCs
      │
      ▼

Old Generation

+------------------------------+
| Long-lived Objects           |
+------------------------------+
```

### Characteristics

- Stores long-lived objects.
- Major (or Full) Garbage Collection occurs here.
- Garbage collection is slower than in the Young Generation.

---

# Minor GC vs Major GC

| Minor GC | Major (Full) GC |
|-----------|-----------------|
| Runs in Young Generation | Runs in Old Generation |
| Faster | Slower |
| Frequent | Less Frequent |
| Cleans short-lived objects | Cleans long-lived objects |

---

For details on Garbage Collection (examples, heap organization, algorithms, collectors, references), see [11.Garbage_Collection.md](11.Garbage_Collection.md).

For GC-specific interview questions and details, see [11.Garbage_Collection.md](11.Garbage_Collection.md#interview-questions).

# Quick Revision

- ✅ Heap stores objects.
- ✅ Heap → Young Generation + Old Generation.
- ✅ Young Generation = Eden + Survivor0 + Survivor1.
- ✅ Long-lived objects move to the Old Generation.
- ✅ Metaspace is **Non-Heap Memory** (Java 8+).
- ✅ Minor GC → Young Generation.
- ✅ Major/Full GC → Old Generation.
- ✅ Mark & Sweep = Mark reachable objects → Remove unreachable objects.

# Interview Questions

### Q1. Who creates Stack and Heap memory?

**Answer:** The JVM creates and manages both Stack and Heap memory.

---

### Q2. What is stored in Stack Memory?

- Local variables
- Primitive values
- Method parameters
- References to Heap objects
- Stack Frames

---

### Q3. What is stored in Heap Memory?

- Objects
- Arrays
- Instance variables
- String Pool

---

### Q4. Which memory is shared among threads?

**Heap Memory**

---

### Q5. Which memory is thread-specific?

**Stack Memory**

---

### Q6. What is Garbage Collection?

Garbage Collection is the automatic process of removing unreachable objects from the Heap.

---

### Q7. Can we force Garbage Collection?

**No.**

`System.gc()` only requests the JVM. The JVM decides whether and when to run the Garbage Collector.

---

### Q8. What happens if Stack memory becomes full?

```
java.lang.StackOverflowError
```

---

### Q9. What happens if Heap memory becomes full?

```
java.lang.OutOfMemoryError
```

---

# Quick Revision

- ✅ JVM manages Stack and Heap memory.
- ✅ Stack stores local variables, primitive values, references, and method frames.
- ✅ Heap stores objects, arrays, instance variables, and the String Pool.
- ✅ Stack is thread-specific; Heap is shared.
- ✅ Garbage Collector removes unreachable objects from the Heap.
- ✅ `System.gc()` is only a request; the JVM decides whether to perform garbage collection.
- ✅ Stack overflow → `StackOverflowError`.
- ✅ Heap exhaustion → `OutOfMemoryError`.
- ✅ Modern JVMs use Young Generation, Old Generation, and **Metaspace** (not PermGen).
