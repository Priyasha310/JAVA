# Thread Joining, Daemon Thread, Thread Priority

> Interview-focused notes covering Producer-Consumer, `Thread.sleep()`, `join()`, Thread Priority, and Daemon Threads.

---

# 1. Producer-Consumer Problem

## What is Producer-Consumer?

The **Producer-Consumer problem** is a classic multithreading problem where:

- **Producer** creates/produces data.
- **Consumer** reads/consumes data.
- Both share a common buffer/queue.
- The producer should wait when the buffer is full.
- The consumer should wait when the buffer is empty.

### Example

```text
Producer
   ↓
Produces data
   ↓
Shared Buffer / Queue
   ↓
Consumer
   ↓
Consumes data
```

### Main challenge

Both threads access the same shared resource, so we need **thread synchronization**.

---

# 2. Producer-Consumer Using `wait()` and `notifyAll()`

A simple implementation can use:

- `synchronized`
- `wait()`
- `notifyAll()`

## How it works

### Producer

```text
Buffer full?
    ↓ Yes
   wait()
    ↓
Buffer has space
    ↓
Produce item
    ↓
notifyAll()
```

### Consumer

```text
Buffer empty?
    ↓ Yes
   wait()
    ↓
Item available
    ↓
Consume item
    ↓
notifyAll()
```

---

# 3. Why `while` Instead of `if`?

Use:

```java
while (buffer.isEmpty()) {
    buffer.wait();
}
```

instead of:

```java
if (buffer.isEmpty()) {
    buffer.wait();
}
```

Because after a thread wakes up, the condition should be **checked again**.

A thread may wake up because another thread called `notifyAll()`, but the condition may no longer be true when it gets the lock.

### Interview Answer

> We use `while` with `wait()` to re-check the condition after waking up and before continuing execution.

---

# 4. `wait()` vs `sleep()`

This is a very common interview question.

| `wait()` | `sleep()` |
|---|---|
| Defined in `Object` | Defined in `Thread` |
| Used for thread communication | Used to pause execution |
| Releases the monitor lock | Does not release the lock |
| Must be called while owning the object's monitor | Does not require synchronized block |
| Wakes through `notify()`, `notifyAll()`, timeout, or interruption | Wakes after specified time or interruption |

### Example

```java
synchronized (lock) {

    // wait() releases the lock while waiting.
    lock.wait();
}
```

```java
// sleep() pauses the current thread.
// It does NOT release any lock held by the thread.
Thread.sleep(1000);
```

### Easy Memory Trick

```text
wait()  → Communication → releases lock
sleep() → Pause → keeps lock
```

---

# 5. `Thread.sleep()` — STOP / SUSPEND

## What does `Thread.sleep()` do?

`Thread.sleep()` pauses the **currently executing thread** for a specified amount of time.

```java
Thread.sleep(2000);
```

This means:

```text
Current thread
     ↓
Sleep for 2 seconds
     ↓
Become runnable again
```

### Example

```java
public class SleepExample {

    public static void main(String[] args) {

        System.out.println("Start");

        try {
            // Pause the main thread for 2 seconds.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // Restore the interrupt status.
            Thread.currentThread().interrupt();
        }

        System.out.println("End");
    }
}
```

---

# 6. Important `sleep()` Interview Points

### 1. Does `sleep()` release the lock?

**No.**

```java
synchronized (lock) {

    // The thread sleeps but still holds the lock.
    Thread.sleep(2000);
}
```

Another thread cannot enter the synchronized section until the sleeping thread releases the lock.

### 2. Is `sleep()` deprecated?

**No.**

`Thread.sleep()` is still a valid and commonly used API.

What is deprecated is the old thread-control method:

```java
Thread.suspend()
```

---

# 7. Why `Thread.suspend()` Is Deprecated

Older Java provided:

```java
thread.suspend();
thread.resume();
```

These methods are deprecated because they can cause **deadlocks**.

### Example problem

Suppose Thread A holds a lock:

```text
Thread A
   ↓
Acquires Lock
   ↓
suspend()
   ↓
Thread A stops while holding Lock
```

Now Thread B needs the same lock:

```text
Thread B
   ↓
Needs Lock
   ↓
Blocked forever
```

Thread A cannot continue because it is suspended, while Thread B cannot continue because it needs A's lock.

### Interview Answer

> `Thread.suspend()` is deprecated because it can suspend a thread while it holds a lock, potentially causing deadlock. Modern Java uses coordination mechanisms such as `wait()/notify()`, interruption, executors, and higher-level concurrency utilities instead.

---

# 8. `Thread.join()`

`join()` makes the **current thread wait until another thread finishes**.

### Example

```java
public class JoinExample {

    public static void main(String[] args) {

        Thread worker = new Thread(() -> {

            try {
                // Simulate some work.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }

            System.out.println("Worker finished");

        });

        worker.start();

        try {
            // Main thread waits until worker completes.
            worker.join();
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }

        // This executes only after worker finishes.
        System.out.println("Main thread continues");
    }
}
```

### Output order

```text
Worker finished
Main thread continues
```

---

# 9. Why Use `join()`?

Without `join()`:

```text
Main Thread ───────────────→ continues
                 ↘
Worker Thread ─────────────→ still running
```

With `join()`:

```text
Main Thread ─────→ join() ─────────→ continues
                       ↑
                       │
Worker Thread ─────────┘
             finishes
```

### Interview Answer

> `join()` is used when one thread needs to wait for another thread to complete before continuing.

---

# 10. `join(timeout)`

You can also specify a maximum waiting time.

```java
try {

    // Main thread waits for at most 2 seconds
    // for the worker thread to finish.
    worker.join(2000);

} catch (InterruptedException e) {

    // Restore interrupt status.
    Thread.currentThread().interrupt();
}
```

Important:

```text
join()
→ Wait until thread finishes

join(2000)
→ Wait at most 2 seconds
```

If the worker is still running after 2 seconds, the current thread continues.

---

# 11. Thread Priority

Every Java thread has a priority.

Java provides:

```java
Thread.MIN_PRIORITY
Thread.NORM_PRIORITY
Thread.MAX_PRIORITY
```

Values:

```text
MIN_PRIORITY  = 1
NORM_PRIORITY = 5
MAX_PRIORITY  = 10
```

Default:

```text
NORM_PRIORITY = 5
```

---

# 12. Setting Thread Priority

```java
public class PriorityExample {

    public static void main(String[] args) {

        Thread lowPriorityThread = new Thread(() -> {

            // Task for the low-priority thread.
            System.out.println("Low priority thread");

        });

        Thread highPriorityThread = new Thread(() -> {

            // Task for the high-priority thread.
            System.out.println("High priority thread");

        });

        // Set priority values.
        lowPriorityThread.setPriority(Thread.MIN_PRIORITY);
        highPriorityThread.setPriority(Thread.MAX_PRIORITY);

        lowPriorityThread.start();
        highPriorityThread.start();
    }
}
```

---

# 13. Important Thread Priority Interview Point

**Higher priority does NOT guarantee that the thread will execute first.**

Thread scheduling depends on:

- JVM implementation
- Operating system
- Scheduler
- Available CPU resources

So don't say:

> "Priority 10 always executes before priority 1."

Instead say:

> **Thread priority is a scheduling hint; it does not guarantee execution order.**

---

# 14. Thread Priority Methods

| Method | Functionality |
|---|---|
| `setPriority(int)` | Sets the thread priority |
| `getPriority()` | Returns the thread priority |

Example:

```java
thread.setPriority(Thread.MAX_PRIORITY);

System.out.println(thread.getPriority());
```

Output:

```text
10
```

---

# 15. Daemon Thread

A **daemon thread** is a background thread that supports other application threads.

Examples of background activities can include:

- Monitoring
- Cleanup
- Background services

The JVM does **not** wait for daemon threads to finish.

### Main Rule

> JVM exits when all **non-daemon/user threads** have finished.

---

# 16. Creating a Daemon Thread

```java
public class DaemonThreadExample {

    public static void main(String[] args) {

        Thread daemonThread = new Thread(() -> {

            while (true) {

                try {
                    // Simulate background work.
                    Thread.sleep(1000);

                    System.out.println(
                        "Daemon thread is running"
                    );

                } catch (InterruptedException e) {

                    // Restore interrupt status and stop the loop.
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        });

        // A thread must be marked as daemon BEFORE start().
        daemonThread.setDaemon(true);

        daemonThread.start();

        try {
            // Main/user thread performs its work for 3 seconds.
            Thread.sleep(3000);
        } catch (InterruptedException e) {

            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }

        System.out.println("Main thread finished");

        // JVM can exit now because only the daemon thread remains.
    }
}
```

---

# 17. Important Daemon Thread Rules

### Rule 1: Set daemon before `start()`

Correct:

```java
thread.setDaemon(true);
thread.start();
```

Incorrect:

```java
thread.start();
thread.setDaemon(true); // IllegalStateException
```

Once a thread has started, its daemon status cannot be changed.

### Rule 2: JVM does not wait for daemon threads

```text
User Thread
    ↓
Finishes
    ↓
No other non-daemon threads
    ↓
JVM exits

Daemon Thread
    ↓
May be terminated automatically
```

### Rule 3: Daemon threads should not be used for critical work

Don't depend on a daemon thread for important operations such as:

- Saving critical data
- Completing a transaction
- Writing an important file
- Sending a required business operation

Because the JVM may terminate before the daemon finishes.

---

# 18. User Thread vs Daemon Thread

| User Thread | Daemon Thread |
|---|---|
| Normal application thread | Background/support thread |
| JVM waits for it | JVM does not wait for it |
| Can keep JVM alive | Cannot keep JVM alive |
| Default for manually created threads | Must explicitly set daemon status |

### Example

```java
Thread thread = new Thread(() -> {
    System.out.println("Running");
});

System.out.println(thread.isDaemon()); // false
```

---

# 19. `isDaemon()`

Use `isDaemon()` to check whether a thread is a daemon thread.

```java
Thread thread = new Thread(() -> {

    // Background task.
    System.out.println("Running");

});

thread.setDaemon(true);

System.out.println(thread.isDaemon()); // true

thread.start();
```

---

# 20. Quick Comparison

| Concept | Main Purpose | Key Point |
|---|---|---|
| Producer-Consumer | Coordinate producer and consumer | Shared buffer + synchronization |
| `wait()` | Wait for a condition | Releases monitor |
| `notify()` | Wake one waiting thread | Must own monitor |
| `notifyAll()` | Wake all waiting threads | Must own monitor |
| `sleep()` | Pause current thread | Does not release lock |
| `join()` | Wait for another thread | Current thread waits |
| Priority | Influence scheduling | No execution guarantee |
| Daemon | Background support | JVM doesn't wait for it |

---

# 21. Most Important Interview Differences

## `wait()` vs `sleep()`

```text
wait()
→ Object method
→ Used for communication
→ Releases lock

sleep()
→ Thread method
→ Used for pausing
→ Does NOT release lock
```

## `sleep()` vs `join()`

```text
sleep()
→ Pause current thread for a duration

join()
→ Current thread waits for another thread
```

## User vs Daemon Thread

```text
User Thread
→ JVM waits

Daemon Thread
→ JVM does not wait
```

## Priority

```text
1 → MIN
5 → NORMAL
10 → MAX

Higher priority ≠ guaranteed first execution
```

---

# 22. Interview Questions

### Q1. What is Producer-Consumer?

> A concurrency problem where producers generate data and consumers process it using a shared buffer. Synchronization is required to prevent race conditions and coordinate when the buffer is full or empty.

### Q2. Why use `wait()` inside a `while` loop?

> To re-check the condition after the thread wakes up before continuing.

### Q3. Does `wait()` release the lock?

> Yes, `wait()` releases the object's monitor while the thread is waiting.

### Q4. Does `sleep()` release the lock?

> No. `sleep()` pauses the thread but does not release monitors held by it.

### Q5. Why is `Thread.suspend()` deprecated?

> It can suspend a thread while it holds a lock and can therefore cause deadlock.

### Q6. What does `join()` do?

> It makes the current thread wait until the target thread finishes.

### Q7. Does higher thread priority guarantee earlier execution?

> No. Priority is only a scheduling hint and execution order is not guaranteed.

### Q8. What is a daemon thread?

> A background/support thread that does not prevent the JVM from exiting.

### Q9. Can we call `setDaemon(true)` after `start()`?

> No. It must be called before the thread is started.

### Q10. When does JVM terminate?

> When all non-daemon threads have finished.

---

# 23. Final Revision

```text
Producer-Consumer
→ Shared buffer
→ Producer adds
→ Consumer removes
→ wait() / notifyAll()
→ synchronize access

sleep()
→ Pause current thread
→ Does NOT release lock
→ Thread.sleep()

join()
→ Wait for another thread to finish

Priority
→ 1 to 10
→ Default = 5
→ Higher priority does NOT guarantee execution first

Daemon
→ Background thread
→ JVM does not wait for it
→ setDaemon(true) before start()
```

## One-Line Interview Summary

> **Producer-Consumer demonstrates thread coordination, `sleep()` pauses a thread without releasing its lock, `join()` waits for another thread to finish, thread priority provides a scheduling hint, and daemon threads perform background work without preventing JVM termination.**
