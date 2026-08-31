# IoC — Inversion of Control

## 1. What is IoC?

IoC stands for **Inversion of Control**.

It means that control over object creation and dependency management is transferred from your application code to a framework/container.

### Without IoC

```java
class UserService {

    private UserRepository repository;

    public UserService() {
        this.repository = new UserRepository();
    }
}
```

`UserService` decides:

- which implementation to create
- when to create it
- how to create it

This creates tight coupling.

### With IoC

```java
class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Now something outside `UserService` provides the dependency.

Spring can perform this responsibility.

---

## 2. Traditional control vs IoC

```text
Traditional Java

Application
    |
    +--> new UserRepository()
    |
    +--> new UserService(repository)


Spring

Application
    |
    v
Spring Container
    |
    +--> creates UserRepository
    |
    +--> creates UserService
    |
    +--> injects UserRepository
```

---

## 3. IoC is a principle

IoC is a **design principle**, not an annotation.

Spring implements IoC primarily using **Dependency Injection**.

```text
IoC
 |
 +-- Dependency Injection
       |
       +-- Constructor Injection
       +-- Setter Injection
       +-- Field Injection
```
# IoC vs DI vs DIP

## 1. IoC — Inversion of Control

**Type:** Principle / Idea

> Control of object creation and dependency management is transferred from application code to an external container/framework.

Spring implements IoC mainly through Dependency Injection.

---

## 2. DI — Dependency Injection

**Type:** Technique / Approach

> Dependencies are supplied to a class from outside instead of the class creating them itself.

```java
class OrderService {

    private final NotificationService notificationService;

    OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

**DI is a common technique used to achieve IoC.**

---

## 3. DIP — Dependency Inversion Principle

**Type:** SOLID Design Principle

> High-level modules should depend on abstractions, not concrete implementations.

```java
class OrderService {

    private final NotificationService notificationService;
}
```

`OrderService` depends on the `NotificationService` abstraction rather than `EmailService`.

**DI can help implement DIP.**

---

## Relationship

```text
IoC
│
├── Principle / Idea
│
└── DI
    └── Technique used to achieve IoC


DIP
└── SOLID Principle
    └── Depend on abstractions
        └── DI can help implement DIP
```

## Quick Comparison

| Concept | Type | Key Idea |
|---|---|---|
| **IoC** | Principle | Control is transferred outside |
| **DI** | Technique | Dependency is supplied from outside |
| **DIP** | SOLID Principle | Depend on abstractions |

### Interview Line

> **IoC is the principle, DI is a technique to achieve IoC, and DIP is a SOLID principle that promotes dependency on abstractions.**

---

## 4. Real-world example

Imagine a payment service.

Bad design:

```java
class PaymentService {

    private final StripePaymentGateway gateway =
            new StripePaymentGateway();
}
```

The service is tied to Stripe.

Better:

```java
interface PaymentGateway {
    void pay(double amount);
}

class PaymentService {

    private final PaymentGateway gateway;

    public PaymentService(PaymentGateway gateway) {
        this.gateway = gateway;
    }
}
```

Now the service does not care whether the implementation is Stripe, Razorpay, a mock, etc.

---

## 5. Interview answer

> IoC is a principle in which the control of object creation and dependency management is moved from application code to a container or framework. Spring implements IoC mainly through Dependency Injection.

---

## 6. Common interview trap

### Is IoC the same as DI?

No.

**IoC** is the broader principle.

**DI** is one of the main techniques used to implement IoC.

---

## 7. Questions to prepare

- What is IoC?
- Why is IoC useful?
- How does Spring implement IoC?
- IoC vs DI?
- Give a real-world example of IoC.
