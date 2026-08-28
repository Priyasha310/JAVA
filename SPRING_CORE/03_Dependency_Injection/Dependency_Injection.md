# Dependency Injection (DI)

## 1. What is a dependency?

If one class needs another class to perform its job, the second class is a dependency.

```java
class UserService {

    private UserRepository repository;
}
```

Here:

```text
UserService --> UserRepository
```

`UserRepository` is a dependency of `UserService`.

---

## 2. What is Dependency Injection?

Dependency Injection means that a class receives its dependencies from an external source instead of creating them itself.

```java
class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

The dependency is injected through the constructor.

---

# 3. Types of Dependency Injection

Spring supports three common forms:

1. Constructor Injection ⭐⭐⭐⭐⭐
2. Setter Injection ⭐⭐⭐
3. Field Injection ⭐⭐

---

## 4. Constructor Injection — Recommended

```java
class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Advantages:

- Dependencies are explicit
- Supports `final` fields
- Object can be immutable
- Easy unit testing
- Prevents partially initialized objects

Test:

```java
UserRepository repository = new FakeUserRepository();

UserService service = new UserService(repository);
```

No Spring is required to unit-test the class.

---

## 5. Setter Injection

```java
class UserService {

    private UserRepository repository;

    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
```

Useful when the dependency is optional or can be changed after construction.

---

## 6. Field Injection

With Spring:

```java
@Service
class UserService {

    @Autowired
    private UserRepository repository;
}
```

This works, but constructor injection is generally preferred because dependencies are hidden and harder to supply in plain unit tests.

---

# 7. Spring DI example

```java
public interface NotificationService {
    void send(String message);
}
```

```java
@Component
public class EmailNotificationService
        implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}
```

```java
@Service
public class OrderService {

    private final NotificationService notificationService;

    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void placeOrder() {
        System.out.println("Order placed");
        notificationService.send("Order confirmed");
    }
}
```

Spring creates both Beans and injects the notification service into `OrderService`.

---

## 8. Multiple implementations

Suppose:

```java
@Component
class EmailNotificationService implements NotificationService {}

@Component
class SmsNotificationService implements NotificationService {}
```

Now Spring sees two candidates.

This can cause an ambiguity.

Use `@Primary`:

```java
@Primary
@Component
class EmailNotificationService implements NotificationService {}
```

Or use `@Qualifier`:

```java
public OrderService(
        @Qualifier("smsNotificationService")
        NotificationService notificationService) {
    this.notificationService = notificationService;
}
```

---

## 9. Interview questions

- What is DI?
- Constructor vs setter injection?
- Why is constructor injection preferred?
- Why is field injection discouraged?
- What happens when multiple Beans match a dependency?
- `@Primary` vs `@Qualifier`?
