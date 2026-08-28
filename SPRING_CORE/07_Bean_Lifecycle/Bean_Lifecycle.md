# Spring Bean Lifecycle

Spring controls a Bean's lifecycle from creation to destruction.

## 1. High-level lifecycle

```text
Bean Definition
      |
      v
Instantiation
      |
      v
Dependency Injection
      |
      v
Initialization callbacks
      |
      v
@PostConstruct
      |
      v
Bean is ready
      |
      v
Application shutdown
      |
      v
@PreDestroy
      |
      v
Bean destroyed
```

The exact internal lifecycle contains additional extension points, but this model is enough for most 2–3 YOE interviews.

---

# 2. `@PostConstruct`

Used for initialization after dependencies have been injected.

```java
@Component
public class DatabaseService {

    @PostConstruct
    public void init() {
        System.out.println("DatabaseService initialized");
    }
}
```

At this point, injected dependencies are available.

---

# 3. `@PreDestroy`

Used for cleanup before a managed Bean is destroyed.

```java
@Component
public class ConnectionManager {

    @PreDestroy
    public void cleanup() {
        System.out.println("Closing resources");
    }
}
```

---

# 4. Full example

```java
@Component
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
        System.out.println("Constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("Initialization complete");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Cleanup");
    }
}
```

Conceptual order:

```text
1. Constructor
2. Dependency injection
3. Initialization callbacks
4. @PostConstruct
5. Bean available
6. @PreDestroy during shutdown
```

---

# 5. Other lifecycle mechanisms

Spring also provides interfaces/callbacks such as:

```java
InitializingBean
DisposableBean
```

Example:

```java
@Component
public class MyService implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println("Initialized");
    }
}
```

For normal application code, annotations such as `@PostConstruct` and `@PreDestroy` are usually clearer.

---

# 6. Important scope detail

For singleton Beans, Spring normally manages destruction callbacks.

For prototype Beans, Spring creates and initializes them, but it does not generally manage their complete destruction lifecycle after handing them to the caller.

---

## Interview questions

- Explain Spring Bean lifecycle.
- When is `@PostConstruct` called?
- When is `@PreDestroy` called?
- Constructor vs `@PostConstruct`?
- Does Spring destroy prototype Beans automatically?
