package edu.ccrm.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Student extends Person {
    private String regNo;
    private LocalDateTime enrollmentDate;
    private Set<String> enrolledCourses;
    private Map<String, Grade> grades;
    
    public Student(String id, String regNo, Name name, String email, LocalDate dateOfBirth) {
        super(id, name, email, dateOfBirth);
        this.regNo = Objects.requireNonNull(regNo, "Registration number cannot be null");
        this.enrollmentDate = LocalDateTime.now();
        this.enrolledCourses = new HashSet<>();
        this.grades = new HashMap<>();
    }
    
    @Override
    public String getRole() {
        return "Student";
    }
    
    @Override
    public void displayProfile() {
        System.out.println("=== Student Profile ===");
        System.out.println("ID: " + id);
        System.out.println("Registration No: " + regNo);
        System.out.println("Name: " + name.getFullName());
        System.out.println("Email: " + email);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Enrollment Date: " + enrollmentDate.toLocalDate());
        System.out.println("Status: " + (active ? "Active" : "Inactive"));
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
        System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
    }
    
    public void enrollInCourse(String courseCode) {
        enrolledCourses.add(courseCode);
    }
    
    public void unenrollFromCourse(String courseCode) {
        enrolledCourses.remove(courseCode);
        grades.remove(courseCode);
    }
    
    public void assignGrade(String courseCode, Grade grade) {
        if (enrolledCourses.contains(courseCode)) {
            grades.put(courseCode, grade);
        }
    }
    
    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;
        return grades.values().stream()
                .mapToDouble(Grade::getGradePoint)
                .average()
                .orElse(0.0);
    }
    
    public String getRegNo() { return regNo; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Set<String> getEnrolledCourses() { return new HashSet<>(enrolledCourses); }
    public Map<String, Grade> getGrades() { return new HashMap<>(grades); }
    
    public void setRegNo(String regNo) { this.regNo = regNo; }
    
    @Override
    public String toString() {
        return String.format("Student[id=%s, regNo=%s, name=%s, courses=%d, gpa=%.2f]", 
                           id, regNo, name, enrolledCourses.size(), calculateGPA());
    }
}
