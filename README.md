# Calculator-Application-
# Calculator Application Using Method Overloading

## Project Overview
This is a **Java-based Calculator Application** that demonstrates the concept of **method overloading**. The application allows users to perform basic arithmetic operations such as addition, subtraction, multiplication, and division using overloaded methods. It features a user-friendly console interface for input and output.

---

## Features and Capabilities
The calculator supports:

1. **Addition**
   - Two integers
   - Two doubles
   - Three integers

2. **Subtraction**
   - Two integers

3. **Multiplication**
   - Two doubles

4. **Division**
   - Two integers
   - Handles division by zero gracefully without exceptions

5. **User-friendly menu**
   - Users can select the operation to perform
   - Loop continues until the user chooses to exit

---

## Objectives
- To understand and implement **method overloading** in Java.
- To handle different data types and numbers of arguments using overloaded methods.
- To implement basic arithmetic operations with proper input validation.
- To demonstrate a simple console-based **user interface** in Java.

---

## How It Works
- The `Calculator` class contains the overloaded methods for addition and regular methods for subtraction, multiplication, and division.
- The `CalculatorApplication` class provides a **menu-driven interface** for user interaction.
- Division by zero is handled without using exception handling by returning a default value and displaying an error message.

### Method Overloading Example
```java
public int add(int a, int b) { ... }
public double add(double a, double b) { ... }
public int add(int a, int b, int c) { ... }
