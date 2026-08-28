# Spring Core — Interview Questions (2–3 YOE)

## Fundamentals

### 1. What is Spring?

Spring is an open-source Java framework for building loosely coupled and maintainable applications. Its core container manages object creation, dependencies and Bean lifecycle.

### 2. What is IoC?

IoC means control of object creation and dependency management is transferred from application code to the Spring container.

### 3. What is DI?

Dependency Injection is the technique of supplying an object's dependencies from outside instead of creating them inside the class.

### 4. IoC vs DI?

IoC is the broader principle. DI is a common technique used to implement IoC.

---

## Beans

### 5. What is a Spring Bean?

An object created/configured and managed by the Spring IoC container.

### 6. `@Component` vs `@Bean`?

`@Component` is placed on a class and discovered through component scanning.

`@Bean` is placed on a method in configuration and explicitly registers the returned object.

### 7. `@Component` vs `@Service` vs `@Repository`?

All are component stereotypes and can be detected by component scanning. `@Service` communicates service-layer intent, `@Repository` communicates persistence-layer intent, and `@Component` is the generic stereotype.

---

## Dependency Injection

### 8. Which DI type is preferred?

Constructor injection.

```java
@Service
class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

### 9. Why constructor injection?

- Explicit dependencies
- Supports final fields
- Easier testing
- Better immutability
- Prevents partially initialized objects

### 10. What if multiple Beans match?

Use:

```java
@Primary
```

or:

```java
@Qualifier
```

---

## Container

### 11. What is ApplicationContext?

It is a feature-rich Spring container responsible for Bean management and additional framework features such as events, resources and environment support.

### 12. BeanFactory vs ApplicationContext?

BeanFactory provides the basic IoC container functionality. ApplicationContext extends the container capabilities and is the usual choice in modern Spring applications.

---

## Scopes

### 13. What is the default Bean scope?

Singleton.

### 14. Singleton vs prototype?

Singleton generally provides one instance per Spring container. Prototype creates a new instance whenever the container is asked for the Bean.

### 15. What happens if prototype is injected into singleton?

The prototype instance is normally created when the singleton is created. Repeated calls to the singleton do not automatically create a new prototype. Use `ObjectProvider`, scoped proxies or another design when a fresh instance is required.

---

## Lifecycle

### 16. Explain Bean lifecycle.

A simplified lifecycle is:

```text
Instantiation
   ↓
Dependency Injection
   ↓
Initialization callbacks
   ↓
@PostConstruct
   ↓
Bean ready
   ↓
@PreDestroy
```

### 17. `@PostConstruct` vs constructor?

Constructor runs when the object is instantiated. `@PostConstruct` runs after dependency injection and is suitable for initialization that requires injected dependencies.

---

## Component Scanning

### 18. What is component scanning?

Spring scans configured packages to find component classes such as `@Component`, `@Service`, `@Repository` and `@Controller`.

### 19. Why can Spring fail to find a Bean?

Common causes:

- Package is outside the component scan
- Missing stereotype annotation
- Incorrect configuration
- Bean is conditionally disabled
- Application configuration is not loaded

---

# Scenario-based questions

## 20. You have two implementations of an interface. What happens?

Spring cannot choose based only on the interface type and may report an ambiguous dependency.

Use:

```java
@Primary
```

or:

```java
@Qualifier
```

---

## 21. How would you make a class easy to unit test?

Use constructor injection:

```java
class OrderService {

    private final PaymentService paymentService;

    OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

A test can directly provide a fake or mock.

---

## 22. Why shouldn't service classes use `new Repository()`?

It creates tight coupling and makes dependency replacement/testing harder.

Prefer:

```java
class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Spring can then provide the repository.

---

# Quick revision

```text
Spring
  ↓
IoC
  ↓
DI
  ↓
Spring Container
  ↓
ApplicationContext
  ↓
Beans
  ↓
Component Scanning
  ↓
Bean Scopes
  ↓
Bean Lifecycle
  ↓
@Primary / @Qualifier
```

## 2–3 YOE must-know

⭐⭐⭐⭐⭐

- IoC
- DI
- Constructor Injection
- Spring Bean
- ApplicationContext
- Component Scanning
- Singleton vs Prototype
- Bean Lifecycle
- `@Component` / `@Service` / `@Repository`
- `@Bean`
- `@Primary` / `@Qualifier`

⭐⭐⭐

- BeanFactory
- Request/Session scopes
- Lifecycle interfaces
- Circular dependencies
- `ObjectProvider`
