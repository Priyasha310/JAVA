## 1. Why can a single Java file have only one public class?

Main method should be inside the public class, and the filename must match the public class name.

In Java, a single file can have only one public class because of the way the Java compiler and runtime environment are designed. The public class is meant to be the main entry point for the program, and it is used to define the structure and behavior of the application. If multiple public classes were allowed in a single file, it would create ambiguity about which class should be used as the entry point for the program.