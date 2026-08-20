# Java Thread Pool Types — Interview Notes

> Interview-focused notes covering **Fixed Thread Pool, Cached Thread Pool, Single Thread Executor, Work-Stealing Pool, and Fork/Join Pool**.

---

# 1. Fixed Thread Pool

A **Fixed Thread Pool** has a fixed number of worker threads.

```text
newFixedThreadPool(3)

        Thread Pool
      ┌────┬────┬────┐
      │ T1 │ T2 │ T3 │
      └────┴────┴────┘
             ↑
          Task Queue
```

### Creation

```java
Executors.newFixedThreadPool(n);
```

### Characteristics

- Fixed number of worker threads
- Tasks wait in the queue when all workers are busy
- Threads are reused
- Provides controlled concurrency

### Use cases

- Controlled background processing
- CPU-bound tasks with an appropriate pool size
- Workloads where you want a predictable concurrency limit

### Interview answer

> A fixed thread pool maintains a fixed number of worker threads. When all workers are busy, new tasks wait in the queue.

---

# 2. Cached Thread Pool

A **Cached Thread Pool** creates threads dynamically and reuses idle threads.

```java
Executors.newCachedThreadPool();
```

### Flow

```text
Task arrives
    ↓
Idle thread available?
 ┌──┴──┐
Yes    No
 ↓      ↓
Reuse  Create thread
```

### Characteristics

- Creates threads as needed
- Reuses idle threads
- Good for many short-lived asynchronous tasks
- Does not provide a fixed maximum thread count in the usual `Executors` configuration

### Important concern

A cached pool can create a **large number of threads** if tasks arrive faster than they complete.

```text
Many tasks
    ↓
Many threads
    ↓
High memory usage
+ context switching
```

### Use cases

- Many short-lived tasks
- Irregular bursts of work
- Asynchronous operations that usually complete quickly

### Interview answer

> A cached thread pool dynamically creates and reuses threads. It is useful for many short-lived asynchronous tasks, but it can create many threads under heavy load.

---

# 3. Fixed vs Cached

| Fixed Thread Pool | Cached Thread Pool |
|---|---|
| Fixed number of workers | Threads created dynamically |
| Tasks wait in queue | More threads can be created |
| Controlled concurrency | Less predictable concurrency |
| Good for controlled workloads | Good for short-lived tasks |
| Lower risk of thread explosion | Can create many threads |

### Easy memory trick

```text
Fixed
→ Fixed workers
→ Queue tasks

Cached
→ Create/reuse workers
→ Potentially many threads
```

---

# 4. Single Thread Executor

A **Single Thread Executor** uses only one worker thread.

```java
Executors.newSingleThreadExecutor();
```

```text
Task 1 ─┐
Task 2 ─┤
Task 3 ─┤→ Thread 1
Task 4 ─┘
```

Tasks execute sequentially.

### Characteristics

- One worker thread
- Tasks execute one at a time
- Tasks are processed in submission order
- If the worker terminates unexpectedly, the executor can replace it

### Use cases

- Sequential file processing
- Ordered event processing
- A background queue where only one task should execute at a time

### Interview answer

> A single thread executor has one worker thread, so submitted tasks execute sequentially.

---

# 5. Fixed vs Single Thread

| Fixed | Single |
|---|---|
| Multiple workers | One worker |
| Tasks can execute concurrently | Tasks execute sequentially |
| Controlled parallelism | No parallel execution inside the executor |
| `newFixedThreadPool(n)` | `newSingleThreadExecutor()` |

---

# 6. Work-Stealing Pool

Java provides:

```java
Executors.newWorkStealingPool();
```

It is backed by a **ForkJoinPool** and uses a **work-stealing algorithm**.

### Basic idea

Each worker can have its own task queue:

```text
Worker 1 → [A, B, C]
Worker 2 → [D]
Worker 3 → []
```

Worker 3 finishes its work and can steal a task:

```text
Worker 3
   ↓
Steals C
   ↓
Worker 1's queue
```

### Why work stealing?

It helps reduce idle worker time and can improve CPU utilization for parallel workloads.

### Use cases

- CPU-intensive parallel tasks
- Divide-and-conquer algorithms
- Independent parallel tasks

### Interview answer

> Work stealing allows an idle worker to take tasks from another worker's queue, improving worker utilization. Java's work-stealing pool is backed by `ForkJoinPool`.

---

# 7. ForkJoinPool

`ForkJoinPool` is designed mainly for **parallel divide-and-conquer tasks**.

```text
Large Task
    ↓
   fork
 ┌──┴──┐
 ↓     ↓
T1    T2
↓      ↓
split  split
↓      ↓
...    ...
 └──┬──┘
    join
      ↓
Final result
```

### Fork

Break a large task into smaller tasks.

### Join

Wait for subtasks and combine their results.

### Example use cases

- Recursive algorithms
- Large array processing
- Parallel calculations
- Divide-and-conquer problems

---

# 8. RecursiveTask vs RecursiveAction

Fork/Join provides two important task types.

## RecursiveTask<V>

Used when the task **returns a result**.

```text
RecursiveTask<Integer>
        ↓
returns Integer
```

Examples:

- Calculate sum
- Find maximum
- Search and return an object

## RecursiveAction

Used when the task **does not return a result**.

Examples:

- Update/process elements
- Perform a side effect on data

| RecursiveTask | RecursiveAction |
|---|---|
| Returns a result | No result |
| Generic `<V>` | No result type |
| `compute()` returns `V` | `compute()` returns `void` |

---

# 9. Work-Stealing Pool vs ForkJoinPool

These are closely related.

| Work-Stealing Pool | ForkJoinPool |
|---|---|
| Created using `Executors.newWorkStealingPool()` | Actual executor class |
| Backed by `ForkJoinPool` | Supports fork/join tasks directly |
| Provides an `ExecutorService` view | Specialized for fork/join |
| Uses work stealing | Uses work stealing |

### Important interview point

```text
Executors.newWorkStealingPool()
            ↓
       ForkJoinPool
```

The work-stealing pool uses `ForkJoinPool` internally.

---

# 10. When to Use Which?

## Fixed Thread Pool

Use when you want:

```text
Controlled number of concurrent workers
+
Tasks waiting in a queue
```

---

## Cached Thread Pool

Use when:

```text
Many short-lived tasks
+
Irregular task arrival
```

Be careful with workloads that can create very large numbers of concurrent tasks.

---

## Single Thread Executor

Use when:

```text
Tasks must execute sequentially
```

---

## Work-Stealing Pool

Use when:

```text
Independent parallel tasks
+
CPU-heavy workload
```

and work stealing can keep workers busy.

---

## ForkJoinPool

Use when:

```text
Large task
   ↓
Split recursively
   ↓
Execute subtasks in parallel
   ↓
Combine results
```

---

# 11. Quick Comparison

| Pool | Threads | Best For | Main Feature |
|---|---|---|---|
| Fixed | Fixed | Controlled concurrency | Fixed workers + queue |
| Cached | Dynamic | Short-lived async tasks | Creates/reuses threads |
| Single | 1 | Sequential tasks | One worker |
| Work-Stealing | Dynamic | Parallel CPU work | Work stealing |
| ForkJoinPool | Worker pool | Divide-and-conquer | Fork + Join |

---

# 12. Common Interview Questions

### Q1. What is a Fixed Thread Pool?

> A thread pool with a fixed number of worker threads. Additional tasks wait in a queue when all workers are busy.

### Q2. What happens when all Fixed Thread Pool threads are busy?

> New tasks are placed in the executor's work queue.

### Q3. What is a Cached Thread Pool?

> It dynamically creates threads when required and reuses idle threads.

### Q4. What is the main concern with Cached Thread Pool?

> It can create a large number of threads under heavy load, causing memory pressure and excessive context switching.

### Q5. What is a Single Thread Executor?

> An executor with one worker thread that executes submitted tasks sequentially.

### Q6. What is work stealing?

> An idle worker takes tasks from another worker's queue so available CPU resources can be better utilized.

### Q7. What is ForkJoinPool?

> A specialized executor for parallel divide-and-conquer tasks using fork/join and work-stealing techniques.

### Q8. Difference between RecursiveTask and RecursiveAction?

> `RecursiveTask` returns a result; `RecursiveAction` does not.

### Q9. Is WorkStealingPool the same as ForkJoinPool?

> Not exactly. `newWorkStealingPool()` creates an `ExecutorService` backed by a `ForkJoinPool`, while `ForkJoinPool` is the actual executor class.

### Q10. Which pool would you choose for CPU-bound tasks?

> A fixed-size pool or ForkJoinPool/work-stealing approach can be appropriate depending on the workload. The pool size should be based on available CPU cores and validated with benchmarking.

---

# 13. Important Interview Cautions

Do not say:

> "CachedThreadPool is always better because it creates more threads."

More threads do not automatically mean better performance.

Do not say:

> "ForkJoinPool creates a new thread for every subtask."

Instead:

> `ForkJoinPool` maintains a set of worker threads and uses work stealing to efficiently execute forked tasks.

---

# 14. One-Minute Revision

```text
Fixed Thread Pool
→ Fixed workers + task queue.

Cached Thread Pool
→ Creates/reuses threads dynamically.
→ Good for short-lived tasks.
→ Can create many threads.

Single Thread Executor
→ One worker.
→ Sequential execution.

Work-Stealing Pool
→ Backed by ForkJoinPool.
→ Idle workers steal tasks.
→ Good for parallel work.

ForkJoinPool
→ Divide-and-conquer.
→ fork() → split task.
→ join() → wait/combine.

RecursiveTask
→ Returns result.

RecursiveAction
→ No return value.
```
