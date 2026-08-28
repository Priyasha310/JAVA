# Component Scanning and Configuration

## 1. What is component scanning?

Component scanning allows Spring to find classes annotated with component stereotypes and register them as Beans.

Common annotations:

```java
@Component
@Service
@Repository
@Controller
```

Example:

```java
@Service
public class UserService {
}
```

If the package is included in component scanning, Spring registers `UserService` as a Bean.

---

# 2. `@ComponentScan`

Example:

```java
@Configuration
@ComponentScan("com.example.service")
public class AppConfig {
}
```

Spring scans the specified package and subpackages for components.

---

# 3. Why package structure matters

Example:

```text
com.example
 |
 +-- Application.java
 |
 +-- service
 |     +-- UserService.java
 |
 +-- repository
       +-- UserRepository.java
```

If component scanning starts from `com.example`, the service and repository packages are discovered.

---

# 4. `@Configuration`

Marks a class as a source of Bean definitions.

```java
@Configuration
public class AppConfig {

    @Bean
    public UserService userService() {
        return new UserService();
    }
}
```

---

# 5. `@Bean`

```java
@Configuration
public class AppConfig {

    @Bean
    public EmailClient emailClient() {
        return new EmailClient();
    }
}
```

Spring registers the returned object as a Bean.

---

# 6. `@Component` vs `@Bean`

| `@Component` | `@Bean` |
|---|---|
| Applied to class | Applied to method |
| Discovered by component scanning | Explicitly declared |
| Good for application classes | Good for custom/third-party objects |
| Spring creates the instance | Method creates/returns the instance |

---

# 7. Configuration example

```java
@Configuration
public class AppConfig {

    @Bean
    public PaymentGateway paymentGateway() {
        return new StripePaymentGateway();
    }

    @Bean
    public PaymentService paymentService(
            PaymentGateway paymentGateway) {

        return new PaymentService(paymentGateway);
    }
}
```

Spring resolves the `PaymentGateway` Bean and supplies it to `PaymentService`.

---

# 8. `@Import`

Configuration can be split into multiple classes.

```java
@Configuration
@Import(DatabaseConfig.class)
public class AppConfig {
}
```

This is useful for modular configuration.

---

# 9. Interview questions

- What is component scanning?
- How does Spring discover `@Component`?
- What does `@ComponentScan` do?
- What is `@Configuration`?
- `@Configuration` vs `@Component`?
- `@Component` vs `@Bean`?
- Why are packages important for component scanning?
