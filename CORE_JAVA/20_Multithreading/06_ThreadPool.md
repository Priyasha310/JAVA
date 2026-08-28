# Java Thread Pool — Interview Notes

> Interview-focused notes covering Thread Pool, `ExecutorService`, `ThreadPoolExecutor`, thread-pool sizing, lifecycle, and common interview questions.

---

# 1. What is a Thread Pool?

A **Thread Pool** is a collection of reusable worker threads used to execute multiple tasks.

Instead of creating a new thread for every task:

```text
Task 1 → new Thread
Task 2 → new Thread
Task 3 → new Thread
```

we create a fixed/reusable set of threads:

```text
              Thread Pool
          ┌─────┬─────┬─────┐
          ↓     ↓     ↓
         T1    T2    T3
          ↑     ↑     ↑
          └──── Tasks ──────
```

### Why use a Thread Pool?

- Reuses threads
- Reduces thread-creation overhead
- Controls the number of concurrent threads
- Prevents creating too many threads
- Provides task queuing
- Makes task execution easier to manage

### Interview answer

> A thread pool maintains a set of reusable worker threads and assigns submitted tasks to them. It improves performance by reusing threads and prevents uncontrolled thread creation.

---

# 2. Why Not Create a New Thread for Every Task?

Creating threads repeatedly has overhead.

```java
new Thread(task1).start();
new Thread(task2).start();
new Thread(task3).start();
```

With many tasks:

```text
1000 tasks
   ↓
1000 threads
   ↓
High memory usage
High context switching
Poor performance
```

A thread pool can instead use a limited number of worker threads:

```text
1000 tasks
   ↓
Queue
   ↓
10 worker threads
```

The workers repeatedly pick tasks from the queue.

---

# 3. Executor Framework

Java provides the **Executor Framework** in:

```text
java.util.concurrent
```

Important interfaces/classes:

```text
Executor
   ↓
ExecutorService
   ↓
ThreadPoolExecutor
```

### Executor

Basic interface for executing tasks.

```java
executor.execute(task);
```

### ExecutorService

Provides additional lifecycle and task-management methods.

Examples:

```java
submit()
shutdown()
shutdownNow()
isShutdown()
isTerminated()
```

### ThreadPoolExecutor

Provides detailed control over thread-pool behavior.

---

# 4. Common Thread Pool Types

Using `Executors`:

| Method | Purpose |
|---|---|
| `newFixedThreadPool(n)` | Fixed number of worker threads |
| `newSingleThreadExecutor()` | One worker thread |
| `newCachedThreadPool()` | Creates/reuses threads dynamically |
| `newScheduledThreadPool(n)` | Scheduled/delayed tasks |

### Interview note

For production systems, many developers prefer explicitly configuring `ThreadPoolExecutor` rather than blindly using `Executors`, because you can control the queue and rejection policy.

---

# 5. ThreadPoolExecutor

Constructor:

```java
ThreadPoolExecutor(
    corePoolSize,
    maximumPoolSize,
    keepAliveTime,
    timeUnit,
    workQueue,
    threadFactory,
    rejectedExecutionHandler
);
```

### Seven important parameters

| Parameter | Meaning |
|---|---|
| `corePoolSize` | Number of core worker threads |
| `maximumPoolSize` | Maximum number of threads |
| `keepAliveTime` | How long extra threads can remain idle |
| `unit` | Time unit for keep-alive |
| `workQueue` | Holds waiting tasks |
| `threadFactory` | Creates worker threads |
| `RejectedExecutionHandler` | Handles tasks rejected by the pool |

---

# 6. How ThreadPoolExecutor Executes a Task

This is one of the most important interview topics.

Suppose:

```java
corePoolSize = 2
maximumPoolSize = 4
queueCapacity = 2
```

Tasks arrive:

```text
Task arrives
    ↓
Are core threads available?
    ↓
Create worker thread until corePoolSize
    ↓
If core threads are busy
    ↓
Put task into queue
    ↓
If queue is full
    ↓
Create additional threads up to maximumPoolSize
    ↓
If maximumPoolSize is reached
    ↓
Reject task
```

### Example

```text
corePoolSize = 2
maximumPoolSize = 4
queue capacity = 2
```

Approximate flow:

```text
Task 1 → Thread 1
Task 2 → Thread 2

Task 3 → Queue
Task 4 → Queue

Task 5 → Thread 3
Task 6 → Thread 4

Task 7 → Rejected
```

### Important interview point

A common mistake is saying:

> "Once core threads are busy, ThreadPoolExecutor immediately creates more threads."

That is **not correct**.

The normal order is:

```text
1. Create threads up to corePoolSize
2. Queue tasks
3. If queue is full, create threads up to maximumPoolSize
4. If maximum is reached, reject
```

---

# 7. Core Pool Size

`corePoolSize` is the number of core worker threads maintained by the pool.

Example:

```java
corePoolSize = 2
```

Initially, the pool can create up to two core workers as tasks arrive.

### Important

`corePoolSize` does **not** necessarily mean:

> "Exactly two threads always exist."

Threads are created as tasks are submitted, unless the pool is configured to prestart core threads.

---

# 8. Interview Question: Why corePoolSize = 2?

### Question

> In a ThreadPool, why did you choose `corePoolSize` as 2 instead of 10 or 15? What is the logic?

### Best answer

There is **no universally correct number** such as 2, 10, or 15.

The value should be chosen based on:

- Type of workload
- CPU cores
- CPU-bound vs I/O-bound tasks
- Task execution time
- Blocking behavior
- Memory available
- Expected throughput
- Queue capacity
- Maximum pool size

### CPU-bound tasks

For CPU-heavy work, you generally don't want far more active threads than available CPU cores.

A common starting point is:

```text
threads ≈ number of CPU cores
```

or sometimes:

```text
cores + 1
```

Then benchmark and tune.

### I/O-bound tasks

For I/O-heavy tasks, threads may spend significant time waiting for:

- Database
- Network
- File I/O
- External APIs

Therefore, more threads may be useful because while one thread waits, another can execute.

A rough starting formula sometimes used is:

```text
Threads ≈ cores × (1 + wait time / compute time)
```

This is only a starting point, not a fixed rule.

### Strong interview answer

> I would not choose corePoolSize arbitrarily. I would consider whether the workload is CPU-bound or I/O-bound, the available CPU cores, blocking time, expected concurrency, memory limits, and queue size. For CPU-bound tasks I would start close to the number of cores, while I/O-bound workloads can generally use more threads, followed by load testing and tuning.

---

# 9. CPU-Bound vs I/O-Bound

## CPU-Bound

The task spends most of its time using the CPU.

Examples:

- Image processing
- Complex calculations
- Compression
- Encryption

```text
Thread
  ↓
Mostly CPU work
```

Too many threads can cause excessive context switching.

---

## I/O-Bound

The task spends significant time waiting.

Examples:

- Database calls
- REST API calls
- File operations
- Network operations

```text
Thread
  ↓
Send request
  ↓
WAIT
  ↓
Response
  ↓
Continue work
```

More threads can help utilize CPU while other threads are waiting.

---

# 10. Queue

The work queue stores tasks that cannot immediately be executed.

Example:

```java
new ArrayBlockingQueue<>(10)
```

means the queue can hold up to 10 waiting tasks.

```text
Workers
  ↓
 ┌───────────────┐
 │ Task 3        │
 │ Task 4        │
 │ Task 5        │
 │ ...           │
 └───────────────┘
       Queue
```

### Common queues

| Queue | Characteristics |
|---|---|
| `ArrayBlockingQueue` | Bounded |
| `LinkedBlockingQueue` | Optionally bounded |
| `SynchronousQueue` | No stored capacity; direct handoff |
| `PriorityBlockingQueue` | Priority-based ordering |

---

# 11. Why Bounded Queue?

A bounded queue prevents unlimited task accumulation.

Without a practical limit:

```text
Tasks keep arriving
      ↓
Queue keeps growing
      ↓
Memory usage increases
      ↓
Possible OutOfMemoryError
```

A bounded queue provides **backpressure**.

---

# 12. Maximum Pool Size

`maximumPoolSize` defines the maximum number of worker threads.

Example:

```java
corePoolSize = 2
maximumPoolSize = 5
```

The pool can grow beyond 2 when:

1. Core threads are busy.
2. The work queue is full.
3. More tasks arrive.

It can grow up to 5 workers.

---

# 13. Keep-Alive Time

`keepAliveTime` controls how long **non-core/extra threads** can remain idle before being removed.

Example:

```java
keepAliveTime = 60
TimeUnit.SECONDS
```

means extra threads can be removed after being idle for 60 seconds.

### Note

By default, keep-alive mainly applies to threads beyond `corePoolSize`.

Core-thread timeout can be enabled separately with:

```java
allowCoreThreadTimeOut(true);
```

---

# 14. ThreadFactory

`ThreadFactory` controls how worker threads are created.

It can be used to customize:

- Thread name
- Priority
- Daemon status
- Uncaught exception handler

Example:

```java
ThreadFactory factory = runnable -> {
    Thread thread = new Thread(runnable);
    thread.setName("payment-worker");
    return thread;
};
```

Naming threads is useful for debugging and monitoring.

---

# 15. RejectedExecutionHandler

What happens when the pool cannot accept another task?

Example:

```text
Core threads → full
Queue       → full
Maximum     → reached
                ↓
             REJECT
```

Common policies:

| Policy | Behavior |
|---|---|
| `AbortPolicy` | Throws `RejectedExecutionException` |
| `CallerRunsPolicy` | Caller thread executes the rejected task, unless the executor is shut down |
| `DiscardPolicy` | Silently discards task, without throwing Exception |
| `DiscardOldestPolicy` | Removes oldest queued task to accomodate new task and retries | 

### Interview recommendation

`AbortPolicy` is often useful when rejection should be visible instead of silently losing work.

---

# 16. Thread Pool Lifecycle

Important methods:

### `shutdown()`

Stops accepting new tasks but allows already submitted tasks to finish.

```text
shutdown()
   ↓
No new tasks
   ↓
Existing tasks complete
   ↓
Pool terminates
```

### `shutdownNow()`

Attempts to stop currently executing tasks by interrupting worker threads and returns tasks that never started.

It does **not** guarantee immediate termination.

### `isShutdown()`

Checks whether shutdown has been initiated.

### `isTerminated()`

Returns true when shutdown has completed and all tasks have finished.

---

# ExecutorService Lifecycle

## Thread Pool State Transition

```mermaid
flowchart LR
    R[RUNNING] -->|shutdown()| S[SHUTDOWN]
    R -->|shutdownNow()| P[STOP]
    S -->|All tasks completed| T[TERMINATED]
    P -->|All tasks completed| T[TERMINATED]
    S -->|shutdownNow()| P
```

### Simple Flow

```text
                    shutdown()
               ┌─────────────────┐
               │                 ↓
          ┌──────────┐      ┌──────────┐
          │ RUNNING  │      │ SHUTDOWN │
          └──────────┘      └──────────┘
               │                  │
               │ shutdownNow()    │
               ↓                  │
          ┌──────────┐             │
          │   STOP   │─────────────┘
          └──────────┘
               │
               │ Tasks finish / terminate
               ↓
          ┌──────────────┐
          │ TERMINATED   │
          └──────────────┘
```

## States

| State | Meaning |
|---|---|
| **RUNNING** | Accepts new tasks and processes existing tasks |
| **SHUTDOWN** | `shutdown()` called; no new tasks accepted, but queued/existing tasks are allowed to complete |
| **STOP** | `shutdownNow()` called; no new tasks accepted, queued tasks are returned, and running tasks are interrupted on a best-effort basis |
| **TERMINATED** | All tasks have completed and the executor has fully stopped |

## Interview Point

### `shutdown()`

```java
executor.shutdown();
```

Graceful shutdown:

```text
RUNNING
   ↓ shutdown()
SHUTDOWN
   ↓ existing tasks complete
TERMINATED
```

### `shutdownNow()`

```java
executor.shutdownNow();
```

Immediate/best-effort shutdown:

```text
RUNNING
   ↓ shutdownNow()
STOP
   ↓ tasks terminate
TERMINATED
```

> **Important:** `shutdownNow()` does not guarantee that a running task stops immediately. It attempts to interrupt the worker threads.

### One-Line Interview Answer

> `shutdown()` moves the executor from RUNNING to SHUTDOWN and allows existing tasks to finish, while `shutdownNow()` moves it to STOP and attempts to interrupt running tasks; both eventually reach TERMINATED.

# 17. `shutdown()` vs `shutdownNow()`

| `shutdown()` | `shutdownNow()` |
|---|---|
| Graceful shutdown | Attempts immediate shutdown |
| No new tasks accepted | No new tasks accepted |
| Existing tasks continue | Attempts to interrupt running tasks |
| Queued tasks can execute | Returns queued tasks that never started |

### Interview answer

> Use `shutdown()` for graceful shutdown. Use `shutdownNow()` when you need to make a best-effort attempt to stop running tasks quickly.

---

# 18. ExecutorService `execute()` vs `submit()`

### `execute()`

Used for a `Runnable`.

```java
executor.execute(() -> {
    System.out.println("Task");
});
```

Does not return a `Future`.

### `submit()`

Can accept `Runnable` or `Callable`.

```java
Future<Integer> future = executor.submit(() -> 10);
```

Returns a `Future`.

```text
execute()
→ no Future

submit()
→ Future
```

---

# 19. Thread Pool and Future

When a task is submitted:

```java
Future<Integer> future =
    executor.submit(() -> 100);
```

The `Future` represents the pending result.

```text
submit()
   ↓
Future
   ↓
Task executes asynchronously
   ↓
future.get()
   ↓
Result
```

`future.get()` waits if the task has not completed.

---

# 20. Important Thread Pool Design Considerations

When selecting pool configuration, consider:

### 1. CPU cores

```text
Runtime.getRuntime().availableProcessors()
```

### 2. Task type

```text
CPU-bound
vs
I/O-bound
```

### 3. Blocking time

How long tasks wait for external resources.

### 4. Queue size

Too small:

```text
More rejections
```

Too large:

```text
More waiting
Potential memory pressure
```

### 5. Maximum pool size

Controls the upper concurrency limit.

### 6. Rejection policy

Defines what happens under overload.

### 7. Task nature

Ask:

- Can tasks block?
- Are tasks independent?
- Are tasks latency-sensitive?
- Is task ordering important?

---

# 21. Common Interview Questions

### Q1. What is a thread pool?

> A group of reusable worker threads that execute submitted tasks.

### Q2. Why use a thread pool?

> To reuse threads, control concurrency, reduce thread-creation overhead, and manage tasks through a queue.

### Q3. What is `corePoolSize`?

> The number of core worker threads maintained by a `ThreadPoolExecutor`.

### Q4. What is `maximumPoolSize`?

> The maximum number of worker threads the pool can create.

### Q5. When does ThreadPoolExecutor create threads beyond corePoolSize?

> When core threads are busy, the work queue is full, and additional tasks arrive, up to maximumPoolSize.

### Q6. Why not always use 10 or 15 threads?

> Thread count depends on workload, CPU cores, blocking behavior, memory, and throughput requirements. More threads are not always faster.

### Q7. What is a good pool size for CPU-bound work?

> A common starting point is around the number of available CPU cores, possibly cores + 1, followed by benchmarking.

### Q8. What about I/O-bound work?

> More threads may be useful because threads spend time waiting on I/O, but the value should still be determined through workload characteristics and load testing.

### Q9. What happens when the queue is full?

> If the pool has not reached `maximumPoolSize`, additional threads may be created. If maximum size is reached, the rejection policy is applied.

### Q10. What is keepAliveTime?

> The time an idle non-core thread can remain alive before being removed.

### Q11. `shutdown()` vs `shutdownNow()`?

> `shutdown()` performs graceful shutdown; `shutdownNow()` attempts to interrupt running tasks and returns tasks that never started.

### Q12. What is a RejectedExecutionHandler?

> It defines how the executor handles tasks that cannot be accepted because the pool and queue are saturated.

### Q13. Why use a bounded queue?

> To prevent unlimited task accumulation and memory pressure and to provide backpressure.

---

# 22. Example ThreadPoolExecutor

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    2,                              // Core pool size
    4,                              // Maximum pool size
    60,                             // Keep-alive time
    TimeUnit.SECONDS,               // Keep-alive unit
    new ArrayBlockingQueue<>(2),    // Queue capacity
    Executors.defaultThreadFactory(),
    new ThreadPoolExecutor.AbortPolicy()
);
```

### Configuration

```text
Core threads     = 2
Maximum threads  = 4
Queue capacity   = 2
Keep alive       = 60 seconds
Rejected policy  = AbortPolicy
```

### Possible task flow

```text
Task 1 → Worker 1
Task 2 → Worker 2

Task 3 → Queue
Task 4 → Queue

Task 5 → Worker 3
Task 6 → Worker 4

Task 7 → Rejected
```

---

# 23. Important Correction for Interviews

Do **not** say:

> "corePoolSize is the number of threads that always run."

Better:

> "`corePoolSize` defines the number of core workers that the pool maintains/uses under normal operation. Core threads are created as needed unless prestarted, and by default they do not time out."

Also do not say:

> "ThreadPoolExecutor creates maximum threads immediately."

Instead:

```text
Create up to corePoolSize
        ↓
Queue tasks
        ↓
Queue full?
        ↓
Create up to maximumPoolSize
        ↓
Still full?
        ↓
Reject
```

---

# 24. One-Minute Revision

```text
Thread Pool
→ Reusable worker threads.

ExecutorService
→ Manage task execution + lifecycle.

ThreadPoolExecutor
→ Detailed thread-pool configuration.

corePoolSize
→ Core worker-thread target.

maximumPoolSize
→ Maximum worker threads.

workQueue
→ Stores waiting tasks.

keepAliveTime
→ Idle timeout for extra threads.

ThreadFactory
→ Controls worker-thread creation.

RejectedExecutionHandler
→ Handles overload/rejected tasks.

shutdown()
→ Graceful shutdown.

shutdownNow()
→ Attempts to interrupt running tasks.

CPU-bound
→ Start around CPU-core count and benchmark.

I/O-bound
→ More threads may help because tasks spend time waiting.

Most important execution order:
Core threads → Queue → Extra threads → Reject
```
