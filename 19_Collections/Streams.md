# Java Streams — Interview Notes (2–3 YOE)

## 1. What is a Stream?

A **Stream** in Java is a sequence of elements that supports functional-style operations such as filtering, mapping, sorting, and collecting.

```java
List<Integer> numbers = List.of(10, 15, 20, 25);

numbers.stream()
       .filter(n -> n > 15)
       .forEach(System.out::println);
```

Output:

```text
20
25
```

### Important Interview Point

A Stream is **NOT a data structure** and does **not store data**.

It operates on a data source such as:

- `List`
- `Set`
- Array
- `Map` (through `entrySet()`, `keySet()`, or `values()`)
- `Stream.of(...)`
- Files / I/O sources

Think:

```text
Data Source → Stream → Operations → Result
```

---

# 2. Stream vs Collection

| Collection | Stream |
|---|---|
| Stores data | Does not store data |
| Used to represent data | Used to process data |
| Can be iterated multiple times | Generally cannot be reused after terminal operation |
| Supports eager operations | Intermediate operations are lazy |
| Example: `List` | Example: `list.stream()` |

### Interview Answer

> "A Collection stores data, whereas a Stream provides a pipeline for processing data."

---

# 3. Different Ways to Create a Stream

## 3.1 From Collection

Most common approach.

```java
List<String> names = List.of("A", "B", "C");

Stream<String> stream = names.stream();
```

For a Set:

```java
Set<Integer> numbers = Set.of(1, 2, 3);

numbers.stream();
```

---

## 3.2 From Array

```java
int[] numbers = {1, 2, 3, 4};

IntStream stream = Arrays.stream(numbers);
```

For an object array:

```java
String[] names = {"A", "B", "C"};

Stream<String> stream = Arrays.stream(names);
```

---

## 3.3 Using `Stream.of()`

```java
Stream<String> stream =
        Stream.of("Java", "Python", "Go");
```

---

## 3.4 Using `Stream.empty()`

Creates an empty stream.

```java
Stream<String> stream = Stream.empty();
```

---

## 3.5 Using `Stream.builder()`

```java
Stream<String> stream =
        Stream.<String>builder()
              .add("Java")
              .add("Spring")
              .add("React")
              .build();
```

---

## 3.6 Using `Stream.generate()`

Generates an **infinite stream**.

```java
Stream<Double> stream =
        Stream.generate(Math::random);
```

Usually combined with `limit()`:

```java
Stream.generate(Math::random)
      .limit(5)
      .forEach(System.out::println);
```

---

## 3.7 Using `Stream.iterate()`

Creates values based on a function.

```java
Stream.iterate(1, n -> n + 1)
      .limit(5)
      .forEach(System.out::println);
```

Output:

```text
1
2
3
4
5
```

---

## 3.8 From Map

A Map itself does not directly provide `stream()`.

Use:

```java
Map<Integer, String> map = Map.of(
        1, "A",
        2, "B"
);
```

### Keys

```java
map.keySet().stream();
```

### Values

```java
map.values().stream();
```

### Entries

```java
map.entrySet().stream();
```

---

# 4. Stream Pipeline

A Stream pipeline generally consists of:

```text
Source
  ↓
Intermediate Operations
  ↓
Intermediate Operations
  ↓
Terminal Operation
```

Example:

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

numbers.stream()
       .filter(n -> n % 2 == 0)
       .map(n -> n * 10)
       .forEach(System.out::println);
```

Pipeline:

```text
numbers
   ↓
stream()
   ↓
filter()
   ↓
map()
   ↓
forEach()
```

---

# 5. What Does "Stream Operations Are Lazy" Mean?

**Important correction:** It is the **intermediate operations** that are lazy, not "I/O" operations.

For example:

```java
List<Integer> numbers = List.of(1, 2, 3, 4);

numbers.stream()
       .filter(n -> {
           System.out.println("filter: " + n);
           return n > 2;
       });
```

Nothing is printed.

Why?

Because `filter()` is an **intermediate operation** and intermediate operations are not executed until a terminal operation is called.

Add:

```java
.forEach(System.out::println);
```

Now the pipeline executes.

### Interview Answer

> "Stream intermediate operations are lazy. They are not executed when the pipeline is created; execution starts only when a terminal operation is invoked."

---

# 6. Why Are Streams Lazy?

Lazy evaluation provides several benefits:

### 1. Avoids unnecessary processing

```java
numbers.stream()
       .filter(n -> n > 5)
       .findFirst();
```

The stream can stop as soon as the first matching element is found.

### 2. Enables short-circuiting

Operations such as:

```java
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
limit()
```

can stop processing early.

### 3. Improves performance

The Stream API can process elements through the pipeline without creating unnecessary intermediate collections.

---

# 7. Intermediate Operations

Intermediate operations return another `Stream`.

Example:

```java
stream.filter(...);
```

The result is still a Stream.

## Main Types

### 7.1 `filter()`

Filters elements based on a condition.

```java
numbers.stream()
       .filter(n -> n > 10);
```

Example:

```text
Input:  5, 10, 15, 20
Output: 15, 20
```

---

### 7.2 `map()`

Transforms each element.

```java
numbers.stream()
       .map(n -> n * 2);
```

```text
Input:  1, 2, 3
Output: 2, 4, 6
```

---

### 7.3 `flatMap()`

Used when each element produces multiple elements and you want to flatten them into one stream.

```java
List<List<Integer>> numbers = List.of(
        List.of(1, 2),
        List.of(3, 4)
);

numbers.stream()
       .flatMap(List::stream)
       .forEach(System.out::println);
```

Output:

```text
1
2
3
4
```

### Interview Difference

```text
map     → one input → one output
flatMap → one input → multiple outputs, then flatten
```

---

### 7.4 `distinct()`

Removes duplicates.

```java
List.of(1, 2, 2, 3, 3)
    .stream()
    .distinct();
```

Result:

```text
1, 2, 3
```

---

### 7.5 `sorted()`

Natural ordering:

```java
numbers.stream()
       .sorted();
```

Custom ordering:

```java
employees.stream()
         .sorted(Comparator.comparing(Employee::getSalary));
```

---

### 7.6 `limit()`

Limits the number of elements.

```java
numbers.stream()
       .limit(3);
```

---

### 7.7 `skip()`

Skips the first N elements.

```java
numbers.stream()
       .skip(2);
```

---

### 7.8 `peek()`

Mainly useful for debugging/observing elements flowing through a pipeline.

```java
numbers.stream()
       .peek(n -> System.out.println("Before: " + n))
       .filter(n -> n > 2)
       .forEach(System.out::println);
```

Do not use `peek()` for essential business logic.

---

### 7.9 `takeWhile()` — Java 9+

Takes elements while the condition remains true.

```java
Stream.of(1, 2, 3, 6, 4)
      .takeWhile(n -> n < 5)
      .forEach(System.out::println);
```

Output:

```text
1
2
3
```

---

### 7.10 `dropWhile()` — Java 9+

Drops elements while the condition remains true.

```java
Stream.of(1, 2, 3, 6, 4)
      .dropWhile(n -> n < 5)
      .forEach(System.out::println);
```

Output:

```text
6
4
```

---

# 8. Important: Intermediate Operations Are Lazy

Consider:

```java
List<Integer> numbers = List.of(1, 2, 3);

numbers.stream()
       .filter(n -> {
           System.out.println("filter " + n);
           return n > 1;
       })
       .map(n -> {
           System.out.println("map " + n);
           return n * 10;
       });
```

Expected output:

```text
No output
```

Because there is no terminal operation.

Now add:

```java
.forEach(System.out::println);
```

The pipeline executes.

---

# 9. Sequence of Stream Operations — Expected vs Actual Execution

This is a **very common interview question**.

Consider:

```java
List<Integer> numbers = List.of(1, 2, 3);

numbers.stream()
       .filter(n -> {
           System.out.println("filter " + n);
           return n > 1;
       })
       .map(n -> {
           System.out.println("map " + n);
           return n * 10;
       })
       .forEach(n ->
           System.out.println("output " + n)
       );
```

### Many beginners expect:

```text
filter 1
filter 2
filter 3
map 2
map 3
output 20
output 30
```

### Actual output:

```text
filter 1
filter 2
map 2
output 20
filter 3
map 3
output 30
```

### Why?

Streams generally process elements through the entire pipeline **element by element**, rather than completing one intermediate operation for the entire collection first.

Think:

```text
Element 1
   ↓
filter
   ↓
map
   ↓
output

Element 2
   ↓
filter
   ↓
map
   ↓
output

Element 3
   ↓
filter
   ↓
map
   ↓
output
```

This is called **vertical execution** of the pipeline.

---

# 10. Another Important Example

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

numbers.stream()
       .filter(n -> {
           System.out.println("filter: " + n);
           return n % 2 == 0;
       })
       .map(n -> {
           System.out.println("map: " + n);
           return n * 10;
       })
       .findFirst();
```

Execution can stop after:

```text
filter: 1
filter: 2
map: 2
```

because `findFirst()` is a **short-circuiting terminal operation**.

---

# 11. Terminal Operations

A terminal operation produces a final result or side effect and **consumes the stream**.

After a terminal operation, the stream cannot be reused.

## Main Terminal Operations

### `forEach()`

```java
numbers.stream()
       .forEach(System.out::println);
```

Returns:

```text
void
```

---

### `collect()`

Very commonly used.

```java
List<Integer> result =
        numbers.stream()
               .filter(n -> n > 2)
               .collect(Collectors.toList());
```

---

### `toList()` — Java 16+

```java
List<Integer> result =
        numbers.stream()
               .filter(n -> n > 2)
               .toList();
```

---

### `count()`

```java
long count = numbers.stream().count();
```

---

### `min()`

```java
Optional<Integer> min =
        numbers.stream().min(Integer::compareTo);
```

---

### `max()`

```java
Optional<Integer> max =
        numbers.stream().max(Integer::compareTo);
```

---

### `findFirst()`

```java
Optional<Integer> result =
        numbers.stream().findFirst();
```

---

### `findAny()`

```java
Optional<Integer> result =
        numbers.stream().findAny();
```

Especially useful with parallel streams when any matching element is sufficient.

---

### `anyMatch()`

Checks whether **at least one** element matches.

```java
boolean result =
        numbers.stream()
               .anyMatch(n -> n > 10);
```

---

### `allMatch()`

Checks whether **all** elements match.

```java
boolean result =
        numbers.stream()
               .allMatch(n -> n > 0);
```

---

### `noneMatch()`

Checks whether **no** element matches.

```java
boolean result =
        numbers.stream()
               .noneMatch(n -> n < 0);
```

---

### `reduce()`

Combines elements into a single result.

```java
int sum =
        numbers.stream()
               .reduce(0, Integer::sum);
```

Example:

```text
1 + 2 + 3 + 4 = 10
```

---

# 12. Terminal Operations — Quick Table

| Terminal Operation | Purpose | Return |
|---|---|---|
| `forEach()` | Process each element | `void` |
| `collect()` | Collect into a result | Collection/result |
| `toList()` | Create List | `List` |
| `count()` | Count elements | `long` |
| `min()` | Find minimum | `Optional<T>` |
| `max()` | Find maximum | `Optional<T>` |
| `findFirst()` | First element | `Optional<T>` |
| `findAny()` | Any element | `Optional<T>` |
| `anyMatch()` | At least one matches | `boolean` |
| `allMatch()` | All match | `boolean` |
| `noneMatch()` | None match | `boolean` |
| `reduce()` | Combine elements | `T` / `Optional<T>` |

---

# 13. How Many Times Can We Use One Stream?

A Stream can generally be used **only once**.

Example:

```java
Stream<Integer> stream =
        List.of(1, 2, 3).stream();

stream.forEach(System.out::println);

stream.count();  // ❌ Exception
```

You get:

```text
IllegalStateException:
stream has already been operated upon or closed
```

### Why?

A terminal operation **consumes the stream**.

If you need another operation, create another stream:

```java
List<Integer> numbers = List.of(1, 2, 3);

numbers.stream().count();

numbers.stream().forEach(System.out::println);
```

### Interview Answer

> "A Stream is single-use. Once a terminal operation is invoked, the stream is consumed and cannot be reused. We need to create a new stream from the original source."

---

# 14. Intermediate vs Terminal Operations

| Intermediate | Terminal |
|---|---|
| Returns a Stream | Returns a result / performs action |
| Lazy | Triggers execution |
| Can be chained | Ends the pipeline |
| `filter()` | `forEach()` |
| `map()` | `collect()` |
| `sorted()` | `count()` |
| `distinct()` | `reduce()` |
| `limit()` | `findFirst()` |
| `skip()` | `anyMatch()` |
| `flatMap()` | `allMatch()` |

### Easy Rule

```text
Intermediate → Stream
Terminal     → Result
```

---

# 15. Short-Circuiting Operations

A short-circuiting operation can stop processing without consuming all elements.

## Intermediate

```java
limit()
takeWhile()
dropWhile()
```

`takeWhile()` can stop once its condition becomes false.

## Terminal

```java
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
```

Example:

```java
List<Integer> numbers =
        List.of(1, 2, 3, 4, 5);

boolean result =
        numbers.stream()
               .anyMatch(n -> n > 3);
```

Once `4` is found, processing can stop.

---

# 16. Parallel Stream

A **parallel stream** divides stream processing across multiple threads, using the common `ForkJoinPool` by default.

Example:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Or:

```java
numbers.stream()
       .parallel()
       .forEach(System.out::println);
```

---
                    Collection
                       │
                       │ parallelStream()
                       ▼
              ┌─────────────────┐
              │   Stream Data   │
              │  1 2 3 4 5 6 7 8│
              └────────┬────────┘
                       │
                 Split into parts
                       │
          ┌────────────┼────────────┐
          ▼            ▼            ▼
      ┌────────┐   ┌────────┐   ┌────────┐
      │ Part 1 │   │ Part 2 │   │ Part 3 │
      │  1  2  │   │  3  4  │   │  5  6  │
      └───┬────┘   └───┬────┘   └───┬────┘
          │            │            │
          ▼            ▼            ▼
       Thread 1     Thread 2     Thread 3
          │            │            │
          └────────────┼────────────┘
                       ▼
                 Process Results
                       │
                       ▼
              ┌─────────────────┐
              │  Combined Result│
              └─────────────────┘
---

# 17. Sequential vs Parallel Stream

### Sequential

```java
numbers.stream()
       .forEach(System.out::println);
```

Processing is sequential.

Conceptually:

```text
Thread
  ↓
1 → 2 → 3 → 4 → 5
```

### Parallel

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Processing may happen concurrently:

```text
Thread 1 → 1, 2
Thread 2 → 3
Thread 3 → 4, 5
```

The exact distribution depends on the runtime.

---

# 18. Important Parallel Stream Interview Point

Do **NOT** assume that parallel stream output is ordered.

```java
List.of(1, 2, 3, 4, 5)
    .parallelStream()
    .forEach(System.out::println);
```

Possible output:

```text
3
5
1
4
2
```

If encounter order is required:

```java
.parallelStream()
.forEachOrdered(System.out::println);
```

Output:

```text
1
2
3
4
5
```

But ordering can reduce some of the performance benefits of parallel processing.

---

# 19. When Should We Use Parallel Streams?

Parallel streams can help when:

- Dataset is sufficiently large.
- Work per element is CPU-intensive.
- Operations are independent.
- The workload can be effectively split.
- Ordering is not important.

Example:

```java
largeList.parallelStream()
         .map(this::expensiveCalculation)
         .toList();
```

---

# 20. When Should We NOT Use Parallel Streams?

Avoid blindly using parallel streams when:

- Dataset is small.
- Operations are cheap.
- Operations perform blocking I/O.
- Shared mutable state is involved.
- Strict ordering is important.
- The application already has its own thread-pool/concurrency model.

Example of risky code:

```java
List<Integer> result = new ArrayList<>();

numbers.parallelStream()
       .forEach(n -> result.add(n)); // ❌
```

`ArrayList` is not thread-safe, so this introduces a concurrency problem.

Prefer:

```java
List<Integer> result =
        numbers.parallelStream()
               .map(n -> n * 2)
               .toList();
```

---

# 21. Parallel Stream and Thread Safety

This is important for interviews.

Avoid shared mutable state:

```java
int[] sum = {0};

numbers.parallelStream()
       .forEach(n -> sum[0] += n); // ❌
```

Multiple threads may update `sum[0]` concurrently.

Use a reduction instead:

```java
int sum =
        numbers.parallelStream()
               .reduce(0, Integer::sum);
```

---

# 22. `forEach()` vs `forEachOrdered()`

| `forEach()` | `forEachOrdered()` |
|---|---|
| Does not guarantee encounter order in parallel | Preserves encounter order |
| Potentially better parallel performance | Can reduce parallel performance |
| Common default | Use when order matters |

---

# 23. `stream()` vs `parallelStream()`

```java
list.stream()
```

→ Sequential stream.

```java
list.parallelStream()
```

→ Parallel stream.

You can also convert:

```java
list.stream()
    .parallel();
```

and:

```java
list.parallelStream()
    .sequential();
```

---

# 24. Primitive Streams

Java provides specialized streams:

```text
IntStream
LongStream
DoubleStream
```

Example:

```java
IntStream.range(1, 5)
         .forEach(System.out::println);
```

Output:

```text
1
2
3
4
```

For `rangeClosed()`:

```java
IntStream.rangeClosed(1, 5)
         .forEach(System.out::println);
```

Output:

```text
1
2
3
4
5
```

### Why primitive streams?

They avoid unnecessary boxing/unboxing in many numeric operations.

```java
List<Integer> numbers
```

uses `Integer` objects, while:

```java
IntStream
```

works with primitive `int` values.

---

# 25. Common Interview Example

### Question

Find the names of employees whose salary is greater than 50,000.

```java
List<String> names =
        employees.stream()
                 .filter(e -> e.getSalary() > 50000)
                 .map(Employee::getName)
                 .toList();
```

Pipeline:

```text
employees
    ↓
stream()
    ↓
filter()
    ↓
map()
    ↓
toList()
```

---

# 26. Very Important Interview Trap: `map()` vs `filter()`

### `filter()`

Used to **remove/select elements**.

```java
.filter(n -> n > 10)
```

Input:

```text
5, 15, 20
```

Output:

```text
15, 20
```

### `map()`

Used to **transform elements**.

```java
.map(n -> n * 2)
```

Input:

```text
5, 10
```

Output:

```text
10, 20
```

Remember:

```text
filter → select
map    → transform
```

---

# 27. Very Important Interview Trap: `map()` vs `flatMap()`

```text
map:
A → B

flatMap:
A → Stream<B>
then flatten
```

Example:

```java
List<List<String>> names = List.of(
        List.of("A", "B"),
        List.of("C", "D")
);
```

`map()`:

```java
names.stream()
     .map(List::stream);
```

Result conceptually:

```text
Stream<Stream<String>>
```

`flatMap()`:

```java
names.stream()
     .flatMap(List::stream);
```

Result:

```text
Stream<String>
```

---

# 28. Stream vs Parallel Stream — Interview Table

| Feature | Stream | Parallel Stream |
|---|---|---|
| Processing | Sequential | Potentially parallel |
| Threads | Usually calling thread | Multiple threads |
| Ordering | Usually encounter order | Not guaranteed with `forEach()` |
| Overhead | Lower | Higher |
| Small data | Usually better | Often unnecessary |
| CPU-heavy large data | May be slower | Can be faster |
| Thread safety concern | Lower | Higher |
| Default pool | N/A | Common `ForkJoinPool` |

---

# 29. Top Interview Questions

### Q1. What is a Stream?

> A Stream is a sequence of elements supporting functional-style operations to process data from a source. It does not store data.

### Q2. Is Stream a data structure?

> No. A Stream is a processing abstraction over a data source.

### Q3. Are streams lazy?

> Intermediate operations are lazy. They execute only when a terminal operation triggers the pipeline.

### Q4. Why are streams lazy?

> To avoid unnecessary computation and enable optimizations such as short-circuiting.

### Q5. What are intermediate operations?

> Operations that return another Stream, such as `filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, and `skip`.

### Q6. What are terminal operations?

> Operations that consume the stream and produce a result or side effect, such as `collect`, `forEach`, `count`, `reduce`, `findFirst`, and matching operations.

### Q7. Can we reuse a Stream?

> No. A Stream is single-use after a terminal operation. A new Stream must be created from the source.

### Q8. Difference between `stream()` and `parallelStream()`?

> `stream()` creates a sequential stream, while `parallelStream()` enables parallel processing using multiple threads.

### Q9. Is parallel stream always faster?

> No. It has overhead and is beneficial mainly for sufficiently large datasets and suitable CPU-bound, independent operations.

### Q10. Does `forEach()` guarantee order in a parallel stream?

> No. Use `forEachOrdered()` if encounter order must be preserved.

---

# 30. One-Page Revision

```text
JAVA STREAM
│
├── Source
│   ├── Collection.stream()
│   ├── Arrays.stream()
│   ├── Stream.of()
│   ├── Stream.generate()
│   └── Stream.iterate()
│
├── Intermediate Operations
│   ├── filter()
│   ├── map()
│   ├── flatMap()
│   ├── distinct()
│   ├── sorted()
│   ├── limit()
│   ├── skip()
│   ├── peek()
│   ├── takeWhile()
│   └── dropWhile()
│
└── Terminal Operations
    ├── forEach()
    ├── collect()
    ├── toList()
    ├── count()
    ├── min()
    ├── max()
    ├── findFirst()
    ├── findAny()
    ├── anyMatch()
    ├── allMatch()
    ├── noneMatch()
    └── reduce()
```

### Most Important Rules

```text
1. Stream does NOT store data.

2. Intermediate operations are lazy.

3. Terminal operation triggers execution.

4. A Stream is single-use.

5. filter() → select elements.

6. map() → transform elements.

7. flatMap() → flatten nested streams.

8. parallelStream() → potentially parallel processing.

9. parallelStream().forEach() does NOT guarantee order.

10. Don't use parallel streams blindly.
```

---

# ⭐ Interview Cheat Sheet

| Topic | Remember |
|---|---|
| Stream | Processing pipeline, not data structure |
| Lazy | Intermediate operations execute only when terminal operation is invoked |
| Intermediate | Returns Stream |
| Terminal | Produces result / side effect |
| `filter()` | Select |
| `map()` | Transform |
| `flatMap()` | Flatten |
| `sorted()` | Sort |
| `distinct()` | Remove duplicates |
| `limit()` | Take first N |
| `skip()` | Skip first N |
| `collect()` | Gather result |
| `reduce()` | Combine into one result |
| `findFirst()` | First element |
| `findAny()` | Any element |
| `anyMatch()` | At least one |
| `allMatch()` | All |
| `noneMatch()` | None |
| Reuse | ❌ Not after terminal operation |
| `stream()` | Sequential |
| `parallelStream()` | Parallel-capable |
| `forEach()` in parallel | Order not guaranteed |
| `forEachOrdered()` | Preserves encounter order |
