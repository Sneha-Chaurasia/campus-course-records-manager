package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Enrollment {
    private String studentId;
    private String courseCode;
    private LocalDateTime enrollmentDate;
    private Grade grade;
    private boolean active;
    
    public Enrollment(String studentId, String courseCode) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.courseCode = Objects.requireNonNull(courseCode, "Course code cannot be null");
        this.enrollmentDate = LocalDateTime.now();
        this.active = true;
    }
    
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public boolean isActive() { return active; }
    
    public void setGrade(Grade grade) { this.grade = grade; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return Objects.equals(studentId, that.studentId) && 
               Objects.equals(courseCode, that.courseCode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }
    
    @Override
    public String toString() {
        return String.format("Enrollment[student=%s, course=%s, grade=%s, date=%s]", 
                           studentId, courseCode, grade, enrollmentDate.toLocalDate());
    }
}
