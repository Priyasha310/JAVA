# Garbage Collection (GC)

## What is Garbage Collection?

Garbage Collection is the process of **automatically removing objects that are no longer reachable** from the Heap.

It helps prevent memory leaks.

---

## Example

```java
public class Test {

    public static void main(String[] args){

        Student s = new Student();

        s = null;
    }
}
```

Memory

```text
Initially

STACK

s -----------+

              |
              ▼

HEAP

+------------------+
| Student Object   |
+------------------+

↓

s = null

STACK

s -----> null

HEAP

+------------------+
| Student Object   |   ← Unreachable
+------------------+

↓

Garbage Collector

↓

Object Removed
```

---

# Another Example

```java
Student s1 = new Student();

Student s2 = s1;

s1 = null;
```

Memory

```text
STACK

s1 ---> null

s2 ---------+

             |
             ▼

HEAP

+----------------+
| Student Object |
+----------------+
```

The object **will NOT** be garbage collected because `s2` still references it.

---

# Eligible for Garbage Collection

```java
Student s = new Student();

s = null;
```

Eligible for GC.

---

```java
new Student();
```

Eligible immediately because no reference exists.

---

```java
Student s1 = new Student();

Student s2 = s1;

s1 = null;
```

Not eligible because `s2` still references the object.

---

# Can We Force Garbage Collection?

```java
System.gc();
```

or

```java
Runtime.getRuntime().gc();
```

## Important Interview Point

> **No.**

These methods **only request** the JVM to run the Garbage Collector.

The JVM **may or may not** execute it.

The JVM has complete control over when garbage collection actually occurs.

---

# How Does Garbage Collection Work?

Simplified steps:

```text
Create Objects

        │
        ▼

Object Becomes Unreachable

        │
        ▼

Garbage Collector Detects It

        │
        ▼

Memory is Reclaimed
```

---

# Generational Heap (Modern JVM)

Modern JVMs divide the Heap into generations because most objects have short lifetimes.

```text
                Heap Memory

        +-----------------------+
        | Young Generation      |
        |-----------------------|
        | Eden                  |
        | Survivor 0            |
        | Survivor 1            |
        +-----------------------+

        +-----------------------+
        | Old (Tenured) Gen     |
        +-----------------------+

        +-----------------------+
        | Metaspace*            |
        +-----------------------+
```

> **Note:** Since **Java 8**, **PermGen** has been replaced by **Metaspace**, which stores class metadata outside the Heap.

---

# Heap Memory Organization (Modern JVM)

Heap Memory is the runtime memory area where **objects and arrays** are stored.

To improve Garbage Collection performance, the Heap is divided into multiple regions.

> **Note:** In modern JVMs (Java 8+), **Metaspace is NOT part of the Heap**. It is a **Non-Heap Memory** area that stores class metadata.

---

# JVM Memory Structure

```text
                         JVM Memory
                              │
         ┌────────────────────┴────────────────────┐
         │                                         │
         ▼                                         ▼
     Heap Memory                           Non-Heap Memory
                                              (Metaspace)
```

---

# Heap Memory Organization

```text
                        Heap Memory
                             │
       ┌─────────────────────┴─────────────────────┐
       │                                           │
       ▼                                           ▼
 Young Generation                           Old Generation
 (Minor GC)                                 (Major/Full GC)
```

---

# Detailed Heap Structure

```text
                   Heap Memory

+----------------------------------------------------------+
|                                                          |
|                Young Generation                          |
|  +------------+------------+------------+                |
|  |    Eden    | Survivor 0 | Survivor 1 |                |
|  +------------+------------+------------+                |
|                                                          |
|               Minor Garbage Collection                   |
|                                                          |
+----------------------------------------------------------+

                     Objects that survive
                              │
                              ▼

+----------------------------------------------------------+
|                                                          |
|                Old (Tenured) Generation                  |
|                                                          |
|      Long-lived objects are stored here                 |
|                                                          |
|               Major / Full Garbage Collection            |
|                                                          |
+----------------------------------------------------------+
```

---

# Non-Heap Memory (Metaspace)

```text
              Non-Heap Memory

+----------------------------------------+
|              Metaspace                 |
|----------------------------------------|
| Class Metadata                         |
| Method Metadata                        |
| Runtime Constant Pool                  |
| Bytecode Information                   |
+----------------------------------------+
```

> Before Java 8, this area was called **PermGen (Permanent Generation)**.
>
> Since **Java 8**, PermGen has been replaced by **Metaspace**.

---

# What Happens When an Object is Created?

```java
Student s = new Student();
```

### Memory Flow

```text
Object Created

       │
       ▼

Young Generation (Eden)

       │
       ▼
Still Alive after Minor GC?

       │
      Yes
       │
       ▼

Survivor Space

       │
Repeatedly survives GC
       │
       ▼

Old Generation

       │
Eventually becomes unreachable
       │
       ▼

Major / Full Garbage Collection
```

---

# Young Generation

The Young Generation stores **newly created objects**.

It is divided into:

- Eden Space
- Survivor Space 0 (S0)
- Survivor Space 1 (S1)

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

# Old (Tenured) Generation

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

# Mark and Sweep Algorithm

One of the earliest and most fundamental Garbage Collection algorithms is the **Mark and Sweep Algorithm**.

It consists of two phases:

1. Mark Phase
2. Sweep Phase

---

# Step 1: Objects Before GC

```java
Student s1 = new Student();

Student s2 = new Student();

Student s3 = new Student();

s2 = null;
```

Memory

```text
Stack

s1 -----------+
              |
s2 ---> null  |
              |
s3 -----------|
              |
              ▼

Heap

+---------+
| Object1 |
+---------+

+---------+
| Object2 |   ← Unreachable
+---------+

+---------+
| Object3 |
+---------+
```

---

# Step 2: Mark Phase

The Garbage Collector starts from **GC Roots** (Stack variables, static variables, etc.) and marks all reachable objects.

```text
Stack

s1 -----------+
              |
s3 -----------|
              |
              ▼

Heap

✔ Object1

✘ Object2

✔ Object3
```

Reachable objects are **marked**.

Unreachable objects remain **unmarked**.

---

# Step 3: Sweep Phase

The Garbage Collector removes every unmarked object.

```text
Before Sweep

+---------+
| Object1 |
+---------+

+---------+
| Object2 | ❌
+---------+

+---------+
| Object3 |
+---------+

↓

After Sweep

+---------+
| Object1 |
+---------+

+---------+
| Object3 |
+---------+
```

Object2's memory is reclaimed.

---

# Mark and Sweep Flow
---

# Interview Questions

### Q1. What are the main parts of Heap Memory?

- Young Generation
- Old (Tenured) Generation

> **Metaspace is a Non-Heap Memory area.**

---

### Q2. What is stored in the Young Generation?

Newly created objects.

---

### Q3. What is stored in the Old Generation?

Objects that survive multiple Minor Garbage Collections.

---

### Q4. What is Metaspace?

Metaspace is a **Non-Heap Memory** area that stores class metadata, runtime constant pool information, and method metadata.

---

### Q5. What is the Mark and Sweep Algorithm?

It is a Garbage Collection algorithm that:

1. Marks all reachable objects.
2. Removes all unmarked (unreachable) objects.

---

### Q6. What is the difference between Minor GC and Major GC?

- Minor GC cleans the Young Generation.
- Major (Full) GC cleans the Old Generation and is generally slower.

---

```text
Objects Created

        │
        ▼

GC Starts

        │
        ▼

Mark Reachable Objects

        │
        ▼

Unmarked Objects Found

        │
        ▼

Delete Unmarked Objects

        │
        ▼

Memory Reclaimed
```

---

# Advantages

- Automatically frees unused memory.
- Prevents memory leaks.
- No manual memory management.
- Improves application stability.

---

# Disadvantages

- Garbage Collection may pause application execution.
- Sweep phase can leave memory fragmentation.
- Major GC is slower than Minor GC.

---

# Common Garbage Collectors

- Serial GC
- Parallel GC
- G1 GC (Default in modern Java)
- ZGC
- Shenandoah GC

> **CMS (Concurrent Mark-Sweep)** was available in older Java versions but has been removed in modern releases.

---

# Strong vs Weak References (Basic)

## Strong Reference

```java
Student s = new Student();
```

Object will **not** be garbage collected while `s` exists.

---

## Weak Reference

```java
WeakReference<Student> ref =
    new WeakReference<>(new Student());
```

The object may be garbage collected as soon as it has only weak references.
