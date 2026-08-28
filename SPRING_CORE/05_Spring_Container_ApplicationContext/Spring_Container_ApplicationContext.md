# Spring Container and ApplicationContext

## 1. What is the Spring Container?

The Spring Container is responsible for managing Spring Beans.

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
       v            v            v
   UserService  UserRepo    EmailService
```

---

## 2. ApplicationContext

`ApplicationContext` is the commonly used Spring container interface.

It provides features such as:

- Bean management
- Dependency Injection
- Application events
- Resource loading
- Message resolution
- Environment/profile support

---

## 3. Simple Java configuration example

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

---

## 4. Getting a Bean

```java
UserService service =
        context.getBean(UserService.class);
```

Or by name:

```java
UserService service =
        context.getBean("userService", UserService.class);
```

---

## 5. BeanFactory vs ApplicationContext

### BeanFactory

Basic IoC container.

### ApplicationContext

More feature-rich container built on top of BeanFactory concepts.

It adds support for features such as:

- Events
- Internationalization
- Resource loading
- Easier integration with Spring infrastructure

For modern Spring applications, `ApplicationContext` is the usual choice.

---

## 6. What happens conceptually?

```text
Application starts
       |
       v
Spring creates ApplicationContext
       |
       v
Reads configuration
       |
       v
Scans components / configuration
       |
       v
Creates Beans
       |
       v
Resolves dependencies
       |
       v
Injects dependencies
       |
       v
Beans are ready
```

---

## 7. Interview questions

- What is the Spring Container?
- What is ApplicationContext?
- BeanFactory vs ApplicationContext?
- How do you retrieve a Bean?
- Who manages Spring Beans?
