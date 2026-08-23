# Java ExecutorService Shutdown & ScheduledThreadPoolExecutor — Interview Notes

---

# 1. ExecutorService Shutdown

An `ExecutorService` should be shut down when it is no longer needed.

```java
executor.shutdown();
```

Shutdown does **not** mean "kill all running tasks immediately".

It means:

```text
Stop accepting new tasks
        ↓
Allow already submitted tasks to finish
        ↓
Executor terminates
```

---

# 2. `shutdown()` vs `awaitTermination()`

This is a very common interview question.

## `shutdown()`

```java
executor.shutdown();
```

### What it does

- Stops accepting new tasks.
- Allows already submitted tasks to complete.
- Starts the shutdown process.
- Does **not wait** for tasks to finish.

```text
shutdown()
    ↓
Return immediately
    ↓
Tasks continue running
```

---

## `awaitTermination()`

```java
executor.awaitTermination(10, TimeUnit.SECONDS);
```

### What it does

- Waits for the executor to terminate.
- Allows you to specify a maximum waiting time.
- Usually called **after `shutdown()`**.

```text
shutdown()
    ↓
Stop accepting new tasks
    ↓
awaitTermination()
    ↓
Wait for completion
```

### Interview answer

> `shutdown()` initiates graceful shutdown but does not wait for tasks to finish. `awaitTermination()` waits for the executor to terminate for a specified amount of time.

---

# 3. Important: `awaitTermination()` Does NOT Shut Down the Pool

This is a common mistake.

```java
executor.awaitTermination(10, TimeUnit.SECONDS);
```

does **not** initiate shutdown.

You normally need:

```java
executor.shutdown();

executor.awaitTermination(
    10,
    TimeUnit.SECONDS
);
```

Think:

```text
shutdown()
→ "Please stop accepting new work."

awaitTermination()
→ "I will wait and see when you are completely stopped."
```

---

# 4. Scenario 1 — Task Submission After `shutdown()`

Example:

```java
ExecutorService poolObj =
        Executors.newFixedThreadPool(5);

// First task is accepted.
poolObj.submit(() -> {
    System.out.println("Task 1 is running");
});

// Start shutdown.
poolObj.shutdown();

// This task is submitted AFTER shutdown().
poolObj.submit(() -> {
    System.out.println("Task 2 is running");
});
```

### What happens?

The second `submit()` throws:

```text
RejectedExecutionException
```

### Why?

After:

```java
poolObj.shutdown();
```

the executor no longer accepts new tasks.

```text
RUNNING
   │
   │ shutdown()
   ↓
SHUTDOWN
   │
   ├── Existing tasks → allowed to finish
   │
   └── New tasks → RejectedExecutionException
```

### Important interview answer

> After `shutdown()`, the executor stops accepting new tasks. Submitting a new task results in `RejectedExecutionException`.

---

# 5. Scenario 1 — Proper Code

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class ShutdownSubmissionExample {

    public static void main(String[] args) {

        // Create a fixed thread pool with 5 worker threads.
        ExecutorService poolObj =
                Executors.newFixedThreadPool(5);

        // Submit the first task while the executor is RUNNING.
        poolObj.submit(() -> {
            System.out.println("Task 1 is running");
        });

        // Initiate graceful shutdown.
        // The executor will no longer accept new tasks.
        poolObj.shutdown();

        try {

            // This task is submitted AFTER shutdown().
            // It will be rejected.
            poolObj.submit(() -> {
                System.out.println("Task 2 is running");
            });

        } catch (RejectedExecutionException e) {

            // Handle the rejected task.
            System.out.println(
                    "Task 2 was rejected because executor is shut down"
            );
        }
    }
}
```

---

# 6. Scenario 2 — Existing Tasks After `shutdown()`

A very important distinction:

```text
shutdown()
→ Existing submitted tasks are NOT cancelled.
```

Example:

```java
executor.submit(task1);
executor.submit(task2);

executor.shutdown();
```

Both `task1` and `task2` are allowed to complete.

```text
Task 1 ───────────────→ completes
Task 2 ─────────────────────→ completes

shutdown()
   ↓
No new tasks
```

---

# 7. Scenario 3 — `shutdown()` + `awaitTermination()`

This is the common graceful shutdown pattern.

```java
executor.shutdown();

try {

    // Wait up to 10 seconds for all tasks to finish.
    if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {

        // Tasks did not finish within 10 seconds.
        executor.shutdownNow();
    }

} catch (InterruptedException e) {

    // The current thread was interrupted while waiting.
    executor.shutdownNow();

    // Restore interrupt status.
    Thread.currentThread().interrupt();
}
```

### Flow

```text
shutdown()
    ↓
No new tasks
    ↓
Wait using awaitTermination()
    ↓
 ┌───────────────┐
 │ Tasks finish?  │
 └───────┬───────┘
       Yes│       │No
          ↓       ↓
     TERMINATED  shutdownNow()
```

### Interview answer

> A common graceful shutdown pattern is to call `shutdown()`, wait with `awaitTermination()`, and if tasks do not finish within the timeout, use `shutdownNow()` as a best-effort fallback.

---

# 8. Scenario 4 — `awaitTermination()` Times Out

Suppose:

```java
executor.shutdown();

boolean terminated =
        executor.awaitTermination(
                5,
                TimeUnit.SECONDS
        );
```

If tasks need 20 seconds:

```text
Task duration = 20 sec
Wait timeout  = 5 sec

shutdown()
    ↓
awaitTermination(5 sec)
    ↓
5 seconds pass
    ↓
Returns false
    ↓
Tasks may still be running
```

### Important

`false` means:

> The executor did not terminate within the specified waiting time.

It does **not** mean the tasks were automatically cancelled.

---

# 9. Scenario 5 — `shutdownNow()`

```java
executor.shutdownNow();
```

`shutdownNow()`:

- Stops accepting new tasks.
- Attempts to interrupt currently running tasks.
- Returns tasks that were queued but never started.
- Does not guarantee that running tasks stop immediately.

```text
RUNNING
   ↓
shutdownNow()
   ↓
STOP
 ├── Queued tasks → returned
 └── Running tasks → interrupted (best effort)
```

### Important interview point

A task must cooperate with interruption.

For example:

```java
try {
    Thread.sleep(10000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

---

# 10. `shutdown()` vs `shutdownNow()`

| `shutdown()` | `shutdownNow()` |
|---|---|
| Graceful shutdown | Forceful/best-effort shutdown |
| No new tasks accepted | No new tasks accepted |
| Existing tasks can finish | Attempts to interrupt running tasks |
| Queued tasks can execute | Queued tasks that never started are returned |
| Does not interrupt running tasks | Attempts interruption |

### Interview one-liner

> `shutdown()` allows submitted tasks to finish, while `shutdownNow()` attempts to stop running tasks and returns tasks that never started.

---

# 11. `shutdown()` + `awaitTermination()` + `shutdownNow()`

A robust shutdown pattern:

```java
executor.shutdown();

try {

    // Wait for normal completion.
    if (!executor.awaitTermination(
            10,
            TimeUnit.SECONDS)) {

        // Tasks are taking too long.
        executor.shutdownNow();

        // Optionally wait again for termination.
        if (!executor.awaitTermination(
                10,
                TimeUnit.SECONDS)) {

            System.out.println(
                    "Executor did not terminate"
            );
        }
    }

} catch (InterruptedException e) {

    // If the current thread is interrupted,
    // make a best-effort attempt to stop the executor.
    executor.shutdownNow();

    // Restore interrupt status.
    Thread.currentThread().interrupt();
}
```

### Flow

```text
shutdown()
    ↓
Wait 10 sec
    ↓
Terminated?
 ┌──────┴──────┐
Yes           No
 ↓             ↓
Done       shutdownNow()
               ↓
         Wait again
```

---

# 12. Executor Lifecycle

```text
              shutdown()
RUNNING ─────────────────→ SHUTDOWN
   │                          │
   │ shutdownNow()            │ Existing tasks finish
   ↓                          ↓
 STOP ─────────────────────→ TERMINATED
        tasks terminate
```

### States

| State | Meaning |
|---|---|
| `RUNNING` | Accepts and executes tasks |
| `SHUTDOWN` | No new tasks; existing tasks can complete |
| `STOP` | `shutdownNow()` requested; running tasks are interrupted on a best-effort basis |
| `TERMINATED` | Executor has completely stopped |

---

# 13. Checking Executor State

### `isShutdown()`

```java
executor.isShutdown();
```

Returns `true` once shutdown has been initiated.

### `isTerminated()`

```java
executor.isTerminated();
```

Returns `true` only after the executor has completely terminated.

### Difference

```text
isShutdown()
→ Has shutdown started?

isTerminated()
→ Has shutdown completely finished?
```

---

# 14. ScheduledThreadPoolExecutor

`ScheduledThreadPoolExecutor` is used to execute tasks:

- After a delay
- Periodically

It is available through:

```java
java.util.concurrent.ScheduledThreadPoolExecutor
```

It implements `ScheduledExecutorService`.

Hierarchy:

```text
Executor
   ↓
ExecutorService
   ↓
ScheduledExecutorService
   ↓
ScheduledThreadPoolExecutor
```

---

# 15. Creating ScheduledThreadPoolExecutor

```java
ScheduledThreadPoolExecutor executor =
        new ScheduledThreadPoolExecutor(2);
```

This creates a scheduled executor with:

```text
2 core worker threads
```

---

# 16. `schedule()`

Used to execute a task **once after a delay**.

```java
executor.schedule(
        task,
        5,
        TimeUnit.SECONDS
);
```

Meaning:

```text
Wait 5 seconds
      ↓
Execute once
      ↓
Finish
```

### Example

```java
ScheduledThreadPoolExecutor executor =
        new ScheduledThreadPoolExecutor(2);

// Execute the task once after 5 seconds.
executor.schedule(
        () -> System.out.println("Executed"),
        5,
        TimeUnit.SECONDS
);
```

---

# 17. `scheduleAtFixedRate()`

Used for repeated execution at a fixed rate.

```java
executor.scheduleAtFixedRate(
        task,
        initialDelay,
        period,
        TimeUnit.SECONDS
);
```

Example:

```java
executor.scheduleAtFixedRate(
        () -> System.out.println("Running"),
        2,
        5,
        TimeUnit.SECONDS
);
```

Meaning:

```text
Initial delay = 2 sec

Run
 ↓
Target next execution = 5 sec later
 ↓
Run
 ↓
Target next execution = 5 sec later
 ↓
Run...
```

### Important concept

`fixed rate` is based on the **scheduled start times**, not simply "wait 5 seconds after the previous task finishes."

If a task takes longer than the period, executions do not overlap in the same scheduled executor thread for that periodic task. The next execution waits until the previous execution completes.

---

# 18. `scheduleWithFixedDelay()`

Used for repeated execution with a fixed delay **after the previous execution completes**.

```java
executor.scheduleWithFixedDelay(
        task,
        initialDelay,
        delay,
        TimeUnit.SECONDS
);
```

Example:

```java
executor.scheduleWithFixedDelay(
        () -> System.out.println("Running"),
        2,
        5,
        TimeUnit.SECONDS
);
```

Flow:

```text
Initial delay
     ↓
   Task
     ↓
Task finishes
     ↓
Wait 5 seconds
     ↓
   Task
     ↓
Task finishes
     ↓
Wait 5 seconds
     ↓
   Task
```

---

# 19. Fixed Rate vs Fixed Delay

This is a very common interview question.

| `scheduleAtFixedRate()` | `scheduleWithFixedDelay()` |
|---|---|
| Based on scheduled execution rate | Based on previous completion |
| Tries to maintain a fixed rate | Waits fixed delay after completion |
| Useful for periodic schedules | Useful when gap after completion matters |

### Easy memory trick

```text
Fixed Rate
→ "Run every X seconds."

Fixed Delay
→ "Wait X seconds after the previous run finishes."
```

---

# 20. Example: Fixed Rate vs Fixed Delay

Suppose:

```text
Task execution time = 3 sec
Period/delay = 5 sec
```

### Fixed Rate

```text
Start
  ↓
3 sec → Finish
  ↓
Next execution target is based on the schedule
  ↓
5 sec schedule
```

### Fixed Delay

```text
Start
  ↓
3 sec → Finish
  ↓
Wait 5 sec
  ↓
Next Start
```

So the interval between starts for fixed delay is approximately:

```text
Task duration + delay
```

---

# 21. `ScheduledFuture`

Scheduling methods return a `ScheduledFuture`.

```java
ScheduledFuture<?> future =
        executor.scheduleAtFixedRate(...);
```

It can be used to cancel the scheduled task.

```java
future.cancel(false);
```

### `cancel(false)`

Attempts to cancel without interrupting a currently running task.

### `cancel(true)`

Attempts to interrupt the running task.

---

# 22. Scheduled Executor Example

```java
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorExample {

    public static void main(String[] args)
            throws InterruptedException {

        // Create a scheduled thread pool with 2 worker threads.
        ScheduledThreadPoolExecutor executor =
                new ScheduledThreadPoolExecutor(2);

        // Execute once after 3 seconds.
        executor.schedule(
                () -> System.out.println("One-time task"),
                3,
                TimeUnit.SECONDS
        );

        // Execute repeatedly with an initial delay of 1 second
        // and a fixed period of 5 seconds.
        ScheduledFuture<?> periodicTask =
                executor.scheduleAtFixedRate(
                        () -> System.out.println("Periodic task"),
                        1,
                        5,
                        TimeUnit.SECONDS
                );

        // Allow the periodic task to run for a while.
        Thread.sleep(15000);

        // Cancel the periodic task.
        periodicTask.cancel(false);

        // Stop accepting new scheduled tasks.
        executor.shutdown();

        // Wait for executor termination.
        executor.awaitTermination(
                5,
                TimeUnit.SECONDS
        );
    }
}
```

---

# 23. Common ScheduledThreadPoolExecutor Use Cases

### `schedule()`

Use for:

```text
Run once after a delay
```

Examples:

- Delayed notification
- Timeout action
- Retry after a delay

### `scheduleAtFixedRate()`

Use for:

```text
Periodic execution at a target rate
```

Examples:

- Metrics collection
- Periodic health checks
- Polling

### `scheduleWithFixedDelay()`

Use when:

```text
The next run should wait a fixed amount of time
after the previous run completes.
```

Examples:

- Polling where each operation may take variable time
- Periodic cleanup where you want a gap between executions

---

# 24. ScheduledThreadPoolExecutor vs Timer

| Timer | ScheduledThreadPoolExecutor |
|---|---|
| Uses one timer thread | Supports multiple worker threads |
| Less flexible | More flexible |
| Timer task failure can affect timer execution | Better executor framework integration |
| Older API | Preferred modern approach |

### Interview answer

> `ScheduledThreadPoolExecutor` is generally preferred over `Timer` because it supports multiple worker threads and integrates with the Executor framework.

---

# 25. Important Interview Questions

### Q1. Does `shutdown()` wait for tasks to finish?

> No. `shutdown()` initiates graceful shutdown but returns without waiting for termination.

### Q2. How do you wait for tasks after shutdown?

> Use `awaitTermination()`.

### Q3. Does `awaitTermination()` shut down the executor?

> No. It only waits for termination. Normally call `shutdown()` first.

### Q4. What happens when you submit a task after `shutdown()`?

> `RejectedExecutionException` is thrown.

### Q5. Does `shutdownNow()` guarantee that running tasks stop?

> No. It attempts to interrupt them. The task must respond to interruption.

### Q6. What does `awaitTermination()` return?

> `true` if the executor terminated within the timeout; `false` if the timeout elapsed before termination.

### Q7. What does `schedule()` do?

> Executes a task once after a specified delay.

### Q8. `scheduleAtFixedRate()` vs `scheduleWithFixedDelay()`?

> Fixed rate targets a regular execution rate; fixed delay waits for a fixed duration after the previous execution completes.

### Q9. Can periodic tasks overlap?

> A periodic task scheduled by `ScheduledExecutorService` does not overlap with itself; the next execution waits if the previous execution has not completed. Different tasks can execute concurrently when enough worker threads are available.

### Q10. What is `ScheduledFuture`?

> It represents the scheduled task and can be used to inspect or cancel it.

---

# 26. One-Minute Revision

```text
shutdown()
→ Stop accepting new tasks.
→ Existing tasks can finish.
→ Does not wait.

awaitTermination()
→ Wait for executor termination.
→ Does not initiate shutdown.

shutdownNow()
→ Stop accepting tasks.
→ Interrupt running tasks on a best-effort basis.
→ Returns tasks that never started.

submit() after shutdown()
→ RejectedExecutionException.

isShutdown()
→ Shutdown initiated?

isTerminated()
→ Completely terminated?

ScheduledThreadPoolExecutor
→ Execute tasks after delay or periodically.

schedule()
→ Once after delay.

scheduleAtFixedRate()
→ Periodic execution based on a target rate.

scheduleWithFixedDelay()
→ Wait fixed delay after previous execution finishes.

ScheduledFuture
→ Represents scheduled task; can cancel it.
```

---

# 27. Interview Flow to Remember

```text
                    ExecutorService
                          │
              ┌───────────┴───────────┐
              ↓                       ↓
          shutdown()              shutdownNow()
              │                       │
              ↓                       ↓
         SHUTDOWN                    STOP
              │                       │
              └──────────┬────────────┘
                         ↓
                    TERMINATED

awaitTermination()
→ waits for the above termination
→ does NOT initiate shutdown
```

```text
ScheduledThreadPoolExecutor
          │
          ├── schedule()
          │      → once after delay
          │
          ├── scheduleAtFixedRate()
          │      → periodic target rate
          │
          └── scheduleWithFixedDelay()
                 → delay after completion
```
