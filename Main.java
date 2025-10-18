// Main.java
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentManager manager = new StudentManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> searchStudentById();
                case 6 -> searchStudentByName();
                case 7 -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Exit");
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");
        int id = readInt("Enter student ID (integer): ");
        if (manager.idExists(id)) {
            System.out.println("A student with that ID already exists. Use update instead.");
            return;
        }
        String name = readString("Enter name: ");
        int age = readInt("Enter age: ");
        String course = readString("Enter course: ");
        Student s = new Student(id, name, age, course);
        manager.addStudent(s);
        System.out.println("Student added successfully.");
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> list = manager.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int id = readInt("Enter student ID to update: ");
        if (!manager.idExists(id)) {
            System.out.println("No student found with that ID.");
            return;
        }
        System.out.println("Leave a field empty to keep current value.");
        String name = readOptionalString("New name: ");
        Integer age = readOptionalInt("New age: ");
        String course = readOptionalString("New course: ");
        boolean ok = manager.updateStudent(id,
                name.isBlank() ? null : name,
                age,
                course.isBlank() ? null : course);
        if (ok) System.out.println("Student updated.");
        else System.out.println("Update failed.");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int id = readInt("Enter student ID to delete: ");
        boolean removed = manager.removeStudent(id);
        if (removed) System.out.println("Student removed.");
        else System.out.println("No student found with that ID.");
    }

    private static void searchStudentById() {
        System.out.println("\n--- Search by ID ---");
        int id = readInt("Enter ID: ");
        Student s = manager.searchById(id);
        if (s == null) System.out.println("Not found.");
        else System.out.println(s);
    }

    private static void searchStudentByName() {
        System.out.println("\n--- Search by Name ---");
        String name = readString("Enter name (partial allowed): ");
        List<Student> found = manager.searchByName(name);
        if (found.isEmpty()) System.out.println("No matches.");
        else found.forEach(System.out::println);
    }

    // --- Helper input functions ---
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static Integer readOptionalInt(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        if (line.isEmpty()) return null;
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered — keeping current value.");
            return null;
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    private static String readOptionalString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
}
