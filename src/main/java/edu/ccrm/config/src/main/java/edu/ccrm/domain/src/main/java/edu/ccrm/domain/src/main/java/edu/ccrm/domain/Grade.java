package edu.ccrm.domain;

public enum Grade {
    S(10.0, "Outstanding"),
    A(9.0, "Excellent"),
    B(8.0, "Very Good"),
    C(7.0, "Good"),
    D(6.0, "Satisfactory"),
    E(5.0, "Pass"),
    F(0.0, "Fail");
    
    private final double gradePoint;
    private final String description;
    
    Grade(double gradePoint, String description) {
        this.gradePoint = gradePoint;
        this.description = description;
    }
    
    public double getGradePoint() {
        return gradePoint;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name() + " (" + gradePoint + ")";
    }
}
