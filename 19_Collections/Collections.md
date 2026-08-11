# Java Collections Framework --- Interview Notes

> Complete, interview-focused notes for Core Java / 2--3 YOE interviews.

## 1. Java Collections Framework

The **Java Collections Framework (JCF)** is a set of interfaces,
implementations, and utility classes used to store and manipulate groups
of objects.

Main interfaces/classes:

-   `Iterable`
-   `Collection`
-   `List`
-   `Set`
-   `Queue`
-   `Deque`
-   `Map` (separate from `Collection`)
-   `ArrayList`
-   `LinkedList`
-   `Vector`
-   `Stack`
-   `PriorityQueue`
-   `ArrayDeque`
-   `HashMap`
-   `LinkedHashMap`
-   `TreeMap`
-   `HashSet`
-   `LinkedHashSet`
-   `TreeSet`

### Why Collections?

Arrays have a fixed size:

``` java
int[] numbers = new int[5];
```

Collections provide dynamic sizing and ready-made data structures.

``` java
List<Integer> numbers = new ArrayList<>();
numbers.add(10);
numbers.add(20);
```

------------------------------------------------------------------------

# 2. Collections Hierarchy

Simplified hierarchy:

``` text
Iterable
   |
Collection
   |
   +-- List
   |    +-- ArrayList
   |    +-- LinkedList
   |    +-- Vector
   |         +-- Stack
   |
   +-- Set
   |    +-- HashSet
   |    +-- LinkedHashSet
   |    +-- SortedSet
   |         +-- NavigableSet
   |              +-- TreeSet
   |
   +-- Queue
        +-- PriorityQueue
        +-- Deque
             +-- ArrayDeque
             +-- LinkedList

Map                         <-- NOT a Collection
 |
 +-- HashMap
 +-- LinkedHashMap
 +-- SortedMap
      +-- NavigableMap
           +-- TreeMap
```

### Interview Point

`Map` is **not** a child of `Collection`. It stores key-value pairs,
while `Collection` represents groups of individual elements.

------------------------------------------------------------------------

# 3. Iterable Interface

`Iterable` is the root interface for objects that can be iterated.

Important method:

``` java
Iterator<T> iterator();
```

It enables the enhanced `for-each` loop:

``` java
List<String> names = List.of("A", "B", "C");

for (String name : names) {
    System.out.println(name);
}
```

------------------------------------------------------------------------

# 4. Collection Interface

`Collection` extends `Iterable`.

It represents a group of objects.

Common methods:

| Method                          | Usage                                                                 |
| ------------------------------- | --------------------------------------------------------------------- |
| `size()`                        | Returns the total number of elements present in the collection.       |
| `isEmpty()`                     | Checks if the collection is empty or has some value. Returns `true`/`false`. |
| `contains(Object o)`            | Searches for an element in the collection. Returns `true`/`false`.    |
| `toArray()`                     | Converts the collection into an array.                                |
| `add(E e)`                      | Inserts an element at the end of the collection.                      |
| `remove(Object o)`              | Removes the first occurrence of the element from the collection.      |
| `addAll(Collection<? extends E> c)` | Inserts all elements of the specified collection at the end.   |
| `removeAll(Collection<?> c)`     | Removes all elements from this collection that are present in the given collection. |
| `retainAll(Collection<?> c)`     | Keeps only the elements that are present in the given collection.     |
| `containsAll(Collection<?> c)`  | Checks whether all elements of another collection exist.             |
| `clear()`                       | Removes all elements from the collection.                             |
| `equals(Object o)`              | Checks if two collections are equal.                                  |
| `stream()` / `parallelStream()` | Provides an effective way to work with collection data using streams. |
| `iterator()`                    | Returns an iterator used to traverse the collection.                  |

Example:

``` java
Collection<String> names = new ArrayList<>();

names.add("Alice");
names.add("Bob");

System.out.println(names.size());
```

Main child interfaces:

-   `List`
-   `Set`
-   `Queue`

------------------------------------------------------------------------

# 5. Iterator Interface

`Iterator` provides a standard way to traverse a collection.

Important methods:

``` java
hasNext()
next()
remove()
```

Example:

``` java
Iterator<Integer> iterator = numbers.iterator();

while (iterator.hasNext()) {
    Integer number = iterator.next();
    System.out.println(number);
}
```

### Safe removal

``` java
Iterator<Integer> iterator = numbers.iterator();

while (iterator.hasNext()) {
    Integer number = iterator.next();

    if (number == 20) {
        iterator.remove();
    }
}
```

Do not structurally modify a collection directly while iterating with a
normal iterator.

------------------------------------------------------------------------

# 6. Iterator vs ListIterator

  Iterator                           ListIterator
  ---------------------------------- ------------------------
  Works with collections generally   Only works with `List`
  Forward traversal                  Forward + backward
  `hasNext()`                        `hasNext()`
  `next()`                           `next()`
  `remove()`                         `remove()`
  No `add()`                         Supports `add()`
  No `set()`                         Supports `set()`

### ListIterator methods

Forward iteration

- `hasNext()` — returns `true` if there is a next element.
- `next()` — returns the next element and advances the cursor.
- `nextIndex()` — returns the index of the element that would be returned by `next()`.

Backward iteration

- `hasPrevious()` — returns `true` if there is a previous element.
- `previous()` — returns the previous element and moves the cursor backward.
- `previousIndex()` — returns the index of the element that would be returned by `previous()`.

Modification methods

- `remove()` — removes the last element returned by `next()` or `previous()`.
- `set(E e)` — replaces the last returned element with the specified element.
- `add(E e)` — inserts the specified element at the current position.


Example:

``` java
ListIterator<String> it = list.listIterator();

while (it.hasNext()) {
    System.out.println(it.next());
}

while (it.hasPrevious()) {
    System.out.println(it.previous());
}
```

------------------------------------------------------------------------

# 7. List Interface

`List` represents an ordered collection.

Characteristics:

-   Maintains order
-   Allows duplicates
-   Supports index-based access
-   Common implementations: `ArrayList`, `LinkedList`, `Vector`

Example:

``` java
List<String> names = new ArrayList<>();

names.add("Alice");
names.add("Bob");
names.add("Alice");
```

Result contains both `"Alice"` values.

------------------------------------------------------------------------

### Common `List` methods

| Method                          | Functionality                                                                 |
| ------------------------------- | ----------------------------------------------------------------------------- |
| `add(int index, E element)`     | Inserts an element at the specific position and shifts later elements right.   |
| `addAll(int index, Collection<? extends E> c)` | Inserts all elements from the given collection starting at the specified index. |
| `replaceAll(UnaryOperator<E> operator)` | Replaces each element with the result of applying the operator.            |
| `sort(Comparator<? super E> c)` | Sorts the list by the given comparator.                                      |
| `get(int index)`                | Returns the element at the specified position in the list.                  |
| `set(int index, E element)`     | Replaces the element at the specified index.                                 |
| `remove(int index)`             | Removes the element at the specified index and shifts remaining elements left. |
| `indexOf(Object o)`             | Returns the index of the first occurrence of the specified element, or -1 if not found. |
| `lastIndexOf(Object o)`         | Returns the index of the last occurrence of the specified element, or -1 if not found. |
| `listIterator()`                | Returns a `ListIterator` for bidirectional traversal of the list.           |
| `listIterator(int index)`       | Returns a `ListIterator` starting at the specified index.                   |
| `subList(int fromIndex, int toIndex)` | Returns a view of the portion of the list between `fromIndex` (inclusive) and `toIndex` (exclusive). |

------------------------------------------------------------------------

# 8. ArrayList

`ArrayList` is a resizable-array implementation of `List`.

``` java
List<Integer> numbers = new ArrayList<>();
```

Characteristics:

-   Maintains insertion order
-   Allows duplicates
-   Allows `null`
-   Fast random/index access
-   Not synchronized
-   Backed by a dynamic array

### Important Methods

``` java
| Method                  | Functionality                             |
| ----------------------- | ----------------------------------------- |
| `add(E e)`              | Adds an element at the end                |
| `add(int index, E e)`   | Adds an element at a specific index       |
| `get(int index)`        | Returns the element at the given index    |
| `set(int index, E e)`   | Replaces the element at the given index   |
| `remove(int index)`     | Removes the element at the given index    |
| `remove(Object o)`      | Removes the first matching element        |
| `indexOf(Object o)`     | Returns the index of the first occurrence |
| `lastIndexOf(Object o)` | Returns the index of the last occurrence  |
| `subList(from, to)`     | Returns a portion/view of the list        |
| `sort(Comparator)`      | Sorts the list                            |

```

Example:

``` java
List<String> names = new ArrayList<>();

names.add("A");
names.add("B");

System.out.println(names.get(0));

names.set(1, "C");
names.remove("A");
```

### Internal Working

Conceptually:

``` text
Object[]
[10][20][30][ ][ ][ ]
```

When capacity is insufficient, a larger internal array is allocated and
elements are copied.

Current OpenJDK implementations typically grow capacity by about 1.5x,
but the exact growth strategy is an implementation detail.

### Complexity

  Operation                      Typical Complexity
  ---------------------------- --------------------
  `get(index)`                                 O(1)
  `set(index)`                                 O(1)
  Add at end                         O(1) amortized
  Insert at beginning/middle                   O(n)
  Remove by index                              O(n)
  Search / `contains()`                        O(n)

------------------------------------------------------------------------

# 9. LinkedList

`LinkedList` is a doubly linked list and implements:

``` text
List
Queue
Deque
```

Conceptually:

``` text
[A] <-> [B] <-> [C] <-> [D]
```

Characteristics:

-   Maintains insertion order
-   Allows duplicates
-   Allows `null`
-   O(n) random/index access
-   O(1) operations at the ends

### Complexity

  Operation                                           Typical Complexity
  ------------------------------------------------- --------------------
  `get(index)`                                                      O(n)
  Search                                                            O(n)
  Add/remove at ends                                                O(1)
  Add/remove once node/iterator position is known                   O(1)
  Add/remove by index                                               O(n)

### Interview Trap

Do **not** say "LinkedList insertion is always O(1)."

Finding the required position can take O(n).

------------------------------------------------------------------------

# 10. ArrayList vs LinkedList

  -----------------------------------------------------------------------
  ArrayList                           LinkedList
  ----------------------------------- -----------------------------------
  Dynamic array                       Doubly linked list

  `get()` O(1)                        `get()` O(n)

  Better cache locality               More node/memory overhead

  Insert/remove requires shifting     Link changes are cheap once
  when in middle                      position is reached

  Usually preferred for general lists Useful for deque/end-operation use
                                      cases
  -----------------------------------------------------------------------

### Practical Choice

For most normal list usage, choose `ArrayList`.

------------------------------------------------------------------------

# 11. Vector

`Vector` is a legacy resizable-array implementation.

Characteristics:

-   Similar to `ArrayList`
-   Methods are synchronized
-   Generally slower due to synchronization overhead
-   Legacy API

``` java
Vector<Integer> numbers = new Vector<>();
numbers.add(10);
```

### Interview Point

Prefer `ArrayList` for modern non-concurrent code.

------------------------------------------------------------------------

# 12. Stack

`Stack` is a legacy class extending `Vector`.

It follows:

``` text
LIFO = Last In, First Out
```

``` java
Stack<Integer> stack = new Stack<>();

stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.pop()); // 30
```

Methods:

``` java
push()
pop()
peek()
empty()
search()
```

### Modern Alternative

Prefer:

``` java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);

stack.pop();
stack.peek();
```

------------------------------------------------------------------------

# 13. Queue Interface

A `Queue` is generally used for processing elements in an ordered
manner.

Typical behavior:

``` text
FIFO
First In → First Out
```

Common implementations:

-   `ArrayDeque`
-   `LinkedList`
-   `PriorityQueue`

## Queue Method

| Method       | Functionality                                                   |
| ------------ | --------------------------------------------------------------- |
| `add(E e)`   | Adds an element; may throw an exception if insertion fails      |
| `offer(E e)` | Adds an element; returns `false` if insertion fails             |
| `remove()`   | Removes and returns the head; throws exception if empty         |
| `poll()`     | Removes and returns the head; returns `null` if empty           |
| `element()`  | Returns the head without removing it; throws exception if empty |
| `peek()`     | Returns the head without removing it; returns `null` if empty   |

## Queue Method Pairs

  Operation   Throws exception   Returns special value
  ----------- ------------------ -----------------------
  Insert      `add()`            `offer()`
  Remove      `remove()`         `poll()`
  Examine     `element()`        `peek()`

Example:

``` java
Queue<Integer> queue = new ArrayDeque<>();

queue.offer(10);
queue.offer(20);
queue.offer(30);

System.out.println(queue.peek()); // 10
System.out.println(queue.poll()); // 10
```

------------------------------------------------------------------------

# 14. PriorityQueue

`PriorityQueue` is a heap-based priority queue.

By default it behaves as a **min-heap**.

``` java
PriorityQueue<Integer> queue = new PriorityQueue<>();

queue.offer(30);
queue.offer(10);
queue.offer(20);

System.out.println(queue.poll()); // 10
```

### Important

Iteration over a `PriorityQueue` does **not** guarantee sorted order.

Repeated `poll()` operations return elements according to priority.

### Complexity

  Operation     Complexity
  ----------- ------------
  `peek()`            O(1)
  `offer()`       O(log n)
  `poll()`        O(log n)
  Search              O(n)

### Max-Heap

``` java
PriorityQueue<Integer> maxHeap =
        new PriorityQueue<>(Comparator.reverseOrder());
```

------------------------------------------------------------------------

# 15. Comparable vs Comparator

Very common interview question.

## Comparable

Defines the class's **natural ordering**.

``` java
class Employee implements Comparable<Employee> {

    private int salary;

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.salary, other.salary);
    }
}
```

Method:

``` java
compareTo()
```

Package:

``` text
java.lang
```

## Comparator

Defines an **external/custom ordering**.

``` java
Comparator<Employee> byName =
        Comparator.comparing(Employee::getName);
```

Package:

``` text
java.util
```

### Comparison

  Comparable                     Comparator
  ------------------------------ ---------------------------
  Natural ordering               Custom ordering
  Implemented by class           Separate comparator
  `compareTo()`                  `compare()`
  `java.lang`                    `java.util`
  Usually one natural ordering   Can create many orderings

### Result

``` text
negative → first object comes before second
zero     → equal in ordering
positive → first object comes after second
```

Prefer:

``` java
Integer.compare(a, b);
```

instead of:

``` java
a - b;
```

because subtraction can overflow.

------------------------------------------------------------------------

# 16. Deque

`Deque` means **Double Ended Queue**.

It allows insertion and removal from both ends.

``` text
Front                  Rear
  ↓                      ↓
[10] [20] [30] [40]
```

Important methods:

``` java
| Method            | Functionality                                    |
| ----------------- | ------------------------------------------------ |
| `addFirst(E e)`   | Adds element at the front                        |
| `addLast(E e)`    | Adds element at the rear                         |
| `offerFirst(E e)` | Adds element at the front                        |
| `offerLast(E e)`  | Adds element at the rear                         |
| `removeFirst()`   | Removes and returns the first element            |
| `removeLast()`    | Removes and returns the last element             |
| `pollFirst()`     | Removes first element; returns `null` if empty   |
| `pollLast()`      | Removes last element; returns `null` if empty    |
| `peekFirst()`     | Returns first element without removing           |
| `peekLast()`      | Returns last element without removing            |
| `push(E e)`       | Adds element at the front — Stack operation      |
| `pop()`           | Removes element from the front — Stack operation |
```

------------------------------------------------------------------------

# 17. ArrayDeque

`ArrayDeque` is a resizable-array implementation of `Deque`.

Characteristics:

-   Efficient at both ends
-   Can act as Queue
-   Can act as Stack
-   Not synchronized
-   Does not allow `null`

### As Queue

``` java
Queue<Integer> queue = new ArrayDeque<>();

queue.offer(10);
queue.offer(20);

queue.poll();
```

### As Stack

``` java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);

stack.pop(); // 20
```

### Interview Recommendation

For stack/queue/deque operations, `ArrayDeque` is usually preferred over
legacy `Stack` or `LinkedList` when their extra features are not
required.

## `doubleCapacity()` in ArrayDeque

- `ArrayDeque` internally uses a **circular array**.
- When the array becomes full, it needs to **resize/grow**.
- `doubleCapacity()` creates a larger array, typically **doubling the capacity** in implementations that use this method.
- Existing elements are copied to the new array while maintaining their order.
- `head` and `tail` are then adjusted for the new array.

### Example

```text
Old capacity = 8
       ↓
Array becomes full
       ↓
New capacity = 16
       ↓
Copy elements
       ↓
Continue insertion

------------------------------------------------------------------------

# 18. ArrayDeque vs LinkedList

  -----------------------------------------------------------------------
  ArrayDeque                          LinkedList
  ----------------------------------- -----------------------------------
  Resizable array                     Doubly linked list

  Implements `Deque`                  Implements `List`, `Queue`, `Deque`

  Does not allow `null`               Allows `null`

  Efficient end operations            Efficient end operations

  Usually preferred for pure          Useful when List behavior is also
  deque/stack use                     needed
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 19. Map

A `Map` stores key-value pairs.

``` text
Key → Value
```
## Map Methods

```
| Method                                | Functionality                                       |
| ------------------------------------- | --------------------------------------------------- |
| `put(K key, V value)`                 | Adds a key-value pair or updates an existing key    |
| `putIfAbsent(K key, V value)`         | Adds the pair only if the key doesn't already exist |
| `get(K key)`                          | Returns the value associated with a key             |
| `getOrDefault(K key, V defaultValue)` | Returns value if present, otherwise default value   |
| `containsKey(K key)`                  | Checks whether a key exists                         |
| `containsValue(V value)`              | Checks whether a value exists                       |
| `remove(K key)`                       | Removes the mapping for a key                       |
| `replace(K key, V value)`             | Replaces the value for an existing key              |
| `compute()`                           | Computes/updates a value for a key                  |
| `computeIfAbsent()`                   | Computes a value only when the key is absent        |
| `computeIfPresent()`                  | Computes a value only when the key is present       |
| `merge()`                             | Combines an existing value with a new value         |
| `keySet()`                            | Returns all keys as a `Set`                         |
| `values()`                            | Returns all values as a `Collection`                |
| `entrySet()`                          | Returns all key-value pairs                         |
| `size()`                              | Returns the number of key-value mappings            |
| `isEmpty()`                           | Checks whether the map is empty                     |
| `clear()`                             | Removes all mappings                                |
```
Example:

``` java
Map<Integer, String> students = new HashMap<>();

students.put(1, "Alice");
students.put(2, "Bob");

System.out.println(students.get(1));
```

Output:

``` text
Alice
```

Important:

-   Keys are unique
-   Values can be duplicated
-   `Map` is not a `Collection`

------------------------------------------------------------------------

# 20. HashMap

`HashMap` provides hash-based key-value storage.

Typical characteristics:

-   Average O(1) `put`
-   Average O(1) `get`
-   Average O(1) `remove`
-   One `null` key
-   Multiple `null` values
-   No guaranteed iteration order
-   Not synchronized

``` java
Map<String, Integer> marks = new HashMap<>();

marks.put("Alice", 90);
marks.put("Bob", 85);

System.out.println(marks.get("Alice"));
```

------------------------------------------------------------------------

# 21. HashMap Internal Design

Conceptually:

``` text
HashMap
   |
Bucket Array
   |
   +-- Bucket 0
   +-- Bucket 1
   +-- Bucket 2
   +-- ...
```

For:

``` java
map.put(key, value);
```

Simplified process:

``` text
key
 ↓
hashCode()
 ↓
hash spreading
 ↓
bucket index
 ↓
find/insert entry
```

For `get()` the same key hashing process identifies the bucket, then key
comparison identifies the correct entry.

------------------------------------------------------------------------

# 22. Why HashMap Needs `hashCode()` and `equals()`

`hashCode()` helps locate the bucket.

`equals()` determines whether the keys are actually equal.

Contract:

``` text
If a.equals(b) is true
→ a.hashCode() == b.hashCode() must be true
```

But:

``` text
Same hashCode does NOT mean objects are equal.
```

Different objects can have the same hash code.

------------------------------------------------------------------------

# 23. Hash Collision

A collision occurs when different keys end up in the same bucket.

``` text
Key A ─┐
       ├──> Bucket 5
Key B ─┘
```

HashMap handles collisions internally.

Modern implementations can transform a heavily populated collision
bucket into a balanced tree when certain conditions are met.

------------------------------------------------------------------------

# 24. HashMap Treeification

Common OpenJDK interview thresholds:

-   Treeify threshold: `8`
-   Untreeify threshold: `6`
-   Minimum capacity for treeification: `64`

These are **implementation details**, not guarantees of the `Map` API.

Good interview answer:

> HashMap can convert a heavily-collided bucket into a tree structure to
> improve lookup performance.

------------------------------------------------------------------------

# 25. HashMap Load Factor

Default load factor:

``` text
0.75
```

Conceptually:

``` text
threshold = capacity × load factor
```

For capacity 16:

``` text
16 × 0.75 = 12
```

When the threshold is reached, HashMap resizes.

A lower load factor generally means fewer collisions but more memory
usage.

------------------------------------------------------------------------

# 26. HashMap Resizing

When the map grows beyond its threshold:

``` text
old table
   ↓
resize
   ↓
larger table
   ↓
entries redistributed
```

Common capacity progression:

``` text
16 → 32 → 64 → 128 ...
```

HashMap implementations use power-of-two table sizes.

------------------------------------------------------------------------

# 27. Mutable Keys in HashMap

Avoid mutable objects as keys when fields used by `equals()` /
`hashCode()` can change.

Bad:

``` java
Map<Employee, String> map = new HashMap<>();

Employee e = new Employee(1);

map.put(e, "Developer");

// Changing a hashCode-related field
e.setId(2);
```

The entry may no longer be found using the expected hash bucket.

### Best Practice

Use immutable keys such as:

``` text
String
Integer
Long
UUID
```

------------------------------------------------------------------------

# 28. LinkedHashMap

`LinkedHashMap` combines hash-based lookup with predictable iteration
order.

By default, it maintains **insertion order**.

``` java
Map<Integer, String> map = new LinkedHashMap<>();

map.put(3, "C");
map.put(1, "A");
map.put(2, "B");

System.out.println(map);
```

Output:

``` text
{3=C, 1=A, 2=B}
```

It can also be configured for **access order**:

``` java
new LinkedHashMap<>(16, 0.75f, true);
```

Access-order mode is useful for LRU-style cache designs.

------------------------------------------------------------------------

# 29. TreeMap

`TreeMap` stores keys in sorted order.

It is based on a self-balancing tree.

``` java
Map<Integer, String> map = new TreeMap<>();

map.put(30, "C");
map.put(10, "A");
map.put(20, "B");

System.out.println(map);
```

Output:

``` text
{10=A, 20=B, 30=C}
```

### Complexity

``` text
get()    → O(log n)
put()    → O(log n)
remove() → O(log n)
```

Useful methods:

``` java
firstKey()
lastKey()
lowerKey()
higherKey()
floorKey()
ceilingKey()
subMap()
headMap()
tailMap()
```

------------------------------------------------------------------------

# 30. HashMap vs LinkedHashMap vs TreeMap

  ------------------------------------------------------------------------
  Feature           HashMap           LinkedHashMap      TreeMap
  ----------------- ----------------- ------------------ -----------------
  Ordering          No guaranteed     Insertion/access   Sorted key order
                    order             order              

  Get/Put           O(1) average      O(1) average       O(log n)

  Null key          One               One                Generally no
                                                         under natural
                                                         ordering

  Main use          Fast lookup       Lookup +           Sorted
                                      predictable order  keys/navigation
  ------------------------------------------------------------------------

------------------------------------------------------------------------

# 31. Set Interface

A `Set` stores **unique elements**.

Common implementations:

``` text
HashSet
LinkedHashSet
TreeSet
```

Example:

``` java
Set<Integer> numbers = new HashSet<>();

numbers.add(10);
numbers.add(20);
numbers.add(10);
```

Only one `10` remains.

------------------------------------------------------------------------

# 32. HashSet

`HashSet` is hash-based.

Characteristics:

-   Unique elements
-   Average O(1) add/search/remove
-   One `null` element
-   No guaranteed iteration order
-   Not synchronized
-   Common implementation is backed internally by a `HashMap`

------------------------------------------------------------------------

# 33. LinkedHashSet

`LinkedHashSet` provides:

-   Unique elements
-   Hash-based lookup
-   Insertion-order iteration

``` java
Set<Integer> numbers = new LinkedHashSet<>();

numbers.add(30);
numbers.add(10);
numbers.add(20);
```

Iteration order:

``` text
30
10
20
```

------------------------------------------------------------------------

# 34. TreeSet

`TreeSet` stores unique elements in sorted order.

``` java
Set<Integer> numbers = new TreeSet<>();

numbers.add(30);
numbers.add(10);
numbers.add(20);

System.out.println(numbers);
```

Output:

``` text
[10, 20, 30]
```

Typical operations:

``` text
add()    → O(log n)
remove() → O(log n)
contains → O(log n)
```

It is based on a balanced tree structure.

------------------------------------------------------------------------

# 35. HashSet vs LinkedHashSet vs TreeSet

  -----------------------------------------------------------------------
  Feature           HashSet           LinkedHashSet     TreeSet
  ----------------- ----------------- ----------------- -----------------
  Duplicates        No                No                No

  Order             No guarantee      Insertion order   Sorted

  Add/Search        O(1) average      O(1) average      O(log n)

  Null              One allowed       One allowed       Generally no null
                                                        under natural
                                                        ordering

  Main use          Fast uniqueness   Uniqueness +      Sorted unique
                                      insertion order   elements
  -----------------------------------------------------------------------

------------------------------------------------------------------------

# 36. Collection vs Collections

Very common question.

## Collection

`Collection` is an **interface**.

``` java
Collection<String> names;
```

It defines common operations for groups of objects.

## Collections

`Collections` is a **utility class** containing static methods.

``` java
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
Collections.max(list);
Collections.min(list);
```

### Memory Trick

``` text
Collection  → Interface
Collections → Utility class
```

------------------------------------------------------------------------

# 37. Collections Utility Methods

Common methods:

``` java
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
Collections.max(list);
Collections.min(list);
Collections.frequency(list, value);
Collections.binarySearch(list, value);
```

Example:

``` java
List<Integer> numbers =
        new ArrayList<>(List.of(30, 10, 20));

Collections.sort(numbers);

System.out.println(numbers);
```

------------------------------------------------------------------------

# 38. Arrays vs Collections

`Arrays` is a utility class for arrays:

``` java
Arrays.sort(array);
Arrays.binarySearch(array, value);
```

`Collections` is a utility class for collections:

``` java
Collections.sort(list);
Collections.reverse(list);
```

------------------------------------------------------------------------

# 39. Generics in Collections

Prefer generics:

``` java
List<String> names = new ArrayList<>();
```

This provides compile-time type safety.

Avoid raw types:

``` java
List list = new ArrayList();
```

Collections use reference types, so primitive types need wrappers:

``` java
List<Integer> numbers = new ArrayList<>();
```

Autoboxing/unboxing happens automatically:

``` java
numbers.add(10);       // int → Integer
int x = numbers.get(0); // Integer → int
```

------------------------------------------------------------------------

# 40. Map Methods

Important methods:

``` java
put()
putIfAbsent()
get()
getOrDefault()
containsKey()
containsValue()
remove()
replace()
compute()
computeIfAbsent()
computeIfPresent()
merge()
size()
isEmpty()
clear()
```

Example:

``` java
Map<String, Integer> map = new HashMap<>();

map.put("A", 10);
map.putIfAbsent("A", 20);

System.out.println(map.get("A")); // 10
```

------------------------------------------------------------------------

# 41. Iterating a Map

### `entrySet()` --- preferred when both key and value are needed

``` java
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(
        entry.getKey() + " " + entry.getValue()
    );
}
```

### `keySet()`

``` java
for (String key : map.keySet()) {
    System.out.println(key);
}
```

### `values()`

``` java
for (Integer value : map.values()) {
    System.out.println(value);
}
```

------------------------------------------------------------------------

# 42. Map Key Uniqueness

A map cannot contain duplicate keys.

``` java
Map<Integer, String> map = new HashMap<>();

map.put(1, "A");
map.put(1, "B");
```

Result:

``` text
{1=B}
```

The second value replaces the first.

Duplicate values are allowed:

``` java
map.put(1, "A");
map.put(2, "A");
```

------------------------------------------------------------------------

# 43. HashMap vs Hashtable

`Hashtable` is a legacy synchronized map.

  HashMap               Hashtable
  --------------------- ----------------
  Not synchronized      Synchronized
  Allows one null key   No null key
  Allows null values    No null values
  Modern/common         Legacy

For modern concurrent maps, consider `ConcurrentHashMap`.

------------------------------------------------------------------------

# 44. Comparable / Comparator in Collections

Sorting a list naturally:

``` java
Collections.sort(employees);
```

requires a natural ordering, typically through `Comparable`.

Custom ordering:

``` java
employees.sort(
    Comparator.comparing(Employee::getName)
);
```

Multiple comparators can be created for the same class:

``` java
Comparator<Employee> byName =
        Comparator.comparing(Employee::getName);

Comparator<Employee> bySalary =
        Comparator.comparingInt(Employee::getSalary);
```

------------------------------------------------------------------------

# 45. Fail-Fast Iterators

Many standard collection iterators are fail-fast.

If the collection is structurally modified directly during iteration, an
iterator may throw:

``` text
ConcurrentModificationException
```

Example:

``` java
for (String name : names) {
    names.remove(name);
}
```

Safer options include:

-   `Iterator.remove()`
-   Collect items to remove and remove afterward
-   Use suitable concurrent collections when concurrency is actually
    required

Fail-fast behavior is not a synchronization mechanism.

------------------------------------------------------------------------

# 46. Unmodifiable Collections

Example:

``` java
List<String> list = List.of("A", "B", "C");
```

This list cannot be modified.

Also:

``` java
List<String> unmodifiable =
        Collections.unmodifiableList(original);
```

An unmodifiable **view** can still reflect changes made through another
reference to the original collection.

------------------------------------------------------------------------

# 47. `List.of()` vs `Arrays.asList()`

## `List.of()`

``` java
List<String> list = List.of("A", "B");
```

-   Unmodifiable
-   Does not allow `null`

## `Arrays.asList()`

``` java
List<String> list =
        Arrays.asList("A", "B");
```

-   Fixed-size
-   `set()` is allowed
-   `add()` / `remove()` are not supported
-   Backed by the array

To get a normal resizable list:

``` java
List<String> list =
        new ArrayList<>(Arrays.asList("A", "B"));
```

------------------------------------------------------------------------

# 48. Queue vs Deque

  Queue          Deque
  -------------- --------------------------------
  Usually FIFO   Both ends
  `offer()`      `offerFirst()` / `offerLast()`
  `poll()`       `pollFirst()` / `pollLast()`
  `peek()`       `peekFirst()` / `peekLast()`

Deque can implement both:

``` text
FIFO Queue
LIFO Stack
```

------------------------------------------------------------------------

# 49. Stack vs Queue

## Stack

``` text
LIFO
Last In → First Out
```

``` text
push A
push B
push C

pop → C
```

## Queue

``` text
FIFO
First In → First Out
```

``` text
A → B → C

poll → A
```

------------------------------------------------------------------------

# 50. Time Complexity Cheat Sheet

Typical complexities:

  ------------------------------------------------------------------------
  Structure               Get/Search                Add             Remove
  --------------- ------------------ ------------------ ------------------
  ArrayList          O(1) get / O(n) O(1) amortized end               O(n)
                              search                    

  LinkedList                    O(n)          O(1) ends          O(1) ends

  HashSet               O(1) average       O(1) average       O(1) average

  TreeSet                   O(log n)           O(log n)           O(log n)

  HashMap               O(1) average       O(1) average       O(1) average

  LinkedHashMap         O(1) average       O(1) average       O(1) average

  TreeMap                   O(log n)           O(log n)           O(log n)

  PriorityQueue       O(n) arbitrary           O(log n)      O(log n) poll
                  search / O(1) peek                    

  ArrayDeque       N/A for arbitrary     O(1) amortized     O(1) amortized
                              access               ends               ends
  ------------------------------------------------------------------------

------------------------------------------------------------------------

# 51. How to Choose a Collection

``` text
Need key-value pairs?
        ↓
       Map
        |
        +-- Fast lookup → HashMap
        +-- Insertion/access order → LinkedHashMap
        +-- Sorted keys → TreeMap

Need unique elements?
        ↓
       Set
        |
        +-- Fast lookup → HashSet
        +-- Insertion order → LinkedHashSet
        +-- Sorted → TreeSet

Need ordered elements + duplicates?
        ↓
       List
        |
        +-- General purpose → ArrayList
        +-- Deque/list behavior → LinkedList

Need queue?
        ↓
   ArrayDeque / PriorityQueue

Need stack?
        ↓
   ArrayDeque
```

------------------------------------------------------------------------

# 52. Most Important Interview Comparisons

## ArrayList vs LinkedList

Know:

-   Internal structure
-   Random access
-   Insertion/removal
-   Memory overhead
-   Practical choice

## HashMap vs LinkedHashMap vs TreeMap

Know:

-   Ordering
-   Performance
-   Null keys
-   Use cases

## HashSet vs LinkedHashSet vs TreeSet

Know:

-   Uniqueness
-   Ordering
-   Performance
-   Null handling

## Comparable vs Comparator

Know:

-   Natural vs custom ordering
-   `compareTo()` vs `compare()`
-   `java.lang` vs `java.util`

## Collection vs Collections

Know:

-   Interface vs utility class

## Iterator vs ListIterator

Know:

-   Forward vs bidirectional
-   Collection vs List
-   Supported modification methods

## ArrayDeque vs Stack

Know:

-   Modern vs legacy
-   LIFO
-   Null handling

------------------------------------------------------------------------

# 53. Top Interview Questions

1.  What is the Java Collections Framework?
2.  Why do we need collections?
3.  Explain the Collection hierarchy.
4.  Is `Map` a Collection?
5.  What is `Iterable`?
6.  What is `Iterator`?
7.  Iterator vs ListIterator?
8.  ArrayList vs LinkedList?
9.  How does ArrayList grow?
10. Why is ArrayList `get()` O(1)?
11. Why is LinkedList random access O(n)?
12. Why is Vector considered legacy?
13. Why is Stack considered legacy?
14. How does Queue work?
15. `add()` vs `offer()`?
16. `remove()` vs `poll()`?
17. `element()` vs `peek()`?
18. What is PriorityQueue?
19. How does PriorityQueue work internally?
20. What is a Deque?
21. Why use ArrayDeque?
22. Comparable vs Comparator?
23. What is Map?
24. How does HashMap work internally?
25. Why are `equals()` and `hashCode()` important?
26. What is hash collision?
27. What is HashMap load factor?
28. What happens during HashMap resizing?
29. What is treeification in HashMap?
30. Why should HashMap keys be immutable?
31. HashMap vs LinkedHashMap?
32. LinkedHashMap vs TreeMap?
33. HashSet vs LinkedHashSet vs TreeSet?
34. Can HashMap have duplicate keys?
35. Can HashMap have duplicate values?
36. Can HashMap have null?
37. Can TreeMap have null keys?
38. What is fail-fast behavior?
39. `List.of()` vs `Arrays.asList()`?
40. `Collection` vs `Collections`?

------------------------------------------------------------------------

# 54. Rapid-Fire Answers

### Why ArrayList?

Fast index access and good general-purpose list performance.

### Why LinkedList?

Useful when deque behavior or frequent end operations are required;
random access is slow.

### Why HashSet?

Fast average uniqueness checks.

### Why LinkedHashSet?

Uniqueness + insertion order.

### Why TreeSet?

Unique + sorted elements.

### Why HashMap?

Fast average key-based lookup without needing ordering.

### Why LinkedHashMap?

HashMap-like lookup + predictable insertion/access order.

### Why TreeMap?

Sorted keys and navigation/range operations.

### Why PriorityQueue?

Repeatedly retrieve the highest/lowest-priority element.

### Why ArrayDeque?

Efficient queue/deque/stack operations without using legacy `Stack`.

### Why Comparable?

Define the natural ordering of a class.

### Why Comparator?

Define one or more external/custom orderings.

------------------------------------------------------------------------

# 55. Interview-Level HashMap Answer

If asked **"How does HashMap work internally?"**, a strong concise
answer is:

> `HashMap` stores entries in an internal bucket table. For a key, it
> uses `hashCode()` and hash spreading to determine a bucket. If
> multiple keys land in the same bucket, HashMap handles the collision
> using internal node structures and can treeify heavily-collided
> buckets in modern implementations. During lookup, it uses the hash and
> `equals()` to find the correct key. The average complexity of `get()`
> and `put()` is O(1), assuming good hash distribution.

------------------------------------------------------------------------

# 56. Interview-Level Collection Selection Answer

If asked **"Which collection would you choose?"**, answer based on
requirements:

``` text
Need duplicates + index?
→ ArrayList

Need unique values?
→ HashSet

Need unique + insertion order?
→ LinkedHashSet

Need unique + sorted?
→ TreeSet

Need key-value + fast lookup?
→ HashMap

Need key-value + insertion order?
→ LinkedHashMap

Need key-value + sorted keys?
→ TreeMap

Need FIFO?
→ ArrayDeque

Need priority-based processing?
→ PriorityQueue

Need LIFO?
→ ArrayDeque
```

------------------------------------------------------------------------

# 57. Final Memory Map

``` text
JAVA COLLECTIONS
│
├── Iterable
│    └── Collection
│         ├── List
│         │    ├── ArrayList
│         │    ├── LinkedList
│         │    └── Vector → Stack
│         │
│         ├── Set
│         │    ├── HashSet
│         │    ├── LinkedHashSet
│         │    └── TreeSet
│         │
│         └── Queue
│              ├── PriorityQueue
│              └── Deque
│                   └── ArrayDeque
│
└── Map
     ├── HashMap
     ├── LinkedHashMap
     └── TreeMap
```

## One-Line Revision

> **List = ordered + duplicates, Set = unique, Queue = processing order,
> Deque = both ends, Map = key-value pairs.**

For interviews, focus especially on:

\`\`\`text ArrayList vs LinkedList HashMap internal working equals() +
hashCode() HashMap vs LinkedHashMap vs TreeMap HashSet vs LinkedHashSet
vs TreeSet Comparable vs Comparator Queue methods PriorityQueue Deque /
ArrayDeque Iterator Collection vs Collections Time complexities
