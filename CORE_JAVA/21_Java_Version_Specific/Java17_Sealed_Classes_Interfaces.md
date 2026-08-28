# Java 17 — Sealed Classes and Interfaces

> **Main idea:** Sealed classes/interfaces give controlled inheritance by explicitly defining which types are allowed to extend or implement them.

---

# 1. What Problem Do Sealed Classes Solve?

Normally, a Java class is open for inheritance unless it is `final`.

```java
class Vehicle {
}
```

Any class can extend it:

```java
class Car extends Vehicle {
}

class Bike extends Vehicle {
}

class Truck extends Vehicle {
}
```

Sometimes this is not desirable.

You may want:

```text
Vehicle
   ├── Car
   ├── Bike
   └── Truck

No other class should directly extend Vehicle.
```

A **sealed class** allows you to explicitly control this hierarchy.

### Interview answer

> A sealed class restricts which classes can directly extend it. It is useful when the developer wants a controlled and known inheritance hierarchy.

Sealed classes and interfaces became a permanent feature in **Java 17**. citeturn0search4turn0search0

---

# 2. Basic Syntax

```java
public sealed class Vehicle
        permits Car, Bike {
}
```

The important keywords are:

```text
sealed
permits
final
sealed
non-sealed
```

### Meaning

```text
sealed
→ Restrict who can extend/implement

permits
→ Explicitly list allowed direct subclasses/subinterfaces

final
→ Stop inheritance completely

sealed
→ Continue restricted inheritance

non-sealed
→ Open the hierarchy again
```

---

# 3. Basic Sealed Class Example

```java
sealed class Vehicle
        permits Car, Bike {
}
```

Now:

```java
final class Car extends Vehicle {
}

final class Bike extends Vehicle {
}
```

is valid.

But:

```java
class Truck extends Vehicle {
}
```

is **not valid**.

The compiler rejects it because `Truck` is not listed in `permits`.

```text
Vehicle
  │
  ├── Car   ✓
  ├── Bike  ✓
  └── Truck ✗
```

---

# 4. What Does `permits` Mean?

`permits` defines the types that are allowed to be **direct subclasses** of the sealed class.

```java
sealed class Vehicle
        permits Car, Bike {
}
```

This means:

```text
Vehicle
 ├── Car   ✓
 └── Bike  ✓
```

but:

```text
Truck extends Vehicle
```

is not allowed.

### Important interview point

The types listed in `permits` must be **direct subclasses** of the sealed class.

They cannot be unrelated classes.

---

# 5. Rules for Permitted Subclasses

Every direct subclass of a sealed class must explicitly continue the inheritance policy using exactly one of:

```text
final
sealed
non-sealed
```

Example:

```java
sealed class Vehicle
        permits Car, Bike, Truck {
}

final class Car extends Vehicle {
}

sealed class Bike extends Vehicle
        permits SportsBike {
}

non-sealed class Truck extends Vehicle {
}
```

These three keywords mean different things.

---

# 6. `final` Permitted Subclass

```java
final class Car extends Vehicle {
}
```

Meaning:

```text
Vehicle
   ↓
  Car
   ↓
No more subclasses
```

Example:

```java
class ElectricCar extends Car {
}
```

will fail because `Car` is `final`.

### Interview answer

> `final` closes that branch of the sealed hierarchy.

---

# 7. `sealed` Permitted Subclass

A permitted subclass can itself be sealed.

```java
sealed class Bike extends Vehicle
        permits SportsBike, NormalBike {
}
```

Now:

```text
Vehicle
   ↓
  Bike
  ├── SportsBike
  └── NormalBike
```

Those subclasses must again be declared:

```text
final
sealed
or
non-sealed
```

### Example

```java
final class SportsBike extends Bike {
}

final class NormalBike extends Bike {
}
```

---

# 8. `non-sealed` Permitted Subclass

`non-sealed` **opens the hierarchy again**.

```java
non-sealed class Truck extends Vehicle {
}
```

Now any class can extend `Truck`:

```java
class HeavyTruck extends Truck {
}

class MiniTruck extends Truck {
}
```

Hierarchy:

```text
Vehicle          ← sealed
   │
   └── Truck     ← non-sealed
        ├── HeavyTruck
        ├── MiniTruck
        └── AnyOtherTruck
```

### Interview answer

> `non-sealed` allows unrestricted inheritance from that point downward.

---

# 9. `final` vs `sealed` vs `non-sealed`

| Modifier | Meaning |
|---|---|
| `final` | No further inheritance |
| `sealed` | Further inheritance is allowed only through explicitly permitted types |
| `non-sealed` | Removes the sealing restriction for that branch |

### Easy memory trick

```text
final
→ STOP

sealed
→ CONTROL

non-sealed
→ OPEN
```

---

# 10. Important Rule from the Screenshot

For a direct subclass of a sealed class:

```text
It must be:
final
OR
sealed
OR
non-sealed
```

This is mandatory.

For example:

```java
sealed class Animal
        permits Dog {
}

// ❌ Compile-time error
class Dog extends Animal {
}
```

Correct:

```java
final class Dog extends Animal {
}
```

or:

```java
sealed class Dog extends Animal
        permits Puppy {
}
```

or:

```java
non-sealed class Dog extends Animal {
}
```

This requirement prevents accidentally reopening a sealed hierarchy. citeturn0search0

---

# 11. Direct Subclass Rule

Suppose:

```java
sealed class A
        permits B {
}

final class B extends A {
}

class C extends B {
}
```

This is valid because:

```text
A
↓
B        ← directly extends A
↓
C        ← extends B
```

`C` does not need to appear in `A`'s `permits` clause because `C` is not a direct subclass of `A`.

### Important

`permits` controls:

> **Direct subclasses only.**

---

# 12. Sealed Interface

Interfaces can also be sealed.

```java
sealed interface Payment
        permits CreditCardPayment,
                UpiPayment {
}
```

Only the permitted classes/interfaces can directly implement or extend it.

Example:

```java
final class CreditCardPayment
        implements Payment {
}

final class UpiPayment
        implements Payment {
}
```

But:

```java
class CashPayment implements Payment {
}
```

is not allowed.

### Interview answer

> A sealed interface restricts which classes can implement it and which interfaces can extend it.

---

# 13. Sealed Interface Can Permit Interfaces Too

A sealed interface can permit:

- Classes
- Interfaces

Example:

```java
sealed interface Payment
        permits CardPayment, DigitalPayment {
}
```

```java
final class CardPayment
        implements Payment {
}

non-sealed interface DigitalPayment
        extends Payment {
}
```

Because `DigitalPayment` is `non-sealed`, other interfaces/classes can extend or implement it according to normal Java rules.

---

# 14. Sealed Interface Example

```java
sealed interface Shape
        permits Circle, Rectangle {
}

final class Circle
        implements Shape {
}

final class Rectangle
        implements Shape {
}
```

Hierarchy:

```text
Shape
  │
  ├── Circle
  └── Rectangle
```

No other class can directly implement `Shape`.

---

# 15. Sealed Interface with Interface Hierarchy

```java
sealed interface Payment
        permits CardPayment, DigitalPayment {
}

final class CardPayment
        implements Payment {
}

non-sealed interface DigitalPayment
        extends Payment {
}
```

Now:

```text
Payment
   │
   ├── CardPayment
   │
   └── DigitalPayment
            ↓
       Open hierarchy
```

Another interface can extend `DigitalPayment`:

```java
interface UpiPayment
        extends DigitalPayment {
}
```

---

# 16. `permits` Types Must Be Direct Subtypes

This is a common interview/trick question.

Incorrect:

```java
sealed class Animal
        permits Puppy {
}

final class Dog extends Animal {
}

final class Puppy extends Dog {
}
```

This is invalid because:

```text
Animal
  ↓
 Dog
  ↓
Puppy
```

`Puppy` is not a **direct subclass** of `Animal`.

Correct:

```java
sealed class Animal
        permits Dog {
}

final class Dog extends Animal {
}
```

The `permits` list must identify direct permitted subclasses. citeturn0search0

---

# 17. Where Must Permitted Types Be Located?

The permitted subclasses/interfaces must be accessible to the sealed type at compile time.

For a named module:

```text
Same module
```

For an unnamed module:

```text
Same package
```

This keeps a sealed hierarchy within a controlled maintenance boundary. citeturn0search0

### Interview answer

> Permitted subclasses must be accessible and must be in the same module as the sealed type; for an unnamed module, they must be in the same package.

---

# 18. Can `permits` Be Omitted?

Yes.

If all permitted subclasses are declared in the **same source file**, the `permits` clause can be omitted.

Example:

```java
public sealed class Vehicle {
}

final class Car extends Vehicle {
}

final class Bike extends Vehicle {
}
```

The compiler can infer the permitted direct subclasses from the same compilation unit.

However, if the permitted subclasses are in separate source files, use:

```java
sealed class Vehicle
        permits Car, Bike {
}
```

citeturn0search0

---

# 19. `sealed` vs `final`

This is one of the most common interview questions.

### `final`

```java
final class Vehicle {
}
```

Means:

```text
No subclass allowed.
```

### `sealed`

```java
sealed class Vehicle
        permits Car, Bike {
}
```

Means:

```text
Some subclasses allowed.
Only specified subclasses.
```

### Comparison

| `final` | `sealed` |
|---|---|
| Zero subclasses | Fixed permitted subclasses |
| Completely closed | Controlled hierarchy |
| No `permits` | Can use `permits` |
| Useful when inheritance should stop | Useful when only specific types should inherit |

### Interview answer

> `final` completely prevents inheritance, whereas `sealed` allows inheritance but restricts it to a known set of permitted subclasses.

---

# 20. `sealed` vs `abstract`

These solve different problems.

### `abstract`

Controls whether the class can be instantiated directly.

```java
abstract class Animal {
}
```

It does **not** control who can extend it.

### `sealed`

Controls inheritance.

```java
sealed class Animal
        permits Dog, Cat {
}
```

It does not primarily exist to prevent instantiation.

### Important

They can be combined:

```java
abstract sealed class Animal
        permits Dog, Cat {
}
```

---

# 21. Sealed Classes and Records

Records are implicitly `final`.

Therefore, a record can implement a sealed interface.

Example:

```java
sealed interface Payment
        permits CardPayment, UpiPayment {
}

record CardPayment(String cardNumber)
        implements Payment {
}

record UpiPayment(String upiId)
        implements Payment {
}
```

The records satisfy the requirement because records are implicitly `final`. citeturn0search0

---

# 22. Why Use Sealed Classes?

## 1. Controlled inheritance

You know exactly which types can directly extend the class.

## 2. Better domain modeling

Useful when a domain has a fixed number of variants.

Example:

```text
Payment
 ├── CardPayment
 ├── UpiPayment
 └── CashPayment
```

## 3. Safer API design

Library authors can prevent clients from creating arbitrary direct subclasses.

## 4. Better reasoning about a type hierarchy

The compiler knows the permitted direct subclasses.

## 5. Useful with pattern matching

Sealed hierarchies work particularly well with Java's pattern-matching features because the compiler can reason about a known set of subclasses.

---

# 23. Sealed Classes + Pattern Matching

A common conceptual example:

```java
sealed interface Payment
        permits CardPayment, UpiPayment {
}

final class CardPayment
        implements Payment {
}

final class UpiPayment
        implements Payment {
}
```

The compiler knows:

```text
Payment
 ├── CardPayment
 └── UpiPayment
```

So sealed hierarchies are useful when implementing logic over a **closed set of domain types**.

> Note: Pattern matching for `switch` evolved across Java releases and some versions were preview features. For a Java 17 interview, focus primarily on the sealed-class rules unless the interviewer specifically asks about Java 17 preview features.

---

# 24. Complete Example

```java
public class SealedClassesDemo {

    public static void main(String[] args) {

        // Car is one of the permitted subclasses.
        Vehicle car = new Car();

        // Bike is another permitted subclass.
        Vehicle bike = new Bike();

        System.out.println(car.getClass().getSimpleName());
        System.out.println(bike.getClass().getSimpleName());
    }
}

// Vehicle controls which classes can directly extend it.
sealed class Vehicle
        permits Car, Bike {
}

// final closes this branch of the hierarchy.
final class Car extends Vehicle {
}

// non-sealed opens this branch for further inheritance.
non-sealed class Bike extends Vehicle {
}

// Because Bike is non-sealed, this is allowed.
class SportsBike extends Bike {
}

// ❌ This would NOT compile:
//
// class Truck extends Vehicle {
// }
//
// Truck is not listed in the permits clause.
```

---

# 25. Complete Sealed Interface Example

```java
public class SealedInterfaceDemo {

    public static void main(String[] args) {

        Payment payment =
                new CardPayment("1234");

        payment.pay();
    }
}

// Only CardPayment and UpiPayment can directly implement Payment.
sealed interface Payment
        permits CardPayment, UpiPayment {

    void pay();
}

// final means this branch cannot be extended.
record CardPayment(String cardNumber)
        implements Payment {

    @Override
    public void pay() {
        System.out.println(
                "Payment using card: " + cardNumber
        );
    }
}

// non-sealed means this branch can be extended again.
non-sealed class UpiPayment
        implements Payment {

    @Override
    public void pay() {
        System.out.println("Payment using UPI");
    }
}

// Allowed because UpiPayment is non-sealed.
class GooglePayPayment extends UpiPayment {
}
```

---

# 26. Interview Scenario

### Question

> Why not just use `final`?

Answer:

```text
final
→ No inheritance.

sealed
→ Controlled inheritance.
→ Some known types can inherit.
```

Example:

```text
Payment
 ├── CardPayment
 ├── UpiPayment
 └── CashPayment
```

If you want these three types but no arbitrary fourth type:

```java
sealed interface Payment
        permits CardPayment,
                UpiPayment,
                CashPayment {
}
```

That is exactly where sealed interfaces are useful.

---

# 27. Interview Scenario — Why Not Just Use an Abstract Class?

An abstract class does not restrict who can extend it.

```java
abstract class Payment {
}
```

Anyone can create:

```java
class CardPayment extends Payment {
}

class UpiPayment extends Payment {
}

class UnknownPayment extends Payment {
}
```

With sealed:

```java
sealed class Payment
        permits CardPayment, UpiPayment {
}
```

the compiler prevents:

```java
class UnknownPayment extends Payment {
}
```

---

# 28. Common Mistakes

### Mistake 1

```java
sealed class A
        permits B {
}

class B extends A {
}
```

❌ Invalid.

`B` must be:

```java
final
sealed
```

or:

```java
non-sealed
```

---

### Mistake 2

```java
sealed class A
        permits C {
}

class B extends A {
}

final class C extends B {
}
```

❌ Invalid.

`C` is not a direct subclass of `A`.

---

### Mistake 3

```java
sealed class A
        permits B {
}

final class B extends A {
}

class C extends B {
}
```

❌ Invalid because `B` is final.

---

### Mistake 4

```java
sealed class A
        permits B {
}

non-sealed class B extends A {
}

class C extends B {
}
```

✅ Valid.

`B` explicitly opens its branch.

---

# 29. Quick Comparison

| Feature | `final` | `sealed` | `non-sealed` | `abstract` |
|---|---|---|---|---|
| Controls inheritance | Yes | Yes | Opens inheritance | No |
| Allows subclasses | No | Only permitted | Yes | Yes |
| Controls instantiation | No | No | No | Yes |
| `permits` used | No | Yes/optional in same file | No | No |
| Main purpose | Close class | Control hierarchy | Re-open hierarchy | Prevent direct instantiation |

---

# 30. One-Minute Revision

```text
Sealed class
→ Controls direct inheritance.

permits
→ Lists allowed direct subclasses.

Every direct permitted subclass must be:
→ final
→ sealed
→ non-sealed

final
→ Stop inheritance.

sealed
→ Continue controlled inheritance.

non-sealed
→ Open inheritance again.

permits types
→ Must be direct subclasses/subinterfaces.

Permitted types
→ Must be accessible.
→ Same module for named modules.
→ Same package for unnamed modules.

permits can be omitted
→ When permitted types are declared in the same source file.

sealed interface
→ Controls which classes implement it
   and which interfaces extend it.

record
→ Implicitly final, so it can implement a sealed interface.

Sealed vs final
→ final = zero subclasses
→ sealed = fixed allowed subclasses
```

---

# 31. Strong Interview Answer

> **Sealed classes and interfaces were introduced as a way to control inheritance. A sealed type explicitly defines which types are allowed to directly extend or implement it using `permits`. Every direct permitted subtype must be `final`, `sealed`, or `non-sealed`. `final` closes the branch, `sealed` continues controlled inheritance, and `non-sealed` opens that branch again. This is useful for modeling a closed set of domain types and works well with pattern matching.**

---

# 32. Key Rules to Memorize

```text
1. sealed → controls direct inheritance

2. permits → allowed direct subclasses/subinterfaces

3. permits types must be direct subtypes

4. Direct permitted subtype must be:
   final OR sealed OR non-sealed

5. final → no further subclass

6. sealed → must define/continue permitted hierarchy

7. non-sealed → hierarchy becomes open

8. A sealed interface can permit:
   classes AND interfaces

9. Permitted types must be accessible
   and belong to the same module/package boundary

10. permits can be omitted when permitted types
    are declared in the same source file
```
