# Maven — Complete Interview Notes

# Maven — Interview Notes

## What is Maven?

**Maven is a project management and build tool for Java projects.**

It helps with:

- Standard project structure
- Compilation
- Testing
- Packaging
- Dependency management

### Why Maven?

Without Maven, developers may need to manually download JARs, manage versions, resolve transitive dependencies, compile code, and package applications.

> **Key Point:** Maven reduces manual build and dependency-management work.

## Maven's Main Responsibilities

```text
Maven
 ├── Standard Project Structure
 ├── Compile Code
 ├── Test Code
 ├── Package Application
 └── Manage Dependencies
```

## Important Terms

- **Dependency:** External library required by the project.
- **Plugin:** Provides build-related functionality.
- **Lifecycle:** Standard sequence of Maven build phases.
- **Build System:** Defines how a project is compiled, tested, and packaged.

### Interview Questions

1. What is Maven?
2. Why do we use Maven?
3. What problems does Maven solve?
4. What is a dependency?
5. What is a Maven lifecycle?

---

# JAR, Library, Dependency & Classpath

## JAR File

**JAR = Java Archive**

A JAR packages multiple compiled classes and resources into a single archive.

```text
MyLibrary.jar
 ├── .class files
 ├── resources
 └── packages
```

### Why JAR?

Instead of sharing many individual `.class` files, a project/library can be packaged into one JAR.

Two major uses:

1. Share Java code easily.
2. Use third-party libraries in an application.

## Library vs Application

### Library

- Contains reusable code/classes.
- Used by another application.
- Is not normally independently runnable.
- Becomes a **dependency** when an application uses it.

### Application

- Intended to run.
- A Spring Boot application can be packaged as an executable JAR.

> **Key Point:** Library = reusable code; Application = runnable program.

## Executable JAR

A Spring Boot application can be packaged as an **executable JAR**, allowing the application to run as a packaged artifact.

## Classpath

The **classpath** is where Java looks for classes required by the application.

```text
Application
    ↓
Classpath
    ↓
Application + required libraries
    ↓
JVM
```

If code uses a class from a third-party JAR, that JAR must be available to the runtime classpath.

### Interview Questions

1. What is a JAR?
2. Why are JAR files used?
3. Library vs application?
4. What is a dependency?
5. What is an executable JAR?
6. What is classpath?

---

# Maven — Project Structure

Maven provides a **standard project structure**, making projects consistent across teams and IDEs.

## Standard Structure

```text
project/
│
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   │
│   └── test/
│       └── java/
│
└── target/
```

## `src/main/java`

Contains application Java source code.

Examples:

```text
Controller
Service
Repository
Model
```

## `src/main/resources`

Contains non-Java resources:

- Properties files
- Static resources
- Images
- Other configuration/resources

For Spring Boot:

```text
src/main/resources/application.properties
```

## `src/test/java`

Contains test code.

The transcript mentions tools/libraries such as:

- JUnit
- Mockito

The test package structure generally mirrors the main package structure.

## `target/`

Contains generated build output.

It can include:

- Compiled `.class` files
- Generated files
- Packaged JAR files

Example:

```text
target/
├── classes/
├── generated-sources/
└── application.jar
```

> **Key Point:** `src` contains source code; `target` contains generated build output.

## Package Structure

For:

```text
groupId = com.example
artifactId = demo
```

the Java package may look like:

```text
com/
└── example/
    └── demo/
```

`com.example.demo` represents the package hierarchy:

```text
com → example → demo
```

### Interview Questions

1. Explain Maven's standard project structure.
2. What goes inside `src/main/java`?
3. What goes inside `src/main/resources`?
4. What goes inside `src/test/java`?
5. What is the `target` folder?
6. Why does Maven use a standard structure?

---

# Maven — `pom.xml`, GAV & Dependencies

## What is `pom.xml`?

**POM = Project Object Model**

`pom.xml` is the most important Maven configuration file.

It contains project information/configuration such as:

- Project coordinates
- Dependencies
- Plugins
- Other Maven configuration

```xml
<project>
    ...
</project>
```

`<project>` is the root element of the POM.

## `modelVersion`

The transcript highlights:

```xml
<modelVersion>4.0.0</modelVersion>
```

This is the **POM model version**, not the Maven version.

## GAV — Maven Coordinates ⭐

The three important coordinates are:

```text
GroupId
ArtifactId
Version
```

Together they identify an artifact/version.

### `groupId`

Identifies the organization/group.

A common convention is to reverse a domain name:

```text
com.example
```

Example:

```text
in.codersarmy
```

### `artifactId`

Identifies the project/artifact.

Example:

```text
maven-demo
```

### `version`

Identifies the artifact version.

Example:

```text
1.0-SNAPSHOT
```

## Dependency Declaration

Dependencies are declared in `pom.xml`.

```xml
<dependency>
    <groupId>...</groupId>
    <artifactId>...</artifactId>
    <version>...</version>
</dependency>
```

Maven uses this information to identify and obtain the required artifact.

## Transitive Dependencies ⭐

A dependency can depend on other dependencies.

```text
Application
    ↓
Dependency A
    ↓
Dependency B
    ↓
Dependency C
```

B and C are **transitive dependencies** from the application's perspective.

Maven can resolve these automatically.

> **Key Point:** A transitive dependency is a dependency required by another dependency.

## Why `pom.xml` Matters

```text
pom.xml
   ↓
Maven reads dependencies
   ↓
Resolves required artifacts
   ↓
Downloads missing artifacts
   ↓
Makes them available to the build
```

### Interview Questions

1. What is POM?
2. What is `pom.xml`?
3. What is GAV?
4. What is `groupId`?
5. What is `artifactId`?
6. What is `version`?
7. What are transitive dependencies?
8. Where are Maven dependencies declared?

---

# Maven — Lifecycle & Repository

## Maven Lifecycle ⭐⭐⭐

Maven provides a standard build lifecycle.

Important phases from the transcript:

```text
clean
  ↓
validate
  ↓
compile
  ↓
test
  ↓
package
```

## Important Phases

### `clean`

Removes previous build output.

### `validate`

Validates the Maven project.

### `compile`

Compiles Java source code into bytecode.

Typical output:

```text
target/classes
```

### `test`

Runs project tests.

### `package`

Packages the compiled project into a distributable format such as a JAR.

Example:

```text
target/
└── maven-demo-1.0-SNAPSHOT.jar
```

> **Key Point:** `compile` creates compiled output; `package` creates the packaged artifact.

## Build Flow

```text
Source Code
    ↓
validate
    ↓
compile
    ↓
test
    ↓
package
    ↓
JAR
```

## Maven Repository

Maven uses repositories to obtain dependencies.

### Local Repository

Maven maintains a local repository under:

```text
~/.m2/repository
```

The transcript describes `.m2` as a hidden folder in the user's home directory.

Downloaded dependencies are stored locally for reuse.

### Maven Central

If an artifact is not available locally, Maven can download it from a remote repository such as Maven Central.

## Dependency Resolution Flow ⭐

```text
pom.xml
   ↓
Read dependency
   ↓
Check Local Repository
(~/.m2/repository)
   │
   ├── Found → Use it
   │
   └── Not Found
          ↓
     Remote Repository
          ↓
       Download
          ↓
   Store locally
```

### Why Local Caching?

After an artifact is downloaded, Maven can reuse the local copy on later builds instead of downloading it again.

## Plugins

Maven also uses **plugins** to perform build-related tasks.

> **Key Point:** Dependencies are libraries used by the project; plugins provide Maven build functionality.

### Interview Questions

1. What is Maven lifecycle?
2. What does `clean` do?
3. What happens during `compile`?
4. What happens during `test`?
5. What happens during `package`?
6. What is a Maven repository?
7. What is `.m2/repository`?
8. What is Maven Central?
9. How does Maven resolve dependencies?
10. What is a Maven plugin?