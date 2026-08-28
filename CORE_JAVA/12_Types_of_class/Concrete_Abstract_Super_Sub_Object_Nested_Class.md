# Types of Classes in Java

Java provides different types of classes based on their purpose and usage.

```text
Classes
│
├── Concrete Class
├── Abstract Class
├── Super Class
├── Sub Class
├── Object Class
└── Nested Class
      │
      ├── Static Nested Class
      └── Inner Class (Non-static Nested Class)
            │
            ├── Member Inner Class
            ├── Local Inner Class
            └── Anonymous Inner Class
```

---

# 1. Concrete Class

## Definition

A **Concrete Class** is a normal Java class whose **all methods have implementations**.

Objects of a concrete class **can be created using the `new` keyword**.

## Characteristics

- Can be instantiated.
- Contains implemented (concrete) methods.
- Can extend another class.
- Can implement an interface.
- Can extend an abstract class by implementing all abstract methods.

## Example

```java
class Employee {

    void work() {
        System.out.println("Working");
    }
}

public class Test {

    public static void main(String[] args) {

        Employee emp = new Employee();

        emp.work();
    }
}
```

## Interview Points

- Concrete classes are the most commonly used classes.
- Any class that is **not abstract** is a concrete class.

---

# 2. Abstract Class

## Definition

An **Abstract Class** is declared using the `abstract` keyword.

It is used to achieve **partial abstraction (0–100%)** by hiding implementation details and exposing only essential functionality.

> **Use an abstract class when multiple child classes share common properties and behaviour.**

---

## Characteristics

- Declared using the `abstract` keyword.
- Cannot be instantiated.
- Can contain:
  - Abstract methods (without body)
  - Concrete methods (with body)
- Can have constructors.
- Can have instance variables and static variables.
- Can implement interfaces.
- Can be extended by other classes.

---

## Example

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal Constructor");
    }

    abstract void sound();

    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

---

## Constructor in Abstract Class

Although an abstract class cannot be instantiated, it **can have constructors**.

The constructor is invoked when a child class object is created.

```java
abstract class Animal {

    Animal() {
        System.out.println("Animal Constructor");
    }
}

class Dog extends Animal {

    Dog() {
        System.out.println("Dog Constructor");
    }
}
```

Output

```text
Animal Constructor
Dog Constructor
```

---

## When to Use?

Use an abstract class when:

- Child classes have common state.
- Child classes share common implementation.
- Some methods should be compulsory while others can be common.

---

## Interview Points

- Cannot create an object.
- Can have constructors.
- Can contain both abstract and concrete methods.
- Supports partial abstraction.

---

# 3. Super Class

## Definition

A **Super Class** (Parent Class/Base Class) is the class whose properties and methods are inherited by another class.

---

## Example

```java
class Animal {

    void eat() {

    }
}

class Dog extends Animal {

}
```

Here,

```text
Animal
```

is the **Super Class**.

---

# 4. Sub Class

## Definition

A **Sub Class** (Child Class/Derived Class) is the class that inherits from another class.

---

## Example

```java
class Animal {

}

class Dog extends Animal {

}
```

Here,

```text
Dog
```

is the **Sub Class**.

---

## Interview Points

- Subclass inherits fields and methods of the superclass.
- Achieves code reusability.
- Supports Runtime Polymorphism through method overriding.

---

# 5. Object Class

## Definition

`java.lang.Object` is the **root (top-most) class** in Java.

Every class in Java **implicitly extends the Object class** if no other superclass is specified.

```text
          Object
             │
      ┌──────┴──────┐
      │             │
   Employee      Student
```

---

## Common Methods of Object Class

- `toString()`
- `equals()`
- `hashCode()`
- `clone()`
- `wait()`
- `notify()`
- `notifyAll()`
- `getClass()`

---

## Example

```java
class Employee {

}

public class Test {

    public static void main(String[] args) {

        Employee emp = new Employee();

        System.out.println(emp.toString());
    }
}
```

---

## Interview Points

- Every Java class directly or indirectly extends `Object`.
- All objects inherit methods like `toString()` and `equals()`.

---

# 6. Nested Class

## Definition

A **Nested Class** is a class declared inside another class.

```java
class Outer {

    class Inner {

    }
}
```

---

## Why Use Nested Classes?

- Groups logically related classes.
- Improves encapsulation.
- Reduces namespace pollution.
- Useful when the inner class is used only by the outer class.

---

## Types of Nested Classes

```text
Nested Class
│
├── Static Nested Class
└── Inner Class (Non-static)
      │
      ├── Member Inner Class
      ├── Local Inner Class
      └── Anonymous Inner Class
```

---

# 6.1 Static Nested Class

## Definition

A nested class declared using the `static` keyword.

```java
class Outer {

    static class Inner {

    }
}
```

---

## Characteristics

- Belongs to the outer class.
- Can access only static members of the outer class directly.
- Can be instantiated without creating the outer class object.

---

## Example

```java
class Outer {

    static class Inner {

        void display() {
            System.out.println("Static Nested Class");
        }
    }
}

public class Test {

    public static void main(String[] args) {

        Outer.Inner obj = new Outer.Inner();

        obj.display();
    }
}
```

---

# 6.2 Inner Class (Non-static Nested Class)

## Definition

A nested class **without the `static` keyword**.

It is associated with an object of the outer class.

---

## Characteristics

- Can access all members (including private) of the outer class.
- Requires an outer class object before creating the inner class object.

---

## Example

```java
class Outer {

    class Inner {

        void display() {

        }
    }
}

Outer outer = new Outer();

Outer.Inner inner = outer.new Inner();
```

---

# Types of Inner Classes

---

## A. Member Inner Class

### Definition

An inner class declared directly inside another class.

```java
class Outer {

    class Inner {

    }
}
```

### Characteristics

- Can have access modifiers (`private`, `protected`, `public`, `default`).
- Can access all members of the outer class.
- Requires an outer class object.

---

## B. Local Inner Class

### Definition

A class declared inside a method, constructor, or any code block.

```java
class Outer {

    void display() {

        class Local {

        }
    }
}
```

### Characteristics

- Scope is limited to that block.
- Cannot be accessed outside the block.
- Cannot have access modifiers.

---

## C. Anonymous Inner Class

### Definition

An inner class **without a name**.

It is declared and instantiated in a single statement.

---

### Why Use?

Used when you need to **override or implement a method only once** without creating a separate subclass.

---

### Example

```java
Animal animal = new Animal() {

    @Override
    void sound() {
        System.out.println("Dog barks");
    }
};
```

---

## Characteristics

- No class name.
- Single-use class.
- Commonly used for event handling and callbacks.
- Cannot be reused.

---

# Static Nested Class vs Inner Class

| Static Nested Class | Inner Class |
|----------------------|-------------|
| Declared with `static` | No `static` keyword |
| Does not require outer object | Requires outer object |
| Can directly access only static members | Can access all members of outer class |
| Behaves like a static member | Behaves like an instance member |

---

# Interview Tips

### Concrete Class

- Instantiable class.
- All methods implemented.

### Abstract Class

- Cannot instantiate.
- Can have constructors.
- Can contain abstract and concrete methods.

### Super Class

- Parent class.

### Sub Class

- Child class that inherits from parent.

### Object Class

- Root class of Java.
- Every class extends `Object`.

### Nested Class

- Class inside another class.
- Used for better encapsulation and logical grouping.

### Member Inner Class

- Requires outer object.
- Can access all members of outer class.

### Local Inner Class

- Exists only inside a method or block.

### Anonymous Inner Class

- No class name.
- Used for one-time implementation.
- Commonly used to override methods without creating a separate subclass.

---

# Quick Revision

- ✅ Concrete Class → Can create objects.
- ✅ Abstract Class → Cannot instantiate, supports partial abstraction.
- ✅ Super Class → Parent class.
- ✅ Sub Class → Child class.
- ✅ Object Class → Root class of Java.
- ✅ Nested Class → Class inside another class.
- ✅ Static Nested Class → Does not need outer object.
- ✅ Member Inner Class → Needs outer object.
- ✅ Local Inner Class → Declared inside a method/block.
- ✅ Anonymous Inner Class → One-time implementation without a class name.