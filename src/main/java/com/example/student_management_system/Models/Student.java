package com.example.student_management_system.Models;

public class Student {
    private String name;
    private int age;
    private double grade;
    private String studentID;

    public Student(String name, int age, double grade, String studentID) {
        setName(name);
        setAge(age);
        setGrade(grade);
        this.studentID = studentID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    // Getter and Setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer");
        }
        this.age = age;
    }

    // Getter and Setter for grade
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0");
        }
        this.grade = grade;
    }

    // Getter for studentID (no setter to ensure immutability)
    public String getStudentID() {
        return studentID;
    }

    // Method to display student information
    public void displayInfo() {
        System.out.println("Student Details:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Grade: " + grade);
        System.out.println("Student ID: " + studentID);
    }

    // Main method for testing
    public static void main(String[] args) {
        Student student = new Student("John Doe", 20, 85.5, "S12345");
        student.displayInfo();
    }
}
