# Java Multithreading — Interview Notes

## 1. What is Multithreading?

**Multithreading** is executing multiple threads concurrently within a single process.

A **thread** is the smallest unit of execution inside a process.

```java
class Task extends Thread {
    @Override
    public void run() {
        System.out.println("Task running");
    }
}

public class Main {
    public static void main(String[] args) {
        Task task = new Task();
        task.start();
    }
}
```

### Interview answer

> Multithreading allows multiple threads within a process to execute concurrently, improving responsiveness and potentially improving CPU utilization and throughput.

---

#
# 3. JVM, Processes, and Threads — Interview Diagram

A useful way to visualize the relationship between **processes, JVM instances, heap memory, and threads** is:

![JVM Memory and Threads Diagram](Java_Multithreading_JVM_Memory_Diagram.png)

### How to read this diagram

```text
Physical Memory / RAM
        │
        ├────────────── Process 1
        │                  │
        │                  └── JVM Instance 1
        │                       ├── Heap
        │                       ├── Method Area / Class Metadata
        │                       ├── Code / Data Areas
        │                       │      ├── Code Segment
        │                       │      ├── Data Segment
        │                       │      └── Runtime Constants / Loaded Classes
        │                       └── Threads
        │                           ├── Thread 1
        │                           │   ├── Stack
        │                           │   ├── PC Register / Program Counter
        │                           │   └── Native Method Stack (optional conceptual)
        │                           ├── Thread 2
        │                           │   ├── Stack
        │                           │   ├── PC Register / Program Counter
        │                           │   └── Native Method Stack (optional conceptual)
        │                           └── Thread 3
        │                               ├── Stack
        │                               ├── PC Register / Program Counter
        │                               └── Native Method Stack (optional conceptual)
        │
        └────────────── Process 2
                           │
                           └── JVM Instance 2
                                ├── Heap
                                ├── Method Area / Class Metadata
                                ├── Code / Data Areas
                                └── Threads
```

### One-line meaning of each part

- **Process**: An independent program running in the operating system with its own memory space.
- **JVM Instance**: The runtime environment that executes a Java program.
- **Heap**: Shared memory area where objects and instance data are stored.
- **Method Area / Class Metadata**: Stores class definitions, static variables, and runtime metadata.
- **Code Segment**: Holds executable bytecode/instructions for the program.
- **Data Segment**: Stores static/runtime data needed by the program.
- **Thread**: A lightweight execution path inside a process.
- **Stack**: Stores method calls, local variables, and frames for each thread.
- **PC Register / Program Counter**: Tracks the next instruction to execute for that thread.
- **Native Method Stack**: Memory used for native method calls outside JVM-managed Java code.

### Key interview points

- A **process** is an independent execution environment.
- A Java application normally runs inside a **JVM instance**.
- Two separate Java processes generally have **separate JVM instances**.
- Each JVM instance has a **heap**, **method area / class metadata**, and **code/data areas**.
- Threads in the same JVM/process **share the heap and class data**.
- Each thread has its own **stack** and **program-counter (PC) register**.
- The JVM also conceptually has **code segment** and **data segment**-like areas for loaded classes and runtime constants.
- Therefore, `Thread1`, `Thread2`, and `Thread3` in the same JVM can access shared heap objects, while each thread keeps its own execution state.
- This shared memory is why multithreaded programs can have **race conditions** and require synchronization when mutable data is shared.
- Separate processes do not normally share the same Java heap directly.

### Important correction for interviews

Do not say:

> "Each thread has its own heap."

The better answer is:

> **Threads in the same JVM share the heap and class metadata, while each thread has its own stack and PC register / program-counter state.**

Also, the diagram is a **conceptual model**. JVM memory contains additional areas and implementation details; the intent is to explain the relationship among processes, JVM instances, code/data areas, and threads for interviews.

---

# 2. Benefits of Multithreading

- Better CPU utilization
- Improved application responsiveness
- Better throughput
- Allows independent tasks to make progress concurrently
- Threads share process resources, making communication easier than between processes

Example:

```text
Application
    |
    +---- Thread 1 → API call
    |
    +---- Thread 2 → File processing
    |
    +---- Thread 3 → Background task
```

### Important

More threads **does not always mean better performance**. Too many threads can cause context-switching overhead and contention.

---

## 3. Challenges of Multithreading

### Race Condition

Occurs when multiple threads access shared mutable data and the result depends on execution timing.

```java
count++;
```

This is not one atomic operation.

Conceptually:

```text
Read → Modify → Write
```

Two threads can interfere with each other.

### Deadlock

Two or more threads wait indefinitely for resources held by each other.

```text
Thread 1 → Lock A → waits for Lock B
Thread 2 → Lock B → waits for Lock A
```

### Starvation

A thread continuously waits because other threads keep getting access to a required resource.

### Livelock

Threads are active and responding to each other but make no useful progress.

### Context Switching

CPU switches between threads.

Too many threads can increase overhead.

---

# 4. Process vs Thread

## Process

A **process** is an independent program in execution.

Examples:

```text
Java Application
Browser
Database
```

## Thread

A **thread** is an execution unit inside a process.

```text
Java Process
    |
    +---- Thread 1
    +---- Thread 2
    +---- Thread 3
```

### Process vs Thread

| Process | Thread |
|---|---|
| Independent execution unit | Execution unit within a process |
| Separate address space | Threads share process memory |
| More resource-heavy | More lightweight |
| Process creation is relatively expensive | Thread creation is relatively cheaper |
| Inter-process communication is more involved | Threads can communicate through shared memory |
| Failure is generally more isolated | A thread failure can affect the process |

### Interview answer

> A process is an independent program in execution, while a thread is a lightweight execution unit inside a process. Threads of the same process share memory and resources.

---

# 5. Concurrency vs Parallelism

## Concurrency

Multiple tasks make progress during overlapping periods.

```text
Time →

Thread 1: ███     ███
Thread 2:    ███     ███
```

They may be taking turns on a single CPU.

## Parallelism

Multiple tasks actually execute simultaneously, usually on different CPU cores.

```text
Core 1: █████████
Core 2: █████████
```

### Interview answer

> Concurrency means multiple tasks are in progress with overlapping execution, while parallelism means multiple tasks are executing simultaneously.

---

# 6. Multithreading in Java

Common ways to create/execute tasks:

1. Extend `Thread`
2. Implement `Runnable`
3. Implement `Callable`
4. Use `ExecutorService`
5. Use higher-level concurrency APIs

---

# 7. Creating a Thread Using `Thread`

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread running");
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
    }
}
```

### Important

Use:

```java
thread.start();
```

not:

```java
thread.run();
```

---

# 8. `start()` vs `run()`

### `start()`

```java
thread.start();
```

Starts a new thread of execution and causes `run()` to execute on that thread.

### `run()`

```java
thread.run();
```

If called directly, it is just a normal method call and executes on the current thread.

### Remember

```text
start()
   ↓
new thread execution
   ↓
run()
```

Whereas:

```text
run()
   ↓
normal method call
   ↓
current thread
```

---

# 9. Creating a Thread Using `Runnable`

```java
class MyTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Task running");
    }
}

public class Main {
    public static void main(String[] args) {

        Runnable task = new MyTask();

        Thread thread = new Thread(task);

        thread.start();
    }
}
```

### Why is `Runnable` often preferred?

`Runnable` represents the **task**, while `Thread` represents the execution mechanism.

Java supports single inheritance. If your class extends `Thread`, it cannot extend another class.

With `Runnable`:

```java
class MyTask extends SomeClass implements Runnable
```

is possible.

---

# 10. Runnable Using Lambda

`Runnable` is a functional interface.

```java
Runnable task = () -> {
    System.out.println("Task running");
};

new Thread(task).start();
```

Or:

```java
new Thread(() ->
    System.out.println("Task running")
).start();
```

---

# 11. Callable

`Callable` is useful when a task needs to:

- Return a result
- Throw checked exceptions

```java
Callable<Integer> task = () -> {
    return 10 + 20;
};
```

It is commonly used with `ExecutorService` and `Future`.

---

# 12. Runnable vs Callable

| Runnable | Callable |
|---|---|
| `run()` | `call()` |
| Returns `void` | Returns a value |
| Cannot directly throw checked exceptions | Can throw checked exceptions |
| Suitable when no result is required | Suitable when a result is required |

Example:

```java
Runnable r = () -> System.out.println("Hello");
```

```java
Callable<Integer> c = () -> 10 + 20;
```

---

# 13. Thread Lifecycle / States

Java's `Thread.State` has six states:

```text
NEW
 ↓ start()
RUNNABLE
 ↓
 ├── BLOCKED
 ├── WAITING
 └── TIMED_WAITING
 ↓
RUNNABLE
 ↓
TERMINATED
```

### States

| State | Meaning |
|---|---|
| `NEW` | Thread created but not started |
| `RUNNABLE` | Ready/running under JVM scheduling |
| `BLOCKED` | Waiting to acquire a monitor lock |
| `WAITING` | Waiting indefinitely for another thread/action |
| `TIMED_WAITING` | Waiting for a specified time |
| `TERMINATED` | Execution completed |

### Important interview trap

Java's official `Thread.State` enum does **not** have a separate `RUNNING` state. A running thread is represented by `RUNNABLE`.

---

# 14. Common Thread Methods

| Method | Purpose |
|---|---|
| `start()` | Starts thread execution |
| `run()` | Contains task logic |
| `sleep()` | Pauses current thread for a duration |
| `join()` | Waits for another thread to finish |
| `interrupt()` | Requests interruption |
| `isAlive()` | Checks whether thread has started and not terminated |
| `currentThread()` | Returns current thread |
| `getName()` | Gets thread name |
| `setName()` | Sets thread name |

---

# 15. `sleep()` vs `wait()`

Very common interview question.

## `sleep()`

```java
Thread.sleep(1000);
```

- Static method of `Thread`
- Pauses the current thread
- Does **not** release a monitor lock held by the thread

## `wait()`

```java
object.wait();
```

- Method of `Object`
- Used for thread coordination
- Must be called while owning that object's monitor
- Releases that object's monitor while waiting

### Easy rule

```text
sleep() → pause
wait()  → coordination
```

---

# 16. `join()`

Used when one thread needs to wait for another thread to finish.

```java
Thread t1 = new Thread(() -> {
    System.out.println("Task 1");
});

t1.start();

t1.join();

System.out.println("Main continues");
```

Conceptually:

```text
Main Thread
    |
    +---- start t1
    |
    +---- wait for t1
              |
              ↓
          t1 finishes
              |
              ↓
        Main continues
```

---

# 17. `sleep()` vs `join()` vs `wait()`

| Method | Main purpose |
|---|---|
| `sleep()` | Pause current thread |
| `join()` | Wait for another thread to finish |
| `wait()` | Coordinate using an object's monitor |

---

# 18. Important Interview Questions

### Q1. What is multithreading?

> Multithreading is the execution of multiple threads concurrently within a process.

### Q2. Process vs Thread?

> A process is an independent execution unit with its own address space, while a thread is a lightweight execution unit within a process and shares the process's memory.

### Q3. Concurrency vs Parallelism?

> Concurrency means multiple tasks can make progress during overlapping periods. Parallelism means multiple tasks execute simultaneously.

### Q4. `start()` vs `run()`?

> `start()` starts a new thread of execution; calling `run()` directly is a normal method call on the current thread.

### Q5. Why prefer `Runnable` over extending `Thread`?

> `Runnable` separates the task from the execution mechanism and allows the class to extend another class.

### Q6. Runnable vs Callable?

> Runnable returns no result, while Callable can return a result and throw checked exceptions.

### Q7. What are common multithreading problems?

> Race condition, deadlock, starvation, livelock, and excessive context switching.

### Q8. What are Java thread states?

> NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, and TERMINATED.

---

# 19. 2–3 YOE Interview Focus

## Must Know

- Multithreading definition
- Process vs Thread
- Concurrency vs Parallelism
- Thread vs Runnable
- Runnable vs Callable
- `start()` vs `run()`
- Thread lifecycle/states
- `sleep()` vs `wait()`
- `join()`
- Race condition
- Deadlock

## Next Topics

- `synchronized`
- `volatile`
- Atomic classes
- ExecutorService
- Thread Pool
- Future
- CompletableFuture
- ConcurrentHashMap
- Locks
- Producer-Consumer
- Parallel Streams
