# Spring Bean Scopes

A Bean scope defines **how long a Bean lives and how many instances are created**.

## 1. Main scopes

Spring provides:

```text
singleton
prototype
request
session
application
websocket
```

The most important for interviews are:

- Singleton
- Prototype
- Request/Session basics

---

# 2. Singleton Scope ⭐⭐⭐⭐⭐

Default scope.

```java
@Component
public class UserService {
}
```

Normally, one instance is created per Spring container.

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

---

# 3. Prototype Scope

```java
@Component
@Scope("prototype")
public class ReportGenerator {
}
```

Each time the container is asked for the Bean, a new instance is created.

```java
ReportGenerator a =
        context.getBean(ReportGenerator.class);

ReportGenerator b =
        context.getBean(ReportGenerator.class);

System.out.println(a == b); // false
```

---

# 4. Request Scope

Used in web applications.

A new Bean instance is created for each HTTP request.

```java
@RequestScope
@Component
public class RequestData {
}
```

Conceptually:

```text
Request 1 --> RequestData #1
Request 2 --> RequestData #2
Request 3 --> RequestData #3
```

---

# 5. Session Scope

One instance per HTTP session.

```java
@SessionScope
@Component
public class UserSessionData {
}
```

---

# 6. Singleton vs Prototype

| Feature | Singleton | Prototype |
|---|---|---|
| Default | Yes | No |
| Instances | One per container | New instance per lookup |
| Typical use | Stateless services | Stateful objects |
| Lifecycle | Spring manages full lifecycle | Destruction is not automatically managed in the same way |

---

# 7. Important interview trap

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

The prototype is injected when the singleton is created. Calling the singleton repeatedly does **not** automatically create a fresh prototype instance.

If you truly need a new instance each time, use mechanisms such as `ObjectProvider`, a scoped proxy, or redesign the dependency.

---

## Interview questions

- What is Bean scope?
- What is the default scope?
- Singleton vs prototype?
- Is singleton one object for the whole JVM?
- What happens when prototype is injected into singleton?
- What are request and session scopes?
