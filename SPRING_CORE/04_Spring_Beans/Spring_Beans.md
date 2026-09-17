# Spring Beans

## 0. Spring Framework → IoC Container

The **IoC (Inversion of Control) Container** is responsible for:

1. **Create Objects** — Instantiate beans/objects
2. **Manage Objects** — Manage their lifecycle
3. **Connect Objects** — Inject dependencies and wire objects together

```text
                Spring IoC Container
                        |
          +-------------+-------------+
          |             |             |
       Create         Manage        Connect
       Objects        Lifecycle     Dependencies
          |
       Spring Beans
```

---

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

The `UserService` instance created and managed by Spring is a **Spring Bean**.

---

## 2. IoC Container

Spring provides an IoC container that manages Beans.

Two important container interfaces/classes:

```text
BeanFactory
    |
    +-- Basic IoC container

ApplicationContext
    |
    +-- Advanced IoC container
```

### BeanFactory

Basic container for creating and managing Beans.

### ApplicationContext

More commonly used in modern Spring applications.

It provides Bean management along with additional Spring features such as:

* Dependency Injection
* Event handling
* Internationalization
* Resource loading
* Integration with other Spring features

### Interview Point

> `ApplicationContext` is commonly used instead of directly working with `BeanFactory` in modern Spring applications.

---

# 3. Creating Beans

There are several ways to define Spring Beans.

| Annotation        | Layer / Purpose                  |
| ----------------- | -------------------------------- |
| `@Component`      | Generic Spring-managed component |
| `@Service`        | Business/service layer           |
| `@Repository`     | Persistence/data-access layer    |
| `@Controller`     | MVC web/controller layer         |
| `@RestController` | REST API controller              |
| `@Bean`           | Explicit Bean configuration      |

---

## 4. Stereotype Annotations

These annotations are specialized forms of `@Component`.

```text
                    @Component
                        |
          +-------------+-------------+
          |             |             |
      @Service      @Repository    @Controller
                                      |
                               @RestController
```

### `@Component`

Generic Spring-managed component.

```java
@Component
public class EmailService {

}
```

### `@Service`

Used for business/service-layer classes.

```java
@Service
public class UserService {

}
```

### `@Repository`

Used for persistence/data-access classes.

```java
@Repository
public class UserRepository {

}
```

It also participates in Spring's exception translation mechanism for persistence exceptions.

### `@Controller`

Used for Spring MVC controllers.

```java
@Controller
public class UserController {

}
```

### `@RestController`

Used for REST APIs.

```java
@RestController
public class UserController {

}
```

Conceptually:

```text
@Component
   |
   +-- @Service
   +-- @Repository
   +-- @Controller
```

---

# 5. `@Bean`

`@Bean` is used when you want to explicitly register an object as a Spring Bean.

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

Spring calls the method and registers the returned object as a Bean.

### When is `@Bean` useful?

Especially useful when:

* You need explicit configuration
* You need to customize object creation
* The class comes from a third-party library
* You cannot add `@Component` to the class

Example:

```java
@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper();
}
```

---

# 6. `@Component` vs `@Bean`

### `@Component`

Placed on the **class**.

```java
@Component
class EmailService {

}
```

Spring discovers it through component scanning.

### `@Bean`

Placed on a **method**.

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

### Interview Difference

| `@Component`              | `@Bean`                               |
| ------------------------- | ------------------------------------- |
| Applied to class          | Applied to method                     |
| Automatic discovery       | Explicit registration                 |
| Uses component scanning   | Uses configuration method             |
| Good for your own classes | Useful for third-party/custom objects |

**Easy rule:**

> If you control the class → `@Component` is usually convenient.
> If you need to explicitly construct/configure the object → `@Bean`.

---

# 7. XML Bean Configuration

Spring can also define Beans using XML configuration.

Example:

```xml
<bean id="laptop"
      class="com.example.Laptop">
</bean>
```

The `id` identifies the Bean and `class` specifies the class Spring should instantiate.

Example:

```xml
<bean id="developer"
      class="com.example.Developer">

    <property name="computer"
              ref="laptop"/>

</bean>
```

Here:

```text
developer
    |
    | depends on
    ↓
 laptop
```

Spring creates the objects and injects the dependency.

### Why know XML?

Mostly useful for:

* Understanding legacy Spring applications
* Understanding how Spring configuration works internally
* Interview questions around traditional Spring

Modern Spring Boot applications generally prefer Java/annotation-based configuration.

---

# 8. Dependency Injection / Wiring

Suppose:

```java
class Developer {

    private Computer computer;

}
```

`Developer` depends on `Computer`.

Instead of manually doing:

```java
Developer developer = new Developer();
developer.setComputer(new Laptop());
```

Spring can create and connect the objects.

```text
IoC Container
     |
     +---- Developer Bean
     |
     +---- Laptop Bean
              |
              ↓
       injected into
       Developer
```

This process is called **Dependency Injection (DI)**.

## IOC Internal
                 ApplicationContext
                        |
                        ↓
                  BeanFactory
                        |
              Read Configuration
                        |
                        ↓
                Component Scan
                        |
                        ↓
                 BeanDefinition
                        |
                        ↓
              BeanDefinitionRegistry
                        |
                        ↓
                 Bean Creation
                        |
                        ↓
               Dependency Injection
                        |
                        ↓
              BeanPostProcessors
                        |
                        ↓
                  Initialization
                        |
                        ↓
                 Spring Bean
                        |
                        ↓
                 Bean is managed
---

# 9. Autowiring

Spring can automatically resolve and inject dependencies.

In older XML configuration, you may see:

```xml
autowire="byName"
```

or:

```xml
autowire="byType"
```

Modern Spring applications commonly use annotations such as:

```java
@Autowired
```

along with:

```java
@Qualifier
@Primary
```

---

# 10. Autowiring by Name

Example:

```java
class Developer {

    private Computer computer;

}
```

If the dependency/property is named:

```text
computer
```

Spring looks for a Bean matching that name.

```xml
<bean id="computer"
      class="com.example.Laptop"/>
```

Conceptually:

```text
Developer.computer
       |
       | match by name
       ↓
Bean: computer
```

---

# 11. Autowiring by Type

Spring can resolve a dependency based on its type.

Example:

```java
class Developer {

    private Computer computer;

}
```

If the container contains:

```text
Laptop implements Computer
```

Spring can inject the `Laptop` Bean because:

```text
Developer → needs Computer
Laptop    → is a Computer
```

### Problem

What if there are multiple implementations?

```java
@Component
class Laptop implements Computer {}

@Component
class Desktop implements Computer {}
```

Now:

```java
Computer computer;
```

is ambiguous.

Spring finds:

```text
Computer
   |
   +-- Laptop
   |
   +-- Desktop
```

Spring cannot automatically decide which one to inject.

---

# 12. Resolving Multiple Beans

There are two important solutions:

## `@Primary`

Mark one Bean as the default.

```java
@Component
@Primary
public class Laptop implements Computer {

}
```

Now if Spring finds multiple `Computer` Beans, it prefers `Laptop`.

```text
Computer
   |
   +-- Laptop  ← @Primary
   |
   +-- Desktop
```

---

## `@Qualifier`

Explicitly tell Spring which Bean to use.

```java
@Component
public class Laptop implements Computer {

}
```

```java
@Component
public class Desktop implements Computer {

}
```

Then:

```java
@Autowired
@Qualifier("laptop")
private Computer computer;
```

Spring specifically injects the `laptop` Bean.

### `@Primary` vs `@Qualifier`

| `@Primary`                             | `@Qualifier`                                             |
| -------------------------------------- | -------------------------------------------------------- |
| Defines default Bean                   | Explicitly selects Bean                                  |
| Used when no specific Bean is selected | Used when you want a specific Bean                       |
| One default can be preferred           | Can select different Beans at different injection points |

---

# 13. Bean Naming

By default, Spring generally uses the class name with the first letter converted to lowercase.

```java
@Component
class UserService {

}
```

Bean name:

```text
userService
```

You can explicitly specify a name:

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

# 14. Important Bean Concept

A Bean is not simply a class.

```text
Class
  ↓
Spring creates object
  ↓
Spring configures object
  ↓
Spring manages object
  ↓
Spring Bean
```

Therefore:

```java
User user = new User();
```

does **not** automatically mean `user` is a Spring Bean.

Spring must know about/manage the object through configuration, component scanning, `@Bean`, XML, etc.

---

# 15. Complete Conceptual Flow

```text
                  Spring Application
                         |
                         ↓
                  IoC Container
                         |
             +-----------+-----------+
             |           |           |
          Create      Configure    Manage
             |           |           |
             +-----------+-----------+
                         |
                         ↓
                  Spring Beans
                         |
                         ↓
                 Dependency Injection
                         |
                         ↓
                 Objects Connected
```

---

# 16. Interview Questions

### Basic

1. What is a Spring Bean?
2. What is the Spring IoC container?
3. What is the difference between an ordinary Java object and a Spring Bean?
4. What are `BeanFactory` and `ApplicationContext`?
5. How do you create a Spring Bean?

### Annotations

6. What is `@Component`?
7. What is `@Service`?
8. What is `@Repository`?
9. What is `@Controller`?
10. What is `@RestController`?
11. Why do we have different stereotype annotations if they are based on `@Component`?
12. What is `@Bean`?

### Configuration

13. `@Component` vs `@Bean`?
14. Why would you use `@Bean` instead of `@Component`?
15. What is component scanning?
16. Can Spring Beans be configured using XML?

### Dependency Injection

17. What is Dependency Injection?
18. What is autowiring?
19. What is `@Autowired`?
20. What happens when multiple Beans have the same type?
21. How does `@Primary` resolve multiple Beans?
22. How does `@Qualifier` resolve multiple Beans?
23. `@Primary` vs `@Qualifier`?

### Bean Naming

24. How does Spring determine the default Bean name?
25. Can you customize a Bean's name?

### Important Interview Scenario

**Q: Suppose you have two implementations:**

```java
@Component
class EmailNotification implements NotificationService {}

@Component
class SmsNotification implements NotificationService {}
```

And:

```java
@Autowired
private NotificationService notificationService;
```

**What happens?**

Spring finds two Beans of type `NotificationService`, so the dependency is ambiguous.

Solutions:

```java
@Primary
```

or:

```java
@Qualifier("smsNotification")
```

---

## Quick Revision

```text
Spring Bean
    ↓
Object managed by Spring IoC Container

Create Bean
    ↓
@Component / @Service / @Repository
@Controller / @RestController
@Bean
XML configuration

Inject Dependency
    ↓
@Autowired
Constructor Injection
Setter Injection
Field Injection

Multiple Beans
    ↓
@Primary
@Qualifier

IoC Container
    ↓
BeanFactory
ApplicationContext
```
