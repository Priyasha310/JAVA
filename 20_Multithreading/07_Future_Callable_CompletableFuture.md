# Java `Future`, `Callable` and `CompletableFuture` --- Interview Notes

> Interview-focused notes for Java 8+ / Core Java.\
> Focus on understanding **what problem each interface solves**, not
> memorizing implementation details.

------------------------------------------------------------------------

# 1. Quick Overview

Java provides different ways to execute tasks asynchronously:

``` text
Runnable
   ↓
Task with NO return value

Callable<T>
   ↓
Task WITH return value + checked exception

Future<T>
   ↓
Represents the result of an asynchronous task

CompletableFuture<T>
   ↓
Future + chaining + combining + exception handling
```

### Simple memory trick

``` text
Runnable  → Execute
Callable  → Execute + Return
Future    → Get/Control the result later
CompletableFuture → Build async pipelines
```

------------------------------------------------------------------------

# 2. Runnable vs Callable

## Runnable

`Runnable` represents a task that does **not return a result**.

``` java
Runnable task = () -> {
    System.out.println("Task is running");
};
```

Method:

``` java
void run()
```

It cannot declare a checked exception.

------------------------------------------------------------------------

## Callable

`Callable<T>` represents a task that:

-   Returns a result
-   Can throw a checked exception

``` java
Callable<Integer> task = () -> {
    return 100;
};
```

Method:

``` java
T call() throws Exception
```

------------------------------------------------------------------------

# 3. Runnable vs Callable --- Interview Table

  Feature                   Runnable                   Callable
  ------------------------- -------------------------- -----------------------
  Method                    `run()`                    `call()`
  Return value              No                         Yes
  Return type               `void`                     Generic `T`
  Checked exception         Cannot declare             Can declare
  Usually submitted using   `execute()` / `submit()`   `submit()`
  Result obtained through   No result                  `Future<T>`
  Use when                  Only execute a task        Need result from task

### Interview Answer

> **Runnable is used when a task does not need to return a result, while
> Callable is used when the task needs to return a result or throw a
> checked exception.**

------------------------------------------------------------------------

# 4. Callable --- 3 Important Use Cases

## Use Case 1: Return a Result

Use `Callable` when a background task needs to calculate something and
return it.

``` java
ExecutorService executor = Executors.newFixedThreadPool(2);

Callable<Integer> task = () -> {
    return 10 + 20;
};

Future<Integer> future = executor.submit(task);

// get() waits until the Callable finishes
Integer result = future.get();

System.out.println(result); // 30

executor.shutdown();
```

------------------------------------------------------------------------

## Use Case 2: Execute a Task That Can Throw a Checked Exception

`Callable.call()` can throw checked exceptions.

``` java
Callable<String> task = () -> {
    // Example: operation that may throw a checked exception
    Thread.sleep(1000);

    return "Task completed";
};

Future<String> future = executor.submit(task);

try {
    System.out.println(future.get());
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}
```

### Interview Point

`Runnable.run()` cannot declare a checked exception:

``` java
void run()
```

But `Callable.call()` can:

``` java
T call() throws Exception
```

------------------------------------------------------------------------

## Use Case 3: Execute Multiple Tasks and Collect Results

`Callable` is useful when multiple independent tasks return values.

``` java
ExecutorService executor = Executors.newFixedThreadPool(3);

List<Callable<Integer>> tasks = List.of(
    () -> 10,
    () -> 20,
    () -> 30
);

// Executes all tasks and returns their Future objects
List<Future<Integer>> futures = executor.invokeAll(tasks);

for (Future<Integer> future : futures) {
    System.out.println(future.get());
}

executor.shutdown();
```

Output:

``` text
10
20
30
```

### Interview Point

`invokeAll()` is useful when you want to submit multiple `Callable`
tasks and wait for all of them to finish.

------------------------------------------------------------------------

# 5. What is Future?

`Future<T>` represents the **result of an asynchronous computation**.

When we submit a task:

``` java
Future<Integer> future = executor.submit(callableTask);
```

the task can execute in another thread while the current thread
continues.

Later:

``` java
Integer result = future.get();
```

we can retrieve the result.

------------------------------------------------------------------------

# 6. Future --- Common Methods

  Method                 Functionality
  ---------------------- -------------------------------------------------------
  `get()`                Waits until the task completes and returns the result
  `get(timeout, unit)`   Waits only for the specified time
  `isDone()`             Checks whether the task has completed
  `isCancelled()`        Checks whether the task was cancelled
  `cancel(boolean)`      Attempts to cancel the task

### Memory Trick

``` text
get()             → Get result / wait
get(timeout)      → Wait for limited time
isDone()          → Completed?
isCancelled()     → Cancelled?
cancel()          → Cancel it
```

------------------------------------------------------------------------

# 7. Future `get()`

``` java
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(3000);
    return 100;
});

Integer result = future.get();
```

`get()` is **blocking**.

If the task takes 3 seconds, the calling thread can wait for up to 3
seconds.

------------------------------------------------------------------------

# 8. Future `get(timeout, unit)`

``` java
Integer result =
    future.get(2, TimeUnit.SECONDS);
```

The calling thread waits for at most 2 seconds.

If the task is still running:

``` text
TimeoutException
```

is thrown.

### Difference

``` text
get()
→ Wait indefinitely until completion

get(2, SECONDS)
→ Wait maximum 2 seconds
```

------------------------------------------------------------------------

# 9. Future `isDone()`

``` java
if (future.isDone()) {
    System.out.println("Task completed");
}
```

Returns:

``` text
true  → Task completed, failed, or was cancelled
false → Task is still running
```

### Important

`isDone()` does **not** mean the task completed successfully.

It only means the computation is no longer running.

------------------------------------------------------------------------

# 10. Future `isCancelled()`

``` java
if (future.isCancelled()) {
    System.out.println("Task was cancelled");
}
```

Returns `true` if the task was cancelled before completion.

------------------------------------------------------------------------

# 11. Future `cancel()`

``` java
boolean cancelled = future.cancel(true);
```

Parameter:

``` text
true
→ attempt to interrupt the running task

false
→ do not interrupt if already running
```

### Important

Cancellation is an **attempt** to cancel the task. It does not guarantee
that arbitrary code will stop immediately.

------------------------------------------------------------------------

# 12. Future Exceptions

`future.get()` can throw:

``` text
InterruptedException
ExecutionException
TimeoutException
```

Example:

``` java
try {
    Integer result = future.get();
} catch (InterruptedException e) {
    // Current thread was interrupted while waiting
    Thread.currentThread().interrupt();
} catch (ExecutionException e) {
    // Task itself failed
    e.printStackTrace();
}
```

For timed `get()`:

``` java
try {
    future.get(2, TimeUnit.SECONDS);
} catch (TimeoutException e) {
    System.out.println("Task did not finish within 2 seconds");
}
```

------------------------------------------------------------------------

# 13. `Future<?>` vs `Future<T>`

If the task does not return a meaningful result:

``` java
Future<?> future = executor.submit(() -> {
    System.out.println("Task");
});
```

If the task returns a value:

``` java
Future<Integer> future = executor.submit(() -> {
    return 100;
});
```

### Memory Trick

``` text
Runnable → Future<?>
Callable<Integer> → Future<Integer>
```

------------------------------------------------------------------------

# 14. `submit(Runnable)` vs `submit(Callable)`

ExecutorService provides overloaded `submit()` methods.

### Runnable

``` java
Future<?> future = executor.submit(() -> {
    System.out.println("Hello");
});
```

No useful return value.

### Callable

``` java
Future<Integer> future = executor.submit(() -> {
    return 100;
});
```

Returns a value through `Future<Integer>`.

------------------------------------------------------------------------

# 15. What is CompletableFuture?

`CompletableFuture<T>` is an advanced implementation of `Future`.

It provides:

-   Asynchronous execution
-   Result chaining
-   Combining multiple tasks
-   Exception handling
-   Non-blocking-style pipelines

Package:

``` java
java.util.concurrent
```

### Main advantage over Future

With `Future`:

``` java
Future<Integer> future = executor.submit(task);

Integer result = future.get(); // Blocking
```

With `CompletableFuture`:

``` java
CompletableFuture
    .supplyAsync(() -> 100)
    .thenApply(x -> x * 2)
    .thenAccept(System.out::println);
```

We can create an async pipeline instead of repeatedly blocking on
`get()`.

------------------------------------------------------------------------

# 16. Five Core CompletableFuture Methods

For interview preparation, understand these five first:

1.  `runAsync()`
2.  `supplyAsync()`
3.  `thenApply()`
4.  `thenAccept()`
5.  `thenRun()`

------------------------------------------------------------------------

# 17. `runAsync()`

Used when an asynchronous task **does not return a result**.

``` java
CompletableFuture<Void> future =
    CompletableFuture.runAsync(() -> {

        // Runs asynchronously
        System.out.println("Task is running");

    });

// Wait only if the caller actually needs to wait.
// join() returns when the async task finishes.
future.join();
```

### Remember

``` text
runAsync()
→ Async task
→ No return value
→ CompletableFuture<Void>
```

------------------------------------------------------------------------

# 18. `supplyAsync()`

Used when an asynchronous task **returns a result**.

``` java
CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> {

        // Calculate result asynchronously
        return 10 + 20;

    });

System.out.println(future.join()); // 30
```

### Remember

``` text
supplyAsync()
→ Async task
→ Returns a value
→ CompletableFuture<T>
```

### `runAsync()` vs `supplyAsync()`

  Method            Return
  ----------------- ---------------------------
  `runAsync()`      `CompletableFuture<Void>`
  `supplyAsync()`   `CompletableFuture<T>`

------------------------------------------------------------------------

# 19. `thenApply()`

Used to **transform the result** of a previous stage.

Think:

``` text
Input → Transformation → New Result
```

Example:

``` java
CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> {

        // First async operation
        return 10;

    }).thenApply(number -> {

        // Transform 10 into 20
        return number * 2;

    });

System.out.println(future.join()); // 20
```

### Interview Memory

``` text
thenApply()
→ Takes result
→ Transforms result
→ Returns CompletableFuture<R>
```

Example:

``` text
10 → multiply by 2 → 20
```

------------------------------------------------------------------------

# 20. `thenAccept()`

Used when you want to **consume the result** but don't need to return
another result.

``` java
CompletableFuture.supplyAsync(() -> {

    // Produce a value
    return 100;

}).thenAccept(result -> {

    // Consume the value
    System.out.println("Result = " + result);

});
```

Return type:

``` java
CompletableFuture<Void>
```

### Memory Trick

``` text
thenApply  → transform
thenAccept → consume
```

------------------------------------------------------------------------

# 21. `thenRun()`

Used when the next step **does not need the previous result**.

``` java
CompletableFuture.supplyAsync(() -> {

    // First task
    return 100;

}).thenRun(() -> {

    // We don't need the previous result here.
    System.out.println("Previous task completed");

});
```

Return type:

``` java
CompletableFuture<Void>
```

### Important Difference

``` text
thenApply()
→ Needs previous result
→ Produces new result

thenAccept()
→ Needs previous result
→ Does not produce result

thenRun()
→ Does not need previous result
→ Does not produce result
```

------------------------------------------------------------------------

# 22. The Most Important CompletableFuture Comparison

  Method           Uses previous result?   Returns a new result?
  ---------------- ----------------------- -----------------------
  `thenApply()`    Yes                     Yes
  `thenAccept()`   Yes                     No
  `thenRun()`      No                      No

### Easy Memory Trick

``` text
Apply  → Apply a transformation
Accept → Accept/consume result
Run    → Just run something
```

------------------------------------------------------------------------

# 23. CompletableFuture Complete Example

``` java
CompletableFuture
    .supplyAsync(() -> {

        // Step 1: Get user ID asynchronously
        return 10;

    })
    .thenApply(userId -> {

        // Step 2: Transform the user ID
        return "User-" + userId;

    })
    .thenAccept(userName -> {

        // Step 3: Consume the final result
        System.out.println(userName);

    })
    .join();
```

Flow:

``` text
supplyAsync()
     ↓
10
     ↓
thenApply()
     ↓
"User-10"
     ↓
thenAccept()
     ↓
Print result
```

------------------------------------------------------------------------

# 24. CompletableFuture with a Custom Executor

By default, async methods such as `supplyAsync()` use the common
`ForkJoinPool` when no executor is supplied.

You can provide your own executor:

``` java
ExecutorService executor =
        Executors.newFixedThreadPool(2);

CompletableFuture<Integer> future =
    CompletableFuture.supplyAsync(() -> {

        System.out.println(
            "Running on: " +
            Thread.currentThread().getName()
        );

        return 100;

    }, executor);

System.out.println(future.join());

executor.shutdown();
```

### Interview Point

``` text
supplyAsync(task)
→ Uses default async executor

supplyAsync(task, executor)
→ Uses the supplied executor
```

------------------------------------------------------------------------

# 25. `thenApply()` vs `thenApplyAsync()`

### `thenApply()`

``` java
future.thenApply(value -> value * 2);
```

Continuation may execute in the thread that completes the previous
stage.

### `thenApplyAsync()`

``` java
future.thenApplyAsync(value -> value * 2);
```

Continuation is scheduled asynchronously.

You can also provide an executor:

``` java
future.thenApplyAsync(
    value -> value * 2,
    executor
);
```

### Interview Answer

> `thenApply()` is for synchronous-style continuation, while
> `thenApplyAsync()` schedules the continuation asynchronously.

------------------------------------------------------------------------

# 26. CompletableFuture Exception Handling

Although the five core methods above are the first ones to learn,
exception handling is also very important in interviews.

## `exceptionally()`

Provides a fallback when the previous stage fails.

``` java
CompletableFuture<Integer> future =
    CompletableFuture
        .supplyAsync(() -> {
            throw new RuntimeException("Something went wrong");
        })
        .exceptionally(ex -> {

            // Return fallback value
            System.out.println(ex.getMessage());
            return 0;
        });

System.out.println(future.join()); // 0
```

------------------------------------------------------------------------

# 27. `handle()`

Handles both success and failure.

``` java
CompletableFuture<Integer> future =
    CompletableFuture
        .supplyAsync(() -> 100)
        .handle((result, exception) -> {

            if (exception != null) {
                // Handle failure
                return 0;
            }

            // Handle successful result
            return result * 2;
        });

System.out.println(future.join()); // 200
```

### Difference

``` text
exceptionally()
→ Mainly handles failure

handle()
→ Handles both success and failure
```

------------------------------------------------------------------------

# 28. `whenComplete()`

Used to perform an action after completion, without normally
transforming the result.

``` java
CompletableFuture<Integer> future =
    CompletableFuture
        .supplyAsync(() -> 100)
        .whenComplete((result, exception) -> {

            if (exception != null) {
                System.out.println("Failed");
            } else {
                System.out.println("Completed: " + result);
            }
        });
```

------------------------------------------------------------------------

# 29. `thenCompose()` --- Important Bonus

Used when one asynchronous operation depends on the result of another
asynchronous operation.

Without `thenCompose()`:

``` text
CompletableFuture<CompletableFuture<String>>
```

With `thenCompose()`:

``` text
CompletableFuture<String>
```

Example:

``` java
CompletableFuture<String> result =
    CompletableFuture
        .supplyAsync(() -> 101)
        .thenCompose(userId ->

            // Second async operation depends on userId
            CompletableFuture.supplyAsync(
                () -> "User-" + userId
            )
        );

System.out.println(result.join());
```

### Memory Trick

``` text
thenApply()
→ value → value

thenCompose()
→ future → future
→ flattens nested CompletableFuture
```

------------------------------------------------------------------------

# 30. `thenCombine()` --- Important Bonus

Used to combine results from **two independent CompletableFutures**.

``` java
CompletableFuture<Integer> price =
    CompletableFuture.supplyAsync(() -> 100);

CompletableFuture<Integer> tax =
    CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> total =
    price.thenCombine(
        tax,
        (p, t) -> p + t
    );

System.out.println(total.join()); // 120
```

Flow:

``` text
price ──┐
        ├── thenCombine() → total
tax ────┘
```

------------------------------------------------------------------------

# 31. Future vs CompletableFuture

  Future                                   CompletableFuture
  ---------------------------------------- ----------------------------------
  Java 5                                   Java 8
  Mainly used to retrieve/control result   Supports async pipelines
  `get()` commonly blocks                  Supports chaining
  Limited composition                      Easy composition
  Limited exception handling               Rich exception handling
  Cannot easily chain operations           `thenApply`, `thenCompose`, etc.
  Manual coordination is common            Supports combining async tasks

### Interview Answer

> `Future` represents the result of an asynchronous computation, but its
> API is mainly based around waiting for that result.
> `CompletableFuture` extends this idea by providing chaining,
> composition, combination, and exception-handling operations.

------------------------------------------------------------------------

# 32. `get()` vs `join()` in CompletableFuture

Both wait for completion.

### `get()`

``` java
future.get();
```

Throws checked exceptions such as:

``` text
InterruptedException
ExecutionException
```

### `join()`

``` java
future.join();
```

Throws unchecked:

``` text
CompletionException
```

### Interview Shortcut

``` text
get()
→ Checked exception

join()
→ Unchecked CompletionException
```

------------------------------------------------------------------------

# 33. Complete Interview Example

``` java
ExecutorService executor =
        Executors.newFixedThreadPool(3);

CompletableFuture<Integer> price =
    CompletableFuture.supplyAsync(() -> {

        // Simulate fetching price
        return 100;

    }, executor);

CompletableFuture<Integer> tax =
    CompletableFuture.supplyAsync(() -> {

        // Simulate fetching tax
        return 20;

    }, executor);

CompletableFuture<Integer> total =
    price.thenCombine(
        tax,
        (p, t) -> {

            // Combine results from two independent tasks
            return p + t;
        }
    );

total.thenAccept(result -> {

    // Consume final result
    System.out.println("Total = " + result);

}).join();

executor.shutdown();
```

Output:

``` text
Total = 120
```

------------------------------------------------------------------------

# 34. Common Interview Questions

### 1. Runnable vs Callable?

``` text
Runnable  → no result
Callable  → result + checked exception
```

### 2. What is Future?

> Represents the result of an asynchronous computation.

### 3. Is `Future.get()` blocking?

> Yes. `get()` waits until the computation completes.

### 4. Difference between `get()` and `get(timeout)`?

> `get()` can wait indefinitely; timed `get()` waits only for the
> specified duration.

### 5. What does `isDone()` mean?

> The computation has completed, failed, or been cancelled.

### 6. What does `cancel(true)` do?

> Attempts to cancel the task and may interrupt the running thread.

### 7. Why CompletableFuture?

> To create asynchronous pipelines, combine tasks, and handle exceptions
> without manually blocking after every operation.

### 8. `runAsync()` vs `supplyAsync()`?

``` text
runAsync()    → no result
supplyAsync() → returns result
```

### 9. `thenApply()` vs `thenAccept()`?

``` text
thenApply()  → transform result
thenAccept() → consume result
```

### 10. `thenAccept()` vs `thenRun()`?

``` text
thenAccept() → needs previous result
thenRun()    → does not need previous result
```

### 11. `thenApply()` vs `thenCompose()`?

``` text
thenApply()  → transforms a value
thenCompose() → chains another async operation
```

### 12. `thenCombine()`?

> Combines results from two independent CompletableFutures.

------------------------------------------------------------------------

# 35. Final Revision Sheet

``` text
Runnable
→ Task
→ No return value

Callable<T>
→ Task
→ Returns T
→ Can throw checked exception

Future<T>
→ Represents async result
→ get()
→ get(timeout)
→ isDone()
→ isCancelled()
→ cancel()

CompletableFuture<T>
→ Async pipeline
→ Chaining
→ Combining
→ Exception handling
```

## CompletableFuture Core 5

``` text
runAsync()
→ Run async task, no result

supplyAsync()
→ Run async task, return result

thenApply()
→ Transform result

thenAccept()
→ Consume result

thenRun()
→ Run next action without needing result
```

## Important Bonus Methods

``` text
thenCompose()
→ Chain dependent async operations

thenCombine()
→ Combine two independent async results

exceptionally()
→ Handle failure with fallback

handle()
→ Handle success + failure

whenComplete()
→ Perform action after completion
```

## One-Line Interview Summary

> **Runnable executes a task, Callable executes a task and returns a
> result, Future lets us retrieve/control that result, and
> CompletableFuture lets us build and compose asynchronous workflows.**
