# Spring Container and ApplicationContext

## 1. What is the Spring Container?

The **Spring Container** is responsible for managing Spring Beans.

It handles:

- Bean creation
- Dependency Injection
- Configuration
- Bean lifecycle
- Bean scope
- Bean destruction

```text
             Spring Container
                    |
       +------------+------------+
       |            |            |
       ↓            ↓            ↓
 UserService    UserRepo    EmailService
```

> The Spring Container is the core mechanism that creates, configures, connects, and manages Beans.

---

## 2. ApplicationContext

`ApplicationContext` is the commonly used Spring container interface.

It provides:

- Bean management
- Dependency Injection
- Application events
- Resource loading
- Message resolution / internationalization
- Environment and profile support

Conceptually:

```text
ApplicationContext
        |
        ↓
Spring IoC Container
        |
        ↓
Spring Beans
```

---

## 3. BeanFactory vs ApplicationContext

### BeanFactory

A basic IoC container interface responsible for Bean creation and management.

### ApplicationContext

A more feature-rich container built on the core BeanFactory functionality.

It adds support for:

- Events
- Internationalization
- Resource loading
- Environment/profiles
- Easier integration with Spring infrastructure

```text
BeanFactory
     ↑
     |
ApplicationContext
```

For modern Spring applications, `ApplicationContext` is the usual choice.

---

## 4. Creating an ApplicationContext

Example using Java configuration:

```java
@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```

Application:

```java
public class Main {

    public static void main(String[] args) {

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService service =
                context.getBean(UserService.class);

        service.createUser();
    }
}
```

Conceptually:

```text
AppConfig
   ↓
AnnotationConfigApplicationContext
   ↓
IoC Container
   ↓
Spring Beans
```

---

## 5. Getting a Bean

By type:

```java
UserService service =
        context.getBean(UserService.class);
```

By name and type:

```java
UserService service =
        context.getBean("userService", UserService.class);
```

`getBean()` asks the container for a managed Bean.

---

## 6. What Happens When the Container Starts?

Conceptually:

```text
Application starts
       ↓
ApplicationContext created
       ↓
Reads configuration
       ↓
Scans components / configuration
       ↓
Creates BeanDefinitions
       ↓
Registers BeanDefinitions
       ↓
Creates Beans
       ↓
Resolves dependencies
       ↓
Injects dependencies
       ↓
Initializes Beans
       ↓
Beans are ready
```

---

## 7. IoC Container Internals

This explains **how the Spring container internally creates and manages Beans**.

### BeanDefinition

A `BeanDefinition` is metadata describing how Spring should create and configure a Bean.

Conceptually:

```text
BeanDefinition
-------------------------
Bean name: userService
Class: UserService
Scope: singleton
Dependencies: ...
```

Think:

> **BeanDefinition = blueprint/instructions for creating a Bean**

### BeanDefinitionRegistry

The container maintains/registers BeanDefinitions.

```text
Component / @Bean / XML
          ↓
    BeanDefinition
          ↓
BeanDefinitionRegistry
```

### Bean Instantiation

Spring uses the BeanDefinition to create the actual object.

```text
BeanDefinition
      ↓
Object created
      ↓
Spring Bean
```

### Dependency Injection

After identifying the required dependencies, Spring resolves them from the container and injects them into the Bean.

```text
UserService
     |
     | requires
     ↓
UserRepository Bean
```

### BeanPostProcessor

Spring can process Beans before and after initialization.

```text
Bean created
    ↓
Before initialization
    ↓
Initialization
    ↓
After initialization
    ↓
Bean ready
```

`BeanPostProcessor` is an important extension point used by Spring to process or enhance Beans.

---

## 8. Container Startup — Simplified Internal Flow

```text
                ApplicationContext
                        |
                        ↓
                 Read Configuration
                        |
                        ↓
                  Component Scan
                        |
                        ↓
                  BeanDefinitions
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
                 BeanPostProcessor
                        |
                        ↓
                  Initialization
                        |
                        ↓
                    Bean Ready
```

### Important distinction

```text
Spring Container & ApplicationContext
        ↓
How the container works

Spring Beans
        ↓
What a Bean is
How Beans are defined
Bean lifecycle
Bean scope
```

---

## 9. Container and Dependency Injection

Suppose:

```java
@Service
class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
```

The container:

1. Creates `UserRepository`
2. Creates `UserService`
3. Finds that `UserService` requires `UserRepository`
4. Injects the `UserRepository` Bean into `UserService`

```text
Spring Container
      |
      +-- UserRepository Bean
      |
      +-- UserService Bean
              |
              +-- depends on
                      ↓
               UserRepository
```

---

## 10. ApplicationContext Features

### Bean Management

Creates and manages Spring Beans.

### Dependency Injection

Resolves and injects dependencies.

### Events

Supports application event publishing and listening.

### Resource Loading

Provides access to application resources.

### Message Resolution

Supports internationalization and message resolution.

### Environment / Profiles

Provides access to environment properties and Spring profiles.

---

## 11. Interview Questions

### Basic

- What is the Spring Container?
- What is IoC?
- What is `ApplicationContext`?
- What does `ApplicationContext` manage?
- How do you create an `ApplicationContext`?
- How do you retrieve a Bean from the container?

### Comparison

- `BeanFactory` vs `ApplicationContext`?
- Why is `ApplicationContext` commonly used?

### Internals

- What happens when `ApplicationContext` starts?
- What is a `BeanDefinition`?
- What is `BeanDefinitionRegistry`?
- How does the container create a Bean?
- What is `BeanPostProcessor`?
- How does dependency injection happen internally?

### Practical

- How are Beans registered with the container?
- How does the container resolve dependencies?
