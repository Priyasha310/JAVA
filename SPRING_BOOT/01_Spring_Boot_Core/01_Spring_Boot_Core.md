# Spring Boot Core — Interview Notes (2–3 YOE)

## 1. Spring vs Spring Boot

**Spring:** Framework for building Java applications using IoC, DI, AOP, MVC, etc.

**Spring Boot:** Built on Spring; reduces configuration and provides auto-configuration, starters, embedded servers, and production-ready features.

| Spring | Spring Boot |
|---|---|
| More manual configuration | Auto-configuration |
| External server commonly used | Embedded server |
| Dependencies configured individually | Starter dependencies |
| More boilerplate | Convention over configuration |

---

## 2. `@SpringBootApplication`

Main Spring Boot annotation.

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Equivalent to:

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
```

### `@SpringBootConfiguration`
Specialized `@Configuration` annotation identifying the main Boot configuration class.

### `@ComponentScan`
Scans the package of the main class and its sub-packages for Spring components.

### `@EnableAutoConfiguration`
Tells Spring Boot to configure beans based on classpath dependencies, existing beans, properties, and conditions.

---

## 3. Auto-Configuration

Basic flow:

```text
Application starts
      ↓
@EnableAutoConfiguration
      ↓
Check classpath + properties + existing beans
      ↓
Evaluate conditions
      ↓
Create applicable default beans
```

Common conditions:

- `@ConditionalOnClass`
- `@ConditionalOnMissingBean`
- `@ConditionalOnBean`
- `@ConditionalOnProperty`
- `@ConditionalOnWebApplication`

**Interview point:** Auto-configuration provides defaults but generally backs off when the application provides its own configuration.

Disable specific auto-configuration:

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```

---

## 4. Starter Dependencies

Starters provide a convenient dependency set.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Common starters:

- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-test`

---

## 5. `SpringApplication.run()`

It bootstraps the application:

1. Creates `ApplicationContext`
2. Performs component scanning
3. Applies auto-configuration
4. Creates beans
5. Starts embedded server for web applications

---

## 6. Embedded Server

Spring Boot web applications can run with an embedded server such as Tomcat.

Typical flow:

```text
java -jar app.jar
      ↓
SpringApplication.run()
      ↓
ApplicationContext
      ↓
Embedded Tomcat starts
      ↓
Application ready
```

---

## 7. Configuration

### `application.properties`

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost:3306/app
```

### `application.yml`

```yaml
server:
  port: 8081
```

### `@Value`

```java
@Value("${server.port}")
private int port;
```

### `@ConfigurationProperties`

Preferred for grouping related configuration.

```java
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String name;
}
```

---

## 8. Profiles

Use different configuration for different environments.

```text
application.yml
application-dev.yml
application-prod.yml
```

Activate:

```properties
spring.profiles.active=dev
```

Use:

```java
@Profile("dev")
```

---

## 9. Environment Variables

Useful for environment-specific values and secrets.

```properties
spring.datasource.url=${DB_URL}
```

Avoid hardcoding passwords/secrets in source code.

---

## 10. Maven Basics

Important files/commands:

- `pom.xml` → dependencies + build configuration
- `mvn clean` → removes build output
- `mvn test` → runs tests
- `mvn package` → creates artifact
- `mvn spring-boot:run` → runs Boot application

---

## 11. Common Interview Questions

**Q: What does `@SpringBootApplication` contain?**

`@SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan`

**Q: What is auto-configuration?**

Automatic configuration based on classpath, conditions, properties, and existing beans.

**Q: Can auto-configuration be overridden?**

Yes. Custom beans/configuration can override or cause Boot's conditional configuration to back off.

**Q: Why are starters useful?**

They provide a curated set of related dependencies and reduce dependency-management boilerplate.

**Q: Why does component scanning usually work automatically?**

Because `@SpringBootApplication` includes `@ComponentScan`, which scans from the main application's package downward.
