# Autowiring, @Primary and @Qualifier

## 1. What is autowiring?

Autowiring means Spring automatically resolves and injects a dependency into a Bean.

Constructor injection is preferred:

```java
@Service
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

If there is exactly one matching `PaymentService` Bean, Spring can inject it automatically.

---

# 2. `@Autowired`

You may see:

```java
@Autowired
public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
}
```

With modern Spring, if a class has a single constructor, explicit `@Autowired` is generally unnecessary.

---

# 3. Multiple implementations

```java
public interface PaymentService {
    void pay();
}
```

```java
@Component
public class CardPaymentService implements PaymentService {
    public void pay() {
        System.out.println("Card payment");
    }
}
```

```java
@Component
public class UpiPaymentService implements PaymentService {
    public void pay() {
        System.out.println("UPI payment");
    }
}
```

Now:

```java
public OrderService(PaymentService paymentService) {
}
```

is ambiguous because two Beans match.

---

# 4. `@Primary`

```java
@Primary
@Component
public class UpiPaymentService implements PaymentService {
}
```

Now UPI becomes the default candidate when Spring needs a `PaymentService`.

---

# 5. `@Qualifier`

Choose a specific Bean:

```java
public OrderService(
        @Qualifier("cardPaymentService")
        PaymentService paymentService) {

    this.paymentService = paymentService;
}
```

---

# 6. `@Primary` vs `@Qualifier`

```text
@Primary
    |
    +-- default choice


@Qualifier
    |
    +-- explicit choice
```

If you need one particular implementation at a particular injection point, `@Qualifier` is clearer.

---

# 7. Common interview scenario

### Question

You have three implementations of an interface. How will Spring know which one to inject?

### Answer

Use `@Primary` to designate a default candidate or `@Qualifier` to explicitly select the required Bean.

---

# 8. Circular dependency

Example:

```text
A --> B
B --> A
```

Constructor injection can expose such design problems early.

Rather than trying to work around the problem blindly, first look for a better design that removes the circular dependency.

---

## Interview questions

- What is autowiring?
- Is `@Autowired` mandatory for a single constructor?
- What happens when multiple Beans match?
- `@Primary` vs `@Qualifier`?
- What is circular dependency?
- Why is constructor injection preferred?
