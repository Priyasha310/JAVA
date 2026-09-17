# Spring Beans

## 1. What is a Spring Bean?

A **Spring Bean** is an object that is:

> Created, configured, and managed by the Spring IoC container.

Example:

```java
@Component
public class UserService {
}
```

Spring detects the class through component scanning and creates/manages its object as a Bean.

### Important

Not every Java object is a Spring Bean.

```java
User user = new User();
```

This is an ordinary Java object.

```java
@Component
class UserService {}
```

The instance created and managed by Spring is a **Spring Bean**.

---

## 2. Creating Beans

There are several ways to define Spring Beans.

| Annotation | Layer / Purpose |
|---|---|
| `@Component` | Generic Spring-managed component |
| `@Service` | Business/service layer |
| `@Repository` | Persistence/data-access layer |
| `@Controller` | MVC web/controller layer |
| `@RestController` | REST API controller |
| `@Bean` | Explicit Bean configuration |

### `@Component`

```java
@Component
public class EmailService {
}
```

### `@Service`

```java
@Service
public class UserService {
}
```

### `@Repository`

```java
@Repository
public class UserRepository {
}
```

### `@Controller`

```java
@Controller
public class UserController {
}
```

These are **stereotype annotations**.

Conceptually:

```text
@Component
   |
   +-- @Service
   +-- @Repository
   +-- @Controller
```

`@RestController` is a specialized controller annotation used for REST APIs.

---

## 3. `@Bean`

`@Bean` is used to explicitly register an object as a Spring Bean.

It is normally declared inside a `@Configuration` class.

```java
@Configuration
public class AppConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}
```

This is especially useful when:

- Explicit configuration is required
- Object creation needs customization
- The class comes from a third-party library
- You cannot add `@Component` to the class

Example:

```java
@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper();
}
```

---

## 4. `@Component` vs `@Bean`

### `@Component`

Placed on the **class**:

```java
@Component
class EmailService {
}
```

Spring discovers it through component scanning.

### `@Bean`

Placed on a **method**:

```java
@Configuration
class AppConfig {

    @Bean
    EmailService emailService() {
        return new EmailService();
    }
}
```

Spring registers the returned object as a Bean.

| `@Component` | `@Bean` |
|---|---|
| Applied to class | Applied to method |
| Automatic discovery | Explicit registration |
| Uses component scanning | Uses configuration method |
| Good for classes you control | Useful for third-party/custom objects |

**Easy rule:**

> `@Component` → Spring discovers the class.  
> `@Bean` → You explicitly tell Spring how to create the object.

---

## 5. XML Bean Configuration

Spring can also define Beans using XML configuration.

```xml
<bean id="laptop"
      class="com.example.Laptop">
</bean>
```

The `id` identifies the Bean and `class` specifies the class Spring should instantiate.

Dependency example:

```xml
<bean id="developer"
      class="com.example.Developer">

    <property name="computer"
              ref="laptop"/>

</bean>
```

```text
developer
    |
    | depends on
    ↓
  laptop
```

Spring creates the objects and injects the dependency.

XML is mainly useful for:

- Understanding legacy Spring applications
- Understanding traditional Spring configuration
- Interview questions around XML configuration

Modern Spring Boot applications generally prefer Java/annotation-based configuration.

---

## 6. Bean Naming

By default, a component such as:

```java
@Component
class UserService {
}
```

normally gets the Bean name:

```text
userService
```

You can provide a custom name:

```java
@Component("customerService")
class UserService {
}
```

Bean name:

```text
customerService
```

---

## 7. Multiple Beans of the Same Type

Suppose:

```java
@Component
class EmailService implements NotificationService {}

@Component
class SmsService implements NotificationService {}
```

Then:

```java
NotificationService service;
```

is ambiguous because Spring finds two Beans of the same type.

### `@Primary`

Mark one Bean as the preferred/default Bean:

```java
@Component
@Primary
class EmailService implements NotificationService {}
```

### `@Qualifier`

Explicitly select a Bean:

```java
@Autowired
@Qualifier("smsService")
private NotificationService service;
```

### `@Primary` vs `@Qualifier`

| `@Primary` | `@Qualifier` |
|---|---|
| Defines preferred/default Bean | Explicitly selects a Bean |
| Useful when one implementation should normally be used | Useful when different injection points need different implementations |

---

## 8. Dependency Injection / Wiring

Suppose:

```java
class Developer {

    private final Computer computer;

    Developer(Computer computer) {
        this.computer = computer;
    }
}
```

`Developer` depends on `Computer`.

Spring can create and connect the objects:

```text
Spring Container
      |
      +-- Computer Bean
      |
      +-- Developer Bean
              |
              +-- Computer injected
```

This process is called **Dependency Injection (DI)**.

### Constructor Injection

```java
@Service
class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

Constructor injection is the commonly preferred approach because the dependency is explicit and can be `final`.

---

## 9. Autowiring

Spring can automatically resolve and inject dependencies.

Modern Spring commonly uses:

```java
@Autowired
```

along with:

```java
@Primary
@Qualifier
```

Older XML configuration can use:

```xml
autowire="byName"
```

or:

```xml
autowire="byType"
```

---

## 10. Bean Lifecycle

A simplified Bean lifecycle is:

```text
Instantiation
      ↓
Dependency Injection
      ↓
Initialization
      ↓
Bean Ready
      ↓
Bean Used
      ↓
Destruction
```

Spring manages this lifecycle as part of its Bean management.

---

## 11. Interview Questions

### Basic

- What is a Spring Bean?
- How do you create a Spring Bean?
- What is `@Component`?
- What is `@Service`?
- What is `@Repository`?
- What is `@Controller`?
- What is `@RestController`?
- What is `@Bean`?

### Configuration

- `@Component` vs `@Bean`?
- What is component scanning?
- Can Spring Beans be configured using XML?
- When would you use `@Bean`?

### Dependency Injection

- What is Dependency Injection?
- What is autowiring?
- What is constructor injection?
- What happens when multiple Beans have the same type?
- How does `@Primary` work?
- How does `@Qualifier` work?
- `@Primary` vs `@Qualifier`?

### Bean Lifecycle

- What is the Spring Bean lifecycle?
- What happens when a Bean is created?
- What happens when the Spring container shuts down?
