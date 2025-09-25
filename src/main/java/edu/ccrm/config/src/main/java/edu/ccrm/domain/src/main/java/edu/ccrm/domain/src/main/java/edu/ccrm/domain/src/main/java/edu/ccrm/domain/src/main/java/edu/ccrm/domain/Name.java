package edu.ccrm.domain;

import java.util.Objects;

public final class Name {
    private final String firstName;
    private final String lastName;
    
    public Name(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName, "First name cannot be null");
        this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null");
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Name name = (Name) obj;
        return Objects.equals(firstName, name.firstName) && 
               Objects.equals(lastName, name.lastName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
    
    @Override
    public String toString() {
        return getFullName();
    }
}
