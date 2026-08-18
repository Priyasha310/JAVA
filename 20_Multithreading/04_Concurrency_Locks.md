# Java Concurrency Locks — Interview Notes

> Interview-focused notes covering **ReentrantLock, ReadWriteLock, StampedLock, Semaphore, and Condition**.
>
> **Code rule:** Each concept has its own `.java` file. All classes required for that concept are kept inside the same file.

---

# 1. ReentrantLock

`ReentrantLock` is an explicit lock from `java.util.concurrent.locks`.

### Key features
- Explicit `lock()` / `unlock()`
- `tryLock()`
- Timed lock acquisition
- `lockInterruptibly()`
- Optional fairness
- Reentrant: the same thread can acquire the lock multiple times

### Why "reentrant"?

```text
Thread T
  ↓
lock()       → hold count = 1
  ↓
lock()       → hold count = 2
  ↓
unlock()     → hold count = 1
  ↓
unlock()     → hold count = 0 → lock released
```
When multiple objects are created and have to allow only single thread, in that case we use reentrant locks.

Every successful lock acquisition must be matched by an `unlock()`.

**Code:** `ReentrantLockExample.java`

---

# 2. ReadWriteLock

`ReadWriteLock` maintains separate **read** and **write** locks.

```text
Multiple readers → allowed concurrently

Writer → exclusive access
```

### Rules

- Multiple readers can hold the read lock together.
- Only one writer can hold the write lock.
- A writer is exclusive.
- Readers cannot access the protected data while a writer holds the write lock.

The common implementation is `ReentrantReadWriteLock`.

### When to use?

Use it when:

```text
Many reads
Few writes
```

and concurrent reads are safe.

### Interview answer

> `ReadWriteLock` improves concurrency for read-heavy workloads by allowing multiple readers while keeping writes exclusive.

**Code:** `ReadWriteLockExample.java`

---

# 3. StampedLock

`StampedLock` supports:

- Write lock
- Read lock
- Optimistic read

It returns a **stamp** when a lock is acquired.

```text
StampedLock
 ├── writeLock()
 ├── readLock()
 └── tryOptimisticRead()
```

## Optimistic Read

The thread first gets a stamp and reads without immediately acquiring a read lock. It then validates the stamp.

```text
tryOptimisticRead()
       ↓
    Read data
       ↓
validate(stamp)
   ┌───┴────┐
 valid    invalid
   ↓         ↓
use data   retry with
           read lock
```

If a writer changed the data during the read, validation fails.

### Important interview point

**`StampedLock` is NOT reentrant.**

### ReadWriteLock vs StampedLock

| ReadWriteLock | StampedLock |
|---|---|
| Read/write locks | Read/write + optimistic read |
| Reentrant | Not reentrant |
| Simpler | More complex |
| Uses lock objects | Uses stamps |

### Interview answer

> `StampedLock` provides read, write, and optimistic-read modes. Optimistic reading can reduce locking overhead for short read-heavy operations, but the stamp must be validated.

**Code:** `StampedLockExample.java`

---

# 4. Semaphore

A `Semaphore` controls access using a fixed number of **permits**.

Example with 3 permits:

```text
Thread 1 → acquire → Permit 1
Thread 2 → acquire → Permit 2
Thread 3 → acquire → Permit 3
Thread 4 → waits
```

When a thread calls `release()`, a permit becomes available.

### Common use case

Only 3 database connections are available:

```text
Semaphore(3)
     ↓
At most 3 threads use the resource concurrently
```

### Important methods

| Method | Functionality |
|---|---|
| `acquire()` | Acquires a permit; waits if none is available |
| `release()` | Returns a permit |
| `tryAcquire()` | Attempts to acquire a permit without waiting indefinitely |
| `availablePermits()` | Returns currently available permits |

### Semaphore vs Lock

| Semaphore | Lock |
|---|---|
| Controls number of permits | Usually controls one critical section |
| Multiple threads can hold permits | Usually one thread owns the lock |
| `acquire()` / `release()` | `lock()` / `unlock()` |
| Resource limiting | Mutual exclusion |

### Important interview point

A semaphore with one permit can provide mutual-exclusion-like behavior, but it is conceptually different from a lock. A lock has ownership; a semaphore manages permits.

**Code:** `SemaphoreExample.java`

---

# 5. Condition

`Condition` provides thread coordination with an explicit `Lock`.

```text
ReentrantLock
      ↓
  Condition
      ↓
await()
signal()
signalAll()
```

### Mapping

| Object monitor | Condition |
|---|---|
| `wait()` | `await()` |
| `notify()` | `signal()` |
| `notifyAll()` | `signalAll()` |
| `synchronized` | `Lock` |

### Important rule

The associated lock must be held before calling:

- `await()`
- `signal()`
- `signalAll()`

### What does `await()` do?

1. Releases the associated lock.
2. Puts the current thread into the waiting state.
3. Waits for a signal/interruption/other permitted wake-up.
4. Re-acquires the lock before returning.

### Why is Condition useful?

A single lock can have multiple conditions.

For Producer-Consumer:

```text
             ReentrantLock
                  │
          ┌───────┴───────┐
          ↓               ↓
      notFull          notEmpty
          ↓               ↓
      Producers        Consumers
```

This gives more precise coordination than a single `wait()`/`notifyAll()` wait-set.

**Code:** `ConditionExample.java`

---

# 6. Condition vs wait()/notify()

| `wait()/notify()` | `Condition` |
|---|---|
| Uses object's monitor | Uses explicit `Lock` |
| One wait-set per object | Multiple conditions can be created for one lock |
| `wait()` | `await()` |
| `notify()` | `signal()` |
| `notifyAll()` | `signalAll()` |
| `synchronized` | `Lock` |

---

# 7. Which One Should I Use?

### ReentrantLock
Use when you need explicit/flexible locking such as `tryLock()`, timeout, interruptible acquisition, or fairness.

### ReadWriteLock
Use for **many reads and relatively few writes**, when concurrent reads are safe.

### StampedLock
Use when optimistic reads may help performance in a read-heavy workload. Remember that it is **not reentrant**.

### Semaphore
Use when you need to limit the number of threads accessing a resource at the same time.

### Condition
Use when you need explicit-lock-based thread coordination with `await()`, `signal()`, or `signalAll()`.

---

# 8. Quick Comparison

| Concept | Main Purpose | Key Point |
|---|---|---|
| `ReentrantLock` | Mutual exclusion | Flexible alternative to `synchronized` |
| `ReadWriteLock` | Separate read/write access | Multiple readers can run together |
| `StampedLock` | Read/write + optimistic read | Not reentrant |
| `Semaphore` | Limit concurrent access | Controls permits |
| `Condition` | Thread coordination | `await/signal/signalAll` with `Lock` |

---

# 9. Common Interview Questions

### Q1. Why is ReentrantLock called reentrant?
Because the thread holding the lock can acquire the same lock again without blocking itself. It must unlock it the same number of times.

### Q2. Can multiple threads hold a read lock simultaneously?
Yes, as long as no writer currently holds the write lock.

### Q3. Is StampedLock reentrant?
No.

### Q4. What is optimistic reading?
A thread reads without immediately acquiring a read lock and then validates whether a write occurred during the read.

### Q5. What does Semaphore control?
It controls concurrent access using a fixed number of permits.

### Q6. What happens when `Semaphore.acquire()` has no permit?
The calling thread waits until a permit becomes available or it is interrupted.

### Q7. What is `Condition.await()` similar to?
Conceptually, it is the explicit-lock equivalent of `Object.wait()`.

### Q8. `signal()` vs `signalAll()`?
`signal()` wakes one waiting thread; `signalAll()` wakes all waiting threads.

### Q9. Why use Condition instead of wait()/notifyAll()?
It supports explicit locks and allows multiple independent condition queues for the same lock.

---

# 10. One-Line Revision

```text
ReentrantLock
→ Explicit, flexible mutual-exclusion lock.

ReadWriteLock
→ Multiple readers OR one writer.

StampedLock
→ Read + write + optimistic read; NOT reentrant.

Semaphore
→ Controls concurrent access using permits.

Condition
→ await/signal/signalAll for coordination with Lock.
```

---

# 11. Code Files

Each concept is implemented separately so it can be compiled and practiced independently.

| Concept | Java File |
|---|---|
| ReentrantLock | `ReentrantLockExample.java` |
| ReadWriteLock | `ReadWriteLockExample.java` |
| StampedLock | `StampedLockExample.java` |
| Semaphore | `SemaphoreExample.java` |
| Condition | `ConditionExample.java` |
