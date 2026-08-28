# Java File Handling — Interview Notes

> Based on the provided video chapters: InputStream/OutputStream, FileInputStream/FileOutputStream, Buffered streams, Data streams, Object streams, character streams, InputStreamReader/OutputStreamWriter, BufferedReader and BufferedWriter.

## 1. Java I/O Overview

Java I/O is used to read data from a source and write data to a destination.

```text
Input:  Source → InputStream / Reader → Java Program
Output: Java Program → OutputStream / Writer → Destination
```

### Two major categories

| Category | Base Classes | Data |
|---|---|---|
| Byte Streams | `InputStream`, `OutputStream` | Bytes / binary data |
| Character Streams | `Reader`, `Writer` | Characters / text |

---

## 2. InputStream and OutputStream

`InputStream` reads bytes; `OutputStream` writes bytes.

### Important InputStream methods

| Method | Functionality |
|---|---|
| `read()` | Reads one byte; returns `-1` at end |
| `read(byte[])` | Reads bytes into an array |
| `skip(long)` | Skips bytes |
| `available()` | Estimates bytes readable without blocking |
| `close()` | Closes the stream |

### Important OutputStream methods

| Method | Functionality |
|---|---|
| `write(int)` | Writes one byte |
| `write(byte[])` | Writes a byte array |
| `write(byte[], int, int)` | Writes part of an array |
| `flush()` | Pushes buffered output |
| `close()` | Closes the stream |

---

## 3. FileInputStream

Reads raw bytes from a file.

```java
try (FileInputStream input =
         new FileInputStream("input.txt")) {

    int data;

    // read() returns -1 when the end of the file is reached.
    while ((data = input.read()) != -1) {
        System.out.print((char) data);
    }
}
```

Use it for byte-level/binary input such as images, PDFs, and other binary files.

---

## 4. FileOutputStream

Writes raw bytes to a file.

```java
try (FileOutputStream output =
         new FileOutputStream("output.txt")) {

    String message = "Hello Java";

    // Convert text to bytes before writing.
    output.write(message.getBytes());
}
```

---

## 5. FileInputStream + FileOutputStream

Common interview example: copying a file.

```java
try (
    FileInputStream input =
        new FileInputStream("input.txt");

    FileOutputStream output =
        new FileOutputStream("copy.txt")
) {
    byte[] buffer = new byte[1024];
    int bytesRead;

    // Read and write data in chunks instead of one byte at a time.
    while ((bytesRead = input.read(buffer)) != -1) {
        output.write(buffer, 0, bytesRead);
    }
}
```

---

## 6. BufferedInputStream

Adds buffering to byte input.

```text
File
 ↓
FileInputStream
 ↓
BufferedInputStream
 ↓
Application
```

```java
try (BufferedInputStream input =
         new BufferedInputStream(
             new FileInputStream("input.txt"))) {

    int data;

    // BufferedInputStream reduces direct I/O operations.
    while ((data = input.read()) != -1) {
        System.out.print((char) data);
    }
}
```

**Interview:** `BufferedInputStream` improves byte-input efficiency by buffering data.

---

## 7. BufferedOutputStream

Adds buffering to byte output.

```java
try (BufferedOutputStream output =
         new BufferedOutputStream(
             new FileOutputStream("output.txt"))) {

    output.write("Hello Java".getBytes());

    // Push pending buffered bytes to the underlying stream.
    output.flush();
}
```

**Interview:** Buffering reduces the number of direct I/O operations.

---

## 8. DataInputStream and DataOutputStream

These classes read/write Java primitive values in a defined binary format.

### Writing

```java
try (DataOutputStream output =
         new DataOutputStream(
             new FileOutputStream("data.bin"))) {

    // Write values in a known order.
    output.writeInt(101);
    output.writeUTF("Priyasha");
    output.writeBoolean(true);
}
```

### Reading

```java
try (DataInputStream input =
         new DataInputStream(
             new FileInputStream("data.bin"))) {

    // Read using the same order and compatible methods.
    int id = input.readInt();
    String name = input.readUTF();
    boolean active = input.readBoolean();

    System.out.println(id);
    System.out.println(name);
    System.out.println(active);
}
```

**Interview:** `DataOutputStream` writes primitive values; `DataInputStream` reads them.

---

## 9. ObjectOutputStream and ObjectInputStream

Used for Java object serialization/deserialization.

```text
Object
  ↓
ObjectOutputStream
  ↓
Bytes
  ↓
File / Stream
```

### Serialization

```java
class Employee implements Serializable {

    private final int id;
    private final String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

Employee employee =
        new Employee(101, "Priyasha");

try (ObjectOutputStream output =
         new ObjectOutputStream(
             new FileOutputStream("employee.ser"))) {

    // Serialize the Employee object.
    output.writeObject(employee);
}
```

### Deserialization

```java
try (ObjectInputStream input =
         new ObjectInputStream(
             new FileInputStream("employee.ser"))) {

    // Reconstruct the serialized object.
    Object object = input.readObject();

    System.out.println(object);
}
```

The class generally needs to implement:

```java
Serializable
```

### `transient`

```java
class Employee implements Serializable {

    private int id;

    // Excluded from default Java serialization.
    private transient String password;
}
```

**Interview:** `transient` prevents a field from participating in default Java serialization.

---

## 10. Character Streams

Character streams are designed for text.

```text
Byte streams
→ InputStream / OutputStream

Character streams
→ Reader / Writer
```

A character is not necessarily one byte, so text should be handled with appropriate character encoding.

**Interview:** Byte streams operate on bytes; character streams operate on characters and are intended for text.

---

## 11. Reader and Writer

### Reader

Reads characters.

| Method | Functionality |
|---|---|
| `read()` | Reads one character |
| `read(char[])` | Reads characters into an array |
| `skip(long)` | Skips characters |
| `ready()` | Checks whether reading can proceed without blocking |
| `close()` | Closes the reader |

### Writer

Writes characters.

| Method | Functionality |
|---|---|
| `write(int)` | Writes one character |
| `write(char[])` | Writes characters |
| `write(String)` | Writes a string |
| `append(...)` | Appends character data |
| `flush()` | Flushes output |
| `close()` | Closes the writer |

---

## 12. InputStreamReader

A bridge from **byte stream → character stream**.

```text
InputStream
    ↓
InputStreamReader
    ↓
Reader
```

It decodes bytes using a character set.

```java
try (InputStreamReader reader =
         new InputStreamReader(
             new FileInputStream("input.txt"),
             StandardCharsets.UTF_8)) {

    int character;

    // Bytes are decoded as UTF-8 characters.
    while ((character = reader.read()) != -1) {
        System.out.print((char) character);
    }
}
```

**Interview:** `InputStreamReader` converts/decodes bytes into characters.

---

## 13. OutputStreamWriter

A bridge from **character stream → byte stream**.

```text
Writer
   ↓
OutputStreamWriter
   ↓
OutputStream
```

It encodes characters into bytes.

```java
try (Writer writer =
         new OutputStreamWriter(
             new FileOutputStream("output.txt"),
             StandardCharsets.UTF_8)) {

    // Characters are encoded as UTF-8 bytes.
    writer.write("Hello Java");
}
```

**Interview:** `OutputStreamWriter` encodes characters into bytes for an output stream.

---

## 14. InputStreamReader vs OutputStreamWriter

| InputStreamReader | OutputStreamWriter |
|---|---|
| Bytes → Characters | Characters → Bytes |
| Input | Output |
| Decodes | Encodes |
| Wraps `InputStream` | Wraps `OutputStream` |

---

## 15. BufferedReader

Buffers character input and provides convenient line-based reading.

```java
try (BufferedReader reader =
         new BufferedReader(
             new FileReader("input.txt"))) {

    String line;

    // readLine() returns null when there are no more lines.
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

**Interview:** `BufferedReader` efficiently reads character data and provides `readLine()`.

---

## 16. BufferedWriter

Buffers character output.

```java
try (BufferedWriter writer =
         new BufferedWriter(
             new FileWriter("output.txt"))) {

    writer.write("Hello Java");

    // Write a platform-independent new line.
    writer.newLine();

    writer.write("File Handling");
}
```

---

## 17. BufferedReader vs BufferedInputStream

| `BufferedReader` | `BufferedInputStream` |
|---|---|
| Character-oriented | Byte-oriented |
| Extends `Reader` | Extends `InputStream` |
| Good for text | Good for binary/raw bytes |
| Has `readLine()` | No `readLine()` |

---

## 18. BufferedWriter vs BufferedOutputStream

| `BufferedWriter` | `BufferedOutputStream` |
|---|---|
| Character-oriented | Byte-oriented |
| Extends `Writer` | Extends `OutputStream` |
| Writes text | Writes bytes |

---

## 19. FileReader and FileWriter

Convenient character-stream classes for file text.

```java
try (
    FileReader reader = new FileReader("input.txt");
    FileWriter writer = new FileWriter("output.txt")
) {
    int character;

    // Copy characters from input file to output file.
    while ((character = reader.read()) != -1) {
        writer.write(character);
    }
}
```

For explicit charset control, prefer APIs where the charset can be specified, such as `InputStreamReader`/`OutputStreamWriter` or modern `java.nio.file` APIs.

---

## 20. Common Stream Combinations

### Binary input

```text
File
 ↓
FileInputStream
 ↓
Application
```

### Buffered binary input

```text
File
 ↓
FileInputStream
 ↓
BufferedInputStream
 ↓
Application
```

### Text input with explicit UTF-8

```text
File
 ↓
FileInputStream
 ↓
InputStreamReader(UTF-8)
 ↓
BufferedReader
 ↓
Application
```

### Text output with explicit UTF-8

```text
Application
 ↓
BufferedWriter
 ↓
OutputStreamWriter(UTF-8)
 ↓
FileOutputStream
 ↓
File
```

---

## 21. Important Comparison Table

| Class | Data | Main Purpose |
|---|---|---|
| `FileInputStream` | Bytes | Read file bytes |
| `FileOutputStream` | Bytes | Write file bytes |
| `BufferedInputStream` | Bytes | Buffered byte input |
| `BufferedOutputStream` | Bytes | Buffered byte output |
| `DataInputStream` | Primitive data | Read primitive values |
| `DataOutputStream` | Primitive data | Write primitive values |
| `ObjectInputStream` | Objects | Deserialize objects |
| `ObjectOutputStream` | Objects | Serialize objects |
| `InputStreamReader` | Characters | Bytes → characters |
| `OutputStreamWriter` | Characters | Characters → bytes |
| `BufferedReader` | Characters | Buffered character input / lines |
| `BufferedWriter` | Characters | Buffered character output |

---

## 22. `flush()` vs `close()`

### `flush()`

```java
output.flush();
```

Pushes pending buffered output to the underlying destination.

The stream remains usable.

### `close()`

```java
output.close();
```

Releases the resource and closes the stream.

**Interview:** `flush()` pushes pending output; `close()` releases the resource.

---

## 23. Try-With-Resources

Prefer try-with-resources for closeable resources.

```java
try (FileInputStream input =
         new FileInputStream("input.txt")) {

    // Use the resource.

} // Java automatically closes the resource here.
```

It provides automatic cleanup even when an exception occurs.

---

## 24. Common Exceptions

Classic Java I/O operations commonly involve:

```java
IOException
```

Example:

```java
try {
    // File I/O operation.
} catch (IOException e) {

    // Handle the I/O failure.
    e.printStackTrace();
}
```

---

## 25. Common Interview Questions

### Q1. InputStream vs Reader?

> `InputStream` works with bytes; `Reader` works with characters.

### Q2. OutputStream vs Writer?

> `OutputStream` writes bytes; `Writer` writes characters.

### Q3. Why use BufferedInputStream?

> To buffer byte input and reduce direct I/O operations.

### Q4. Why use BufferedReader?

> To efficiently read character data and provide convenient `readLine()` support.

### Q5. What is InputStreamReader?

> A bridge that decodes bytes from an InputStream into characters.

### Q6. What is OutputStreamWriter?

> A bridge that encodes characters into bytes for an OutputStream.

### Q7. BufferedReader vs InputStreamReader?

> `InputStreamReader` performs byte-to-character decoding; `BufferedReader` adds character buffering and `readLine()`.

### Q8. DataInputStream vs ObjectInputStream?

> `DataInputStream` reads primitive values; `ObjectInputStream` reads serialized objects.

### Q9. What is serialization?

> Converting an object into a byte representation for storage or transmission.

### Q10. What is deserialization?

> Reconstructing an object from its serialized representation.

### Q11. What does `transient` do?

> It excludes a field from default Java serialization.

### Q12. Why does `read()` return `-1`?

> `-1` indicates the end of the stream.

### Q13. Why call `flush()`?

> To force pending buffered output to the underlying destination.

### Q14. Should we manually call `close()`?

> Prefer try-with-resources for automatic resource management.

---

## 26. Quick Decision Guide

```text
What data?
    |
    +-- Binary / raw bytes
    |      |
    |      +-- FileInputStream / FileOutputStream
    |      +-- Add Buffered streams when useful
    |
    +-- Text
           |
           +-- Reader / Writer
           |
           +-- Need explicit charset?
           |      |
           |      +-- InputStreamReader /
           |          OutputStreamWriter
           |
           +-- Need line-based input?
                  |
                  +-- BufferedReader
```

---

## 27. Most Important Hierarchy

```text
InputStream
├── FileInputStream
├── BufferedInputStream
├── DataInputStream
└── ObjectInputStream

OutputStream
├── FileOutputStream
├── BufferedOutputStream
├── DataOutputStream
└── ObjectOutputStream

Reader
├── InputStreamReader
│   └── FileReader
└── BufferedReader

Writer
├── OutputStreamWriter
│   └── FileWriter
└── BufferedWriter
```

---

## 28. One-Minute Revision

```text
InputStream
→ read bytes

OutputStream
→ write bytes

Reader
→ read characters

Writer
→ write characters

BufferedInputStream
→ buffered byte input

BufferedOutputStream
→ buffered byte output

BufferedReader
→ buffered character input
→ readLine()

BufferedWriter
→ buffered character output

DataInputStream
→ read primitive data

DataOutputStream
→ write primitive data

ObjectInputStream
→ deserialize objects

ObjectOutputStream
→ serialize objects

InputStreamReader
→ bytes → characters

OutputStreamWriter
→ characters → bytes
```

---

## 29. Strong Interview Answer

> **Java I/O has byte streams and character streams. `InputStream` and `OutputStream` work with bytes and are commonly used for binary data, while `Reader` and `Writer` work with characters and are suited to text. Buffered classes improve efficiency by reducing direct I/O operations. `InputStreamReader` and `OutputStreamWriter` bridge byte streams and character streams while handling decoding/encoding. `DataInputStream`/`DataOutputStream` handle primitive values, and `ObjectInputStream`/`ObjectOutputStream` handle Java object serialization and deserialization.**
