# Java Exception Handling — Complete Interview Notes

## 1. What is an Exception?

An **exception** is an event that occurs during program execution and disrupts the normal flow of the program.

When an exception occurs:

1. JVM creates an **exception object**.
2. The object contains information such as:
   - Exception type
   - Error message
   - Stack trace
3. JVM looks for an appropriate **exception handler**.
4. If a matching handler is found, it handles the exception.
5. If no handler is found, the exception propagates up the call stack and may terminate the current thread.

### Example

```java
int a = 10;
int b = 0;

int result = a / b;
```

Result:

```text
ArithmeticException: / by zero
```

---

## 2. Exception Hierarchy

The root of Java's exception hierarchy is `Throwable`.

```text
                    Throwable
                       |
            +----------+----------+
            |                     |
         Error                Exception
            |                     |
     OutOfMemoryError       +-----+----------------+
     StackOverflowError     |                      |
                       RuntimeException       Other Exceptions
                              |
                    +---------+---------+
                    |         |         |
              NullPointer  Arithmetic  ArrayIndexOutOfBounds
              Exception   Exception       Exception
```

### Throwable

`Throwable` is the superclass of errors and exceptions that can be thrown by Java.

It has two major branches:

- `Error`
- `Exception`

---

## 3. Error vs Exception

### Error

`Error` generally represents serious problems from which an application normally should not try to recover.

Examples:

```java
OutOfMemoryError
StackOverflowError
```

Example:

```java
public static void recursiveMethod() {
    recursiveMethod();
}
```

Repeated recursion can eventually cause:

```text
StackOverflowError
```

### Exception

`Exception` represents conditions that an application may be able to handle.

Examples:

```java
IOException
SQLException
NullPointerException
ArithmeticException
```

### Interview Point

Do not treat every `Throwable` as an application exception.

```text
Throwable
├── Error       → generally serious JVM/system problems
└── Exception   → application-level exceptional conditions
```

---

## 4. Checked vs Unchecked Exceptions

```text
Exception
   |
   +-- RuntimeException
   |      └── Unchecked Exceptions
   |
   +-- Other Exceptions
          └── Checked Exceptions
```

### Checked Exceptions

Checked exceptions are exceptions that the **compiler requires you to handle or declare**.

Examples:

```java
IOException
SQLException
ClassNotFoundException
```

Example:

```java
public void readFile() throws IOException {

    FileReader file =
        new FileReader("data.txt");
}
```

You must either:

```text
Handle it → try/catch
```

or:

```text
Declare it → throws
```

### Unchecked Exceptions

Unchecked exceptions are subclasses of `RuntimeException`.

The compiler does **not** force you to handle or declare them.

Examples:

```java
NullPointerException
ArithmeticException
ArrayIndexOutOfBoundsException
IllegalArgumentException
```

Example:

```java
int result = 10 / 0;
```

No `try-catch` is required by the compiler.

### Checked vs Unchecked

| Checked Exception | Unchecked Exception |
|---|---|
| Compiler checks handling | Compiler does not force handling |
| Subclasses of `Exception`, excluding `RuntimeException` | Subclasses of `RuntimeException` |
| Must handle or declare | Handling is optional |
| `IOException` | `NullPointerException` |
| `SQLException` | `ArithmeticException` |

### Important Interview Point

Do **not** say:

> Checked exceptions occur at compile time.

The exception itself occurs at **runtime**.

The compiler checks whether a checked exception is properly **handled or declared**.

---

## 5. Exception Handling Keywords

Java provides five important exception-handling keywords:

```text
try
catch
finally
throw
throws
```

---

## 6. `try`

The `try` block contains code that may throw an exception.

```java
try {

    int result = 10 / 0;

} catch (ArithmeticException e) {

    System.out.println("Cannot divide by zero");
}
```

A `try` block must be followed by:

- `catch`
- `finally`

or both.

### Interview Point

A standalone `try` block is not valid:

```java
try {
    // code
}
```

It must have a corresponding `catch` or `finally`.

---

## 7. `catch`

The `catch` block handles an exception thrown from its associated `try` block.

```java
try {

    int result = 10 / 0;

} catch (ArithmeticException e) {

    System.out.println("Cannot divide by zero");
}
```

### Flow

```text
try
 |
 | exception occurs
 v
catch
 |
 v
handled
```

The exception variable provides access to information about the exception:

```java
catch (ArithmeticException e) {

    System.out.println(e.getMessage());
    e.printStackTrace();
}
```

---

## 8. `finally`

`finally` is used for code that should generally execute after the `try`/`catch` processing.

```java
try {

    int result = 10 / 2;

} catch (ArithmeticException e) {

    System.out.println("Error");

} finally {

    System.out.println("Cleanup");
}
```

Common use:

- Resource cleanup
- Closing resources
- Cleanup operations

### Flow

```text
             try
              |
       +------+------+
       |             |
   Exception      No Exception
       |             |
     catch           |
       |             |
       +------+------+
              |
              v
           finally
```

### Important Interview Point

`finally` normally executes whether an exception occurs or not.

However, do not say it **literally always executes**. Situations such as JVM termination (`System.exit()`) can prevent it from executing.

For resources such as files, streams, and database connections, **try-with-resources** is generally preferred.

---

## 9. `throw`

`throw` is used to **explicitly throw an exception object**.

```java
throw new IllegalArgumentException(
    "Age cannot be negative"
);
```

Example:

```java
public void setAge(int age) {

    if (age < 0) {
        throw new IllegalArgumentException(
            "Age cannot be negative"
        );
    }
}
```

### Key Point

```text
throw → actually throws an exception
```

Example:

```java
throw new Exception();
```

The expression after `throw` must evaluate to a `Throwable` object.

---

## 10. `throws`

`throws` is used in a method declaration to **declare that a method may throw one or more exceptions**.

```java
public void readFile()
        throws IOException {

    // file operation
}
```

The caller must handle or further declare a checked exception.

```java
try {

    readFile();

} catch (IOException e) {

    e.printStackTrace();
}
```

### Key Point

`throws` does not itself throw the exception. It declares the possibility of an exception.

---

## 11. `throw` vs `throws`

| `throw` | `throws` |
|---|---|
| Explicitly throws an exception | Declares possible exceptions |
| Used inside method body | Used in method signature |
| Works with an exception object | Works with exception types |
| `throw new IOException()` | `throws IOException` |

### Easy Way to Remember

```text
throw
   ↓
Do it / actually throw

throws
   ↓
Declare it
```

---

## 12. Exception Propagation

If an exception is not handled in the current method, it propagates to the caller.

```java
void method3() {

    int x = 10 / 0;
}

void method2() {

    method3();
}

void method1() {

    method2();
}
```

Flow:

```text
method1()
   |
   v
method2()
   |
   v
method3()
   |
   v
Exception
   |
   ^  method3 doesn't handle
   |
method2()
   |
   ^  method2 doesn't handle
   |
method1()
   |
   v
JVM / uncaught exception handler
```

If no handler is found, the exception becomes uncaught for that thread and the JVM's uncaught-exception handling terminates that thread and prints the exception information/stack trace.

### Interview Point

Exception propagation is one reason `throws` is useful: a method can pass responsibility for handling a checked exception to its caller.

---

## 13. Multiple Catch Blocks

A `try` block can have multiple `catch` blocks.

```java
try {

    // risky code

} catch (ArithmeticException e) {

    System.out.println("Arithmetic error");

} catch (NullPointerException e) {

    System.out.println("Null value");

} catch (Exception e) {

    System.out.println("Other exception");
}
```

### Important Rule

The more specific exception should come **before** the more general exception.

Correct:

```java
catch (ArithmeticException e) {

} catch (Exception e) {

}
```

Incorrect:

```java
catch (Exception e) {

} catch (ArithmeticException e) {

}
```

The incorrect version causes a **compile-time error** because `ArithmeticException` is already covered by `Exception`.

### Interview Point

Catch ordering is based on the exception hierarchy.

```text
Exception
   |
   +-- RuntimeException
          |
          +-- ArithmeticException
```

Therefore, the child exception must be caught before its parent.

---

## 14. Multi-Catch

Java allows multiple exception types to be handled in a single `catch` block.

```java
try {

    // code

} catch (IOException | SQLException e) {

    System.out.println("Operation failed");
}
```

Useful when the handling logic is the same.

### Important Points

- Introduced in **Java 7**.
- The exception types must not have a parent-child relationship.

For example, this is invalid:

```java
catch (IOException | Exception e) {
}
```

because `Exception` already covers `IOException`.

---

## 15. User-Defined Exception

We can create our own exception by extending `Exception` or `RuntimeException`.

### Checked Custom Exception

```java
class InvalidAgeException extends Exception {

    public InvalidAgeException(String message) {
        super(message);
    }
}
```

Usage:

```java
public void validateAge(int age)
        throws InvalidAgeException {

    if (age < 18) {

        throw new InvalidAgeException(
            "Age must be at least 18"
        );
    }
}
```

### Unchecked Custom Exception

```java
class InvalidAgeException
        extends RuntimeException {

    public InvalidAgeException(String message) {
        super(message);
    }
}
```

No `throws` declaration is required because it is unchecked.

---

## 16. Why Create Custom Exceptions?

Custom exceptions make application-specific failures easier to understand.

Examples:

```text
InvalidAgeException
InsufficientBalanceException
InvalidOrderException
UserNotFoundException
```

Instead of:

```text
RuntimeException
```

we can communicate the exact business problem.

### Interview Point

Custom exceptions are especially useful for **business/domain validation**.

Example:

```java
if (balance < amount) {
    throw new InsufficientBalanceException(
        "Insufficient account balance"
    );
}
```

---

## 17. Exception Chaining

Exception chaining means preserving the **original exception as the cause** of another exception.

```java
try {

    // database operation

} catch (SQLException e) {

    throw new RuntimeException(
        "Unable to process order",
        e
    );
}
```

Conceptually:

```text
RuntimeException
      |
      +-- cause → SQLException
```

This preserves the original root cause for debugging.

### Why Use It?

A low-level exception may not be meaningful to the application layer.

Example:

```text
SQLException
     ↓
OrderProcessingException
```

The higher-level exception communicates application context while retaining the original cause.

---

## 18. Try-with-Resources

Try-with-resources is used to automatically close resources that implement `AutoCloseable`.

```java
try (FileReader reader =
         new FileReader("data.txt")) {

    // use reader

} catch (IOException e) {

    e.printStackTrace();
}
```

The resource is automatically closed after the `try` block.

Common resources:

- Files
- Streams
- Database resources
- Other `AutoCloseable` resources

### Multiple Resources

```java
try (
    FileReader reader = new FileReader("data.txt");
    BufferedReader br = new BufferedReader(reader)
) {

    System.out.println(br.readLine());

} catch (IOException e) {

    e.printStackTrace();
}
```

### Interview Point

Try-with-resources is preferred over manually closing resources in `finally`.

---

## 19. Exception Handling Best Practices

### 1. Catch Specific Exceptions

Prefer:

```java
catch (IOException e) {

}
```

instead of:

```java
catch (Exception e) {

}
```

---

### 2. Don't Swallow Exceptions

Avoid:

```java
catch (Exception e) {

}
```

The exception disappears without any action or useful logging.

---

### 3. Don't Use Exceptions for Normal Control Flow

Use normal conditional logic for expected conditions instead of using exceptions as a replacement for `if/else`.

---

### 4. Provide Meaningful Messages

```java
throw new InvalidAgeException(
    "Age must be greater than or equal to 18"
);
```

---

### 5. Preserve the Original Cause

When wrapping an exception:

```java
throw new RuntimeException(
    "Unable to process order",
    e
);
```

---

### 6. Prefer Try-with-Resources for Resources

Instead of manually closing resources:

```java
finally {
    reader.close();
}
```

prefer:

```java
try (FileReader reader = ...) {
    // use resource
}
```

---

## 20. Advantages of Exception Handling

### Reliability

Allows the application to handle recoverable failures instead of failing unexpectedly.

### Separation of Error Handling

Error-handling logic can be separated from normal business logic.

### Exception Propagation

An exception can be passed to a method higher in the call stack that is capable of handling it.

### Debugging

Exception objects provide:

- Exception type
- Message
- Stack trace
- Cause

### Maintainability

Proper exception handling can make applications easier to maintain.

---

## 21. Disadvantages of Exception Handling

### Performance Overhead

Throwing and handling exceptions has runtime overhead.

### Complexity

Excessive exception handling can make code harder to understand.

### Improper Handling

Broad exception handling can hide actual problems.

```java
catch (Exception e) {
}
```

### Misuse

Using exceptions for normal program flow makes the code harder to understand and maintain.

### Poor Exception Design

Creating too many unnecessary custom exceptions can make the codebase harder to maintain.

---

## 22. Exception vs Error

| Exception | Error |
|---|---|
| Usually application-level exceptional condition | Usually serious JVM/system-level problem |
| Can often be handled | Generally not meant to be handled |
| Example: `IOException` | Example: `OutOfMemoryError` |
| Example: `SQLException` | Example: `StackOverflowError` |
| Application may recover | Recovery is generally difficult/unreliable |

### Interview Answer

> Exceptions generally represent conditions that an application can potentially handle, whereas Errors usually represent serious JVM or system-level problems that applications generally should not attempt to recover from.

---

## 23. `final` vs `finally` vs `finalize()`

### `final`

`final` is a Java keyword used with variables, methods, and classes.

```java
final int MAX = 100;
```

Examples:

```java
final int x = 10;       // value cannot be reassigned

final class A { }       // cannot be extended

final void display() { } // cannot be overridden
```

---

### `finally`

`finally` is a block used with exception handling.

```java
try {

    // risky code

} finally {

    // cleanup
}
```

---

### `finalize()`

`finalize()` was an old mechanism associated with garbage collection.

It has been **deprecated for removal** and should not be used for resource cleanup.

Prefer:

- Try-with-resources
- `AutoCloseable`
- Explicit resource management

### Interview One-Liner

```text
final    → keyword
finally  → exception-handling block
finalize → obsolete/deprecated cleanup mechanism
```

---

## 24. Important Interview Differences

### Checked vs Unchecked

```text
Checked
   ↓
Compiler forces handling or declaration

Unchecked
   ↓
Compiler does not force handling or declaration
```

---

### `throw` vs `throws`

```text
throw
   ↓
Actually throws an exception

throws
   ↓
Declares possible exceptions
```

---

### Exception vs Error

```text
Exception
   ↓
Usually recoverable/application-level condition

Error
   ↓
Serious JVM/system-level problem
```

---

### `try` vs `finally`

```text
try
   ↓
Contains risky code

finally
   ↓
Contains cleanup/finalization logic that normally executes
```

---

## 25. Exception Flow

```text
                Exception occurs
                       |
                       v
                Exception Object
                       |
                       v
             Current method checks
               for exception handler
                       |
             +---------+---------+
             |                   |
             v                   v
        Handler found       Handler not found
             |                   |
             v                   v
          catch              Propagation
                                 |
                                 v
                          Caller method
                                 |
                                 v
                         Handler found?
                           /         \
                         Yes          No
                          |            |
                          v            v
                       catch      Continue propagation
                                       |
                                       v
                              Uncaught exception
                                       |
                                       v
                         JVM uncaught-exception
                               handling
                                       |
                                       v
                           Current thread ends
```

---

## 26. Most Important Interview Questions

### Basic

1. What is an exception in Java?
2. Why do we need exception handling?
3. Explain the Java exception hierarchy.
4. What is `Throwable`?
5. What is the difference between `Error` and `Exception`?

### Checked / Unchecked

6. What is a checked exception?
7. What is an unchecked exception?
8. What is the difference between checked and unchecked exceptions?
9. Is `RuntimeException` checked or unchecked?
10. Does a checked exception happen at compile time?

### Exception Handling

11. Explain `try`, `catch`, and `finally`.
12. Can we have a `try` without a `catch`?
13. Can we have multiple `catch` blocks?
14. Why should specific exceptions be caught before generic exceptions?
15. What is multi-catch?
16. What happens if an exception is not handled?
17. What is exception propagation?
18. Does `finally` always execute?

### `throw` / `throws`

19. What is the difference between `throw` and `throws`?
20. Can `throw` be used with checked exceptions?
21. Can a method declare multiple exceptions using `throws`?

Example:

```java
void process()
        throws IOException, SQLException {
}
```

### Custom Exceptions

22. How do you create a custom exception?
23. Difference between extending `Exception` and `RuntimeException`.
24. When would you create a custom exception?
25. What is exception chaining?

### Resources

26. What is try-with-resources?
27. Why is try-with-resources preferred for resource management?
28. What is `AutoCloseable`?

### Interview Traps

29. Difference between `final`, `finally`, and `finalize()`.
30. Is `finally` guaranteed to execute in every possible situation?
31. Can we catch `Exception` before `ArithmeticException`?
32. Can we use multiple exception types in one catch?
33. Can a checked exception be converted/wrapped into an unchecked exception?
34. What is the difference between rethrowing and wrapping an exception?

---

## 27. Quick Revision

### Core Concepts

- **Exception** → event that disrupts normal program flow.
- **Throwable** → root class for errors and exceptions.
- **Error** → serious JVM/system-level problem.
- **Exception** → exceptional condition that an application may handle.
- **Checked Exception** → compiler requires handling or declaration.
- **Unchecked Exception** → `RuntimeException` and its subclasses.

### Keywords

- **try** → contains risky code.
- **catch** → handles an exception.
- **finally** → cleanup/finalization block that normally executes.
- **throw** → explicitly throws an exception object.
- **throws** → declares possible exceptions in a method signature.

### Important Concepts

- **Exception propagation** → exception moves up the call stack until handled.
- **Custom Exception** → application-specific exception.
- **Exception chaining** → preserves the original cause.
- **Try-with-resources** → automatically closes `AutoCloseable` resources.
- **Multi-catch** → handles multiple exception types in one `catch`.
- Prefer **specific exception handling** over broad `Exception` catches.
- Do not use exceptions for normal control flow.
- Preserve the original cause when wrapping exceptions.
- Use try-with-resources for resource management.

### One-Line Interview Summary

> Java exception handling provides a structured mechanism to detect, propagate, and handle exceptional conditions using `try`, `catch`, `finally`, `throw`, and `throws`, while the exception hierarchy distinguishes recoverable application-level exceptions from serious errors.
