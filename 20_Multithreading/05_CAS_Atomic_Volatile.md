# Java Concurrency — CAS, Atomic Variables, Volatile & Concurrent Collections

> Interview-focused notes for Java concurrency.

---

# 1. CAS — Compare And Swap

**CAS (Compare-And-Swap)** is a non-blocking atomic operation.

Conceptually:

```text
CAS(variable, expectedValue, newValue)

if current value == expected value
    update to new value
    return true
else
    do not update
    return false
```

### Example

```text
Initial value = 10

Thread T1: expected=10, new=11 → CAS succeeds
Thread T2: expected=10, new=11 → CAS fails because value is now 11
```

T2 can read the latest value and retry.

### CAS vs Lock

| CAS | Lock |
|---|---|
| Non-blocking approach | Blocking approach |
| Thread may retry | Thread may wait |
| No traditional lock required | Uses lock |
| Good for small atomic updates | Good for larger critical sections |

### Interview answer

> CAS compares the current value with an expected value and updates it atomically only when they match.

---

# 2. Atomic Variables

Java provides atomic classes in:

```text
java.util.concurrent.atomic
```

Common classes:

| Class | Purpose |
|---|---|
| `AtomicInteger` | Atomic `int` operations |
| `AtomicLong` | Atomic `long` operations |
| `AtomicBoolean` | Atomic `boolean` operations |
| `AtomicReference<T>` | Atomic object-reference operations |

### Why use AtomicInteger?

`count++` is not one atomic operation:

```text
Read count
   ↓
Add 1
   ↓
Write count
```

Two threads can interfere with these steps.

`AtomicInteger` provides atomic operations such as:

```text
incrementAndGet()
decrementAndGet()
addAndGet()
compareAndSet()
```

**Code:** `AtomicVariablesExample.java`

### Common methods

| Method | Functionality |
|---|---|
| `get()` | Returns current value |
| `set(value)` | Sets value |
| `incrementAndGet()` | Increments, then returns new value |
| `getAndIncrement()` | Returns old value, then increments |
| `decrementAndGet()` | Decrements, then returns new value |
| `addAndGet(n)` | Adds `n`, then returns new value |
| `compareAndSet(expected, update)` | Updates only if current value equals expected |

### Important

```text
incrementAndGet()
→ return NEW value

getAndIncrement()
→ return OLD value
```

---

# 3. `volatile` Variable

`volatile` provides **visibility** between threads.

```java
private volatile boolean running = true;
```

When one thread changes a volatile variable, another thread can see the latest value.

### What volatile guarantees

- Visibility
- Ordering / happens-before relationship for volatile access

### What volatile does NOT guarantee

It does **not** make compound operations atomic.

```java
volatile int count;

count++; // NOT atomic
```

`count++` still means:

```text
read → increment → write
```

### Common use case

A simple thread-stop flag:

```text
volatile boolean running
```

One thread sets:

```text
running = false
```

Another thread observes it and exits.

**Code:** `VolatileExample.java`

### Interview answer

> `volatile` provides visibility and ordering guarantees, but it does not provide atomicity for compound operations such as `count++`.

---

# 4. volatile vs Atomic Variable

| `volatile` | Atomic variable |
|---|---|
| Visibility + ordering | Atomic operations + visibility |
| `count++` is not atomic | `incrementAndGet()` is atomic |
| Good for simple flags/state | Good for counters/CAS updates |
| Does not provide read-modify-write atomicity | Supports atomic read-modify-write |

### Easy memory trick

```text
volatile
→ "Can other threads see my latest value?"

AtomicInteger
→ "Can I safely update my value atomically?"
```

---

# 5. Concurrent Collections

Java provides thread-safe collections in:

```text
java.util.concurrent
```

Common ones:

| Collection | Use |
|---|---|
| `ConcurrentHashMap` | Concurrent thread-safe Map |
| `CopyOnWriteArrayList` | Many reads, few writes |
| `CopyOnWriteArraySet` | Many reads, few writes |
| `BlockingQueue` | Producer-Consumer |
| `ConcurrentLinkedQueue` | Non-blocking concurrent queue |
| `ConcurrentLinkedDeque` | Non-blocking concurrent deque |

---

# 6. ConcurrentHashMap

`ConcurrentHashMap` is a thread-safe `Map` designed for concurrent access.

### Important points

- Thread-safe
- Supports concurrent operations
- Does not allow `null` keys or values
- Provides useful atomic-style methods:
  - `putIfAbsent()`
  - `computeIfAbsent()`
  - `compute()`
  - `merge()`

### HashMap vs ConcurrentHashMap

| HashMap | ConcurrentHashMap |
|---|---|
| Not thread-safe | Thread-safe |
| Requires external synchronization for safe concurrent access | Designed for concurrent access |
| Allows one null key and null values | Does not allow null keys/values |

---

# 7. CopyOnWriteArrayList

`CopyOnWriteArrayList` is a thread-safe list.

On modification, it creates a new copy of the underlying array.

### Best use case

```text
Many reads
Few writes
```

Examples:

- Listener lists
- Configuration snapshots
- Frequently read data

### Disadvantage

Writes can be expensive because the array is copied.

---

# 8. BlockingQueue

`BlockingQueue` is commonly used for Producer-Consumer problems.

| Method | Functionality |
|---|---|
| `put()` | Adds element; waits if queue is full |
| `take()` | Removes element; waits if queue is empty |
| `offer()` | Attempts to add without indefinite waiting |
| `poll()` | Attempts to remove without indefinite waiting |

Common implementations:

```text
ArrayBlockingQueue
LinkedBlockingQueue
PriorityBlockingQueue
DelayQueue
```

### Flow

```text
Producer
   ↓ put()
BlockingQueue
   ↓ take()
Consumer
```

---

# 9. ConcurrentLinkedQueue

`ConcurrentLinkedQueue` is a **thread-safe, non-blocking queue**.

### Difference

```text
BlockingQueue
→ Supports blocking operations such as put() / take()

ConcurrentLinkedQueue
→ Non-blocking
```

Use it when threads should not wait for space/items through blocking queue operations.

---

# 10. Why Concurrent Collections?

Normal collections such as `HashMap`, `ArrayList`, and `LinkedList` are not generally designed for concurrent modification.

Concurrent collections provide thread-safe operations and concurrency-friendly implementations.

### Interview answer

> Concurrent collections are designed specifically for multi-threaded access and generally provide better concurrency characteristics than simply synchronizing a normal collection.

---

# 11. CAS + Atomic + Volatile + Concurrent Collections

Think of them at different levels:

```text
CAS
 ↓
Low-level atomic compare-and-update mechanism

Atomic Variables
 ↓
Thread-safe atomic operations on individual values

volatile
 ↓
Visibility + ordering

Concurrent Collections
 ↓
Thread-safe data structures for multiple threads
```

---

# 12. Common Interview Questions

### Q1. What is CAS?

> CAS compares the current value with an expected value and updates it atomically only when they match.

### Q2. Is CAS blocking?

> CAS is generally considered a non-blocking synchronization technique.

### Q3. Why AtomicInteger instead of volatile int?

> `volatile` provides visibility but does not make compound operations such as `count++` atomic. `AtomicInteger` provides atomic read-modify-write operations.

### Q4. Does volatile make a variable thread-safe?

> Not necessarily. It provides visibility and ordering, but compound operations may still require atomic operations or synchronization.

### Q5. Is `count++` atomic?

> No.

### Q6. Is `AtomicInteger.incrementAndGet()` atomic?

> Yes.

### Q7. Does ConcurrentHashMap allow null?

> No. It does not allow null keys or null values.

### Q8. When should you use CopyOnWriteArrayList?

> When reads are very frequent and writes are relatively rare.

### Q9. When should you use BlockingQueue?

> Commonly for Producer-Consumer scenarios where producers/consumers may need to wait when the queue is full or empty.

### Q10. BlockingQueue vs ConcurrentLinkedQueue?

> `BlockingQueue` supports blocking operations such as `put()` and `take()`, while `ConcurrentLinkedQueue` is non-blocking.

---

# 13. Final Revision

```text
CAS
→ Compare expected value and update atomically.

AtomicInteger
→ Thread-safe atomic operations such as incrementAndGet().

volatile
→ Visibility + ordering, NOT compound-operation atomicity.

ConcurrentHashMap
→ Thread-safe concurrent Map.

CopyOnWriteArrayList
→ Best for many reads and few writes.

BlockingQueue
→ Blocking Producer-Consumer queue.

ConcurrentLinkedQueue
→ Non-blocking concurrent queue.
```
