# Reflection API in Java (Interview Notes)

## What is Reflection?

Reflection is a feature in Java that allows a program to **inspect and manipulate its own classes, methods, fields, constructors, and annotations at runtime**.

Reflection belongs to the package:

```java
java.lang.reflect
```

---

## Why Do We Need Reflection?

Reflection is mainly used by frameworks that need to inspect classes dynamically without knowing them at compile time.

Common use cases:

- Spring Framework (Dependency Injection)
- Hibernate (ORM)
- JUnit
- Jackson/Gson (JSON Serialization)
- IDEs (Auto-completion)
- Custom Annotations Processing

---

# Reflection Architecture

```
                 JVM
                  │
                  ▼
             Class Object
                  │
      ┌───────────┼────────────┐
      ▼           ▼            ▼
   Fields      Methods    Constructors
      │           │            │
      ▼           ▼            ▼
 Read / Write   Invoke     Create Objects
```

---

# What is Class.class?

Every loaded Java class has exactly **one Class object** created by the JVM.

Example

```java
Student.class
```

or

```java
student.getClass()
```

Both refer to the metadata of the class.

```
            Student.class
                  │
                  ▼
        Class<Student> Object
                  │
     Stores metadata about Student
```

---

# Three Ways to Obtain Class Object

### 1. Using `.class`

```java
Class<Student> cls = Student.class;
```

Used when class is known at compile time.

---

### 2. Using `getClass()`

```java
Student s = new Student();

Class<?> cls = s.getClass();
```

Used when object already exists.

---

### 3. Using `Class.forName()`

```java
Class<?> cls =
        Class.forName("com.demo.Student");
```

Loads class dynamically.

Mostly used by frameworks.

---

# Common Reflection Classes

| Class | Purpose |
|---------|----------|
| Class | Represents class metadata |
| Field | Represents class fields |
| Method | Represents class methods |
| Constructor | Represents constructors |

---

# Common get() Methods

## Class

```java
getName()

getSimpleName()

getPackage()

getSuperclass()

getInterfaces()

getModifiers()

getDeclaredFields()

getFields()

getDeclaredMethods()

getMethods()

getDeclaredConstructors()

getConstructors()

getDeclaredField(String)

getDeclaredMethod(String,...)

getDeclaredConstructor(...)
```

---

## Field

```java
getName()

getType()

getModifiers()

get()

set()

setAccessible(true)
```

---

## Method

```java
getName()

getReturnType()

getParameterTypes()

invoke()

getModifiers()

setAccessible(true)
```

---

## Constructor

```java
getName()

getParameterCount()

newInstance()

getModifiers()

setAccessible(true)
```

---

# Reflecting Fields

Suppose

```java
class Eagle {

    public String name;

    private boolean canSwim;
}
```

Get public field

```java
Field field =
        Eagle.class.getField("name");
```

Get private field

```java
Field field =
        Eagle.class.getDeclaredField("canSwim");
```

---

# Accessing Private Field

Without

```java
field.setAccessible(true);
```

Java throws

```
IllegalAccessException
```

Reason

Private members cannot be accessed outside the class.

Correct way

```java
Field field =
        Eagle.class.getDeclaredField("canSwim");

field.setAccessible(true);

field.set(eagleObj, true);
```

---

# Public vs Declared Methods

| Method | Returns |
|----------|----------|
| getFields() | Only public fields (including inherited) |
| getDeclaredFields() | All fields declared in class |
| getMethods() | Public methods (including inherited) |
| getDeclaredMethods() | All declared methods |
| getConstructors() | Public constructors |
| getDeclaredConstructors() | All constructors |

---

# Invoking Methods

```java
Method method =
        cls.getDeclaredMethod("display");

method.invoke(obj);
```

Passing parameters

```java
Method method =
        cls.getDeclaredMethod(
                "add",
                int.class,
                int.class);

method.invoke(obj,10,20);
```

---

# Creating Objects Using Reflection

Normally

```java
Student s = new Student();
```

Reflection

```java
Constructor<Student> c =
        Student.class.getDeclaredConstructor();

Student s =
        c.newInstance();
```

---

# Accessing Private Constructor

```java
Constructor<Student> c =
Student.class.getDeclaredConstructor();

c.setAccessible(true);

Student s = c.newInstance();
```

---

# Reflection Can Break Singleton

Suppose

```java
class Database {

    private static Database INSTANCE =
            new Database();

    private Database() {

    }

    public static Database getInstance() {
        return INSTANCE;
    }
}
```

Normally

```java
Database d1 =
Database.getInstance();

Database d2 =
Database.getInstance();
```

```
d1 == d2

true
```

---

Using Reflection

```java
Constructor<Database> c =
Database.class.getDeclaredConstructor();

c.setAccessible(true);

Database d3 = c.newInstance();
```

Now

```
Database d1

Database d3
```

Two different objects exist.

Singleton is broken.

---

## Diagram

```
Without Reflection

Database.getInstance()

        │

        ▼

    INSTANCE

       ▲

       │

Every caller gets
same object

-----------------------------

With Reflection

Constructor.newInstance()

        │

        ▼

New Object Created

INSTANCE      NEW OBJECT

Singleton Broken
```

---

# How to Prevent Reflection Breaking Singleton

## Solution 1 (Recommended) - Enum Singleton

```java
enum Database {

    INSTANCE;
}
```

Reflection cannot create enum instances.

This is the safest Singleton implementation.

---

## Solution 2 - Constructor Check

```java
class Database {

    private static boolean created;

    private Database() {

        if(created)
            throw new RuntimeException(
                "Singleton already created");

        created = true;
    }
}
```

If Reflection tries to invoke the constructor again,

```
RuntimeException
```

is thrown.

---

# Advantages of Reflection

- Dynamic object creation
- Dynamic method invocation
- Access private members
- Used heavily by Spring/Hibernate
- Runtime inspection of metadata

---

# Disadvantages

- Slower than normal method calls
- Breaks encapsulation
- Security risks
- Runtime errors instead of compile-time errors
- Difficult to maintain

---

# Reflection vs Normal Access

| Normal Java | Reflection |
|-------------|------------|
| Compile-time | Runtime |
| Fast | Slower |
| Type Safe | Less Type Safe |
| Private members inaccessible | Can access using `setAccessible(true)` |
| Preferred for application code | Mainly used by frameworks |

---

# Most Asked Interview Questions ⭐

### 1. What is Reflection in Java?

Reflection allows inspecting and manipulating classes, methods, fields, constructors, and annotations at runtime.

---

### 2. What is the purpose of the Class class?

`Class` stores the metadata of a loaded class and acts as the entry point to the Reflection API.

---

### 3. What are the three ways to obtain a Class object?

- `.class`
- `getClass()`
- `Class.forName()`

---

### 4. Difference between `getField()` and `getDeclaredField()`?

| getField() | getDeclaredField() |
|------------|--------------------|
| Only public fields | Any field (public/private/protected/default) |
| Includes inherited fields | Only fields declared in current class |

---

### 5. Difference between `getMethods()` and `getDeclaredMethods()`?

| getMethods() | getDeclaredMethods() |
|--------------|----------------------|
| Public methods including inherited | All methods declared in current class |

---

### 6. Why do we use `setAccessible(true)`?

It bypasses Java access checks, allowing Reflection to access private fields, methods, and constructors.

---

### 7. Can Reflection access private members?

Yes.

Using

```java
setAccessible(true)
```

---

### 8. How does Reflection break Singleton?

Reflection can invoke the private constructor and create multiple instances, violating the Singleton pattern.

---

### 9. How can Singleton be protected from Reflection?

- Use Enum Singleton (best approach)
- Add constructor guard to prevent multiple instantiations

---

### 10. Where is Reflection used in real projects?

- Spring Framework
- Hibernate
- JUnit
- Jackson
- Dependency Injection
- Annotation Processing

---

# Quick Revision

- ✅ Reflection works at runtime.
- ✅ Entry point is the `Class` object.
- ✅ Three ways to obtain a `Class` object: `.class`, `getClass()`, `Class.forName()`.
- ✅ `getDeclaredXxx()` returns all declared members, while `getXxx()` returns only public members (including inherited where applicable).
- ✅ `setAccessible(true)` bypasses Java access checks.
- ✅ Reflection can invoke private constructors and break Singleton.
- ✅ Prefer **Enum Singleton** to prevent Reflection attacks.
- ✅ Reflection is powerful but slower and should mainly be used by frameworks.