package edu.ccrm;

import edu.ccrm.cli.MenuSystem;
import edu.ccrm.config.AppConfig;

/**
 * Campus Course & Records Manager (CCRM)
 * Main application class demonstrating Java SE features including:
 * - OOP principles (Encapsulation, Inheritance, Abstraction, Polymorphism)
 * - Design patterns (Singleton, Builder)
 * - Modern Java features (Streams, Lambdas, Date/Time API)
 * - Exception handling and NIO.2 file operations
 * 
 * @author CCRM Development Team
 * @version 1.0
 */
public class CCRMApplication {
    
    public static void main(String[] args) {
        // Enable assertions (demonstrate assertion usage)
        assert args != null : "Arguments array should not be null";
        
        try {
            // Initialize application configuration (Singleton pattern)
            AppConfig.getInstance();
            
            // Display system information
            displaySystemInfo();
            
            // Start the menu system
            MenuSystem menuSystem = new MenuSystem();
            menuSystem.start();
            
        } catch (Exception e) {
            System.err.println("Fatal error starting application: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Application shutdown complete.");
        }
    }
    
    /**
     * Display system and Java platform information
     * Demonstrates string operations and system properties
     */
    private static void displaySystemInfo() {
        System.out.println("=".repeat(60));
        System.out.println("CAMPUS COURSE & RECORDS MANAGER (CCRM)");
        System.out.println("=".repeat(60));
        
        // Demonstrate string operations and system properties
        String javaVersion = System.getProperty("java.version");
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String userHome = System.getProperty("user.home");
        
        System.out.println("System Information:");
        System.out.println("- Java Version: " + javaVersion);
        System.out.println("- Operating System: " + osName + " " + osVersion);
        System.out.println("- User Home: " + userHome);
        System.out.println("- Available Processors: " + Runtime.getRuntime().availableProcessors());
        
        // Demonstrate arithmetic operations and operator precedence
        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        
        // Operator precedence: division before subtraction
        long usedMemory = totalMemory - freeMemory;
        double memoryUsagePercent = (double) usedMemory / totalMemory * 100;
        
        System.out.printf("- Memory Usage: %.1f%% (Used: %d MB, Total: %d MB)%n",
                         memoryUsagePercent,
                         usedMemory / (1024 * 1024),
                         totalMemory / (1024 * 1024));
        
        System.out.println("=".repeat(60));
        
        // Demonstrate bitwise operations
        int flags = 0b1010; // Binary literal
        System.out.println("Application flags (binary): " + Integer.toBinaryString(flags));
        System.out.println("Debug mode: " + ((flags & 0b0010) != 0 ? "ON" : "OFF"));
        System.out.println("Verbose mode: " + ((flags & 0b1000) != 0 ? "ON" : "OFF"));
        System.out.println();
    }
}
