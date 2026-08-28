# Spring Beans

## 1. What is a Spring Bean?

A Spring Bean is an object that is:

> Created, configured and managed by the Spring IoC container.

Example:

```java
@Component
public class UserService {
}
```

Spring detects the class and creates a Bean.

---

## 2. Creating Beans

| Annotation        | Layer / Purpose                  |
| ----------------- | -------------------------------- |
| `@Component`      | Generic Spring-managed component |
| `@Service`        | Business/service layer           |
| `@Repository`     | Persistence/data-access layer    |
| `@Controller`     | MVC web/controller layer         |
| `@RestController` | REST API controller              |

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

These are stereotype annotations.

Conceptually:

```text
@Component
   |
   +-- @Service
   +-- @Repository
   +-- @Controller
```

---

## 3. `@Bean`

You can explicitly define a Bean inside a configuration class.

```java
@Configuration
public class AppConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService();
    }
}
```

This is especially useful when you need to create/configure an object from a third-party library.

---

## 4. `@Component` vs `@Bean`

### `@Component`

Used on the class:

```java
@Component
class EmailService {
}
```

Spring discovers it through component scanning.

### `@Bean`

Used on a method:

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

---

## 5. Bean naming

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

You can provide a name:

```java
@Component("customerService")
class UserService {
}
```

---

## 6. Multiple Beans of the same type

```java
@Component
class EmailService implements NotificationService {}

@Component
class SmsService implements NotificationService {}
```

Injecting only:

```java
NotificationService service
```

is ambiguous.

Use:

```java
@Primary
```

or:

```java
@Qualifier("smsService")
```

---

## 7. Important distinction

Not every Java object is automatically a Spring Bean.

```java
User user = new User();
```

This is an ordinary Java object.

```java
@Component
class UserService {}
```

The instance created and managed by Spring is a Spring Bean.

---

## 8. Interview questions

- What is a Spring Bean?
- How do you create a Bean?
- `@Component` vs `@Bean`?
- `@Component` vs `@Service`?
- How does Spring resolve multiple Beans?
- Can an ordinary Java object be a Spring Bean?
