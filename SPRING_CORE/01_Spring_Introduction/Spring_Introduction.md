# Spring Framework — Introduction

## 1. What is Spring?

Spring is an open-source Java framework used to build maintainable, loosely coupled and scalable applications.

Spring provides infrastructure for:

- Dependency Injection (DI)
- Inversion of Control (IoC)
- Bean management
- Web applications
- Data access
- Transactions
- Security
- Testing
- AOP

For Spring Core, the most important ideas are **IoC, DI, Beans and the Spring Container**.

---

## 2. Why was Spring introduced?

Servlet-based applications can become difficult to maintain as they grow.

Traditional Java applications often create their dependencies directly:

```java
class OrderService {

    private OrderRepository repository;

    public OrderService() {
        this.repository = new OrderRepository();
    }
}
```

`OrderService` is tightly coupled to the concrete `OrderRepository`.

A better design is:

```java
class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
}
```

Now the dependency is supplied from outside.

Spring takes this idea further by creating and managing these objects for us.

---

## 3. Main benefits of Spring

### Loose coupling

Classes depend on abstractions rather than creating concrete dependencies themselves.

### Dependency Injection

Spring supplies required dependencies.

### Testability

Dependencies can easily be replaced with mocks/fakes.

### Centralized configuration

Object creation and application configuration can be managed by Spring.

### Lifecycle management

Spring controls creation, initialization and destruction of Beans.

---

## 4. Spring vs Spring Core

**Spring Framework** is the complete ecosystem.

**Spring Core** mainly refers to the fundamental container features:

```text
Spring Core
    |
    +-- IoC Container
    +-- Dependency Injection
    +-- Beans
    +-- ApplicationContext
    +-- Bean scopes
    +-- Bean lifecycle
    +-- Component scanning
    +-- Configuration
```

---

## 6. Interview answer

> Spring is an open-source Java framework that helps build loosely coupled and maintainable applications. Its core feature is IoC, where the Spring container manages object creation and dependencies instead of application code managing them directly.

---

## 7. Important interview questions

1. What is Spring?
2. Why do we use Spring?
3. What problem does Spring solve?
4. What is Spring Core?
5. What is IoC?
6. What is Dependency Injection?
7. What is a Spring Bean?
8. What is the Spring Container?
