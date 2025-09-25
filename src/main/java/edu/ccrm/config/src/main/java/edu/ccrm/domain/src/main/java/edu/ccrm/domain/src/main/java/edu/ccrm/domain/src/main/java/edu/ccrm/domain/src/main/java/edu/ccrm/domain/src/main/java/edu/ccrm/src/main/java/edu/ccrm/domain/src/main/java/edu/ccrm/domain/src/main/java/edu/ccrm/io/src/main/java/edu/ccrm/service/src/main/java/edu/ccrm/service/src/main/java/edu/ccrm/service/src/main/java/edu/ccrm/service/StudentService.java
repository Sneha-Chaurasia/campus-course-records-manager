package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.util.ValidationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentService implements Persistable<Student>, Searchable<Student> {
    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Enrollment> enrollments = new ConcurrentHashMap<>();
    
    @Override
    public void save(Student student) throws IOException {
        Objects.requireNonNull(student, "Student cannot be null");
        students.put(student.getId(), student);
    }
    
    @Override
    public void saveAll(List<Student> studentList) throws IOException {
        for (Student student : studentList) {
            save(student);
        }
    }
    
    @Override
    public Student findById(String id) {
        return students.get(id);
    }
    
    @Override
    public List<Student> findAll() {
        return new ArrayList<>(students.values());
    }
    
    @Override
    public void delete(String id) {
        Student student = students.get(id);
        if (student != null) {
            student.setActive(false);
        }
    }
    
    @Override
    public boolean exists(String id) {
        return students.containsKey(id);
    }
    
    @Override
    public List<Student> search(Predicate<Student> criteria) {
        return students.values().stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }
    
    public void enrollStudent(String studentId, String courseCode, int maxCreditsPerSemester) 
            throws ValidationException {
        Student student = findById(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }
        
        if (student.getEnrolledCourses().contains(courseCode)) {
            throw new ValidationException("Student already enrolled in course: " + courseCode);
        }
        
        // Business rule: Check max credits per semester
        int currentCredits = getCurrentSemesterCredits(studentId);
        if (currentCredits >= maxCreditsPerSemester) {
            throw new ValidationException("Maximum credit limit exceeded for semester");
        }
        
        student.enrollInCourse(courseCode);
        Enrollment enrollment = new Enrollment(studentId, courseCode);
        enrollments.put(studentId + "_" + courseCode, enrollment);
    }
    
    public void unenrollStudent(String studentId, String courseCode) throws ValidationException {
        Student student = findById(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }
        
        student.unenrollFromCourse(courseCode);
        enrollments.remove(studentId + "_" + courseCode);
    }
    
    public void assignGrade(String studentId, String courseCode, Grade grade) 
            throws ValidationException {
        Student student = findById(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }
        
        if (!student.getEnrolledCourses().contains(courseCode)) {
            throw new ValidationException("Student not enrolled in course: " + courseCode);
        }
        
        student.assignGrade(courseCode, grade);
        Enrollment enrollment = enrollments.get(studentId + "_" + courseCode);
        if (enrollment != null) {
            enrollment.setGrade(grade);
        }
    }
    
    private int getCurrentSemesterCredits(String studentId) {
        // Simplified: assume 3 credits per course
        Student student = findById(studentId);
        return student != null ? student.getEnrolledCourses().size() * 3 : 0;
    }
    
    public List<Student> findByRegNo(String regNo) {
        return search(student -> student.getRegNo().equalsIgnoreCase(regNo));
    }
    
    public List<Student> getActiveStudents() {
        return search(Student::isActive);
    }
}
