package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class TranscriptService {
    private final StudentService studentService;
    private final CourseService courseService;
    
    public TranscriptService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    public void printTranscript(String studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) {
            System.out.println("Student not found: " + studentId);
            return;
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("OFFICIAL TRANSCRIPT");
        System.out.println("=".repeat(60));
        
        // Polymorphic call - uses Student's overridden displayProfile method
        student.displayProfile();
        
        System.out.println("\n" + "-".repeat(60));
        System.out.println("ACADEMIC RECORD");
        System.out.println("-".repeat(60));
        
        Map<String, Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            System.out.println("No grades recorded.");
        } else {
            System.out.printf("%-10s %-30s %-8s %-6s%n", "Course", "Title", "Credits", "Grade");
            System.out.println("-".repeat(60));
            
            double totalGradePoints = 0;
            int totalCredits = 0;
            
            for (Map.Entry<String, Grade> entry : grades.entrySet()) {
                String courseCode = entry.getKey();
                Grade grade = entry.getValue();
                Course course = courseService.findById(courseCode);
                
                String courseTitle = course != null ? course.getTitle() : "Unknown";
                int credits = course != null ? course.getCredits() : 3;
                
                System.out.printf("%-10s %-30s %-8d %-6s%n", 
                                courseCode, courseTitle, credits, grade.name());
                
                totalGradePoints += grade.getGradePoint() * credits;
                totalCredits += credits;
            }
            
            System.out.println("-".repeat(60));
            System.out.printf("Total Credits: %d%n", totalCredits);
            System.out.printf("GPA: %.2f%n", totalCredits > 0 ? totalGradePoints / totalCredits : 0.0);
        }
        
        System.out.println("=".repeat(60));
        System.out.println("End of Transcript");
        System.out.println("=".repeat(60) + "\n");
    }
    
    // Builder pattern for transcript generation
    public static class TranscriptBuilder {
        private String studentId;
        
        public TranscriptBuilder forStudent(String studentId) {
            this.studentId = studentId;
            return this;
        }
        
        public String build(TranscriptService service) {
            // Build transcript as string instead of printing
            Student student = service.studentService.findById(studentId);
            if (student == null) {
                return "Student not found: " + studentId;
            }
            
            StringBuilder transcript = new StringBuilder();
            transcript.append("TRANSCRIPT FOR: ").append(student.getName().getFullName()).append("\n");
            transcript.append("GPA: ").append(String.format("%.2f", student.calculateGPA())).append("\n");
            
            return transcript.toString();
        }
    }
    
    // Anonymous inner class example for custom transcript formatter
    public void printCustomTranscript(String studentId) {
        TranscriptFormatter formatter = new TranscriptFormatter() {
            @Override
            public void format(Student student, Map<String, Course> courseMap) {
                System.out.println("*** CUSTOM TRANSCRIPT FORMAT ***");
                System.out.println("Student: " + student.getName().getFullName());
                System.out.println("ID: " + student.getId());
                System.out.println("Current GPA: " + String.format("%.2f", student.calculateGPA()));
                
                student.getGrades().forEach((courseCode, grade) -> {
                    Course course = courseMap.get(courseCode);
                    System.out.printf("%s: %s (%s)%n", 
                                    courseCode, 
                                    grade.name(), 
                                    course != null ? course.getTitle() : "Unknown");
                });
                System.out.println("*** END CUSTOM TRANSCRIPT ***");
            }
        };
        
        Student student = studentService.findById(studentId);
        if (student != null) {
            Map<String, Course> courseMap = courseService.findAll().stream()
                    .collect(Collectors.toMap(Course::getCode, course -> course));
            formatter.format(student, courseMap);
        }
    }
    
    // Functional interface for transcript formatting
    @FunctionalInterface
    private interface TranscriptFormatter {
        void format(Student student, Map<String, Course> courseMap);
    }
}
