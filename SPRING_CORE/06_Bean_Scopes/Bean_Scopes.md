# Spring Bean Scopes

A Bean scope defines **how long a Bean lives and how many instances are created**.

## 1. What is a Bean scope?

Bean scope answers these questions:

- How many instances of a Bean will be created?
- How long will they live?
- Is the same instance reused across requests/components?

Spring supports the following scopes:

```text
singleton
prototype
request
session
application
websocket
```

The most important ones for interviews are:

- Singleton
- Prototype
- Request/Session basics

---

# 2. Singleton Scope ⭐⭐⭐⭐⭐

This is the default scope in Spring.

```java
@Component
public class UserService {
}
```

By default, Spring creates only one instance of this Bean per Spring container.

```text
ApplicationContext
       |
       +---- UserService instance #1
       |
       +---- Every injection gets #1
```

Example:

```java
UserService a = context.getBean(UserService.class);
UserService b = context.getBean(UserService.class);

System.out.println(a == b); // true
```

### Important

Singleton means:

> One Bean instance per Spring container.

It does **not** mean one instance for the entire JVM in every possible context.

A Spring application may have multiple containers or multiple contexts, and each container can have its own singleton instances.

---

# 3. Prototype Scope

A prototype Bean creates a new instance every time Spring provides it.

```java
@Component
@Scope("prototype")
public class ReportGenerator {
}
```

Another common form is:

```java
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportGenerator {
}
```

Example:

```java
ReportGenerator a = context.getBean(ReportGenerator.class);
ReportGenerator b = context.getBean(ReportGenerator.class);

System.out.println(a == b); // false
```

### When to use prototype

- Stateful objects
- Objects that should not be shared
- Objects requiring fresh state for each use

### Important note

Prototype Beans are not like singleton Beans in terms of lifecycle management. Spring creates them but does not manage their complete lifecycle in the same way as singleton Beans.

---

# 4. Request Scope

Used in web applications.

Each HTTP request gets a new instance of the Bean.

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestData {
}
```

Conceptually:

```text
Request 1 --> RequestData #1
Request 2 --> RequestData #2
Request 3 --> RequestData #3
```

This is useful for request-specific data like headers, locale, user request details, etc.

---

# 5. Session Scope

One instance per HTTP session.

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionData {
}
```

Conceptually:

```text
Session 1 --> UserSessionData #1
Session 2 --> UserSessionData #2
```

Used when data is tied to a user session.

---

# 6. Application Scope

One instance per ServletContext / application.

```java
@Component
@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
public class AppConfigData {
}
```

This is shared across all requests in the application.

---

# 7. WebSocket Scope

One Bean instance per WebSocket session.

```java
@Component
@Scope(value = "websocket")
public class WebSocketSessionBean {
}
```

Used in web socket based applications.

---

# 8. Singleton vs Prototype

| Feature | Singleton | Prototype |
|---|---|---|
| Default | Yes | No |
| Instances | One per container | New instance per lookup |
| Typical use | Stateless services | Stateful objects |
| Shared across app | Yes | No |
| Lifecycle | Spring manages lifecycle fully | Created on demand; not managed as a shared singleton |

---

# 9. Important interview trap

If a prototype Bean is injected into a singleton:

```java
@Component
class SingletonService {

    private final PrototypeService prototype;

    SingletonService(PrototypeService prototype) {
        this.prototype = prototype;
    }
}
```
A singleton bean means one instance per Spring container **created from that bean definition**.

The prototype is injected when the singleton is created. Calling the singleton repeatedly does **not** automatically create a fresh prototype instance.

If you truly need a new instance each time, use:

- `ObjectProvider`
- Scoped proxies
- Re-design dependency

Example with `ObjectProvider`:

```java
@Component
public class SingletonService {

    private final ObjectProvider<PrototypeService> prototypeProvider;

    public SingletonService(ObjectProvider<PrototypeService> prototypeProvider) {
        this.prototypeProvider = prototypeProvider;
    }

    public void run() {
        PrototypeService p = prototypeProvider.getObject();
    }
}
```

This makes a new prototype instance when needed.

---

# 10. Lazy vs Eager Initialization

By default, Spring creates singleton Beans as soon as the application context starts.

This is called eager initialization.

## Eager initialization (default)

```java
@Component
public class MyService {
}
```

When the container starts, Spring creates `MyService` immediately if it is a singleton.

This is useful for:

- startup validation
- early resource creation
- critical dependencies that should exist before requests arrive

### Example

```java
AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);
```

At this point, singleton Beans are already created.

---

## Lazy initialization

If a Bean is marked lazy, it is created only when it is actually requested for the first time.

```java
@Component
@Lazy
public class HeavyService {
}
```

This delays creation and can improve startup time if some Beans are expensive.

### `@Lazy` on configuration class

```java
@Configuration
@Lazy
public class AppConfig {

    @Bean
    public HeavyService heavyService() {
        return new HeavyService();
    }
}
```

This makes all Beans declared in that configuration class lazy.

### `@Lazy` on injection point

```java
@Component
public class OrderService {

    private final HeavyService heavyService;

    public OrderService(@Lazy HeavyService heavyService) {
        this.heavyService = heavyService;
    }
}
```

Here, Spring injects a lazy proxy instead of creating the Bean immediately.

---

## Important point about eager/lazy

- There is no `@Eager` annotation used in normal Spring code.
- Eager is simply the default behavior for singleton Beans.
- `@Lazy` is used when you want to delay creation.

So in simple terms:

```text
singleton + no @Lazy = eager
singleton + @Lazy = lazy
prototype = created when requested
```

---

## Interview-style summary

- Default scope of a Spring Bean is singleton.
- Prototype creates new instance each lookup.
- Request/session scope are for web apps.
- Prototype injected into singleton is a classic interview trap.
- Singleton Beans are eager by default; `@Lazy` delays initialization.
- There is no `@Eager` annotation; eager is just the default behavior.

---

## Interview questions

- What is Bean scope?
- What is the default scope?
- Singleton vs prototype?
- Is singleton one object for the whole JVM?
- What happens when prototype is injected into singleton?
- What are request and session scopes?
- What is lazy initialization?
- Why use `@Lazy`?
- Is there a `@Eager` annotation in Spring?
- Are singleton Beans created at startup or on first use?
