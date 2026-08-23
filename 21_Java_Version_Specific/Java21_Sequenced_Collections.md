# Java 21 — SequencedCollection, SequencedSet & SequencedMap

---

# 1. Why Were Sequenced Interfaces Introduced?

Before Java 21, Java had several collections with a defined encounter order, but there was **no common interface** that represented "a collection with a defined order and operations at both ends."

For example:

```text
List
Deque
LinkedHashSet
SortedSet
LinkedHashMap
SortedMap
```

had ordering characteristics, but there was no uniform API for operations such as:

```text
get first
get last
remove first
remove last
reverse the order
```

Java 21 introduced:

```text
SequencedCollection
SequencedSet
SequencedMap
```

These were introduced through **JEP 431**. citeturn0search0

---


---

# 2. New Collection Hierarchy in Java 21

Java 21 introduced three new interfaces through **JEP 431 — Sequenced Collections**:

```text
                    Collection
                        │
          ┌─────────────┼─────────────┐
          │             │             │
         List          Deque          Set
          │             │             │
          └──────┬──────┘             │
                 ↓                    ↓
        SequencedCollection      SequencedSet
                                      │
                                  SortedSet
```

For maps:

```text
                     Map
                      │
                SequencedMap
                      │
                  SortedMap
```

The key idea is that Java now has a common type for collections/maps whose elements or mappings have a **defined encounter order**. Existing collection types were retrofitted into this hierarchy rather than requiring a completely separate collection framework. citeturn0search5turn0search1

### Important implementations

```text
List
 ├── ArrayList
 └── LinkedList
        ↓
 SequencedCollection

LinkedHashSet
        ↓
 SequencedSet

TreeSet
        ↓
 SortedSet → SequencedSet

LinkedHashMap
        ↓
 SequencedMap

TreeMap
        ↓
 SortedMap → SequencedMap
```

### Interview answer

> Java 21 introduced `SequencedCollection`, `SequencedSet`, and `SequencedMap` and retrofitted existing ordered collection types into this hierarchy. This gives different ordered collections a common API for first/last operations and reverse traversal.

---

# 3. Why Were These Interfaces Introduced?

This is an important **"Why Java 21?"** interview question.

Before Java 21, many collections had an encounter order, but there was **no single common interface representing the concept of a sequence**.

For example:

```text
List
→ Has encounter order

LinkedHashSet
→ Has encounter order

LinkedHashMap
→ Has encounter order

TreeSet
→ Has sorted encounter order
```

But the APIs for working with that order were inconsistent.

The Java Collections Framework therefore had a problem:

```text
Different ordered collections
          ↓
Different APIs
          ↓
No common "sequenced" abstraction
```

JEP 431 specifically identifies three related problems:

### Problem 1 — No common abstraction

There was no single interface that meant:

> "This collection has a defined first element, second element, ..., last element."

This made it difficult for APIs to express that requirement precisely. citeturn0search5turn0search3

For example, if a method requires a collection whose order matters, using:

```java
Collection<String>
```

does not communicate that requirement because a `HashSet` can also be passed.

Java 21 allows the API to express the requirement more clearly:

```java
SequencedCollection<String>
```

---

### Problem 2 — First/last operations were inconsistent

Before Java 21, different collection types used different methods.

For example:

```text
List
→ get(0)
→ get(size - 1)

Deque
→ getFirst()
→ getLast()

NavigableSet
→ first()
→ last()
```

There was no common:

```text
getFirst()
getLast()
```

API across all sequenced collections.

Java 21 provides a consistent API:

```java
getFirst();
getLast();
```

and:

```java
addFirst();
addLast();

removeFirst();
removeLast();
```

citeturn0search5turn0search7

---

### Problem 3 — Reverse traversal was inconsistent

Some collections supported reverse iteration or reverse views, while others did not have a convenient/common API.

For example, collections had different approaches such as:

```text
descendingIterator()
descendingSet()
custom iteration
```

Java 21 introduces:

```java
reversed()
```

as a common operation for sequenced collections and maps.

```text
Original
[A, B, C, D]
      ↓
reversed()
      ↓
[D, C, B, A]
```

It provides a reverse-ordered **view**, rather than requiring a separate copy. citeturn0search5turn0search0

---

### Problem 4 — LinkedHashSet was logically ordered but lacked a common end-operation API

`LinkedHashSet` maintains encounter order, but before Java 21 it did not provide the same convenient first/last/reverse operations available on some other collection types.

Java 21 gives it the `SequencedSet` API:

```java
set.getFirst();
set.getLast();
set.addFirst(value);
set.addLast(value);
set.removeFirst();
set.removeLast();
set.reversed();
```

This is one of the practical benefits of the new hierarchy. citeturn0search5

---

# 4. What Problem Are Sequenced Interfaces Actually Solving?

Think of the change as:

### Before Java 21

```text
             Collection
                  │
       ┌──────────┼──────────┐
       ↓          ↓          ↓
      List    LinkedHashSet  Deque
       │          │           │
    get(0)    custom logic   getFirst()
    get(n-1)  custom logic   getLast()
```

There was **no common abstraction** for:

```text
"Give me something that has a defined encounter order."
```

### Java 21

```text
             Collection
                  │
        SequencedCollection
                  │
      ┌───────────┼───────────┐
      ↓           ↓           ↓
     List        Deque    SequencedSet
                              │
                        LinkedHashSet
```

Now the application can depend on:

```java
SequencedCollection<String>
```

and use:

```java
getFirst();
getLast();
reversed();
```

without caring whether the implementation is a `List`, `Deque`, or another sequenced collection.

---

# 5. Interview-Friendly "Why?" Answer

### Question

> Why were `SequencedCollection`, `SequencedSet`, and `SequencedMap` introduced in Java 21?

### Strong short answer

> Before Java 21, several collections had a defined encounter order, but there was no common abstraction for sequenced collections, and operations such as accessing the first/last element and reverse traversal were inconsistent across collection types. Java 21 introduced these interfaces to provide a uniform API for first/last operations and reverse-ordered views.

### Even shorter answer

> They solve the inconsistency around ordered collections by giving them a common hierarchy and common first/last/reverse APIs.

---

# 6. What Changed vs What Was New?

An important interview point is that Java did **not** replace `List`, `Set`, `Map`, etc.

Instead:

```text
Existing Collections
        ↓
Retrofitted into
        ↓
New Sequenced Interfaces
```

For example:

```text
List
  implements/extends
SequencedCollection
```

and:

```text
LinkedHashSet
        ↓
SequencedSet
```

and:

```text
LinkedHashMap
        ↓
SequencedMap
```

So existing collection implementations gain the new common API while preserving their original semantics. citeturn0search5turn0search7


---

# 7. What is a Sequenced Collection?

A **SequencedCollection** represents a collection with a well-defined **encounter order**.

It provides a common API for:

- First element
- Last element
- Add at first
- Add at last
- Remove from first
- Remove from last
- Reverse-ordered view

```text
First
  ↓
[A] [B] [C] [D]
              ↑
             Last
```

The interface is:

```java
interface SequencedCollection<E> extends Collection<E> {

    void addFirst(E e);

    void addLast(E e);

    E getFirst();

    E getLast();

    E removeFirst();

    E removeLast();

    SequencedCollection<E> reversed();
}
```

These operations provide a uniform API across sequenced collection types. citeturn0search0

---

# 3. Important `SequencedCollection` Methods

| Method | Functionality |
|---|---|
| `addFirst(e)` | Adds element at the beginning |
| `addLast(e)` | Adds element at the end |
| `getFirst()` | Returns first element |
| `getLast()` | Returns last element |
| `removeFirst()` | Removes and returns first element |
| `removeLast()` | Removes and returns last element |
| `reversed()` | Returns a reverse-ordered view |

### Example

```java
List<String> names =
        new ArrayList<>(List.of("A", "B", "C"));

names.addFirst("X");
names.addLast("Y");

System.out.println(names);
// [X, A, B, C, Y]

System.out.println(names.getFirst());
// X

System.out.println(names.getLast());
// Y

System.out.println(names.reversed());
// [Y, C, B, A, X]
```

**Note:** `List` is a `SequencedCollection` in Java 21. citeturn0search0

---

# 4. What is `SequencedSet`?

`SequencedSet` is a combination of:

```text
Set
+
SequencedCollection
```

So it has:

- No duplicate elements
- Well-defined encounter order
- First/last operations
- Reverse-ordered view

```java
interface SequencedSet<E>
        extends Set<E>, SequencedCollection<E>
```

Java 21 retrofitted types such as `LinkedHashSet` and `SortedSet` into this hierarchy. citeturn0search0turn0search2

---

# 5. `SequencedSet` Example

```java
LinkedHashSet<String> set =
        new LinkedHashSet<>();

set.add("A");
set.add("B");
set.add("C");

set.addFirst("X");
set.addLast("Y");

System.out.println(set);
// [X, A, B, C, Y]

System.out.println(set.getFirst());
// X

System.out.println(set.getLast());
// Y

System.out.println(set.reversed());
// [Y, C, B, A, X]
```

### Why is this useful?

Before Java 21, repositioning elements in a `LinkedHashSet` was not straightforward.

Java 21 provides:

```java
set.addFirst("X");
set.addLast("Y");
```

and `reversed()`.

For `LinkedHashSet`, if an existing element is added using `addFirst()` or `addLast()`, it can be repositioned to that location. citeturn0search0

---

# 6. What is `SequencedMap`?

`SequencedMap` is a `Map` with a well-defined encounter order.

```text
First Entry
     ↓
A → 10
B → 20
C → 30
     ↑
Last Entry
```

It provides operations for:

- First mapping
- Last mapping
- Adding at first
- Adding at last
- Removing first
- Removing last
- Reverse-ordered view

The interface includes methods such as:

```java
putFirst()
putLast()
firstEntry()
lastEntry()
pollFirstEntry()
pollLastEntry()
reversed()
```

It also provides sequenced views:

```java
sequencedKeySet()
sequencedValues()
sequencedEntrySet()
```

citeturn0search0

---

# 7. `SequencedMap` Example

```java
LinkedHashMap<String, Integer> map =
        new LinkedHashMap<>();

map.put("A", 10);
map.put("B", 20);
map.put("C", 30);

map.putFirst("X", 100);
map.putLast("Y", 200);

System.out.println(map);
// {X=100, A=10, B=20, C=30, Y=200}

System.out.println(map.firstEntry());
// X=100

System.out.println(map.lastEntry());
// Y=200

System.out.println(map.reversed());
// {Y=200, C=30, B=20, A=10, X=100}
```

`LinkedHashMap` implements `SequencedMap` in Java 21. citeturn0search0

---

# 8. Important `SequencedMap` Methods

| Method | Functionality |
|---|---|
| `putFirst(k,v)` | Places mapping at beginning |
| `putLast(k,v)` | Places mapping at end |
| `firstEntry()` | Returns first mapping |
| `lastEntry()` | Returns last mapping |
| `pollFirstEntry()` | Removes and returns first mapping |
| `pollLastEntry()` | Removes and returns last mapping |
| `reversed()` | Returns reverse-ordered view |
| `sequencedKeySet()` | Ordered key-set view |
| `sequencedValues()` | Ordered values view |
| `sequencedEntrySet()` | Ordered entry-set view |

---

# 9. Java 21 Collection Hierarchy

The important hierarchy is:

```text
Collection
    │
    ├── List
    │     └── SequencedCollection
    │
    ├── Deque
    │     └── SequencedCollection
    │
    ├── Set
    │     └── SequencedSet
    │           └── SortedSet
    │
    └── ...

Map
    │
    └── SequencedMap
          └── SortedMap
```

More precisely, Java 21 retrofitted the hierarchy so:

```text
List ───────────────→ SequencedCollection
Deque ──────────────→ SequencedCollection

LinkedHashSet ──────→ SequencedSet
SortedSet ──────────→ SequencedSet

LinkedHashMap ──────→ SequencedMap
SortedMap ──────────→ SequencedMap
```

citeturn0search0turn0search1

---

# 10. `reversed()` — Very Important

`reversed()` returns a **reverse-ordered view**.

Example:

```java
List<String> list =
        new ArrayList<>(List.of("A", "B", "C"));

SequencedCollection<String> reversed =
        list.reversed();

System.out.println(reversed);
// [C, B, A]
```

### Important: It is a view

It is not simply a new independent copy.

Conceptually:

```text
Original
[A, B, C]
     │
     │ reversed()
     ↓
View
[C, B, A]
```

Changes to the underlying collection can be visible through the reversed view, depending on the collection implementation. The API defines it as a reverse-ordered view. citeturn0search0

---

# 11. `reversed()` vs `Collections.reverse()`

This is a useful interview comparison.

### `Collections.reverse(list)`

```java
Collections.reverse(list);
```

- Modifies the original list.
- Reorders the list itself.

### `reversed()`

```java
list.reversed();
```

- Returns a reverse-ordered view.
- The original collection's encounter order is not simply replaced by the reversed view.

```text
Collections.reverse()

[A, B, C]
    ↓
[C, B, A]


reversed()

[A, B, C]
    │
    └──→ reverse view [C, B, A]
```

---

# 12. SequencedCollection vs List

A `List` already has an order.

So why introduce `SequencedCollection`?

Because Java needed a **common abstraction** for all collections that have a defined encounter order.

For example:

```text
List
Deque
```

can both expose:

```text
getFirst()
getLast()
addFirst()
addLast()
removeFirst()
removeLast()
reversed()
```

through a common interface.

### Interview answer

> `SequencedCollection` provides a common ordered-collection abstraction and uniform first/last/reverse operations across types such as `List` and `Deque`.

---

# 13. SequencedSet vs Set

A normal `Set` only guarantees uniqueness.

```text
Set
→ No duplicates
```

A `SequencedSet` guarantees:

```text
No duplicates
+
Defined encounter order
```

```text
Set
→ uniqueness

SequencedSet
→ uniqueness + order
```

### Examples

```text
HashSet
→ Set
→ no guaranteed encounter order

LinkedHashSet
→ SequencedSet
→ insertion/encounter order

TreeSet
→ SequencedSet
→ sorted encounter order
```

---

# 14. SequencedMap vs Map

A normal `Map` provides:

```text
Key → Value
```

but `Map` itself does not define a common encounter-order API.

`SequencedMap` adds:

```text
Map
+
Defined encounter order
+
First/last operations
+
Reverse view
```

### Example

```text
LinkedHashMap
→ predictable encounter order
→ SequencedMap
```

---

# 20. Important Interview Question

### Q: Is `HashSet` a `SequencedSet`?

**No.**

`HashSet` does not define a predictable encounter order.

```text
HashSet
→ Set
→ no defined encounter order
```

Whereas:

```text
LinkedHashSet
→ SequencedSet
→ defined encounter order
```

---

# 16. Important Interview Question

### Q: Is `HashMap` a `SequencedMap`?

**No.**

`HashMap` does not provide a defined encounter order.

```text
HashMap
→ Map
```

Whereas:

```text
LinkedHashMap
→ SequencedMap
```

---

# 17. `SortedSet` and `SortedMap`

This is a subtle point.

Java 21 makes:

```text
SortedSet → SequencedSet
SortedMap → SequencedMap
```

However, sorted collections determine their order using sorting rules.

For example:

```java
TreeSet<Integer> set =
        new TreeSet<>();

set.add(30);
set.add(10);
set.add(20);
```

The encounter order is:

```text
[10, 20, 30]
```

But explicit positioning operations such as:

```java
set.addFirst(...)
set.addLast(...)
```

are not meaningful for a sorted set and throw `UnsupportedOperationException`. citeturn0search0

### Interview point

> `SortedSet` is sequenced in Java 21, but its ordering is controlled by its sorting rules, so explicit `addFirst()`/`addLast()` positioning is unsupported.

---

# 18. Common Use Case — LRU Cache

`SequencedMap` is particularly interesting for ordered map use cases.

For example:

```text
LRU Cache

Least Recently Used
        ↓
[A, B, C, D]
 ↑           ↑
First       Last
```

Having first/last operations makes ordered map manipulation easier.

A `LinkedHashMap` is already commonly used for LRU-style behavior, and Java 21's `SequencedMap` gives a common API for ordered map operations.

---

# 19. Quick Comparison

| Interface | Extends | Main Purpose |
|---|---|---|
| `SequencedCollection` | `Collection` | Ordered collection with first/last/reverse operations |
| `SequencedSet` | `Set`, `SequencedCollection` | Ordered collection with unique elements |
| `SequencedMap` | `Map` | Ordered key-value mappings |

---

# 20. Java 21 — Before vs After

### Before Java 21

Different collection types often needed different APIs:

```text
List
→ get(0)
→ get(size - 1)

Deque
→ getFirst()
→ getLast()

NavigableSet
→ first()
→ last()
→ descendingSet()

LinkedHashMap
→ custom handling for order
```

### Java 21

Common sequenced APIs:

```text
getFirst()
getLast()
addFirst()
addLast()
removeFirst()
removeLast()
reversed()
```

For maps:

```text
putFirst()
putLast()
firstEntry()
lastEntry()
pollFirstEntry()
pollLastEntry()
reversed()
```

This provides a more consistent Collections Framework API. citeturn0search0

---

# 21. Common Interview Questions

### Q1. What was introduced in Java 21 for ordered collections?

> `SequencedCollection`, `SequencedSet`, and `SequencedMap`.

### Q2. Why were these interfaces introduced?

> To provide a common abstraction and uniform API for collections and maps with a defined encounter order.

### Q3. What is `SequencedCollection`?

> A collection with a well-defined encounter order that supports operations at both ends and reverse traversal.

### Q4. What is `SequencedSet`?

> A `Set` that also has a defined encounter order.

### Q5. What is `SequencedMap`?

> A `Map` with a defined encounter order and operations for the first and last mappings.

### Q6. Is `HashSet` a `SequencedSet`?

> No. `HashSet` does not define a predictable encounter order.

### Q7. Is `LinkedHashSet` a `SequencedSet`?

> Yes.

### Q8. Is `LinkedHashMap` a `SequencedMap`?

> Yes.

### Q9. What does `reversed()` return?

> A reverse-ordered view of the original collection/map.

### Q10. Does `reversed()` create a copy?

> No. It provides a reverse-ordered view.

### Q11. Is `TreeSet` a `SequencedSet` in Java 21?

> Yes. `SortedSet` extends `SequencedSet`, and `TreeSet` implements `NavigableSet`/`SortedSet`.

### Q12. Can `TreeSet` use `addFirst()`?

> No. A sorted set cannot support explicit first/last positioning, so these operations throw `UnsupportedOperationException`.

### Q13. What is the biggest benefit of Sequenced interfaces?

> A uniform API for first, last, insertion/removal at both ends, and reverse-order processing across different ordered collection types.

---

# 22. One-Minute Revision

```text
Java 21
   ↓
JEP 431
   ↓
SequencedCollection
SequencedSet
SequencedMap
```

```text
SequencedCollection
→ Ordered collection
→ addFirst / addLast
→ getFirst / getLast
→ removeFirst / removeLast
→ reversed
```

```text
SequencedSet
→ Set + SequencedCollection
→ Unique + ordered
```

```text
SequencedMap
→ Map + encounter order
→ putFirst / putLast
→ firstEntry / lastEntry
→ pollFirstEntry / pollLastEntry
→ reversed
```

```text
LinkedHashSet
→ SequencedSet

TreeSet
→ SequencedSet

LinkedHashMap
→ SequencedMap

HashSet
→ NOT SequencedSet

HashMap
→ NOT SequencedMap
```

### Interview one-liner

> **Java 21 introduced SequencedCollection, SequencedSet, and SequencedMap to provide a common API for collections and maps with a defined encounter order, including first/last operations and reverse-ordered views.**
