# Java Annotations (Interview Notes)

## What is an Annotation?

An **Annotation** is metadata (data about data) that provides information to the compiler, JVM, or frameworks.

Annotations **do not directly affect program execution** but are used by:

- Compiler
- JVM
- Reflection
- Frameworks (Spring, Hibernate, JUnit, etc.)

---

## Why Do We Need Annotations?

Without annotations, developers had to configure everything manually (XML/config files).

Annotations make the code:

- More readable
- Less configuration
- Easier for frameworks to process using Reflection

Example

```java
@Override
public void display() {

}
```

The compiler verifies whether `display()` actually overrides a superclass method.

---

# Types of Annotations

There are two major categories.

```
Annotations
│
├── Built-in Annotations
│     ├── @Override
│     ├── @Deprecated
│     ├── @SuppressWarnings
│     ├── @FunctionalInterface
│     └── @SafeVarargs
│
└── Meta Annotations
      ├── @Target
      ├── @Retention
      ├── @Documented
      ├── @Inherited
      └── @Repeatable
```

---

# Built-in Annotations

---

## 1. @Override

Indicates that a method overrides a superclass/interface method.

```java
class Animal {

    void sound(){ }
}

class Dog extends Animal {

    @Override
    void sound(){

    }
}
```

If the method signature doesn't match,

Compiler Error.

Advantages

- Prevents accidental overloading
- Improves readability
- Compiler verification

---

## 2. @Deprecated

Marks an API as obsolete.

```java
@Deprecated
void oldMethod(){

}
```

Using

```java
oldMethod();
```

Produces

```
Warning:
This API is deprecated.
```

Use when

- API should no longer be used.
- Better alternative exists.

---

## 3. @SuppressWarnings

Suppresses compiler warnings.

Example

```java
@SuppressWarnings("unchecked")
List list = new ArrayList();
```

Common warnings

```
unchecked

deprecation

rawtypes

unused
```

---

## 4. @FunctionalInterface

Indicates that an interface contains **exactly one abstract method**.

```java
@FunctionalInterface
interface Calculator{

    int add(int a,int b);
}
```

Compiler ensures

- Only one abstract method exists.

Used with

- Lambda Expressions
- Streams

---

## 5. @SafeVarargs

Introduced in Java 7.

Suppresses warnings related to **heap pollution** when using generic varargs.

Applicable only to

- static methods
- final methods
- private methods (Java 9)

Example

```java
@SafeVarargs
static <T> void print(T... values){

}
```

---

# What is Heap Pollution?

This is a very common interview question.

---

## Definition

Heap Pollution occurs when a variable of a parameterized type refers to an object of a different parameterized type.

Example

```java
List<String> names = new ArrayList<>();

Object obj = names;
```

Now

```java
List<Integer> nums =
(List<Integer>) obj;
```

Compiler allows the cast (unchecked warning).

Now

```java
nums.add(100);
```

Internally,

```
names

↓

["Java",100]
```

Later

```java
String s = names.get(1);
```

Throws

```
ClassCastException
```

This is Heap Pollution.

---

## Heap Pollution with Varargs

Example

```java
static void demo(List<String>... lists){

    Object[] arr = lists;

    arr[0] = List.of(100);

    String s = lists[0].get(0);
}
```

Runtime

```
ClassCastException
```

Compiler warning

```
Possible heap pollution
```

To indicate that the method is safe,

```java
@SafeVarargs
```

is used.

---

# Meta Annotations

Meta annotations are annotations applied **to other annotations**.

```
@Target

@Retention

@Inherited

@Documented

@Repeatable
```

---

# @Target

Specifies where an annotation can be used.

Example

```java
@Target(ElementType.METHOD)
```

Allowed targets

```
TYPE

FIELD

METHOD

PARAMETER

CONSTRUCTOR

LOCAL_VARIABLE

PACKAGE

ANNOTATION_TYPE
```

---

# @Retention

Specifies how long the annotation is retained.

```
SOURCE

CLASS

RUNTIME
```

---

### SOURCE

Removed after compilation.

```
.java

↓

Compiler

↓

Gone
```

Example

```
@Override
```

---

### CLASS

Stored in

```
.class
```

file.

Ignored by JVM.

---

### RUNTIME

Available during runtime.

Used by Reflection.

Spring/Hibernate use this.

Example

```java
@Retention(RetentionPolicy.RUNTIME)
```

---

# @Documented

Indicates that the annotation should appear in generated JavaDocs.

Without

```
JavaDocs

↓

Annotation Missing
```

With

```
JavaDocs

↓

Annotation Visible
```

---

# @Inherited

Allows child classes to inherit annotations from parent classes.

Example

```java
@Inherited
@interface MyAnnotation{

}
```

```
Parent

↓

@MyAnnotation

↓

Child

Automatically has annotation
```

Works only for

- Classes

Not for

- Methods
- Fields

---

# @Repeatable (Java 8)

This is the most confusing annotation.

Let's understand it step by step.

---

## Problem Before Java 8

Suppose a student knows

- Java
- Spring
- Hibernate

We want

```java
@Skill("Java")

@Skill("Spring")

@Skill("Hibernate")
```

Before Java 8,

This was illegal.

One annotation could appear only once.

---

## Old Solution

Create an array.

```java
@Skills({

    @Skill("Java"),

    @Skill("Spring"),

    @Skill("Hibernate")
})
```

This works,

but is ugly.

---

## Java 8 Solution

Use

```java
@Repeatable
```

Now we can simply write

```java
@Skill("Java")

@Skill("Spring")

@Skill("Hibernate")
```

Much cleaner.

---

# How Does @Repeatable Work?

Suppose

```java
@Skill("Java")

@Skill("Spring")

@Skill("Hibernate")
```

Compiler secretly converts it into

```
@Skills({

   @Skill("Java"),

   @Skill("Spring"),

   @Skill("Hibernate")

})
```

You don't write this conversion.

The compiler does it.

---

## Diagram

What you write

```
Employee

|

@Skill("Java")

@Skill("Spring")

@Skill("Hibernate")
```

Compiler converts into

```
Employee

|

@Skills(

    {

      Skill("Java"),

      Skill("Spring"),

      Skill("Hibernate")

    }

)
```

---

# Creating Repeatable Annotation

Step 1

Create annotation.

```java
@Repeatable(Skills.class)

@interface Skill{

    String value();
}
```

Step 2

Container annotation.

```java
@interface Skills{

    Skill[] value();
}
```

Usage

```java
@Skill("Java")

@Skill("Spring")

@Skill("Hibernate")
class Employee{

}
```

---

# Why Do We Need Repeatable?

Useful when the same metadata occurs multiple times.

Examples

- Multiple Roles
- Multiple Skills
- Multiple Permissions
- Multiple Mappings
- Multiple Tags

---

# Custom Annotation

We can create our own annotation.

Example

```java
@Target(ElementType.TYPE)

@Retention(RetentionPolicy.RUNTIME)

@interface Developer{

    String name();

    int experience();
}
```

Usage

```java
@Developer(

    name="Priyasha",

    experience=3

)
class Employee{

}
```

---

# Accessing Annotation Using Reflection

```java
Class<Employee> cls = Employee.class;

Developer dev =
cls.getAnnotation(Developer.class);

System.out.println(dev.name());

System.out.println(dev.experience());
```

---

# Built-in vs Custom Annotation

| Built-in | Custom |
|-----------|---------|
| Provided by Java | Created by developer |
| @Override | @Developer |
| @Deprecated | @Author |
| @FunctionalInterface | @EntityInfo |

---

# Interview Questions ⭐

## What is an Annotation?

Metadata that provides information to the compiler, JVM, or frameworks.

---

## What are Meta Annotations?

Annotations that are applied to other annotations.

Examples

- @Target
- @Retention
- @Inherited
- @Repeatable

---

## Difference between SOURCE, CLASS and RUNTIME?

| Retention | Available |
|------------|-----------|
| SOURCE | Compiler only |
| CLASS | Bytecode |
| RUNTIME | Reflection |

---

## What is Heap Pollution?

Heap Pollution occurs when a parameterized type refers to an object of another parameterized type, usually because of raw types or generic varargs, which may cause `ClassCastException` at runtime.

---

## Why is @SafeVarargs needed?

To suppress compiler warnings for generic varargs methods that are guaranteed to be type-safe.

---

## What is @Repeatable?

Allows the same annotation to be applied multiple times to the same declaration.

Internally,

```
Multiple annotations

↓

Container annotation

↓

Processed by JVM
```

---

## Why was @Repeatable introduced?

Before Java 8, the same annotation couldn't be applied multiple times.

Developers had to create wrapper annotations manually.

Java 8 automated this using `@Repeatable`.

---

## What is the purpose of @Retention?

Specifies how long an annotation is retained:

- SOURCE
- CLASS
- RUNTIME

---

## When is Reflection required for annotations?

Only when the annotation has

```java
@Retention(RetentionPolicy.RUNTIME)
```

---

# Quick Revision

- ✅ Annotations provide metadata.
- ✅ Built-in annotations: `@Override`, `@Deprecated`, `@SuppressWarnings`, `@FunctionalInterface`, `@SafeVarargs`.
- ✅ Meta annotations: `@Target`, `@Retention`, `@Documented`, `@Inherited`, `@Repeatable`.
- ✅ Heap Pollution occurs due to unsafe generic type usage.
- ✅ `@SafeVarargs` suppresses safe generic varargs warnings.
- ✅ `@Repeatable` allows the same annotation multiple times; the compiler wraps them into a container annotation.
- ✅ Custom annotations are created using `@interface`.
- ✅ Runtime annotations can be accessed using Reflection.