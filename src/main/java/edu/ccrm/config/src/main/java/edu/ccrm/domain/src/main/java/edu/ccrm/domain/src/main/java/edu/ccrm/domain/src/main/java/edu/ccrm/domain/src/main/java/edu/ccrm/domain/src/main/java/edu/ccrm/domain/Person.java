package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Person {
    protected String id;
    protected Name name;
    protected String email;
    protected LocalDate dateOfBirth;
    protected boolean active;
    
    public Person(String id, Name name, String email, LocalDate dateOfBirth) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null");
        this.active = true;
    }
    
    public abstract String getRole();
    public abstract void displayProfile();
    
    public String getId() { return id; }
    public Name getName() { return name; }
    public String getEmail() { return email; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public boolean isActive() { return active; }
    
    public void setName(Name name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return Objects.equals(id, person.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s, email=%s, active=%s]", 
                           getClass().getSimpleName(), id, name, email, active);
    }
}
