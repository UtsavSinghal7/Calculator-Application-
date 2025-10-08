import java.util.Scanner;
   class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

        public double add(double a, double b) {
            return a + b;
        }

        public int add(int a, int b, int c) {
            return a + b + c;
        }

        public int subtract(int a, int b) {
            return a - b;
        }

        public double multiply(double a, double b) {
            return a * b;
        }

        public double divide(int a, int b) {
            if (b == 0) {
                System.out.println("Error: Division by zero is not allowed!");
                return 0;
            }
            return (double) a / b;
        }
    }
    class CalculatorApplication {

        private Scanner sc = new Scanner(System.in);
        private Calculator calc = new Calculator();

        public void performAddition() {
            System.out.println("Choose addition type:");
            System.out.println("1. Two integers\n2. Two doubles\n3. Three integers");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter first integer: ");
                    int a = sc.nextInt();
                    System.out.print("Enter second integer: ");
                    int b = sc.nextInt();
                    System.out.println("Result: " + calc.add(a, b));
                    break;

                case 2:
                    System.out.print("Enter first double: ");
                    double x = sc.nextDouble();
                    System.out.print("Enter second double: ");
                    double y = sc.nextDouble();
                    System.out.println("Result: " + calc.add(x, y));
                    break;

                case 3:
                    System.out.print("Enter first integer: ");
                    int p = sc.nextInt();
                    System.out.print("Enter second integer: ");
                    int q = sc.nextInt();
                    System.out.print("Enter third integer: ");
                    int r = sc.nextInt();
                    System.out.println("Result: " + calc.add(p, q, r));
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        public void performSubtraction() {
            System.out.print("Enter first integer: ");
            int a = sc.nextInt();
            System.out.print("Enter second integer: ");
            int b = sc.nextInt();
            System.out.println("Result: " + calc.subtract(a, b));
        }

        public void performMultiplication() {
            System.out.print("Enter first double: ");
            double a = sc.nextDouble();
            System.out.print("Enter second double: ");
            double b = sc.nextDouble();
            System.out.println("Result: " + calc.multiply(a, b));
        }

        public void performDivision() {
            System.out.print("Enter first integer: ");
            int a = sc.nextInt();
            System.out.print("Enter second integer: ");
            int b = sc.nextInt();

            double result = calc.divide(a, b);
            System.out.println("Result: " + result);
        }

        public void mainMenu() {
            int choice;
            do {
                System.out.println("\n=== Calculator Application ===");
                System.out.println("1. Add Numbers");
                System.out.println("2. Subtract Numbers");
                System.out.println("3. Multiply Numbers");
                System.out.println("4. Divide Numbers");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        performAddition();
                        break;
                    case 2:
                        performSubtraction();
                        break;
                    case 3:
                        performMultiplication();
                        break;
                    case 4:
                        performDivision();
                        break;
                    case 5:
                        System.out.println("Exiting... Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 5);
        }

        public static void main(String[] args) {
            CalculatorApplication app = new CalculatorApplication();
            app.mainMenu();
        }
    }

