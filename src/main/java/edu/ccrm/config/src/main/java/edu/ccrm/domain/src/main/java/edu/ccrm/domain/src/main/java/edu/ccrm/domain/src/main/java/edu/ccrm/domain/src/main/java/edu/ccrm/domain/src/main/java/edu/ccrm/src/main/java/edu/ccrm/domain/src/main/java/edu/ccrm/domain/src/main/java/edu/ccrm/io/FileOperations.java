package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOperations {
    private final AppConfig config = AppConfig.getInstance();
    
    public void exportStudentsToCSV(List<Student> students, String filename) throws IOException {
        Path exportPath = config.getExportFolderPath();
        Files.createDirectories(exportPath);
        
        Path filePath = exportPath.resolve(filename);
        List<String> lines = new ArrayList<>();
        lines.add("ID,RegNo,FirstName,LastName,Email,DateOfBirth,Active,EnrollmentDate");
        
        for (Student student : students) {
            String line = String.join(",",
                student.getId(),
                student.getRegNo(),
                student.getName().getFirstName(),
                student.getName().getLastName(),
                student.getEmail(),
                student.getDateOfBirth().toString(),
                String.valueOf(student.isActive()),
                student.getEnrollmentDate().toLocalDate().toString()
            );
            lines.add(line);
        }
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Students exported to: " + filePath.toAbsolutePath());
    }
    
    public void exportCoursesToCSV(List<Course> courses, String filename) throws IOException {
        Path exportPath = config.getExportFolderPath();
        Files.createDirectories(exportPath);
        
        Path filePath = exportPath.resolve(filename);
        List<String> lines = new ArrayList<>();
        lines.add("Code,Title,Credits,InstructorId,Semester,Department,Active");
        
        for (Course course : courses) {
            String line = String.join(",",
                course.getCode(),
                course.getTitle(),
                String.valueOf(course.getCredits()),
                course.getInstructorId() != null ? course.getInstructorId() : "",
                course.getSemester() != null ? course.getSemester().name() : "",
                course.getDepartment() != null ? course.getDepartment() : "",
                String.valueOf(course.isActive())
            );
            lines.add(line);
        }
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Courses exported to: " + filePath.toAbsolutePath());
    }
    
    public List<Student> importStudentsFromCSV(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        
        List<Student> students = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            students = lines.skip(1) // Skip header
                    .map(this::parseStudentFromCSV)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        
        System.out.println("Imported " + students.size() + " students from: " + filePath.toAbsolutePath());
        return students;
    }
    
    public List<Course> importCoursesFromCSV(String filename) throws IOException {
        Path filePath = Paths.get(filename);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + filename);
        }
        
        List<Course> courses = new ArrayList<>();
        try (Stream<String> lines = Files.lines(filePath)) {
            courses = lines.skip(1) // Skip header
                    .map(this::parseCourseFromCSV)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        
        System.out.println("Imported " + courses.size() + " courses from: " + filePath.toAbsolutePath());
        return courses;
    }
    
    private Student parseStudentFromCSV(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                Name name = new Name(parts[2], parts[3]);
                return new Student(parts[0], parts[1], name, parts[4], 
                                 java.time.LocalDate.parse(parts[5]));
            }
        } catch (Exception e) {
            System.err.println("Error parsing student line: " + line + " - " + e.getMessage());
        }
        return null;
    }
    
    private Course parseCourseFromCSV(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                Course.Builder builder = new Course.Builder(parts[0], parts[1])
                        .credits(Integer.parseInt(parts[2]));
                
                if (parts.length > 3 && !parts[3].isEmpty()) {
                    builder.instructor(parts[3]);
                }
                if (parts.length > 4 && !parts[4].isEmpty()) {
                    builder.semester(Semester.valueOf(parts[4]));
                }
                if (parts.length > 5 && !parts[5].isEmpty()) {
                    builder.department(parts[5]);
                }
                
                return builder.build();
            }
        } catch (Exception e) {
            System.err.println("Error parsing course line: " + line + " - " + e.getMessage());
        }
        return null;
    }
    
    public void createBackup() throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = config.getBackupFolderPath().resolve("backup_" + timestamp);
        Files.createDirectories(backupDir);
        
        Path exportDir = config.getExportFolderPath();
        if (Files.exists(exportDir)) {
            try (Stream<Path> files = Files.walk(exportDir, 1)) {
                files.filter(Files::isRegularFile)
                     .forEach(file -> {
                         try {
                             Files.copy(file, backupDir.resolve(file.getFileName()), 
                                       StandardCopyOption.REPLACE_EXISTING);
                         } catch (IOException e) {
                             System.err.println("Error copying file: " + e.getMessage());
                         }
                     });
            }
        }
        
        System.out.println("Backup created at: " + backupDir.toAbsolutePath());
    }
    
    // Recursive utility to calculate total size of backup directory
    public long calculateBackupSize() throws IOException {
        Path backupDir = config.getBackupFolderPath();
        if (!Files.exists(backupDir)) {
            return 0;
        }
        
        return calculateDirectorySize(backupDir);
    }
    
    private long calculateDirectorySize(Path directory) throws IOException {
        if (!Files.isDirectory(directory)) {
            return Files.size(directory);
        }
        
        long totalSize = 0;
        try (Stream<Path> paths = Files.list(directory)) {
            for (Path path : paths.collect(Collectors.toList())) {
                totalSize += calculateDirectorySize(path); // Recursive call
            }
        }
        return totalSize;
    }
    
    // Recursive utility to list files by depth
    public void listFilesByDepth(Path directory, int maxDepth) throws IOException {
        listFilesByDepth(directory, 0, maxDepth);
    }
    
    private void listFilesByDepth(Path directory, int currentDepth, int maxDepth) throws IOException {
        if (currentDepth > maxDepth || !Files.exists(directory)) {
            return;
        }
        
        String indent = "  ".repeat(currentDepth);
        System.out.println(indent + directory.getFileName() + 
                          (Files.isDirectory(directory) ? "/" : ""));
        
        if (Files.isDirectory(directory) && currentDepth < maxDepth) {
            try (Stream<Path> paths = Files.list(directory)) {
                for (Path path : paths.collect(Collectors.toList())) {
                    listFilesByDepth(path, currentDepth + 1, maxDepth); // Recursive call
                }
            }
        }
    }
}
