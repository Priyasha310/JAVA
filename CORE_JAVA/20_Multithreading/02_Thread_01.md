# Java Threads — Interview Notes

## 1. What is a Thread?

A **thread** is the smallest unit of execution within a process.

A Java application can have multiple threads executing concurrently.

```text
Java Process
     |
     +---- Thread 1
     +---- Thread 2
     +---- Thread 3
```

### Interview Answer

> A thread is a lightweight unit of execution within a process. Multiple threads in the same process share the process's heap memory but have their own stack and execution state.

---

# 2. Creating Threads in Java

There are two basic ways commonly asked in interviews:

1. Extending the `Thread` class
2. Implementing the `Runnable` interface

Modern applications generally use higher-level APIs such as `ExecutorService` rather than manually creating many threads.

---

# 3. Extending the `Thread` Class

Create a class that extends `Thread` and override `run()`.

```java
class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("Thread is running");
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

```java
thread.start();
```

starts a new thread.

Do NOT confuse it with:

```java
thread.run();
```

Calling `run()` directly does **not** start a new thread.

---

# 4. `start()` vs `run()`

## `start()`

```java
thread.start();
```

- Creates/schedules a new thread of execution.
- JVM eventually invokes `run()` on that thread.
- A thread can be started only once.

## `run()`

```java
thread.run();
```

- Just a normal method call if called directly.
- Executes on the current thread.
- Does not create a new thread.

### Interview Question

**What happens if I call `start()` twice?**

```java
thread.start();
thread.start(); // ❌
```

It throws:

```text
IllegalThreadStateException
```

### Remember

```text
start()
   ↓
new thread execution
   ↓
run()
```

Direct:

```text
run()
   ↓
normal method call
   ↓
current thread
```

---

# 5. Implementing `Runnable`

Instead of extending `Thread`, implement `Runnable`.

```java
class MyTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Task is running");
    }
}

public class Main {

    public static void main(String[] args) {

        MyTask task = new MyTask();

        Thread thread = new Thread(task);

        thread.start();
    }
}
```

---

# 6. Why Prefer `Runnable`?

`Runnable` represents the **task**, while `Thread` represents the **execution mechanism**.

With `Thread`:

```java
class MyTask extends Thread {
}
```

your class already uses its single inheritance slot.

With `Runnable`:

```java
class MyTask extends SomeClass implements Runnable {
}
```

you can still extend another class.

### Interview Answer

> Runnable provides better separation between the task and the thread that executes it, and it avoids the limitation of Java's single inheritance.

---

# 7. Runnable Using Lambda

`Runnable` is a functional interface.

Therefore:

```java
Runnable task = () -> {
    System.out.println("Task running");
};

Thread thread = new Thread(task);

thread.start();
```

Or:

```java
new Thread(() ->
    System.out.println("Task running")
).start();
```

---

# 8. Thread Lifecycle

A Java thread goes through different states during its lifetime.

```text
                 Thread object created
                         |
                         ▼
                       NEW
                         |
                       start()
                         |
                         ▼
                    RUNNABLE
                    /   |                       /    |                       ▼     ▼      ▼
             BLOCKED WAITING TIMED_WAITING
                  \     |      /
                   \    |     /
                    ▼   ▼    ▼
                    RUNNABLE
                       |
                       ▼
                   TERMINATED
```

### Important Interview Point

Java's official `Thread.State` enum has **six states**:

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

There is **no separate `RUNNING` state** in `Thread.State`.

A thread that is executing or ready to execute is represented by `RUNNABLE`.

---

# 9. NEW State

A thread is in `NEW` when the `Thread` object has been created but `start()` has not been called.

```java
Thread thread = new Thread(() -> {
    System.out.println("Hello");
});
```

At this point:

```text
NEW
```

The thread has not started executing.

---

# 10. RUNNABLE State

After:

```java
thread.start();
```

the thread moves to `RUNNABLE`.

```text
NEW
 ↓
start()
 ↓
RUNNABLE
```

`RUNNABLE` means the thread is eligible to run and may be running when scheduled by the JVM/OS.

### Interview Trap

Do not say:

> RUNNABLE means the thread is definitely executing.

Better:

> RUNNABLE represents a thread that is ready to run or is currently running.

---

# 11. BLOCKED State

A thread becomes `BLOCKED` when it is waiting to acquire a monitor lock.

Example:

```java
synchronized (lock) {
    // critical section
}
```

If Thread 1 already owns the lock:

```text
Thread 1 → owns Lock A

Thread 2 → tries to acquire Lock A
             ↓
          BLOCKED
```

Once Thread 1 releases the lock, Thread 2 can become runnable.

---

# 12. WAITING State

A thread is in `WAITING` when it waits indefinitely for another thread/action.

Common examples:

```java
object.wait();
thread.join();
```

Example:

```java
synchronized (lock) {
    lock.wait();
}
```

The thread waits until another thread performs an appropriate notification such as:

```java
lock.notify();
```

or:

```java
lock.notifyAll();
```

### Important

`wait()` must be called while the thread owns that object's monitor.

---

# 13. TIMED_WAITING State

A thread enters `TIMED_WAITING` when it waits for a specified amount of time.

Examples:

```java
Thread.sleep(1000);
```

```java
object.wait(1000);
```

```java
thread.join(1000);
```

Conceptually:

```text
RUNNABLE
   ↓
sleep(1000)
   ↓
TIMED_WAITING
   ↓
time expires
   ↓
RUNNABLE
```

---

# 14. TERMINATED State

A thread reaches `TERMINATED` when its `run()` method completes or the thread otherwise finishes execution.

```java
Thread thread = new Thread(() -> {
    System.out.println("Task completed");
});

thread.start();
```

After the task finishes:

```text
TERMINATED
```

A terminated thread cannot be started again.

```java
thread.start(); // ❌ if already terminated
```

---

# 15. Complete Lifecycle Example

```java
class MyTask implements Runnable {

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Task completed");
    }
}

public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(new MyTask());

        System.out.println(thread.getState());
        // NEW

        thread.start();

        System.out.println(thread.getState());
        // Usually RUNNABLE, but exact timing is nondeterministic
    }
}
```

### Important

Thread states are **time-dependent**. You should not write code assuming that a thread will always be observed in a particular state at a particular line.

---

# 16. Thread State Summary

| State | Meaning | Common Cause |
|---|---|---|
| `NEW` | Created but not started | `new Thread()` |
| `RUNNABLE` | Ready/running | `start()` |
| `BLOCKED` | Waiting for monitor lock | `synchronized` lock unavailable |
| `WAITING` | Waiting indefinitely | `wait()`, `join()` |
| `TIMED_WAITING` | Waiting for a specified time | `sleep()`, timed `wait()`, timed `join()` |
| `TERMINATED` | Execution completed | `run()` finished |

---

# 17. `sleep()` and Thread State

```java
Thread.sleep(2000);
```

The current thread enters:

```text
TIMED_WAITING
```

After the sleep duration:

```text
TIMED_WAITING
       ↓
    RUNNABLE
```

### Important

`sleep()` does **not release a monitor lock** held by the thread.

---

# 18. `wait()` and Thread State

```java
synchronized (lock) {
    lock.wait();
}
```

The thread enters:

```text
WAITING
```

and releases the monitor for that object.

Another thread can notify it:

```java
synchronized (lock) {
    lock.notify();
}
```

The waiting thread must reacquire the monitor before continuing.

---

# 19. `wait()` vs `sleep()`

| `wait()` | `sleep()` |
|---|---|
| Defined in `Object` | Defined in `Thread` |
| Used for thread coordination | Used to pause execution |
| Releases the object's monitor | Does not release monitor locks |
| Usually used with `notify()` / `notifyAll()` | No notification required |
| Can be indefinite or timed | Timed |

### Interview Answer

> `wait()` is used for inter-thread coordination and releases the object's monitor, while `sleep()` simply pauses the current thread and does not release its monitor locks.

---

# 20. `join()` and Thread Lifecycle

```java
Thread t1 = new Thread(() -> {
    System.out.println("Task 1");
});

t1.start();

t1.join();

System.out.println("Main continues");
```

The calling thread waits for `t1` to finish.

```text
Main
 |
 +---- start t1
 |
 +---- join()
 |       |
 |       ↓
 |    wait for t1
 |       |
 |       ↓
 +---- t1 TERMINATED
 |
 +---- Main continues
```

---

# 21. `join()` and WAITING/TIMED_WAITING

```java
t1.join();
```

The calling thread enters:

```text
WAITING
```

Whereas:

```java
t1.join(1000);
```

causes the calling thread to enter:

```text
TIMED_WAITING
```

for up to the specified duration.

---

# 22. Thread Creation — Interview Comparison

| Approach | Main Idea | Interview Point |
|---|---|---|
| Extend `Thread` | Thread + task in same class | Simple but uses inheritance |
| Implement `Runnable` | Task separated from Thread | More flexible |
| Lambda `Runnable` | Concise task definition | Good for simple tasks |
| `Callable` | Task with return value | Used with `Future`/executors |

---

# 23. Common Interview Questions

### Q1. What are the states of a Java thread?

> NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, and TERMINATED.

### Q2. Is RUNNING a Java thread state?

> No. Java's `Thread.State` has no separate RUNNING state. A running or ready-to-run thread is represented by RUNNABLE.

### Q3. Difference between BLOCKED and WAITING?

> BLOCKED means the thread is waiting to acquire a monitor lock. WAITING means it is waiting indefinitely for another thread/action, such as notification or completion of another thread.

### Q4. What causes TIMED_WAITING?

> Methods such as `Thread.sleep()`, timed `wait()`, and timed `join()`.

### Q5. Can a terminated thread be started again?

No.

```java
thread.start();
thread.start(); // ❌
```

This results in:

```text
IllegalThreadStateException
```

### Q6. What happens when `run()` is called directly?

> It executes like a normal method on the current thread. No new thread is created.

### Q7. Why use Runnable?

> It separates the task from the execution mechanism and avoids the single-inheritance limitation of extending Thread.

### Q8. Does `sleep()` release the lock?

> No. `sleep()` does not release monitor locks.

### Q9. Does `wait()` release the lock?

> Yes, `wait()` releases the monitor of the object on which it is called, and the thread later needs to reacquire it before continuing.

### Q10. Can `wait()` be called outside synchronized code?

Normally, no. The current thread must own the object's monitor; otherwise Java throws `IllegalMonitorStateException`.

---

# 24. Interview Revision Diagram

```text
                 new Thread(...)
                       |
                       ▼
                     NEW
                       |
                     start()
                       |
                       ▼
                  RUNNABLE
                 /    |                     /     |                     ▼      ▼       ▼
           BLOCKED  WAITING  TIMED_WAITING
               |       |        |
               └───────┴────────┘
                       |
                       ▼
                   RUNNABLE
                       |
                 run() finishes
                       |
                       ▼
                  TERMINATED
```

### Remember

```text
NEW
  → Thread created

RUNNABLE
  → Ready/running

BLOCKED
  → Waiting for monitor lock

WAITING
  → Waiting indefinitely

TIMED_WAITING
  → Waiting for a specified time

TERMINATED
  → Execution completed
```

---

# 25. ⭐ 2–3 YOE Must-Know

Before moving to advanced multithreading, be comfortable answering these without code:

1. What is a thread?
2. How do you create a thread in Java?
3. Thread vs Runnable?
4. Why is Runnable generally preferred?
5. `start()` vs `run()`?
6. What are the six Java thread states?
7. BLOCKED vs WAITING?
8. WAITING vs TIMED_WAITING?
9. What causes a thread to enter each state?
10. `sleep()` vs `wait()`?
11. Does `sleep()` release a lock?
12. Does `wait()` release a lock?
13. What does `join()` do?
14. Can a thread be started twice?
15. Is RUNNING a separate Java thread state?

---

# Quick Cheat Sheet

```text
Thread
  ↓
Smallest execution unit

Thread creation
  ↓
Thread / Runnable / Callable

start()
  ↓
Starts new thread

run()
  ↓
Task logic
Direct call = current thread

NEW
  ↓ start()
RUNNABLE
  ├── BLOCKED       → waiting for monitor
  ├── WAITING       → indefinite wait
  └── TIMED_WAITING → timed wait
  ↓
TERMINATED

sleep()
  → TIMED_WAITING
  → does NOT release monitor

wait()
  → WAITING / TIMED_WAITING
  → releases object's monitor

join()
  → waits for another thread to finish
