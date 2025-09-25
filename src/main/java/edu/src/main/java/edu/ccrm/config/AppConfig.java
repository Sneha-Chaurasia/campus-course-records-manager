package edu.ccrm.domain;

import java.util.Objects;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructorId;
    private Semester semester;
    private String department;
    private boolean active;
    
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructorId = builder.instructorId;
        this.semester = builder.semester;
        this.department = builder.department;
        this.active = true;
    }
    
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructorId;
        private Semester semester;
        private String department;
        
        public Builder(String code, String title) {
            this.code = Objects.requireNonNull(code, "Course code cannot be null");
            this.title = Objects.requireNonNull(title, "Course title cannot be null");
        }
        
        public Builder credits(int credits) {
            if (credits <= 0) throw new IllegalArgumentException("Credits must be positive");
            this.credits = credits;
            return this;
        }
        
        public Builder instructor(String instructorId) {
            this.instructorId = instructorId;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            return new Course(this);
        }
    }
    
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    
    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public void setDepartment(String department) { this.department = department; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return Objects.equals(code, course.code);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
    
    @Override
    public String toString() {
        return String.format("Course[code=%s, title=%s, credits=%d, dept=%s, semester=%s]", 
                           code, title, credits, department, semester);
    }
}
