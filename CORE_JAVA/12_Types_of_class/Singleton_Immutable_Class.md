# Singleton Class in Java

## What is a Singleton Class?

A **Singleton Class** is a class that allows **only one object (instance)** to be created throughout the application's lifecycle.

It provides a **global access point** to that single instance.

---

## Why Do We Need Singleton?

Singleton is used when exactly **one shared object** should exist across the application.

Examples:

- Database Connection
- Logger
- Configuration Manager
- Cache Manager
- Thread Pool
- Runtime Class

---

## Rules to Create a Singleton Class

1. Make the constructor `private`.
2. Create a `private static` instance of the class.
3. Provide a `public static getInstance()` method to return the instance.

---

# Different Ways to Implement Singleton

```text
Singleton
│
├── Eager Initialization
├── Lazy Initialization
├── Synchronized Method
├── Double Checked Locking
├── Bill Pugh Singleton
└── Enum Singleton
```

---

# 1. Eager Initialization

## Idea

The object is created **when the class is loaded**, even if it is never used.

### Code

```java
public class DatabaseConnection {

    // Object created immediately during class loading
    private static final DatabaseConnection INSTANCE =
            new DatabaseConnection();

    // Prevent object creation from outside
    private DatabaseConnection() {

    }

    // Return the same object every time
    public static DatabaseConnection getInstance() {

        return INSTANCE;
    }
}
```

---

## Advantages

- Very simple.
- Thread-safe.
- No synchronization required.

---

## Disadvantages

- Object is created even if never used.
- Wastes memory if initialization is expensive.

---

## Interview Point

Use when object creation is lightweight and always required.

---

# 2. Lazy Initialization

## Idea

Create the object **only when it is needed**.

### Code

```java
public class DatabaseConnection {

    private static DatabaseConnection instance;

    private DatabaseConnection() {

    }

    public static DatabaseConnection getInstance() {

        if (instance == null) {

            instance = new DatabaseConnection();
        }

        return instance;
    }
}
```

---

## Advantages

- Object created only when required.
- Saves memory.

---

## Disadvantages

- Not thread-safe.
- Multiple threads may create multiple objects.

---

### Problem

```text
Thread-1
        \
         instance == null
        /
Thread-2

Both create separate objects.
```

---

## Interview Point

Never use this implementation in a multithreaded application.

---

# 3. Synchronized Method Singleton

## Idea

Synchronize the `getInstance()` method so only one thread can execute it at a time.

### Code

```java
public class DatabaseConnection {

    private static DatabaseConnection instance;

    private DatabaseConnection() {

    }

    public synchronized static DatabaseConnection getInstance() {

        if (instance == null) {

            instance = new DatabaseConnection();
        }

        return instance;
    }
}
```

---

## Advantages

- Thread-safe.
- Easy to implement.

---

## Disadvantages

- Every call acquires a lock.
- Slower due to synchronization overhead.

---

## Interview Point

Although thread-safe, synchronization affects performance because locking happens even after the object has been created.

---

# 4. Double Checked Locking (DCL)

## Idea

Synchronize **only during the first object creation**.

Subsequent calls avoid synchronization.

### Code

```java
public class DatabaseConnection {

    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {

    }

    public static DatabaseConnection getInstance() {

        if (instance == null) {

            synchronized (DatabaseConnection.class) {

                if (instance == null) {

                    instance = new DatabaseConnection();
                }
            }
        }

        return instance;
    }
}
```

---

## Why Double Check?

### First Check

Avoids synchronization after the object is created.

### Second Check

Ensures another thread has not already created the object while waiting for the lock.

---

## Why `volatile`?

Without `volatile`, JVM instruction reordering may expose a partially initialized object to another thread.

`volatile` prevents this by ensuring proper visibility and ordering.

---

## Advantages

- Thread-safe.
- Better performance than synchronized method.
- Synchronization happens only once.

---

## Disadvantages

- More complex.
- Requires `volatile`.

---

## Interview Point

Double Checked Locking is a popular interview topic. Always mention that **`volatile` is mandatory**.

---

# 5. Bill Pugh Singleton

## Idea

Uses a **static inner helper class**.

The object is created only when the helper class is loaded.

### Code

```java
public class DatabaseConnection {

    private DatabaseConnection() {

    }

    private static class Helper {

        private static final DatabaseConnection INSTANCE =
                new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {

        return Helper.INSTANCE;
    }
}
```

---

## How It Works

The JVM loads the inner class only when `getInstance()` is called.

Therefore,

- Lazy Initialization
- Thread Safety
- No Synchronization

are achieved automatically.

---

## Advantages

- Lazy initialization.
- Thread-safe.
- No synchronization overhead.
- Simple implementation.

---

## Interview Point

This is considered one of the **best Singleton implementations**.

---

# 6. Enum Singleton

## Idea

Use an Enum with a single constant.

### Code

```java
public enum DatabaseConnection {

    INSTANCE;
}
```

Usage

```java
DatabaseConnection obj =
        DatabaseConnection.INSTANCE;
```

---

## Advantages

- Thread-safe.
- Prevents Reflection attacks.
- Prevents Serialization issues.
- Simplest implementation.

---

## Disadvantages

- Not suitable if lazy initialization with constructor parameters is required.

---

## Interview Point

Joshua Bloch recommends Enum Singleton because it automatically handles serialization and reflection issues.

---

# Comparison

| Approach | Lazy | Thread-safe | Performance |
|----------|------|-------------|-------------|
| Eager Initialization | ❌ | ✅ | Excellent |
| Lazy Initialization | ✅ | ❌ | Excellent |
| Synchronized Method | ✅ | ✅ | Slow |
| Double Checked Locking | ✅ | ✅ | Good |
| Bill Pugh Singleton | ✅ | ✅ | Excellent |
| Enum Singleton | ✅* | ✅ | Excellent |

> **Note:** Enum instances are created when the enum class is initialized by the JVM.

---

# Which Singleton Should You Use?

| Scenario | Recommended |
|-----------|-------------|
| Simple application | Eager Initialization |
| Multithreaded application | Bill Pugh Singleton |
| Maximum safety (Reflection + Serialization) | Enum Singleton |
| Legacy applications | Double Checked Locking |

---

# Common Interview Questions

## Why is the constructor private?

To prevent object creation using the `new` keyword.

---

## Why is the instance variable static?

Static variables belong to the class, ensuring a single shared instance.

---

## Why is `getInstance()` static?

So it can be called without creating an object.

```java
DatabaseConnection.getInstance();
```

---

## Why is Lazy Initialization not thread-safe?

Multiple threads can simultaneously observe `instance == null` and create separate objects.

---

## Why is the synchronized method slow?

Every call acquires a lock, even after the object has already been created.

---

## Why is `volatile` used in Double Checked Locking?

To prevent instruction reordering and ensure visibility across threads.

---

## Why is Bill Pugh better?

- Lazy initialization.
- Thread-safe.
- No synchronization overhead.
- Relies on JVM class loading mechanism.

---

## Why is Enum Singleton considered the safest?

Because it protects against:

- Reflection
- Serialization
- Multiple object creation

---

# Most Asked Interview Questions ⭐

1. What is a Singleton Class?
2. Why is the constructor private?
3. Why is `getInstance()` static?
4. Difference between Eager and Lazy Initialization.
5. Why is Lazy Initialization not thread-safe?
6. Why is synchronized Singleton slow?
7. Explain Double Checked Locking.
8. Why is `volatile` required?
9. Explain Bill Pugh Singleton.
10. Why is Enum Singleton considered the best implementation?

---

# Quick Revision

- ✅ Singleton allows only one object.
- ✅ Constructor must be private.
- ✅ Instance is stored in a static variable.
- ✅ `getInstance()` returns the same object.
- ✅ Lazy Initialization is not thread-safe.
- ✅ Synchronized method is thread-safe but slower.
- ✅ Double Checked Locking uses `volatile`.
- ✅ Bill Pugh uses a static inner helper class.
- ✅ Enum Singleton is the safest implementation.
- ✅ Most preferred in interviews: **Bill Pugh** and **Enum Singleton**.

# Immutable Class

## What is an Immutable Class?

An **Immutable Class** is a class whose **object state cannot be changed once it is created**.

Once an object is initialized, its data remains constant throughout its lifetime.

---

## Why Do We Need Immutable Classes?

Immutable objects are:

- Thread-safe by default.
- Easy to cache.
- Safe to share between multiple threads.
- Secure because the state cannot be modified.

---

## Rules to Create an Immutable Class

### 1. Declare the class as `final`

This prevents inheritance.

```java
public final class Employee {

}
```

---

### 2. Make all instance variables `private` and `final`

- `private` → prevents direct access.
- `final` → value can be assigned only once.

```java
private final int id;
private final String name;
```

---

### 3. Initialize variables only through the constructor

```java
public Employee(int id, String name) {

    this.id = id;
    this.name = name;
}
```

---

### 4. Do not provide setter methods

❌

```java
public void setName(String name) {

}
```

Without setters, the object's state cannot change after creation.

---

### 5. Provide only getter methods

```java
public String getName() {

    return name;
}
```

---

### 6. Return defensive copies for mutable objects

If a field is mutable (e.g., `Date`, `List`), never return the original object.

❌ Wrong

```java
public Date getDob() {

    return dob;
}
```

✔ Correct

```java
public Date getDob() {

    return new Date(dob.getTime());
}
```

Similarly, make a defensive copy inside the constructor.

```java
this.dob = new Date(dob.getTime());
```

---

# Example

```java
public final class Employee {

    private final int id;
    private final String name;

    public Employee(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
```

Usage

```java
Employee emp = new Employee(101, "Priyasha");

// No setter methods available

System.out.println(emp.getName());
```

Once created, `emp` cannot be modified.

---

# Mutable vs Immutable Class

| Mutable Class | Immutable Class |
|---------------|-----------------|
| Object state can change | Object state cannot change |
| Has setters | No setters |
| Not inherently thread-safe | Thread-safe by default |
| Easier to modify | Safer to share |

---

# Advantages

- Thread-safe.
- Easy to cache.
- Easy to maintain.
- Prevents accidental modification.
- Improves security.
- Ideal for shared objects.

---

# Real-world Examples

Java provides many immutable classes:

- `String`
- `Integer`
- `Long`
- `Double`
- `Boolean`
- `BigInteger`
- `BigDecimal`
- `LocalDate`
- `LocalDateTime`

---

# Immutable Class vs Final Class

| Immutable Class | Final Class |
|-----------------|-------------|
| Object state cannot change | Class cannot be inherited |
| Usually declared `final` | May still have mutable fields |
| No setters | Setters are allowed |
| Focus is object immutability | Focus is preventing inheritance |

**Note:** A `final` class is **not necessarily immutable**.

Example:

```java
public final class Student {

    private String name;

    public void setName(String name) {

        this.name = name;
    }
}
```

This class is **final**, but **not immutable** because `name` can still be modified.

---

# Common Interview Questions

## What is an Immutable Class?

An immutable class is a class whose object state cannot be modified after it is created.

---

## How do you create an Immutable Class?

- Declare the class as `final`.
- Make fields `private` and `final`.
- Initialize fields through the constructor.
- Do not provide setters.
- Return defensive copies for mutable objects.

---

## Why is String immutable?

- Thread-safe.
- Enables String Constant Pool.
- Improves security.
- Allows hashcode caching.
- Safe to use as keys in collections like `HashMap`.

---

## Why should mutable objects be returned as defensive copies?

Returning the original object allows external code to modify the internal state, breaking immutability.

---

## Is a Final Class always Immutable?

No.

A final class only prevents inheritance.

If it has setters or mutable fields, it is **not immutable**.

---

# Most Asked Interview Questions ⭐

1. What is an Immutable Class?
2. How do you create an Immutable Class?
3. Why is String immutable?
4. Difference between Immutable Class and Final Class.
5. Why don't immutable classes have setters?
6. What is defensive copying?
7. Why are immutable classes thread-safe?

---

# Quick Revision

- ✅ Immutable object cannot change after creation.
- ✅ Declare the class `final`.
- ✅ Fields should be `private` and `final`.
- ✅ Initialize fields through the constructor.
- ✅ Do not provide setters.
- ✅ Return defensive copies for mutable objects.
- ✅ `String`, `Integer`, `BigDecimal`, `LocalDate` are immutable.
- ✅ Immutable classes are naturally thread-safe.