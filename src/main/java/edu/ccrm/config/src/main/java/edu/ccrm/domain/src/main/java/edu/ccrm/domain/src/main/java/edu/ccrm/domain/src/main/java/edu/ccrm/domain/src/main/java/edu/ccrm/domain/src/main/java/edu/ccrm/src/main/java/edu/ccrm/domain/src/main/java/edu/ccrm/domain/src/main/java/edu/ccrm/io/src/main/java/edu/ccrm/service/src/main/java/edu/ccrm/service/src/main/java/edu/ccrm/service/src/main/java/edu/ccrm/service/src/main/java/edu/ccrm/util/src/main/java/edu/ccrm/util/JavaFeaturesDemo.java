package edu.ccrm.util;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstration class for various Java language features
 * This class showcases concepts required by the project specification
 */
public class JavaFeaturesDemo {
    
    // Demonstrate operator precedence and bitwise operations
    public static void demonstrateOperators() {
        System.out.println("=== Operator Precedence & Bitwise Operations ===");
        
        // Arithmetic operator precedence: multiplication before addition
        int result1 = 5 + 3 * 2; // Result: 11 (not 16)
        System.out.println("5 + 3 * 2 = " + result1);
        
        // Relational and logical operators
        boolean condition = (10 > 5) && (3 < 7); // true
        System.out.println("(10 > 5) && (3 < 7) = " + condition);
        
        // Bitwise operations
        int a = 12; // 1100 in binary
        int b = 10; // 1010 in binary
        
        System.out.println("a = " + a + " (binary: " + Integer.toBinaryString(a) + ")");
        System.out.println("b = " + b + " (binary: " + Integer.toBinaryString(b) + ")");
        System.out.println("a & b = " + (a & b) + " (AND)");
        System.out.println("a | b = " + (a | b) + " (OR)");
        System.out.println("a ^ b = " + (a ^ b) + " (XOR)");
        System.out.println("~a = " + (~a) + " (NOT)");
        System.out.println("a << 1 = " + (a << 1) + " (Left shift)");
        System.out.println("a >> 1 = " + (a >> 1) + " (Right shift)");
        System.out.println();
    }
    
    // Demonstrate arrays and Arrays utility class
    public static void demonstrateArrays() {
        System.out.println("=== Arrays and Arrays Utility ===");
        
        // Array creation and initialization
        String[] courseIds = {"CS101", "MATH201", "PHYS101", "ENG101", "HIST101"};
        
        System.out.println("Original course IDs: " + Arrays.toString(courseIds));
        
        // Sorting using Arrays.sort()
        Arrays.sort(courseIds);
        System.out.println("Sorted course IDs: " + Arrays.toString(courseIds));
        
        // Binary search (array must be sorted)
        int index = Arrays.binarySearch(courseIds, "ENG101");
        System.out.println("Index of ENG101: " + index);
        
        // Array copying
        String[] copiedIds = Arrays.copyOf(courseIds, courseIds.length);
        System.out.println("Copied array: " + Arrays.toString(copiedIds));
        
        // Fill array
        int[] grades = new int[5];
        Arrays.fill(grades, 85);
        System.out.println("Filled grades array: " + Arrays.toString(grades));
        
        System.out.println();
    }
    
    // Demonstrate string methods
    public static void demonstrateStrings() {
        System.out.println("=== String Methods Demonstration ===");
        
        String studentName = "  John Michael Doe  ";
        String email = "john.doe@university.edu";
        
        // String methods
        System.out.println("Original: '" + studentName + "'");
        System.out.println("Trimmed: '" + studentName.trim() + "'");
        System.out.println("Uppercase: " + studentName.toUpperCase());
        System.out.println("Lowercase: " + studentName.toLowerCase());
        
        // Substring operations
        String firstName = studentName.trim().substring(0, studentName.trim().indexOf(' '));
        System.out.println("First name: " + firstName);
        
        // String splitting and joining
        String[] nameParts = studentName.trim().split("\\s+");
        System.out.println("Name parts: " + Arrays.toString(nameParts));
        String joinedName = String.join("-", nameParts);
        System.out.println("Joined with hyphens: " + joinedName);
        
        // String comparison
        String name1 = "John";
        String name2 = new String("John");
        System.out.println("name1 == name2: " + (name1 == name2)); // false
        System.out.println("name1.equals(name2): " + name1.equals(name2)); // true
        System.out.println("name1.compareTo(name2): " + name1.compareTo(name2)); // 0
        
        // Email validation using string methods
        boolean isValidEmail = email.contains("@") && email.contains(".") && 
                              email.indexOf("@") < email.lastIndexOf(".");
        System.out.println("Is valid email: " + isValidEmail);
        
        System.out.println();
    }
    
    // Demonstrate control structures with labeled breaks
    public static void demonstrateControlStructures() {
        System.out.println("=== Control Structures with Labeled Break ===");
        
        // Nested loops with labeled break
        outerLoop: for (int semester = 1; semester <= 3; semester++) {
            System.out.println("Semester " + semester + ":");
            
            for (int course = 1; course <= 5; course++) {
                if (semester == 2 && course == 3) {
                    System.out.println("  Breaking out of both loops at semester 2, course 3");
                    break outerLoop; // Labeled break
                }
                
                if (course == 2) {
                    System.out.println("  Skipping course 2");
                    continue; // Continue to next iteration
                }
                
                System.out.println("  Course " + course);
            }
        }
        
        // Do-while loop example
        int attempts = 0;
        do {
            attempts++;
            System.out.println("Login attempt: " + attempts);
        } while (attempts < 3);
        
        System.out.println();
    }
    
    // Demonstrate upcasting and downcasting with instanceof
    public static void demonstrateCasting(List<Person> people) {
        System.out.println("=== Upcasting and Downcasting ===");
        
        for (Person person : people) {
            // Upcasting (implicit) - Person reference to Student/Instructor object
            System.out.println("Person: " + person.getName().getFullName() + " (" + person.getRole() + ")");
            
            // Downcasting with instanceof check (necessary for type safety)
            if (person instanceof Student) {
                Student student = (Student) person; // Explicit downcasting
                System.out.println("  Student GPA: " + String.format("%.2f", student.calculateGPA()));
                System.out.println("  Enrolled courses: " + student.getEnrolledCourses().size());
            } else if (person instanceof Instructor) {
                Instructor instructor = (Instructor) person; // Explicit downcasting
                System.out.println("  Instructor department: " + instructor.getDepartment());
                System.out.println("  Assigned courses: " + instructor.getAssignedCourses().size());
            }
        }
        
        System.out.println();
    }
    
    // Demonstrate method overloading
    public static void printGradeInfo(Grade grade) {
        System.out.println("Grade: " + grade.name() + " (" + grade.getGradePoint() + " points)");
    }
    
    public static void printGradeInfo(Grade grade, String courseName) {
        System.out.println("Course: " + courseName + ", Grade: " + grade.name() + 
                          " (" + grade.getGradePoint() + " points)");
    }
    
    public static void printGradeInfo(Grade grade, String courseName, int credits) {
        double totalPoints = grade.getGradePoint() * credits;
        System.out.println("Course: " + courseName + " (" + credits + " credits), " +
                          "Grade: " + grade.name() + ", Total Points: " + totalPoints);
    }
    
    // Demonstrate varargs
    public static double calculateAverageGPA(Student... students) {
        if (students.length == 0) return 0.0;
        
        return Arrays.stream(students)
                .mapToDouble(Student::calculateGPA)
                .average()
                .orElse(0.0);
    }
    
    // Demonstrate lambda expressions and functional interfaces
    public static void demonstrateLambdas(List<Student> students) {
        System.out.println("=== Lambda Expressions and Functional Interfaces ===");
        
        // Predicate lambda
        List<Student> highPerformers = students.stream()
                .filter(student -> student.calculateGPA() >= 8.0) // Lambda predicate
                .collect(Collectors.toList());
        
        System.out.println("High performers (GPA >= 8.0): " + highPerformers.size());
        
        // Comparator lambda
        students.stream()
                .sorted((s1, s2) -> Double.compare(s2.calculateGPA(), s1.calculateGPA())) // Lambda comparator
                .limit(3)
                .forEach(student -> System.out.println("  " + student.getName().getFullName() + 
                                                      ": " + String.format("%.2f", student.calculateGPA())));
        
        // Method reference
        students.stream()
                .map(Student::getName) // Method reference
                .map(Name::getFullName) // Method reference
                .sorted()
                .forEach(System.out::println); // Method reference
        
        System.out.println();
    }
}
