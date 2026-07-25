## 1. Why can a single Java file have only one public class?

Main method should be inside the public class, and the filename must match the public class name.

In Java, a single file can have only one public class because of the way the Java compiler and runtime environment are designed. The public class is meant to be the main entry point for the program, and it is used to define the structure and behavior of the application. If multiple public classes were allowed in a single file, it would create ambiguity about which class should be used as the entry point for the program.

## 2. Why Shouldn't We Use float/double for Money?

float and double follow the IEEE 754 floating-point standard.
They store numbers in binary (base-2) rather than decimal (base-10).
Many decimal values (like 0.1 or 0.2) cannot be represented exactly in binary, which causes rounding errors.

Example:
System.out.println(0.3f - 0.1f);
Output:
0.20000002 instead of 0.2

Therefore, For financial or currency calculations, use: **BigDecimal** instead of float or double.