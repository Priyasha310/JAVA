# Java Control Flow Statements --- Complete Interview Notes

> **Goal:** Understand Java decision-making, loops, and branching
> statements clearly from an interview perspective.
>
> **Interview level:** Suitable for Core Java / 2--3 YOE interviews.

------------------------------------------------------------------------

# 1. What Are Control Flow Statements?

Control flow statements determine the **order in which Java statements
are executed**.

Normally, Java executes statements from **top to bottom**.

Control flow statements allow us to:

-   Make decisions
-   Repeat code
-   Skip an iteration
-   Exit a loop or switch
-   Choose one block from multiple options

## Main Categories

``` text
Control Flow Statements
│
├── 1. Decision-Making Statements
│   ├── if
│   ├── if-else
│   ├── if-else-if
│   ├── nested if
│   └── switch
│
├── 2. Iterative Statements / Loops
│   ├── for
│   ├── while
│   ├── do-while
│   └── enhanced for / for-each
│
└── 3. Branching Statements
    ├── break
    └── continue
```

------------------------------------------------------------------------

# 2. Decision-Making Statements

Decision-making statements execute code based on a condition.

The main statements are:

-   `if`
-   `if-else`
-   `if-else-if`
-   nested `if`
-   `switch`
-   switch expressions

------------------------------------------------------------------------

# 3. `if` Statement

The `if` statement executes a block of code **only when the condition is
true**.

## Syntax

``` java
if (condition) {
    // statements
}
```

The condition must evaluate to a `boolean`.

## Example

``` java
int age = 20;

if (age >= 18) {
    System.out.println("Adult");
}
```

Output:

``` text
Adult
```

If the condition is false, the block is skipped.

``` java
int age = 15;

if (age >= 18) {
    System.out.println("Adult");
}

System.out.println("End");
```

Output:

``` text
End
```

## Important Interview Point

Java does **not** allow integers or other values directly as conditions.

This is valid:

``` java
if (age >= 18) {
}
```

This is invalid:

``` java
if (age) {
}
```

Unlike some languages, Java requires the condition to be `boolean`.

------------------------------------------------------------------------

# 4. `if-else`

`if-else` is used when there are **two possible execution paths**.

## Syntax

``` java
if (condition) {
    // if condition is true
} else {
    // if condition is false
}
```

## Example

``` java
int age = 16;

if (age >= 18) {
    System.out.println("Adult");
} else {
    System.out.println("Minor");
}
```

Output:

``` text
Minor
```

Only one of the two blocks executes.

------------------------------------------------------------------------

# 5. `if-else-if` Ladder

Used when there are **multiple conditions**.

## Syntax

``` java
if (condition1) {
    // block 1
} else if (condition2) {
    // block 2
} else if (condition3) {
    // block 3
} else {
    // default block
}
```

## Example

``` java
int marks = 75;

if (marks >= 90) {
    System.out.println("A");
} else if (marks >= 75) {
    System.out.println("B");
} else if (marks >= 50) {
    System.out.println("C");
} else {
    System.out.println("Fail");
}
```

Output:

``` text
B
```

## Important Rule

Conditions are checked **from top to bottom**.

As soon as Java finds the first `true` condition, that block executes
and the remaining `else-if` conditions are skipped.

### Example

``` java
int marks = 95;

if (marks >= 50) {
    System.out.println("Pass");
} else if (marks >= 90) {
    System.out.println("Excellent");
}
```

Output:

``` text
Pass
```

The second condition is never checked because the first condition is
already true.

### Interview Tip

Put **more specific conditions before more general conditions**.

------------------------------------------------------------------------

# 6. Nested `if`

An `if` statement inside another `if` statement is called a **nested
if**.

## Example

``` java
int age = 25;
boolean hasLicense = true;

if (age >= 18) {

    if (hasLicense) {
        System.out.println("Can drive");
    }
}
```

Here, the second condition is checked only if the first condition is
true.

## When to Use

Nested `if` can be useful when one condition depends on another.

However, excessive nesting can make code difficult to read.

Prefer combining conditions when appropriate:

``` java
if (age >= 18 && hasLicense) {
    System.out.println("Can drive");
}
```

------------------------------------------------------------------------

# 7. `switch` Statement

`switch` is useful when one expression needs to be compared against
multiple possible values.

## Syntax

``` java
switch (expression) {

    case value1:
        // statements
        break;

    case value2:
        // statements
        break;

    default:
        // statements
}
```

## Example

``` java
int day = 2;

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    case 3:
        System.out.println("Wednesday");
        break;

    default:
        System.out.println("Invalid day");
}
```

Output:

``` text
Tuesday
```

------------------------------------------------------------------------

# 8. Components of `switch`

## 8.1 `switch` expression

The value being checked.

``` java
switch (day)
```

## 8.2 `case`

Defines a possible matching value.

``` java
case 1:
```

## 8.3 `break`

Stops execution from continuing into the next case.

``` java
break;
```

## 8.4 `default`

Executes when no case matches.

``` java
default:
```

------------------------------------------------------------------------

# 9. `switch` Fall-Through

One of the most important interview concepts.

If `break` is missing in a traditional switch, execution continues into
the following cases.

Example:

``` java
int number = 1;

switch (number) {

    case 1:
        System.out.println("One");

    case 2:
        System.out.println("Two");

    case 3:
        System.out.println("Three");

    default:
        System.out.println("Other");
}
```

Output:

``` text
One
Two
Three
Other
```

This behavior is called **fall-through**.

## With `break`

``` java
int number = 1;

switch (number) {

    case 1:
        System.out.println("One");
        break;

    case 2:
        System.out.println("Two");
        break;

    default:
        System.out.println("Other");
}
```

Output:

``` text
One
```

------------------------------------------------------------------------

# 10. `default` in Switch

`default` is executed when none of the cases match.

``` java
int day = 10;

switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    default:
        System.out.println("Invalid day");
}
```

Output:

``` text
Invalid day
```

`default` is optional.

------------------------------------------------------------------------

# 11. What Types Can Be Used in `switch`?

Commonly supported types include:

-   `byte`
-   `short`
-   `char`
-   `int`
-   corresponding wrapper types
-   `String`
-   `enum`

Example with `String`:

``` java
String role = "ADMIN";

switch (role) {

    case "ADMIN":
        System.out.println("Admin");
        break;

    case "USER":
        System.out.println("User");
        break;

    default:
        System.out.println("Unknown role");
}
```

## Important

`boolean`, `long`, `float`, and `double` are not valid traditional
switch selector types.

------------------------------------------------------------------------

# 12. `switch` with `enum`

`switch` works very well with enums.

``` java
enum Status {
    PENDING,
    APPROVED,
    REJECTED
}
```

``` java
Status status = Status.APPROVED;

switch (status) {

    case PENDING:
        System.out.println("Pending");
        break;

    case APPROVED:
        System.out.println("Approved");
        break;

    case REJECTED:
        System.out.println("Rejected");
        break;
}
```

------------------------------------------------------------------------

# 13. Switch Expression

Modern Java supports **switch expressions**, which can produce a value.

## Example

``` java
int day = 2;

String result = switch (day) {

    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    default -> "Invalid";
};

System.out.println(result);
```

Output:

``` text
Tuesday
```

This is different from the traditional switch statement because the
switch expression can be assigned to a variable.

------------------------------------------------------------------------

# 14. Arrow Syntax in Switch

Modern switch syntax uses `->`.

``` java
switch (day) {

    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
    default -> System.out.println("Invalid");
}
```

With arrow syntax, there is no traditional fall-through between cases.

------------------------------------------------------------------------

# 15. `yield` in Switch Expressions

When a switch expression needs multiple statements inside a case,
`yield` can return the value.

``` java
int day = 2;

String result = switch (day) {

    case 1 -> "Monday";

    case 2 -> {
        System.out.println("Processing...");
        yield "Tuesday";
    }

    default -> "Invalid";
};
```

`yield` returns a value from a switch expression.

------------------------------------------------------------------------

# 16. `if-else` vs `switch`

  `if-else`                                `switch`
  ---------------------------------------- --------------------------------
  Good for ranges and complex conditions   Good for fixed values
  Supports relational operators            Mainly matches discrete values
  Supports `&&`, `||`, etc.                Case-based matching
  Better for complex conditions            Cleaner for many fixed choices

Example where `if` is better:

``` java
if (marks >= 90) {
    System.out.println("A");
} else if (marks >= 75) {
    System.out.println("B");
}
```

Example where `switch` is cleaner:

``` java
switch (day) {
    case 1 -> System.out.println("Monday");
    case 2 -> System.out.println("Tuesday");
}
```

------------------------------------------------------------------------

# 17. Iterative Statements / Loops

Loops execute a block of code repeatedly.

Java provides:

1.  `for`
2.  `while`
3.  `do-while`
4.  enhanced `for` / `for-each`

------------------------------------------------------------------------

# 18. `for` Loop

Use a `for` loop when you have a counter or know the iteration pattern.

## Syntax

``` java
for (initialization; condition; update) {
    // body
}
```

## Example

``` java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

Output:

``` text
0
1
2
3
4
```

------------------------------------------------------------------------

# 19. Execution Order of `for` Loop

For:

``` java
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
```

Execution order is:

``` text
1. initialization
2. condition
3. body
4. update
5. condition
6. body
7. update
...
```

Visualization:

``` text
initialization
      ↓
  condition
      ↓
    body
      ↓
   update
      ↓
  condition
      ↓
    body
      ↓
   update
      ↓
     ...
```

Initialization happens only **once**.

------------------------------------------------------------------------

# 20. Infinite `for` Loop

All three parts of a `for` loop are optional.

``` java
for (;;) {
    System.out.println("Running");
}
```

This creates an infinite loop.

Equivalent idea:

``` java
while (true) {
    System.out.println("Running");
}
```

An infinite loop should normally have some condition that eventually
exits it.

------------------------------------------------------------------------

# 21. Multiple Variables in `for`

You can initialize and update multiple variables.

``` java
for (int i = 0, j = 10; i < j; i++, j--) {
    System.out.println(i + " " + j);
}
```

------------------------------------------------------------------------

# 22. `while` Loop

A `while` loop executes as long as its condition is true.

## Syntax

``` java
while (condition) {
    // body
}
```

## Example

``` java
int i = 0;

while (i < 5) {
    System.out.println(i);
    i++;
}
```

Output:

``` text
0
1
2
3
4
```

------------------------------------------------------------------------

# 23. Important Property of `while`

The condition is checked **before** the body.

Therefore, a `while` loop may execute **zero times**.

``` java
int i = 10;

while (i < 5) {
    System.out.println(i);
}
```

No output is produced.

------------------------------------------------------------------------

# 24. `do-while` Loop

A `do-while` loop executes the body first and checks the condition
afterward.

## Syntax

``` java
do {
    // body
} while (condition);
```

Notice the semicolon after the condition.

## Example

``` java
int i = 0;

do {
    System.out.println(i);
    i++;
} while (i < 5);
```

Output:

``` text
0
1
2
3
4
```

------------------------------------------------------------------------

# 25. Why `do-while` Executes At Least Once

The body comes before the condition.

``` java
int i = 10;

do {
    System.out.println(i);
} while (i < 5);
```

Output:

``` text
10
```

The condition is false, but the body has already executed once.

------------------------------------------------------------------------

# 26. `for` vs `while` vs `do-while`

  Loop         Condition Check     Minimum Executions Common Use
  ------------ ----------------- -------------------- ----------------------------
  `for`        Before                               0 Counter-controlled loops
  `while`      Before                               0 Condition-controlled loops
  `do-while`   After                                1 Must execute at least once

### Easy Interview Answer

-   Use `for` when iteration is counter-based.
-   Use `while` when execution depends mainly on a condition.
-   Use `do-while` when the body must execute at least once.

------------------------------------------------------------------------

# 27. Enhanced `for` / `for-each`

The enhanced `for` loop is used to iterate through arrays and
collections.

## Syntax

``` java
for (Type variable : collectionOrArray) {
    // body
}
```

## Array Example

``` java
int[] numbers = {10, 20, 30, 40};

for (int number : numbers) {
    System.out.println(number);
}
```

Output:

``` text
10
20
30
40
```

## Collection Example

``` java
List<String> names = List.of("Alice", "Bob", "Charlie");

for (String name : names) {
    System.out.println(name);
}
```

------------------------------------------------------------------------

# 28. `for` vs `for-each`

Use traditional `for` when you need the index.

``` java
for (int i = 0; i < numbers.length; i++) {
    System.out.println(numbers[i]);
}
```

Use `for-each` when you only need each element.

``` java
for (int number : numbers) {
    System.out.println(number);
}
```

## Important Interview Point

`for-each` does not directly provide the index.

------------------------------------------------------------------------

# 29. Can We Modify the Collection During `for-each`?

Be careful when modifying a collection while iterating over it.

Example:

``` java
List<Integer> numbers = new ArrayList<>();
numbers.add(1);
numbers.add(2);
numbers.add(3);

for (Integer number : numbers) {
    if (number == 2) {
        numbers.remove(number);
    }
}
```

This can result in a `ConcurrentModificationException`.

A safer approach for supported operations is to use an `Iterator`:

``` java
Iterator<Integer> iterator = numbers.iterator();

while (iterator.hasNext()) {

    Integer number = iterator.next();

    if (number == 2) {
        iterator.remove();
    }
}
```

This is a useful interview-level collection + loop concept.

------------------------------------------------------------------------

# 30. `break` Statement

`break` immediately terminates the nearest enclosing loop or switch.

## Example

``` java
for (int i = 1; i <= 10; i++) {

    if (i == 5) {
        break;
    }

    System.out.println(i);
}
```

Output:

``` text
1
2
3
4
```

### Remember

``` text
break = EXIT
```

------------------------------------------------------------------------

# 31. `break` in `switch`

`break` is also commonly used in traditional switch statements.

``` java
switch (day) {

    case 1:
        System.out.println("Monday");
        break;

    case 2:
        System.out.println("Tuesday");
        break;

    default:
        System.out.println("Invalid");
}
```

Here `break` exits the switch.

------------------------------------------------------------------------

# 32. `continue` Statement

`continue` skips the current iteration and moves to the next iteration
of the loop.

## Example

``` java
for (int i = 1; i <= 5; i++) {

    if (i == 3) {
        continue;
    }

    System.out.println(i);
}
```

Output:

``` text
1
2
4
5
```

### Remember

``` text
continue = SKIP CURRENT ITERATION
```

------------------------------------------------------------------------

# 33. `break` vs `continue`

  -----------------------------------------------------------------------
  `break`                             `continue`
  ----------------------------------- -----------------------------------
  Exits the loop                      Skips current iteration

  Loop terminates                     Loop continues

  Used when no more iterations are    Used when current iteration should
  needed                              be ignored
  -----------------------------------------------------------------------

Example:

``` java
for (int i = 1; i <= 5; i++) {

    if (i == 3) {
        break;
    }

    System.out.println(i);
}
```

Output:

``` text
1
2
```

With `continue`:

``` java
for (int i = 1; i <= 5; i++) {

    if (i == 3) {
        continue;
    }

    System.out.println(i);
}
```

Output:

``` text
1
2
4
5
```

------------------------------------------------------------------------

# 34. Nested Loops

A loop inside another loop is called a nested loop.

Example:

``` java
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {
        System.out.println(i + " " + j);
    }
}
```

The inner loop completes all its iterations for every iteration of the
outer loop.

------------------------------------------------------------------------

# 35. `break` in Nested Loops

A normal `break` exits only the **nearest enclosing loop**.

``` java
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (j == 2) {
            break;
        }

        System.out.println(i + " " + j);
    }
}
```

The `break` exits the inner loop, not the outer loop.

------------------------------------------------------------------------

# 36. Labeled `break`

Java supports labeled statements.

This can be used to break out of an outer loop.

``` java
outer:
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (i == 2 && j == 2) {
            break outer;
        }

        System.out.println(i + " " + j);
    }
}
```

Here:

``` java
break outer;
```

terminates the outer loop.

### Interview Tip

Labeled breaks are valid Java, but they should be used sparingly because
excessive use can reduce readability.

------------------------------------------------------------------------

# 37. Labeled `continue`

`continue` can also be labeled.

``` java
outer:
for (int i = 1; i <= 3; i++) {

    for (int j = 1; j <= 3; j++) {

        if (j == 2) {
            continue outer;
        }

        System.out.println(i + " " + j);
    }
}
```

This skips the remaining work of the current outer-loop iteration and
starts the next outer iteration.

------------------------------------------------------------------------

# 38. Scope of Variables in Loops

Variables declared inside a loop have scope limited to that block.

``` java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

This is invalid:

``` java
System.out.println(i);
```

because `i` exists only inside the `for` loop.

------------------------------------------------------------------------

# 39. Common Infinite Loop Mistake

Forgetting to update the loop variable can create an infinite loop.

``` java
int i = 0;

while (i < 5) {
    System.out.println(i);
}
```

`i` never changes, so the condition remains true.

Correct:

``` java
int i = 0;

while (i < 5) {
    System.out.println(i);
    i++;
}
```

------------------------------------------------------------------------

# 40. Common Off-by-One Error

Be careful with:

``` java
i < 5
```

versus:

``` java
i <= 5
```

Example:

``` java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
```

Output:

``` text
0
1
2
3
4
```

Total iterations = **5**

Whereas:

``` java
for (int i = 0; i <= 5; i++) {
    System.out.println(i);
}
```

Output:

``` text
0
1
2
3
4
5
```

Total iterations = **6**

------------------------------------------------------------------------

# 41. Empty Loop

A loop can technically have an empty body.

``` java
for (int i = 0; i < 10; i++);
```

The semicolon means the loop body is empty.

Be careful because an accidental semicolon can cause bugs.

Incorrect:

``` java
for (int i = 0; i < 5; i++);
{
    System.out.println(i);
}
```

The block is not the body of the loop.

------------------------------------------------------------------------

# 42. Nested `if` vs `if` with `&&`

Instead of:

``` java
if (age >= 18) {
    if (hasLicense) {
        System.out.println("Can drive");
    }
}
```

we can often write:

``` java
if (age >= 18 && hasLicense) {
    System.out.println("Can drive");
}
```

Use whichever makes the business logic clearer.

------------------------------------------------------------------------

# 43. Short-Circuit Conditions in Control Flow

Logical operators are commonly used in conditions.

## AND --- `&&`

Both conditions must be true.

``` java
if (age >= 18 && hasLicense) {
    System.out.println("Can drive");
}
```

## OR --- `||`

At least one condition must be true.

``` java
if (isAdmin || isManager) {
    System.out.println("Access granted");
}
```

## NOT --- `!`

Reverses a boolean value.

``` java
if (!isLoggedIn) {
    System.out.println("Please login");
}
```

------------------------------------------------------------------------

# 44. Short-Circuit Evaluation

With `&&` and `||`, Java may avoid evaluating the second condition.

Example:

``` java
if (user != null && user.isActive()) {
    // ...
}
```

If `user == null`, Java does not evaluate:

``` java
user.isActive()
```

This prevents a possible `NullPointerException`.

Similarly:

``` java
if (user != null || isGuest) {
}
```

If `user != null` is true, Java does not need to evaluate the right
side.

------------------------------------------------------------------------

# 45. `&&` vs `&`

For boolean expressions:

``` java
&&
```

is the logical AND with short-circuit behavior.

``` java
&
```

can also operate on booleans, but both operands are evaluated.

Example:

``` java
if (user != null && user.isActive()) {
}
```

is generally preferred when the second condition should only be
evaluated if the first is true.

------------------------------------------------------------------------

# 46. `||` vs `|`

Similarly:

``` java
||
```

is logical OR with short-circuit behavior.

``` java
|
```

can also operate on booleans but evaluates both operands.

For normal conditional logic, use `||`.

------------------------------------------------------------------------

# 47. Common Interview Output Questions

## Example 1

``` java
int x = 10;

if (x > 5) {
    System.out.println("A");
} else {
    System.out.println("B");
}
```

Output:

``` text
A
```

------------------------------------------------------------------------

## Example 2

``` java
for (int i = 0; i < 3; i++) {
    System.out.println(i);
}
```

Output:

``` text
0
1
2
```

------------------------------------------------------------------------

## Example 3

``` java
for (int i = 0; i < 5; i++) {

    if (i == 2) {
        continue;
    }

    System.out.println(i);
}
```

Output:

``` text
0
1
3
4
```

------------------------------------------------------------------------

## Example 4

``` java
for (int i = 0; i < 5; i++) {

    if (i == 2) {
        break;
    }

    System.out.println(i);
}
```

Output:

``` text
0
1
```

------------------------------------------------------------------------

## Example 5

``` java
int i = 10;

do {
    System.out.println(i);
} while (i < 5);
```

Output:

``` text
10
```

------------------------------------------------------------------------

# 48. Important Interview Questions

## Q1. What are control flow statements in Java?

Control flow statements control the order in which statements execute.

They are mainly:

-   Decision-making statements
-   Iterative statements
-   Branching statements

------------------------------------------------------------------------

## Q2. What is the difference between `if` and `switch`?

`if` is better for conditions involving ranges, relational operators, or
complex boolean expressions.

`switch` is cleaner when comparing one value against multiple fixed
options.

------------------------------------------------------------------------

## Q3. What is the difference between `while` and `do-while`?

`while` checks the condition before executing the body.

`do-while` executes the body first and checks the condition afterward.

Therefore, `do-while` executes at least once.

------------------------------------------------------------------------

## Q4. What is the difference between `for` and `while`?

Both can perform repeated execution.

A `for` loop is commonly used when there is a counter or known iteration
pattern.

A `while` loop is commonly used when repetition depends primarily on a
condition.

------------------------------------------------------------------------

## Q5. What is the difference between `for` and `for-each`?

Traditional `for` gives direct access to the index.

`for-each` is simpler when we only need each element.

------------------------------------------------------------------------

## Q6. What happens if `break` is not used in a traditional switch?

Execution can fall through into subsequent cases.

------------------------------------------------------------------------

## Q7. Does `default` have to be the last case?

No. In a traditional switch, `default` can technically appear elsewhere.

However, placing it at the end is the normal and readable approach.

------------------------------------------------------------------------

## Q8. Can switch use String?

Yes.

``` java
switch (role) {
    case "ADMIN":
        // ...
        break;
}
```

------------------------------------------------------------------------

## Q9. Can switch use boolean?

No, `boolean` is not a valid traditional switch selector type.

------------------------------------------------------------------------

## Q10. What is the difference between `break` and `continue`?

`break` terminates the loop.

`continue` skips the current iteration and moves to the next iteration.

------------------------------------------------------------------------

## Q11. Can `break` be used outside a loop or switch?

A normal `break` must be inside a loop or switch.

------------------------------------------------------------------------

## Q12. Can `continue` be used outside a loop?

No.

`continue` is used to continue with the next iteration of a loop.

------------------------------------------------------------------------

## Q13. Can we have nested loops?

Yes.

A loop can contain another loop.

------------------------------------------------------------------------

## Q14. What does `break` do in nested loops?

It exits only the nearest enclosing loop unless a labeled break is used.

------------------------------------------------------------------------

## Q15. What is an infinite loop?

A loop whose condition never becomes false.

Example:

``` java
while (true) {
    System.out.println("Running");
}
```

------------------------------------------------------------------------

# 49. Quick Comparison Table

  Statement      Purpose                            Executes At Least Once?
  -------------- ---------------------------------- -------------------------
  `if`           Conditional execution              No
  `if-else`      Choose between two paths           No
  `if-else-if`   Choose among multiple conditions   No
  `switch`       Choose among fixed values          No
  `for`          Repetition                         No
  `while`        Condition-based repetition         No
  `do-while`     Condition-based repetition         **Yes**
  `for-each`     Iterate elements                   No
  `break`        Exit loop/switch                   N/A
  `continue`     Skip current iteration             N/A

------------------------------------------------------------------------

# 50. Interview Cheat Sheet

``` text
if
→ Execute when condition is true.

if-else
→ Choose between two paths.

if-else-if
→ Multiple conditions.
→ First true condition executes.

nested if
→ if inside another if.

switch
→ Multiple fixed-value choices.
→ Traditional switch can fall through without break.

switch expression
→ Switch that can produce a value.
→ Modern arrow syntax avoids fall-through.

for
→ Counter / known iteration pattern.

while
→ Condition checked before body.
→ Can execute zero times.

do-while
→ Condition checked after body.
→ Executes at least once.

for-each
→ Iterate arrays / collections.
→ No direct index.

break
→ EXIT loop/switch.

continue
→ SKIP current iteration.

nested loop
→ Loop inside another loop.

labeled break
→ Exit an outer loop.

labeled continue
→ Continue an outer loop.
```

------------------------------------------------------------------------

# 51. What You Should Know for a Core Java Interview

For interviews, make sure you can confidently explain:

### Must Know

-   `if`
-   `if-else`
-   `if-else-if`
-   nested `if`
-   `switch`
-   switch fall-through
-   `for`
-   `while`
-   `do-while`
-   `for-each`
-   `break`
-   `continue`
-   nested loops

### Should Know

-   Switch expressions
-   `yield`
-   labeled `break`
-   labeled `continue`
-   short-circuit evaluation
-   common infinite-loop mistakes
-   off-by-one errors
-   modifying collections during iteration

### Very Common Interview Comparisons

``` text
if vs switch
for vs while
while vs do-while
for vs for-each
break vs continue
&& vs &
|| vs |
switch statement vs switch expression
```

------------------------------------------------------------------------

# 52. Final Memory Map

``` text
                 CONTROL FLOW
                      │
        ┌─────────────┼─────────────┐
        │             │             │
    DECISION        LOOPS        BRANCHING
        │             │             │
   ┌────┴────┐   ┌────┼────┐    ┌───┴────┐
   │         │   │    │    │    │        │
  if      switch for while do  break  continue
   │                   │      while
   ├── if-else         │
   ├── else-if         └── for-each
   └── nested if
```

## One-Line Revision

> **Decision statements choose what to execute, loops decide how many
> times to execute it, and branching statements change or terminate the
> normal loop flow.**
