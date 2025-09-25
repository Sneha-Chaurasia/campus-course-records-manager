package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.*;

public class Instructor extends Person {
    private String employeeId;
    private String department;
    private Set<String> assignedCourses;
    
    public Instructor(String id, String employeeId, Name name, String email, 
                     LocalDate dateOfBirth, String department) {
        super(id, name, email, dateOfBirth);
        this.employeeId = Objects.requireNonNull(employeeId, "Employee ID cannot be null");
        this.department = Objects.requireNonNull(department, "Department cannot be null");
        this.assignedCourses = new HashSet<>();
    }
    
    @Override
    public String getRole() {
        return "Instructor";
    }
    
    @Override
    public void displayProfile() {
        System.out.println("=== Instructor Profile ===");
        System.out.println("ID: " + id);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name.getFullName());
        System.out.println("Email: " + email);
        System.out.println("Department: " + department);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Status: " + (active ? "Active" : "Inactive"));
        System.out.println("Assigned Courses: " + assignedCourses.size());
    }
    
    public void assignCourse(String courseCode) {
        assignedCourses.add(courseCode);
    }
    
    public void unassignCourse(String courseCode) {
        assignedCourses.remove(courseCode);
    }
    
    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public Set<String> getAssignedCourses() { return new HashSet<>(assignedCourses); }
    
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setDepartment(String department) { this.department = department; }
    
    @Override
    public String toString() {
        return String.format("Instructor[id=%s, empId=%s, name=%s, dept=%s, courses=%d]", 
                           id, employeeId, name, department, assignedCourses.size());
    }
}
