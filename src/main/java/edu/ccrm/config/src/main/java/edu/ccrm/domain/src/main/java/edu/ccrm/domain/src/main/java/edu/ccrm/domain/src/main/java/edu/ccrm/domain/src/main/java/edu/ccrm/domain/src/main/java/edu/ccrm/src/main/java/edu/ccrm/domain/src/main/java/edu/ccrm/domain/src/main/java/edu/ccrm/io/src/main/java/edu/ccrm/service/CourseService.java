package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.util.ValidationException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseService implements Persistable<Course>, Searchable<Course> {
    private final Map<String, Course> courses = new ConcurrentHashMap<>();
    
    @Override
    public void save(Course course) throws IOException {
        Objects.requireNonNull(course, "Course cannot be null");
        courses.put(course.getCode(), course);
    }
    
    @Override
    public void saveAll(List<Course> courseList) throws IOException {
        for (Course course : courseList) {
            save(course);
        }
    }
    
    @Override
    public Course findById(String code) {
        return courses.get(code);
    }
    
    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courses.values());
    }
    
    @Override
    public void delete(String code) {
        Course course = courses.get(code);
        if (course != null) {
            course.setActive(false);
        }
    }
    
    @Override
    public boolean exists(String code) {
        return courses.containsKey(code);
    }
    
    @Override
    public List<Course> search(Predicate<Course> criteria) {
        return courses.values().stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }
    
    public List<Course> findByInstructor(String instructorId) {
        return search(course -> Objects.equals(course.getInstructorId(), instructorId));
    }
    
    public List<Course> findByDepartment(String department) {
        return search(course -> course.getDepartment() != null && 
                               course.getDepartment().equalsIgnoreCase(department));
    }
    
    public List<Course> findBySemester(Semester semester) {
        return search(course -> Objects.equals(course.getSemester(), semester));
    }
    
    public List<Course> getActiveCourses() {
        return search(Course::isActive);
    }
    
    public void assignInstructor(String courseCode, String instructorId) throws ValidationException {
        Course course = findById(courseCode);
        if (course == null) {
            throw new ValidationException("Course not found: " + courseCode);
        }
        course.setInstructorId(instructorId);
    }
    
    // Stream API demonstration for filtering and sorting
    public List<Course> getCoursesFilteredAndSorted(String department, Semester semester) {
        return courses.values().stream()
                .filter(course -> course.isActive())
                .filter(course -> department == null || 
                        (course.getDepartment() != null && course.getDepartment().equalsIgnoreCase(department)))
                .filter(course -> semester == null || Objects.equals(course.getSemester(), semester))
                .sorted(Comparator.comparing(Course::getCode))
                .collect(Collectors.toList());
    }
}
