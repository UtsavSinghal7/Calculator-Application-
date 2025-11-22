import java.util.InputMismatchException;
import java.util.Scanner;

    public class ResultManager {

        static class InvalidMarksException extends Exception {
            public InvalidMarksException(String msg) { super(msg); }
        }


        static class Student {
            int roll;
            String name;
            int[] marks; // 3 subjects

            Student(int roll, String name, int[] marks) throws InvalidMarksException {
                if (marks == null || marks.length != 3)
                    throw new InvalidMarksException("Marks must be provided for 3 subjects.");
                this.roll = roll;
                this.name = name;
                this.marks = marks;
                validateMarks(); // may throw
            }

            void validateMarks() throws InvalidMarksException {
                for (int i = 0; i < marks.length; i++) {
                    if (marks[i] < 0 || marks[i] > 100)
                        throw new InvalidMarksException("Invalid marks for subject " + (i+1) + ": " + marks[i]);
                }
            }

            double average() {
                return (marks[0] + marks[1] + marks[2]) / 3.0;
            }

            boolean isPass() { // pass if all >= 35
                for (int m : marks) if (m < 35) return false;
                return true;
            }

            void display() {
                System.out.println("Roll Number: " + roll);
                System.out.println("Student Name: " + name);
                System.out.print("Marks: ");
                System.out.println(marks[0] + " " + marks[1] + " " + marks[2]);
                System.out.println("Average: " + average());
                System.out.println("Result: " + (isPass() ? "Pass" : "Fail"));
            }
        }

        private Student[] students;
        private int count = 0;
        private Scanner sc = new Scanner(System.in);

        public ResultManager(int capacity) { students = new Student[capacity]; }

        public void addStudent() throws InvalidMarksException {
            if (count >= students.length) {
                System.out.println("Storage full.");
                return;
            }
            System.out.print("Enter Roll Number: ");
            int roll = sc.nextInt(); sc.nextLine();
            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            int[] marks = new int[3];
            for (int i = 0; i < 3; i++) {
                System.out.print("Enter marks for subject " + (i+1) + ": ");
                marks[i] = sc.nextInt();
            }

            students[count++] = new Student(roll, name, marks);
            System.out.println("Student added successfully. Returning to main menu...");
        }

        public void showStudentDetails() {
            System.out.print("Enter Roll Number to search: ");
            try {
                int roll = sc.nextInt();
                for (int i = 0; i < count; i++) {
                    if (students[i] != null && students[i].roll == roll) {
                        students[i].display();
                        System.out.println("Search completed.");
                        return;
                    }
                }
                System.out.println("Student with roll number " + roll + " not found.");
            } catch (InputMismatchException ime) {
                System.out.println("Please enter a valid integer roll number.");
                sc.nextLine();
            }
        }

        public void mainMenu() {
            boolean running = true;
            while (running) {
                System.out.println("\n=====Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                try {
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            try {
                                addStudent();
                            } catch (InvalidMarksException ime) {
                                System.out.println("Error: " + ime.getMessage() + " Returning to main menu...");
                                sc.nextLine();
                            }
                            break;
                        case 2:
                            showStudentDetails();
                            break;
                        case 3:
                            System.out.println("Exiting program. Thank you!");
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Enter 1-3.");
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Invalid input. Please enter an integer choice.");
                    sc.nextLine();
                } finally {
                    System.out.println("(Returning to menu...)");
                }
            }

            sc.close();
            System.out.println("Scanner closed.");
        }

        public static void main(String[] args) {
            Scanner temp = new Scanner(System.in);
            System.out.print("Enter how many students you want to store: ");
            int capacity = temp.nextInt();
            System.out.println();

            ResultManager rm = new ResultManager(capacity);
            rm.mainMenu();
        }
    }



